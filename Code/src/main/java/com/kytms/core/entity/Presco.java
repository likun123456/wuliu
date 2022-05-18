package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/*
 2018-09-26
 sundezeng
  计划单(预录单)
  状态： 0 删除（逻辑删除不现实）
        1 生效（可删除，其他状态不可删除）
        2、已配载
        3、提货中
        4、提货结束  可以点击转正按钮（一对一的前提、一对多的是在订单里选择提货单）
        5、历史预录单是查看 转正完毕的数据
 */
@Entity(name = "JC_PRESCO")
public class Presco extends BaseEntity implements Serializable{

    //private final Logger logger = LoggerFactory.getLogger(UserController.class);


    private String relatebill1;//发货单号
    private Double pickMileage ;// 提货公里数
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp dateAccepted; //受里日期
    private Integer costomerType; //客户类型

    private String costomerNameLs; //客户名称(零散客户)

    private ServerZone serverZone;//服务区域

    private String address; //地址

    private List<PrescoProduct> prescoProducts; //计划单货品明细

    @JSONField(serialize=false)
    private Single single;//派车单
    private Organization organization;//组织机构

    private Customer customer;//客户管理

    private String FH_name;//发货单位
    private String FH_person;//发货人
    private String FH_iphone;//发货人电话
    private String FH_address;//发货地址
    private String FH_ltl;//发货经纬度
    private String FH_detailedAddress;//发货详细地址

    private String SH_name;//收货单位
    private String SH_person;//收货人
    private String SH_iphone;//收货电话
    private String SH_address;//收货地址
    private String SH_ltl;//收货经纬度
    @JSONField(serialize=false)
    private Order order;//订单表
    private List<Led> leds;//分段订单
    private String SH_detailedAddress;//收货详细地址

    private Double weight; //重量
    private Double volume; //体积
    private Double value; //货值
    private Integer number; //数量
    private Double jzWeight;// 计重

    private final Logger log = Logger.getLogger(Presco.class);//输出Log日志


    //-------------------------分割线-------------------------


    @Column(name = "JZ_WEIGHT")
    public Double getJzWeight() {
        return jzWeight;
    }

    public void setJzWeight(Double jzWeight) {
        this.jzWeight = jzWeight;
    }

    @Column(name = "WEIGHT")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "VOLUME")
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Column(name = "VALUE")
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Column(name = "NUMBER")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    @Column(name = "FH_DETAILEADDRESS")
    public String getFH_detailedAddress() {
        return FH_detailedAddress;
    }

    public void setFH_detailedAddress(String FH_detailedAddress) {
        this.FH_detailedAddress = FH_detailedAddress;
    }

    @Column(name = "SH_DETAILEADDRESS")
    public String getSH_detailedAddress() {
        return SH_detailedAddress;
    }

    public void setSH_detailedAddress(String SH_detailedAddress) {
        this.SH_detailedAddress = SH_detailedAddress;
    }

    //@JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="presco")
    public List<Led> getLeds() {
        return leds;
    }
    public void setLeds(List<Led> leds) {
        this.leds = leds;
    }


    @JSONField(serialize = false)
    @OneToOne(mappedBy = "presco", cascade = CascadeType.REMOVE)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private List<LedgerDetail> ledgerDetail;//台账明细

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_SERVER_ZONE_ID")
    public ServerZone getServerZone() {
        return serverZone;
    }
    public void setServerZone(ServerZone serverZone) {
        this.serverZone = serverZone;
    }



    @OneToMany(cascade = { CascadeType.ALL},mappedBy ="presco")
    public List<LedgerDetail> getLedgerDetail() {
        return ledgerDetail;
    }

    public void setLedgerDetail(List<LedgerDetail> ledgerDetail) {
        this.ledgerDetail = ledgerDetail;
    }

    @Column(name = "RELATEBILL1")
    public String getRelatebill1() {
        return relatebill1;
    }

    public void setRelatebill1(String relatebill1) {
        this.relatebill1 = relatebill1;
    }

    @Column(name = "FH_NAME")
    public String getFH_name() {
        return FH_name;
    }
    public void setFH_name(String FH_name) {
        this.FH_name = FH_name;
    }
    @Column(name = "FH_PERSON")
    public String getFH_person() {
        return FH_person;
    }
    public void setFH_person(String FH_person) {
        this.FH_person = FH_person;
    }
    @Column(name = "FH_IPHONE")
    public String getFH_iphone() {
        return FH_iphone;
    }

    public void setFH_iphone(String FH_iphone) {
        this.FH_iphone = FH_iphone;
    }
    @Column(name = "FH_ADDRESS")
    public String getFH_address() {
        return FH_address;
    }

    public void setFH_address(String FH_address) {
        this.FH_address = FH_address;
    }
   @Column(name="FH_LTL")
    public String getFH_ltl() {
        return FH_ltl;
    }

    public void setFH_ltl(String FH_ltl) {
        this.FH_ltl = FH_ltl;
    }

    @Column(name="SH_NAME")
    public String getSH_name() {
        return SH_name;
    }

    public void setSH_name(String SH_name) {
        this.SH_name = SH_name;
    }

    @Column(name="SH_PERSON")
    public String getSH_person() {
        return SH_person;
    }

    public void setSH_person(String SH_person) {
        this.SH_person = SH_person;
    }

    @Column(name="SH_ADDRESS")
    public String getSH_address() {
        return SH_address;
    }

    public void setSH_address(String SH_address) {
        this.SH_address = SH_address;
    }

    @Column(name="SH_LTL")
    public String getSH_ltl() {
        return SH_ltl;
    }
    public void setSH_ltl(String SH_ltl) {
        this.SH_ltl = SH_ltl;
    }
    @Column(name="SH_IPHONE")
    public String getSH_iphone() {
        return SH_iphone;
    }

    public void setSH_iphone(String SH_iphone) {
        this.SH_iphone = SH_iphone;
    }

    @ManyToOne(cascade={CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_COSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @Column(name = "COSTOMER_NAME_LS")
    public String getCostomerNameLs() {
        return costomerNameLs;
    }

    public void setCostomerNameLs(String costomerNameLs) {
        this.costomerNameLs = costomerNameLs;
    }


    @OneToMany(cascade = {CascadeType.REFRESH}, mappedBy = "presco")
    public List<PrescoProduct> getPrescoProducts() {
        return prescoProducts;
    }

    public void setPrescoProducts(List<PrescoProduct> prescoProducts) {
        this.prescoProducts = prescoProducts;
    }


    @Column(name = "PICK_MILEAGE")
    public Double getPickMileage() {
        return pickMileage;
    }

    public void setPickMileage(Double pickMileage) {
        this.pickMileage = pickMileage;
    }


    @Column(name = "DATE_ACCEPTED")
    public Timestamp getDateAccepted() {
        return dateAccepted;
    }

    public void setDateAccepted(Timestamp dateAccepted) {
        this.dateAccepted = dateAccepted;
    }


    @Column(name = "COSTOMER_TPYE")
    public Integer getCostomerType() {
        return costomerType;
    }
    public void setCostomerType(Integer costomerType) {
        this.costomerType = costomerType;
    }





    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JSONField(serialize=false)
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "JC_SINGLE_ID")
    public Single getSingle() {
        return single;
    }
    public void setSingle(Single single) {
        this.single = single;
    }


    @ManyToOne(cascade={CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
