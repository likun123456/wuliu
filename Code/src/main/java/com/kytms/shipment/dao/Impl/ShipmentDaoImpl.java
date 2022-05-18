package com.kytms.shipment.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Shipment;
import com.kytms.shipment.dao.ShipmentDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/8.
 */
@Repository(value = "ShipmentDao")
public class ShipmentDaoImpl extends BaseDaoImpl<Shipment> implements ShipmentDao<Shipment> {
    private final Logger log = Logger.getLogger(ShipmentDaoImpl.class);//输出Log日志
}
