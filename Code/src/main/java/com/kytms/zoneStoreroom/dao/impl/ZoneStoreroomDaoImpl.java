package com.kytms.zoneStoreroom.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.ZoneStoreroom;
import com.kytms.zoneStoreroom.dao.ZoneStoreroomDao;
import org.springframework.stereotype.Repository;

@Repository(value = "ZoneStoreroomDao")
public class ZoneStoreroomDaoImpl extends BaseDaoImpl<ZoneStoreroom> implements  ZoneStoreroomDao<ZoneStoreroom> {
}
