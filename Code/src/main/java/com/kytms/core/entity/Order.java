package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.kytms.orderabnormal.Abnormal;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
/**
 * 奇趣源码商城 www.qiqucode.com
 * 订单管理
 *
 * 分段订单 出现差异的  直接新增一条数据
 * 用于发运 配载  和到站
 * 用于记录发货库存的
 * @author 奇趣源码
 * @create 2018-01-02
 */
@Entity(name = "JC_ORDER")
public class Order extends BaseEntity implements Serializable {
    @Abnormal(name = "发货单号")
    private String relatebill1; //客户订单号1-5
    private String relatebill2;
    private String relatebill3;
    private String relatebill4;
    private String relatebill5;
    @Abnormal(name = "开单日期")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp time; // 订单日期
    @Abnormal(name = "订单公里数")
    private Double orderMileage;//订单公里数
    @Abnormal(name = "客户类型",dataBookName = "CustomerType")
    private Integer costomerType; //客户类型  0:合同，1零散
    @Abnormal(name = "零散客户名称")
    private String costomerName; //客户名称(零散客户)
    @Abnormal(name = "交付方式",dataBookName = "HandoverType")
    private Integer handoverType; //交付方式
//    private String psqy;// 临时派送区域
//    private Zone fromCity;//起运站（行政区域）
//    private Zone toCity;//目的站（行政区域）
    @Abnormal(name = "要求发运时间")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planLeaveTime;//要求发运时间
    @Abnormal(name = "要求到达时间")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planArriveTime; //要求到达时间
    @Abnormal(name = "结算方式",dataBookName = "Clearing")
    private Integer feeType;//结算方式
    @Abnormal(name = "目的网点",clszz =Organization.class)
    private Organization toOrganization;//目的网点
    @Abnormal(name = "交付方式",dataBookName = "CommIsNot")
    private Integer isBack; //是否回单

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factArriveTime;//实际到达时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factLeaveTime;//实际发运时间
    @Abnormal(name = "销售负责人")
    private String salePersion; //销售负责人
    @Abnormal(name = "交付方式",dataBookName = "TransportPro")
    private Integer transportPro;//运输性质
    private Integer costomerIsExceed;//合同是否超期
    private Integer shipmentMethod;//运输方式
    @Abnormal(name = "回单份数")
    private Integer backNumber;//回单份数
    private Integer number;//件数
    private Double weight;//重量
    private Double volume; //体积
    private Double value; //货值
    private Double jzWeight; //计重

    private Double agent; // 代收货款
    @Abnormal(name = "是否有异常",dataBookName = "CommIsNot")
    private Integer isAbnormal; //是否有异常
    private Integer isTake;//是否有拆单
    private Integer hxtype;//核销状态
    private Double hxmoney;//核销金额
    private Double whxmoney;//未核销金额
    private List<com.kytms.core.entity.Abnormal> abnormals;

    private OrderBack orderBack;//订单回单
    @Abnormal(name = "客户",clszz =Customer.class)
    private Customer customer; //客户
    private Organization organization; //组织机构(出发网点)
    private List<OrderReceivingParty> orderReceivingParties; //所属收发货方
    private List<OrderProduct> orderProducts;//货品
    private List<Led> leds;//分段订单
    private List<OrderTrack> orderTracks;//追踪表
//    private Settlement settlement;//结算表
//    private int jsStatus; //结算状态
    private List<LedgerDetail> ledgerDetails;//台账明细

    private Single single;//提派单
    @Abnormal(name = "库存区域",clszz =ZoneStoreroom.class)
    private ZoneStoreroom zoneStoreroom; //库存区域
    @Abnormal(name = "出发运点",clszz =ServerZone.class)
    private  ServerZone startZone;    //出发运点
    @Abnormal(name = "目的运点",clszz =ServerZone.class)
    private  ServerZone  endZone;      //目的运点

    private Presco presco; //预录单ID

    @JSONField(serialize = false)
    private List<VerificationZb> verifications;//核销表

    private String qsperson;//签收人

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp qsTime;//签收时间
    private Double zamount;//总金额
    private Double price;//单价
    private Integer jsType;//计算方式
    private int is_inoutcome;//是否收入成本确认

    private Logger log = Logger.getLogger(Order.class);//输出Log日志


    //-----------------------分割线-------------------------------------


    @Column(name = "IS_INOUTCOME")
    public int getIs_inoutcome() {
        return is_inoutcome;
    }

    public void setIs_inoutcome(int is_inoutcome) {
        this.is_inoutcome = is_inoutcome;
    }

