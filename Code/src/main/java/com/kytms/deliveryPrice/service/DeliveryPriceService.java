package com.kytms.deliveryPrice.service;

import com.kytms.core.entity.DeliveryPrice;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

public interface DeliveryPriceService<DeliveryPrice> extends BaseService<DeliveryPrice> {
    JgGridListModel getList(CommModel commModel);
}
