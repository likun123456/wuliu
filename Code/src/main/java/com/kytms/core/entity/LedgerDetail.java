package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 奇趣源码商城 www.qiqucode.com
 *台帐明细
 * @author 奇趣源码
 * @create 2018-01-10
 */
@Entity(name = "JC_LEDGER_DETAIL")
public class LedgerDetail extends BaseEntityNoCode implements Serializable {
    private Double amount = 0.00; //金额
    private Double taxRate  = 0.00; //税率
    private Double income  = 0.00; //收入
    private Double outcome  = 0.00; //成本
    private Double input  = 0.00; //税金
    private Order order;//订单

    private Double hxmoney = 0.00;//核销金额
    private Double whxmoney = 0.00;//未核销金额

    @JSONField(serialize=false)
    private Shipment shipment;//运单

    private ShipmentTemplate  shipmentTemplate;  //运单模板

    @JSONField(serialize=false)
    private Presco presco;//预录单(计划单)

    private Integer type;//单据类型(订单、运单,0 订单 1 运单 2 派车单 4 费用传递,3 其他费用)
    private Integer cost;//台账类型(正常、调整)
    private FeeType feeType;//费用类型
    private  Carrier carrier;// 承运商
    private  String  explain1;  //说明1
    private  String  explain2;//说明2
    private  String  explain3; //说明3
    private  String occurrenceTime;  //发生时间
    private  Single single;//派车单
    private  FeeSeed feeSeed; //费用传递
    private  OtherFee otherFee;//其他费用
    private Double nowPay =0.00;// 现付
    private Double arrivePay =0.00;// 到付
    private Double backPay =0.00;// 回付
    private Double monthPay =0.00;// 月结
    private Double oilPrepaid = 0.00;//预付油卡
    private Double cashAdvances=0.00;//预付现金


    private Double zfy  =0.00;//总费用
    private Double zthf =0.00;//总提货费
    private Double zshf =0.00;//总送货费
    private Double zgxyf =0.00;//总干线运费
    private Double yuE =0.00;//余额
    private Double zqtfy =0.00;//总其他费用
    private Double yf_youka;//预收油卡
    private Double yajin;//押金
    private String settlementMethod;//结算方式
    private String payMethod;//支付方式
    private String collectMethod; //收款方式

    private int is_inoutcome;//是否收入成本确认  确认 1，未确认 0
     @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp affirm_Time;//确认时间


    private Logger log = Logger.getLogger(LedgerDetail.class);//输出Log日志

    //----------------------------分割线--------------------------

    @Column(name = "SETTLEMENT_METHOD")
    public String getSettlementMethod() {
        return settlementMethod;
    }
    public void setSettlementMethod(String settlementMethod) {
        this.settlementMethod = settlementMethod;
    }

    @Column(name = "PAY_METHOD")
    public String getPayMethod() {
        return payMethod;
    }
    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    @Column(name = "COLLECT_METHOD")
    public String getCollectMethod() {
        return collectMethod;
    }
    public void setCollectMethod(String collectMethod) {
        this.collectMethod = collectMethod;
    }

    @Column(name = "IS_INOUTCOME")
    public int getIs_inoutcome() {
        return is_inoutcome;
    }

    public void setIs_inoutcome(int is_inoutcome) {
        this.is_inoutcome = is_inoutcome;
    }
    @Column(name = "AFFIRM_TIME")
    public Timestamp getAffirm_Time() {
        return affirm_Time;
    }

    public void setAffirm_Time(Timestamp affirm_Time) {
        this.affirm_Time = affirm_Time;
    }

