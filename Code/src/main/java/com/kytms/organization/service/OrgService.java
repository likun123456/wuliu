package com.kytms.organization.service;

import com.kytms.core.entity.Organization;
import com.kytms.core.entity.User;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-23
 */
public interface OrgService<Organization> extends BaseService<Organization> {
    List<Organization> getOrgGrid(CommModel commModel);
    List<TreeModel> getZoneTree(String roleId, String id);
    void saveZone(String id,String ids);
    JgGridListModel getOrgList(CommModel commModel);
    List<com.kytms.core.entity.Organization> selectUserOrgs(User sessionUser);

    List<com.kytms.core.entity.Organization> getOrgTreeW(CommModel commModel);
}
