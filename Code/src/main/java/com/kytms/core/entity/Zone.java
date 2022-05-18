package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 行政区域
 * @author 奇趣源码
 * @create 2017-12-04
 */
@Entity(name = "JC_ZONE")
public class Zone extends BaseEntity implements Serializable {
    private String level;
    private String latitude;
    private String cityCode;
    private Zone zone;
    private List<Zone> zones; //自关联
    private List<Organization> organizations; //组织机构
    private List<ReceivingParty> receivingParties;//收发货方
    private List<Customer> customers; //客户档案

    private final Logger log = Logger.getLogger(Zone.class);//输出Log日志

    @Column(name = "LEVEL", nullable = false, length = 20)
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "LATITUDE", length = 50)
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(name = "CITY_CODE", length = 20)
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @ManyToOne(cascade={CascadeType.REFRESH})
    @JoinColumn(name="ZID")
    public Zone getZone() {
        return zone;
    }
    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @OrderBy(value = "code")
    @JSONField(serialize=false)
    @OneToMany(targetEntity=Zone.class,cascade={CascadeType.REFRESH},mappedBy="zone",fetch= FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    public List<Zone> getZones() {
        return zones;
    }
    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "zones",cascade = {CascadeType.REFRESH})
    public List<Organization> getOrganizations() {
        return organizations;
    }
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="zone")
    public List<ReceivingParty> getReceivingParties() {
        return receivingParties;
    }
    public void setReceivingParties(List<ReceivingParty> receivingParties) {
        this.receivingParties = receivingParties;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="zone")
    public List<Customer> getCustomers() {
        return customers;
    }
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
