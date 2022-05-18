package com.kytms.driverset.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Driver;
import com.kytms.driverset.action.DriverAction;
import com.kytms.driverset.dao.DriverDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/12/4 0004.
 * 司机Dao实现类
 */
@Repository(value = "DriverDao")
public class DriverDaoImpl extends BaseDaoImpl<Driver> implements DriverDao<Driver> {
    private final Logger log = Logger.getLogger(DriverDaoImpl.class);//输出Log日志
}
