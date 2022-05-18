package com.kytms.feeSeed.service;

import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

/**
 * @创建人: 陈小龙
 * @创建日期: 2018/10/29
 * @类描述:
 */
public interface FeeSeedService<FeeSeed> extends BaseService<FeeSeed> {
    JgGridListModel getList(CommModel commModel);
    void saveFeeSeed(FeeSeed feeSeed);
    void confirm(String id) throws MessageException;
}
