package com.kytms.shipment.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kytms.carrier.dao.CarrierDao;
import com.kytms.carrier.dao.impl.CarrierDaoImpl;
import com.kytms.core.apportionment.dao.ApportionmentDao;
import com.kytms.core.constants.Entity;
import com.kytms.core.constants.Symbol;
import com.kytms.core.entity.*;
import com.kytms.core.exception.AppBugException;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.*;
import com.kytms.feetype.dao.FeeTypeDao;
import com.kytms.inOrOutRecord.dao.InOrOutRecordDao;
import com.kytms.ledgerdetails.dao.LedgerDetailDao;
import com.kytms.orderabnormal.dao.AbnormalDao;
import com.kytms.orderabnormal.dao.AbnormalDetailDao;
import com.kytms.orderabnormal.service.AbnormalService;
import com.kytms.organization.dao.OrgDao;
import com.kytms.shipment.ShipmentSql;
import com.kytms.shipment.dao.BerthStandDao;
import com.kytms.shipment.dao.Impl.ShipmentDaoImpl;
import com.kytms.shipment.dao.ShipmentDao;
import com.kytms.shipment.outLedgerSql;
import com.kytms.shipment.service.ShipmentService;
import com.kytms.shipmenttrack.ShipmentTrackUtil;
import com.kytms.transportorder.OrderStatus;
import com.kytms.transportorder.OrderTrackUtil;
import com.kytms.transportorder.dao.*;
import com.kytms.transportorder.dao.impl.LedProductDaoImpl;
import com.kytms.transportorder.dao.impl.LedRPDaoImpl;
import com.kytms.verification.dao.VerificationDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/8.
 */
@Service(value = "ShipmentService")
public class ShipmentServiceImpl extends BaseServiceImpl<Shipment> implements ShipmentService<Shipment> {
    private final Logger log = Logger.getLogger(ShipmentServiceImpl.class);//输出Log日志
    private ShipmentDao<Shipment> shipmentDao;
    private LedgerDetailDao<LedgerDetail> ledgerDetailDao;
    private LedDao ledDao;
    private InOrOutRecordDao<InOrOutRecord> inOrOutRecordDao;
    private OrderRPDao orderRPDao;
    private LedRPDao ledRPDao;
    private LedProductDao ledProductDao;
    private BerthStandDao berthStandDao;
    private OrgDao orgDao;
    private TransportOrderDao<Order> transportOrderDao;
    private VerificationDao<VerificationZb> verificationDao;
    private ApportionmentDao apportionmentDao;


    @Resource(name = "ApportionmentDao")
    public void setApportionmentDao(ApportionmentDao apportionmentDao) {
        this.apportionmentDao = apportionmentDao;
    }

    @Resource(name = "LedRPDao")
    public void setLedRPDao(LedRPDao ledRPDao) {
        this.ledRPDao = ledRPDao;
    }
    @Resource(name = "LedProductDao")
    public void setLedProductDao(LedProductDao ledProductDao) {
        this.ledProductDao = ledProductDao;
    }

    @Resource(name ="VerificationDao")
    public void setVerificationDao(VerificationDao<VerificationZb> verificationDao) {
        this.verificationDao = verificationDao;
    }

    @Resource(name ="TransportOrderDao")
    public void setTransportOrderDao(TransportOrderDao<Order> transportOrderDao) {
        this.transportOrderDao = transportOrderDao;
    }
    private AbnormalDetailDao abnormalDetailDao;
    private AbnormalService abnormalService;
    private AbnormalDao abnormalDao;
    private FeeTypeDao feeTypeDao;
    private CarrierDao carrierDao;


    @Resource(name = "CarrierDao")
    public void setCarrierDao(CarrierDao carrierDao) {
        this.carrierDao = carrierDao;
    }

    @Resource(name = "FeeTypeDao")
    public void setFeeTypeDao(FeeTypeDao feeTypeDao) {
        this.feeTypeDao = feeTypeDao;
    }

    @Resource(name = "AbnormalDetailDao")
    public void setAbnormalDetailDao(AbnormalDetailDao abnormalDetailDao) {
        this.abnormalDetailDao = abnormalDetailDao;
    }
    @Resource(name = "AbnormalService")
    public void setAbnormalService(AbnormalService abnormalService) {
        this.abnormalService = abnormalService;
    }
    @Resource(name = "AbnormalDao")
    public void setAbnormalDao(AbnormalDao abnormalDao) {
        this.abnormalDao = abnormalDao;
    }

    @Resource(name = "InOrOutRecordDao")
    public void setInOrOutRecordDao(InOrOutRecordDao<InOrOutRecord> inOrOutRecordDao) {
        this.inOrOutRecordDao = inOrOutRecordDao;
    }




    @Resource(name = "OrgDao")
    public void setOrgDao(OrgDao orgDao) {
        this.orgDao = orgDao;
    }

    @Resource(name = "OrderRPDao")
    public void setOrderRPDao(OrderRPDao orderRPDao) {
        this.orderRPDao = orderRPDao;
    }

    @Resource(name = "BerthStandDao")
    public void setBerthStandDao(BerthStandDao berthStandDao) {
        this.berthStandDao = berthStandDao;
    }

    @Resource(name = "LedgerDetailDao")
    public void setLedgerDetailDao(LedgerDetailDao<LedgerDetail> ledgerDetailDao) {
        this.ledgerDetailDao = ledgerDetailDao;
    }

    @Resource(name = "ShipmentDao")
    public void setShipmentDao(ShipmentDao<Shipment> shipmentDao) {
        this.shipmentDao = shipmentDao;
        super.setBaseDao(shipmentDao);
    }

    @Resource(name = "LedDao")
    public void setLedDao(LedDao ledDao) {
        this.ledDao = ledDao;
    }

    public JgGridListModel getShipmentList(CommModel commModel) {
        String status = commModel.getStatus();
        String where = " and org.id = '" + SessionUtil.getOrgId() + "'";
        if (status != null) {
            where += " and ship.status = " + status;
        } else {
            where += "and (ship.status != 0 and ship.status != 99)";
        }
        String orderBy = " group by ship.id  order by ship.create_Time desc";
        return super.getListByPageToHql(ShipmentSql.SHIPMENT_LIST, ShipmentSql.SHIPMENT_COUNT, commModel, where, orderBy);
    }

    /**
     * 保存运单
     * @param shipment
     * @return
     */
    public Shipment saveShipment(Shipment shipment) {
        Shipment oldShipment = new Shipment();
        shipment.setOrganization(SessionUtil.getOrg());//绑定组织机构
        if (StringUtils.isEmpty(shipment.getId())) { // 如果是空 说明是新建设置
            shipment.setCode(super.getShipmentCode());
        } else {
            oldShipment = shipmentDao.selectBean(shipment.getId());//查出原始数据
        }
//        List<Ledger> ledgers = shipment.getLedgers();//获取台账信息
//        shipment.setLedgers(null);
        //处理合同是否超期
        CarrierDaoImpl carrierDao = SpringUtils.getBean(CarrierDaoImpl.class);
        if (shipment.getCarrier() != null) {
            Carrier carrier = carrierDao.selectBean(shipment.getCarrier().getId());
            Timestamp startTime = carrier.getStartTime();
            Timestamp endTime = carrier.getEndTime();
            Timestamp timestamp = shipment.getTime();
            boolean b = DateUtils.belongCalendar(timestamp, startTime, endTime);
            if (b) {
                shipment.setCarriageIsExceed(Entity.UNACTIVE);//状态0 超期
            } else {
                shipment.setCarriageIsExceed(Entity.ACTIVE);// 状态1 不超期
            }
        }else{
            shipment.setCarriageIsExceed(0);//临时承运商，不计算是否超期
            shipment.setCarrierType(0);
        }
        //处理关联信息
        if (StringUtils.isNotEmpty(shipment.getId())) { //
            Shipment query = shipmentDao.selectBean(shipment.getId());
            if (query.getLeds() != null) {
                shipment.setLeds(query.getLeds());
            }
        }
        shipment.setOrderCode(oldShipment.getOrderCode());
        shipment.setRelatebill1(oldShipment.getRelatebill1());
        shipment.setRefuse(0);
        shipment.setNewOrganization(shipment.getFromOrganization());
        shipment.setCarrierType(shipment.getCarrierType());
        List<LedgerDetail> ledgerDetails = shipment.getLedgerDetails();//获取台账明细
        shipment.setLedgerDetails(null);//阻断关系
        Shipment shipment1 = shipmentDao.savaBean(shipment);
        //处理经由站
        String orgIds = shipment1.getOrgIds();
        berthStandDao.executeHql("delete from JC_BERTH_STAND where shipment.id = '"+shipment1.getId()+"'",null);
        List<String> sp = new ArrayList<String>();  // 站点不一致验证
        sp.add(shipment.getFromOrganization().getId());
        sp.add(shipment.getToOrganization().getId());
        if(StringUtils.isNotEmpty(orgIds)){
            String[] split = orgIds.split(Symbol.COMMA);
            shipment1.setBerthStand(null);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <split.length ; i++) {
                String s = split[i];
                sp.add(s);
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
                sb.append(org.getName()).append("-");
                berthStandDao.savaBean(berthStand);
            }
            sb.deleteCharAt(sb.lastIndexOf("-"));
            shipment1.setOrgNameList(sb.toString());
        }
        Set<String> set = new HashSet<String>();
        for (String s:sp) {
            set.add(s);
        }
        if(set.size() != sp.size()){
            throw new MessageException("线路站点不能重复");
        }


