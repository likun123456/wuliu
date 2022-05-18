package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author:InOrOutRecord
 * @Date:2018/10/22
 * 出入库记录表
 */
@Entity(name = "JC_INOROUTRECORD")
public class InOrOutRecord extends BaseEntityNoCode implements Serializable {
    private Organization organization;//组织机构
   // private Order order;//订单号
    private Integer type;//出入库标识 --0 入库，--1 出库
    private ZoneStoreroom zoneStoreroom;//在库位置
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;//出入库时间
    private Integer number;//数量
    private Double volume;//体积
    private Double weight;//重量
    private Led led;//分段订单


    private Logger log = Logger.getLogger(InOrOutRecord.class);//输出Log日志

    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_LED_ID")
    public Led getLed() {
        return led;
    }
    public void setLed(Led led) {
        this.led = led;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

//    @JSONField(serialize = false)
//    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
//    @JoinColumn(name = "JC_ORDER_ID")
//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ZONE_STOREROOM_ID")
    public ZoneStoreroom getZoneStoreroom() {
        return zoneStoreroom;
    }

    public void setZoneStoreroom(ZoneStoreroom zoneStoreroom) {
        this.zoneStoreroom = zoneStoreroom;
    }
    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    @Column(name = "NUMBER")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    @Column(name = "VOLUME")
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
    @Column(name = "WEIGHT")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
