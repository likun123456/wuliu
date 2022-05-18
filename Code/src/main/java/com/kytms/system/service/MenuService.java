package com.kytms.system.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-18
 */
public interface MenuService<Menu> extends BaseService<Menu>{
    List<TreeModel> getMneuTree();

    List<Menu> selectMenuList(CommModel comm);

    List<Menu> selectUserMenuList();

    List<com.kytms.core.entity.Menu> selectMenuListMain(CommModel comm);
}
