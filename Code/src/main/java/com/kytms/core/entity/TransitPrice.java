package com.kytms.core.entity;

/*
中转单价表
 */

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "JC_TRANSIT_PRICE")
public class TransitPrice  extends BaseEntityNoCode implements Serializable{
    private ServerZone serverZone;//服务区域
    private double mixPrice;//最低收费
    private double tonPrice;//吨单价
    private double volumePrice;//方单价

    private final Logger log = Logger.getLogger(TransitPrice.class);//输出Log日志

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SERVER_ZONE_ID")
    public ServerZone getServerZone() {
        return serverZone;
    }

    public void setServerZone(ServerZone serverZone) {
        this.serverZone = serverZone;
    }

    @Column(name = "MIX_PRICE")
    public double getMixPrice() {
        return mixPrice;
    }

    public void setMixPrice(double mixPrice) {
        this.mixPrice = mixPrice;
    }

    @Column(name = "TON_PRICE")
    public double getTonPrice() {
        return tonPrice;
    }

    public void setTonPrice(double tonPrice) {
        this.tonPrice = tonPrice;
    }

    @Column(name = "VOLUME_PRICE")
    public double getVolumePrice() {
        return volumePrice;
    }

    public void setVolumePrice(double volumePrice) {
        this.volumePrice = volumePrice;
    }
}
