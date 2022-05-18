package com.kytms.zone.dao;

import com.kytms.core.dao.BaseDao;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-23
 */
public interface ZoneDao<Zone> extends BaseDao<Zone> {
    List<Zone> selectAllZone();
}
