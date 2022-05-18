package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/*
核销记录表
 */

@Entity(name = "JC_VERIFICATION_RECORD")
public class VerificationRecord extends BaseEntityNoCode implements Serializable {


    private Double zmoney;//总金额
    private Double yhxmoney;//已核销金额
    private Double hxmoney;//核销金额
    private Double whxmoney;//未核销金额
    private Double syhxmoney;//剩余核销金额

    private String operator;//核销人
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp hxTime;//核销时间
    private Integer hxtype;//核销类型？？？

    private VerificationZb verificationZb;//核销总表

    private final Logger log = Logger.getLogger(VerificationRecord.class);//输出Log日志


    //------------------------分割线--------------------------


    @Column(name = "YHX_MONEY")
    public Double getYhxmoney() {
        return yhxmoney;
    }

    public void setYhxmoney(Double yhxmoney) {
        this.yhxmoney = yhxmoney;
    }

    @Column(name = "Z_MONEY")
    public Double getZmoney() {
        return zmoney;
    }

    public void setZmoney(Double zmoney) {
        this.zmoney = zmoney;
    }

    @Column(name = "HX_MONEY")
    public Double getHxmoney() {
        return hxmoney;
    }

    public void setHxmoney(Double hxmoney) {
        this.hxmoney = hxmoney;
    }
    @Column(name = "WHX_MONEY")
    public Double getWhxmoney() {
        return whxmoney;
    }

    public void setWhxmoney(Double whxmoney) {
        this.whxmoney = whxmoney;
    }
    @Column(name = "HX_TYPE")
    public Integer getHxtype() {
        return hxtype;
    }

    public void setHxtype(Integer hxtype) {
        this.hxtype = hxtype;
    }

    @JSONField(serialize = false)
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_VERIFICATIONZB_ID")

    public VerificationZb getVerificationZb() {
        return verificationZb;
    }

    public void setVerificationZb(VerificationZb verificationZb) {
        this.verificationZb = verificationZb;
    }





    @Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "HX_TIME")
    public Timestamp getHxTime() {
        return hxTime;
    }

    public void setHxTime(Timestamp hxTime) {
        this.hxTime = hxTime;
    }



}
