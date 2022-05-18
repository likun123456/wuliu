package com.kytms.shipmentback.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.ShipmentBack;
import com.kytms.shipmentback.action.ShipmentBackAction;
import com.kytms.shipmentback.dao.ShipmentBackDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2018-01-19
 */
@Repository(value = "ShipmentBackDao")
public class ShipmentBackDaoImpl extends BaseDaoImpl<ShipmentBack> implements ShipmentBackDao<ShipmentBack> {
    private final Logger log = Logger.getLogger(ShipmentBackDaoImpl.class);//输出Log日志
}
