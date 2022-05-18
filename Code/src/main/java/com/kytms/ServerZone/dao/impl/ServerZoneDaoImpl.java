package com.kytms.ServerZone.dao.impl;

import com.kytms.ServerZone.action.ServerZoneAction;
import com.kytms.ServerZone.dao.ServerZoneDao;
import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.ServerZone;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "ServerZoneDao")
public class ServerZoneDaoImpl extends BaseDaoImpl<ServerZone> implements ServerZoneDao<ServerZone> {
    private final Logger log = Logger.getLogger(ServerZoneDaoImpl.class);//输出Log日志
}
