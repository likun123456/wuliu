package com.kytms.transportorder.action;

import com.kytms.core.entity.Order;
import com.kytms.core.entity.OrderReceivingParty;
import com.kytms.system.service.Impl.SystemConfigServiceImpl;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 订单实体类封装
 *
 * @author
 * @create 2018-01-11
 */
public class OrderForm {
    private final Logger log = Logger.getLogger(OrderForm.class);//输出Log日志
    private Order order;
    private List<OrderReceivingParty> orderReceivingParties;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderReceivingParty> getOrderReceivingParties() {
        return orderReceivingParties;
    }

    public void setOrderReceivingParties(List<OrderReceivingParty> orderReceivingParties) {
        this.orderReceivingParties = orderReceivingParties;
    }
}
