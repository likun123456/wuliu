package com.kytms.rbac.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/20 0020.
 * 角色业务层
 */
public interface RoleService<Role> extends BaseService<Role> {
    JgGridListModel getRoleList(CommModel commModel);

    List<TreeModel> getRoleMenuTree(CommModel commModel);

    void saveMenu(String id, String ids);

    List<TreeModel> getButtonFucntionTree(CommModel commModel);

    void saveButton(String id, String ids);

}
