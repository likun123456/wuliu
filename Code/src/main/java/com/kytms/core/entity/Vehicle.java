package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车型管理
 * @author 奇趣源码
 * @create 2018-01-02
 */
@Entity(name = "JC_VEHICLE")
public class Vehicle extends BaseEntity implements Serializable {
    @JSONField(serialize=false)
    private List<Shipment> shipments;


    private final Logger log = Logger.getLogger(Vehicle.class);//输出Log日志

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="vehicle")
    public List<Shipment> getShipments() {
        return shipments;
    }
    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }


}
