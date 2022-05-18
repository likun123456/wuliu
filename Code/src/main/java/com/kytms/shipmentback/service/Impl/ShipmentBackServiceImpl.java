package com.kytms.shipmentback.service.Impl;

import com.kytms.core.constants.Symbol;
import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.DateUtils;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.SpringUtils;
import com.kytms.orderback.dao.OrderBackDao;
import com.kytms.shipmentback.dao.Impl.ShipmentBackDetailDaoImpl;
import com.kytms.shipmentback.dao.ShipmentBackDao;
import com.kytms.shipmentback.dao.ShipmentBackDetailDao;
import com.kytms.shipmentback.service.ShipmentBackService;
import com.kytms.transportorder.OrderStatus;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2018-01-19
 */
@Service(value = "ShipmentBackService")
public class ShipmentBackServiceImpl extends BaseServiceImpl<ShipmentBack> implements ShipmentBackService<ShipmentBack> {
    private final Logger log = Logger.getLogger(ShipmentBackServiceImpl.class);//输出Log日志
    private ShipmentBackDao shipmentBackDao;
    @Resource(name = "ShipmentBackDao")
    public void setShipmentBackDao(ShipmentBackDao shipmentBackDao) {
        super.setBaseDao(shipmentBackDao);
        this.shipmentBackDao = shipmentBackDao;
    }
    private ShipmentBackDetailDao bean = SpringUtils.getBean(ShipmentBackDetailDao.class);




    public JgGridListModel getShipmentBackList(CommModel commModel) {
        String where = " and organization.id ='" + SessionUtil.getOrgId() + "'";
        return super.getListByPage(commModel, where, null);
    }

    public void sing(ShipmentBackDetail shipmentBack) {
        ShipmentBackDetail shipmentBackDetail = (ShipmentBackDetail) bean.selectBean(shipmentBack.getId());
        ShipmentBack shipmentBack1 = shipmentBackDetail.getShipmentBack();
        if (shipmentBackDetail.getSingNumber() == shipmentBackDetail.getBackNumber()) {
            throw new MessageException("此单已经签收完成，不能修改");
        }
        if (shipmentBack1.getStatus() != OrderStatus.NOT_SING.getValue()) {
            throw new MessageException("只能签收未签收状态的运单回单");
        }
        shipmentBackDetail.setTime(shipmentBack.getTime());
        shipmentBackDetail.setSingNumber(shipmentBack.getSingNumber());
        updateShipmentBackSing(shipmentBack1);
//        ShipmentBack sb = (ShipmentBack) shipmentBackDao.selectBean(shipmentBack.getId());
//        if (sb.getStatus() != OrderStatus.NOT_SING.getValue()){
//            throw new MessageException("只能签收未签收状态的运单");
//        }
//        sb.setDescription(shipmentBack.getDescription());
//        sb.setTime(shipmentBack.getTime());
    }

    /**
     * 主单据签收判定，如果子单据全部签收完成，则变更主单据数据 传入持久化运单回单对象
     */
    private void updateShipmentBackSing(ShipmentBack shipmentBack) {
        boolean isTrue = true;
        int singNumber = 0;
        List<ShipmentBackDetail> shipmentBackDetailList = shipmentBack.getShipmentBackDetailList();
        for (ShipmentBackDetail shipmentBackDetail : shipmentBackDetailList) {
            if (shipmentBackDetail.getSingNumber() != shipmentBackDetail.getBackNumber()) { //如果子单据有任意一个数据不相等
                isTrue = false;
            }
            singNumber += shipmentBackDetail.getSingNumber();
        }
        shipmentBack.setSingNumber(singNumber);//修改主单据数量
        if (isTrue) { //如果相等 则修改住单据状态
            shipmentBack.setStatus(OrderStatus.END_SING.getValue());
            shipmentBack.setTime(DateUtils.getTimestamp());
        }

    }

    public void back(ShipmentBack shipmentBack) {
        ShipmentBack sb = (ShipmentBack) shipmentBackDao.selectBean(shipmentBack.getId());
        if (sb.getStatus() == OrderStatus.END_SING.getValue() || sb.getStatus() == OrderStatus.SING_EXCEPTION.getValue()) {
            sb.setBackTime(shipmentBack.getBackTime());
            sb.setBackType(shipmentBack.getBackType());
            sb.setExpressCode(shipmentBack.getExpressCode());
            sb.setExpressName(shipmentBack.getExpressName());
            sb.setStatus(OrderStatus.BACK.getValue());
        } else {
            throw new MessageException("只能回单已签收状态或异常状态的的运单");
        }
        //修改运单、分段运单、订单回单状态，没处理拆分
        Shipment shipment = sb.getShipment();
        shipment.setStatus(OrderStatus.BACK.getValue());
        List<Led> leds = shipment.getLeds();
        for (Led led : leds) {
            Order order = led.getOrder();
            //每个订单生成订单回单
            OrderBackDao bean = SpringUtils.getBean(OrderBackDao.class);
            if (order.getIsBack() == 1) { //如果订单有回单
                if (order.getOrderBack() == null) {
                    OrderBack orderBack = new OrderBack();
                    orderBack.setOrder(order);
                    orderBack.setOrganization(order.getOrganization());
                    orderBack.setIsSubmit(0);
                    orderBack.setIsReceive(0);
                    orderBack.setSingNumber(sb.getSingNumber());
                    orderBack.setBackNumber(order.getBackNumber());
                    orderBack.setBackTime(sb.getBackTime());
                    orderBack.setExpressName(sb.getExpressName());
                    orderBack.setExpressCode(sb.getExpressCode());
                    orderBack.setTime(sb.getTime());
                    orderBack.setStatus(OrderStatus.UNABSORBED.getValue());
                    bean.savaBean(orderBack);
                }
                order.setStatus(OrderStatus.BACK_SEED.getValue());
            } else { //如果没有 直接完结
                order.setStatus(OrderStatus.END.getValue());
            }
        }
    }

    public void end(String ids) {
        String[] split = ids.split(Symbol.COMMA);
        for (String id : split) {
            ShipmentBack shipmentBack = (ShipmentBack) shipmentBackDao.selectBean(id);
            if (shipmentBack.getStatus() == OrderStatus.BACK.getValue() || shipmentBack.getStatus() == OrderStatus.NOT_BACK.getValue() || shipmentBack.getStatus() == OrderStatus.SING_EXCEPTION.getValue() || shipmentBack.getStatus() == OrderStatus.END_SING.getValue()) {
                shipmentBack.setStatus(OrderStatus.END.getValue());
                shipmentBack.getShipment().setStatus(OrderStatus.END.getValue());
            } else {
                throw new MessageException("签收失败，请检查运单状态");
            }
        }
    }

    public void singAll(CommModel comm) {
        ShipmentBack shipmentBack1 = (ShipmentBack) shipmentBackDao.selectBean(comm.getId());
        if (shipmentBack1.getStatus() != OrderStatus.NOT_SING.getValue()) {
            throw new MessageException("只能签收未签收状态的运单回单");
        }
        List<ShipmentBackDetail> shipmentBackDetailList = shipmentBack1.getShipmentBackDetailList();
        for (ShipmentBackDetail shipmentBackDetail : shipmentBackDetailList) {
            shipmentBackDetail.setSingNumber(shipmentBackDetail.getBackNumber());
            shipmentBackDetail.setTime(DateUtils.getTimestamp());
        }
        updateShipmentBackSing(shipmentBack1);
    }
}
