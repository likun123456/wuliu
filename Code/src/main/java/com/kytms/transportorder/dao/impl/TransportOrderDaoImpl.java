package com.kytms.transportorder.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Order;
import com.kytms.transportorder.dao.TransportOrderDao;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2018-01-05
 */
@Repository(value = "TransportOrderDao")
public class TransportOrderDaoImpl  extends BaseDaoImpl<Order> implements TransportOrderDao<Order> {
}
