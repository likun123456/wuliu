package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/11.
 * 承运商表
 */
@Entity(name = "JC_CARRIER")
public class Carrier extends BaseEntity implements Serializable {
    private String iphone;//承运商电话
    private String principal;//负责人
    private String principalIphone;//负责人电话
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;//合同开始日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp endTime; //合同结束日期
    private Integer paymentDays; //账期
    private String address; //联系地址
    private Integer carrierType;//承运商类型
    private Integer isEnabled;//是否启用
    @JSONField(serialize=false)
    private List<Shipment> shipmentList;//运单表
    private Organization organization;//组织机构
    private Organization zdOrganization;//指定组织机构（承运商类型为自有时需要）



    private Logger log = Logger.getLogger(Carrier.class);//输出Log日志
    private Rule rule;//规则表


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_RULE_ID")
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Column(name = "IPHONE")
    public String getIphone() {
        return iphone;
    }
    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    @Column(name = "PRINCIPAL")
    public String getPrincipal() {
        return principal;
    }
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Column(name = "PRINCIPAL_IPHONE")
    public String getPrincipalIphone() {
        return principalIphone;
    }
    public void setPrincipalIphone(String principalIphone) {
        this.principalIphone = principalIphone;
    }

    @Column(name = "START_TIME")
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TIME")
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Column(name = "PAYMENT_DAYS")
    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }
    @Column(name = "CARRIER_TYPE")
    public Integer getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(Integer carrierType) {
        this.carrierType = carrierType;
    }
    @Column(name = "IS_ENABLED")
    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }


    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }




    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="carrier")
    public List<Shipment> getShipmentList() {
        return shipmentList;
    }
    public void setShipmentList(List<Shipment> shipmentList) {
        this.shipmentList = shipmentList;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "ZD_ORGANIZATION_ID")
    public Organization getZdOrganization() {
        return zdOrganization;
    }
    public void setZdOrganization(Organization zdOrganization) {
        this.zdOrganization = zdOrganization;
    }
}
