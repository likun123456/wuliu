package com.kytms.shipmentback.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.ShipmentBackDetail;
import com.kytms.shipmentback.dao.ShipmentBackDetailDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2018-01-19
 */
@Repository(value = "ShipmentBackDetailDao")
public class ShipmentBackDetailDaoImpl extends BaseDaoImpl<ShipmentBackDetail> implements ShipmentBackDetailDao<ShipmentBackDetail> {
    private final Logger log = Logger.getLogger(ShipmentBackDetailDaoImpl.class);//输出Log日志
}
