package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 用户管理
 * @author 奇趣源码
 * @create 2017-11-17
 */
@Entity(name = "JC_SYS_USER")
public class User extends BaseEntity implements Serializable {
    private String password;
    private String email;
    private String phone;
    private String dept;
    private List<Role> roles;
    private List<Organization> organizations;

    private final Logger log = Logger.getLogger(User.class);//输出Log日志

    @Column(name = "PASSWORD",nullable = false, length = 32)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "E_MAIL",length = 50)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "PHONE", length = 20)
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JSONField(serialize = false)
    @JoinTable(name = "JC_SYS_USER_ROLE", joinColumns = @JoinColumn(name = "JC_SYS_USER_ID"), inverseJoinColumns = @JoinColumn(name = "JC_SYS_ROLE_ID"))
    @ManyToMany(cascade = {CascadeType.PERSIST})
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @JSONField(serialize = false)
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "JC_SYS_USER_ORGANIZATION", joinColumns = @JoinColumn(name = "JC_SYS_USER_ID"), inverseJoinColumns = @JoinColumn(name = "JC_SYS_ORGANIZATION_ID"))
    public List<Organization> getOrganizations() {
        return organizations;
    }
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Column(name = "DEPT", length = 20)
    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }
}
