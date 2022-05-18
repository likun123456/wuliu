package com.kytms.customerFile.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

/**
 * 奇趣源码商城 www.qiqucode.com
   客户档案服务层接口
 * 陈小龙
 * @create 2018-1-5
 */
public interface CustomerService<Customer> extends BaseService<Customer> {
    JgGridListModel getList(CommModel commModel);
}
