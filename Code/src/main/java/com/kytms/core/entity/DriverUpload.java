package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "JC_DRIVERUPLOAD")
public class DriverUpload extends BaseEntityNoCode implements Serializable{
   private String path; //路径
   private  Driver driver;//司机
    private String oldName; //原文件名
    private  String newName;//新文件名


    private Logger log = Logger.getLogger(DriverUpload.class);//输出Log日志

    @Column(name = "PATH")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_DRIVER_ID")
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Column(name = "OLD_NAME")
    public String getOldName() {
        return oldName;
    }
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    @Column(name = "NEW_NAME")
    public String getNewName() {
        return newName;
    }
    public void setNewName(String newName) {
        this.newName = newName;
    }
}
