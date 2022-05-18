package com.kytms.transportorder;

import com.kytms.core.entity.Order;
import com.kytms.core.entity.OrderTrack;
import com.kytms.core.utils.DateUtils;
import com.kytms.core.utils.SpringUtils;
import com.kytms.transportorder.dao.OrderTrackDao;
import com.kytms.transportorder.dao.impl.OrderTrackDaoImpl;

/**
 * 奇趣源码商城 www.qiqucode.com
 * <p>
 * 在途跟踪工具类
 *
 * @author 奇趣源码
 * @create 2018-01-25
 */
public abstract class OrderTrackUtil {


    /**
     * 添加一个在途信息
     *
     * @param order
     * @param event
     * @return
     */
    public static OrderTrack addTrack(Order order, String event) {
        OrderTrackDao<OrderTrack> orderTrackDao = SpringUtils.getBean(OrderTrackDaoImpl.class);
        OrderTrack orderTrack = new OrderTrack();//在途跟踪登记
        orderTrack.setTime(DateUtils.getTimestamp());
        orderTrack.setEvent(event);
        orderTrack.setOrder(order);
        return (OrderTrack) orderTrackDao.savaBean(orderTrack);
    }
}
