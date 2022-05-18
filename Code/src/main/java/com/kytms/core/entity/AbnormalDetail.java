package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 异常明细
 * @author 奇趣源码
 * @create 2018-02-22
 */
@Entity(name = "JC_ABNORMAL_DETAIL")
public class AbnormalDetail extends BaseEntityNoCode implements Serializable{
    private Abnormal abnormal; //关联类
    private String source; //字段CODE
    private String targer;//原来字段名称
    private String sourceValue;//原有值
    private String targerValue; //目标值
    private Logger log = Logger.getLogger(AbnormalDetail.class);//输出Log日志




    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ABNORMAL_ID")
    public Abnormal getAbnormal() {
        return abnormal;
    }
    public void setAbnormal(Abnormal abnormal) {
        this.abnormal = abnormal;
    }

    @Column(name = "SOURCE", length = 255)
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "TARGER", length = 255)
    public String getTarger() {
        return targer;
    }
    public void setTarger(String targer) {
        this.targer = targer;
    }

    @Column(name = "SOURCE_VALUE", length = 255)
    public String getSourceValue() {
        return sourceValue;
    }
    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }

    @Column(name = "TARGER_VALUE", length = 255)
    public String getTargerValue() {
        return targerValue;
    }
    public void setTargerValue(String targerValue) {
        this.targerValue = targerValue;
    }
}
