package com.kytms.orderArrive.service.impl;

import com.kytms.core.entity.*;
import com.kytms.core.exception.AppBugException;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.SqlUtils;
import com.kytms.inOrOutRecord.dao.InOrOutRecordDao;
import com.kytms.orderArrive.dao.OrderArriveDao;
import com.kytms.orderArrive.dao.impl.OrderArriveDaoImpl;
import com.kytms.orderArrive.service.OrderArriveService;
import com.kytms.shipment.dao.ShipmentDao;
import com.kytms.transportorder.OrderTrackUtil;
import com.kytms.transportorder.dao.LedDao;
import com.kytms.transportorder.dao.TransportOrderDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:sundezeng
 * @Date:2018/10/29
 */
@Service(value = "OrderArriveService")
public class OrderArriveServiceImpl extends BaseServiceImpl<OrderArrive> implements OrderArriveService<OrderArrive> {
    private final Logger log = Logger.getLogger(OrderArriveServiceImpl.class);//输出Log日志
     private OrderArriveDao<OrderArrive> orderArriveDao;
     private TransportOrderDao<Order> transportOrderDao;//订单
     private InOrOutRecordDao<InOrOutRecord> inOrOutRecordDao;//出入库记录
     private LedDao<Led> ledDao;//分段运单
     private ShipmentDao<Shipment> shipmentDao;


     @Resource(name = "ShipmentDao")
    public void setShipmentDao(ShipmentDao<Shipment> shipmentDao) {
        this.shipmentDao = shipmentDao;
    }

    @Resource(name = "LedDao")
    public void setLedDao(LedDao<Led> ledDao) {
        this.ledDao = ledDao;
    }

    @Resource(name = "OrderArriveDao")
    public void setOrderArriveDao(OrderArriveDao<OrderArrive> orderArriveDao) {
        this.orderArriveDao = orderArriveDao;
        super.setBaseDao(orderArriveDao);
    }

    @Resource(name = "TransportOrderDao")
    public void setTransportOrderDao(TransportOrderDao<Order> transportOrderDao) {
        this.transportOrderDao = transportOrderDao;
    }
    @Resource(name = "InOrOutRecordDao")
    public void setInOrOutRecordDao(InOrOutRecordDao<InOrOutRecord> inOrOutRecordDao) {
        this.inOrOutRecordDao = inOrOutRecordDao;
    }
    private void AssBut(){
        throw new AppBugException("系统出现BUG，请联系大神奇趣源码");
    }
    /**
     * 货品到站操作
     *
     */
    public void saveOrderArrive1(String date, String ids) {
            Timestamp timestamp = Timestamp.valueOf(date.toString());
            String[] id= ids.split(",");
            Shipment shipment =null;
            for (String idd:id) {
                 Led led = ledDao.selectBean(idd); //获取分段运单
                   led.setShipment(null);
                List<Shipment> shipments = led.getShipments();
//                if(shipments.size() != 1){
//                    AssBut();
//                }
                if (shipment ==null) {
                    shipment = shipments.get(0); //提取运单
                }
                led.setOrganization(SessionUtil.getOrg()); //变更组织机构
                led.setFactArriveTime(timestamp);//修改到站时间
                //入库记录
                if(led.getOrganization().getId().equals(SessionUtil.getOrgId())){
                InOrOutRecord inOrOutRecord = new InOrOutRecord();
                   inOrOutRecord.setLed(led);
                   inOrOutRecord.setOrganization(SessionUtil.getOrg());
                   inOrOutRecord.setWeight(led.getWeight());
                   inOrOutRecord.setVolume(led.getVolume());
                   inOrOutRecord.setNumber(led.getNumber());
                   inOrOutRecord.setType(0);
                   inOrOutRecord.setTime(timestamp);
                   inOrOutRecord.setZoneStoreroom(led.getOrder().getZoneStoreroom());
                    inOrOutRecordDao.savaBean(inOrOutRecord);
                }
                //根据分段运单的状态，修改订单，分段的状态;
                //Organization formOrganization = led.getToOrganization(); //目的网点
                Organization organization = led.getEndZone().getOrganization();

                if(organization.getId().equals(SessionUtil.getOrgId())){ //如果分段订单的目的运点对应的组织机构和目的卸货网点相同
                    if(led.getHandoverType() == null){
                        AssBut();
                    }
                    int handoverType = led.getHandoverType();
                    if(handoverType == 0){ // 自提
                        led.setStatus(9);
                        updateOrder(led.getOrder(),9);
                    }
                    if(handoverType == 1){ //配送
                        led.setStatus(10);
                        led.setType(1);
                        updateOrder(led.getOrder(),10);
                    }
                }else {
                    updateOrder(led.getOrder(),11);//中转
                    led.setStatus(11);
                }
                //                led.setStatus(5);//修改分段订单状态
        }
//        String hql = "SELECT a FROM JC_LED a left join a.shipments b WHERE  b.id = '" + shipment.getId() + "' and a.id not in(" + SqlUtils.splitForIn(id) + ") ";
//        List<Led> objs = ledDao.executeQueryHql(hql);
//        List<Led> list = new ArrayList<Led>();
//        for (Led l:objs) {
//            list.add(l);
//        }
//        shipment.setLeds(list);
        //处理运单状态
        Organization toOrganization = shipment.getToOrganization();// 获取最终站点
        if(SessionUtil.getOrgId().equals(toOrganization.getId())){ //如果最终站点与当前登陆机构相同。
            long l = shipmentDao.selectCountByHql("Select count(a.id) from JC_LED a left join a.shipment b  where b.id = '" + shipment.getId() + "' group by a.id", null);
            if(l >0){
                shipment.setStatus(5);
            }else {
                shipment.setStatus(8);
            }
        }else{
            shipment.setStatus(5);
        }
    }

    /**
     * 查询订单下所有分段运单状态 如果分段运单状态都一致，则修改订单
     * @param order
     * @param i
     */
    private void updateOrder(Order order, int i) {
        boolean as = true;
        List<Led> leds = order.getLeds();
        for (Led led :leds) {
            int status = led.getStatus();
            if(status != i){
                as = false;
                break;
            }

        }
        if(as){
            order.setStatus(i);
            if(i == 9){
                OrderTrackUtil.addTrack(order,"订单已经全部到站，等待自提");
            }
            if(i==10){
                OrderTrackUtil.addTrack(order,"订单已经全部到站，等待派送");
            }
            if(i==11){
                OrderTrackUtil.addTrack(order,"订单已经全部到站，等待中转");
            }
        }
    }
}