    @Column(name = "Z_AMOUNT")
    public Double getZamount() {
        return zamount;
    }

    public void setZamount(Double zamount) {
        this.zamount = zamount;
    }
    @Column(name ="PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "JS_TYPE")
    public Integer getJsType() {
        return jsType;
    }

    public void setJsType(Integer jsType) {
        this.jsType = jsType;
    }

    @Column(name = "JZ_WEIGHT")
    public Double getJzWeight() {
        return jzWeight;
    }

    public void setJzWeight(Double jzWeight) {
        this.jzWeight = jzWeight;
    }

    @Column(name = "QS_PERSON")
    public String getQsperson() {
        return qsperson;
    }

    public void setQsperson(String qsperson) {
        this.qsperson = qsperson;
    }

    @Column(name = "QS_TIME")
    public Timestamp getQsTime() {
        return qsTime;
    }

    public void setQsTime(Timestamp qsTime) {
        this.qsTime = qsTime;
    }

    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "order")
    public List<VerificationZb> getVerifications() {
        return verifications;
    }
    public void setVerifications(List<VerificationZb> verifications) {
        this.verifications = verifications;
    }


    @JSONField(serialize = false)
    @OneToOne
    @JoinColumn(name = "JC_PRESCO_ID")
    public Presco getPresco() {
        return presco;
    }

    public void setPresco(Presco presco) {
        this.presco = presco;
    }

    @Column(name = "ORDER_MILEAGE")
    public Double getOrderMileage() {
        return orderMileage;
    }

    public void setOrderMileage(Double orderMileage) {
        this.orderMileage = orderMileage;
    }
    @Column(name = "COSTOMER_TYPE")
    public Integer getCostomerType() {
        return costomerType;
    }

    public void setCostomerType(Integer costomerType) {
        this.costomerType = costomerType;
    }

    @Column(name = "HANDOVER_TYPE")
    public Integer getHandoverType() {
        return handoverType;
    }

    public void setHandoverType(Integer handoverType) {
        this.handoverType = handoverType;
    }

