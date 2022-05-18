package com.kytms.shipmentTemplate.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 运单模板SERVICE
 *
 * @author 陈小龙
 * @create 2018-04-09
 */
public interface TemplateService<ShipmentTemplate> extends BaseService<ShipmentTemplate> {
    JgGridListModel getList(CommModel commModel);
    ShipmentTemplate  saveTemplate(ShipmentTemplate shipmentTemplate);
}
