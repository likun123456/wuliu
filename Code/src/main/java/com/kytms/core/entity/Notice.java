package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 系统通告
 * @author 奇趣源码
 * @create 2017-12-07
 */
@Entity(name="JC_SYS_NOTICE")
public class Notice extends BaseEntity implements Serializable {
    private int type;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp time;
    private String notice;

    private Logger log = Logger.getLogger(Notice.class);//输出Log日志


    @Column(name = "TYPE", nullable = false, length = 5)
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "TIME")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "NOTICE", length = 5000)
    public String getNotice() {
        return notice;
    }
    public void setNotice(String notice) {
        this.notice = notice;
    }
}