    @Column(name = "FEE_TYEP")
    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }
    @Column(name = "IS_BACK")
    public Integer getIsBack() {
        return isBack;
    }

    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
    }
    @Column(name = "TRANSPORT_PRO")
    public Integer getTransportPro() {
        return transportPro;
    }

    public void setTransportPro(Integer transportPro) {
        this.transportPro = transportPro;
    }

    @Column(name = "COSTOMER_IS_EXCEED")
    public Integer getCostomerIsExceed() {
        return costomerIsExceed;
    }

    public void setCostomerIsExceed(Integer costomerIsExceed) {
        this.costomerIsExceed = costomerIsExceed;
    }

    @Column(name = "SHIPMENT_METHOD")
    public Integer getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(Integer shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    @Column(name = "BACK_NUMBER")
    public Integer getBackNumber() {
        return backNumber;
    }

    public void setBackNumber(Integer backNumber) {
        this.backNumber = backNumber;
    }

    @Column(name = "NUMBER")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    @Column(name = "WEIGHT")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
    @Column(name = "VOLUME")
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
    @Column(name = "VALUE")
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Column(name = "IS_ABNORMAL", columnDefinition = "INT default 1")
    public Integer getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(Integer isAbnormal) {
        this.isAbnormal = isAbnormal;
    }
    @Column(name = "HX_TYPE")
    public Integer getHxtype() {
        return hxtype;
    }

    public void setHxtype(Integer hxtype) {
        this.hxtype = hxtype;
    }
    @Column(name = "HX_MONEY")
    public Double getHxmoney() {
        return hxmoney;
    }

    public void setHxmoney(Double hxmoney) {
        this.hxmoney = hxmoney;
    }

    @Column(name = "WHX_MONEY")
    public Double getWhxmoney() {
        return whxmoney;
    }

    public void setWhxmoney(Double whxmoney) {
        this.whxmoney = whxmoney;
    }

    @JSONField(serialize = false)
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_SINGLE_ID")
    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }



    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "RELATEBILL1", length = 50)
    public String getRelatebill1() {
        return relatebill1;
    }
    public void setRelatebill1(String relatebill1) {
        this.relatebill1 = relatebill1;
    }

    @Column(name = "RELATEBILL2", length = 50)
    public String getRelatebill2() {
        return relatebill2;
    }
    public void setRelatebill2(String relatebill2) {
        this.relatebill2 = relatebill2;
    }

    @Column(name = "RELATEBILL3", length = 50)
    public String getRelatebill3() {
        return relatebill3;
    }
    public void setRelatebill3(String relatebill3) {
        this.relatebill3 = relatebill3;
    }

    @Column(name = "RELATEBILL4", length = 50)
    public String getRelatebill4() {
        return relatebill4;
    }
    public void setRelatebill4(String relatebill4) {
        this.relatebill4 = relatebill4;
    }

    @Column(name = "RELATEBILL5", length = 50)
    public String getRelatebill5() {
        return relatebill5;
    }
    public void setRelatebill5(String relatebill5) {
        this.relatebill5 = relatebill5;
    }



    @Column(name = "COSTOMER_NAME", length = 50)
    public String getCostomerName() {
        return costomerName;
    }
    public void setCostomerName(String costomerName) {
        this.costomerName = costomerName;
    }



    @Column(name = "SALE_PERSION", length = 25)
    public String getSalePersion() {
        return salePersion;
    }
    public void setSalePersion(String salePersion) {
        this.salePersion = salePersion;
    }


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "order")
    public List<OrderReceivingParty> getOrderReceivingParties() {
        return orderReceivingParties;
    }
    public void setOrderReceivingParties(List<OrderReceivingParty> orderReceivingParties) {
        this.orderReceivingParties = orderReceivingParties;
    }

    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "order")
    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }
    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }


    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "order")
    public List<Led> getLeds() {
        return leds;
    }
    public void setLeds(List<Led> leds) {
        this.leds = leds;
    }





    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "order")
    public List<OrderTrack> getOrderTracks() {
        return orderTracks;
    }
    public void setOrderTracks(List<OrderTrack> orderTracks) {
        this.orderTracks = orderTracks;
    }

    @JSONField(serialize = false)
    @OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE)
    public OrderBack getOrderBack() {
        return orderBack;
    }
    public void setOrderBack(OrderBack orderBack) {
        this.orderBack = orderBack;
    }


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_TO_ORGANIZATION_ID")
    public Organization getToOrganization() {
        return toOrganization;
    }

    public void setToOrganization(Organization toOrganization) {
        this.toOrganization = toOrganization;
    }


    @Column(name = "PLAN_LEAVE_TIME")
    public Timestamp getPlanLeaveTime() {
        return planLeaveTime;
    }

    public void setPlanLeaveTime(Timestamp planLeaveTime) {
        this.planLeaveTime = planLeaveTime;
    }

    @Column(name = "PLAN_ARRIVE_TIME")
    public Timestamp getPlanArriveTime() {
        return planArriveTime;
    }

    public void setPlanArriveTime(Timestamp planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    @Column(name = "FACT_ARRIVE_TIME")
    public Timestamp getFactArriveTime() {
        return factArriveTime;
    }

    public void setFactArriveTime(Timestamp factArriveTime) {
        this.factArriveTime = factArriveTime;
    }
    @Column(name = "FACT_LEAVE_TIME")
    public Timestamp getFactLeaveTime() {
        return factLeaveTime;
    }

    public void setFactLeaveTime(Timestamp factLeaveTime) {
        this.factLeaveTime = factLeaveTime;
    }

    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="order")
    public List<LedgerDetail> getLedgerDetails() {
        return ledgerDetails;
    }

    public void setLedgerDetails(List<LedgerDetail> ledgerDetails) {
        this.ledgerDetails = ledgerDetails;
    }


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_ZONE_STOREROOM_ID")
    public ZoneStoreroom getZoneStoreroom() {
        return zoneStoreroom;
    }
    public void setZoneStoreroom(ZoneStoreroom zoneStoreroom) {
        this.zoneStoreroom = zoneStoreroom;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_START_ZONE_ID")
    public ServerZone getStartZone() {
        return startZone;
    }
    public void setStartZone(ServerZone startZone) {
        this.startZone = startZone;
    }


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_END_ZONE_ID")
    public ServerZone getEndZone() {
        return endZone;
    }
    public void setEndZone(ServerZone endZone) {
        this.endZone = endZone;
    }
    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="order")
    public List<com.kytms.core.entity.Abnormal> getAbnormals() {
        return abnormals;
    }

    public void setAbnormals(List<com.kytms.core.entity.Abnormal> abnormals) {
        this.abnormals = abnormals;
    }
    @Column(name = "IS_TAKE",length = 5,columnDefinition="INT default 0")
    public Integer getIsTake() {
        return isTake;
    }

    public void setIsTake(Integer isTake) {
        this.isTake = isTake;
    }

    @Column(name = "AGENT")
    public Double getAgent() {
        return agent;
    }

    public void setAgent(Double agent) {
        this.agent = agent;
    }
}
