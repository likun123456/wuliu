package com.kytms.vehicle.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Vehicle;
import com.kytms.vehicle.dao.VehicleDao;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车型DAO实现类
 *
 * @author 陈小龙
 * @create 2018-01-10
 */
@Repository(value = "VehicleDao")
public class VehicleDaoImpl extends BaseDaoImpl<Vehicle> implements VehicleDao<Vehicle>{
}
