package com.kytms.shipment.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.BerthStand;
import com.kytms.shipment.action.ShipmentAction;
import com.kytms.shipment.dao.BerthStandDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/8.
 */
@Repository(value = "BerthStandDao")
public class BerthStandDaoImpl extends BaseDaoImpl<BerthStand> implements BerthStandDao<BerthStand> {
    private final Logger log = Logger.getLogger(BerthStandDaoImpl.class);//输出Log日志
}
