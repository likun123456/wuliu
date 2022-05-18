package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 订单追踪表
 * @author 奇趣源码
 * @create 2018-01-25
 */
@Entity(name = "JC_ORDER_TRACK")
public class OrderTrack extends BaseEntityNoCode implements Serializable{
    private Order order;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;//追踪
    private String event;

    private Logger log = Logger.getLogger(OrderTrack.class);//输出Log日志


    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
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
}
