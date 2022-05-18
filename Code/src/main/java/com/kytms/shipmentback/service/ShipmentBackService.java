package com.kytms.shipmentback.service;

import com.kytms.core.entity.ShipmentBackDetail;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2018-01-19
 */
public interface ShipmentBackService<ShipmentBack> extends BaseService<ShipmentBack> {

    JgGridListModel getShipmentBackList(CommModel commModel);

    void sing(ShipmentBackDetail shipmentBack);

    void back(ShipmentBack shipmentBack);

    void end(String ids);

    void singAll(CommModel comm);
}
