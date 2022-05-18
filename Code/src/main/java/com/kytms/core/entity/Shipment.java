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
 * 运单管理
 * @author 奇趣源码
 * @create 2018-01-02
 * 按钮： 新增、删除（逻辑删除）、修改、发运、批量确认
 * 状态：保存、配载中、确认、已发运、
 */
@Entity(name = "JC_SHIPMENT")
public class Shipment extends BaseEntity implements Serializable {
    private Organization organization;//组织机构
    @Abnormal(name = "运单日期")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time; // 运单日期
    @Abnormal(name = "发运单号")
    private String tan; //发运单号
    @Abnormal(name = "运作模式",dataBookName = "OperationPattern")
    private Integer operationPattern;  //运作模式  -- 自营车辆    ----干线外包   ----外包中转   ---
    @Abnormal(name = "承运商",clszz = Carrier.class)
    private Carrier carrier; //承运商
    @Abnormal(name = "承运商类型",dataBookName = "CarrierType")
    private Integer carrierType; //承运商类型
    @Abnormal(name = "车型",clszz = Vehicle.class,clazzName="code")
    private Vehicle vehicle;//车型  指定车型
    @Abnormal(name = "回单分数")
    private Integer backNumber;//回单份数
    //private String loadingInfo; //装卸信息
    @Abnormal(name = "回单分数",dataBookName = "TransportMenth")
    private Integer shipmentMethod;//运输方式
    @Abnormal(name = "要求发运日期")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planLeaveTime;//要求发运日期
    @Abnormal(name = "要求到车日期")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp requireArriveTime; //要求到车日期
    @Abnormal(name = "付款方式",dataBookName = "Clearing")
    private Integer feeType;//付款方式

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factArriveTime;//实际到达时间

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factLeaveTime;//实际发运时间

    @Abnormal(name = "车头",clazzName="code",clszz = VehicleHead.class)
    private VehicleHead vehicleHead; //车头
    @Abnormal(name = "外部车牌号")
    private String liense;//外部车牌号
    @Abnormal(name = "外部司机名称")
    private String outDriver;//外部司机名称
    @Abnormal(name = "外部司机电话")
    private String outIphpne;//外部司机电话
    @Abnormal(name = "司机",clszz = Driver.class)
    private Driver driver;//司机

    private Integer carriageIsExceed;//合同是否超期

    @JSONField(serialize=false)
    private ShipmentBack shipmentBack;//运单回单

    private List<Led> leds;//分段订单
    private Integer isAbnormal; //是否有异常



//    private int loadingMethod;//配载方式
//    private int transportPro;//运输性质

    private Double number = 0.00;//件数
    private Double weight = 0.00;//重量
    private Double volume = 0.00; //体积
    private Double value = 0.00;//货值
    private Double jzWeight = 0.00;//计重
    private Integer refuse;//拒收标记
    private String orgIds; //经由站组织机构集合
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp actionTime;//实际操作时间

    private Organization fromOrganization;//起运站
    private Organization newOrganization;//当前站
    private Organization nextOrganization;//下一站
    private Organization toOrganization;//目的站

    private List<BerthStand> berthStand;//停靠站


    @JSONField(serialize=false)
    private List<VehicleArrive> vehicleArrives;//到货车辆

    private String relatebill1;//发运单号
    private String orderCode; //订单号
    private List<LedgerDetail> ledgerDetails;//台账明细

    @JSONField(serialize=false)
    private Settlement settlement;//结算

    private List<com.kytms.core.entity.Abnormal> abnormals;


    private String orgNameList;//途径站显示

    private List<VerificationZb> verifications;//核销表
    private List<Led> ledsss;//分段订单
    private List<Apportionment> apportionments;//成本分摊

    private int is_inoutcome;//是否收入成本确认

    private Rule rule;//规则表


    private final Logger log = Logger.getLogger(Shipment.class);//输出Log日志

