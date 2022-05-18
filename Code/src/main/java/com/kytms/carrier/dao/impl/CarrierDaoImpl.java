package com.kytms.carrier.dao.impl;

import com.kytms.carrier.dao.CarrierDao;
import com.kytms.carrier.service.impl.CarrierServiceImpl;
import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Carrier;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 承运商设置DAO层实现类
 * @author 陈小龙
 * @create 2018-01-15
 */
@Repository(value = "CarrierDao")
public class CarrierDaoImpl extends BaseDaoImpl<Carrier> implements CarrierDao<Carrier> {
    private Logger log = Logger.getLogger(CarrierDaoImpl.class);//输出Log日志
}
