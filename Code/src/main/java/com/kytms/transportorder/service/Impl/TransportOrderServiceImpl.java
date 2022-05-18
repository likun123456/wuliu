package com.kytms.transportorder.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kytms.ServerZone.dao.ServerZoneDao;
import com.kytms.carrier.dao.CarrierDao;
import com.kytms.carrier.dao.impl.CarrierDaoImpl;
import com.kytms.core.constants.Entity;
import com.kytms.core.constants.Symbol;
import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.*;
import com.kytms.customerFile.dao.CustomerDao;
import com.kytms.customerFile.dao.impl.CustomerDaoimpl;
import com.kytms.feeTypeContrast.dao.FeeTypeContrastDao;
import com.kytms.feetype.dao.FeeTypeDao;
import com.kytms.feetype.dao.impl.FeeTypeDaoImpl;
import com.kytms.inOrOutRecord.dao.InOrOutRecordDao;
import com.kytms.ledgerdetails.dao.LedgerDetailDao;
import com.kytms.ledgerdetails.dao.impl.LedgerDetailDaoImpl;
import com.kytms.orderabnormal.dao.AbnormalDao;
import com.kytms.orderabnormal.dao.AbnormalDetailDao;
import com.kytms.orderabnormal.service.AbnormalService;
import com.kytms.orderback.dao.OrderBackDao;
import com.kytms.organization.dao.OrgDao;
import com.kytms.presco.dao.PrescoProductDao;
import com.kytms.receivingparty.dao.impl.ReceivingPartyDaoImpl;
import com.kytms.shipment.dao.BerthStandDao;
import com.kytms.shipment.dao.Impl.BerthStandDaoImpl;
import com.kytms.shipment.dao.Impl.ShipmentDaoImpl;
import com.kytms.shipment.dao.ShipmentDao;
import com.kytms.shipment.service.Impl.ShipmentServiceImpl;
import com.kytms.shipment.service.ShipmentService;
import com.kytms.shipmentTemplate.dao.impl.TemplateDaoImpl;
import com.kytms.single.service.impl.SingleServiceImpl;
import com.kytms.transportorder.OrderSql;
import com.kytms.transportorder.OrderStatus;
import com.kytms.transportorder.OrderTrackUtil;
import com.kytms.transportorder.dao.OrderProductDao;
import com.kytms.transportorder.dao.OrderRPDao;
import com.kytms.transportorder.dao.TransportOrderDao;
import com.kytms.transportorder.dao.impl.*;
import com.kytms.transportorder.service.TransportOrderService;
import com.kytms.verification.dao.VerificationDao;
import com.kytms.weizhitms.datasource.TMSDataSource;
import com.kytms.zone.dao.ZoneDao;
import com.kytms.zoneStoreroom.dao.ZoneStoreroomDao;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * @author 奇趣源码
 * @create 2017-11-23
 */
@Service(value = "TransportOrderService")
public class TransportOrderServiceImpl extends BaseServiceImpl<Order> implements TransportOrderService<Order> {
    private final Logger log = Logger.getLogger(TransportOrderServiceImpl.class);//输出Log日志
    private TransportOrderDao<Order> transportOrderDao;
    private LedgerDetailDao ledgerDetailDao;
    private AbnormalDetailDao abnormalDetailDao;
    private AbnormalService abnormalService;
    private AbnormalDao abnormalDao;
    private FeeTypeDao feeTypeDao;
    private OrderBackDao orderBackDao;
    private VerificationDao verificationDao;
    private PrescoProductDao prescoProductDao;
    private SingleServiceImpl singleService;
    private CustomerDao customerDao;
    private ZoneStoreroomDao zoneStoreroomDao;//在库区域
    private ServerZoneDao serverZoneDao;//运点
    private OrgDao orgDao;//目的运点
     private OrderProductDao orderProductDao;//货品
    private OrderRPDao neworderRPDao;
    private FeeTypeContrastDao feeTypeContrastDao;
//    private ZoneDao zoneDao;//城市
//
//    @Resource(name = "ZoneDao")
//    public void setZoneDao(ZoneDao zoneDao) {
//        this.zoneDao = zoneDao;
//    }

    @Resource(name = "FeeTypeContrastDao")
    public void setFeeTypeContrastDao(FeeTypeContrastDao feeTypeContrastDao) {
        this.feeTypeContrastDao = feeTypeContrastDao;
    }

    @Resource(name = "OrderRPDao")
    public void setNeworderRPDao(OrderRPDao neworderRPDao) {
        this.neworderRPDao = neworderRPDao;
    }
    @Resource(name = "OrderProductDao")
    public void setOrderProductDao(OrderProductDao orderProductDao) {
        this.orderProductDao = orderProductDao;
    }

    @Resource(name = "OrgDao")
    public void setOrgDao(OrgDao orgDao) {
        this.orgDao = orgDao;
    }

    @Resource(name = "ServerZoneDao")
    public void setServerZoneDao(ServerZoneDao serverZoneDao) {
        this.serverZoneDao = serverZoneDao;
    }

    @Resource(name = "ZoneStoreroomDao")
    public void setZoneStoreroomDao(ZoneStoreroomDao zoneStoreroomDao) {
        this.zoneStoreroomDao = zoneStoreroomDao;
    }

    @Resource(name = "CustomerDao")
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Resource(name = "PrescoProductDao")
    public void setPrescoProductDao(PrescoProductDao prescoProductDao) {
        this.prescoProductDao = prescoProductDao;
    }

    @Resource()
    public void setSingleService(SingleServiceImpl singleService) {
        this.singleService = singleService;
    }

    @Resource(name = "VerificationDao")
    public void setVerificationDao(VerificationDao verificationDao) {
        this.verificationDao = verificationDao;
    }

    @Resource(name = "OrderBackDao")
    public void setOrderBackDao(OrderBackDao orderBackDao) {
        this.orderBackDao = orderBackDao;
    }

    @Resource(name = "FeeTypeDao")
    public void setFeeTypeDao(FeeTypeDao feeTypeDao) {
        this.feeTypeDao = feeTypeDao;
    }

    @Resource(name = "AbnormalDao")
    public void setAbnormalDao(AbnormalDao abnormalDao) {
        this.abnormalDao = abnormalDao;
    }

    public static final String CODE_ONE = "-1";
    private InOrOutRecordDao<InOrOutRecord> inOrOutRecordDao;


    @Resource(name = "InOrOutRecordDao")
    public void setInOrOutRecordDao(InOrOutRecordDao<InOrOutRecord> inOrOutRecordDao) {
        this.inOrOutRecordDao = inOrOutRecordDao;
    }

    @Resource()
    public void setAbnormalService(AbnormalService abnormalService) {
        this.abnormalService = abnormalService;
    }

    @Resource(name = "AbnormalDetailDao")
    public void setAbnormalDetailDao(AbnormalDetailDao abnormalDetailDao) {
        this.abnormalDetailDao = abnormalDetailDao;
    }

    @Resource(name = "TransportOrderDao")
    public void setTransportOrderDao(TransportOrderDao transportOrderDao) {
        super.setBaseDao(transportOrderDao);
        this.transportOrderDao = transportOrderDao;
    }
    @Resource(name = "LedgerDetailDao")
    public void setLedgerDetailDao(LedgerDetailDao ledgerDetailDao) {
        this.ledgerDetailDao = ledgerDetailDao;
    }
    private ReceivingPartyDaoImpl receivingPartyDao = SpringUtils.getBean(ReceivingPartyDaoImpl.class);
    private ShipmentService shipmentService = SpringUtils.getBean(ShipmentServiceImpl.class);
    private OrderRPDao orderRPDao = SpringUtils.getBean(OrderRPDaoImpl.class);
    private ShipmentDao shipmentDao = SpringUtils.getBean(ShipmentDaoImpl.class);





    public JgGridListModel getOrderGrid(CommModel commModel) {
//        String status = commModel.getStatus();
//        String where = " and organization.id = '"+SessionUtil.getOrgId()+"'";
//        if (status != null){
//            where += " and status = "+status;
//        }else {
//            where += "and (status != 0 and status != 99)";
//        }
//        String orderBy = " order by create_Time desc";
//        return super.getListByPage(commModel,where,orderBy);
        String status = commModel.getStatus();
        String where = " and b.id = '" + SessionUtil.getOrgId() + "'";
        if (status != null) {
            where += " and a.status = " + status;
        } else {
            where += "and (a.status != 0 and a.status != 99)";
        }

        String orderBy = " group by a.id order by a.create_Time desc";
        return super.getListByPageToHql(OrderSql.ORDER_LIST, OrderSql.ORDER_COUNT, commModel, where, orderBy);
    }

    /**
     * 保存订单
     * @param order1
     * @return
     */
    public Order saveOrder(Order order1) {
        order1.setOrganization(SessionUtil.getOrg());//绑定组织机构
        if (order1.getIsBack() == 0) {//处理回单份数
            order1.setBackNumber(0);
        }

        if (StringUtils.isEmpty(order1.getId())) { // 如果是空 说明是新建
            order1.setCode(getOrderCode());
        }
        order1.setIsTake(0);
        //处理合同是否超期
        if (order1.getCustomer() == null) {
            order1.setCostomerIsExceed(Entity.UNACTIVE);
        } else {
            CustomerDaoimpl bean = SpringUtils.getBean(CustomerDaoimpl.class);
            Customer customer = bean.selectBean(order1.getCustomer().getId());
            Timestamp startTime = customer.getStartTime();
            Timestamp endTime = customer.getEndTime();
            Timestamp timestamp = order1.getTime();
            boolean b = DateUtils.belongCalendar(timestamp, startTime, endTime);
            if (b) {
                order1.setCostomerIsExceed(Entity.UNACTIVE);
            } else {
                order1.setCostomerIsExceed(Entity.ACTIVE);
            }
        }
        List<OrderReceivingParty> orderReceivingParties = order1.getOrderReceivingParties();//获取收发货方
        List<OrderProduct> orderProducts = order1.getOrderProducts(); //获取货品信息
        List<LedgerDetail> ledgerDetails = order1.getLedgerDetails();//获取台账信息
        order1.setOrderProducts(null); //阻断关联关系
        order1.setOrderReceivingParties(null);
        order1.setLedgerDetails(null);


        Order order2 = transportOrderDao.savaBean(order1); //保存订单单头
//        Order order =  transportOrderDao.selectBean(order2.getId());
//        order.setOrderProducts(null);
//        order.setOrderReceivingParties(null);
//        order.setLedgerDetails(null);

       transportOrderDao.executeHql(" Delete FROM JC_ORDER_RECEIVINGPARTY Where order.id = '" + order2.getId() + "'", null);// 删除关信息
       transportOrderDao.executeHql(" Delete FROM JC_ORDER_PRODUCT Where order.id = '" + order2.getId() + "'", null);
       OrderRPServiceImpl orderRPService = SpringUtils.getBean(OrderRPServiceImpl.class);//存储收发货放
       for (OrderReceivingParty orderReceivingParty : orderReceivingParties) {
            handyRp(orderReceivingParty); //处理收货方方便利性
            orderReceivingParty.setOrder(order2);
            if(order2.getCostomerType() == 0 && orderReceivingParty.getType() == 0){
                order2.setCostomerName(orderReceivingParty.getName());
            }
            orderRPService.saveBean(orderReceivingParty);
        }
        OrderProductDaoImpl orderProductDao = SpringUtils.getBean(OrderProductDaoImpl.class); //存储货品明细
       double w = 0; //计算重量
       double t = 0; //计算体积
        int n = 0; //计算数量
        double v = 0; //计算货值
        double j = 0; //计算计重
       for (OrderProduct orderProduct : orderProducts) {
           n += orderProduct.getNumber();
            t += orderProduct.getVolume();
            w += orderProduct.getWeight();
           v += orderProduct.getValue();
           j+= orderProduct.getJzWeight();
          orderProduct.setOrder(order2);
          orderProductDao.savaBean(orderProduct);
        }
        order2.setNumber(n);
        order2.setWeight(w);
        order2.setVolume(t);
        order2.setValue(v);
        order2.setJzWeight(j);
        order2.setHxtype(0);//核销状态，0未核销
        double whxje =0.0;
        double agent = 0.0; //代收货款
        transportOrderDao.executeHql(" Delete FROM JC_LEDGER_DETAIL Where order.id = '" + order2.getId() + "'", null);
       //OrderLedgerDaoImpl orderLedgerDao = SpringUtils.getBean(OrderLedgerDaoImpl.class); //存储台账信息
//        LedgerDetailDaoImpl ledgerDetailDao = SpringUtils.getBean(LedgerDetailDaoImpl.class); //存储明细
        for(LedgerDetail ledgerDetail:ledgerDetails){
            double amount = ledgerDetail.getAmount();
            whxje += ledgerDetail.getAmount();
            if(amount != 0){
                //处理代收货款
                FeeType feeType = (FeeType) feeTypeDao.selectBean(ledgerDetail.getFeeType().getId());
                if(feeType.getIsCount() != null){
                    int isCount = feeType.getIsCount();
                    if(isCount ==1){ //如果是代收代付类的
                        agent+=amount;
                    }
                }
                ledgerDetail.setCost(0);
                ledgerDetail.setType(0);
                ledgerDetail.setId(null);
                ledgerDetail.setOrder(order2);
                ledgerDetailDao.savaBean(ledgerDetail);
            }

        }
        order2.setWhxmoney(whxje);
        order2.setAgent(agent);
        return order2;
    }


    /**
     * 处理收发货方存储
     *
     * @param orderReceivingParty
     */
    private void handyRp(OrderReceivingParty orderReceivingParty) {
        String hql = "SELECT COUNT(*) FROM JC_RECEIVINGPARTY where organization.id ='" + SessionUtil.getOrgId() + "' and name='" + orderReceivingParty.getName() + "' and  contactperson = '" + orderReceivingParty.getContactperson() + "' and iphone = '" + orderReceivingParty.getIphone() + "' and address = '" + orderReceivingParty.getAddress() + "' and ltl ='" + orderReceivingParty.getLtl() + "'";
        List<ReceivingParty> receivingParties = receivingPartyDao.executeQueryHql(hql);
        Object obj = receivingParties.get(0);
        Long l = (Long) obj;
        if (l.longValue() == 0) { //如果没有
            if(StringUtils.isNotEmpty(orderReceivingParty.getAddress()) &&
               StringUtils.isNotEmpty(orderReceivingParty.getContactperson()) &&
               StringUtils.isNotEmpty(orderReceivingParty.getLtl()) &&
               StringUtils.isNotEmpty(orderReceivingParty.getName()) &&
               StringUtils.isNotEmpty(orderReceivingParty.getIphone())){
            }
            ReceivingParty receivingParty = new ReceivingParty();
            receivingParty.setOrganization(SessionUtil.getOrg());
            receivingParty.setStatus(1);
            receivingParty.setContactperson(orderReceivingParty.getContactperson());
            receivingParty.setAddress(orderReceivingParty.getAddress());
            receivingParty.setLtl(orderReceivingParty.getLtl());
            receivingParty.setName(orderReceivingParty.getName());
            receivingParty.setIphone(orderReceivingParty.getIphone());
            receivingParty.setDescription(orderReceivingParty.getDetailedAddress());
            receivingParty.setDetailedAddress(orderReceivingParty.getDetailedAddress());
            receivingPartyDao.savaBean(receivingParty);
        }
    }

