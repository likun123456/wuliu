package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/** 辽宁捷畅物流有限公司  集团信息部
 * 孙德增
 * Created by sundezeng on 2017/12/15.
 * 收发货方
 */
@Entity(name = "JC_RECEIVINGPARTY")
public class ReceivingParty extends BaseEntityNoCode implements Serializable {
    private String contactperson; //联系人
    private String iphone; //电话
    private String address; //地址
    private String ltl; //经纬度
    private String detailedAddress;//详细地址
    private int orderBy; //排序
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp requireArriveTime; //要求到达时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factArriveTime;//实际到达时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp planLeaveTime;//要求发运时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp factLeaveTime;//实际发运时间
    private Organization organization; //组织机构
    private Zone zone; //行政区域

    private final Logger log = Logger.getLogger(ReceivingParty.class);//输出Log日志

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
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ZONE_ID")
    public Zone getZone() {
        return zone;
    }
    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
