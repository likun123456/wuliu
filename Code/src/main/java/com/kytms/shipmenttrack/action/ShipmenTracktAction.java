package com.kytms.shipmenttrack.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Shipment;
import com.kytms.core.entity.ShipmentTrack;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.shipment.service.ShipmentService;
import com.kytms.shipmentTemplate.service.impl.TemplateServiceImpl;
import com.kytms.shipmenttrack.service.ShipmentTrackService;
import com.kytms.transportorder.OrderStatus;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/8.
 * 运单追踪
 */
@Controller
@RequestMapping("/shipmenttrack")
public class ShipmenTracktAction extends BaseAction {
    private final Logger log = Logger.getLogger(ShipmenTracktAction.class);//输出Log日志
    private ShipmentTrackService shipmentTrackService;
    private ShipmentService shipmentService;
    @Resource
    public void setShipmentTrackService(ShipmentTrackService shipmentTrackService) {
        this.shipmentTrackService = shipmentTrackService;
    }
    @Resource
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }




    @RequestMapping(value = "/getTrackList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getTrackList(CommModel commModel){
        JgGridListModel jgGridListModel = shipmentTrackService.getTrackList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/getTrackforString", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getTrackforString(CommModel commModel){
        ReturnModel returnModel = getReturnModel();
        String s = shipmentTrackService.getTrackforString(commModel);
        returnModel.setObj(s);
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/saveTrack", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveTrack(ShipmentTrack shipmentTrack) {
        ReturnModel returnModel = getReturnModel();
        //唯一性校验
        Shipment shipment = (Shipment) shipmentService.selectBean(shipmentTrack.getShipment().getId());
        if(shipment.getStatus() != OrderStatus.DESPATCH.getValue()){
            returnModel.addError("event","只能在在发运状态下进行跟踪");
        }
        Boolean isResult = returnModel.isResult();
        if (isResult) {
            shipmentTrackService.saveBean(shipmentTrack);
        }
        return returnModel.toJsonString();
    }
}
