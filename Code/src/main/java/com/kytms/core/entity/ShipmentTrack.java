package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 运单追踪表
 * @author 奇趣源码
 * @create 2018-01-25
 */
@Entity(name = "JC_SHIPMENT_TRACK")
public class ShipmentTrack extends BaseEntityNoCode implements Serializable{
    private Shipment shipment;//运单
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;//追踪时间
    private String event; //事件
    private int type;//类型
    private String person;//操作人

    private final Logger log = Logger.getLogger(ShipmentTrack.class);//输出Log日志


    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "EVENT", length = 500)
    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }

    @Column(name = "TYPE")
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "PERSON", length = 20)
    public String getPerson() {
        return person;
    }
    public void setPerson(String person) {
        this.person = person;
    }
}
