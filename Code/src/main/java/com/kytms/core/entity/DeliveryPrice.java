package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "JC_DELIVERY_PRICE")
public class DeliveryPrice extends BaseEntityNoCode implements Serializable{

    private String zlinterval;//区间

    private Double pirce;//单价

    private ServerZone serverZone;//服务区域


    private Logger log = Logger.getLogger(DeliveryPrice.class);//输出Log日志


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SERVER_ZONE_ID")
    public ServerZone getServerZone() {
        return serverZone;
    }

    public void setServerZone(ServerZone serverZone) {
        this.serverZone = serverZone;
    }

    @Column(name = "ZL_INTERVAL")
    public String getZlinterval() {
        return zlinterval;
    }

    public void setZlinterval(String zlinterval) {
        this.zlinterval = zlinterval;
    }

    @Column(name = "PIRCE")
    public Double getPirce() {
        return pirce;
    }

    public void setPirce(Double pirce) {
        this.pirce = pirce;
    }
}
