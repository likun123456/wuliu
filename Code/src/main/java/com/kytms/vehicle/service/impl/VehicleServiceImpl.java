package com.kytms.vehicle.service.impl;


import com.kytms.core.entity.Vehicle;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.StringUtils;
import com.kytms.vehicle.action.VehicleAction;
import com.kytms.vehicle.dao.VehicleDao;
import com.kytms.vehicle.service.VehicleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车型Service实现类
 *
 * @author 陈小龙
 * @create 2018-01-10
 */
@Service(value = "VehicleService")
public class VehicleServiceImpl extends BaseServiceImpl<Vehicle> implements VehicleService<Vehicle> {
    private final Logger log = Logger.getLogger(VehicleServiceImpl.class);//输出Log日志
    private VehicleDao<Vehicle>  VehicleDao;
    @Resource(name = "VehicleDao")
    public void setVehicleDao(VehicleDao<Vehicle> vehicleDao) {
        super.setBaseDao(vehicleDao);
        VehicleDao = vehicleDao;
    }




    public JgGridListModel getList(CommModel commModel) {
        String where = null;
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where = " and status = "+commModel.getStatus();
        }
        String orderBY = " ORDER BY  create_time desc";
        return super.getListByPage(commModel,where,orderBY);
    }

    /**
     * 获取车型 孙德增编写
     */
    public List<TreeModel> getVehicle(CommModel commModel) {
        String where = " and status = 1";
        List<Vehicle> vehicles = super.selectList(commModel, where, null);
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Vehicle vehicle:vehicles) {
            TreeModel treeModel = new TreeModel();
            treeModel.setText(vehicle.getCode());
            treeModel.setId(vehicle.getId());
            //treeModel.setValue(vehicle.getName());
            treeModels.add(treeModel);
        }
        return treeModels;
    }
}
