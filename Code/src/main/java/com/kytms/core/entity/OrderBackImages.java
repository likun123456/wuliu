package com.kytms.core.entity;


import javax.persistence.*;
import java.io.Serializable;


/**
 * 奇趣源码商城 www.qiqucode.com
 * 运单回单
 * @author 奇趣源码
 * @create 2018-01-29
 */
@Entity(name = "JC_ORDER_BACK_IMAGES")
public class OrderBackImages extends BaseEntityNoCode implements Serializable {
    private String url;
    private OrderBack orderBack;

     //-------------------------------------


    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORDER_BACK_ID")
    public OrderBack getOrderBack() {
        return orderBack;
    }

    public void setOrderBack(OrderBack orderBack) {
        this.orderBack = orderBack;
    }
}
