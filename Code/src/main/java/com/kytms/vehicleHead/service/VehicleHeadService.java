package com.kytms.vehicleHead.service;


import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车头档案服务层
 *
 * @author 陈小龙
 * @create 2018-01-12
 */
public interface VehicleHeadService<VehicleHead> extends BaseService<VehicleHead> {
    JgGridListModel getList(CommModel commModel);

   void saveVehicleHead(VehicleHead vehicleHead);

    List getOrgVelList();

    List selectCont();
}
