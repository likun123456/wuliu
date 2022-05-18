package com.kytms.vehicle.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车型SERVICE
 *
 * @author 陈小龙
 * @create 2018-01-10
 */
public interface VehicleService<Vehicle> extends BaseService<Vehicle> {
    JgGridListModel getList(CommModel commModel);
    /**
     * 获取车型 孙德增编写
     */
    List<TreeModel> getVehicle(CommModel commModel);

}
