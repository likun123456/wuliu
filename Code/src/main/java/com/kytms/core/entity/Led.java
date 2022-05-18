package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 分段订单
 * @author 奇趣源码
 * @create 2018-01-02
 */
@Entity(name = "JC_LED")
public class Led extends BaseEntity implements Serializable {
    private String relatebill1; //客户订单号1-5
    private String relatebill2;
    private String relatebill3;
    private String relatebill4;
    private String relatebill5;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp time; // 订单日期
    private Double orderMileage;//订单公里数
    private Integer costomerType; //客户类型  0:合同，1零散
    private String costomerName; //客户名称(零散客户)
    private Integer handoverType; //交付方式
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planLeaveTime;//要求发运时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planArriveTime; //要求到达时间

    private Integer feeType;//结算方式
    private Organization formOrganization;//始发网点
    private Organization toOrganization;//目的网点
    private Integer isBack; //是否回单

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factArriveTime;//实际到达时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factLeaveTime;//实际发运时间
    private String salePersion; //销售负责人
    private Integer transportPro;//运输性质
    private Integer costomerIsExceed;//合同是否超期
    private Integer shipmentMethod;//运输方式
    private Integer backNumber;//回单份数
    private Integer number=00;//件数
    private Double weight=0.00;//重量
    private Double volume=0.00; //体积
    private Double value=0.00; //货值
    private Double jzWeight;// 计重
    private Integer isAbnormal; //是否有异常

    private ZoneStoreroom zoneStoreroom; //库存区域
    private  ServerZone startZone;    //出发运点
    private  ServerZone  endZone;      //目的运点
    private Presco presco; //预录单ID


    private Double agent=0.0; // 代收货款
    private Double arrivePay =0.0; //到付款
    private List<Shipment> shipments;//此关联有中间表
    private Customer customer; //客户
    private Organization organization; //当前组织机构
    private Order order;//订单表
    private List<LedReceivingParty> ledReceivingParties; //所属收发货方
    private List<LedProduct> ledProducts;
    private List<ArriveGoods> arriveGoods;//货品到站
    private List<Apportionment> apportionments;//成本分摊
    private Single single;//派车单

    private Integer type;//提货或者是派送状态

    private String qsperson;//签收人

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp qsTime;//签收时间


    private Shipment shipment;//运单 直接在分段运点里插入运单ID

    private Logger log = Logger.getLogger(Led.class);//输出Log日志


    //-----------------------分割线-------------------------------------
    @JSONField(serialize = false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="led")
    public List<Apportionment> getApportionments() {
        return apportionments;
    }

    public void setApportionments(List<Apportionment> apportionments) {
        this.apportionments = apportionments;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
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

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @JSONField(serialize = false)
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_FORM_ORGANIZATION_ID")
    public Organization getFormOrganization() {
        return formOrganization;
    }

    public void setFormOrganization(Organization formOrganization) {
        this.formOrganization = formOrganization;
    }

    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
   @Column(name = "COSTOMER_NAME")
    public String getCostomerName() {
        return costomerName;
    }

    public void setCostomerName(String costomerName) {
        this.costomerName = costomerName;
    }

    @Column(name = "HANDOVER_TYPE")
    public Integer getHandoverType() {
        return handoverType;
    }

    public void setHandoverType(Integer handoverType) {
        this.handoverType = handoverType;
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
    @Column(name = "FEE_TYPE")
    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_TO_ORGANIZATION_ID")
    public Organization getToOrganization() {
        return toOrganization;
    }

    public void setToOrganization(Organization toOrganization) {
        this.toOrganization = toOrganization;
    }

    @Column(name = "IS_BACK")
    public Integer getIsBack() {
        return isBack;
    }

    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
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
    @Column(name = "SALE_PERSION")
    public String getSalePersion() {
        return salePersion;
    }

    public void setSalePersion(String salePersion) {
        this.salePersion = salePersion;
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
    @Column(name = "IS_ABNORMAL")
    public Integer getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(Integer isAbnormal) {
        this.isAbnormal = isAbnormal;
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

    @JSONField(serialize = false)
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_PRESCO_ID")
    public Presco getPresco() {
        return presco;
    }

    public void setPresco(Presco presco) {
        this.presco = presco;
    }

    @JSONField(serialize = false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="led")
    public List<ArriveGoods> getArriveGoods() {
        return arriveGoods;
    }

    public void setArriveGoods(List<ArriveGoods> arriveGoods) {
        this.arriveGoods = arriveGoods;
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


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="led")
    public List<LedReceivingParty> getLedReceivingParties() {
        return ledReceivingParties;
    }
    public void setLedReceivingParties(List<LedReceivingParty> ledReceivingParties) {
        this.ledReceivingParties = ledReceivingParties;
    }

    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="led")
    public List<LedProduct> getLedProducts() {
        return ledProducts;
    }
    public void setLedProducts(List<LedProduct> ledProducts) {
        this.ledProducts = ledProducts;
    }



    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "leds",cascade = {CascadeType.MERGE})
    public List<Shipment> getShipments() {
        return shipments;
    }
    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_SINGLE_ID")
    public Single getSingle() {
        return single;
    }
    public void setSingle(Single single) {
        this.single = single;
    }

    @Column(name = "AGENT")
    public Double getAgent() {
        return agent;
    }

    public void setAgent(Double agent) {
        this.agent = agent;
    }
    @Column(name = "ARRIVE_PAY")
    public Double getArrivePay() {
        return arrivePay;
    }

    public void setArrivePay(Double arrivePay) {
        this.arrivePay = arrivePay;
    }
}
