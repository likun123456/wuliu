package com.kytms.core.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.kytms.orderabnormal.Abnormal;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/*
* 提派单
*   提派单(提货单)
  状态： 0 新增初始状态
        1 保存（可删除）
        2 装载
        3、执行中  向预录单、订单传送执行状态（预录单是提货中，订单是派送中）
        4、执行结束  向预录单传送执行状态

 */
@Entity(name = "JC_SINGLE")
public class Single extends BaseEntity implements Serializable{
    @Abnormal(name = "计划日期")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp dateBilling; //计划日期
    private String  toSendInfo;//提派信息
    @Abnormal(name = "结算方式",dataBookName = "Clearing")
    private Integer accountType;//结算方式
    private Integer number;//件数
    private Double weight;//重量
    private Vehicle vehicle;//车型
    private Double volume;//体积
    private Double agent; //代收货款
    private Double arrivePay =0.0;//到付款
    private String reBubbleRatio;//重泡比
    @Abnormal(name = "承运商",clszz =Carrier.class)
    private Carrier carrier;//承运商
    @Abnormal(name = "承运类型",dataBookName = "Cytp")
    private Integer carrierType;//承运类型
    private String vehicleHead;//车牌号
    @Abnormal(name = "司机")
    private String driver;//司机
    private List<com.kytms.core.entity.Abnormal> abnormals;
    @Abnormal(name = "司机电话")
    private String driverIphone;//司机电话
    private String vehicleHeadTemporary;//临时车牌号
    private String driverTemporary;//临时司机
    private String driverIphoneTemporary;//临时司机电话
    private List<LedgerDetail> ledgerDetails;//台账明细
    private Integer isOverdueCarrier; //承运商是否超期
    private Integer isAbnormail; //是否有异常
    private List<Presco> prescos;//计划单
    private List<Led> leds;//待配送的订单
    private Organization organization;//组织机构
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planStartTime; //实际出发时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planEndTime; //实际结束时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planCilckStartTime; //实际出发点击时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planCilckEndTime; //实际结束点击时间

    private List<VerificationZb> verifications;//核销表
    private Integer printCount = 0;//打印次数

    private final Logger log = Logger.getLogger(Single.class);//输出Log日志

    //------------------------分割线--------------------------


    @Column(name = "PRINT_CONUT")
    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "single")
    public List<VerificationZb> getVerifications() {
        return verifications;
    }
    public void setVerifications(List<VerificationZb> verifications) {
        this.verifications = verifications;
    }


    @Column(name = "DATE_BILLING")
    public Timestamp getDateBilling() {
        return dateBilling;
    }

    public void setDateBilling(Timestamp dateBilling) {
        this.dateBilling = dateBilling;
    }
    @Column(name = "TO_SEND_INFO")
    public String getToSendInfo() {
        return toSendInfo;
    }

    public void setToSendInfo(String toSendInfo) {
        this.toSendInfo = toSendInfo;
    }

    @Column(name = "ACCOUNT_TYPE")
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
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
    @Column(name = "REBUBBLE_RATIO")
    public String getReBubbleRatio() {
        return reBubbleRatio;
    }

    public void setReBubbleRatio(String reBubbleRatio) {
        this.reBubbleRatio = reBubbleRatio;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_CARRIER_ID")
    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }
    @Column(name = "VEHICLE_HEAD")
    public String getVehicleHead() {
        return vehicleHead;
    }

    public void setVehicleHead(String vehicleHead) {
        this.vehicleHead = vehicleHead;
    }
    @Column(name = "DRIVER")
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Column(name = "DRIVER_IPHONE")
    public String getDriverIphone() {
        return driverIphone;
    }

    public void setDriverIphone(String driverIphone) {
        this.driverIphone = driverIphone;
    }


//    @Column(name = "COST")
//    public double getCost() {
//        return cost;
//    }
//
//    public void setCost(double cost) {
//        this.cost = cost;
//    }
//    @Column(name = "RATE")
//    public double getRate() {
//        return rate;
//    }
//
//    public void setRate(double rate) {
//        this.rate = rate;
//    }
//
//    @Column(name = "TAXES")
//    public double getTaxes() {
//        return taxes;
//    }
//
//    public void setTaxes(double taxes) {
//        this.taxes = taxes;
//    }

    @JSONField(serialize=false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "single")
    public List<Presco> getPrescos() {
        return prescos;
    }

    public void setPrescos(List<Presco> prescos) {
        this.prescos = prescos;
    }
    @JSONField(serialize=false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "single")
    public List<Led> getLeds() {
        return leds;
    }

    public void setLeds(List<Led> leds) {
        this.leds = leds;
    }

    @Column(name = "VEHICLEHEAD_TEMPORARY")
    public String getVehicleHeadTemporary() {
        return vehicleHeadTemporary;
    }

    public void setVehicleHeadTemporary(String vehicleHeadTemporary) {
        this.vehicleHeadTemporary = vehicleHeadTemporary;
    }

    @Column(name = "DRIVER_TEMPORARY")
    public String getDriverTemporary() {
        return driverTemporary;
    }

    public void setDriverTemporary(String driverTemporary) {
        this.driverTemporary = driverTemporary;
    }


    @ManyToOne(cascade={CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    @Column(name = "CARRIER_TYPE")
    public Integer getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
        this.carrierType = carrierType;
    }

    @Column(name = "DRIVER_IPHONE_TEMPORARY")
    public String getDriverIphoneTemporary() {
        return driverIphoneTemporary;
    }

    public void setDriverIphoneTemporary(String driverIphoneTemporary) {
        this.driverIphoneTemporary = driverIphoneTemporary;
    }
    @Column(name = "PLAN_START_TIME")
    public Timestamp getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Timestamp planStartTime) {
        this.planStartTime = planStartTime;
    }
    @Column(name = "PLAN_END_TIME")
    public Timestamp getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Timestamp planEndTime) {
        this.planEndTime = planEndTime;
    }
    @Column(name = "PLAN_CILCK_START_TIME")
    public Timestamp getPlanCilckStartTime() {
        return planCilckStartTime;
    }

    public void setPlanCilckStartTime(Timestamp planCilckStartTime) {
        this.planCilckStartTime = planCilckStartTime;
    }
    @Column(name = "PLAN_CILCK_END_TIME")
    public Timestamp getPlanCilckEndTime() {
        return planCilckEndTime;
    }

    public void setPlanCilckEndTime(Timestamp planCilckEndTime) {
        this.planCilckEndTime = planCilckEndTime;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_VEHICLE_ID")
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="single")
    public List<LedgerDetail> getLedgerDetails() {
        return ledgerDetails;
    }

    public void setLedgerDetails(List<LedgerDetail> ledgerDetails) {
        this.ledgerDetails = ledgerDetails;
    }
    @Column(name = "IS_OVERDUE_CARRIER")
    public Integer getIsOverdueCarrier() {
        return isOverdueCarrier;
    }

    public void setIsOverdueCarrier(Integer isOverdueCarrier) {
        this.isOverdueCarrier = isOverdueCarrier;
    }
    @Column(name = "IS_ABNORMAIL")
    public Integer getIsAbnormail() {
        return isAbnormail;
    }

    public void setIsAbnormail(Integer isAbnormail) {
        this.isAbnormail = isAbnormail;
    }
    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="single")
    public List<com.kytms.core.entity.Abnormal> getAbnormals() {
        return abnormals;
    }

    public void setAbnormals(List<com.kytms.core.entity.Abnormal> abnormals) {
        this.abnormals = abnormals;
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