    /**
     * 确认订单，并生成分段订单，生成分段订单编码
     *
     * @param id
     */
    public void confirmOrder(String id) throws MessageException {
        String[] split = id.split(Symbol.COMMA);
        for (String splitId : split) {
            InOrOutRecord inOrOutRecord =new InOrOutRecord();
            Order order = transportOrderDao.selectBean(splitId);
            OrderTrackUtil.addTrack(order, "您的订单在已被受理,正在等待装车 操作人:" + SessionUtil.getUserName());
            if (order.getStatus() != 1) {
                throw new MessageException("只能确认保存状态下的订单");
            }
            if(order.getCustomer() != null && order.getCostomerType() == 1){
                if (order.getOrganization().getIsOverdueContract() == 0){
                    Timestamp  startTime = order.getCustomer().getStartTime();
                    Timestamp  endTime= order.getCustomer().getEndTime();
                    Timestamp timestamp =new Timestamp(System.currentTimeMillis());
                    boolean b = DateUtils.belongCalendar(timestamp, startTime, endTime);
                    if (b==false) {
                        throw new MessageException("合同超期不能确认");
                    }
                }
            }
            order.setStatus(OrderStatus.CONFIGRM.getValue());// 修改订单状态
            List<OrderReceivingParty> orderReceivingParties = order.getOrderReceivingParties();
            List<OrderProduct> orderProducts = order.getOrderProducts();
//            order.setLedgers(null);
            order.setOrderReceivingParties(null);
            order.setOrderProducts(null);
            Order order1 = transportOrderDao.savaBean(order);
            String orderString = JSONObject.toJSONString(order1, SerializerFeature.DisableCircularReferenceDetect); //序列化
            String RPString = JSONObject.toJSONString(orderReceivingParties, SerializerFeature.DisableCircularReferenceDetect);
            String productString = JSONObject.toJSONString(orderProducts, SerializerFeature.DisableCircularReferenceDetect);
            Led led = JSONObject.parseObject(orderString, Led.class); //反序列化对象
            List<LedReceivingParty> ledReceivingParties = JSONObject.parseArray(RPString, LedReceivingParty.class);//反序列化
            List<LedProduct> ledProducts = JSONObject.parseArray(productString, LedProduct.class);
            LedDaoImpl ledDao = SpringUtils.getBean(LedDaoImpl.class);//获取Dao层
            LedRPDaoImpl ledRPDao = SpringUtils.getBean(LedRPDaoImpl.class);
            LedProductDaoImpl ledProductDao = SpringUtils.getBean(LedProductDaoImpl.class);

            //处理单头
            led.setOrder(order1);
            led.setCode(led.getCode() + CODE_ONE);
            led.setFormOrganization(order.getOrganization());
            led.setStatus(2);


            Led saveLed = ledDao.savaBean(led);//存储分段单据
            //修改预录单
//            if(order1.getPresco() != null){
//               updatePresco(order1);
//            }
            for (LedReceivingParty ledReceivingParty : ledReceivingParties) {
                ledReceivingParty.setLed(saveLed); //设置关联关系
                ledRPDao.savaBean(ledReceivingParty);
            }
            for (LedProduct ledProduct : ledProducts) {
                ledProduct.setLed(saveLed); //设置关联关系
                ledProductDao.savaBean(ledProduct);
                inOrOutRecord.setNumber(ledProduct.getNumber());
                inOrOutRecord.setVolume(ledProduct.getVolume());
                inOrOutRecord.setWeight(ledProduct.getWeight());
//                    Presco presco = saveLed.getPresco();
//                    if(presco != null){
//                        double w = 0; //计算重量
//                        double t = 0; //计算体积
//                        int n = 0; //计算数量
//                        double v = 0; //计算货值
//                        double j = 0; //计算计重
//                    List<PrescoProduct> prescoProducts = presco.getPrescoProducts();
//                    for (PrescoProduct prescoProduct:prescoProducts) {
//                        prescoProduct.setWeight(ledProduct.getWeight());
//                        prescoProduct.setJzWeight(ledProduct.getJzWeight());
//                        prescoProduct.setVolume(ledProduct.getVolume());
//                        prescoProduct.setValue(ledProduct.getValue());
//                        prescoProduct.setNumber(ledProduct.getNumber());
//                        prescoProduct.setUnit(ledProduct.getUnit());
//                        prescoProduct.setPresco(presco);
//                        prescoProduct.setName(ledProduct.getName());
//                        w +=ledProduct.getWeight();
//                        t +=ledProduct.getVolume();
//                        n +=ledProduct.getNumber();
//                        v +=ledProduct.getValue();
//                        j += ledProduct.getJzWeight();
//                    }//  prescoProductDao.savaBean(prescoProduct);
//                }
            }


            inOrOutRecord.setLed(saveLed);
            // inOrOutRecord.setOrder(order);
             inOrOutRecord.setOrganization(order1.getOrganization());
             Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
             inOrOutRecord.setTime(timestamp);
             inOrOutRecord.setType(0);
             inOrOutRecord.setZoneStoreroom(order.getZoneStoreroom());
             inOrOutRecordDao.savaBean(inOrOutRecord);

             //处理回单
            if(order.getIsBack() ==1){
            OrderBack orderBack = new OrderBack();
            orderBack.setOrder(order);
            orderBack.setSingNumber(0);
            orderBack.setBackNumber(order.getBackNumber());
            orderBackDao.savaBean(orderBack);
            }
            List<LedgerDetail> ledgerDetails = order.getLedgerDetails();
            Double amount =0.0;
            Double input = 0.0;
            for (LedgerDetail ledgerDetail:ledgerDetails) {
                 amount += ledgerDetail.getAmount();
                 input  += ledgerDetail.getInput();
            }
            //订单确认后，向核销表插入一条数据
            VerificationZb verificationZb = new VerificationZb();
               verificationZb.setOrganization(SessionUtil.getOrg());
                verificationZb.setOrder(order1);
                verificationZb.setStatus(0);
                verificationZb.setHxStatus(0);
                verificationZb.setWhxMoney(amount);
                verificationZb.setzMoney(amount);
                verificationZb.setzInput(input);
                verificationZb.setHxCount(0);
                verificationZb.setType(0);
                verificationZb.setHxMoney(0.0);
                verificationZb.setTzSource(0);
                verificationDao.savaBean(verificationZb);
        }
    }

//    public void updatePresco(Order order1) {
//        Presco presco = order1.getPresco();
//        prescoProductDao.executeHql("DELETE FROM JC_PRESCO_PRODUCT where presco.id= '"+ presco.getId()+"'",null);
//        List<OrderProduct> orderProducts = order1.getOrderProducts();
//        String parse = JSONObject.toJSONString(orderProducts);
//        List<PrescoProduct> prescoProducts = JSONObject.parseArray(parse, PrescoProduct.class);
//        int number = 0;
//        double weight =0;
//        double value =0;
//        double volume =0;
//        double jzWeight= 0;
//        for (PrescoProduct prescoProduct:prescoProducts) {
//            prescoProduct.setId(null);
//            prescoProduct.setPresco(presco);
//            number += prescoProduct.getNumber();
//            weight += prescoProduct.getWeight();
//            value += prescoProduct.getValue();
//            volume += prescoProduct.getVolume();
//            jzWeight += prescoProduct.getJzWeight();
//            prescoProductDao.savaBean(parse);
//        }
//        presco.setNumber(number);
//        presco.setWeight(weight);
//        presco.setValue(value);
//        presco.setVolume(volume);
//        presco.setJzWeight(jzWeight);
//    }

    public Order saveAbnormalOrder(Order updateOrder, Timestamp time, String text) {
        Order order1 = transportOrderDao.selectBean(updateOrder.getId());//查出原始数据
        List<Led> leds = order1.getLeds();
        Abnormal abnormal = new Abnormal();
        abnormal.setOrganization(SessionUtil.getOrg());
        List<AbnormalDetail> abnormalList = abnormalService.getAbnormalList(order1, updateOrder);
        //处理合同是否超期
        if (order1.getCustomer() == null) {
            order1.setCostomerIsExceed(Entity.UNACTIVE);
        } else {
            CustomerDaoimpl bean = SpringUtils.getBean(CustomerDaoimpl.class);
            Customer customer = bean.selectBean(order1.getCustomer().getId());
            Timestamp startTime = customer.getStartTime();
            Timestamp endTime = customer.getEndTime();
            Timestamp timestamp = order1.getTime();
            boolean b = DateUtils.belongCalendar(timestamp, startTime, endTime);
            if (b) {
                order1.setCostomerIsExceed(Entity.UNACTIVE);
            } else {
                order1.setCostomerIsExceed(Entity.ACTIVE);
            }
        }
        if(order1.getIsBack() == 1){
             String hql = "select id from JC_ORDER_BACK where jc_order_id = '"+order1.getId()+"'";
            List list = orderBackDao.executeQueryHql(hql);
             if(list.size()<1){
                 OrderBack orderBack = new OrderBack();
                 orderBack.setOrder(order1);
                 orderBack.setSingNumber(0);
                 orderBack.setBackNumber(order1.getBackNumber());
                 orderBackDao.savaBean(orderBack);
             }
        }        List<VerificationZb> verifications = order1.getVerifications();//核销总表
        List<OrderReceivingParty> orderReceivingParties = order1.getOrderReceivingParties();//获取收发货方
        List<OrderReceivingParty> orderReceivingParties1 = updateOrder.getOrderReceivingParties();
        for (OrderReceivingParty orderReceivingParty : orderReceivingParties) {
            for (OrderReceivingParty orderReceivingParty1 : orderReceivingParties1) {
                if (orderReceivingParty1.getId().equals(orderReceivingParty.getId())) {
                    String type = null;
                    if (orderReceivingParty.getType() == 0) { //如果是发货方
                        type = "发货方";
                    } else {
                        type = "收货方";
                    }
                    if (!orderReceivingParty.getName().equals(orderReceivingParty1.getName())) {
                        abnormalList.add(getAbnormalDetail(type + "客户名称", orderReceivingParty.getName(), "name", orderReceivingParty1.getName()));
                        orderReceivingParty.setName(orderReceivingParty1.getName());
                    }
                    if (!orderReceivingParty.getContactperson().equals(orderReceivingParty1.getContactperson())) {
                        abnormalList.add(getAbnormalDetail(type + "联系人", orderReceivingParty.getContactperson(), "contactperson", orderReceivingParty1.getContactperson()));
                        orderReceivingParty.setContactperson(orderReceivingParty1.getContactperson());
                    }
                    if (!orderReceivingParty.getIphone().equals(orderReceivingParty1.getIphone())) {
                        abnormalList.add(getAbnormalDetail(type + "电话", orderReceivingParty.getIphone(), "iphone", orderReceivingParty1.getIphone()));
                        orderReceivingParty.setIphone(orderReceivingParty1.getIphone());
                    }
                    if (!orderReceivingParty.getAddress().equals(orderReceivingParty1.getAddress())) {
                        abnormalList.add(getAbnormalDetail(type + "地址", orderReceivingParty.getAddress(), "address", orderReceivingParty1.getAddress()));
                        orderReceivingParty.setAddress(orderReceivingParty1.getAddress());
                    }
                    if (!orderReceivingParty.getLtl().equals(orderReceivingParty1.getLtl())) {
                        abnormalList.add(getAbnormalDetail(type + "经纬度", orderReceivingParty.getLtl(), "ltl", orderReceivingParty1.getLtl()));
                        orderReceivingParty.setLtl(orderReceivingParty1.getLtl());
                    }
                }
            }
        }
        List<OrderProduct> orderProducts = order1.getOrderProducts();
        List<OrderProduct> orderProducts1 = updateOrder.getOrderProducts();
        if(orderProducts.size() != orderProducts1.size()){
            throw new MessageException("不能修改货品明细数量");
        }
        for (OrderProduct orderProduct : orderProducts) {
            for (OrderProduct orderProduct1 : orderProducts1) {
                if(orderProduct1.getId().equals(orderProduct.getId())) {
                    if (!orderProduct.getName().equals(orderProduct1.getName())) {
                        abnormalList.add(getAbnormalDetail("货品名称", orderProduct.getName(), "name", orderProduct1.getName()));
                        orderProduct.setName(orderProduct1.getName());
                    }
                    if (!orderProduct.getDescription().equals(orderProduct1.getDescription())) {
                        abnormalList.add(getAbnormalDetail("备注", orderProduct.getDescription(), "name", orderProduct1.getDescription()));
                        orderProduct.setDescription(orderProduct1.getDescription());
                    }
                    if (!orderProduct.getUnit().equals(orderProduct1.getUnit())) {
                        abnormalList.add(getAbnormalDetail("货品-" + orderProduct.getName() + "-单位", orderProduct.getUnit(), "unit", orderProduct1.getUnit()));
                        orderProduct.setUnit(orderProduct1.getUnit());
                    }
                    double weight = orderProduct.getWeight();
                    double weight1 = orderProduct1.getWeight();
                    if (weight != weight1) {
                        abnormalList.add(getAbnormalDetail("货品-" + orderProduct.getName() + "-重量", String.valueOf(orderProduct.getWeight()), "weight", String.valueOf(orderProduct1.getWeight())));
                        orderProduct.setWeight(orderProduct1.getWeight());
                    }
                    int number = orderProduct.getNumber();
                    int number1 = orderProduct1.getNumber();
                    if (number != number1) {
                        abnormalList.add(getAbnormalDetail("货品-" + orderProduct.getName() + "-数量", String.valueOf(orderProduct.getNumber()), "number", String.valueOf(orderProduct1.getNumber())));
                        orderProduct.setNumber(orderProduct1.getNumber());
                    }
                    double volume1 = orderProduct.getVolume();
                    double volume = orderProduct1.getVolume();
                    if (volume1 != volume) {
                        abnormalList.add(getAbnormalDetail("货品-" + orderProduct.getName() + "-体积", String.valueOf(orderProduct.getVolume()), "volume", String.valueOf(orderProduct1.getVolume())));
                        orderProduct.setVolume(orderProduct1.getVolume());
                    }
                    double value = orderProduct.getValue();
                    double value1 = orderProduct1.getValue();
                    if (value != value1) {
                        abnormalList.add(getAbnormalDetail("货品-" + orderProduct.getName() + "-货值", String.valueOf(orderProduct.getValue()), "weight", String.valueOf(orderProduct1.getValue())));
                        orderProduct.setValue(orderProduct1.getValue());
                    }
                }

            }
        }
        //费用明细明细
        List<LedgerDetail> ledgerDetails = updateOrder.getLedgerDetails();

        for (LedgerDetail ledgerDetail:ledgerDetails) {
            double amount = ledgerDetail.getAmount()==null?0:ledgerDetail.getAmount();
            double arrivePay = ledgerDetail.getArrivePay()==null?0:ledgerDetail.getArrivePay();
            double nowPay = ledgerDetail.getNowPay()==null?0:ledgerDetail.getNowPay();
            double monthPay = ledgerDetail.getMonthPay()==null?0:ledgerDetail.getMonthPay();
            double backPay = ledgerDetail.getBackPay()==null?0:ledgerDetail.getBackPay();
            /*if(amount <(arrivePay+nowPay+monthPay+backPay) ){
                throw new MessageException("现付，到付，回付，还有一个什么付加一起不能超过费用金额");
            }*/
            if(StringUtils.isEmpty(ledgerDetail.getId())){
                FeeType feeType = (FeeType) feeTypeDao.selectBean(ledgerDetail.getFeeType().getId());
                abnormalList.add(getAbnormalDetail("调整费用-" + feeType.getName(), "0", "调整费用-" + feeType.getName()+"  税率:"+ledgerDetail.getTaxRate(), ledgerDetail.getAmount().toString()));
                ledgerDetail.setOrder(order1);
                ledgerDetail.setCost(1);
                ledgerDetail.setType(0);
                VerificationZb verificationZb = verifications.get(0);
                      verificationZb.setWhxMoney(verificationZb.getWhxMoney() + ledgerDetail.getAmount());
                      verificationZb.setzMoney(verificationZb.getzMoney() + ledgerDetail.getAmount());
                if(verificationZb.getStatus() == 1){//核销完毕
                    verificationZb.setStatus(2);//变成部分核销
                }
                ledgerDetailDao.savaBean(ledgerDetail);
            }
        }


        List<OrderProduct> newOrderP = order1.getOrderProducts();


        double w = 0; //计算重量
        double t = 0; //计算体积
        int n = 0; //计算数量
        double v = 0; //计算货值
        for (OrderProduct orderProduct : newOrderP) {
            n += orderProduct.getNumber();
            t += orderProduct.getVolume();
            w += orderProduct.getWeight();
            v += orderProduct.getValue();
            orderProduct.setOrder(order1);
        }
        order1.setNumber(n);
        order1.setWeight(w);
        order1.setVolume(t);
        order1.setValue(v);
        order1.setIsAbnormal(1);
        //OrderLedgerDaoImpl orderLedgerDao = SpringUtils.getBean(OrderLedgerDaoImpl.class); //存储台账信息
        abnormal.setNumber(abnormalList.size());
        abnormal.setDescription(text);
        abnormal.setOrder(order1);
        abnormal.setTime(DateUtils.getTimestamp());
        abnormal.setType(0);
        Abnormal abnormal1 = (Abnormal) abnormalDao.savaBean(abnormal);
        if(abnormalList.size() == 0){
            throw  new MessageException("没有异常，不用修改");
        }
        for (AbnormalDetail abnormalDetail:abnormalList) {
            abnormalDetail.setAbnormal(abnormal1);
            abnormalDetailDao.savaBean(abnormalDetail);
        }
        return order1;
    }



