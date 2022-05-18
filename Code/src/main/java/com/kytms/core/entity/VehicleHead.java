package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车头信息
 * @author 奇趣源码
 * @create 2018-01-02
 */
@Entity(name = "JC_VEHICLE_HEAD")
public class VehicleHead extends BaseEntity implements Serializable {

    private Organization organization; //所属组织机构
    private Driver driverss; //司机
    private JcRegistration jcRegistration;//注册公司


    private String driver;//司机
    private String iphone;//电话

    private  Organization organization1; //使用组织机构
    private String size;// 尺寸
    private Double totalMass; // 总质量
    private Double pullTotalMass; //总牵引质量
    private Double vehicleWeight;//整车重量
    private Integer persionNumber;//准成人数
    private String vin;//车架号
    private String engineVin;//发动机号
    private Double displacement;//排量
    private Integer emiStandard;// 排放标准
    private Double maxPs;//最大马力
    private Double maxTorque; //最大扭矩
    private Double maxKm;//最大功率
    private Double gearBoxType;//变速箱类型
    private Integer tireNumber;//轮胎数量
    private String tireType;//轮胎规格
    private Integer oilType;//燃油类型
    private Double oilSize;//燃油容积
    private Double price;//价钱
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp buyTime;//购买日期
    private String buyAddress; //购买地址
    private String supplierName;//供应商名称
    private String supplierIphone;//供应商电话
    private String source;//来源
    private Double mileage;//起始里程


    private final Logger log = Logger.getLogger(VehicleHead.class);//输出Log日志


    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_DRIVER_ID")
    public Driver getDriverss() {
        return driverss;
    }

    public void setDriverss(Driver driverss) {
        this.driverss = driverss;
    }

    @Column(name = "DRIVER")
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
    @Column(name = "IPHONE")
    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    @Column(name = "SIZE")
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "VIN", length = 50)
    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }

    @Column(name = "ENGINE_VIN", length = 50)
    public String getEngineVin() {
        return engineVin;
    }
    public void setEngineVin(String engineVin) {
        this.engineVin = engineVin;
    }




    @Column(name = "TOTAL_MASS")
    public Double getTotalMass() {
        return totalMass;
    }

    public void setTotalMass(Double totalMass) {
        this.totalMass = totalMass;
    }
    @Column(name = "PULL_TOTAL_MASS")
    public Double getPullTotalMass() {
        return pullTotalMass;
    }

    public void setPullTotalMass(Double pullTotalMass) {
        this.pullTotalMass = pullTotalMass;
    }
    @Column(name = "VEHICLE_WEIGHT")
    public Double getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(Double vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }
    @Column(name = "PERSION_NUMBER")
    public Integer getPersionNumber() {
        return persionNumber;
    }

    public void setPersionNumber(Integer persionNumber) {
        this.persionNumber = persionNumber;
    }







    @Column(name = "DISPLACEMENT")
    public Double getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Double displacement) {
        this.displacement = displacement;
    }
    @Column(name = "EMI_STANDARD")
    public Integer getEmiStandard() {
        return emiStandard;
    }

    public void setEmiStandard(Integer emiStandard) {
        this.emiStandard = emiStandard;
    }
    @Column(name = "MAX_PS")
    public Double getMaxPs() {
        return maxPs;
    }

    public void setMaxPs(Double maxPs) {
        this.maxPs = maxPs;
    }
    @Column(name = "MAX_TORQUE")
    public Double getMaxTorque() {
        return maxTorque;
    }

    public void setMaxTorque(Double maxTorque) {
        this.maxTorque = maxTorque;
    }
    @Column(name = "MAX_KW")
    public Double getMaxKm() {
        return maxKm;
    }

    public void setMaxKm(Double maxKm) {
        this.maxKm = maxKm;
    }
    @Column(name = "GEAR_BOX_TYPE")
    public Double getGearBoxType() {
        return gearBoxType;
    }

    public void setGearBoxType(Double gearBoxType) {
        this.gearBoxType = gearBoxType;
    }
    @Column(name = "TIRE_NUMBER")
    public Integer getTireNumber() {
        return tireNumber;
    }

    public void setTireNumber(Integer tireNumber) {
        this.tireNumber = tireNumber;
    }

    @Column(name = "OIL_TYPE")
    public Integer getOilType() {
        return oilType;
    }

    public void setOilType(Integer oilType) {
        this.oilType = oilType;
    }
    @Column(name = "OIL_SIZE")
    public Double getOilSize() {
        return oilSize;
    }

    public void setOilSize(Double oilSize) {
        this.oilSize = oilSize;
    }
    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    @Column(name = "MILEAGE")
    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }


    @Column(name = "TIRE_TYPE",length = 50)
    public String getTireType() {
        return tireType;
    }
    public void setTireType(String tireType) {
        this.tireType = tireType;
    }



    @Column(name = "BUY_TIME")
    public Timestamp getBuyTime() {
        return buyTime;
    }
    public void setBuyTime(Timestamp buyTime) {
        this.buyTime = buyTime;
    }

    @Column(name = "BUY_ADDRESS",length = 150)
    public String getBuyAddress() {
        return buyAddress;
    }
    public void setBuyAddress(String buyAddress) {
        this.buyAddress = buyAddress;
    }

    @Column(name = "SUPPLIER_NAME",length = 50)
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Column(name = "SUPPLIER_IPHONE",length = 50)
    public String getSupplierIphone() {
        return supplierIphone;
    }
    public void setSupplierIphone(String supplierIphone) {
        this.supplierIphone = supplierIphone;
    }

    @Column(name = "SOURCE")
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
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
//    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="vehicleHead")
//    public List<Driver> getDrivers() {
//        return drivers;
//    }
//    public void setDrivers(List<Driver> drivers) {
//        this.drivers = drivers;
//    }


    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_REGISTRATION_ID")
    public JcRegistration getJcRegistration() {
        return jcRegistration;
    }
    public void setJcRegistration(JcRegistration jcRegistration) {
        this.jcRegistration = jcRegistration;
    }


    @JSONField(serialize=false)
    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_ORGANIZATION_ID_THEIR")
    public Organization getOrganization1() {
        return organization1;
    }
    public void setOrganization1(Organization organization1) {
        this.organization1 = organization1;
    }
}