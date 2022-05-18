package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/*
  车辆到站
 */

@Entity(name = "JC_VEHICLE_ARRIVE")
public class VehicleArrive extends BaseEntityNoCode implements Serializable {

    private Organization organization;//组织机构
    private Shipment shipment;//运单

    private final Logger log = Logger.getLogger(VehicleArrive.class);//输出Log日志


    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp vehicleTime;//到站时间

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp operateTime;//发运时间

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp arriveTime;//运抵时间

    private String whichStation;//当前站

    @JSONField(serialize = false)
    @Column(name = "ARRIVE_TIME")
    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }

    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
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
