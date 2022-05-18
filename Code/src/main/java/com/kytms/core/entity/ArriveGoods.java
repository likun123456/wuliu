package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/*
 货品到站记录
 */
@Entity(name = "JC_ARRIVE_GOODS")
public class ArriveGoods extends BaseEntityNoCode implements Serializable {
    private Organization organization;//组织机构

    private Led led;//分段运单

    private Shipment shipment;//运单

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp vehicleTime;//到站时间

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp operateTime;//操作时间

    private String whichStation;//当前站


    private Logger log = Logger.getLogger(ArriveGoods.class);//输出Log日志


    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_LED_ID")
    public Led getLed() {
        return led;
    }

    public void setLed(Led led) {
        this.led = led;
    }

    @Column(name = "VEHICLE_TIME")
    public Timestamp getVehicleTime() {
        return vehicleTime;
    }

    public void setVehicleTime(Timestamp vehicleTime) {
        this.vehicleTime = vehicleTime;
    }

    @Column(name = "OPERATE_TIME")
    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    @Column(name = "WHICH_STATION")
    public String getWhichStation() {
        return whichStation;
    }

    public void setWhichStation(String whichStation) {
        this.whichStation = whichStation;
    }
}
