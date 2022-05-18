package com.kytms.otherCost.service;

import com.kytms.core.entity.OtherCost;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;


public interface OtherCostService<OtherCost> extends BaseService<OtherCost> {
    JgGridListModel getList(CommModel commModel);
}
