package com.kytms.zone.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Zone;
import com.kytms.zone.dao.ZoneDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-23
 */
@Repository(value = "ZoneDao")
public class ZoneDaoImpl extends BaseDaoImpl<Zone> implements ZoneDao<Zone> {
    public List<Zone> selectAllZone() {
        String Hql = "FROM JC_ZONE WHERE 1=1";
        Query query = openSession().createQuery(Hql);
        List<Zone> zoneList=query.list();
        return  zoneList;
    }
}
