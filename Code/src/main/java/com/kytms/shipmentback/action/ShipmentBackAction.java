package com.kytms.shipmentback.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.ShipmentBack;
import com.kytms.core.entity.ShipmentBackDetail;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.shipment.service.Impl.ShipmentServiceImpl;
import com.kytms.shipmentback.service.ShipmentBackService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 运单回单
 *
 * @author 奇趣源码
 * @create 2018-01-19
 */
@Controller
@RequestMapping("/shipmentback")
public class ShipmentBackAction extends BaseAction {
    private final Logger log = Logger.getLogger(ShipmentBackAction.class);//输出Log日志
    private ShipmentBackService shipmentBackService;
    @Resource
    public void setShipmentBackService(ShipmentBackService shipmentBackService) {
        this.shipmentBackService = shipmentBackService;
    }




    @RequestMapping(value = "/getShipmentBackList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getShipmentBackList(CommModel commModel) {
        JgGridListModel jgGridListModel = shipmentBackService.getShipmentBackList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/getShipmentBackDetail", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getShipmentBackDetail(CommModel commModel) {
        ShipmentBack o = (ShipmentBack) shipmentBackService.selectBean(commModel.getId());
        return returnJsonForBean(o.getShipmentBackDetailList());
    }

    @RequestMapping(value = "/sing", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String sing(ShipmentBackDetail shipmentBackDetail) {
        ReturnModel returnModel = getReturnModel();
        try {
            shipmentBackService.sing(shipmentBackDetail);

        }catch (RuntimeException e){
            e.printStackTrace();
            returnModel.setObj(e.getMessage());
            returnModel.setResult(false);
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/singAll", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String singAll(CommModel Comm) {
        ReturnModel returnModel = getReturnModel();
        try {
            shipmentBackService.singAll(Comm);

        }catch (RuntimeException e){
            returnModel.setObj(e.getMessage());
            returnModel.setResult(false);
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/back", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String back(ShipmentBack shipmentBack) {
        ReturnModel returnModel = getReturnModel();
        try {
            shipmentBackService.back(shipmentBack);
        }catch (RuntimeException e){
            returnModel.setObj(e.getMessage());
            returnModel.setResult(false);
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/end", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String end(String ids) {
        ReturnModel returnModel = getReturnModel();
        try {
            shipmentBackService.end(ids);
        }catch (RuntimeException e){
            returnModel.setObj(e.getMessage());
            returnModel.setResult(false);
        }
        return returnModel.toJsonString();
    }
}
