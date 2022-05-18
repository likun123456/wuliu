package com.kytms.api.action;

import com.kytms.carrier.service.CarrierService;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Carrier;
import com.kytms.core.entity.ObjectUtils;
import com.kytms.core.entity.Order;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.transportorder.action.TransportOrderAction;
import com.kytms.transportorder.dao.TransportOrderDao;
import com.kytms.transportorder.service.TransportOrderService;
import com.kytms.weizhitms.datasource.TMSDataSource;
import net.sf.json.util.JSONBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 承运商设置控制层
 * @author 陈小龙
 * @create 2018-01-15
 */

@Controller
@RequestMapping("/api")
public class Api extends BaseAction {
     private TransportOrderService transportOrderService;
     private TransportOrderDao transportOrderDao;
    private Logger log = Logger.getLogger(Api.class);//输出Log日志

    @Resource
    public void setTransportOrderService(TransportOrderService transportOrderService) {
        this.transportOrderService = transportOrderService;
    }

    @Resource
    public void setTransportOrderDao(TransportOrderDao transportOrderDao) {
        this.transportOrderDao = transportOrderDao;
    }
    @RequestMapping(value = "/getOnlord", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOnlord(String str){
        String id = null;
        Order order = null;
        List list1= null;
        String hql ="select id from JC_ORDER where relatebill1 ='" + str +"'";
        List list = transportOrderDao.executeQueryHql(hql);
         if(list.size()>0) {
             Object o = list.get(0);
             id = o.toString();
             order = (Order) transportOrderService.selectBean(id);
             list1 = order.getOrderTracks();
         }
        //return returnJsonForBean(order.getOrderTracks());
        return returnJson(list1);
    }

}
