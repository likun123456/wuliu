package com.kytms.transportorder.service.Impl;

        import com.kytms.core.entity.OrderReceivingParty;
        import com.kytms.core.service.impl.BaseServiceImpl;
        import com.kytms.transportorder.dao.OrderRPDao;
        import com.kytms.transportorder.service.OrderRPService;
        import org.apache.log4j.Logger;
        import org.springframework.stereotype.Service;

        import javax.annotation.Resource;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2017-11-23
 */
@Service(value = "OrderRPService")
public class OrderRPServiceImpl extends BaseServiceImpl<OrderReceivingParty> implements OrderRPService<OrderReceivingParty> {
    private final Logger log = Logger.getLogger(OrderRPServiceImpl.class);//输出Log日志
    private OrderRPDao orderRPDao;

    @Resource(name = "OrderRPDao")
    public void setOrderRPDao(OrderRPDao orderRPDao) {
        super.setBaseDao(orderRPDao);
        this.orderRPDao = orderRPDao;
    }
}
