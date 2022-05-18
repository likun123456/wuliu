package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 费用类型
 * @author 奇趣源码
 * @create 2018-01-02
 */
@Entity(name = "JC_FEE_TYPE")
public class FeeType extends BaseEntity implements Serializable {
    private Integer cost;//收入成本表示
    private Integer isCount; //是否加入总计
    private Integer transportOrder; //运输订单
    private Integer shipmentModule; //运单
    private Integer defaultCreate; //默认生成
    private List<Organization> organizations;
    private String orgName;
    private String orgId;
    public static final  String TRANSPORT_ORDER="1";
    public static final  String TRANSPORT_SHIPMENT="2";
    public static final  String TRANSPORT_DISPATCH="3";
    public static final  String TRANSPORT_MIX_ORDER="1.1";
    public static final  String TRANSPORT_MIX_SHIPMENT="2.1";
    public static final  String TRANSPORT_MIX_DISPATCH="3.1";
    private List<LedgerDetail> ledgerDetails;

    private Logger log = Logger.getLogger(FeeType.class);//输出Log日志


    @Column(name = "COST")
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Column(name = "IS_COUNT")
    public Integer getIsCount() {
        return isCount;
    }

    public void setIsCount(Integer isCount) {
        this.isCount = isCount;
    }
    @Column(name = "TRANSPORTORDER" ,columnDefinition="INT default 0")
    public Integer getTransportOrder() {
        return transportOrder;
    }

    public void setTransportOrder(Integer transportOrder) {
        this.transportOrder = transportOrder;
    }
    @Column(name = "SHIPMENTMODLE",columnDefinition="INT default 0")
    public Integer getShipmentModule() {
        return shipmentModule;
    }
    public void setShipmentModule(Integer shipmentModule) {
        this.shipmentModule = shipmentModule;
    }


    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="feeType")
    public List<LedgerDetail> getLedgerDetails() {
        return ledgerDetails;
    }
    public void setLedgerDetails(List<LedgerDetail> ledgerDetails) {
        this.ledgerDetails = ledgerDetails;
    }

    @Column(name = "DEFAULT_CREATE" ,columnDefinition="INT default 0")
    public Integer getDefaultCreate() {
        return defaultCreate;
    }
    public void setDefaultCreate(Integer defaultCreate) {
        this.defaultCreate = defaultCreate;
    }
    @JSONField(serialize=false)
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "JC_FEE_TYPE_ORGANIZATION", joinColumns = @JoinColumn(name = "JC_FEE_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "JC_SYS_ORGANIZATION_ID"))
    public List<Organization> getOrganizations() {
        return organizations;
    }
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Column(name = "ORG_NAME",length = 255)
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Column(name = "ORG_ID",length = 255)
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
