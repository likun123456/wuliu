package com.kytms.transportorder.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.OrderReceivingParty;
import com.kytms.transportorder.dao.OrderRPDao;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2018-01-05
 */
@Repository(value = "OrderRPDao")
public class OrderRPDaoImpl extends BaseDaoImpl<OrderReceivingParty> implements OrderRPDao<OrderReceivingParty> {
}
