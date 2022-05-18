package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;

/**
 * @Author:sundezeng
 * @Date:2019/1/2
 * 分段单据成本分摊
 */
@Entity(name = "JC_COST_APPORTIONMENT")
public class Apportionment extends BaseEntityNoCode {

    private Double thAmount = 0.00;//提货费
    private Double shAmount = 0.00;//送货费
    private Double gxAmount = 0.00;//干线运费
    private Double qtAmount = 0.00;//其他费用
    private Double zAmount =0.00;//总费用


    private Led led;//分段订单
    private Shipment shipment;//运单


    private Logger log = Logger.getLogger(Apportionment.class);//输出Log日志

//--------------------------分割线----------------------------------


    @Column(name = "Z_AMOUNT")
    public Double getzAmount() {
        return zAmount;
    }

    public void setzAmount(Double zAmount) {
        this.zAmount = zAmount;
    }

    @Column(name = "TH_AMOUNT")
    public Double getThAmount() {
        return thAmount;
    }

    public void setThAmount(Double thAmount) {
        this.thAmount = thAmount;
    }

    @Column(name = "SH_AMOUNT")
    public Double getShAmount() {
        return shAmount;
    }

    public void setShAmount(Double shAmount) {
        this.shAmount = shAmount;
    }
    @Column(name = "GX_AMOUNT")
    public Double getGxAmount() {
        return gxAmount;
    }

    public void setGxAmount(Double gxAmount) {
        this.gxAmount = gxAmount;
    }
    @Column(name = "QT_AMOUNT")
    public Double getQtAmount() {
        return qtAmount;
    }

    public void setQtAmount(Double qtAmount) {
        this.qtAmount = qtAmount;
    }

    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.ALL })
    @JoinColumn(name = "JC_LED_ID")
    public Led getLed() {
        return led;
    }

    public void setLed(Led led) {
        this.led = led;
    }
    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.ALL })
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
