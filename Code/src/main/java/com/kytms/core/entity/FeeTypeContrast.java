package com.kytms.core.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author:sundezeng
 * @Date:2019/3/15
 */
@Entity(name = "JC_FEE_TYPE_CONTRAST")
public class FeeTypeContrast extends BaseEntityNoCode implements Serializable {
    private String bjfyId;//本系统费用ID
    private String bjfyName;//本系统费用名称
    private String bjfyLx;//本系统费用类型
    private Organization organization;//组织机构

    private Integer tmsFyId;//TMS系统费用ID
    private String tmsFyName;//TMS系统费用名称
    private  String tmsFyLx;//TMS系统费用类型

    //----------------------------------------

    @ManyToOne(cascade={CascadeType.PERSIST })
    @JoinColumn(name = "JC_ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Column(name = "BJ_FY_ID")
    public String getBjfyId() {
        return bjfyId;
    }

    public void setBjfyId(String bjfyId) {
        this.bjfyId = bjfyId;
    }
    @Column(name = "BJ_FY_NAME")
    public String getBjfyName() {
        return bjfyName;
    }

    public void setBjfyName(String bjfyName) {
        this.bjfyName = bjfyName;
    }
    @Column(name = "BJ_FY_LX")
    public String getBjfyLx() {
        return bjfyLx;
    }

    public void setBjfyLx(String bjfyLx) {
        this.bjfyLx = bjfyLx;
    }

    @Column(name = "TMS_FY_LX")
    public String getTmsFyLx() {
        return tmsFyLx;
    }

    public void setTmsFyLx(String tmsFyLx) {
        this.tmsFyLx = tmsFyLx;
    }

    @Column(name = "TMS_FY_ID")
    public Integer getTmsFyId() {
        return tmsFyId;
    }

    public void setTmsFyId(Integer tmsFyId) {
        this.tmsFyId = tmsFyId;
    }
    @Column(name = "TMS_FY_NAME")
    public String getTmsFyName() {
        return tmsFyName;
    }

    public void setTmsFyName(String tmsFyName) {
        this.tmsFyName = tmsFyName;
    }
}
