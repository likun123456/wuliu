package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 系统配置表
 * @author  奇趣源码
 * @create 2018-01-02
 */
@Entity(name ="JC_SYS_CONFIG")
public class Config extends BaseEntityNoCode  implements Serializable {
    private String companyName; //公司名称
    private String systemName; //系统名称
    private int trafficRoundNumber; //系统运量四舍无入
    private int moneyRoundNumber; //系统金额四舍五入
    private int inputRoundNumber; //税金四舍五入


    private Logger log = Logger.getLogger(Config.class);//输出Log日志


    @Column(name = "COMPANY_NAME",nullable = false, length = 100)
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "SYSTEN_NAME",nullable = false, length = 100)
    public String getSystemName() {
        return systemName;
    }
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Column(name = "TRAFFIC_ROUND_NAME",nullable = false, length = 100)
    public int getTrafficRoundNumber() {
        return trafficRoundNumber;
    }
    public void setTrafficRoundNumber(int trafficRoundNumber) {
        this.trafficRoundNumber = trafficRoundNumber;
    }

    @Column(name = "MONEY_ROUND_NUMBER",nullable = false, length = 100)
    public int getMoneyRoundNumber() {
        return moneyRoundNumber;
    }
    public void setMoneyRoundNumber(int moneyRoundNumber) {
        this.moneyRoundNumber = moneyRoundNumber;
    }

    @Column(name = "INPUT_ROUND_NUMBER",nullable = false, length = 100)
    public int getInputRoundNumber() {
        return inputRoundNumber;
    }
    public void setInputRoundNumber(int inputRoundNumber) {
        this.inputRoundNumber = inputRoundNumber;
    }
}
