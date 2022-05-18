package com.kytms.vehicleArrive.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.VehicleArrive;
import com.kytms.vehicleArrive.dao.VehicleArriveDao;
import org.springframework.stereotype.Repository;

/**
 * @Author:sundezeng
 * @Date:2018/10/24
 */
@Repository(value = "VehicleArriveDao")
public class VehicleArriveDaoImpl extends BaseDaoImpl<VehicleArrive> implements VehicleArriveDao<VehicleArrive> {
}