    public void copyOrder(String id) throws MessageException {
        //获取原始数据
        Order order = transportOrderDao.selectBean(id);
        //OrderTrackUtil.addTrack(order,"您的订单在已被受理，正在等待配载…… 操作人:"+ SessionUtil.getUserName());


        //去掉ID
        Order order1 = new Order();
        BeanUtils.copyProperties(order, order1);
        order1.setId(null);
        //订单号
        order1.setCode(getOrderCode());
        //订单日期
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        order1.setTime(timestamp);
        //创建日期
        order1.setCreate_Time(timestamp);
        //创建人
        order1.setCreate_Name(SessionUtil.getUserName());
        //修改事件修改人为空
        order1.setModify_Time(null);
        order1.setModify_Name(null);
        order1.setStatus(1);
        //保存订单
        Order newOrder = (Order) transportOrderDao.savaBean(order1);

        //保存收发货方
        List<OrderReceivingParty> orderReceivingParties = order.getOrderReceivingParties();
        //获取收发货方service
        OrderRPServiceImpl orderRPService = SpringUtils.getBean(OrderRPServiceImpl.class);
        for (OrderReceivingParty orderReceivingParty : orderReceivingParties) {
            OrderReceivingParty orderReceivingParty1 = new OrderReceivingParty();
            BeanUtils.copyProperties(orderReceivingParty, orderReceivingParty1);
            orderReceivingParty1.setId(null);
            orderReceivingParty1.setCreate_Time(timestamp);
            orderReceivingParty1.setCreate_Name(SessionUtil.getUserName());
            //handyRp(orderReceivingParty1);
            orderReceivingParty1.setOrder(newOrder);
            orderRPService.saveBean(orderReceivingParty1);
        }

        //保存货品明细
        List<OrderProduct> orderProducts = order.getOrderProducts();
        //货品明细Dao
        OrderProductDaoImpl orderProductDao = SpringUtils.getBean(OrderProductDaoImpl.class);
        for (OrderProduct orderProduct : orderProducts) {
            OrderProduct orderProduct1 = new OrderProduct();
            BeanUtils.copyProperties(orderProduct, orderProduct1);
            orderProduct1.setId(null);
            orderProduct1.setCreate_Time(timestamp);
            orderProduct1.setCreate_Name(SessionUtil.getUserName());
            //handyRp(orderReceivingParty1);
            orderProduct1.setOrder(newOrder);
            orderProductDao.savaBean(orderProduct1);
        }

        //获取台账
        List<LedgerDetail> ledgerDetails = order.getLedgerDetails();

        //台账明细Dao
        LedgerDetailDaoImpl ledgerDetailDao = SpringUtils.getBean(LedgerDetailDaoImpl.class);

        for (LedgerDetail ledgerDetail : ledgerDetails) {
            LedgerDetail ledgerDetail1 = new LedgerDetail();
            BeanUtils.copyProperties(ledgerDetail, ledgerDetail1);
            ledgerDetail1.setId(null);
            ledgerDetail1.setCreate_Time(timestamp);
            ledgerDetail1.setCreate_Name(SessionUtil.getUserName());
            ledgerDetail1.setOrder(newOrder);
            ledgerDetailDao.savaBean(ledgerDetail1);
        }
    }

    /**
     * 分段訂單 批量生成一条运单
     */
    public void peizaiShip(String[] ids, String templateId) {
        //反射一堆dao
        TemplateDaoImpl templateDao = SpringUtils.getBean(TemplateDaoImpl.class);
        LedDaoImpl ledDao = SpringUtils.getBean(LedDaoImpl.class);
        ShipmentDaoImpl shipmentDao = SpringUtils.getBean(ShipmentDaoImpl.class);
        LedgerDetailDao ledgerDetailDao = SpringUtils.getBean(LedgerDetailDaoImpl.class);
        ShipmentServiceImpl shipmentService = SpringUtils.getBean(ShipmentServiceImpl.class);
        BerthStandDaoImpl berthStandDao = SpringUtils.getBean(BerthStandDaoImpl.class);
        //获取运单模板
        ShipmentTemplate st = templateDao.selectBean(templateId);
        //新建运单
        Shipment shipment = new Shipment();
        //绑定组织机构
        shipment.setOrganization(SessionUtil.getOrg());
        //设置运单号
        shipment.setCode(super.getShipmentCode());
        //当前日期
        Date date = new Date();
        Timestamp today = new Timestamp(date.getTime());
        //设置运单日期
        shipment.setTime(today);
        //运输协议号
        shipment.setTan(st.getTan());
        //承运商
        shipment.setCarrier(st.getCarrier());
        //承运商类型
        shipment.setCarrierType(st.getCarrierType());
        //付款方式‘
        shipment.setFeeType(st.getFeeType());
        //运作模式
        shipment.setOperationPattern(st.getOperationPattern());
        //车型
        shipment.setVehicle(st.getVehicle());
        //运输方式
        shipment.setShipmentMethod(st.getShipmentMethod());
        //车头
        shipment.setVehicleHead(st.getVehicleHead());
        shipment.setLiense(st.getLiense());
        //司机
        shipment.setDriver(st.getDriver());
        shipment.setOutDriver(st.getOutDriver());
        shipment.setOutIphpne(st.getOutIphpne());
        //起运站
        shipment.setFromOrganization(st.getFromOrganization());
        //目的站
        shipment.setToOrganization(st.getToOrganization());
        //经由站组织机构集合
        shipment.setOrgIds(st.getOrgIds());
        //备注
        shipment.setDescription(st.getDescription());
        //保存
        Shipment shipment1 = shipmentDao.savaBean(shipment);

        //经由站
        String orgIds = st.getOrgIds();
        if(StringUtils.isNotEmpty(orgIds)){
            String[] split = orgIds.split(Symbol.COMMA);
            shipment1.setBerthStand(null);
            for (int i = 0; i <split.length ; i++) {
                String s = split[i];
                Organization org = (Organization) orgDao.selectBean(s);
                int type = org.getType();
                if(type ==3){
                    throw  new MessageException("途径站不能是落地派机构");
                }
                BerthStand berthStand = new BerthStand();
                berthStand.setOrganization(org);
                berthStand.setShipment(shipment1);
                berthStand.setOrderBy(i);
                berthStand.setStatus(0);
                berthStandDao.savaBean(berthStand);
            }
        }
        //获取运单模板的台账
        List<LedgerDetail> templateLedgers= st.getTemplateLedgers();
        for (LedgerDetail templateLedgerDetail : templateLedgers) {
           String ss=JSONObject.toJSONString(templateLedgerDetail);
           LedgerDetail ledgerDetail=JSONObject.parseObject(ss,LedgerDetail.class);
           ledgerDetail.setId(null);
           ledgerDetail.setShipmentTemplate(null);
           ledgerDetail.setShipment(shipment1);
           ledgerDetailDao.savaBean(ledgerDetail);
        }

        // 分段订单 配载
        List<Led> list = new ArrayList<Led>();
        for(String  id :ids){
            //查询分段订单
            Led led = ledDao.selectBean(id);
            list.add(led);
        }

        shipmentService.addLed(JSONArray.toJSONString(list), shipment1.getId());

    }

    public void upload(String id, File f) {

    }

