package com.kytms.driverUpload.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.DriverUpload;
import com.kytms.driverUpload.action.DriverUploadAction;
import com.kytms.driverUpload.dao.DriverUploadDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "DriverUploadDao")
public class DriverUploadDaoImpl extends BaseDaoImpl<DriverUpload> implements DriverUploadDao<DriverUpload> {
    private final Logger log = Logger.getLogger(DriverUploadDaoImpl.class);//输出Log日志
}
