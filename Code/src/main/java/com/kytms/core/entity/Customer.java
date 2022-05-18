package com.kytms.core.entity;/**
 * Created by nidaye on 2018/1/2.
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
 * 客户档案
 *
 * @author
 * @create 2018-01-02
 */
@Entity(name = "JC_CUSTOMER")
public class Customer extends BaseEntity implements Serializable {
    private String salePersion; //销售负责人
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime; //合同开始日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp endTime; //合同结束日期
    private Integer paymentDays;//账期
    private String contactperson; // 发货联系人
    private String iphone; //发货电话
    private String address; //发货地址
    private String ltl; //经纬度
    private String detailedAddress;//发货详细地址
    private String email; //邮箱
    private Integer settlementType;//结算方式
    private Organization organization;//组织机构
    private JcRegistration jcRegistration;//注册公司
    private Zone zone; //行政区域 城市
    private List<Order> orders;//订单
    @JSONField(serialize=false)
    private List<Presco> prescos;//预录单

    private Logger log = Logger.getLogger(Customer.class);//输出Log日志

    //2019/03/14
    //平台名称 和 id
    private String ss_ptName;
    private Integer ss_ptId;
    //项目名称 和 ID
    private String ss_xmName;
    private Integer ss_xmId;
    //分项目名称 和 ID
    private String ss_fxmName;
    private Integer ss_fxmId;
    //分公司名称 和ID
    private String ss_fgsName;
    private Integer ss_fgsId;
    //注册公司名称 和ID
    private String ss_zcgsName;
    private Integer ss_zcgsId;
    //是否同步
    private int sftb;


    private String shdanwei;//收货单位
    private String shcontactperson; // 收货联系人
    private String shiphone; //收货电话
    private String shaddress; //收货地址
    private String shltl; //收货经纬度
    private String shdetailedAddress;//收货详细地址


    private Rule rule;

    //---------------------------
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_RULES_ID")
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }



    @Column(name = "SH_DANWEI")
    public String getShdanwei() {
        return shdanwei;
    }

    public void setShdanwei(String shdanwei) {
        this.shdanwei = shdanwei;
    }

    @Column(name = "SH_CONTACTPERSON")
    public String getShcontactperson() {
        return shcontactperson;
    }

    public void setShcontactperson(String shcontactperson) {
        this.shcontactperson = shcontactperson;
    }
    @Column(name = "SH_IPHONE")
    public String getShiphone() {
        return shiphone;
    }

    public void setShiphone(String shiphone) {
        this.shiphone = shiphone;
    }
    @Column(name = "SH_ADDRESS")
    public String getShaddress() {
        return shaddress;
    }

    public void setShaddress(String shaddress) {
        this.shaddress = shaddress;
    }
    @Column(name = "SH_LTL")
    public String getShltl() {
        return shltl;
    }

    public void setShltl(String shltl) {
        this.shltl = shltl;
    }
    @Column(name = "SH_DETAILEDADDRESS")
    public String getShdetailedAddress() {
        return shdetailedAddress;
    }

    public void setShdetailedAddress(String shdetailedAddress) {
        this.shdetailedAddress = shdetailedAddress;
    }




    @Column(name = "SS_FXMNAME")
    public String getSs_fxmName() {
        return ss_fxmName;
    }

    public void setSs_fxmName(String ss_fxmName) {
        this.ss_fxmName = ss_fxmName;
    }
    @Column(name = "SS_FXMID")
    public Integer getSs_fxmId() {
        return ss_fxmId;
    }

    public void setSs_fxmId(Integer ss_fxmId) {
        this.ss_fxmId = ss_fxmId;
    }

    @Column(name = "SFBT")
    public int getSftb() {
        return sftb;
    }

    public void setSftb(int sftb) {
        this.sftb = sftb;
    }

    @Column(name = "SS_PTNAME")
    public String getSs_ptName() {
        return ss_ptName;
    }

    public void setSs_ptName(String ss_ptName) {
        this.ss_ptName = ss_ptName;
    }
    @Column(name = "SS_PTID")
    public Integer getSs_ptId() {
        return ss_ptId;
    }

    public void setSs_ptId(Integer ss_ptId) {
        this.ss_ptId = ss_ptId;
    }
    @Column(name = "SS_XMNAME")
    public String getSs_xmName() {
        return ss_xmName;
    }

    public void setSs_xmName(String ss_xmName) {
        this.ss_xmName = ss_xmName;
    }
    @Column(name = "SS_XMID")
    public Integer getSs_xmId() {
        return ss_xmId;
    }

    public void setSs_xmId(Integer ss_xmId) {
        this.ss_xmId = ss_xmId;
    }
    @Column(name = "SS_FGSNAME")
    public String getSs_fgsName() {
        return ss_fgsName;
    }

    public void setSs_fgsName(String ss_fgsName) {
        this.ss_fgsName = ss_fgsName;
    }
    @Column(name = "SS_FGSID")
    public Integer getSs_fgsId() {
        return ss_fgsId;
    }

    public void setSs_fgsId(Integer ss_fgsId) {
        this.ss_fgsId = ss_fgsId;
    }
    @Column(name = "SS_ZCGSNAME")
    public String getSs_zcgsName() {
        return ss_zcgsName;
    }

    public void setSs_zcgsName(String ss_zcgsName) {
        this.ss_zcgsName = ss_zcgsName;
    }
    @Column(name = "SS_ZCGSID")
    public Integer getSs_zcgsId() {
        return ss_zcgsId;
    }

    public void setSs_zcgsId(Integer ss_zcgsId) {
        this.ss_zcgsId = ss_zcgsId;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="customer")
    public List<Presco> getPrescos() {
        return prescos;
    }

    public void setPrescos(List<Presco> prescos) {
        this.prescos = prescos;
    }

    @Column(name = "SALE_PERSION",length = 50)
    public String getSalePersion() {
        return salePersion;
    }
    public void setSalePersion(String salePersion) {
        this.salePersion = salePersion;
    }

    @Column(name = "START_TIME")
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TIME")
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }


    @Column(name = "PAYMENT_DAYS")
    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }
    @Column(name = "SETTLEMENT_TYPE")
    public Integer getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(Integer settlementType) {
        this.settlementType = settlementType;
    }

    @Column(name = "CONTACTPERSON",length = 50)
    public String getContactperson() {
        return contactperson;
    }
    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    @Column(name = "IPHONE",length = 50)
    public String getIphone() {
        return iphone;
    }
    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    @Column(name = "ADDRESS",length = 120)
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "LTL",length = 20)
    public String getLtl() {
        return ltl;
    }
    public void setLtl(String ltl) {
        this.ltl = ltl;
    }

    @Column(name = "EMAIL",length = 50)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_REGISTRATION")
    public JcRegistration getJcRegistration() {
        return jcRegistration;
    }
    public void setJcRegistration(JcRegistration jcRegistration) {
        this.jcRegistration = jcRegistration;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ZONE_ID")
    public Zone getZone() {
        return zone;
    }
    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="customer")
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    @Column(name = "DETAILED_ADDRESS",length = 255)
    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }
}
