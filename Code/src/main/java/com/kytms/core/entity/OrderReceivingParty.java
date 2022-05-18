package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 订单收发货方
 * @author 奇趣源码
 * @create 2018-01-02
 */
@Entity(name = "JC_ORDER_RECEIVINGPARTY")
public class OrderReceivingParty extends BaseEntityNoCode {
    private String contactperson; //联系人
    private String iphone; //电话
    private String address; //地址
    private String ltl; //经纬度
    private String detailedAddress;//详细地址
    private int orderBy; //排序
    private int type;//收还是发 0是发 1是收
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp requireArriveTime; //要求到达时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factArriveTime;//实际到达时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planLeaveTime;//要求发运时间，这个字段存要求发运和要求到达时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factLeaveTime;//实际发运时间，这个字段存实际发运和实际到达时间
    private Zone zone; //行政区域
    private Order order; //所属订单

    private Logger log = Logger.getLogger(OrderReceivingParty.class);//输出Log日志

//-----------------------------分割线-------------------------------------------------


    @Column(name = "DETAILE_ADDRESS")
    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    @Column(name = "CONTACTPERSON",  length = 50)
    public String getContactperson() {
        return contactperson;
    }
    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    @Column(name = "IPHONE",  length = 50)
    public String getIphone() {
        return iphone;
    }
    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    @Column(name = "ADDRESS",  length = 150)
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "LTL",  length = 20)
    public String getLtl() {
        return ltl;
    }
    public void setLtl(String ltl) {
        this.ltl = ltl;
    }

    @Column(name = "ORDERBY")
    public int getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    @Column(name = "REQUIRE_ARRIVE_TIME")
    public Timestamp getRequireArriveTime() {
        return requireArriveTime;
    }
    public void setRequireArriveTime(Timestamp requireArriveTime) {
        this.requireArriveTime = requireArriveTime;
    }

    @Column(name = "FACT_ARRIVE_TIME")
    public Timestamp getFactArriveTime() {
        return factArriveTime;
    }
    public void setFactArriveTime(Timestamp factArriveTime) {
        this.factArriveTime = factArriveTime;
    }

    @Column(name = "PLAN_LEAVE_TIME")
    public Timestamp getPlanLeaveTime() {
        return planLeaveTime;
    }
    public void setPlanLeaveTime(Timestamp planLeaveTime) {
        this.planLeaveTime = planLeaveTime;
    }

    @Column(name = "FACT_LEAVE_TIME")
    public Timestamp getFactLeaveTime() {
        return factLeaveTime;
    }
    public void setFactLeaveTime(Timestamp factLeaveTime) {
        this.factLeaveTime = factLeaveTime;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ZONE_ID")
    public Zone getZone() {
        return zone;
    }
    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "TYPE")
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}
