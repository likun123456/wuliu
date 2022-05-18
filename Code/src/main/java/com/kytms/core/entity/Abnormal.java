package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.kytms.core.dao.impl.BaseDaoImpl;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 异常
 * @author 奇趣源码
 * @create 2018-02-22
 */
@Entity(name = "JC_ABNORMAL")
public class Abnormal extends BaseEntityNoCode implements Serializable{
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;//异常单日期
    private Integer number;//修改多少处;
    private Integer type;//类型 0 订单 1运单 2派车单
    private Organization organization; //组织机构
    private Order order;
    private Shipment shipment;
    private List<AbnormalDetail> abnormalDetails;
    private Single single; //派车单
    private Logger log = Logger.getLogger(Abnormal.class);//输出Log日志

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SINGLE_ID")
    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }

    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "NUMBER")
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
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
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="abnormal")
    public List<AbnormalDetail> getAbnormalDetails() {
        return abnormalDetails;
    }
    public void setAbnormalDetails(List<AbnormalDetail> abnormalDetails) {
        this.abnormalDetails = abnormalDetails;
    }


    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

}