    //------------------------分割线--------------------------
    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_RULE_ID")
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Column(name = "IS_INOUTCOME")
    public int getIs_inoutcome() {
        return is_inoutcome;
    }
    public void setIs_inoutcome(int is_inoutcome) {
        this.is_inoutcome = is_inoutcome;
    }

    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "shipment")
    public List<Apportionment> getApportionments() {
        return apportionments;
    }
    public void setApportionments(List<Apportionment> apportionments) {
        this.apportionments = apportionments;
    }

    @Column(name = "JZ_WEIGHT")
    public Double getJzWeight() {
        return jzWeight;
    }

    public void setJzWeight(Double jzWeight) {
        this.jzWeight = jzWeight;
    }

    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "shipment")
    public List<Led> getLedsss() {
        return ledsss;
    }

    public void setLedsss(List<Led> ledsss) {
        this.ledsss = ledsss;
    }

    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "shipment")
    public List<VerificationZb> getVerifications() {
        return verifications;
    }
    public void setVerifications(List<VerificationZb> verifications) {
        this.verifications = verifications;
    }

    @Column(name = "ORGNAME_LIST",length = 255)
    public String getOrgNameList() {
        return orgNameList;
    }
    public void setOrgNameList(String orgNameList) {
        this.orgNameList = orgNameList;
    }

    @Column(name = "OUT_IPHPNE")
    public String getOutIphpne() {
        return outIphpne;
    }
    public void setOutIphpne(String outIphpne) {
        this.outIphpne = outIphpne;
    }


    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="shipment")
    public List<BerthStand> getBerthStand() {
        return berthStand;
    }
    public void setBerthStand(List<BerthStand> berthStand) {
        this.berthStand = berthStand;
    }


    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="shipment")
    public List<LedgerDetail> getLedgerDetails() {
        return ledgerDetails;
    }
    public void setLedgerDetails(List<LedgerDetail> ledgerDetails) {
        this.ledgerDetails = ledgerDetails;
    }

    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SETTLEMENT_ID")
    public Settlement getSettlement() {
        return settlement;
    }
    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }



    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_CARRIER_ID")
    public Carrier getCarrier() {
        return carrier;
    }
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }


    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_VEHICLE_ID")
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }



    @Column(name = "PLAN_LEAVE_TIME")
    public Timestamp getPlanLeaveTime() {
        return planLeaveTime;
    }
    public void setPlanLeaveTime(Timestamp planLeaveTime) {
        this.planLeaveTime = planLeaveTime;
    }

    @Column(name = "REQUIRE_ARRIVE_TIME")
    public Timestamp getRequireArriveTime() {
        return requireArriveTime;
    }
    public void setRequireArriveTime(Timestamp requireArriveTime) {
        this.requireArriveTime = requireArriveTime;
    }

    @Column(name = "OPERATION_PATTERN")
    public Integer getOperationPattern() {
        return operationPattern;
    }

    public void setOperationPattern(Integer operationPattern) {
        this.operationPattern = operationPattern;
    }
    @Column(name = "CARRIER_TYPE")
    public Integer getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
        this.carrierType = carrierType;
    }
    @Column(name = "BACK_NUMBER")
    public Integer getBackNumber() {
        return backNumber;
    }

    public void setBackNumber(Integer backNumber) {
        this.backNumber = backNumber;
    }
    @Column(name = "SHIPMENT_METHOD")
    public Integer getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(Integer shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }
    @Column(name = "FEE_TYPE")
    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }
    @Column(name = "CARRIAGE_IS_EXCEED")
    public Integer getCarriageIsExceed() {
        return carriageIsExceed;
    }

    public void setCarriageIsExceed(Integer carriageIsExceed) {
        this.carriageIsExceed = carriageIsExceed;
    }
    @Column(name = "IS_ABNORMAL",columnDefinition="INT default 0")
    public Integer getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(Integer isAbnormal) {
        this.isAbnormal = isAbnormal;
    }
    @Column(name = "NUMBER")
    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
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

    @Column(name = "REFUSE")
    public Integer getRefuse() {
        return refuse;
    }
    public void setRefuse(Integer refuse) {
        this.refuse = refuse;
    }


    @Column(name = "TAN")
    public String getTan() {
        return tan;
    }
    public void setTan(String tan) {
        this.tan = tan;
    }


    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "FACT_ARRIVE_TIME")
    public Timestamp getFactArriveTime() {
        return factArriveTime;
    }
    public void setFactArriveTime(Timestamp factArriveTime) {
        this.factArriveTime = factArriveTime;
    }

    @Column(name = "FACT_LEAVER_TIME")
    public Timestamp getFactLeaveTime() {
        return factLeaveTime;
    }
    public void setFactLeaveTime(Timestamp factLeaveTime) {
        this.factLeaveTime = factLeaveTime;
    }


    @JSONField(serialize = false)
    @JoinTable(name = "JC_LED_SHIPMENT", joinColumns = @JoinColumn(name = "JC_SHIPMENT_ID"), inverseJoinColumns = @JoinColumn(name = "JC_LED_ID"))
    @ManyToMany(cascade = {CascadeType.PERSIST})
    public List<Led> getLeds() {
        return leds;
    }
    public void setLeds(List<Led> leds) {
        this.leds = leds;
    }




    @JSONField(serialize=false)
    @OneToOne(mappedBy = "shipment",cascade = CascadeType.REMOVE)
    public ShipmentBack getShipmentBack() {
        return shipmentBack;
    }
    public void setShipmentBack(ShipmentBack shipmentBack) {
        this.shipmentBack = shipmentBack;
    }



    @Column(name = "LIENSE",length = 25)
    public String getLiense() {
        return liense;
    }
    public void setLiense(String liense) {
        this.liense = liense;
    }



    @Column(name = "RELATEBILL1", length = 50)
    public String getRelatebill1() {
        return relatebill1;
    }
    public void setRelatebill1(String relatebill1) {
        this.relatebill1 = relatebill1;
    }

    @Column(name = "ORDER_CODE", length = 30)
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "OUT_DRIVER", length = 50)
    public String getOutDriver() {
        return outDriver;
    }
    public void setOutDriver(String outDriver) {
        this.outDriver = outDriver;
    }


    @OneToOne
    @JoinColumn(name = "JC_VEHICLE_HEAD_ID")
    public VehicleHead getVehicleHead() {
        return vehicleHead;
    }
    public void setVehicleHead(VehicleHead vehicleHead) {
        this.vehicleHead = vehicleHead;
    }


    @OneToOne
    @JoinColumn(name = "JC_DRIVER_ID")
    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }


    @Column(name = "ACTION_TIME")
    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_FROM_ORGANIZATION_ID")
    public Organization getFromOrganization() {
        return fromOrganization;
    }

    public void setFromOrganization(Organization fromOrganization) {
        this.fromOrganization = fromOrganization;
    }


    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_NEW_ORGANIZATION_ID")
    public Organization getNewOrganization() {
        return newOrganization;
    }

    public void setNewOrganization(Organization newOrganization) {
        this.newOrganization = newOrganization;
    }
    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_NEXT_ORGANIZATION_ID")
    public Organization getNextOrganization() {
        return nextOrganization;
    }

    public void setNextOrganization(Organization nextOrganization) {
        this.nextOrganization = nextOrganization;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_TO_ORGANIZATION_ID")
    public Organization getToOrganization() {
        return toOrganization;
    }

    public void setToOrganization(Organization toOrganization) {
        this.toOrganization = toOrganization;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "shipment")
    public List<VehicleArrive> getVehicleArrives() {
        return vehicleArrives;
    }

    public void setVehicleArrives(List<VehicleArrive> vehicleArrives) {
        this.vehicleArrives = vehicleArrives;
    }
    @Column(name = "ORG_IDS", length = 300)
    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="shipment")
    public List<com.kytms.core.entity.Abnormal> getAbnormals() {
        return abnormals;
    }

    public void setAbnormals(List<com.kytms.core.entity.Abnormal> abnormals) {
        this.abnormals = abnormals;
    }
}

