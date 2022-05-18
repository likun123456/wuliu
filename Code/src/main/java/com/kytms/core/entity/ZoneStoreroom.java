package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
库房分区
 */

@Entity(name = "JC_ZONE_STOREROOM")
public class ZoneStoreroom extends BaseEntityNoCode implements Serializable {

    private Organization organization;//组织机构

    private List<Order> orders;

    private final Logger log = Logger.getLogger(ZoneStoreroom.class);//输出Log日志


    @JSONField(serialize=false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "zoneStoreroom")
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
