package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 运单回单明细
 * @author 奇趣源码
 * @create 2018-02-12
 */
@Entity(name = "JC_SHIPMENT_BACK_DETAIL")
public class ShipmentBackDetail extends BaseEntityNoCode implements Serializable{
    private ShipmentBack shipmentBack; //运单回单
    private Led led; //分段订单
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time; //签收时间
    private int backNumber;//回单份数
    private int singNumber;//签收数量
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp backTime;//回单时间


    private final Logger log = Logger.getLogger(ShipmentBackDetail.class);//输出Log日志




    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_SHIPMENT_BACK_ID")
    public ShipmentBack getShipmentBack() {
        return shipmentBack;
    }
    public void setShipmentBack(ShipmentBack shipmentBack) {
        this.shipmentBack = shipmentBack;
    }

    @OneToOne
    @JoinColumn(name = "JC_LED_ID")
    public Led getLed() {
        return led;
    }
    public void setLed(Led led) {
        this.led = led;
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

    @Column(name = "SING_NUMBER")
    public int getSingNumber() {
        return singNumber;
    }
    public void setSingNumber(int singNumber) {
        this.singNumber = singNumber;
    }

    @Column(name = "BACK_TIME")
    public Timestamp getBackTime() {
        return backTime;
    }
    public void setBackTime(Timestamp backTime) {
        this.backTime = backTime;
    }
}
