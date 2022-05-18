package com.kytms.orderArrive.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.OrderArrive;
import com.kytms.core.model.ReturnModel;
import com.kytms.orderArrive.service.OrderArriveService;
import com.kytms.orderabnormal.service.impl.AbnormalServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @Author:sundezeng
 * @Date:2018/10/29
 */
@Controller
@RequestMapping("/orderArrive")
public class OrderArriveAction extends BaseAction {
    private final Logger log = Logger.getLogger(OrderArriveAction.class);//输出Log日志
    private OrderArriveService<OrderArrive> orderArriveService;

    @Resource(name = "OrderArriveService")
    public void setOrderArriveService(OrderArriveService<OrderArrive> orderArriveService) {
        this.orderArriveService = orderArriveService;
    }

    @RequestMapping(value = "/saveOrderArrive1", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveOrderArrive1(String date,String ids){
        ReturnModel returnModel = new ReturnModel();
        orderArriveService.saveOrderArrive1(date,ids);
        return returnModel.toJsonString();
    }
}
