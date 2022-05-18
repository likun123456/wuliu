package com.kytms.core.model;

import com.kytms.core.listener.SystemInfoListener;
import org.apache.log4j.Logger;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 通用请求注入模型
 * @author 奇趣源码
 * @create 2017-11-18
 */
public class CommModel {
    /**ID*/
    private String id;
    /**行数*/
    private int rows;
    /**页数*/
    private int page;
    /**排序字段升序降序说法*/
    private String sord;
    /**排序名称*/
    private String sidx;
    /**查询值*/
    private String whereValue;
    /**查询字段名称*/
    private String whereName;
    /**状态*/
    private String status;
    //其他
    private String other;

    private final Logger log = Logger.getLogger(CommModel.class);//输出Log日志
    //-------------------------分割线-------------------------------------------

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getWhereValue() {
        return whereValue;
    }

    public void setWhereValue(String whereValue) {
        this.whereValue = whereValue;
    }

    public String getWhereName() {
        return whereName;
    }

    public void setWhereName(String whereName) {
        this.whereName = whereName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
