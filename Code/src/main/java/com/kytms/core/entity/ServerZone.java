package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
  服务区域表
 */
@Entity(name = "JC_SERVER_ZONE")
public class ServerZone extends BaseEntityNoCode implements Serializable {
    private Organization organization;//组织机构
    private Zone zone;//服务区域
    private Integer type;//运点类型(0.  1.)
    private Double minMoney;//最低收费
    private Double weightPrices;//吨单价
    private Double volumePrices;//方单价
    private Double mileage ;//公里数
    private Double songhf;//送货费

    private final Logger log = Logger.getLogger(ServerZone.class);//输出Log日志

    //---------------------------------分割线---------------------


    @Column(name = "SONG_HF")
    public Double getSonghf() {
        return songhf;
    }

    public void setSonghf(Double songhf) {
        this.songhf = songhf;
    }

    @Column(name = "MILEAGE")
    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ZONE_ID")
    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Column(name = "MIN_MONEY")
    public Double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Double minMoney) {
        this.minMoney = minMoney;
    }
    @Column(name = "WEIGHT_PRICES")
    public Double getWeightPrices() {
        return weightPrices;
    }

    public void setWeightPrices(Double weightPrices) {
        this.weightPrices = weightPrices;
    }
    @Column(name = "VOLUME_PRICES")
    public Double getVolumePrices() {
        return volumePrices;
    }

    public void setVolumePrices(Double volumePrices) {
        this.volumePrices = volumePrices;
    }
}
