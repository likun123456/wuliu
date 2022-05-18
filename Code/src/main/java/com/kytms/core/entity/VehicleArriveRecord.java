package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author:sundezeng
 * @Date:2018/10/25
 * 车辆到站记录
 */
@Entity(name = "JC_VEHICLE_ARRIVE_RECORD")
public class VehicleArriveRecord extends BaseEntityNoCode implements Serializable{
    private Organization organization;//组织机构
    private Shipment shipment;//运单

    private final Logger log = Logger.getLogger(VehicleArriveRecord.class);//输出Log日志


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @OneToOne
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
