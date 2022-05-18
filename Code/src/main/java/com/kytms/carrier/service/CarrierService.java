package com.kytms.carrier.service;

import com.kytms.core.entity.Carrier;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

/**
 * 奇趣源码商城 www.qiqucode.com
 *承运商设置服务层
 * @author 陈小龙
 * @create 2018-01-15
 */
public interface CarrierService<Carrier> extends BaseService<Carrier> {
    JgGridListModel getList(CommModel commModel);
    String getCarrier();

}
