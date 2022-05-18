package com.kytms.feetype.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 费用类型service
 * @author 奇趣源码
 * @create 2018-01-04
 */
public interface FeeTypeSerivce<FeeType> extends BaseService<FeeType>  {
    JgGridListModel selectFeeTypeList(CommModel commModel);

    List selectOrderFeeTypeList(CommModel commModel, String where, Object o);

    List<com.kytms.core.entity.FeeType> selectListByHql(String Hql);
}
