package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author:sundezeng
 * @Date:2018/11/2
 * 场站其他费用(修车，变卖资产等成本和收入)
 */
@Entity(name = "JC_OTHER_FEE")
public class OtherFee extends BaseEntityNoCode implements Serializable {
    private Double zmoney;//总金额
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp csTime;//发生时间
    private String operator;//当事人
    private Integer type;//收入成本标识  0 收入 1 成本
    private Double taxRate; //税率
    private Double input; //税金
    private List<VerificationZb> verifications;//核销表
  //  private List<LedgerDetail> ledgerDetails;//费用明细
    private Organization organization;//组织机构

    private Logger log = Logger.getLogger(OtherFee.class);//输出Log日志

    //------------------------分割线--------------------------

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

//    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="otherFee")
//    public List<LedgerDetail> getLedgerDetails() {
//        return ledgerDetails;
//    }
//
//    public void setLedgerDetails(List<LedgerDetail> ledgerDetails) {
//        this.ledgerDetails = ledgerDetails;
//    }

    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "otherFee")
    public List<VerificationZb> getVerifications() {
        return verifications;
    }
    public void setVerifications(List<VerificationZb> verifications) {
        this.verifications = verifications;
    }


    @Column(name = "Z_MONEY")
    public Double getZmoney() {
        return zmoney;
    }

    public void setZmoney(Double zmoney) {
        this.zmoney = zmoney;
    }
    @Column(name = "CS_TIME")
    public Timestamp getCsTime() {
        return csTime;
    }

    public void setCsTime(Timestamp csTime) {
        this.csTime = csTime;
    }
    @Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Column(name = "TAX_RATE")
    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }
    @Column(name = "INPUT")
    public Double getInput() {
        return input;
    }

    public void setInput(Double input) {
        this.input = input;
    }


}
