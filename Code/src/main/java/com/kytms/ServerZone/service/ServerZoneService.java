package com.kytms.ServerZone.service;

import com.kytms.ServerZone.service.impl.ServerZoneServiceImpl;
import com.kytms.core.entity.ServerZone;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;
import org.apache.log4j.Logger;

import java.io.File;


public interface ServerZoneService<ServerZone> extends BaseService<ServerZone> {
    JgGridListModel getList(CommModel commModel);

    void saveServerZone(ServerZone serverZone);

    void lxupload(File f);

}
