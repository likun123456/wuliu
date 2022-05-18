package com.kytms.deliveryPrice.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.DeliveryPrice;
import com.kytms.deliveryPrice.action.DeliveryPriceAction;
import com.kytms.deliveryPrice.dao.DeliveryPriceDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "DeliveryPriceDao")
public class DeliveryPriceDaoImpl extends BaseDaoImpl<DeliveryPrice> implements DeliveryPriceDao<DeliveryPrice> {
    private final Logger log = Logger.getLogger(DeliveryPriceDaoImpl.class);//输出Log日志
}
