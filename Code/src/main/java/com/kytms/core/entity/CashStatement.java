package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

/*
 在库
 */
@Entity(name = "JC_CASH_STATEMENT")
public class CashStatement extends BaseEntityNoCode implements Serializable {

    private ZoneStoreroom zoneStoreroom;//库分区

    private Led led; //分段运单

    private Order order;//订单

    private Integer type;//出入库状态


    private Logger log = Logger.getLogger(CashStatement.class);//输出Log日志



    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ZONE_STOREROOM_ID")
    public ZoneStoreroom getZoneStoreroom() {
        return zoneStoreroom;
    }

    public void setZoneStoreroom(ZoneStoreroom zoneStoreroom) {
        this.zoneStoreroom = zoneStoreroom;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_LED_ID")
    public Led getLed() {
        return led;
    }

    public void setLed(Led led) {
        this.led = led;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
