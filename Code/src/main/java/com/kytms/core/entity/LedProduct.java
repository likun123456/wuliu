package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 订单货品类型
 * @author 奇趣源码
 * @create 2018-01-02
 */
@Entity(name = "JC_LEG_PRODUCT")
public class LedProduct extends BaseEntityNoCode implements Serializable {
    private double weight; //重量
    private double volume; //体积
    private double value; //货值
    private Integer number; //数量
    private Double jzWeight;// 计重
    private String unit;//单位
    private Led led;

    private Logger log = Logger.getLogger(LedProduct.class);//输出Log日志

//----------------------分割线------------------------------


    @Column(name = "JZ_WEIGHT")
    public Double getJzWeight() {
        return jzWeight;
    }

    public void setJzWeight(Double jzWeight) {
        this.jzWeight = jzWeight;
    }




    @Column(name = "WEIGHT")
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Column(name = "VOLUME")
    public double getVolume() {
        return volume;
    }
    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Column(name = "VALUE")
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    @Column(name = "NUMBER")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_LED_ID")
    public Led getLed() {
        return led;
    }
    public void setLed(Led led) {
        this.led = led;
    }

    @Column(name = "UNIT", length = 100)
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
