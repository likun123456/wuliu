package com.kytms.shipmentTemplate.action;

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.*;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.shipmentTemplate.service.TemplateService;
import com.kytms.shipmentback.service.Impl.ShipmentBackServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 运单模板控制层
 * @author 陈小龙
 * @create 2018-04-09
 */
@Controller
@RequestMapping("/template")
public class TemplateAction extends BaseAction {
    private final Logger log = Logger.getLogger(TemplateAction.class);//输出Log日志
    private TemplateService<ShipmentTemplate> templateService;
    @Resource
    public void setTemplateService(TemplateService<ShipmentTemplate> templateService) {
        this.templateService = templateService;
    }




    @RequestMapping(value = "/getTemplateList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getList(CommModel commModel){
        JgGridListModel jgGridListModel =templateService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/saveTemplate", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveTemplate(String str){
        ReturnModel returnModel = getReturnModel();
        ShipmentTemplate shipmentTemplate= JSONObject.parseObject(str, ShipmentTemplate.class);
        //唯一性校驗
        returnModel.codeUniqueAndNull(shipmentTemplate,templateService);

        boolean result = returnModel.isResult();
        if (result){
            ShipmentTemplate shipmentTemplate1 =    templateService.saveTemplate(shipmentTemplate);
            returnModel.setObj(shipmentTemplate1);
        }
        return returnModel.toJsonString();
    }
}
