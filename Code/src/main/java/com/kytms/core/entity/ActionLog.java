package com.kytms.core.entity;/**
 * Created by nidaye on 2018/11/9.
 */

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2018-11-09
 */
@Entity(name = "JC_SYS_ACTION_LOG")
public class ActionLog extends BaseEntityNoCode implements Serializable {
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;//执行时间
    private Long exeTime;//执行时长
    private String url;//执行路径
    private String actionName;
    private String methodName;
    private Logger log = Logger.getLogger(ActionLog.class);//输出Log日志

    @JoinColumn(name = "ACTION_Name")
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    @JoinColumn(name = "METHOD_Name")
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @JoinColumn(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JoinColumn(name = "EXE_TIME")
    public Long getExeTime() {
        return exeTime;
    }

    public void setExeTime(Long exeTime) {
        this.exeTime = exeTime;
    }

    @JoinColumn(name = "TIME")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
