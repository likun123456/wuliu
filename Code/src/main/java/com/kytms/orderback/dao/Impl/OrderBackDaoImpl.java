package com.kytms.orderback.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.OrderBack;
import com.kytms.orderback.action.OrderBackAction;
import com.kytms.orderback.dao.OrderBackDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2018-01-19
 */
@Repository(value = "OrderBackDao")
public class OrderBackDaoImpl extends BaseDaoImpl<OrderBack> implements OrderBackDao<OrderBack> {
    private final Logger log = Logger.getLogger(OrderBackDaoImpl.class);//输出Log日志
}