    @Column(name = "INCOME")
    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }
    @Column(name = "OUTCOME")
    public Double getOutcome() {
        return outcome;
    }

    public void setOutcome(Double outcome) {
        this.outcome = outcome;
    }

    @Column(name = "YF_YOUKA")
    public Double getYf_youka() {
        return yf_youka;
    }

    public void setYf_youka(Double yf_youka) {
        this.yf_youka = yf_youka;
    }

    @Column(name = "YAJIN")
    public Double getYajin() {
        return yajin;
    }

    public void setYajin(Double yajin) {
        this.yajin = yajin;
    }

    @Column(name = "Z_FY")
    public Double getZfy() {
        return zfy;
    }

    public void setZfy(Double zfy) {
        this.zfy = zfy;
    }

    @Column(name = "Z_THF")
    public Double getZthf() {
        return zthf;
    }

    public void setZthf(Double zthf) {
        this.zthf = zthf;
    }

    @Column(name = "Z_SHF")
    public Double getZshf() {
        return zshf;
    }

    public void setZshf(Double zshf) {
        this.zshf = zshf;
    }

    @Column(name = "Z_GXYF")
    public Double getZgxyf() {
        return zgxyf;
    }

    public void setZgxyf(Double zgxyf) {
        this.zgxyf = zgxyf;
    }

    @Column(name = "YU_E")
    public Double getYuE() {
        return yuE;
    }

    public void setYuE(Double yuE) {
        this.yuE = yuE;
    }

    @Column(name = "Z_QTFY")
    public Double getZqtfy() {
        return zqtfy;
    }

    public void setZqtfy(Double zqtfy) {
        this.zqtfy = zqtfy;
    }

    @Column(name = "OIL_PREPAID")
    public Double getOilPrepaid() {
        return oilPrepaid;
    }

    public void setOilPrepaid(Double oilPrepaid) {
        this.oilPrepaid = oilPrepaid;
    }
    @Column(name = "CASH_ADVANCES")
    public Double getCashAdvances() {
        return cashAdvances;
    }

    public void setCashAdvances(Double cashAdvances) {
        this.cashAdvances = cashAdvances;
    }

    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.ALL })
    @JoinColumn(name = "JC_OTHERFEE_ID")
    public OtherFee getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(OtherFee otherFee) {
        this.otherFee = otherFee;
    }

    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.ALL })
    @JoinColumn(name = "JC_SHIPMENT_ID")
    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.ALL })
    @JoinColumn(name = "JC_PRESCO_ID")
    public Presco getPresco() {
        return presco;
    }

    public void setPresco(Presco presco) {
        this.presco = presco;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_FEE_TYPE_ID")
    public FeeType getFeeType() {
        return feeType;
    }
    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_CARRIER_ID")
    public Carrier getCarrier() {
        return carrier;
    }
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.ALL })
    @JoinColumn(name = "JC_ORDER_ID")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    @Column(name = "AMOUNT",columnDefinition="INT default 0")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    @Column(name = "TAXRATE",columnDefinition="INT default 0")
    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }
    @Column(name = "INPUT", columnDefinition="INT default 0")
    public Double getInput() {
        return input;
    }

    public void setInput(Double input) {
        this.input = input;
    }
    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Column(name = "COST")
    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }


    @Column(name = "OCCURRENCE_TIME")
    public String getOccurrenceTime() {
        return occurrenceTime;
    }
    public void setOccurrenceTime(String occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }

    @Column(name = "EXPLAIN1")
    public String getExplain1() {
        return explain1;
    }
    public void setExplain1(String explain1) {
        this.explain1 = explain1;
    }

    @Column(name = "EXPLAIN2")
    public String getExplain2() {
        return explain2;
    }
    public void setExplain2(String explain2) {
        this.explain2 = explain2;
    }

    @Column(name = "EXPLAIN3")
    public String getExplain3() {
        return explain3;
    }
    public void setExplain3(String explain3) {
        this.explain3 = explain3;
    }
    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.ALL })
    @JoinColumn(name = "JC_SINGLE_ID")
    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }

    @JSONField(serialize = false)
    @ManyToOne(cascade={CascadeType.ALL })
    @JoinColumn(name = "JC_FEE_SEED_ID")
    public FeeSeed getFeeSeed() {
        return feeSeed;
    }
    public void setFeeSeed(FeeSeed feeSeed) {
        this.feeSeed = feeSeed;
    }

    @Column(name = "HXMONEY")
    public Double getHxmoney() {
        return hxmoney;
    }

    public void setHxmoney(Double hxmoney) {
        this.hxmoney = hxmoney;
    }
    @Column(name = "WHMONEY")
    public Double getWhxmoney() {
        return whxmoney;
    }

    public void setWhxmoney(Double whxmoney) {
        this.whxmoney = whxmoney;
    }
    @Column(name = "NOW_PAY")
    public Double getNowPay() {
        return nowPay;
    }

    public void setNowPay(Double nowPay) {
        this.nowPay = nowPay;
    }
    @Column(name = "ARRIVE_PAY")
    public Double getArrivePay() {
        return arrivePay;
    }

    public void setArrivePay(Double arrivePay) {
        this.arrivePay = arrivePay;
    }
    @Column(name = "BACK_PAY")
    public Double getBackPay() {
        return backPay;
    }

    public void setBackPay(Double backPay) {
        this.backPay = backPay;
    }
    @Column(name = "MONTY_PAY")
    public Double getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(Double monthPay) {
        this.monthPay = monthPay;
    }


    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.ALL })
    @JoinColumn(name = "JC_SHIPMENT_TEMPLATE_ID")
    public ShipmentTemplate getShipmentTemplate() {
        return shipmentTemplate;
    }
    public void setShipmentTemplate(ShipmentTemplate shipmentTemplate) {
        this.shipmentTemplate = shipmentTemplate;
    }
}
