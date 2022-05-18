package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author:sundezeng
 * @Date:2018/10/29
 * 货品到站记录
 */
@Entity(name = "JC_ORDER_ARRIVE")
public class OrderArrive extends BaseEntityNoCode implements Serializable {

    private Organization organization;
    private Order order;
    private Led led;


    private Logger log = Logger.getLogger(OrderArrive.class);//输出Log日志



    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp arriveTime;//运抵时间

    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_LED_ID")
    public Led getLed() {
        return led;
    }

    public void setLed(Led led) {
        this.led = led;
    }

    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    @Column(name = "ARRIVE_TIME")
    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }
}
