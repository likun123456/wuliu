package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/5/23.
 * 结算明细表
 */
@Entity(name = "JC_SELLTLMENT_DETAILS")
public class SelltlmentDetails extends BaseEntityNoCode implements Serializable {
    private Settlement settlement;//结算总表
    private Order order;//订单
    private Shipment shipment;//运单
    private Organization organization;//组织机构

    private final Logger log = Logger.getLogger(SelltlmentDetails.class);//输出Log日志


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SETTLEMENT_ID")
    public Settlement getSettlement() {
        return settlement;
    }
    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }


    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
