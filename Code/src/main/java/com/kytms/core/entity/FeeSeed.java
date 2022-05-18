package com.kytms.core.entity;/**
 * Created by nidaye on 2018/10/29.
 */

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 费用传递
 *
 * @author
 * @create 2018-10-29
 */
@Entity(name = "FEE_SEED")
public class FeeSeed extends BaseEntity implements Serializable {
    private FeeType feeType;//费用类型
    private Double amount; //金额
    private Double taxRate; //税率
    private Double input; //税金
    private Integer feeSign;// 费用标识(0：收1：付)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp time; // 日期
    private Integer confirmStatus; //确认状态
    private Organization startOrganization; //发起机构
    private Organization receiveOrganization; //接收机构
    private  Organization organization;//组织机构
    private List<LedgerDetail> ledgerDetails; //台账明细


    private Logger log = Logger.getLogger(FeeSeed.class);//输出Log日志


    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_FEE_TYPE_ID")
    public FeeType getFeeType() {
        return feeType;
    }
    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    @Column(name = "AMOUNT",columnDefinition="DOUBLE default 0.0")
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "TAX_RATE",columnDefinition="INT default 0")
    public Double getTaxRate() {
        return taxRate;
    }
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    @Column(name = "INPUT",columnDefinition="DOUBLE default 0.0")
    public Double getInput() {
        return input;
    }
    public void setInput(Double input) {
        this.input = input;
    }

    @Column(name = "FEE_SIGN",columnDefinition="INT default 0")
    public Integer getFeeSign() {
        return feeSign;
    }
    public void setFeeSign(Integer feeSign) {
        this.feeSign = feeSign;
    }

    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "CONFIRM_STATUS",columnDefinition="INT default 0")
    public Integer getConfirmStatus() {
        return confirmStatus;
    }
    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_START_ORGANIZATION_ID")
    public Organization getStartOrganization() {
        return startOrganization;
    }
    public void setStartOrganization(Organization startOrganization) {
        this.startOrganization = startOrganization;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_RECEIVE_ORGANIZATION_ID")
    public Organization getReceiveOrganization() {
        return receiveOrganization;
    }
    public void setReceiveOrganization(Organization receiveOrganization) {
        this.receiveOrganization = receiveOrganization;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="feeSeed")
    public List<LedgerDetail> getLedgerDetails() {
        return ledgerDetails;
    }
    public void setLedgerDetails(List<LedgerDetail> ledgerDetails) {
        this.ledgerDetails = ledgerDetails;
    }

}
