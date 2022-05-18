package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Author:sundezeng
 * @Date:2018/11/3
 */
@Entity(name = "JC_VERIFICATION_ZB")
public class VerificationZb extends BaseEntityNoCode implements Serializable {

    private Integer hxCount;//核销次数
    private Integer hxStatus;//核销状态 0:未核销, 1:已核销，2:部分核销
    private Integer tzSource;//台账来源
    private Double zMoney;//总金额
    private Double zInput;//总税金
    private Double hxMoney;//已核销金额
    private Double whxMoney;//未核销金额
    private Integer type;//类型 (收入，成本)

    private Order order;//订单表
    private Shipment shipment;//运单表
    private Single single;//派车单
    private OtherFee otherFee;//其他费用
    private Organization organization;//组织机构
    private List<VerificationRecord> verificationRecords;//核销记录表

    // 订单号，客户订单号 订单时间 出发站点，目的站点，次数，状态，来源（0 正常 1调整），按钮 （核销）
    // 应收金额，税金，已收金额，已收税金，未收金额，税金 按钮（查看明细），类型
    private final Logger log = Logger.getLogger(VerificationZb.class);//输出Log日志

    //------------------------分割线--------------------------

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "verificationZb")
    public List<VerificationRecord> getVerificationRecords() {
        return verificationRecords;
    }

    public void setVerificationRecords(List<VerificationRecord> verificationRecords) {
        this.verificationRecords = verificationRecords;
    }

    @Column(name = "HX_COUNT")
    public Integer getHxCount() {
        return hxCount;
    }

    public void setHxCount(Integer hxCount) {
        this.hxCount = hxCount;
    }
    @Column(name = "HX_STATUS")
    public Integer getHxStatus() {
        return hxStatus;
    }

    public void setHxStatus(Integer hxStatus) {
        this.hxStatus = hxStatus;
    }
    @Column(name = "TZ_SOURCE")
    public Integer getTzSource() {
        return tzSource;
    }

    public void setTzSource(Integer tzSource) {
        this.tzSource = tzSource;
    }
    @Column(name = "Z_MONEY")
    public Double getzMoney() {
        return zMoney;
    }

    public void setzMoney(Double zMoney) {
        this.zMoney = zMoney;
    }
    @Column(name = "Z_INPUT")
    public Double getzInput() {
        return zInput;
    }

    public void setzInput(Double zInput) {
        this.zInput = zInput;
    }
    @Column(name = "HX_MONEY")
    public Double getHxMoney() {
        return hxMoney;
    }

    public void setHxMoney(Double hxMoney) {
        this.hxMoney = hxMoney;
    }
    @Column(name = "WHX_MONEY")
    public Double getWhxMoney() {
        return whxMoney;
    }

    public void setWhxMoney(Double whxMoney) {
        this.whxMoney = whxMoney;
    }
    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_SINGLE_ID")
    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_OTHER_FEE_ID")
    public OtherFee getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(OtherFee otherFee) {
        this.otherFee = otherFee;
    }
}
