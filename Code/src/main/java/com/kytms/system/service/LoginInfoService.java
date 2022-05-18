package com.kytms.system.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * Menu
 *
 * @author 奇趣源码
 * @create 2017-11-18
 */
public interface LoginInfoService<LoginInfo> extends BaseService<LoginInfo>{
    JgGridListModel getLoginInfoList(CommModel comm);

}
