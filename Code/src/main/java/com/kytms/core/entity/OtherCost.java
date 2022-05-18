package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

/*
内部转移价其他费用登记
 */

@Entity(name = "JC_OTHER_COST")
public class OtherCost extends BaseEntityNoCode implements Serializable {
    private Organization organization;//组织机构

    private Integer fytype;//费用类型

    private Double weightPrice;//吨单价

    private Double volumePrice;//方单价

    private Double taxRate;//税率

    private Logger log = Logger.getLogger(OtherCost.class);//输出Log日志


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Column(name = "FY_TYPE")
    public Integer getFytype() {
        return fytype;
    }

    public void setFytype(Integer fytype) {
        this.fytype = fytype;
    }

    @Column(name = "WEIGHT_PRICE")
    public Double getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(Double weightPrice) {
        this.weightPrice = weightPrice;
    }
    @Column(name = "VOLUME_PRICE")
    public Double getVolumePrice() {
        return volumePrice;
    }

    public void setVolumePrice(Double volumePrice) {
        this.volumePrice = volumePrice;
    }
    @Column(name = "TAX_TATE")
    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }
}
