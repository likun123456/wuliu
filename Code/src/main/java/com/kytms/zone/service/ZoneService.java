package com.kytms.zone.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-23
 */
public interface ZoneService<Zone> extends BaseService<Zone> {
    List<TreeModel> getZoneTree(String id);
    List<TreeModel> getZoneTree(String roleId,String id);

    List<Zone> getZoneGrid(CommModel commModel);

    List<TreeModel> getCity(CommModel commModel);

    List<Zone> selectAllZone();

    List<TreeModel> getAll(CommModel commModel);
}