    public JSONArray queryRP(String name) {
        name = name.replace("'","");
        JSONArray jsonArray = new JSONArray();
        StringBuffer sb = new StringBuffer("SELECT a.id as id,a.name as 单位名称,a.contactperson as 负责人,a.iphone as 联系电话,a.address as 地址,a.ltl as 经纬度,a.detailedAddress as 详细地址 from JC_RECEIVINGPARTY a  where 1=1 and a.organization = '"+SessionUtil.getOrgId()+"'");
        if (StringUtils.isNotEmpty(name)) {
            sb.append(" and (");
            sb.append(" a.name like  '%" + name + "%'");
            sb.append(" or a.contactperson like  '%" + name + "%'");
            sb.append(" or a.iphone like  '%" + name + "%'");
            sb.append(" or a.address like  '%" + name + "%'");
            sb.append(" )");
        }

        List list = orderRPDao.selectByPage(sb.toString(), 0, 9);
        if(list.size()>0){
            for (Object object : list) {
                Object[] obs = (Object[]) object;
                LinkedHashMap jsonObject = new LinkedHashMap();
                jsonObject.put("id", obs[0]); //城市ID
                jsonObject.put("单位名称", obs[1]);
                jsonObject.put("负责人", obs[2]);
                jsonObject.put("联系电话", obs[3]);
                jsonObject.put("地址", obs[4]);
                jsonObject.put("经纬度", obs[5]);
                jsonObject.put("详细地址", obs[6]);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    public Long selectCont() {
        String where = " and  organization='" + SessionUtil.getOrgId() + "'";
        Long bac = transportOrderDao.selectCount(null, where, null);
        return bac;
    }

    /**
     * 订单导入
     * @param
     * @return sundezeng
     */
    public List<Map<String, Object>> orderUploadNew(XSSFWorkbook sdsds) {
        // 返回导入提示信息、统计信息（总数、成功数、失败数）
        List<Map<String, Object>> list = new ArrayList();
        List<Map<String, String>> messages = new ArrayList<Map<String, String>>();
        Map<String, Object> map = new HashMap(); // 存放统计的数量（总数、成功、失败）
        int sum = 0; ///总条数
        int success = 0;  ///成功数（总数-失败数）
        int fail = 0;  ///失败
        XSSFSheet sheetAt = sdsds.getSheetAt(0);
        boolean fl = false;
        // 获取总行数
        sum = sheetAt.getPhysicalNumberOfRows()-1;
            XSSFRow row = sheetAt.getRow(0);//第一行对象
//            int lastRowNum = sheetAt.getLastRowNum(); //行数据
//            int lastCellNum = row.getLastCellNum(); //列数据
            if (sum >= 1) {
                for (int i = 1; i <= sum; i++) {
                    Order order = new Order();
                    OrderProduct orderProduct = new OrderProduct();
                    XSSFRow row1 = sheetAt.getRow(i);
                    XSSFCell fhdh = row1.getCell(0);
                    XSSFCell kdrq = row1.getCell(1);
                    XSSFCell khlx = row1.getCell(2);
                    XSSFCell khmc = row1.getCell(3);
                    XSSFCell khdh = row1.getCell(4);
                    XSSFCell jffs = row1.getCell(5);
                    XSSFCell dk = row1.getCell(6);
                    XSSFCell cfyd = row1.getCell(7);
                    XSSFCell mdwd = row1.getCell(8);
                    XSSFCell mdyd = row1.getCell(9);
                    XSSFCell jsfs = row1.getCell(10);
                    XSSFCell xsfzr = row1.getCell(11);
                    XSSFCell ysxz = row1.getCell(12);
                    XSSFCell sfhd = row1.getCell(13);
                    XSSFCell hdfs = row1.getCell(14);
                    XSSFCell hpmc = row1.getCell(15);
                    XSSFCell hpbz = row1.getCell(16);
                    XSSFCell hpsl = row1.getCell(17);
                    XSSFCell hpzl = row1.getCell(18);
                    XSSFCell hptj = row1.getCell(19);
                    order.setCode(getOrderCode());
                    order.setOrganization(SessionUtil.getOrg());
                    order.setIsTake(0);
                    order.setOrderMileage(0.00);
                    //发货单号
                    String s00 = fhdh.toString();
                    String fhss= null;
                    boolean contains = s00.contains(".");
                    if(contains){
                        fhss = s00.substring(0, s00.indexOf("."));
                        String hql ="select a.id from jc_order a where a.relatebill1 ='" +fhss+ "'";
                        List orders = transportOrderDao.executeQueryHql(hql);
                        if(orders.size()>=1){
                            String message = "您好，第[ "+ i +" ]行，第[ 1 ]列客户单号重复，请勿重复录入! ";  // 客户类型与客户名称判断
                            Map<String,String> mapfhdh = new HashMap<String, String>();
                            mapfhdh.put("message", message);
                            messages.add(mapfhdh);
                            fail++;  // 失败一条
                        }
                    }else{
                        fhss = s00;
                        String hql ="select a from JC_ORDER a where a.relatebill1 ='" +fhss+ "'";
                        List orders = transportOrderDao.executeQueryHql(hql);
                        if(orders.size()>=1){
                            String message = "您好，第[ "+ i +" ]行，第[ 1 ]列客户单号重复，请勿重复录入! ";  // 客户类型与客户名称判断
                            Map<String,String> mapfhdh = new HashMap<String, String>();
                            mapfhdh.put("message", message);
                            messages.add(mapfhdh);
                            fail++;  // 失败一条
                        }
                    }
                    order.setRelatebill1(fhss);
                    //客户单号
                    String khdh1 =  khdh.toString();
                    String khdh2 = null;
                    boolean contains1 = khdh1.contains(".");
                    if(contains1){
                        khdh2 = khdh1.substring(0, khdh1.indexOf("."));
                    }else{
                        khdh2 = khdh1;
                    }
                    order.setRelatebill2(khdh.toString());
                    //开单日期
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dateCellValue = kdrq.getDateCellValue();
                    long time = dateCellValue.getTime();
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    String format = sdf.format(time);
                    ts = Timestamp.valueOf(format);
                    order.setTime(ts);

                    //客户类型
                    if (khlx.toString().equals("合同")) {//0：零散 1：合同
                        order.setCostomerType(1);
                        String hql = "select id from JC_CUSTOMER where name  like '" + khmc.toString() + "'";
                        List list1 = customerDao.executeQueryHql(hql);
                        if (list1.size() > 0) {
                            Object o = list1.get(0);
                            String s = o.toString();
                            Customer customer = (Customer) customerDao.selectBean(s);
                            order.setCustomer(customer);
                        } else {
                            String message = "您好，第[ "+ i +" ]行，第[ 4 ]列客户名称与客户类型不匹配，请重新录入! ";  // 客户类型与客户名称判断
                            Map<String,String> mapKh = new HashMap<String, String>();
                            mapKh.put("message", message);
                            messages.add(mapKh);
                            fail++;  // 失败一条
                        }
                    } else {
                        order.setCostomerType(0);
                        order.setCostomerName(khmc.toString());
                    }
                    //交付方式
                    if (jffs.toString().equals("自提")) {//0：自提  1：送货
                        order.setHandoverType(0);
                    } else {
                        order.setHandoverType(1);
                    }
                    //在库位置(先查询是否有大库的存在)
                    String dkhql = "select id from JC_ZONE_STOREROOM where name = '" + dk.toString() + "' and JC_ORGANIZATION_ID='" + SessionUtil.getOrgId() + "'";
                    List list1 = zoneStoreroomDao.executeQueryHql(dkhql);
                    if (list1.size()>0) {
                        ZoneStoreroom zoneStoreroom = (ZoneStoreroom) zoneStoreroomDao.selectBean((Serializable) list1.get(0));
                        order.setZoneStoreroom(zoneStoreroom);
                    } else {
                        String message = "您好，第[ "+ i +" ]行，第[ 7 ]列本平台库存区域为空，请添加库存区域! ";  // 客户类型与客户名称判断
                        Map<String,String> mapKc = new HashMap<String, String>();
                        mapKc.put("message", message);
                        messages.add(mapKc);
                        fail++;  // 失败一条
                    }
                    //出发运点
                    String cfydhql = "select a.id from JC_SERVER_ZONE a left join a.zone b left join a.organization c  where b.name ='" + cfyd.toString() + "' and c.id= '" + SessionUtil.getOrgId() + "'";
                    List list2 = serverZoneDao.executeQueryHql(cfydhql);
                    if (list2.size()>0){
                        ServerZone serverZone = (ServerZone) serverZoneDao.selectBean((Serializable) list2.get(0));
                        order.setStartZone(serverZone);
                    } else {
                        String message = "您好，第[ "+ i +" ]行，第[ 8 ]列没有查找到该出发运点，请添加出发运点! ";
                        Map<String,String> mapCfyd = new HashMap<String, String>();
                        mapCfyd.put("message", message);
                        messages.add(mapCfyd);
                        fail++;  // 失败一条
                    }
                    //目的网点
                    String mdwdhql = "select id  from JC_SYS_ORGANIZATION where name = '" + mdwd.toString() + "'";
                    List list3 = orgDao.executeQueryHql(mdwdhql);
                    if (list3.size()>0) {
                        Organization organization = (Organization) orgDao.selectBean((Serializable) list3.get(0));
                        order.setToOrganization(organization);
                    } else {
                        String message = "您好，第[ "+ i +" ]行，第[ 9 ]列没有查找到该目的网点，请添加目的网点! ";
                        Map<String,String> mapMfwd = new HashMap<String, String>();
                        mapMfwd.put("message", message);
                        messages.add(mapMfwd);
                        fail++;  // 失败一条
                    }
                    //目的运点
                    String mdydhql = "select a.id from JC_SERVER_ZONE a left join a.zone b left join a.organization c  where b.name ='" + mdyd.toString() + "' and c.id= '" + SessionUtil.getOrgId() + "'";
                       List list4 = serverZoneDao.executeQueryHql(mdydhql);
                    if(list4.size()>0){
                        ServerZone serverZone = (ServerZone) serverZoneDao.selectBean((Serializable) list4.get(0));
                        order.setEndZone(serverZone);
                    }else{
                        String message = "您好，第[ "+ i +" ]行，第[ 10 ]列没有查找到该目的运点，请添加目的运点! ";
                        Map<String,String> mapMfyd = new HashMap<String, String>();
                        mapMfyd.put("message", message);
                        messages.add(mapMfyd);
                        fail++;  // 失败一条
                    }
                    //结算方式
                    if (jsfs.toString().equals("月结")) {//0：月结 1：现结 2：到付 3：回付
                        order.setFeeType(0);
                    }
                    if (jsfs.toString().equals("现结")) {
                        order.setFeeType(1);
                    }
                    if (jsfs.toString().equals("到付")) {
                        order.setFeeType(2);
                    }
                    if (jsfs.toString().equals("回付")) {
                        order.setFeeType(3);
                    }
                    //销售负责人
                    order.setSalePersion(xsfzr.toString());
                    //运输性质
                    if (ysxz.toString().equals("普通")) {//0：普通 1：加急
                        order.setTransportPro(0);
                    }
                    if (ysxz.toString().equals("加急")) {
                        order.setTransportPro(1);
                    }
                    //是否回单  0：否 1：是
                    if (sfhd.toString().equals("否")) {
                        order.setIsBack(0);
                    }
                    if (sfhd.toString().equals("是")) {
                        order.setIsBack(1);
                    }
                    //回单份数
                    String s = hdfs.toString();
                    String intNumber = s.substring(0, s.indexOf("."));
                    Integer integer = Integer.valueOf(intNumber);
                    order.setBackNumber(integer);
                    order.setCostomerIsExceed(0);
                    //货品处理 先判断之前有没有错误，如果有错误 这不创建订单
                    if(fail<=0){
                        Order order1 = transportOrderDao.savaBean(order);
                        //货品名称
                        orderProduct.setName(hpmc.toString());
                        //货品包装
                        orderProduct.setUnit(hpbz.toString());
                        //货品数量
                        String s1 = hpsl.toString();
                        String number1 = s1.substring(0, s1.indexOf("."));
                        Integer numer = Integer.valueOf(number1);
                        orderProduct.setNumber(numer);
                        //货品重量
                        String s3 = hpzl.toString();
                       // String weig1 = s3.substring(0, s3.indexOf("."));
                        Double zl = Double.valueOf(s3);
                        orderProduct.setWeight(zl);
                        //货品体积
                        String s2 = hptj.toString();
                       // String weig = s2.substring(0, s2.indexOf("."));
                        Double tj = Double.valueOf(s2);
                        orderProduct.setVolume(tj);
                        //货品计重
                        Double jz = 0.00;
                        if (zl > tj / 3) {
                            jz = zl;
                        } else {
                            jz = tj / 3;
                        }
                        orderProduct.setJzWeight(jz);
                        orderProduct.setOrder(order1);
                        orderProduct.setValue(0.00);
                        orderProductDao.savaBean(orderProduct);
                        //发货方处理
                        Integer costomerType = order1.getCostomerType();
                        Integer ss = 1;
                        if (ss.equals(costomerType)) {//0：零散 1：合同
                            Customer customer = order1.getCustomer();
                            OrderReceivingParty orderReceivingParty = new OrderReceivingParty();
                            orderReceivingParty.setName(customer.getName());
                            orderReceivingParty.setContactperson(customer.getContactperson());
                            orderReceivingParty.setType(0);
                            orderReceivingParty.setOrder(order1);
                            orderReceivingParty.setLtl(customer.getLtl());
                            orderReceivingParty.setAddress(customer.getAddress());
                            orderReceivingParty.setIphone(customer.getIphone());
                            orderReceivingParty.setDetailedAddress(customer.getDetailedAddress());
                            orderReceivingParty.setOrderBy(1);
                            neworderRPDao.savaBean(orderReceivingParty);
                        }
                    }
                }
            }
         if (fail >= sum) {  // 因为不是层级判断，多个字段不符合要求时，避免失败数无限增大
            fail = sum;
        }
        map.put("sum", String.valueOf(sum));
        map.put("success", String.valueOf(sum-fail));
        map.put("fail", String.valueOf(fail));
        map.put("messages", messages);
        list.add(map);
        return list;
    }

    /**
     * 同步TMS
     * sundezeng
     * @return
     */
    public List<Map<String, Object>> tongbuTMS(String ids) {
        List<Map<String, Object>> list = new ArrayList();
        List<Map<String, String>> messages = new ArrayList<Map<String, String>>();
        TMSDataSource instance = TMSDataSource.getInstance();
        Map<String, Object> mapz = new HashMap(); // 存放统计的数量（总数、成功、失败）
        int sum = 0; ///总条数
        int success = 0;  ///成功数（总数-失败数）
        int fail = 0;  ///失败
        String[] split = ids.split(Symbol.COMMA);
        if(split.length>=1){
             sum = split.length;
        for (String splitId : split) {
            Order order = transportOrderDao.selectBean(splitId);
            String relatebill1 = order.getRelatebill1();//客户订单号1
            String relatebill2 = order.getRelatebill2();//客户订单号1
            Customer customer = order.getCustomer();
            Integer ss_fxmId = customer.getSs_fxmId();//分项目ID
            String ss_fxmName = customer.getSs_fxmName();//分项目名称
            String ss_ptName = customer.getSs_ptName();//平台名称
            Integer ss_ptId = customer.getSs_ptId();//平台ID
            Timestamp time = order.getTime();//下单日期
            Integer shuliang = order.getNumber();//件数
            Double zhongliang = order.getWeight();//重量
            Double tiji = order.getVolume();//体积
            int tuoshu = 0;
            String create_name = order.getCreate_Name();//创建人
            Timestamp create_time = order.getCreate_Time();//创建时间
            //获得发货方信息
            List<OrderReceivingParty> orderReceivingParties = order.getOrderReceivingParties();
            OrderReceivingParty fhfxx = orderReceivingParties.get(0);
            String fhfname = fhfxx.getName();//名称
            String fhflxr = fhfxx.getContactperson();//联系人
            String fhflxdh = fhfxx.getIphone();//联系电话
            String fhfpingYin111 = PinYin.getPinYinHeadChar(fhfname);//汉字变成拼音小写
            // String fhfpingYindx = fhfpingYin111.toUpperCase();//转成大写
            String fhfdz = fhfxx.getDetailedAddress();//地址
            //出发城市
            String cfName =null;
            ServerZone startZone = order.getStartZone();
            Zone cfzone = startZone.getZone();
                 if(cfzone.getLevel().equals("province")){//判断是否是省份
                     if(cfzone.getName().equals("北京市")){
                         cfName="北京市";
                     }
                     if(cfzone.getName().equals("上海市")){
                         cfName="上海市";
                     }
                     if(cfzone.getName().equals("天津市")){
                         cfName="天津市";
                     }
                     if(cfzone.getName().equals("重庆市")){
                         cfName="重庆市";
                     }
                 }
                 if(cfzone.getLevel().equals("city")){//如果是市级
                     cfName = cfzone.getName();
                 }
                 if(cfzone.getLevel().equals("district")){//如果是区级
                     Zone zone1 = cfzone.getZone();
                     cfName = zone1.getName();
                 }
                 if(cfzone.getLevel().equals("villages")|| cfzone.getLevel().equals("street")){//乡镇级 street
                     Zone zone1 = cfzone.getZone();//先获得区级
                     Zone zone2 = zone1.getZone();//获得市级
                    cfName = zone2.getName();
                 }
            Integer cfcsid = 0;//出发城市ID
            Integer mdcsid = 0;//目的城市ID
            Integer cfydid = 0;//出发运点ID
            Integer mdydid = 0;//目的运点ID
            Integer tmsshfid = 0;//TMS收货方ID
            Integer tmsfhfid = 0;//TMS发货方ID
            String status = "ACTIVE";
            String cfydName = cfName;
            long currentTimeMillis = System.currentTimeMillis();
            boolean is_shi = cfName.contains("市");
                  if(is_shi){
                      int shi = cfName.indexOf("市");
                      cfydName = cfName.substring(0,shi);
                  }
            boolean is_qu = cfName.contains("区");
                  if(is_qu){
                      int shi = cfName.indexOf("区");
                      cfydName = cfName.substring(0,shi);
                  }
            boolean is_xian = cfName.contains("县");
                  if(is_xian){
                      int shi = cfName.indexOf("县");
                      cfydName = cfName.substring(0,shi);
                  }
            boolean is_jie = cfName.contains("街");
                  if(is_jie){
                      int shi = cfName.indexOf("街");
                      cfydName = cfName.substring(0,shi);
                  }
            boolean is_lu = cfName.contains("路");
                  if(is_lu){
                      int shi = cfName.indexOf("路");
                      cfydName = cfName.substring(0,shi);
                  }
            //出发运点名称
            String cfydCode1 = PinYin.getPinYinHeadChar(cfName);
            String  dqsjhm = String.valueOf(currentTimeMillis);
            String subs = dqsjhm.substring(4);//截取当时毫秒的后8位
            String cfydCode = subs + cfydCode1;
            String cfsql = "select a.id from TMS_CITY a where a.name = '" + cfName + "' and a.status ='ACTIVE'";
            List<Map> cfmaps = instance.exeQuerySql(cfsql);
            if (cfmaps.size() > 0) {
                Map map = cfmaps.get(0);
                for (Object ss : map.keySet()) {
                    Object o = map.get(ss);
                    String sdfs = o.toString();
                    cfcsid = Integer.parseInt(sdfs);
                }
                //获取出发运点ID
                String cfydsql = "select a.id from TMS_TRANSLOCATION a where a.name = '" + cfydName + "' and a.status ='ACTIVE'";
                List<Map> cfydmaps = instance.exeQuerySql(cfydsql);
                if (cfydmaps.size() > 0) {
                    Map map2 = cfydmaps.get(0);
                    for (Object ss : map2.keySet()) {
                        Object o = map2.get(ss);
                        String s = o.toString();
                        cfydid = Integer.parseInt(s);
                    }
                } else {
                    String insertcfyd="INSERT INTO TMS_TRANSLOCATION(CODE,NAME,TMS_CITY_ID,STATUS,TRANSLOCATION_TYPE_ID,CREATOR,CREATED_TIME)" +
                            "VALUES(?,?,?,?,?,?,?)";
                    Object cfydobj[]={cfydCode,cfydName,cfcsid,"ACTIVE",1,create_name,create_time};
                    boolean b = instance.exeQuerSql(insertcfyd, cfydobj);
                    if(b){
                        String cfydsql1 = "select a.id from TMS_TRANSLOCATION a where a.name = '" + cfydName + "' and a.status ='ACTIVE'";
                        List<Map> cfydmaps1 = instance.exeQuerySql(cfydsql1);
                        if (cfydmaps1.size() > 0) {
                            Map map1 = cfydmaps1.get(0);
                            for (Object ss : map1.keySet()) {
                                Object o = map1.get(ss);
                                String s = o.toString();
                                cfydid = Integer.parseInt(s);
                            }
                        }
                    }
                }
            } else {
                String message = "您好，在TMS系统中没有查到【" + cfName + "】此城市！，请先到TMS系统中添加 ";  // 客户类型与客户名称判断
                Map<String, String> mapfhdh = new HashMap<String, String>();
                mapfhdh.put("message", message);
                messages.add(mapfhdh);
                fail++;  // 失败一条
            }

            //查询发货方信息是否存在，不存在则添加
            String cxtmsshfsj = "select a.id from TMS_RECEIVER a where a.name ='" + fhfname + "' and a.TMS_CITY_ID =" + cfcsid + " and" +
                    " a.CUSTOMER = " + ss_fxmId + " and a.TRANS_LOCATION_ID=" + cfydid + " and a.status ='ACTIVE'";
            List<Map> fhmaps = instance.exeQuerySql(cxtmsshfsj);
            if (fhmaps.size() <= 0) {
                String crfhfsql = "INSERT into TMS_RECEIVER(CODE,NAME,STATUS,TMS_CITY_ID,CUSTOMER,TRANS_LOCATION_ID,ADDRESS,CONTACTNAME,TELEPHONE) " +
                        " VALUES(?,?,?,?,?,?,?,?,?)";
                Object obj[] = {currentTimeMillis, fhfname, status, cfcsid, ss_fxmId, cfydid, fhfdz, fhflxr, fhflxdh};
                boolean b = instance.exeQuerSql(crfhfsql, obj);
                if (b == false) {
                    String message = "您好，插入失败！在该【" + ss_ptName + "】平台下，发货方名称'" + fhfname + "'重复，不能插入";  // 客户类型与客户名称判断
                    Map<String, String> mapfhdh2 = new HashMap<String, String>();
                    mapfhdh2.put("message", message);
                    messages.add(mapfhdh2);
                    fail++;  // 失败一条
                } else {
                    String cxtmsfhfsj1 = "select a.id from TMS_RECEIVER a where a.name ='" + fhfname + "' and a.TMS_CITY_ID =" + cfcsid + " and" +
                            " a.CUSTOMER = " + ss_fxmId + " and a.TRANS_LOCATION_ID=" + cfydid + " and a.status ='ACTIVE'";
                    List<Map> fhmaps1 = instance.exeQuerySql(cxtmsfhfsj1);
                    Map map = fhmaps1.get(0);
                    for (Object ss : map.keySet()) {
                        Object o = map.get(ss);
                        String s = o.toString();
                        tmsfhfid = Integer.parseInt(s);
                    }
                }
            } else {
                Map map = fhmaps.get(0);
                for (Object ss : map.keySet()) {
                    Object o = map.get(ss);
                    String s = o.toString();
                    tmsfhfid = Integer.parseInt(s);
                }
            }

            //目的城市收货方信息
            OrderReceivingParty shfxx = orderReceivingParties.get(1);
            String shfxxName = shfxx.getName();//名称
            String shflxr = shfxx.getContactperson();//联系人
            String shfdh = shfxx.getIphone();//联系电话
            String shfpingYinxx = PinYin.getPinYinHeadChar(shfxxName);//编写订单CODE时用
            String shfpingYin111 = PinYin.getPinYinHeadChar(shfxxName);//汉字变成拼音小写
            String shfpingYindx = shfpingYin111.toUpperCase();//转成大写
            String shfdz = shfxx.getDetailedAddress();//地址
            ServerZone endZone = order.getEndZone();
            Zone mdzone = endZone.getZone();
            String mdName = null;//目的运点
            String mdydName = mdzone.getName();

            if(mdzone.getLevel().equals("province")){//判断是否是省份
                if(mdzone.getName().equals("北京市")){
                    mdName="北京市";
                }
                if(mdzone.getName().equals("上海市")){
                    mdName="上海市";
                }
                if(mdzone.getName().equals("天津市")){
                    mdName="天津市";
                }
                if(mdzone.getName().equals("重庆市")){
                    mdName="重庆市";
                }
            }
            if(mdzone.getLevel().equals("city")){//如果是市级
                mdName = mdzone.getName();
            }
            if(mdzone.getLevel().equals("district")){//如果是区级
                Zone zone1 = mdzone.getZone();
                mdName = zone1.getName();
            }
            if(mdzone.getLevel().equals("villages")|| mdzone.getLevel().equals("street")){//乡镇级 street
                Zone zone1 = mdzone.getZone();//先获得区级
                Zone zone2 = zone1.getZone();//获得市级
                mdName = zone2.getName();
            }

            boolean is_shi1 = mdName.contains("市");
            if(is_shi){
                int shi = mdName.indexOf("市");
                mdydName = mdName.substring(0,shi);
            }
            boolean is_qu1 = mdName.contains("区");
            if(is_qu){
                int shi = mdName.indexOf("区");
                mdydName = mdName.substring(0,shi);
            }
            boolean is_xian1 = mdName.contains("县");
            if(is_xian){
                int shi = mdName.indexOf("县");
                mdydName = mdName.substring(0,shi);
            }
            boolean is_jie1 = mdName.contains("街");
            if(is_jie){
                int shi = mdName.indexOf("街");
                mdydName = mdName.substring(0,shi);
            }
            boolean is_lu1 = mdName.contains("路");
            if(is_lu){
                int shi = mdName.indexOf("路");
                mdydName = mdName.substring(0,shi);
            }

            String mdydCode1 = PinYin.getPinYinHeadChar(mdName);
            String  dqsjhm1 = String.valueOf(currentTimeMillis);
            String subs1 = dqsjhm1.substring(4);//截取当时毫秒的后8位
            String mdydCode = subs1 + mdydCode1;


            String sql = "select a.id from TMS_CITY a where a.name = '" + mdName + "' and a.status ='ACTIVE'";
            List<Map> mdmaps = instance.exeQuerySql(sql);
            if (mdmaps.size() > 0) {
                Map map = mdmaps.get(0);
                for (Object ss : map.keySet()) {
                    Object o = map.get(ss);
                    String s = o.toString();
                    mdcsid = Integer.parseInt(s);
                }
                //获取目的运点ID
                String mdydsql = "select a.id from TMS_TRANSLOCATION a where a.name = '" + mdydName + "'and a.status ='ACTIVE'";
                List<Map> mdydmaps = instance.exeQuerySql(mdydsql);
                if (mdydmaps.size() > 0) {
                    Map map1 = mdydmaps.get(0);
                    for (Object ss : map1.keySet()) {
                        Object o = map1.get(ss);
                        String s = o.toString();
                        mdydid = Integer.parseInt(s);
                    }
                } else {
                    String insertmdyd="INSERT INTO TMS_TRANSLOCATION(CODE,NAME,TMS_CITY_ID,STATUS,TRANSLOCATION_TYPE_ID,CREATOR,CREATED_TIME)" +
                            "VALUES(?,?,?,?,?,?,?)";
                    Object mdydobj[]={mdydCode,mdydName,mdcsid,"ACTIVE",1,create_name,create_time};
                    boolean b = instance.exeQuerSql(insertmdyd, mdydobj);
                    if(b){
                        String mdydsql1 = "select a.id from TMS_TRANSLOCATION a where a.name = '" + mdydName + "' and a.status ='ACTIVE'";
                        List<Map> mdydmaps1 = instance.exeQuerySql(mdydsql1);
                        if (mdydmaps1.size() > 0) {
                            Map map2 = mdydmaps1.get(0);
                            for (Object ss : map2.keySet()) {
                                Object o = map2.get(ss);
                                String s = o.toString();
                                mdydid = Integer.parseInt(s);
                            }
                        }
                    }
                }
            } else {
                String message = "您好，没有查到TMS系统里有同样的城市名称 ";  // 客户类型与客户名称判断
                Map<String, String> mapfhdh4 = new HashMap<String, String>();
                mapfhdh4.put("message", message);
                messages.add(mapfhdh4);
                fail++;  // 失败一条
            }
            //查询收货方信息是否存在，不存在则添加
            String cxtmsfhfsj = "select a.id from TMS_RECEIVER a where a.name ='" + shfxxName + "' and a.TMS_CITY_ID =" + mdcsid + " and" +
                    " a.CUSTOMER = " + ss_fxmId + " and a.TRANS_LOCATION_ID=" + mdydid + " and a.status ='ACTIVE'";
            List<Map> shmaps = instance.exeQuerySql(cxtmsfhfsj);
            if (shmaps.size() <= 0) {
                String crshfsql = "INSERT into TMS_RECEIVER(CODE,NAME,STATUS,TMS_CITY_ID,CUSTOMER,TRANS_LOCATION_ID,ADDRESS,CONTACTNAME,TELEPHONE) " +
                        " VALUES(?,?,?,?,?,?,?,?,?)";
                Object obj[] = {currentTimeMillis, shfxxName, status, mdcsid, ss_fxmId, mdydid, shfdz, shflxr, shfdh};
                boolean b = instance.exeQuerSql(crshfsql, obj);
                if (b == true) {
                    String cxtmsshfsj1 = "select a.id from TMS_RECEIVER a where a.name ='" + shfxxName + "' and a.TMS_CITY_ID =" + mdcsid + " and" +
                            " a.CUSTOMER = " + ss_fxmId + " and a.TRANS_LOCATION_ID=" + mdydid + "and a.status ='ACTIVE'";
                    List<Map> shmaps1 = instance.exeQuerySql(cxtmsshfsj1);
                    Map map = shmaps1.get(0);
                    for (Object ss : map.keySet()) {
                        Object o = map.get(ss);
                        String s = o.toString();
                        tmsshfid = Integer.parseInt(s);
                    }
                } else {
                    String message = "您好，插入失败！在该【" + ss_ptName + "】平台下，发货方名称'" + shfxxName + "'重复，不能插入";  // 客户类型与客户名称判断
                    Map<String, String> mapfhdh5 = new HashMap<String, String>();
                    mapfhdh5.put("message", message);
                    messages.add(mapfhdh5);
                    fail++;  // 失败一条
                }
            } else {
                Map map = shmaps.get(0);
                for (Object ss : map.keySet()) {
                    Object o = map.get(ss);
                    String s = o.toString();
                    tmsshfid = Integer.parseInt(s);
                }
            }
            String ddzt = null;
            int status1 = order.getStatus();
            switch (status1) {
                case 1:
                    ddzt = "OPEN";
                    break;
                case 2:
                    ddzt = "AVAILABLE";
                    break;
                case 3:
                    ddzt = "DISPATCH";
                    break;
                case 4:
                    ddzt = "ONROAD";
                    break;
                case 5:
                    ddzt = "ARRIVED";
                    break;
            };
            String dxfxm = PinYin.getPinYinHeadChar(ss_fxmName);
            String dxfxmname = dxfxm.toUpperCase();
            String s1 = String.valueOf(currentTimeMillis);
            String substring = s1.substring(4);//截取当时毫秒的后8位
            String ddCode = "FXM-" + order.getCode();
            String ysfs = "TRUCK";//运输方式（汽运）
            String ysxz = "STAND";//运输性质 标准：STAND   加急：URGENT
            String pzfs = "LTL";//配载方式（为了方便，通一配载方式为零担）
            String locked = "UNLOCKED";//必填项，不能为空
            String bzdsdx = "3AA";//必填项，不能为空
            double qkzl = zhongliang * 1000;//吨重量，不能为空
            int ddid = 0;
            int IS_EXECUTE_EXP = 0;//不能为空，否则TMS系统里不能查看等操作
            int IS_BALANCE = 0; //是否结费
            int COLLECTION_ON_DELIVERY = 0;//是否有代收货款
            int COMPULSORY_POD = 0;//是否强制回单
            int TOTAL_TORR = 0;//TMS里的总托数
            double BULK_LOAD_NUM = 0.0;
            double BULK_LOAD_PLACE = 0.0;
            int CUSTOMER_MODULUS = 0;//客户单系数-签收单数量
            String BUSINESS_TYPE = "LINK";//业务类型
            String EXTEND_FIELD1 = "STANDAND";//付费和结算方式
            int EXTEND_FIELD4 = 0;//是否MilkRun
            int EXTEND_FIELD5 = 0;//扩展字段5
            double CHARGEABLE_WEIGHT = 0.0;//计费吨
            double GOODS_DAMAGE = 0.0;//货损
            double REMNANT_DAMAGE = 0.0;//残损
            double BOX_DAMAGE = 0.0;//箱损
            double SHORTAGE = 0.0;//短少
            double ORIGINAL_SHORTAGE = 0.0;//原装短少
            double WATERWET = 0.0;//水湿
            double REJECT_QUANTITY = 0.0;//拒收数量
            double SIGN_QUANTITY = 0.0;//签收数量
            double GOODS_AMOUNT = 0.0;//代收货款金额
            int ACCOUNT = 0;//所属账期
            double EXPENSE = 0.0;//一口价
            int yslx = 24;//运输流向 （24：正向）
            //插入订单基础数据后反查该订单ID
            String cxTMSddhql="select a.id from TMS_ORDER a where a.code ='"+ddCode+"'";
            List<Map> ddmaps2 = instance.exeQuerySql(cxTMSddhql);
            if(ddmaps2.size()<=0){
                String crddsql = "INSERT INTO TMS_ORDER (code,STATUS,LOCKED,ORDER_MONITOR_STATUS,PLAT_FORM_ID,CUSTOMER_ID,RELATEBILL1,RELATEBILL2," +
                        "SHIPMENT_METHOD,LOADING_METHOD,ORDER_TYPE_ID,ORDER_PLACE_DATE,QUANTITY,WEIGHT,WEIGHTK,TORR,VOLUME," +
                        "FROM_LOCATOIN_ID,FROM_RECEIVER_ID,TO_LOCATION_ID,TO_RECEIVER_ID,FROM_ADDRESS,FROM_CONTACTNAME," +
                        "FROM_TELEPHONE,TO_ADDRESS,TO_CONTACTNAME,TO_TELEPHONE,SHIPMENT_DIRECTION,TRANSPORT_PRO,IS_EXECUTE_EXP,IS_BALANCE," +
                        "COLLECTION_ON_DELIVERY,CREATOR,CREATED_TIME,COMPULSORY_POD,TOTAL_TORR,BULK_LOAD_NUM,BULK_LOAD_PLACE,CUSTOMER_MODULUS," +
                        "BUSINESS_TYPE,EXTEND_FIELD1,EXTEND_FIELD4,EXTEND_FIELD5,CHARGEABLE_WEIGHT,GOODS_DAMAGE,REMNANT_DAMAGE,BOX_DAMAGE,SHORTAGE," +
                        "ORIGINAL_SHORTAGE,WATERWET,REJECT_QUANTITY,SIGN_QUANTITY,GOODS_AMOUNT,ACCOUNT,EXPENSE)" +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                Object ddobj[] = {ddCode, ddzt, locked, bzdsdx, ss_ptId, ss_fxmId, relatebill1, relatebill2, ysfs, pzfs, yslx, time, shuliang, zhongliang, qkzl, tuoshu, tiji,
                        cfydid, tmsfhfid, mdydid, tmsshfid, fhfdz, fhflxr, fhflxdh, shfdz, shflxr, shfdh, yslx, ysxz, IS_EXECUTE_EXP, IS_BALANCE, COLLECTION_ON_DELIVERY, create_name,
                        create_time, COMPULSORY_POD, TOTAL_TORR, BULK_LOAD_NUM, BULK_LOAD_PLACE, CUSTOMER_MODULUS, BUSINESS_TYPE, EXTEND_FIELD1, EXTEND_FIELD4, EXTEND_FIELD5, CHARGEABLE_WEIGHT,
                        GOODS_DAMAGE, REMNANT_DAMAGE, BOX_DAMAGE, SHORTAGE, ORIGINAL_SHORTAGE, WATERWET, REJECT_QUANTITY, SIGN_QUANTITY, GOODS_AMOUNT, ACCOUNT, EXPENSE};
                boolean b = instance.exeQuerSql(crddsql, ddobj);
                if (b == true) {
                    String cxtmsddid = "select a.id from TMS_ORDER a where a.code ='" + ddCode + "' and a.CUSTOMER_ID = " + ss_fxmId;
                    List<Map> ddmaps1 = instance.exeQuerySql(cxtmsddid);
                    order.setRelatebill3("已同步");
                    Map map = ddmaps1.get(0);
                    for (Object ss : map.keySet()) {
                        Object o = map.get(ss);
                        String s = o.toString();
                        ddid = Integer.parseInt(s);
                    }
                } else {
                    String message = "您好，订单插入失败！在该【" + ss_ptName + "】平台下，单号为【" + relatebill1 + "】的订单插入失败";  // 客户类型与客户名称判断
                    Map<String, String> mapfhdh6 = new HashMap<String, String>();
                    mapfhdh6.put("message", message);
                    messages.add(mapfhdh6);
                    fail++;  // 失败一条
                }
            }else{
                order.setRelatebill3("已同步");
                Map map = ddmaps2.get(0);
                for (Object ss : map.keySet()) {
                    Object o = map.get(ss);
                    String s = o.toString();
                    ddid = Integer.parseInt(s);
                }
            }

            //获取货品信息
            Integer tmshpid = 0;
            Integer tmshplxid = 0;
            List<OrderProduct> orderProducts = order.getOrderProducts();
            String unit = null;
            Integer number = null;//件数
            Double volume = 0.0;//体积
            Double weight = 0.0;//重量
            String productName = null;
            double hz = 0.0;
            if (orderProducts.size() > 0) {
                OrderProduct orderProduct = orderProducts.get(0);
                number = orderProduct.getNumber();//件数
                volume = orderProduct.getVolume();//体积
                weight = orderProduct.getWeight();//重量
                unit = orderProduct.getUnit();//单位
                productName = orderProduct.getName();//货名
                String hpmc = PinYin.getPinYinHeadChar(productName);
                String cxTmshpsql = "SELECT a.id from TMS_PRODUCT a where a.NAME='" + productName + "'and a.status ='ACTIVE'";
                List<Map> maps = instance.exeQuerySql(cxTmshpsql);
                if (maps.size() > 0) {
                    Map map = maps.get(0);
                    for (Object obj : map.keySet()) {
                        Object o = map.get(obj);
                        String s = o.toString();
                        tmshpid = Integer.parseInt(s);
                    }
                } else {
                    String tmshplx = "SELECT a.id from TMS_PRODUCT_TYPE a where a.TMS_ORGANAZITION_ID = " + ss_fxmId + "and a.status ='ACTIVE'";
                    List<Map> maps1 = instance.exeQuerySql(tmshplx);
                    if (maps1.size() > 0) {
                        Map map = maps1.get(0);
                        for (Object obj : map.keySet()) {
                            Object o = map.get(obj);
                            String s = o.toString();
                            tmshplxid = Integer.parseInt(s);
                        }
                    } else {
                        String message = "您好，查询失败！该【" + ss_ptName + "】平台下没有查到货品类型，请先添加";  // 客户类型与客户名称判断
                        Map<String, String> mapfhdh7 = new HashMap<String, String>();
                        mapfhdh7.put("message", message);
                        messages.add(mapfhdh7);
                        fail++;  // 失败一条
                    }
                    String crhpsql = "INSERT into TMS_PRODUCT(CODE,NAME,TMS_ORGANAZITION_ID,STATUS,TMS_PRODUCT_ID,UNIT,COST) " +
                            " VALUES(?,?,?,?,?,?,?)";
                    Object bpobj[] = {hpmc, productName, ss_fxmId, status, tmshplxid, unit, hz};
                    boolean bs = instance.exeQuerSql(crhpsql, bpobj);
                    if (bs == true) {
                        String cxtmshp = "SELECT a.id from TMS_PRODUCT a where a.NAME='" + productName + "' and a.TMS_ORGANAZITION_ID = " + ss_fxmId + "and a.status ='ACTIVE'";
                        List<Map> tmshpmaps = instance.exeQuerySql(cxtmshp);
                         if(tmshpmaps.size()>0){
                             Map map = tmshpmaps.get(0);
                             for (Object ss : map.keySet()) {
                                 Object o = map.get(ss);
                                 String s = o.toString();
                                 tmshpid = Integer.parseInt(s);
                             }
                         }else{
                             String message = "您好，插入失败！在该【" + ss_ptName + "】平台下，货品CODE'" + hpmc + "'重复，不能插入";  // 客户类型与客户名称判断
                             Map<String, String> mapfhdh8 = new HashMap<String, String>();
                             mapfhdh8.put("message", message);
                             messages.add(mapfhdh8);
                             fail++;  // 失败一条
                         }
                    } else {
                        String message = "您好，插入失败！在该【" + ss_ptName + "】平台下，货品CODE'" + hpmc + "'重复，不能插入";  // 客户类型与客户名称判断
                        Map<String, String> mapfhdh8 = new HashMap<String, String>();
                        mapfhdh8.put("message", message);
                        messages.add(mapfhdh8);
                        fail++;  // 失败一条
                    }
                }
            }
            //插入订单明细表(订单和货品关系表) 22列
            String cxddhpmxsql="select a.id from TMS_ORDER_DETAIL a where a.ORDER_ID ="+ddid + " and a.PRODUCT_ID =" + tmshpid;
            List<Map> ddhpmxmaps1 = instance.exeQuerySql(cxddhpmxsql);
            if(ddhpmxmaps1.size()<=0){
                String crddmxb = "insert into TMS_ORDER_DETAIL(ORDER_ID,LINE_NO,BE_LEVEL,BE_REJECT,BOX_DAMAGE,GOODS_DAMAGE,PRODUCT_ID,QUANTITY,REMNANT_DAMAGE," +
                        "SHORTAGE,SIGN_QUANTITY,CURRENT_SIGN_QUANTITY,UN_SIGN_QUANTITY,UNIT,CREATOR,CREATED_TIME,VOLUME,WATERWET,WEIGHT,CHARGEABLE_WEIGHT," +
                        "REJECT_QUANTITY,ITEM_VALUE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                Object ddmxobj[] = {ddid, 1, 0, 0, 0, 0, tmshpid, shuliang, 0, 0, 0, 0, 0, unit, create_name, create_time, tiji, 0, zhongliang, 0, 0, 0.0};
                boolean is_success = instance.exeQuerSql(crddmxb, ddmxobj);
                if (is_success == false) {
                    String message = "您好，订单明细(货品)插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败，请检查原因";  // 客户类型与客户名称判断
                    Map<String, String> mapfhdh9 = new HashMap<String, String>();
                    mapfhdh9.put("message", message);
                    messages.add(mapfhdh9);
                    fail++;  // 失败一条
                }
            }
            //插入分段运单 TMS_LEG
            List<Led> leds = order.getLeds();
            Shipment shipment = null;
            Integer cysid = 0;
            Integer cysfwcpid = 0;
            String cysCode = null;
            String cysName = null;
            String fdydcode = null;
            Integer fdydid =0;
            Integer ydid = 0;
            String ccdfydidsql = null;
            if (leds.size() > 0) {
                for (Led led : leds) {
                    //查找承运商 并且添加承运商
                    //分段运单
                    //39列
                    fdydcode ="FXM-"+ led.getCode();
                    ccdfydidsql="select a.id from TMS_LEG a where a.EC_NO ='"+fdydcode+"'";
                    List<Map> fdydmaps = instance.exeQuerySql(ccdfydidsql);
                    if(fdydmaps.size()>=1){
                        Map map1 = fdydmaps.get(0);
                        for (Object ss : map1.keySet()) {
                            Object o = map1.get(ss);
                            String cxss = o.toString();
                            fdydid = Integer.parseInt(cxss);
                        }
                        List<LedProduct> ledProducts = led.getLedProducts();
                        for (LedProduct ledProduct : ledProducts) {
                            Double fdhpsl = Double.valueOf(ledProduct.getNumber());//分段运单货品数量
                            String unit1 = ledProduct.getUnit();//包装（单位）
                            double volume1 = ledProduct.getVolume();//体积
                            double weight1 = ledProduct.getWeight();//重量
                            String cxfyydhpsql="select a.id from TMS_LEG_DETAIL a where a.PRODUCT_ID="+tmshpid;
                            List<Map> fdhpmaps = instance.exeQuerySql(cxfyydhpsql);
                            if(fdhpmaps.size()<=0){
                                String insertfdydhp = "INSERT INTO TMS_LEG_DETAIL(LEG_ID,LINE_NO,PRODUCT_ID,QUANTITY,UNIT,VOLUME,WEIGHT,CHARGEABLE_WEIGHT" +
                                        ",CREATOR,CREATED_TIME) VALUES(?,?,?,?,?,?,?,?,?,?)";
                                Object fdhpobj[] = {fdydid, 1,tmshpid, fdhpsl, unit1, volume1, weight1, 0, create_name, create_time};
                                boolean is_succrfdhp = instance.exeQuerSql(insertfdydhp, fdhpobj);
                                if (is_succrfdhp == false) {
                                    String message = "您好，分段运单明细插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败";  // 客户类型与客户名称判断
                                    Map<String, String> mapfhdh12 = new HashMap<String, String>();
                                    mapfhdh12.put("message", message);
                                    messages.add(mapfhdh12);
                                    fail++;  // 失败一条
                                }
                            }
                        }
                    }else{
                        String insertfdydb = "INSERT INTO TMS_LEG(TMS_ORDER_ID,LEGNO,LINE_NO,EC_NO,PLAT_FORM_ID,CUSTOMER_ID,STATUS,FROM_RECEIVER_ID," +
                                "FROM_ADDRESS,FROM_CONTACTNAME,FROM_TELEPHONE,TO_RECEIVER_ID,TO_ADDRESS,TO_CONTACTNAME,TO_TELEPHONE,FROM_LOCATION_ID," +
                                "TO_LOCATION_ID,SHIPMENT_METHOD,LOADING_METHOD,BUSINESS_TYPE,QUANTITY,TORR,EXPENSE,VOLUME,WEIGHT,CHARGEABLE_WEIGHT," +
                                "CREATOR,CREATED_TIME,SHARE_COST,SINGLE_QUANTITY,SINGLE_VOLUME,SINGLE_WEIGHT,LEG_QUANTITY,IS_HAVE_RBILL,IS_RBILL,SN," +
                                "IS_BALANCE,IS_MANUAL_BALANCE,IS_SERVICE_BALANCE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                "?,?,?,?,?,?,?,?,?)";
                        Object fdydobj[] = {ddid, ddCode, 1,fdydcode, ss_ptId, ss_fxmId, status, tmsfhfid, fhfdz, fhflxr, fhflxdh, tmsshfid, shfdz, shflxr, shfdh, cfydid,
                                mdydid, ysfs, pzfs, "LINK", shuliang, tuoshu, 0, tiji, zhongliang, 0,create_name, create_time, 0, 0, 0, 0, order.getBackNumber(), order.getIsBack(), 0, 1,
                                0, 0, 0};
                        boolean is_succrfdys = instance.exeQuerSql(insertfdydb, fdydobj);
                        if (is_succrfdys == false) {
                            String message = "您好，分段运单插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败，请检查原因";  // 客户类型与客户名称判断
                            Map<String, String> mapfhdh11 = new HashMap<String, String>();
                            mapfhdh11.put("message", message);
                            messages.add(mapfhdh11);
                            fail++;  // 失败一条
                        } else {
                            led.setRelatebill3("已同步");
                            ccdfydidsql="select a.id from TMS_LEG a where a.EC_NO ='"+fdydcode+"'";
                            List<Map> maps = instance.exeQuerySql(ccdfydidsql);
                            if(maps.size()>=1){
                                Map map1 = maps.get(0);
                                for (Object ss : map1.keySet()) {
                                    Object o = map1.get(ss);
                                    String cxss = o.toString();
                                    fdydid = Integer.parseInt(cxss);
                                }
                            }
                            List<LedProduct> ledProducts = led.getLedProducts();
                            for (LedProduct ledProduct : ledProducts) {
                                Double fdhpsl = Double.valueOf(ledProduct.getNumber());//分段运单货品数量
                                String fdydhp = ledProduct.getId();
                                String unit1 = ledProduct.getUnit();//包装（单位）
                                double volume1 = ledProduct.getVolume();//体积
                                double weight1 = ledProduct.getWeight();//重量
                                String cxfyydhpsql="select a.id from TMS_LEG_DETAIL a where a.PRODUCT_ID="+tmshpid +"and a.LEG_ID="+fdydid;
                                List<Map> fdhpmaps = instance.exeQuerySql(cxfyydhpsql);
                                if(fdhpmaps.size()<=0){
                                    String insertfdydhp = "INSERT INTO TMS_LEG_DETAIL(LEG_ID,LINE_NO,PRODUCT_ID,QUANTITY,UNIT,VOLUME,WEIGHT,CHARGEABLE_WEIGHT" +
                                            ",CREATOR,CREATED_TIME) VALUES(?,?,?,?,?,?,?,?,?,?)";
                                    Object fdhpobj[] = {fdydid, 1,tmshpid, fdhpsl, unit1, volume1, weight1, 0, create_name, create_time};
                                    boolean is_succrfdhp = instance.exeQuerSql(insertfdydhp, fdhpobj);
                                    if (is_succrfdhp == false) {
                                        String message = "您好，分段运单明细插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败";  // 客户类型与客户名称判断
                                        Map<String, String> mapfhdh12 = new HashMap<String, String>();
                                        mapfhdh12.put("message", message);
                                        messages.add(mapfhdh12);
                                        fail++;  // 失败一条
                                    }
                                }
                            }
                        }  //插入分段运单成功后结束地点
                    }
                    shipment = led.getShipment();
                    Carrier carrier = shipment.getCarrier();
                    cysCode = carrier.getCode();
                    cysName = carrier.getName();
                    //先查找承运商，如果没有则添加
                    String czcys = "select a.id from TMS_ORGANIZATION a where a.name='" + carrier.getName() + "' and a.IS_CARRIER =1";
                    List<Map> cysmaps = instance.exeQuerySql(czcys);
                    if (cysmaps.size() >= 1) {
                        Map map = cysmaps.get(0);
                        for (Object ss : map.keySet()) {
                            Object o = map.get(ss);
                            String cysidN = o.toString();
                            cysid = Integer.parseInt(cysidN);
                        }
                        String cztmscysfwcp = "select a.id from TMS_CARRIER_SERVICE_TYPE a where  a.TMS_ORGANIZATION_ID =" + cysid;
                        List<Map> fwcpmaps = instance.exeQuerySql(cztmscysfwcp);
                        if (fwcpmaps.size() >= 1) {
                            Map map1 = fwcpmaps.get(0);
                            for (Object ss : map1.keySet()) {
                                Object o = map1.get(ss);
                                String cysfwcpidN = o.toString();
                                cysfwcpid = Integer.parseInt(cysfwcpidN);
                            }
                        }else{
                            //添加查找承运商服务产品
                            String insertcysfwcp = "INSERT INTO TMS_CARRIER_SERVICE_TYPE(TMS_ORGANIZATION_ID,CODE,NAME,SHIPMENTMETHOD,STATUS," +
                                    "CREATOR,CREATED_TIME,BEGIN_DATE,END_DATE) VALUES(?,?,?,?,?,?,?,?,?)";
                            String cysfwcpCode = cysCode + "YS";
                            String cysfwcpName = cysName + "运输";
                            long l = System.currentTimeMillis();
                            Timestamp timestamp = new Timestamp(l);
                            Object cysfwcpojb[] = {cysid, cysfwcpCode, cysfwcpName, "TRUCK", status, create_name, create_time, timestamp, timestamp};
                            boolean is_fwcp = instance.exeQuerSql(insertcysfwcp, cysfwcpojb);
                            if (is_fwcp == false) {
                                String message = "您好，承运商服务产品插入失败！在该【" + ss_ptName + "】平台下，承运商服务产品【" + cysfwcpName + "】插入失败";  // 客户类型与客户名称判断
                                Map<String, String> mapfhdh14 = new HashMap<String, String>();
                                mapfhdh14.put("message", message);
                                messages.add(mapfhdh14);
                                fail++;  // 失败一条
                            } else {
                                String cztmscysfwcp1 = "select a.id from TMS_CARRIER_SERVICE_TYPE a where a.name='" + cysfwcpName + "' and a.TMS_ORGANIZATION_ID =" + cysid;
                                List<Map> fwcpmaps1 = instance.exeQuerySql(cztmscysfwcp1);
                                if (fwcpmaps1.size() >= 1) {
                                    Map map1 = fwcpmaps1.get(0);
                                    for (Object ss : map1.keySet()) {
                                        Object o = map1.get(ss);
                                        String cysidN = o.toString();
                                        cysfwcpid = Integer.parseInt(cysidN);
                                    }
                                }
                            }
                        }
                    }else {
                        //TEMP 临时承运商 CONTRACT合同承运商
                        //16列
                        String insertcys = "INSERT INTO TMS_ORGANIZATION(CODE,NAME,SIMPLE_CODE,TMS_CITY_ID,STATUS,IS_CARRIER,IS_CUSTOMER," +
                                "IS_PLATFORM,IS_SALE,CREATOR,CREATED_TIME,TYPE,IS_USE_CARRIER,ACCOUNT_DAYS,IS_BRANCH,IS_COMPANY) VALUES(?," +
                                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        Object cysobj[] = {cysCode, cysName, cysName, 1, status, 1, 0, 0, 0, create_name, create_time, "CONTRACT", 1, 90.0, 0, 0};
                        boolean b1 = instance.exeQuerSql(insertcys, cysobj);
                        if (b1 == false) {
                            String message = "您好,承运商插入失败！在该【" + ss_ptName + "】平台下，承运商名称【" + cysName + "】插入失败";  // 客户类型与客户名称判断
                            Map<String, String> mapfhdh13 = new HashMap<String, String>();
                            mapfhdh13.put("message", message);
                            messages.add(mapfhdh13);
                            fail++;  // 失败一条
                        } else {
                            String cztmscys = "select a.id from TMS_ORGANIZATION a where a.name='" + carrier.getName() + "' and a.IS_CARRIER =1";
                            List<Map> cysmaps1 = instance.exeQuerySql(cztmscys);
                            if (cysmaps1.size() >= 1) {
                                Map cysmap = cysmaps1.get(0);
                                for (Object ss : cysmap.keySet()) {
                                    Object o = cysmap.get(ss);
                                    String cysidN = o.toString();
                                    cysid = Integer.parseInt(cysidN);
                                }
                                String cztmscysfwcp = "select a.id from TMS_CARRIER_SERVICE_TYPE a where  a.TMS_ORGANIZATION_ID =" + cysid;
                                List<Map> fwcpmaps = instance.exeQuerySql(cztmscysfwcp);
                                if (fwcpmaps.size() >= 1) {
                                    Map map1 = fwcpmaps.get(0);
                                    for (Object ss : map1.keySet()) {
                                        Object o = map1.get(ss);
                                        String cysfwcpidN = o.toString();
                                        cysfwcpid = Integer.parseInt(cysfwcpidN);
                                    }
                                }else{
                                    //添加查找承运商服务产品
                                    String insertcysfwcp = "INSERT INTO TMS_CARRIER_SERVICE_TYPE(TMS_ORGANIZATION_ID,CODE,NAME,SHIPMENTMETHOD,STATUS," +
                                            "CREATOR,CREATED_TIME,BEGIN_DATE,END_DATE) VALUES(?,?,?,?,?,?,?,?,?)";
                                    String cysfwcpCode = cysCode + "YS";
                                    String cysfwcpName = cysName + "运输";
                                    long l = System.currentTimeMillis();
                                    Timestamp timestamp = new Timestamp(l);
                                    Object cysfwcpojb[] = {cysid, cysfwcpCode, cysfwcpName, "TRUCK", status, create_name, create_time, timestamp, timestamp};
                                    boolean is_fwcp = instance.exeQuerSql(insertcysfwcp, cysfwcpojb);
                                    if (is_fwcp == false) {
                                        String message = "您好，承运商服务产品插入失败！在该【" + ss_ptName + "】平台下，承运商服务产品【" + cysfwcpName + "】插入失败";  // 客户类型与客户名称判断
                                        Map<String, String> mapfhdh14 = new HashMap<String, String>();
                                        mapfhdh14.put("message", message);
                                        messages.add(mapfhdh14);
                                        fail++;  // 失败一条
                                    } else {
                                        String cztmscysfwcp1 = "select a.id from TMS_CARRIER_SERVICE_TYPE a where a.name='" + cysfwcpName + "' and a.TMS_ORGANIZATION_ID =" + cysid;
                                        List<Map> fwcpmaps1 = instance.exeQuerySql(cztmscysfwcp1);
                                        if (fwcpmaps1.size() >= 1) {
                                            Map map1 = fwcpmaps1.get(0);
                                            for (Object ss : map1.keySet()) {
                                                Object o = map1.get(ss);
                                                String cysidN = o.toString();
                                                cysfwcpid = Integer.parseInt(cysidN);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //查找添加车型
                    Integer cxid = 0;
                    String cph = null;
                    VehicleHead vehicleHead = shipment.getVehicleHead();
                    if(vehicleHead == null){
                        cph = shipment.getLiense();
                    }else{
                        cph = vehicleHead.getCode();//车牌号
                    }

                    Vehicle vehicle = shipment.getVehicle();
                    String cxCode = vehicle.getCode();//车型
                    String czTmscxsql = "select a.id from TMS_VEHICLE_TYPE a where a.name='" + cxCode + "'";
                    List<Map> cxmaps = instance.exeQuerySql(czTmscxsql);
                    if (cxmaps.size() > 0) {
                        Map map1 = cxmaps.get(0);
                        for (Object ss : map1.keySet()) {
                            Object o = map1.get(ss);
                            String cxss = o.toString();
                            cxid = Integer.parseInt(cxss);
                        }
                    } else {
                        String insertcxsql = "INSERT INTO TMS_VEHICLE_TYPE(CODE,NAME,STATUS,CREATOR,CREATED_TIME)VALUES(?,?,?,?,?)";
                        Object cxobj[] = {cxCode, cxCode, "ACTIVE", create_name, create_time};
                        boolean b1 = instance.exeQuerSql(insertcxsql, cxobj);
                        if (b1 == false) {
                            String message = "您好，车型插入失败！在该【" + ss_ptName + "】平台下，车型名称【" + cxCode + "】插入失败";  // 客户类型与客户名称判断
                            Map<String, String> mapfhdh16 = new HashMap<String, String>();
                            mapfhdh16.put("message", message);
                            messages.add(mapfhdh16);
                            fail++;  // 失败一条
                        } else {
                            String czTmscxsql1 = "select a.id TMS_VEHICLE_TYPE a where a.name='" + cxCode + "' and a.status='ACTIVE'";
                            List<Map> cxmaps1 = instance.exeQuerySql(czTmscxsql);
                            if (cxmaps1.size() > 0) {
                                Map map1 = cxmaps1.get(0);
                                for (Object ss : map1.keySet()) {
                                    Object o = map1.get(ss);
                                    String cxss = o.toString();
                                    cxid = Integer.parseInt(cxss);
                                }
                            }
                        }
                    }
                    //插入运单  37列
                    String shipCode = "PT-"+shipment.getCode();
                    String czydid="select a.id from TMS_SHIPMENT a where a.code='"+shipCode+"' and a.PLAT_FORM_ID="+ss_ptId;
                    List<Map> ydmaps = instance.exeQuerySql(czydid);
                    if(ydmaps.size()<=0){
                        String insertship = "INSERT INTO TMS_SHIPMENT(CODE,CARRIER_ID,SERVICE_TYPE_ID,PLAT_FORM_ID,SHIPMENT_METHOD,LOADING_METHOD," +
                                "STATUS,EXPENSE,ADVANCE_PAYMENT,QUANTITY,VOLUME,WEIGHT,CHARGEABLE_WEIGHT,LEG_NUM,CREATOR,CREATED_TIME,EXTEND_FIELD2," +
                                "IS_BALANCE,BALANCE_MODE,IS_MILKRUN,REAL_AMOUNT,WITHOUT_AMOUNT,SHIPMENT_QUANTITY,TORR,TRANSPORT_PRO,COMPULSORY_POD," +
                                "VEHICLE_TYPE_ID,FROM_PLACE_ID,TO_PLACE_ID,SHIPMENT_DATE,IS_MANUAL_BALANCE,RELATEBILL1,IS_SERVICE_BALANCE," +
                                "IS_BACK,IS_PRINTED,SUM) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                        Object shipobj[] = {shipCode,cysid,cysfwcpid,ss_ptId,ysfs,pzfs,"ARRIVED",0,0,shipment.getNumber(),shipment.getVolume(),shipment.getWeight(),
                                0,1,create_name,create_time,cph,0,"STANDAND",0,0,0,0,0,ysxz,0,cxid,cfydid,mdydid,shipment.getTime(),0,shipment.getRelatebill1(),0,0,0,0};
                        boolean is_ship = instance.exeQuerSql(insertship, shipobj);
                        if(is_ship){
                            shipment.setField3("已同步");
                            czydid="select a.id from TMS_SHIPMENT a where a.code='"+shipCode+"' and a.PLAT_FORM_ID="+ss_ptId;
                            ydmaps = instance.exeQuerySql(czydid);
                            if (ydmaps.size() > 0) {
                                Map map1 = ydmaps.get(0);
                                for (Object ss : map1.keySet()) {
                                    Object o = map1.get(ss);
                                    String cxss = o.toString();
                                    ydid = Integer.parseInt(cxss);
                                }
                                //UPDATE Person SET FirstName = 'Fred' WHERE LastName = 'Wilson'
                                String insertfyyd="UPDATE TMS_LEG SET SHIPMENT_ID= ? where id = ?";
                                Object ffydobj[]={ydid,fdydid};
                                boolean b = instance.exeQuerSql(insertfyyd,ffydobj);
                                if(b == false){
                                    String message = "您好，分段运单的运单ID插入失败！在该【" + ss_ptName + "】平台下，运单CODE【" + shipment.getCode() + "】插入失败";  // 客户类型与客户名称判断
                                    Map<String, String> mapfhdh16 = new HashMap<String, String>();
                                    mapfhdh16.put("message", message);
                                    messages.add(mapfhdh16);
                                    fail++;  // 失败一条
                                }
                            }else{
                                String message = "您好，运单插入失败！在该【" + ss_ptName + "】平台下，运单CODE【" + shipment.getCode() + "】插入失败";  // 客户类型与客户名称判断
                                Map<String, String> mapfhdh16 = new HashMap<String, String>();
                                mapfhdh16.put("message", message);
                                messages.add(mapfhdh16);
                                fail++;  // 失败一条
                            }
                        }
                    }else{
                        Map map1 = ydmaps.get(0);
                        for (Object ss : map1.keySet()) {
                            Object o = map1.get(ss);
                            String cxss = o.toString();
                            ydid = Integer.parseInt(cxss);
                        }
                        String insertfyyd="UPDATE TMS_LEG SET SHIPMENT_ID= ? where id = ?";
                        Object ffydobj[]={ydid,fdydid};
                        boolean b = instance.exeQuerSql(insertfyyd,ffydobj);
                        if(b == false){
                            String message = "您好，分段运单的运单ID插入失败！在该【" + ss_ptName + "】平台下，运单CODE【" + shipment.getCode() + "】插入失败";  // 客户类型与客户名称判断
                            Map<String, String> mapfhdh16 = new HashMap<String, String>();
                            mapfhdh16.put("message", message);
                            messages.add(mapfhdh16);
                            fail++;  // 失败一条
                        }
                    }
                    //先插入以上的运单，在插入分段建立关系
                }//分段运单循环结束
            }//判断时候存在结束
//                  //订单台账处理
            Integer fylxid =0;//费用类型ID
            Integer ddtzid = 0;//订单台账ID
            String cxddtzid = null;
            Timestamp timestamp = DateUtils.getTimestamp(currentTimeMillis);
            int month = timestamp.getMonth();//获得当前月份周期
            List<LedgerDetail> ddledgerDetails = order.getLedgerDetails();
            Double zje= 0.00;
            Double shuijin=0.00;
            for (LedgerDetail ledgerDetail:ddledgerDetails) {
                zje += ledgerDetail.getAmount();
                shuijin += ledgerDetail.getInput();
            }
            cxddtzid="select a.id from TMS_LEDGER a where a.ORDER_ID ="+ddid +" and a.LEDGER_TYPE = 'INCOME'";
            List<Map> tzidmaps = instance.exeQuerySql(cxddtzid);
            if(tzidmaps.size()>=1){
                Map ddtzmap = tzidmaps.get(0);
                for (Object ss : ddtzmap.keySet()) {
                    Object o = ddtzmap.get(ss);
                    String cxss = o.toString();
                    ddtzid = Integer.parseInt(cxss);
                }
                for (LedgerDetail ddtzmx:ddledgerDetails) {
                    FeeType feeType = ddtzmx.getFeeType();
                    String name = feeType.getName();
                    String id = feeType.getId();
                    String fylxsql="select a.tmsFyId from JC_FEE_TYPE_CONTRAST a where a.bjfyId ='"+id+"'";
                    List list1 = feeTypeContrastDao.executeQueryHql(fylxsql);
                    if(list1.size()>0){
                        Object o = list1.get(0);
                        String s = o.toString();
                        fylxid = Integer.valueOf(s);
                    }else{
                        String message = "您好，订单台账明细插入失败！在该【" + ss_ptName + "】平台下，请查看费用类型【" + name + "】是否已同步";  // 客户类型与客户名称判断
                        Map<String, String> mapfhdh12 = new HashMap<String, String>();
                        mapfhdh12.put("message", message);
                        messages.add(mapfhdh12);
                        fail++;  // 失败一条
                    }
                    String cxddtzmxsql="select a.id from TMS_LEDGER_DETAIL a where a.LEDGER_ID="+ddtzid+" and a.FEE_TYPE_ID="+fylxid+" and a.BALANCE_AMOUNT ="+ddtzmx.getAmount() + "and a.INPUT ="+ddtzmx.getInput();
                    List<Map> ddtzmxmaps = instance.exeQuerySql(cxddtzmxsql);
                    if(ddtzmxmaps.size()<=0){
                        //插入台账明细  16列
                        String insertddtzmx="INSERT INTO TMS_LEDGER_DETAIL(LEDGER_ID,FEE_TYPE_ID,FEE_METHOD,UNIT_PRICE,BALANCE_AMOUNT,CREATOR,CREATED_TIME," +
                                "BILLING_TRAFFIC,REVENUE_COST,INPUT,CONFIRM_DETAIL,BERECONCILED,TAX_RATE,VERSION,DEAL_STATUS,JOIN_BILL_STATUS)VALUES(" +
                                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        Object ddtzmxobj[]={ddtzid,fylxid,"AUTO",0,ddtzmx.getAmount(),create_name,create_time,0,0,ddtzmx.getInput(),0,0,ddtzmx.getTaxRate(),
                                0,"READY","READY"};
                        boolean is_tzmx = instance.exeQuerSql(insertddtzmx, ddtzmxobj);
                        if(is_tzmx == false){
                            String message = "您好，应收台账明细插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败";  // 客户类型与客户名称判断
                            Map<String, String> mapfhdh12 = new HashMap<String, String>();
                            mapfhdh12.put("message", message);
                            messages.add(mapfhdh12);
                            fail++;  // 失败一条
                        }
                    }
                }
            }else{
                //应收台账 32列
                String insrtddtz="INSERT INTO TMS_LEDGER(RESPECTIVE_CUSTOMER_ID,CUSTOMER_CARRIER_ID,LEDGER_TYPE,ORDER_ID,LEG_ID,SHIPMENT_ID,INCOME_COST_IDENTITY," +
                        "INCOME_COST_AMOUNT,RECEIVED_PAID_AMOUNT,UNRECEIVED_PAID_AMOUNT,PIECES,VOLUME,WEIGHT,BILLING_TRAFFIC,IS_RECONCILED,IS_SUBMIT," +
                        "CREATOR,CREATED_TIME,orderCode1,orderCode2,DOWNORDER_DATE,SHIP_DATE,REVENUE_COST,LEDGER_PLATFORM_ID,STATUS,ACCOUNT,IS_INVOICE," +
                        "IS_INCOMECOST,INPUT,COMPULSORY_POD,SERVICE_TYPE_ID,IS_LEDGER)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                Object ddtzobj[]={ss_fxmId,cysid,"INCOME",ddid,fdydid,ydid,"INCOME",zje,0,zje,shuliang,tiji,zhongliang,shuliang,0,0,create_name,create_time,relatebill1,relatebill2,
                        time,order.getFactLeaveTime(),"INCOME",ss_ptId,"OPEN",month,0,0,shuijin,0,cysfwcpid,1};
                boolean is_income = instance.exeQuerSql(insrtddtz, ddtzobj);
                if(is_income == false){
                    String message = "您好，应收台账插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败";  // 客户类型与客户名称判断
                    Map<String, String> mapfhdh12 = new HashMap<String, String>();
                    mapfhdh12.put("message", message);
                    messages.add(mapfhdh12);
                    fail++;  // 失败一条
                }else{
                    cxddtzid="select a.id from TMS_LEDGER a where a.ORDER_ID ="+ddid +" and a.LEDGER_TYPE = 'INCOME'";
                    List<Map> tzidmaps1 = instance.exeQuerySql(cxddtzid);
                    Map ddtzmap = tzidmaps1.get(0);
                    for (Object ss : ddtzmap.keySet()) {
                        Object o = ddtzmap.get(ss);
                        String cxss = o.toString();
                        ddtzid = Integer.parseInt(cxss);
                    }
                    for (LedgerDetail ddtzmx:ddledgerDetails) {
                        FeeType feeType = ddtzmx.getFeeType();
                        String name = feeType.getName();
                        String id = feeType.getId();
                        String fylxsql="select a.tmsFyId from JC_FEE_TYPE_CONTRAST a where a.bjfyId ='"+id+"'";
                        List list1 = feeTypeContrastDao.executeQueryHql(fylxsql);
                        if(list1.size()>0){
                            Object o = list1.get(0);
                            String s = o.toString();
                            fylxid = Integer.valueOf(s);
                        }else{
                            String message = "您好，订单台账明细插入失败！在该【" + ss_ptName + "】平台下，请查看费用类型【" + name + "】是否已同步";  // 客户类型与客户名称判断
                            Map<String, String> mapfhdh12 = new HashMap<String, String>();
                            mapfhdh12.put("message", message);
                            messages.add(mapfhdh12);
                            fail++;  // 失败一条
                        }
                        //插入台账明细  16列
                        String insertddtzmx="INSERT INTO TMS_LEDGER_DETAIL(LEDGER_ID,FEE_TYPE_ID,FEE_METHOD,UNIT_PRICE,BALANCE_AMOUNT,CREATOR,CREATED_TIME," +
                                "BILLING_TRAFFIC,REVENUE_COST,INPUT,CONFIRM_DETAIL,BERECONCILED,TAX_RATE,VERSION,DEAL_STATUS,JOIN_BILL_STATUS)VALUES(" +
                                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        Object ddtzmxobj[]={ddtzid,fylxid,"AUTO",0,ddtzmx.getAmount(),create_name,create_time,0,0,ddtzmx.getInput(),0,0,ddtzmx.getTaxRate(),
                                0,"READY","READY"};
                        boolean is_tzmx = instance.exeQuerSql(insertddtzmx, ddtzmxobj);
                        if(is_tzmx == false){
                            String message = "您好，应收台账明细插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败";  // 客户类型与客户名称判断
                            Map<String, String> mapfhdh12 = new HashMap<String, String>();
                            mapfhdh12.put("message", message);
                            messages.add(mapfhdh12);
                            fail++;  // 失败一条
                        }
                    }
                }
            }


            // 应付台账 32列
            Integer ydtzid =0;//运单台账ID
            String cxydtzid = null;
            Integer ydfylxid =0;//运单费用类型ID
            List<LedgerDetail> ydledgerDetails1 = shipment.getLedgerDetails();
            Double yfzje =0.00;
            Double yfzsj =0.00;
            for (LedgerDetail ydtzmx12:ydledgerDetails1) {
                yfzje += ydtzmx12.getAmount();
                yfzsj += ydtzmx12.getInput();
            }
            cxydtzid="select a.id from TMS_LEDGER a where a.SHIPMENT_ID ="+ydid +" and a.LEDGER_TYPE = 'OUTCOME'";
            List<Map> ydtzidmaps = instance.exeQuerySql(cxydtzid);
            if(ydtzidmaps.size()>=1){
                Map ydtzmap = ydtzidmaps.get(0);
                for (Object ss : ydtzmap.keySet()) {
                    Object o = ydtzmap.get(ss);
                    String cxss = o.toString();
                    ydtzid = Integer.parseInt(cxss);
                }
                for (LedgerDetail ydtzmx:ydledgerDetails1) {
                    FeeType feeType = ydtzmx.getFeeType();
                    String name = feeType.getName();
                    String id = feeType.getId();
                    String fylxsql="select a.tmsFyId from JC_FEE_TYPE_CONTRAST a where a.bjfyId ='"+id+"'";
                    List list1 = feeTypeContrastDao.executeQueryHql(fylxsql);
                    if(list1.size()>0){
                        Object o = list1.get(0);
                        String s = o.toString();
                        ydfylxid = Integer.valueOf(s);
                    }else{
                        String message = "您好，应付台账明细插入失败！在该【" + ss_ptName + "】平台下，请查看费用类型【" + name + "】是否已同步";  // 客户类型与客户名称判断
                        Map<String, String> mapfhdh12 = new HashMap<String, String>();
                        mapfhdh12.put("message", message);
                        messages.add(mapfhdh12);
                        fail++;  // 失败一条
                    }
                    String cxydtzmxsql="select a.id from TMS_LEDGER_DETAIL a where a.LEDGER_ID="+ydtzid+" and a.FEE_TYPE_ID="+ydfylxid+" and " +
                            "a.BALANCE_AMOUNT ="+ydtzmx.getAmount() + "and a.INPUT ="+ydtzmx.getInput();
                    List<Map> ydtzmxmaps = instance.exeQuerySql(cxydtzmxsql);
                    if(ydtzmxmaps.size()<=0){
                        //插入台账明细  16列
                        String insertydtzmx="INSERT INTO TMS_LEDGER_DETAIL(LEDGER_ID,FEE_TYPE_ID,FEE_METHOD,UNIT_PRICE,BALANCE_AMOUNT,CREATOR,CREATED_TIME," +
                                "BILLING_TRAFFIC,REVENUE_COST,INPUT,CONFIRM_DETAIL,BERECONCILED,TAX_RATE,VERSION,DEAL_STATUS,JOIN_BILL_STATUS)VALUES(" +
                                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        Object ydtzmxobj[]={ydtzid,ydfylxid,"AUTO",0,ydtzmx.getAmount(),create_name,create_time,0,0,ydtzmx.getInput(),0,0,ydtzmx.getTaxRate(),
                                0,"READY","READY"};
                        boolean is_tzmx = instance.exeQuerSql(insertydtzmx,ydtzmxobj);
                        if(is_tzmx == false){
                            String message = "您好，应付台账明细插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败";  // 客户类型与客户名称判断
                            Map<String, String> mapfhdh12 = new HashMap<String, String>();
                            mapfhdh12.put("message", message);
                            messages.add(mapfhdh12);
                            fail++;  // 失败一条
                        }
                    }
                }
            }else{
                String insrtydtz="INSERT INTO TMS_LEDGER(RESPECTIVE_CUSTOMER_ID,CUSTOMER_CARRIER_ID,LEDGER_TYPE,ORDER_ID,LEG_ID,SHIPMENT_ID,INCOME_COST_IDENTITY," +
                        "INCOME_COST_AMOUNT,RECEIVED_PAID_AMOUNT,UNRECEIVED_PAID_AMOUNT,PIECES,VOLUME,WEIGHT,BILLING_TRAFFIC,IS_RECONCILED,IS_SUBMIT," +
                        "CREATOR,CREATED_TIME,orderCode1,orderCode2,DOWNORDER_DATE,SHIP_DATE,REVENUE_COST,LEDGER_PLATFORM_ID,STATUS,ACCOUNT,IS_INVOICE," +
                        "IS_INCOMECOST,INPUT,COMPULSORY_POD,SERVICE_TYPE_ID,IS_LEDGER)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                Object ydtzobj[]={ss_fxmId,cysid,"OUTCOME",ddid,fdydid,ydid,"COST",yfzje,0,yfzje,shipment.getNumber(),shipment.getVolume(),shipment.getWeight(),
                        shipment.getNumber(),0,0,create_name,create_time,relatebill1,relatebill2,time,shipment.getFactLeaveTime(),"COST",ss_ptId,"OPEN",month
                        ,0,0,yfzsj,0,cysfwcpid,0};
                boolean is_ydtz = instance.exeQuerSql(insrtydtz, ydtzobj);
                if(is_ydtz == false){
                    String message = "您好，应付台账插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败";  // 客户类型与客户名称判断
                    Map<String, String> mapfhdh12 = new HashMap<String, String>();
                    mapfhdh12.put("message", message);
                    messages.add(mapfhdh12);
                    fail++;  // 失败一条
                }else{
                    cxydtzid="select a.id from TMS_LEDGER a where a.SHIPMENT_ID ="+ydid +" and a.LEDGER_TYPE = 'OUTCOME'";
                    List<Map> ydtzidmaps1 = instance.exeQuerySql(cxydtzid);
                    Map ydtzmap = ydtzidmaps1.get(0);
                    for (Object ss : ydtzmap.keySet()) {
                        Object o = ydtzmap.get(ss);
                        String cxss = o.toString();
                        ydtzid = Integer.parseInt(cxss);
                    }
                    for (LedgerDetail ydtzmx:ydledgerDetails1) {
                        FeeType feeType = ydtzmx.getFeeType();
                        String name = feeType.getName();
                        String id = feeType.getId();
                        String fylxsql="select a.tmsFyId from JC_FEE_TYPE_CONTRAST a where a.bjfyId ='"+id+"'";
                        List list1 = feeTypeContrastDao.executeQueryHql(fylxsql);
                        if(list1.size()>0){
                            Object o = list1.get(0);
                            String s = o.toString();
                            ydfylxid = Integer.valueOf(s);
                        }
                        String cxydtzmxsql="select a.id from TMS_LEDGER_DETAIL a where a.LEDGER_ID="+ydtzid+" and a.FEE_TYPE_ID="+ydfylxid+" and " +
                                "a.BALANCE_AMOUNT ="+ydtzmx.getAmount() + "and a.INPUT ="+ydtzmx.getInput();
                        List<Map> ydtzmxmaps = instance.exeQuerySql(cxydtzmxsql);
                        if(ydtzmxmaps.size()<=0) {
                            //插入台账明细  16列
                            String insertydtzmx = "INSERT INTO TMS_LEDGER_DETAIL(LEDGER_ID,FEE_TYPE_ID,FEE_METHOD,UNIT_PRICE,BALANCE_AMOUNT,CREATOR,CREATED_TIME," +
                                    "BILLING_TRAFFIC,REVENUE_COST,INPUT,CONFIRM_DETAIL,BERECONCILED,TAX_RATE,VERSION,DEAL_STATUS,JOIN_BILL_STATUS)VALUES(" +
                                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            Object ddtzmxobj[] = {ydtzid, ydfylxid, "AUTO", 0, ydtzmx.getAmount(), create_name, create_time, 0, 0, ydtzmx.getInput(), 0, 0, ydtzmx.getTaxRate(),
                                    0, "READY", "READY"};
                            boolean is_tzmx = instance.exeQuerSql(insertydtzmx, ddtzmxobj);
                            if (is_tzmx == false) {
                                String message = "您好，应付台账明细插入失败！在该【" + ss_ptName + "】平台下，订单客户单号【" + relatebill1 + "】插入失败";  // 客户类型与客户名称判断
                                Map<String, String> mapfhdh12 = new HashMap<String, String>();
                                mapfhdh12.put("message", message);
                                messages.add(mapfhdh12);
                                fail++;  // 失败一条
                            }
                        }
                    }
                }
            }//订单循环结束
        }
        }
//        String hql ="select a from JC_ORDER a LEFT JOIN a.organization b  LEFT JOIN a.customer c " +
//                " where a.status = 5 and a.relatebill3 !='已同步' and c.description = '三方' and b.id ='"+SessionUtil.getOrgId()+"'";
//        List orders = transportOrderDao.executeQueryHql(hql);
         // if(orders.size()>0){
            //  for (int i = 0; i <orders.size() ; i++) {
                //  Order order = (Order) orders.get(i);
                 // }
//          }else{
//              String message = "您好，没有查询到需要同步的数据！ ";  // 客户类型与客户名称判断
//              Map<String,String> mapfhdh = new HashMap<String, String>();
//              mapfhdh.put("message", message);
//              messages.add(mapfhdh);
//              fail++;  // 失败一条
//          }

        if (fail > sum) {  // 因为不是层级判断，多个字段不符合要求时，避免失败数无限增大
            fail = sum;
        }
        mapz.put("sum", String.valueOf(sum));
        mapz.put("success", String.valueOf(sum-fail));
        mapz.put("fail", String.valueOf(fail));
        mapz.put("messages", messages);
        list.add(mapz);
        return list;
    }

    public JgGridListModel getLedgerdetailsList(CommModel commModel) {
        String where = " and b.id = '" + SessionUtil.getOrgId() + "' and d.amount != null";
          if(commModel.getStatus() != null){
               where +=" and d.is_inoutcome="+commModel.getStatus();
          }else{
              where +=" and d.is_inoutcome=0";
          }
        String orderBy = " order by a.create_Time desc";
        return super.getListByPageToHql(OrderSql.SRQR_LIST, OrderSql.SRQR_COUNT, commModel, where, orderBy);
    }
/**
 * @Author: 孙德增
 * @Date: 2019-08-02
 * @zhushi:收入确认
 * @return void
 **/
    public void affirmOrder(String id,Timestamp date) {
        String[] split = id.split(",");
        for (String splitId : split) {
            LedgerDetail ledgerDetail = (LedgerDetail) ledgerDetailDao.selectBean(splitId);
            ledgerDetail.setIs_inoutcome(1);
             ledgerDetail.setAffirm_Time(date);
                ledgerDetailDao.savaBean(ledgerDetail);
            String hql = "SELECT a.id from JC_ORDER a left join a.ledgerDetails b where b.id ='" + splitId+"'";
            List<Order> ordersList = transportOrderDao.executeQueryHql(hql);
            if(ordersList.size()>0){
                Order order = transportOrderDao.selectBean(ordersList.get(0));
                order.setIs_inoutcome(1);
                transportOrderDao.savaBean(order);
            }
        }

    }
    /**
     * @Author: 孙德增
     * @Date: 2019-08-02
     * @zhushi:反收入确认
     * @return void
     **/

    public void fanaffirmOrder(String id) {
        String[] split = id.split(",");
        for (String splitId : split) {
                Integer sign=0;
                LedgerDetail ledgerDetail= (LedgerDetail) ledgerDetailDao.selectBean(splitId);
                if (ledgerDetail.getIs_inoutcome() == 0) {
                    throw new MessageException("请选择已确认的订单");
                }
                ledgerDetail.setIs_inoutcome(0);
                ledgerDetail.setAffirm_Time(null)   ;
            Order order = ledgerDetail.getOrder();
           // Shipment shipment=ledgerDetail.getShipment();
                List<LedgerDetail> ledgerDetailList=order.getLedgerDetails();
                for(LedgerDetail ledgerDetail1:ledgerDetailList){
                    if(ledgerDetail1.getId()==splitId){
                        continue;
                    }
                    if(ledgerDetail1.getIs_inoutcome()==1){
                        break;
                    }
                    sign++;
                }
                if(sign==ledgerDetailList.size()-1){
                    order.setIs_inoutcome(0);
                }
            }
            //此方法可以使用，相对比较蛮烦
//            LedgerDetail ledgerDetail = (LedgerDetail) ledgerDetailDao.selectBean(splitId);
//            ledgerDetail.setIs_inoutcome(0);
//            ledgerDetail.setAffirm_Time(null);
//            ledgerDetailDao.savaBean(ledgerDetail);
//            String hql = "SELECT a.id from JC_ORDER a left join a.ledgerDetails b where b.id ='" + splitId+"'";
//            List<Order> ordersList = transportOrderDao.executeQueryHql(hql);
//            if(ordersList.size()>0){
//                String hql3 = "SELECT a.is_inoutcome from JC_LEDGER_DETAIL a left join a.order b where b.id='"+ordersList.get(0)+"'";
//                List  list1 = ledgerDetailDao.executeQueryHql(hql3);
//                 if(list1.size()!= split.length){
//                     String hql2 = "SELECT a.is_inoutcome from JC_LEDGER_DETAIL a left join a.order b where b.id='"+ordersList.get(0)+"' and a.id !='"+splitId+"'";
//                     List  list = ledgerDetailDao.executeQueryHql(hql2);
//                     if(list.size()>0){
//                         if(list.contains(1)){
//                         }else{
//                             Order order = transportOrderDao.selectBean(ordersList.get(0));
//                             order.setIs_inoutcome(0);
//                             transportOrderDao.savaBean(order);
//                         }
//                     }else{
//                         Order order = transportOrderDao.selectBean(ordersList.get(0));
//                         order.setIs_inoutcome(0);
//                         transportOrderDao.savaBean(order);
//                     }
//                 }else {
//                     Order order = transportOrderDao.selectBean(ordersList.get(0));
//                     order.setIs_inoutcome(0);
//                     transportOrderDao.savaBean(order);
//                 }
//
//                     }
            }

    /**
     * 核销用
     */
//    public String getOrderLedger(CommModel commModel) {
//        JgGridListModel listByPageToHql=null;
//        String status = commModel.getStatus();
//        String where=null;
//        String orderBy= null;
//        if("weiVerification".equals(commModel.getId())){
//                 where = " and b.id = '" + SessionUtil.getOrgId() + "' and a.status not in(0,1)";
//             listByPageToHql = super.getListByPageToHql(OrderSql.OO_LIST, OrderSql.OO_COUNT, commModel, where, orderBy);
//        }
//        if("bufenVerification".equals(commModel.getId())){
//            listByPageToHql = super.getListByPageToHql(OrderSql.OO_LIST, OrderSql.OO_COUNT, commModel, where, orderBy);
//        }
//        if("yiVerification".equals(commModel.getId())){
//            listByPageToHql = super.getListByPageToHql(OrderSql.OO_LIST, OrderSql.OO_COUNT, commModel, where, orderBy);
//        }
//        return listByPageToHql.toJSONString();
//    }


    private AbnormalDetail getAbnormalDetail(String source, String sourceValue, String targer, String targerValue) {
        AbnormalDetail abnormalDetail = new AbnormalDetail();
        abnormalDetail.setSource(source);
        abnormalDetail.setTarger(targer);
        abnormalDetail.setSourceValue(sourceValue);
        abnormalDetail.setTargerValue(targerValue);
        return abnormalDetail;
    }


    public void addSubJgGird(String id,Boolean shipmentSign) {
        LedgerDetail ledgerDetail = new LedgerDetail();
        ledgerDetail.setCost(0);
        if(shipmentSign){
            Shipment shipment = (Shipment) shipmentDao.selectBean(id);
            ledgerDetail.setShipment(shipment);
        }else{
            Order order=transportOrderDao.selectBean(id);
            ledgerDetail.setOrder(order);
        }
        ledgerDetailDao.savaBean(ledgerDetail);
    }

    public void dellSubJgGird(String id) {
        LedgerDetail ledgerDetail = (LedgerDetail) ledgerDetailDao.selectBean(id);
        Order order = ledgerDetail.getOrder();
        if(order!=null){
            if (order.getStatus() != OrderStatus.ACTIVE.getValue()){
                throw new MessageException("只能删除订单为保存状态的数据");
            }
        }
        Shipment shipment = ledgerDetail.getShipment();
        if(shipment!=null){
            if (shipment.getStatus() != OrderStatus.ACTIVE.getValue()){
                throw new MessageException("只能删除运单为保存状态的数据");
            }
        }
        ledgerDetailDao.executeHql("DELETE FROM JC_LEDGER_DETAIL WHERE ID = '"+ledgerDetail.getId()+"'",null);
    }


    public void editFree(HttpServletRequest request) {
        String id = request.getParameter("id");
        LedgerDetail ledgerDetail = (LedgerDetail) ledgerDetailDao.selectBean(id);

        Order order =ledgerDetail.getOrder();
        Shipment shipment =ledgerDetail.getShipment();
        if(order!=null){
            if (order.getStatus() != OrderStatus.ACTIVE.getValue()){
                throw  new MessageException("编辑状态不对，请联系管理员");
            }
        }
        if(shipment!=null){
            if (shipment.getStatus() != OrderStatus.ACTIVE.getValue()){
                throw  new MessageException("编辑状态不对，请联系管理员");
            }
        }

        if (request.getParameter("feeType.name") != null){
            String parameter = request.getParameter("feeType.name");
            FeeTypeDao feeTypeDao = SpringUtils.getBean(FeeTypeDaoImpl.class);
            FeeType feeType = (FeeType) feeTypeDao.selectBean(parameter);
            ledgerDetail.setFeeType(feeType);
        }

        if (request.getParameter("carrier.name") != null){
            String parameter = request.getParameter("carrier.name");
            CarrierDao carrierDao = SpringUtils.getBean(CarrierDaoImpl.class);
            Carrier carrier = (Carrier) carrierDao.selectBean(parameter);
            ledgerDetail.setCarrier(carrier);
        }

        if (request.getParameter("taxRate") != null){
            String taxRate = request.getParameter("taxRate");
            ledgerDetail.setTaxRate(Double.valueOf(taxRate));
            double v = ledgerDetail.getAmount() / (1 + ledgerDetail.getTaxRate()) * ledgerDetail.getTaxRate();
            ledgerDetail.setInput(MathExtend.round(v, SystemConfigUtils.getMoneyRound()));
        }
        if (request.getParameter("amount") != null){
            String amount = request.getParameter("amount");
            ledgerDetail.setAmount(Double.valueOf(amount));
            double v = ledgerDetail.getAmount() / (1 + ledgerDetail.getTaxRate()) * ledgerDetail.getTaxRate();
            ledgerDetail.setInput(MathExtend.round(v,SystemConfigUtils.getMoneyRound()));
        }
        if(request.getParameter("settlementMethod") != null){
            String settlementMethod = request.getParameter("settlementMethod");
            ledgerDetail.setSettlementMethod(settlementMethod);
        }
        if(request.getParameter("collectMethod") != null){
            String collectMethod = request.getParameter("collectMethod");
            ledgerDetail.setCollectMethod(collectMethod);
        }
        if(request.getParameter("payMethod") != null){
            String payMethod = request.getParameter("payMethod");
            ledgerDetail.setPayMethod(payMethod);
        }


    }




}
