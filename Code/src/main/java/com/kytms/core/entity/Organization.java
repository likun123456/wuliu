package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.kytms.core.annotation.Tree;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 组织机构
 * @author 奇趣源码
 * @create 2017-11-17
 */
@Tree(name = "organizations")
@Entity(name = "JC_SYS_ORGANIZATION")
public class Organization extends BaseEntity implements Serializable {
    private int type;//组织机构类型
    private String  latitude;//经纬度
    private String  level;//级别
    private String principal; //负责人
    private String address; //地址
    private int orderCode; //订单编号序列
    private int shipmentCode; //运单编号序列
    private int dispatchCode; //派车编号序列
    private int prescoCode;//预录单序列
    private int vehiclePlanCode;
    private int isOverdueCarrier; //是否能使用超期承运商
    private int isOverdueContract; //是否能使用超期合同
    private List<ReceivingParty> receivingParties; // 收发货方
    private List<Organization> organizations; //组织机构
    private Organization organizationl; //自关联
    private List<User> users; //用户表
    private List<Zone> zones; //行政区域

    private List<Customer> customers; //合同信息
    private List<FeeTypeContrast> feeTypeContrasts;//费用类型对比表


    private List<VehicleHead> vehicleHeads; //车头信息
    private List<Driver> drivers;//司机管理

    @JSONField(serialize=false)
    private List<Shipment> shipments; //运单表

    private List<Carrier> carriers;//承运商

    private List<FeeType> feeTypes;//费用类型
    private List<Settlement> settlements;//结算表

    private List<Presco> prescos;//预录单
    private List<VerificationZb> verificationZbs;//核销表


    private Logger log = Logger.getLogger(Organization.class);//输出Log日志

    //------------------------分界线-----------------------

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<FeeTypeContrast> getFeeTypeContrasts() {
        return feeTypeContrasts;
    }

    public void setFeeTypeContrasts(List<FeeTypeContrast> feeTypeContrasts) {
        this.feeTypeContrasts = feeTypeContrasts;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<VerificationZb> getVerificationZbs() {
        return verificationZbs;
    }

    public void setVerificationZbs(List<VerificationZb> verificationZbs) {
        this.verificationZbs = verificationZbs;
    }

    @Column(name = "PRESCO_CODE",columnDefinition="INT default 1")
    public int getPrescoCode() {
        return prescoCode;
    }
    public void setPrescoCode(int prescoCode) {
        this.prescoCode = prescoCode;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<Presco> getPrescos() {
        return prescos;
    }

    public void setPrescos(List<Presco> prescos) {
        this.prescos = prescos;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<Settlement> getSettlements() {
        return settlements;
    }
    public void setSettlements(List<Settlement> settlements) {
        this.settlements = settlements;
    }



    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<Carrier> getCarriers() {
        return carriers;
    }
    public void setCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
    }


    @Column(name = "TYPE",nullable = false, length = 5)
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "LATITUDE", length = 20)
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(name = "LEVEL",nullable = false, length = 50)
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    @JSONField(serialize=false)
    @OneToMany(targetEntity=Organization.class, mappedBy="organizationl",cascade={CascadeType.PERSIST },fetch= FetchType.LAZY)
    public List<Organization> getOrganizations() {
        return organizations;
    }
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name="ZID")
    public Organization getOrganizationl() {
        return organizationl;
    }
    public void setOrganizationl(Organization organizationl) {
        this.organizationl = organizationl;
    }

    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "organizations")
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Column(name = "PRINCIPAL", length = 50)
    public String getPrincipal() {
        return principal;
    }
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Column(name = "ADDRESS", length = 150)
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "IS_OVERDUE_CARRIER",columnDefinition="INT default 1")
    public int getIsOverdueCarrier() {
        return isOverdueCarrier;
    }
    public void setIsOverdueCarrier(int isOverdueCarrier) {
        this.isOverdueCarrier = isOverdueCarrier;
    }

    @Column(name = "IS_OVERDUE_CONTRACT",columnDefinition="INT default 1")
    public int getIsOverdueContract() {
        return isOverdueContract;
    }
    public void setIsOverdueContract(int isOverdueContract) {
        this.isOverdueContract = isOverdueContract;
    }

    @JSONField(serialize = false)
    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(name = "JC_ZONE_ORGANIZATION", joinColumns = @JoinColumn(name = "JC_SYS_ORGANIZATION_ID"), inverseJoinColumns = @JoinColumn(name = "JC_SYS_ZONE_ID"))
    public List<Zone> getZones() {
        return zones;
    }
    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    @OrderBy(value = "orderBy")
    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<ReceivingParty> getReceivingParties() {
        return receivingParties;
    }
    public void setReceivingParties(List<ReceivingParty> receivingParties) {
        this.receivingParties = receivingParties;
    }


    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<Customer> getCustomers() {
        return customers;
    }
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }



    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<VehicleHead> getVehicleHeads() {
        return vehicleHeads;
    }
    public void setVehicleHeads(List<VehicleHead> vehicleHeads) {
        this.vehicleHeads = vehicleHeads;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<Driver> getDrivers() {
        return drivers;
    }
    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }



    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="organization")
    public List<Shipment> getShipments() {
        return shipments;
    }
    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }


    @Column(name = "ORDER_CODE",columnDefinition="INT default 1")
    public int getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "SHIPMENT_CODE",columnDefinition="INT default 1")
    public int getShipmentCode() {
        return shipmentCode;
    }
    public void setShipmentCode(int shipmentCode) {
        this.shipmentCode = shipmentCode;
    }

    @Column(name = "DISPATCH_CODE",columnDefinition="INT default 1")
    public int getDispatchCode() {
        return dispatchCode;
    }
    public void setDispatchCode(int dispatchCode) {
        this.dispatchCode = dispatchCode;
    }

    @Column(name = "VEHICLE_PLAN_CODE",columnDefinition="INT default 1")
    public int getVehiclePlanCode() {
        return vehiclePlanCode;
    }
    public void setVehiclePlanCode(int vehiclePlanCode) {
        this.vehiclePlanCode = vehiclePlanCode;
    }

    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "organizations")
    public List<FeeType> getFeeTypes() {
        return feeTypes;
    }
    public void setFeeTypes(List<FeeType> feeTypes) {
        this.feeTypes = feeTypes;
    }
}
