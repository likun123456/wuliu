package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author:sundezeng
 * @Date:2018/10/23
 * 经由站
 */
@Entity(name = "JC_BERTH_STAND")
public class BerthStand extends BaseEntityNoCode implements Serializable {
    private Shipment shipment;//运单
    private ShipmentTemplate shipmentTemplate;//运单模板
    private Organization organization;//组织机构
    private int orderBy;//
    private int isArrive;// 0,1

    private Logger log = Logger.getLogger(BerthStand.class);//输出Log日志



    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }


    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SHIPMENT_TEMPLATE_ID")
    public ShipmentTemplate getShipmentTemplate() {
        return shipmentTemplate;
    }
    public void setShipmentTemplate(ShipmentTemplate shipmentTemplate) {
        this.shipmentTemplate = shipmentTemplate;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    @OrderBy()
    @Column(name = "ORDER_BY")
    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }
    @Column(name = "IS_ARRIVE")
    public int getIsArrive() {
        return isArrive;
    }

    public void setIsArrive(int isArrive) {
        this.isArrive = isArrive;
    }
}
