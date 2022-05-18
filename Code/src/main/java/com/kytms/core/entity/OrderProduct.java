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
@Entity(name = "JC_ORDER_PRODUCT")
public class OrderProduct extends BaseEntityNoCode implements Serializable {
    private Double weight; //重量
    private Double volume; //体积
    private Double value; //货值
    private Integer number; //数量
    private String unit;//单位
    private Double jzWeight;// 计重
    private Order order;//订单

    private Logger log = Logger.getLogger(OrderProduct.class);//输出Log日志

    //-------------------------------分割线-----------------------------


    @Column(name = "JZ_WEIGHT")
    public Double getJzWeight() {
        return jzWeight;
    }

    public void setJzWeight(Double jzWeight) {
        this.jzWeight = jzWeight;
    }

    @Column(name = "WEIGHT")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "VOLUME")
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Column(name = "VALUE")
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
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
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "UNIT", length = 100)
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