        //处理运单跟踪
        if (StringUtils.isEmpty(shipment.getId())) { // 如果是空 说明是新建设置
            ShipmentTrackUtil.addTrack(shipment1, "运单已经被保存 运单号:" + shipment1.getCode());
        } else {
            ShipmentTrackUtil.addTrack(shipment1, "运单已经被修改");
        }
        shipmentDao.executeHql(" Delete FROM JC_LEDGER_DETAIL Where shipment.id = '" + shipment1.getId() + "'", null);


            for (LedgerDetail ledgerDetail : ledgerDetails) {
                double amount = ledgerDetail.getAmount();
                if(amount != 0){
                    ledgerDetail.setShipment(shipment1);
                    ledgerDetail.setType(1);
                    ledgerDetail.setCost(0);
                    ledgerDetailDao.savaBean(ledgerDetail);
                }
            }
        return shipment1;

    }

    /**
     * 配载分段订单
     * @param ledId
     * @param shipment
     */
    public void addLed(String ledId, String shipment) {
        Shipment shipment1 = shipmentDao.selectBean(shipment);
        List<String> ledIds = new ArrayList<String>();
        //拆单规则
        List<Led> leds = JSONArray.parseArray(ledId, Led.class);
        //数据验证
        for (Led led : leds) {
            Led l = (Led) ledDao.selectBean(led.getId()); //原始数据
            List<LedProduct> ledProducts = l.getLedProducts();
            if (l.getNumber()-led.getNumber() > 0.0) { //根据数量判断是否拆分
                l.getOrder().setIsTake(1);
                //拆分验证
                LedProductDao ledProductDao = SpringUtils.getBean(LedProductDaoImpl.class);
                LedRPDao ledRPDao = SpringUtils.getBean(LedRPDaoImpl.class);
                if (ledProducts.size() > 1) {
                    throw new RuntimeException("快速拆分只支持一条货品明细");
                }
                if (l.getWeight() < led.getWeight()) {
                    throw new RuntimeException("拆分重量不能大于被拆分单据总重量");
                }
                if (l.getNumber() < led.getNumber()) {
                    throw new RuntimeException("拆分数量不能大于被拆分单据总数量");
                }
                if (l.getVolume() < led.getVolume()) {
                    throw new RuntimeException("拆分体积不能大于被拆分单据总体积");
                }
                if (l.getValue() < led.getValue()) {
                    throw new RuntimeException("拆分货值不能大于被拆分单据总货值");
                }
                if (led.getValue() < 0 || led.getVolume() < 0 || led.getNumber() < 0 || led.getWeight() < 0) {
                    throw new RuntimeException("拆分值不能是负数");
                }

                //开始减去拆分数据
                l.setNumber(l.getNumber() - led.getNumber());
                l.setWeight(l.getWeight() - led.getWeight());
                l.setVolume(l.getVolume() - led.getVolume());
                l.setValue(l.getValue() - led.getValue());
                if(led.getJzWeight()!=null){
                    l.setJzWeight(l.getJzWeight()-led.getJzWeight());
                }else{
                    l.setJzWeight(l.getJzWeight()-0.0);
                }
                //处理货品明细
                LedProduct ledProduct = ledProducts.get(0);
                ledProduct.setWeight(ledProduct.getWeight() - led.getWeight());
                ledProduct.setNumber(ledProduct.getNumber()-led.getNumber());
                ledProduct.setVolume(ledProduct.getVolume() - led.getVolume());
                ledProduct.setValue(ledProduct.getValue() - led.getValue());
                if(led.getJzWeight() != null){
                    ledProduct.setJzWeight(ledProduct.getJzWeight() - led.getJzWeight());
                }else{
                    ledProduct.setJzWeight(ledProduct.getJzWeight() - 0.0);
                }

                //克隆对象
                Led cloneObj = (Led) ObjectUtil.cloneObject(l, Led.class);
                cloneObj.setId(null);//处理克隆对象
                cloneObj.setNumber(led.getNumber());
                cloneObj.setWeight(led.getWeight());
                cloneObj.setValue(led.getValue());
                cloneObj.setVolume(led.getVolume());
              // cloneObj.setJzWeight(0.00);
                List<LedProduct> ledProducts1 = cloneObj.getLedProducts();
                LedProduct ledProduct1 = ledProducts1.get(0);
                ledProduct1.setNumber(led.getNumber());
                ledProduct1.setWeight(led.getWeight());
                ledProduct1.setValue(led.getValue());
                ledProduct1.setVolume(led.getVolume());
                //ledProduct1.setJzWeight(led.getJzWeight());
                ledProduct1.setId(null);
                //处理分段运单单号
                String code = cloneObj.getCode();
                long count = ledDao.selectCount(null, " and order.id ='" + l.getOrder().getId() + "'", null);
                String subas = code.substring(0, code.indexOf("-"));
                String newCode = subas + "-" + (count + 1);
                cloneObj.setCode(newCode);
                cloneObj.setBackNumber(0);
                cloneObj.setAgent(0.0);
                cloneObj.setAgent(0.0);
                List<LedReceivingParty> ledReceivingParties = cloneObj.getLedReceivingParties();
                //阻断关系
                cloneObj.setLedProducts(null);
                cloneObj.setLedReceivingParties(null);
                Led returnLed = (Led) ledDao.savaBean(cloneObj);
                ledIds.add(returnLed.getId());
                for (LedReceivingParty ledReceivingPartie : ledReceivingParties) {
                    ledReceivingPartie.setLed(returnLed);
                    ledReceivingPartie.setId(null);
                    ledRPDao.savaBean(ledReceivingPartie);
                }
                for (LedProduct cloneLedProduct : ledProducts1) {
                    cloneLedProduct.setLed(returnLed);
                    cloneLedProduct.setId(null);
                    ledProductDao.savaBean(cloneLedProduct);
                }
            } else {
                ledIds.add(l.getId());
            }
        }
        for (String id : ledIds) {
            Led led = (Led) ledDao.selectBean(id);
            if (led.getStatus() == OrderStatus.STOWAGE.getValue()) {
                throw new MessageException("分段订单号:" + led.getCode() + "已经被其他运单配载,请重新选择");
            }
            led.setStatus(OrderStatus.STOWAGE.getValue()); //修改分段订单状态
            led.getOrder().setStatus(OrderStatus.STOWAGE.getValue()); //修改订单状态
            led.setShipment(shipment1);
            if (shipment1.getLeds() == null) {
                shipment1.setLeds(new ArrayList<Led>());
            }
            shipment1.getLeds().add(led);
            ShipmentTrackUtil.addTrack(shipment1, "运单已经配载分段订单 分段订单号:" + led.getCode() + "   配载货品数量:" + led.getNumber() + "  重量:" + led.getWeight() + "  体积:" + led.getVolume() + "  货值:" + led.getValue());
           // OrderTrackUtil.addTrack(led.getOrder(),"订单已经装车 分段订单号:" + led.getCode() + "   卸载货品数量:" + led.getNumber() + "  重量:" + led.getWeight() + "  体积:" + led.getVolume() + "  货值:" + led.getValue());
        }
        //运单更新客户订单号
        costCustomerNum(shipment1);
    }

    /**
     * 卸载分段订单
     * @param ledId
     * @param shipment
     */
    public void delLed(String ledId, String shipment) {
        String[] ledIds = ledId.split(Symbol.COMMA);
        Shipment shipment1 = shipmentDao.selectBean(shipment);
        String hql = "FROM JC_LED a left join a.shipments b WHERE  b.id = '" + shipment + "' and a.id not in(" + SqlUtils.splitForIn(ledIds) + ") ";
        List<Object> objs = ledDao.executeQueryHql(hql);
        List<Led> list = new ArrayList<Led>();
        for (Object object : objs) {
            Object[] obj = (Object[]) object;
            for (Object o : obj) {
                if (o instanceof Led) {
                    Led led = (Led) o;
                    list.add(led);
                }
            }
        }
        shipment1.setLeds(list); //原卸载逻辑
       // shipment1.setLedsss(list);
        for (String id : ledIds) {
            Led led = (Led) ledDao.selectBean(id);
            led.setShipment(null);
            String id1 = led.getOrder().getOrganization().getId();
            if(id1.equals(SessionUtil.getOrgId())){
                led.setStatus(OrderStatus.CONFIGRM.getValue());
            }else {
                led.setStatus(11);
            }
            Order order = led.getOrder();
            List<Led> leds = order.getLeds();
            //合并分段单据
            String hql2 = "select a from JC_LED a left join a.order b left join a.shipment c left join a.organization d where b.id='" + order.getId() + "' and c.id is null and a.id != '" + id + "' and d.id = '"+SessionUtil.getOrgId()+"'";
            List<Led> ledcs = ledDao.executeQueryHql(hql2);
            if (ledcs.size() > 0) { //说明有未配载单据 需合并
                LedProductDao ledProductDao = SpringUtils.getBean(LedProductDaoImpl.class);
                LedRPDao ledRPDao = SpringUtils.getBean(LedRPDaoImpl.class);
                Led notLed = ledcs.get(0);
                notLed.setNumber(notLed.getNumber() + led.getNumber());
                notLed.setWeight(notLed.getWeight() + led.getWeight());
                notLed.setVolume(notLed.getVolume() + led.getVolume());
                notLed.setValue(notLed.getValue() + led.getValue());
                notLed.setBackNumber(notLed.getBackNumber() + led.getBackNumber());
                //合并货品明细
                List<LedProduct> ledProducts = led.getLedProducts();
                for (LedProduct ledProduct : ledProducts) { //循环装在货品明细
                    List<LedProduct> notLedProducts = ledProductDao.executeQueryHql("FROM JC_LEG_PRODUCT where led.id = '" + notLed.getId() + "' and unit ='" + ledProduct.getUnit() + "' and name='" + ledProduct.getName() + "'");
                    if (notLedProducts.size() > 0) { //有相同货品
                        LedProduct notLedProduct = notLedProducts.get(0);
                        notLedProduct.setNumber(notLedProduct.getNumber() + ledProduct.getNumber());
                        notLedProduct.setWeight(notLedProduct.getWeight() + ledProduct.getWeight());
                        notLedProduct.setVolume(notLedProduct.getVolume() + ledProduct.getVolume());
                        notLedProduct.setValue(notLedProduct.getValue() + ledProduct.getValue());
                        ledProductDao.deleteBean(ledProduct);
                    } else {
                        ledProduct.setLed(notLed);
                    }
                }
                //删除合并后分段单据
                List<LedReceivingParty> ledReceivingParties = led.getLedReceivingParties();
                for (LedReceivingParty ledReceivingParty : ledReceivingParties) {
                    ledRPDao.deleteBean(ledReceivingParty);
                }
                ledDao.deleteBean(led);
            }

            List<Led> leed = ledDao.executeQueryHql("From JC_LED where order.id='" + order.getId() + "'"); //合并后 分段运单号修正
            if (leed.size() == 1) {
                Led led1 = leed.get(0);
                led1.getOrder().setIsTake(0); //回复未拆分状态
                String code = led1.getCode();
                String subas = code.substring(0, code.indexOf("-"));
                String newCode = subas + "-" + 1;
                led1.setCode(newCode);
            }

            boolean s = true;
            for (Led led1 : leds) {
                if (led1.getStatus() != OrderStatus.CONFIGRM.getValue()) {
                    s = false;
                }
            }
            if (s) {
                order.setStatus(OrderStatus.CONFIGRM.getValue());
            }
            //处理运单追踪
            ShipmentTrackUtil.addTrack(shipment1, "运单已经卸载分段订单 分段订单号:" + led.getCode() + "   卸载货品数量:" + led.getNumber() + "  重量:" + led.getWeight() + "  体积:" + led.getVolume() + "  货值:" + led.getValue());
          //  OrderTrackUtil.addTrack(order,"订单已经被卸载 分段订单号:" + led.getCode() + "   卸载货品数量:" + led.getNumber() + "  重量:" + led.getWeight() + "  体积:" + led.getVolume() + "  货值:" + led.getValue());
            //运单更新客户订单号
            costCustomerNum(shipment1);
        }
    }

    public List getAddressInfo(String id) {
        String hql = "SELECT c.id,c.type,b.code,c.name,d.name,c.contactperson,c.iphone,c.address,c.planLeaveTime,c.factLeaveTime,c.ltl FROM JC_SHIPMENT a LEFT JOIN a.leds b LEFT JOIN b.ledReceivingParties c LEFT JOIN c.zone d WHERE a.id = '" + id + "' order by c.type,b.code,c,orderBy";
        List<Shipment> shipments = shipmentDao.executeQueryHql(hql);
        return shipments;
    }
    /**
     * 运单确认
     * @param id
     * @return
     * @throws MessageException
     */
    public Shipment confirmShipment(String id) throws MessageException {
        Shipment shipment = shipmentDao.selectBean(id);

        if (shipment.getStatus() != 1) {
            throw new MessageException("只能确认保存状态下的运单");
        }
        //判定超期
        if(shipment.getCarrier() != null && shipment.getCarrierType() ==1) {
            Timestamp startTime = shipment.getCarrier().getStartTime();
            Timestamp endTime = shipment.getCarrier().getEndTime();
            Timestamp df = new Timestamp(System.currentTimeMillis());
            boolean b = DateUtils.belongCalendar(df, startTime, endTime);
            if (shipment.getOrganization().getIsOverdueCarrier() == 0) {
                if (b == false) {
                    throw new MessageException("承运商超期不能确认");
                }else{
                    shipment.setCarriageIsExceed(1);
                }
            }
        }
            if (shipment.getLeds() == null || shipment.getLeds().size() == 0) {
                throw new MessageException("运单未配载不能确认");
            }
            shipment.setStatus(OrderStatus.CONFIGRM.getValue());//修改运单状态
            //处理装卸信息;
            List<Led> leds = shipment.getLeds();
            int z = 0;
            int x = 0;
            Integer aaa = shipment.getRefuse();
            Double number =0.00;
            Double volume = 0.00;
            Double weight= 0.00;
            Double jz = 0.00;
            for (Led led : leds) {
                //处理在途跟踪
                //int aaa= shipment.getRefuse();
                if (aaa == 0) {//如果被拒收过
                    Order order = led.getOrder();
                    OrderTrackUtil.addTrack(order, "分段订单:" + led.getCode() + "   已被配载，等待发运…… 操作人:" + SessionUtil.getUserName());
                }
               number += led.getNumber();
               volume += led.getVolume();
               weight += led.getWeight();
               jz += led.getJzWeight();
            }
         shipment.setNumber(number);
         shipment.setVolume(volume);
         shipment.setWeight(weight);
         shipment.setJzWeight(jz);
        List<LedgerDetail> ledgerDetails = shipment.getLedgerDetails();
        Double amount =0.0;
        Double input = 0.0;
        for (LedgerDetail ledgerDetail:ledgerDetails) {
            amount += ledgerDetail.getAmount();
            input  += ledgerDetail.getInput();
        }
        VerificationZb verificationZb = new VerificationZb();
             verificationZb.setShipment(shipment);
             verificationZb.setOrganization(SessionUtil.getOrg());
             verificationZb.setHxStatus(0);
             verificationZb.setWhxMoney(amount);
             verificationZb.setzInput(input);
             verificationZb.setzMoney(amount);
             verificationZb.setTzSource(0);
             verificationZb.setType(1);
             verificationZb.setHxMoney(0.0);
             verificationDao.savaBean(verificationZb);
        //shipment.setLoadingInfo(z + "装/" + x + "卸");
        //处理运单的数量，重量，体积
//        String hql = "SELECT SUM(c.number),SUM(c.weight),SUM(c.volume),SUM(c.value)  FROM JC_SHIPMENT a LEFT JOIN a.leds b LEFT JOIN b.ledProducts c  WHERE a.id = '" + id + "'";
//        List list = dispatchDao.executeQueryHql(hql);
//        Object[] o = (Object[]) list.get(0);
//        shipment.setNumber((Double) o[0]);
//        shipment.setWeight((Double) o[1]);
//        shipment.setVolume((Double) o[2]);
//        shipment.setValue((Double) o[3]);
//
//        //处理回单份数
//        String backHql = "SELECT SUM(c.backNumber)  FROM JC_SHIPMENT a LEFT JOIN a.leds b LEFT JOIN b.order c WHERE a.id = '" + id + "' GROUP BY c.id";
//        List backList = dispatchDao.executeQueryHql(backHql);
//        Long backNumbaer = (Long) backList.get(0);
//        int i = backNumbaer.intValue();
//        shipment.setBackNumber(i);
            //处理运单追踪
            if (shipment.getRefuse() == 0) { //如果被拒收过
                ShipmentTrackUtil.addTrack(shipment, "运单已经确认 运单货品数量:" + shipment.getNumber() + "  重量:" + shipment.getWeight() + "  体积:" + shipment.getVolume() + "  货值:" + shipment.getValue());
            }
            return shipment;
    }

    /**
     * 发运操作
     * */
    public void updateRegTime(String id, Timestamp date, String shipment) {
        Shipment shipment1 = shipmentDao.selectBean(id);//修改运单状态
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Organization org =SessionUtil.getOrg();
//        if(shipment1.getOperationPattern() == 0){// 运作模式是自营
////           if(!shipment1.getNewOrganization().getName().equals(org.getName())){
////            throw new MessageException("当前站与登陆机构不一致，不能操作发运");
////        }else
//            if(shipment1.getNewOrganization().getName().equals(shipment1.getToOrganization().getName())){
//            throw new MessageException("该票运单已经到达终点站，不能继续操作发运");
//        }
        int status = shipment1.getStatus();
        if (status == 0 || status == 1) {
            throw new AppBugException("运单状态异常 不能发运");
        }
        shipment1.setStatus(4);
        shipment1.setFactLeaveTime(date);
        Organization newOrg = shipment1.getNewOrganization(); //获取当前站
        Organization fromOrg = shipment1.getFromOrganization();//获取起始站
        List<BerthStand> berthStands=shipment1.getBerthStand();
        if(berthStands == null || berthStands.size() == 0){
            shipment1.setNextOrganization(shipment1.getToOrganization());
        }else {
            boolean panding = true;
            for (BerthStand berthStand:berthStands) {
                if(berthStand.getIsArrive() == 0){
                    shipment1.setNextOrganization(berthStand.getOrganization());
                    panding = false;
                    break;
                }
            }
            if(panding){
                shipment1.setNextOrganization(shipment1.getToOrganization());
            }
        }
        ShipmentTrackUtil.addTrack(shipment1, "运单已发运 正在前下一站："+shipment1.getNewOrganization().getName());
        List<Led> leds= shipment1.getLeds();
        for (Led led:leds) {
            led.setStatus(4); //修改分段运单状态
            led.setFactLeaveTime(date);
            int isBack = led.getOrder().getIsTake(); //是否拆单
            if(isBack == 1){
                OrderTrackUtil.addTrack(led.getOrder(), "您的订单已被拆分:拆分单号" + led.getCode() + " 已发往目的站:"+shipment1.getToOrganization().getName() + "  货品 数量:"+led.getNumber() + " 重量:"+led.getWeight()+" 体积:"+led.getVolume());
            }else {

                    OrderTrackUtil.addTrack(led.getOrder(), "已发往目的站:"+shipment1.getNextOrganization().getName() + "  货品 数量:"+led.getNumber() + " 重量:"+led.getWeight()+" 体积:"+led.getVolume());
            }
           Led led1 = (Led) ledDao.savaBean(led);
           Order orders=  led1.getOrder();
           String hql="select a.status from JC_LED a left join a.order b  where a.status !=88 and  b.id='"+orders.getId()+"'";
           ArrayList leds1 = (ArrayList) ledDao.executeQueryHql(hql, null);
            if(leds1.size()>=1){
                for (Object led2:leds1) {
                    String integer = led2.toString();
                    int aa= Integer.valueOf(integer);
                    orders.setStatus(4);
                    orders.setFactLeaveTime(date);
                }
            }
            orders.setFactLeaveTime(date);
            InOrOutRecord inOrOutRecord = new InOrOutRecord();
            inOrOutRecord.setTime(timestamp);
            inOrOutRecord.setOrganization(SessionUtil.getOrg());
            inOrOutRecord.setWeight(led.getWeight());
            inOrOutRecord.setVolume(led.getVolume());
            inOrOutRecord.setNumber(led.getNumber());
            inOrOutRecord.setType(1);
            inOrOutRecord.setLed(led1);
            inOrOutRecord.setZoneStoreroom(orders.getZoneStoreroom());
            inOrOutRecordDao.savaBean(inOrOutRecord);
        }
    }
   public  void updateYDTime(String id, Timestamp date, String shipment){
       Shipment shipment1 = shipmentDao.selectBean(id);//修改运单状态
       Timestamp timestamp = new Timestamp(System.currentTimeMillis());
       Organization org =SessionUtil.getOrg();
       if(shipment1.getOperationPattern() == 0){// 运作模式是自营
           int status = shipment1.getStatus();
           if (status !=4) {
               throw new AppBugException("运单不是发运状态，不能操作运抵");
           }
           shipment1.setStatus(5);
           shipment1.setFactArriveTime(date);
       }
       shipment1.setStatus(5);
       shipment1.setFactArriveTime(date);
       List<Led> leds= shipment1.getLeds();
       for (Led led:leds) {
           led.setStatus(5); //修改分段运单状态
           led.setFactArriveTime(date);
           int isBack = led.getOrder().getIsTake(); //是否拆单
           if(isBack == 1){
               OrderTrackUtil.addTrack(led.getOrder(), "您的订单已被拆分:拆分单号" + led.getCode() + " 已发往目的站:"+shipment1.getToOrganization().getName() + "  货品 数量:"+led.getNumber() + " 重量:"+led.getWeight()+" 体积:"+led.getVolume());
           }else {
                   OrderTrackUtil.addTrack(led.getOrder(), "已到达目的站:"+shipment1.getNextOrganization().getName() + "  货品 数量:"+led.getNumber() + " 重量:"+led.getWeight()+" 体积:"+led.getVolume());
           }
           Led led1 = (Led) ledDao.savaBean(led);
           Order orders=  led1.getOrder();
           String hql="select a.status from JC_LED a left join a.order b  where a.status !=88 and  b.id='"+orders.getId()+"'";
           ArrayList leds1 = (ArrayList) ledDao.executeQueryHql(hql, null);
           if(leds1.size()>1){
               for (Object led2:leds1) {
                   String integer = led2.toString();
                   int aa= Integer.valueOf(integer);
                       orders.setStatus(5);
                       orders.setFactArriveTime(date);
               }
           }else{
                   orders.setStatus(5);
                   orders.setFactArriveTime(date);
           }
           orders.setFactArriveTime(date);
       }
   }



    /**
     * 订单运抵确认
     */
    public void orderArrive(Order order) {
        //拆单处理方式
//        String hql3 = "SELECT COUNT(*) FROM JC_ORDER a LEFT JOIN a.leds b LEFT JOIN b.shipments c WHERE a.id ='"+order.getId()+"'";
//        Object objNumber = shipmentDao.executeQueryHql(hql3).get(0);
//        Long shipmentNumber = (Long) objNumber; //拿到运单的数量
//        String hql4 = "SELECT COUNT(*) FROM JC_ORDER a LEFT JOIN a.leds b LEFT JOIN b.shipments c WHERE a.id ='"+order.getId()+"' and c.status=5";
//        Object objNumber2 = shipmentDao.executeQueryHql(hql4).get(0);
//        Long shipmentArrive = (Long) objNumber2; //拿到运单运抵的数量
//        if(shipmentNumber.longValue() == shipmentArrive.longValue()){//如果运单全部运抵
//            order.setStatus(OrderStatus.ARRIVE.getValue());
//        }
        //不拆单处理方式 如果该订单的所有收货方都运抵 系统运抵

        String hql = " FROM JC_ORDER_RECEIVINGPARTY where type =1 and order.id = '" + order.getId() + "' and factLeaveTime is null";
        List<OrderReceivingParty> orderReceivingParties = orderRPDao.executeQueryHql(hql);
        if (orderReceivingParties.size() == 0) { //全部发运
            order.setStatus(OrderStatus.ARRIVE.getValue());
        }
//        List<OrderReceivingParty> orderReceivingParties = order.getOrderReceivingParties();
//        int number = 0; //计数器
//        for (OrderReceivingParty orderReceivingParty: orderReceivingParties) {
//            if (orderReceivingParty.getType() == 1 && orderReceivingParty.getFactLeaveTime() != null){
//                number++;
//            }
//        }
//        if (number == 0 ){ //全部发运
//            order.setStatus(OrderStatus.ARRIVE.getValue());
//        }
    }

    public Shipment saveAbnormalShipment(Shipment newShipment, String text) {
        //yichang
        Shipment oldShipment = shipmentDao.selectBean(newShipment.getId());//查出原始数据
        int status = oldShipment.getStatus();
        boolean s =false;
        if(status == 0 || status == 1){
            s = true;
        }
        if(s){
            throw new MessageException("不能异常修改");
        }
        if(newShipment.getOperationPattern() == 1){
              if(newShipment.getStatus() ==4){
                  oldShipment.setStatus(5);
                  List<Led> ledsss = oldShipment.getLedsss();
                  for (Led led:ledsss) {
                      Order order = led.getOrder();
                          order.setStatus(5);
                  }
              }
        }
        Abnormal abnormal = new Abnormal();
        abnormal.setOrganization(SessionUtil.getOrg());
        List<AbnormalDetail> abnormalList = abnormalService.getAbnormalList(oldShipment, newShipment);
        if(newShipment.getStatus() == 2 || newShipment.getStatus() ==4){
          if(!oldShipment.getToOrganization().equals(newShipment.getToOrganization())){
              Organization o = (Organization) orgDao.selectBean(oldShipment.getToOrganization().getId());
              Organization o1 = (Organization) orgDao.selectBean(newShipment.getToOrganization().getId());
              abnormalList.add(getAbnormalDetail("organization",o.getName(),"目的网点",o1.getName()));
              oldShipment.setToOrganization(newShipment.getToOrganization());
              if(oldShipment.getNewOrganization().getId().equals(oldShipment.getToOrganization().getId())){
                  oldShipment.setNextOrganization(null);
              }else{
                  oldShipment.setNextOrganization(newShipment.getToOrganization());
              }
          }

        }

        //处理费用明细
        List<VerificationZb> verifications = oldShipment.getVerifications(); //获得核销总表
        List<LedgerDetail> ledgerDetails = newShipment.getLedgerDetails();
        List<LedgerDetail> ledgerDetails1 = oldShipment.getLedgerDetails();
            Double oldamount=0.0;
            Double oldTax=0.0;
            double oldoilPrepaid = 0.0;
             Double oldcashAdvances =0.0;
        Double oldinput =0.0;
        Double oldtaxRate =0.0;
//        for (LedgerDetail ledgerDetail2:ledgerDetails1) {
//            oldamount = ledgerDetail2.getAmount();
//            oldcashAdvances = ledgerDetail2.getCashAdvances();
//            oldoilPrepaid = ledgerDetail2.getOilPrepaid();
//            oldinput = ledgerDetail2.getInput();
//            oldtaxRate = ledgerDetail2.getTaxRate();
            for (LedgerDetail ledgerDetail : ledgerDetails) {
                Double amount = ledgerDetail.getAmount();
                Double oilPrepaid = ledgerDetail.getOilPrepaid() == null ? 0 : ledgerDetail.getOilPrepaid();//新预付油卡
                Double cashAdvances = ledgerDetail.getCashAdvances() == null ? 0 : ledgerDetail.getCashAdvances();//新预付现金
                Double input = ledgerDetail.getInput() == null ? 0 : ledgerDetail.getInput();
                Double taxRate = ledgerDetail.getTaxRate() == null ? 0 : ledgerDetail.getTaxRate();
                if (amount < (oilPrepaid.doubleValue() + cashAdvances.doubleValue()) && amount >=0.0) {
                    throw new MessageException("预付油卡和预付现金，加一起不能超过费用金额");
                }
                FeeType feeType = (FeeType) feeTypeDao.selectBean(ledgerDetail.getFeeType().getId());
                Carrier carrier =(Carrier) carrierDao.selectBean(ledgerDetail.getCarrier().getId());
                if (StringUtils.isEmpty(ledgerDetail.getId())) {
                    abnormalList.add(getAbnormalDetail("调整费用-" + feeType.getName(), "0", "调整费用-" + feeType.getName() + "  税率:" + ledgerDetail.getTaxRate(), ledgerDetail.getAmount().toString()));
                    abnormalList.add(getAbnormalDetail("承运商","无","承运商",carrier.getName()));
                    ledgerDetail.setShipment(oldShipment);
                    VerificationZb verificationZb = verifications.get(0);
                      verificationZb.setWhxMoney(verificationZb.getWhxMoney()+ledgerDetail.getAmount());
                      verificationZb.setzMoney(verificationZb.getzMoney()+ledgerDetail.getAmount());
                       if(verificationZb.getStatus() == 1){//核销完毕
                             verificationZb.setStatus(2);//变成部分核销
                       }
                    ledgerDetailDao.savaBean(ledgerDetail);
                }


//                else if(ledgerDetail.getFeeType().getId().equals(ledgerDetail2.getFeeType().getId())) {
//                    if (oldamount != amount) {
//                        String v = String.valueOf(oldamount - amount);
//                        abnormalList.add(getAbnormalDetail("调整费用-" + feeType.getName(), "0", "调整费用-" + feeType.getName(), v));
//                    } else if (oldcashAdvances != cashAdvances) {
//                        String v = String.valueOf(oldcashAdvances - cashAdvances);
//                        abnormalList.add(getAbnormalDetail("调整费用-预付现金", "" + oldcashAdvances, "调整费用-预付现金", v));
//                    } else if (oldoilPrepaid != oilPrepaid) {
//                        String v = String.valueOf(oldoilPrepaid - oilPrepaid);
//                        abnormalList.add(getAbnormalDetail("调整费用-预付油卡", "" + oldoilPrepaid, "调整费用-预付油卡", v));
//                    } else if (oldinput != input) {
//                        String v = String.valueOf(oldinput - input);
//                        abnormalList.add(getAbnormalDetail("调整费用-税金", "" + oldinput, "调整费用-税金", v));
//                        abnormalList.add(getAbnormalDetail("调整费用-税率", "" + oldtaxRate, "调整费用-税率", taxRate.toString()));
//                    }
//                }
            }
       // }
        abnormal.setNumber(abnormalList.size());
        abnormal.setDescription(text);
        abnormal.setShipment(oldShipment);
        abnormal.setTime(DateUtils.getTimestamp());
        abnormal.setType(1);
        Abnormal abnormal1 = (Abnormal) abnormalDao.savaBean(abnormal);
        if(abnormalList.size() == 0){
            throw  new MessageException("没有异常，不用修改");
        }
        oldShipment.setIsAbnormal(1);
        for (AbnormalDetail abnormalDetail:abnormalList) {
            abnormalDetail.setAbnormal(abnormal1);
            abnormalDetailDao.savaBean(abnormalDetail);
        }
        return oldShipment;
    }

    private AbnormalDetail getAbnormalDetail(String source, String sourceValue, String targer, String targerValue) {
        AbnormalDetail abnormalDetail = new AbnormalDetail();
        abnormalDetail.setSource(source);
        abnormalDetail.setTarger(targer);
        abnormalDetail.setSourceValue(sourceValue);
        abnormalDetail.setTargerValue(targerValue);
        return abnormalDetail;
    }

    /**
     * 批量确认
     * @param id
     * @throws MessageException
     */
    public void confirmShipmentAll(String id) throws MessageException {
        String[] split = id.split(Symbol.COMMA);
        for (String splitId : split) {
            this.confirmShipment(splitId);
        }
    }

    public Shipment cancelShipment(String id) throws MessageException {
        Shipment shipment = shipmentDao.selectBean(id);

//        if (shipment.getStatus() != 2) {
//            throw new MessageException("只能取消确认状态下的运单");
//        } else if (shipment.getStatus() >= 4) {
//            throw new MessageException("运单已经发运不能取消");
//        } else {
//            shipment.setStatus(OrderStatus.ACTIVE.getValue());//修改运单状态
//            if (shipment.getOperationPattern() == 0) { //如果是自营
//                if (shipment.getVehiclePlan() != null) {
//                    String hql = "delete from JC_VEHICLE_PLAN WHERE ID='" + shipment.getVehiclePlan().getId() + "'";
//                    shipmentDao.executeHql(hql, null);
//                } else {
//                    throw new MessageException("运单已经生产台账不能取消确认");
//                }
//            }
//        }
//        if (shipment.getRefuse() == 0) { //如果被拒收过
//            ShipmentTrackUtil.addTrack(shipment, "运单已经取消确认 运单货品数量:" + shipment.getNumber() + "  重量:" + shipment.getWeight() + "  体积:" + shipment.getVolume() + "  货值:" + shipment.getValue());
//        }
        return shipment;
//        //处理装卸信息;
//        List<Led> leds = shipment.getLeds();
//        int z = 0;
//        int x = 0;
//        for (Led led:leds) {
//            //处理在途跟踪
//            if (shipment.getRefuse() == 0){//如果被拒收过
//                Order order = led.getOrder();
//                OrderTrackUtil.addTrack(order,"订单:"+led.getCode()+"   已被配载，等待发运…… 操作人:"+SessionUtil.getUserName());
//            }
//            //处理装卸信息
//            List<LedReceivingParty> ledReceivingParties = led.getLedReceivingParties();
//            for (LedReceivingParty ledReceivingParty: ledReceivingParties) {
//                if (ledReceivingParty.getType() == 0){
//                    z++;
//                }else {
//                    x++;
//                }
//            }
//        }
//        shipment.setLoadingInfo(z+"装/"+x+"卸");
        //处理运单的数量，重量，体积
//        String hql = "SELECT SUM(c.number),SUM(c.weight),SUM(c.volume),SUM(c.value)  FROM JC_SHIPMENT a LEFT JOIN a.leds b LEFT JOIN b.ledProducts c  WHERE a.id = '"+id+"'";
//        List list = dispatchDao.executeQueryHql(hql);
//        Object[] o = (Object[]) list.get(0);
//        shipment.setNumber((Double) o[0]);
//        shipment.setWeight((Double) o[1]);
//        shipment.setVolume((Double) o[2]);
//        shipment.setValue((Double) o[3]);

//        //处理回单份数
//        String backHql = "SELECT SUM(c.backNumber)  FROM JC_SHIPMENT a LEFT JOIN a.leds b LEFT JOIN b.order c WHERE a.id = '"+id+"' GROUP BY c.id";
//        List backList = dispatchDao.executeQueryHql(backHql);
//        Long backNumbaer = (Long) backList.get(0);
//        int i = backNumbaer.intValue();
//        shipment.setBackNumber(i);
        //处理运单追踪

    }

    public String getOrg() {
        String HQL = HQL ="SELECT id as id,name as name FROM JC_SYS_ORGANIZATION where status =1 and id !='root'";
        List list = shipmentDao.executeQueryHql(HQL);
        JSONObject jsonObject = new JSONObject();
        for (Object object:list) {
            Object[] o = (Object[]) object;
            jsonObject.put(o[0].toString(),o[1].toString());
        }
        return jsonObject.toJSONString();
    }

    public String getOrgww(String aaa) {
        String HQL = HQL ="SELECT id as id,name as name FROM JC_SYS_ORGANIZATION where status =1 and id !='root'";
        String where =" and 1=1";
        if("0".equals(aaa)){
            where =  " and id !='4028819f676d549801676d7bbb2d0058'";
            HQL = HQL + where;
        }
        List list = shipmentDao.executeQueryHql(HQL);
        JSONObject jsonObject = new JSONObject();
        for (Object object:list) {
            Object[] o = (Object[]) object;
            jsonObject.put(o[0].toString(),o[1].toString());
        }
        return jsonObject.toJSONString();
    }



    /**
     * 异常装车
     * @param ledId
     * @param shipment
     */
    public void addLedyc(String ledId, String shipment) {
        Shipment shipment1 = shipmentDao.selectBean(shipment);
        List<String> ledIds = new ArrayList<String>();
        //拆单规则
        List<Led> leds = JSONArray.parseArray(ledId, Led.class);
        //数据验证
        for (Led led : leds) {
            Led l = (Led) ledDao.selectBean(led.getId()); //原始数据
            List<LedProduct> ledProducts = l.getLedProducts();
            if (l.getNumber()-led.getNumber() > 0.0) { //根据数量判断是否拆分
                l.getOrder().setIsTake(1);
                //拆分验证
                LedProductDao ledProductDao = SpringUtils.getBean(LedProductDaoImpl.class);
                LedRPDao ledRPDao = SpringUtils.getBean(LedRPDaoImpl.class);
                if (ledProducts.size() > 1) {
                    throw new RuntimeException("快速拆分只支持一条货品明细");
                }
                if (l.getWeight() < led.getWeight()) {
                    throw new RuntimeException("拆分重量不能大于被拆分单据总重量");
                }
                if (l.getNumber() < led.getNumber()) {
                    throw new RuntimeException("拆分数量不能大于被拆分单据总数量");
                }
                if (l.getVolume() < led.getVolume()) {
                    throw new RuntimeException("拆分体积不能大于被拆分单据总体积");
                }
                if (l.getValue() < led.getValue()) {
                    throw new RuntimeException("拆分货值不能大于被拆分单据总货值");
                }
                if (led.getValue() < 0 || led.getVolume() < 0 || led.getNumber() < 0 || led.getWeight() < 0) {
                    throw new RuntimeException("拆分值不能是负数");
                }

                //开始减去拆分数据
                l.setNumber(l.getNumber() - led.getNumber());
                l.setWeight(l.getWeight() - led.getWeight());
                l.setVolume(l.getVolume() - led.getVolume());
                l.setValue(l.getValue() - led.getValue());
                if(led.getJzWeight()!=null){
                    l.setJzWeight(l.getJzWeight()-led.getJzWeight());
                }else{
                    l.setJzWeight(l.getJzWeight()-0.0);
                }
                //处理货品明细
                LedProduct ledProduct = ledProducts.get(0);
                ledProduct.setWeight(ledProduct.getWeight() - led.getWeight());
                ledProduct.setNumber(ledProduct.getNumber()-led.getNumber());
                ledProduct.setVolume(ledProduct.getVolume() - led.getVolume());
                ledProduct.setValue(ledProduct.getValue() - led.getValue());
                if(led.getJzWeight() != null){
                    ledProduct.setJzWeight(ledProduct.getJzWeight() - led.getJzWeight());
                }else{
                    ledProduct.setJzWeight(ledProduct.getJzWeight() - 0.0);
                }

                //克隆对象
                Led cloneObj = (Led) ObjectUtil.cloneObject(l, Led.class);
                cloneObj.setId(null);//处理克隆对象
                cloneObj.setNumber(led.getNumber());
                cloneObj.setWeight(led.getWeight());
                cloneObj.setValue(led.getValue());
                cloneObj.setVolume(led.getVolume());
                // cloneObj.setJzWeight(0.00);
                List<LedProduct> ledProducts1 = cloneObj.getLedProducts();
                LedProduct ledProduct1 = ledProducts1.get(0);
                ledProduct1.setNumber(led.getNumber());
                ledProduct1.setWeight(led.getWeight());
                ledProduct1.setValue(led.getValue());
                ledProduct1.setVolume(led.getVolume());
                //ledProduct1.setJzWeight(led.getJzWeight());
                ledProduct1.setId(null);
                //处理分段运单单号
                String code = cloneObj.getCode();
                long count = ledDao.selectCount(null, " and order.id ='" + l.getOrder().getId() + "'", null);
                String subas = code.substring(0, code.indexOf("-"));
                String newCode = subas + "-" + (count + 1);
                cloneObj.setCode(newCode);
                cloneObj.setBackNumber(0);
                cloneObj.setAgent(0.0);
                cloneObj.setAgent(0.0);
                List<LedReceivingParty> ledReceivingParties = cloneObj.getLedReceivingParties();
                //阻断关系
                cloneObj.setLedProducts(null);
                cloneObj.setLedReceivingParties(null);
                Led returnLed = (Led) ledDao.savaBean(cloneObj);
                ledIds.add(returnLed.getId());
                for (LedReceivingParty ledReceivingPartie : ledReceivingParties) {
                    ledReceivingPartie.setLed(returnLed);
                    ledReceivingPartie.setId(null);
                    ledRPDao.savaBean(ledReceivingPartie);
                }
                for (LedProduct cloneLedProduct : ledProducts1) {
                    cloneLedProduct.setLed(returnLed);
                    cloneLedProduct.setId(null);
                    ledProductDao.savaBean(cloneLedProduct);
                }
            } else {
                ledIds.add(l.getId());
            }
        }
        for (String id : ledIds) {
            Led led = (Led) ledDao.selectBean(id);
            if (led.getStatus() == OrderStatus.STOWAGE.getValue()) {
                throw new MessageException("分段订单号:" + led.getCode() + "已经被其他运单配载,请重新选择");
            }
            led.setStatus(shipment1.getStatus()); //修改分段订单状态
            led.getOrder().setStatus(shipment1.getStatus()); //修改订单状态
            if(shipment1.getStatus() ==4){
                led.setFactLeaveTime(shipment1.getFactLeaveTime());
                led.getOrder().setFactArriveTime(shipment1.getFactLeaveTime());
            }
            if(shipment1.getStatus() == 5 ||shipment1.getStatus() == 6){
                led.setFactArriveTime(shipment1.getFactLeaveTime());
                led.getOrder().setFactArriveTime(shipment1.getFactArriveTime());
            }

            led.setShipment(shipment1);
            if (shipment1.getLeds() == null) {
                shipment1.setLeds(new ArrayList<Led>());
            }
            shipment1.getLeds().add(led);
            shipment1.setVolume(led.getVolume() + shipment1.getVolume());
            shipment1.setNumber(led.getNumber() + shipment1.getNumber());
            shipment1.setWeight(shipment1.getWeight() + led.getWeight());
            shipment1.setJzWeight(shipment1.getJzWeight() + led.getJzWeight());
            ShipmentTrackUtil.addTrack(shipment1, "运单已经配载分段订单 分段订单号:" + led.getCode() + "   配载货品数量:" + led.getNumber() + "  重量:" + led.getWeight() + "  体积:" + led.getVolume() + "  货值:" + led.getValue());
           //异常模块
            Abnormal abnormal = new Abnormal();
                abnormal.setNumber(1);
                abnormal.setShipment(shipment1);
                abnormal.setType(1);
                abnormal.setTime(new Timestamp(System.currentTimeMillis()));
                abnormal.setDescription("异常装车");
                abnormal.setOrganization(SessionUtil.getOrg());
            Abnormal abnormal1 = (Abnormal) abnormalDao.savaBean(abnormal);
            AbnormalDetail abnormalDetail = new AbnormalDetail();
            abnormalDetail.setTarger("异常装车");
            abnormalDetail.setTargerValue("异常装车："+led.getCode());
            abnormalDetail.setSource("code");
            abnormalDetail.setSourceValue("不知道啊");
            abnormalDetail.setDescription("异常装车");
            abnormalDetail.setAbnormal(abnormal1);
            abnormalDetailDao.savaBean(abnormalDetail);
            shipment1.setIsAbnormal(1);
            // OrderTrackUtil.addTrack(led.getOrder(),"订单已经装车 分段订单号:" + led.getCode() + "   卸载货品数量:" + led.getNumber() + "  重量:" + led.getWeight() + "  体积:" + led.getVolume() + "  货值:" + led.getValue());
        }
        //运单更新客户订单号
        costCustomerNum(shipment1);

    }

    /**
     * 异常卸车
     * @param ledId
     * @param shipment
     */
    public void delLedyc(String ledId, String shipment) {
        String[] ledIds = ledId.split(Symbol.COMMA);
        Shipment shipment1 = shipmentDao.selectBean(shipment);
        String hql = "FROM JC_LED a left join a.shipments b WHERE  b.id = '" + shipment + "' and a.id not in(" + SqlUtils.splitForIn(ledIds) + ") ";
        List<Object> objs = ledDao.executeQueryHql(hql);
        List<Led> list = new ArrayList<Led>();
        for (Object object : objs) {
            Object[] obj = (Object[]) object;
            for (Object o : obj) {
                if (o instanceof Led) {
                    Led led = (Led) o;
                    list.add(led);
                }
            }
        }
        shipment1.setLeds(list); //原卸载逻辑
        // shipment1.setLedsss(list);
        for (String id : ledIds) {
            Led led = (Led) ledDao.selectBean(id);
            led.setShipment(null);
            String id1 = led.getOrder().getOrganization().getId();
            if(id1.equals(SessionUtil.getOrgId())){
                led.setStatus(OrderStatus.CONFIGRM.getValue());
            }else {
                led.setStatus(11);
            }
            Order order = led.getOrder();
             order.setFactLeaveTime(null);
             order.setFactArriveTime(null);
            List<Led> leds = order.getLeds();
            //合并分段单据
            String hql2 = "select a from JC_LED a left join a.order b left join a.shipment c left join a.organization d where b.id='" + order.getId() + "' and c.id is null and a.id != '" + id + "' and d.id = '"+SessionUtil.getOrgId()+"'";
            List<Led> ledcs = ledDao.executeQueryHql(hql2);
            if (ledcs.size() > 0) { //说明有未配载单据 需合并
                LedProductDao ledProductDao = SpringUtils.getBean(LedProductDaoImpl.class);
                LedRPDao ledRPDao = SpringUtils.getBean(LedRPDaoImpl.class);
                Led notLed = ledcs.get(0);
                notLed.setNumber(notLed.getNumber() + led.getNumber());
                notLed.setWeight(notLed.getWeight() + led.getWeight());
                notLed.setVolume(notLed.getVolume() + led.getVolume());
                notLed.setValue(notLed.getValue() + led.getValue());
                notLed.setBackNumber(notLed.getBackNumber() + led.getBackNumber());
                //合并货品明细
                List<LedProduct> ledProducts = led.getLedProducts();
                for (LedProduct ledProduct : ledProducts) { //循环装在货品明细
                    List<LedProduct> notLedProducts = ledProductDao.executeQueryHql("FROM JC_LEG_PRODUCT where led.id = '" + notLed.getId() + "' and unit ='" + ledProduct.getUnit() + "' and name='" + ledProduct.getName() + "'");
                    if (notLedProducts.size() > 0) { //有相同货品
                        LedProduct notLedProduct = notLedProducts.get(0);
                        notLedProduct.setNumber(notLedProduct.getNumber() + ledProduct.getNumber());
                        notLedProduct.setWeight(notLedProduct.getWeight() + ledProduct.getWeight());
                        notLedProduct.setVolume(notLedProduct.getVolume() + ledProduct.getVolume());
                        notLedProduct.setValue(notLedProduct.getValue() + ledProduct.getValue());
                        ledProductDao.deleteBean(ledProduct);
                    } else {
                        ledProduct.setLed(notLed);
                    }
                }
                //删除合并后分段单据
                List<LedReceivingParty> ledReceivingParties = led.getLedReceivingParties();
                for (LedReceivingParty ledReceivingParty : ledReceivingParties) {
                    ledRPDao.deleteBean(ledReceivingParty);
                }
//                List<InOrOutRecord> inOrOutRecords = inOrOutRecordDao.executeQueryHql("SELECT id from JC_INOROUTRECORD where led.id='" + led.getId() + "'");
//                if(inOrOutRecords.size()>=1){
//                    for (InOrOutRecord inOrOutRecord:inOrOutRecords) {
//                        inOrOutRecordDao.deleteBean(inOrOutRecord);
//                    }
//                }
                ledDao.deleteBean(led);

            }

            List<Led> leed = ledDao.executeQueryHql("From JC_LED where order.id='" + order.getId() + "'"); //合并后 分段运单号修正
            if (leed.size() == 1) {
                Led led1 = leed.get(0);
                led1.getOrder().setIsTake(0); //回复未拆分状态
                String code = led1.getCode();
                String subas = code.substring(0, code.indexOf("-"));
                String newCode = subas + "-" + 1;
                led1.setCode(newCode);
            }

            boolean s = true;
            for (Led led1 : leds) {
                if (led1.getStatus() != OrderStatus.CONFIGRM.getValue()) {
                    s = false;
                }
            }
            if (s) {
                order.setStatus(OrderStatus.CONFIGRM.getValue());
            }
            led.setFactLeaveTime(null);
            led.setFactArriveTime(null);
            shipment1.setNumber(shipment1.getNumber() - led.getNumber());
            shipment1.setWeight(shipment1.getWeight() - led.getWeight());
            shipment1.setVolume(shipment1.getVolume() - led.getVolume());
            //异常模块
            String hql22 ="select a from JC_ABNORMAL a left join a.shipment b WHERE b.id='"+shipment1.getId()+"'";
            List list1 = abnormalDao.executeQueryHql(hql22);
            if(list1.size()<=0){
            Abnormal abnormal =  new Abnormal();
                abnormal.setNumber(1);
            abnormal.setShipment(shipment1);
            abnormal.setType(1);
            abnormal.setTime(new Timestamp(System.currentTimeMillis()));
            abnormal.setOrganization(SessionUtil.getOrg());
            abnormal.setDescription("异常卸车");
            Abnormal abnormal1 = (Abnormal) abnormalDao.savaBean(abnormal);
            //添加明细
            AbnormalDetail abnormalDetail = new AbnormalDetail();
            abnormalDetail.setTarger("异常装车");
            abnormalDetail.setTargerValue("异常卸车："+led.getCode());
            abnormalDetail.setSource("code");
            abnormalDetail.setSourceValue("不知道啊");
            abnormalDetail.setDescription("异常卸车");
            abnormalDetail.setAbnormal(abnormal1);
            abnormalDetailDao.savaBean(abnormalDetail);
            shipment1.setIsAbnormal(1);
            }
            //处理运单追踪
            ShipmentTrackUtil.addTrack(shipment1, "运单已经卸载分段订单 分段订单号:" + led.getCode() + "   卸载货品数量:" + led.getNumber() + "  重量:" + led.getWeight() + "  体积:" + led.getVolume() + "  货值:" + led.getValue());
            //  OrderTrackUtil.addTrack(order,"订单已经被卸载 分段订单号:" + led.getCode() + "   卸载货品数量:" + led.getNumber() + "  重量:" + led.getWeight() + "  体积:" + led.getVolume() + "  货值:" + led.getValue());
            //运单更新客户订单号

            costCustomerNum(shipment1);
        }
    }

    /**
     *外包成本保存
     * @param str
     * @return
     */
    public String saveff(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        Double xj = 0.00;
        Double hf = 0.00;
        Double yj = 0.00;
        String xianjie = jsonObject.getString("xianjie");
         if(!xianjie.equals("")){
             xj = Double.valueOf(xianjie.toString());
         }
        String huifu =  jsonObject.getString("huifu");
        if(!huifu.equals("")) {
            hf = Double.valueOf(huifu.toString());
        }
        String yuejie = jsonObject.getString("yuejie");
        if(!yuejie.equals("")) {
            yj = Double.valueOf(yuejie.toString());
        }
        String description1 = jsonObject.getString("description1");
        String shipid = jsonObject.getString("shipid");
        Shipment shipment = shipmentDao.selectBean(shipid);
        List<JSONObject> fyjh = jsonObject.getObject("fyjh", List.class);
        Double ztha = 0.00;
        Double zsha = 0.00;
        Double zgxa = 0.00;
        Double zzfy = 0.00;
        Double zqta = 0.00;
              for (JSONObject apportionment1:fyjh) {
                  Double tha = 0.00;
                  Double sha = 0.00;
                  Double gxa = 0.00;
                  Double zfy = 0.00;
                  Double qta = 0.00;
                  String ledid = apportionment1.getString("ledid");
                  Led led  = (Led) ledDao.selectBean(ledid);
                  String thAmount = apportionment1.getString("thAmount");
                  if(!thAmount.equals("")){
                      tha = Double.valueOf(thAmount);
                      ztha += tha;
                  }
                  String shAmount = apportionment1.getString("shAmount");
                  if(!shAmount.equals("")){
                      sha = Double.valueOf(shAmount);
                      zsha += sha;
                  }
                  String gxAmount = apportionment1.getString("gxAmount");
                  if(!gxAmount.equals("")){
                      gxa = Double.valueOf(gxAmount);
                      zgxa += gxa;
                  }
                  String qtAmount = apportionment1.getString("qtAmount");
                  if(!qtAmount.equals("")){
                      qta = Double.valueOf(qtAmount);
                      zqta += qta;
                  }
                  String zcb = apportionment1.getString("zcb");
                  if(!zcb.equals("")){
                      zfy= Double.valueOf(zcb);
                      zzfy += zfy;
                  }
                  String hql = "select id FROM JC_COST_APPORTIONMENT WHERE jc_led_id='"+led.getId()+"'";
                  List list = apportionmentDao.executeQueryHql(hql);
                  if(list.size()>0){
                      String idd = (String) list.get(0);
                      Apportionment apportionment = (Apportionment) apportionmentDao.selectBean(idd);
                         apportionment.setGxAmount(gxa);
                         apportionment.setQtAmount(qta);
                         apportionment.setShAmount(sha);
                         apportionment.setThAmount(tha);
                         apportionment.setzAmount(zfy);
                  }else {
                      Apportionment apportionment = new Apportionment();
                      apportionment.setLed(led);
                      apportionment.setShipment(shipment);
                      apportionment.setGxAmount(gxa);
                      apportionment.setQtAmount(qta);
                      apportionment.setShAmount(sha);
                      apportionment.setThAmount(tha);
                      apportionment.setzAmount(zfy);
                      apportionmentDao.savaBean(apportionment);
                  }
                  String hql2 ="select id from JC_LEDGER_DETAIL where jc_shipment_id = '"+shipid+"'";
                  List<LedgerDetail> ledgerDetails = ledgerDetailDao.executeQueryHql(hql2);
                  if(ledgerDetails.size()>0){
                      String  aa = String.valueOf(ledgerDetails.get(0));
                      LedgerDetail ledgerDetail = ledgerDetailDao.selectBean(aa);
                      ledgerDetail.setNowPay(xj);
                      ledgerDetail.setBackPay(hf);
                      ledgerDetail.setMonthPay(yj);
                      ledgerDetail.setZfy(zzfy);
                      ledgerDetail.setZthf(ztha);
                      ledgerDetail.setZgxyf(zgxa);
                      ledgerDetail.setZqtfy(zqta);
                      ledgerDetail.setZshf(zsha);
                      ledgerDetail.setYuE(zzfy-xj);
                      ledgerDetail.setAmount(zzfy);
                      ledgerDetail.setDescription(description1);
                  }else{
                      FeeType feeType = (FeeType) feeTypeDao.selectBean("402881b6669eb6d601669eb7ed1f0001");
                      LedgerDetail ledgerDetail = new LedgerDetail();
                      ledgerDetail.setShipment(shipment);
                      ledgerDetail.setType(0);
                      ledgerDetail.setCost(0);
                      ledgerDetail.setNowPay(xj);
                      ledgerDetail.setBackPay(hf);
                      ledgerDetail.setMonthPay(yj);
                      ledgerDetail.setZfy(zzfy);
                      ledgerDetail.setZthf(ztha);
                      ledgerDetail.setZgxyf(zgxa);
                      ledgerDetail.setZqtfy(zqta);
                      ledgerDetail.setZshf(zsha);
                      ledgerDetail.setYuE(zzfy-xj);
                      ledgerDetail.setFeeType(feeType);
                      ledgerDetail.setAmount(zzfy);
                      ledgerDetail.setDescription(description1);
                      ledgerDetailDao.savaBean(ledgerDetail);
                  }
          }


        return "成功";
    }



    /**
     * 车辆到站页面查询
     * @param commModel
     * @return
     */
    public JgGridListModel getRVShipmentList(CommModel commModel) {
        String status = commModel.getStatus();
        String where = " and (torg.id = '" + SessionUtil.getOrgId() + "' or ('"+SessionUtil.getOrgId()+"' in bo.id and bs.isArrive =0))  and ship.status in (4,6,7) and ship.operationPattern !=1";

        String orderBy = " order by ship.create_Time desc";
        return super.getListByPageToHql(ShipmentSql.SHIPMENT_LIST2, ShipmentSql.SHIPMENT_COUNT2, commModel, where, orderBy);
    }

    /**
     * 货品到站页面查询 车辆
     * @param commModel
     * @return
     */
    public JgGridListModel getDZShipmentList(CommModel commModel) {
        String status = commModel.getStatus();
        String where = " and norg.id = '" + SessionUtil.getOrgId() + "' and ship.status in (5,6) and ship.operationPattern !=1";
        String orderBy = " order by ship.create_Time desc";
        return super.getListByPageToHql(ShipmentSql.SHIPMENT_LIST, ShipmentSql.SHIPMENT_COUNT, commModel, where, orderBy);
    }

    public void costCustomerNum(Shipment shipment) {
        shipment.setRelatebill1("");
        shipment.setOrderCode("");
        StringBuilder customerNum = new StringBuilder();
        StringBuilder orderCode = new StringBuilder();
        List<Led> leds = shipment.getLeds();
        for (Led led : leds) {
            customerNum.append(led.getRelatebill1() + "/");
            orderCode.append(led.getOrder().getCode() + "/");
        }
        if (customerNum.length() != 0) {
            customerNum.deleteCharAt(customerNum.lastIndexOf("/"));
        }
        if (orderCode.length() != 0) {
            orderCode.deleteCharAt(orderCode.lastIndexOf("/"));
        }
        shipment.setRelatebill1(customerNum.toString());
        shipment.setOrderCode(orderCode.toString());
        shipmentDao.savaBean(shipment);
    }

    public JgGridListModel getLedgerList(CommModel commModel) {
        String where = " and org.id = '" + SessionUtil.getOrgId() + "'";
        if(commModel.getStatus() != null){
            where +=" and ledgerDetails.is_inoutcome="+commModel.getStatus();
        }else{
            where +=" and ledgerDetails.is_inoutcome=0";
        }
        String orderBy = " order by ledgerDetails.create_Time desc";
        return super.getListByPageToHql(outLedgerSql.LEDGER_LIST, outLedgerSql.LEDGER_LIST_COUNT, commModel, where, orderBy);
    }

    @Override
    public void confirmLedger(String id,String time) throws MessageException {
        String[] split = id.split(Symbol.COMMA);
        for (String splitId : split) {

            LedgerDetail ledgerDetail= ledgerDetailDao.selectBean(splitId);
            if (ledgerDetail.getIs_inoutcome() == 1) {
                throw new MessageException("请选择未确认的订单");
            }
            ledgerDetail.setIs_inoutcome(1);
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            try {
                ts = Timestamp.valueOf(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ledgerDetail.setAffirm_Time(ts);

            Shipment shipment=ledgerDetail.getShipment();
            shipment.setIs_inoutcome(1);
        }
    }

    @Override
    public void backConfirmLedger(String id) throws MessageException {
        String[] split = id.split(Symbol.COMMA);
        for (String splitId : split) {
            Integer sign=0;
            LedgerDetail ledgerDetail= ledgerDetailDao.selectBean(splitId);
            if (ledgerDetail.getIs_inoutcome() == 0) {
                throw new MessageException("请选择已确认的订单");
            }
            ledgerDetail.setIs_inoutcome(0);
            ledgerDetail.setAffirm_Time(null)   ;
            Shipment shipment=ledgerDetail.getShipment();
            List<LedgerDetail> ledgerDetailList=shipment.getLedgerDetails();
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
                shipment.setIs_inoutcome(0);
            }
        }
    }
}
