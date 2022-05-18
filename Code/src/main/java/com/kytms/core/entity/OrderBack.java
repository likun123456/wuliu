package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 运单回单
 * @author 奇趣源码
 * @create 2018-01-29
 */
@Entity(name = "JC_ORDER_BACK")
public class OrderBack extends BaseEntityNoCode implements Serializable {
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time; //签收时间
    private int backNumber;//回单份数
    private int singNumber;//签收数量
    private int backType;//回单方式
    private String expressName;//快递名称
    private String expressCode;//快递单号
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp backTime;//回单时间
    private int isSubmit;//是否交单
    private int isReceive;//是否接收
    private Organization organization;
    private Order order;
    private int isUpload;


    private Logger log = Logger.getLogger(OrderBack.class);//输出Log日志

    @Column(name = "IS_UPLOAD",columnDefinition="INT default 0")
    public int getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(int isUpload) {
        this.isUpload = isUpload;
    }

    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "BACK_NUMBER")
    public int getBackNumber() {
        return backNumber;
    }
    public void setBackNumber(int backNumber) {
        this.backNumber = backNumber;
    }

    @Column(name = "SING_NUMBER",columnDefinition="INT default 0")
    public int getSingNumber() {
        return singNumber;
    }
    public void setSingNumber(int singNumber) {
        this.singNumber = singNumber;
    }

    @Column(name = "BACK_TYPE",columnDefinition="INT default 0")
    public int getBackType() {
        return backType;
    }
    public void setBackType(int backType) {
        this.backType = backType;
    }

    @Column(name = "EXPRESS_NAME",length = 50)
    public String getExpressName() {
        return expressName;
    }
    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    @Column(name = "EXPRESS_CODE",length = 50)
    public String getExpressCode() {
        return expressCode;
    }
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    @Column(name = "BACK_TIME")
    public Timestamp getBackTime() {
        return backTime;
    }
    public void setBackTime(Timestamp backTime) {
        this.backTime = backTime;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


    @OneToOne
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "IS_SUBMIT")
    public int getIsSubmit() {
        return isSubmit;
    }
    public void setIsSubmit(int isSubmit) {
        this.isSubmit = isSubmit;
    }

    @Column(name = "IS_RECEIVE")
    public int getIsReceive() {
        return isReceive;
    }
    public void setIsReceive(int isReceive) {
        this.isReceive = isReceive;
    }
}
