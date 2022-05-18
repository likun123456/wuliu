package com.kytms.shipmenttrack.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.ShipmentTrack;
import com.kytms.shipmenttrack.action.ShipmenTracktAction;
import com.kytms.shipmenttrack.dao.ShipmentTrackDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/8.
 */
@Repository(value = "ShipmentTrackDao")
public class ShipmentTrackDaoImpl extends BaseDaoImpl<ShipmentTrack> implements ShipmentTrackDao<ShipmentTrack> {
    private final Logger log = Logger.getLogger(ShipmentTrackDaoImpl.class);//输出Log日志
}
