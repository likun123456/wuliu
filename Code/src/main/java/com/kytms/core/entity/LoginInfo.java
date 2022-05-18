package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 登陆信息
 * @author 奇趣源码
 * @create 2017-11-25
 */
@Entity(name = "JC_SYS_LOGIN_LOG")
public class LoginInfo extends BaseEntityNoCode  implements Serializable {
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp loginTime;
    private String code;
    private String ip;


    private Logger log = Logger.getLogger(LoginInfo.class);//输出Log日志



    @Column(name = "LOGIN_TIME", nullable = false)
    public Timestamp getLoginTime() {
        return loginTime;
    }
    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    @Column(name = "CODE", nullable = false,length = 50)
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "IP", nullable = false,length = 20)
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
}
