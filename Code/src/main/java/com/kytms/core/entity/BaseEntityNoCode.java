package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.kytms.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 奇趣源码商城 www.qiqucode.com
 * 实体类基础类（无code）
 * @author 奇趣源码
 * @create 2017-11-17
 */
@MappedSuperclass
public class BaseEntityNoCode implements Serializable {
    private String id;
    private int status = 1;
    private String create_Name;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp create_Time;
    private String modify_Name;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp modify_Time;
    private String description;
    private String name; //角色
    private String field1;
    private String field2;
    private String field3;


    private Logger log = Logger.getLogger(BaseEntityNoCode.class);//输出Log日志


    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    public String getId() {
        return id;
    }
    public void setId(String id) {
        if (StringUtils.isEmpty(id)){
            this.id =null;
            return;
        }
        this.id = id;
    }

    @Column(name = "STATUS",length = 5,columnDefinition="INT default 1")
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "CREATE_NAME",length = 40,updatable=false)
    public String getCreate_Name() {
        return create_Name;
    }
    public void setCreate_Name(String create_Name) {
        this.create_Name = create_Name;
    }

    @Column(name = "CREATE_TIME",updatable=false)
    public Timestamp getCreate_Time() {
        return create_Time;
    }
    public void setCreate_Time(Timestamp create_Time) {
        this.create_Time = create_Time;
    }

    @Column(name = "MODIFY_NAME",length = 40)
    public String getModify_Name() {
        return modify_Name;
    }
    public void setModify_Name(String modify_Name) {
        this.modify_Name = modify_Name;
    }

    @Column(name = "MODIFY_TIME")
    public Timestamp getModify_Time() {
        return modify_Time;
    }
    public void setModify_Time(Timestamp modify_Time) {
        this.modify_Time = modify_Time;
    }

    @Column(name = "DESCRIPTION",length = 255)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "FIELD1",length = 255)
    public String getField1() {
        return field1;
    }
    public void setField1(String field1) {
        this.field1 = field1;
    }

    @Column(name = "FIELD2",length = 255)
    public String getField2() {
        return field2;
    }
    public void setField2(String field2) {
        this.field2 = field2;
    }

    @Column(name = "FIELD3",length = 255)
    public String getField3() {
        return field3;
    }
    public void setField3(String field3) {
        this.field3 = field3;
    }
}
