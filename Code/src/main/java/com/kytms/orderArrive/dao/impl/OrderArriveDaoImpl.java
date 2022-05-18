package com.kytms.orderArrive.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.OrderArrive;
import com.kytms.orderArrive.action.OrderArriveAction;
import com.kytms.orderArrive.dao.OrderArriveDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @Author:sundezeng
 * @Date:2018/10/29
 */
@Repository(value = "OrderArriveDao")
public class OrderArriveDaoImpl extends BaseDaoImpl<OrderArrive> implements OrderArriveDao<OrderArrive> {
    private final Logger log = Logger.getLogger(OrderArriveDaoImpl.class);//输出Log日志
}
