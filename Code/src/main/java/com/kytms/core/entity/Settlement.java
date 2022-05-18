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
 * 创建时间： 2018/5/23.
 * 结算总表
 */
@Entity(name = "JC_SETTLEMENT")
public class Settlement extends BaseEntity implements Serializable {
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;//结算时间
    private int way;//结算方式
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp beginTime;//开始日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp endTime;//结束日期
    private Organization organization;//组织机构
    private int jsStatus;//结算状态
//    private List<Order> orders;
    private List<Shipment> shipments;


    private final Logger log = Logger.getLogger(Settlement.class);//输出Log日志



    @Column(name = "JS_STATUS")
    public int getJsStatus() {
        return jsStatus;
    }
    public void setJsStatus(int jsStatus) {
        this.jsStatus = jsStatus;
    }

//    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="settlement")
//    public List<Order> getOrders() {
//        return orders;
//    }
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }

    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="settlement")
    public List<Shipment> getShipments() {
        return shipments;
    }
    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "BEGIN_TIME")
    public Timestamp getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    @Column(name = "END_TIME")
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Column(name = "WAY")
    public int getWay() {
        return way;
    }
    public void setWay(int way) {
        this.way = way;
    }
}
