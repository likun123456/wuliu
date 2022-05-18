package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 运单模板
 * @author 陈小龙
 * @create 2018-04-09
 */
@Entity(name = "JC_SHIPMENT_TEMPLATE")
public class ShipmentTemplate extends BaseEntity implements Serializable {
    private Organization organization;//组织机构
    private Carrier carrier; //承运商
    private Vehicle vehicle;//车型
    private int carrierType; //承运商类型
    private int feeType;//付款方式
    private int shipmentMethod;//运输方式
    private int operationPattern;  //运作模式
    private String tan; //运输协议号
    private String liense;//车牌号
    private String outDriver;//司机名称
    private List<LedgerDetail> templateLedgers; //台账
    private VehicleHead vehicleHead; //车头

    private Driver driver;//司机

    private String outIphpne;//司机电话
    private String orgIds;//经由站组织机构集合
    private Organization fromOrganization;//起运站
    private Organization toOrganization;//目的站
    private List<BerthStand> berthStand;//停靠站


    private final Logger log = Logger.getLogger(ShipmentTemplate.class);//输出Log日志


    @Column(name = "OUT_IPHPNE")
    public String getOutIphpne() {
        return outIphpne;
    }
    public void setOutIphpne(String outIphpne) {
        this.outIphpne = outIphpne;
    }

    @Column(name = "ORG_IDS", length = 300)
    public String getOrgIds() {
        return orgIds;
    }
    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_FROM_ORGANIZATION_ID")
    public Organization getFromOrganization() {
        return fromOrganization;
    }
    public void setFromOrganization(Organization fromOrganization) {
        this.fromOrganization = fromOrganization;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_TO_ORGANIZATION_ID")
    public Organization getToOrganization() {
        return toOrganization;
    }
    public void setToOrganization(Organization toOrganization) {
        this.toOrganization = toOrganization;
    }


    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Column(name = "CARRIER_TYPE")
    public int getCarrierType() {
        return carrierType;
    }
    public void setCarrierType(int carrierType) {
        this.carrierType = carrierType;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_CARRIER_ID")
    public Carrier getCarrier() {
        return carrier;
    }
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    @Column(name = "FEE_TYPE")
    public int getFeeType() {
        return feeType;
    }
    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_VEHICLE_ID")
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Column(name = "SHIPMENT_METHOD")
    public int getShipmentMethod() {
        return shipmentMethod;
    }
    public void setShipmentMethod(int shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }


    @Column(name = "TAN")
    public String getTan() {
        return tan;
    }
    public void setTan(String tan) {
        this.tan = tan;
    }

    @Column(name = "LIENSE",length = 25)
    public String getLiense() {
        return liense;
    }
    public void setLiense(String liense) {
        this.liense = liense;
    }

    @Column(name = "OUT_DRIVER", length = 50)
    public String getOutDriver() {
        return outDriver;
    }
    public void setOutDriver(String outDriver) {
        this.outDriver = outDriver;
    }

    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="shipmentTemplate")
    public List<LedgerDetail> getTemplateLedgers() {
        return templateLedgers;
    }
    public void setTemplateLedgers(List<LedgerDetail> templateLedgers) {
        this.templateLedgers = templateLedgers;
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



    @Column(name = "OPERATION_PATTERN")
    public int getOperationPattern() {
        return operationPattern;
    }
    public void setOperationPattern(int operationPattern) {
        this.operationPattern = operationPattern;
    }

    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="shipmentTemplate")
    public List<BerthStand> getBerthStand() {
        return berthStand;
    }
    public void setBerthStand(List<BerthStand> berthStand) {
        this.berthStand = berthStand;
    }
}
