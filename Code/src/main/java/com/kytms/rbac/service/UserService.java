package com.kytms.rbac.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 用户服务层接口
 * @author 奇趣源码
 * @create 2017-11-18
 */

public interface UserService<User> extends BaseService<User>{
    JgGridListModel getUserList(CommModel commModel);

    List<TreeModel> getRoleTree(String id);

    void saveRole(String id, String ids);

    List<TreeModel> getOrgTree(String id);

    void saveUserOrg(String id,String ids);

    User userLogin(String code, String password);

    Object updatePassWord(String tableName, String age[]);
}
