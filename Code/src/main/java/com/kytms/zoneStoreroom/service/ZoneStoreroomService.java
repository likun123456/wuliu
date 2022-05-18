package com.kytms.zoneStoreroom.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;

import java.util.List;


public interface ZoneStoreroomService<ZoneStoreroom> extends BaseService<ZoneStoreroom> {
    JgGridListModel getList(CommModel commModel);

    void saveZoneStoreroom(ZoneStoreroom zoneStoreroom);

    List<TreeModel> getZoneStoreroomMap(String orgId);
}
