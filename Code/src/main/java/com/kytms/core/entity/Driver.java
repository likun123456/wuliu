package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/12/4 0004.
 * 司机
 */
@Entity(name = "JC_DRIVER")
public class Driver extends BaseEntity implements Serializable {
    private Integer type;//类型
    private String sex;//性别
    private Integer age;//年龄
    private String card;//身份证
    private String iphone1;//电话1
    private String iphone2;//电话2
    private String iphone3;//电话3
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp dri;//领取驾照日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp birthday; //生日
    private Double baseWages; //基础工资
    private Organization organization; //组织机构
  //  private VehicleHead vehicleHead; //车头


    private Logger log = Logger.getLogger(Driver.class);//输出Log日志




    @Column(name = "DRI")
    public Timestamp getDri() {
        return dri;
    }
    public void setDri(Timestamp dri) {
        this.dri = dri;
    }

    @Column(name = "BIRTHDAY")
    public Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    @Column(name = "TYPE", length = 5)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "AGE", length = 20)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Column(name = "BASE_WAGES")
    public Double getBaseWages() {
        return baseWages;
    }

    public void setBaseWages(Double baseWages) {
        this.baseWages = baseWages;
    }

    @Column(name = "SEX", length = 5)
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }



    @Column(name = "CARD", length = 50)
    public String getCard() {
        return card;
    }
    public void setCard(String card) {
        this.card = card;
    }

    @Column(name = "IPHONE1", length = 50)
    public String getIphone1() {
        return iphone1;
    }
    public void setIphone1(String iphone1) {
        this.iphone1 = iphone1;
    }

    @Column(name = "IPHONE2", length = 50)
    public String getIphone2() {
        return iphone2;
    }
    public void setIphone2(String iphone2) {
        this.iphone2 = iphone2;
    }

    @Column(name = "IPHONE3", length = 50)
    public String getIphone3() {
        return iphone3;
    }
    public void setIphone3(String iphone3) {
        this.iphone3 = iphone3;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

//    @JSONField(serialize=false)
//    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
//    @JoinColumn(name = "JC_VEHICLE_HEAD_ID")
//    public VehicleHead getVehicleHead() {
//        return vehicleHead;
//    }
//    public void setVehicleHead(VehicleHead vehicleHead) {
//        this.vehicleHead = vehicleHead;
//    }
}
