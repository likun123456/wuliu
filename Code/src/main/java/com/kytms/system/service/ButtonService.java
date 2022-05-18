package com.kytms.system.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-20
 */
public interface ButtonService<Button> extends BaseService<Button> {

    List<Button> selectButtonList(CommModel commModel);

    Map<String,String> selectUserButtonList(String userId);
}
