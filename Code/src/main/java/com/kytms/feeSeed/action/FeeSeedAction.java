package com.kytms.feeSeed.action;

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.FeeSeed;
import com.kytms.core.entity.Order;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.driverUpload.service.Impl.DriverUploadServiceImpl;
import com.kytms.feeSeed.service.FeeSeedService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @创建人: 陈小龙
 * @创建日期: 2018/10/29
 * @类描述:
 */
@Controller
@RequestMapping("/feeSeed")
public class FeeSeedAction extends BaseAction {
    private final Logger log = Logger.getLogger(FeeSeedAction.class);//输出Log日志
    private FeeSeedService<FeeSeed> feeSeedService;
    @Resource(name = "FeeSeedService")
    public void setFeeSeedService(FeeSeedService<FeeSeed> feeSeedService) {
        this.feeSeedService = feeSeedService;
    }


    @RequestMapping(value = "/getFeeSeedList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getList(CommModel commModel){
        JgGridListModel jgGridListModel =feeSeedService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/getFeeSeed", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getFeeSeed(CommModel commModel) {
        FeeSeed feeSeed = (FeeSeed) feeSeedService.selectBean(commModel.getId());
        String json = returnJsonForBean(feeSeed);
        return json;
    }

    @RequestMapping(value = "/saveFeeSeed", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveFeeSeed(String feeSeed){
        FeeSeed feeSeed1= JSONObject.parseObject(feeSeed, FeeSeed.class);
        ReturnModel returnModel = getReturnModel();
        if (feeSeed1.getTime() == null) {
            returnModel.addError("time", "受理时间不能为空");
        }
        if (feeSeed1.getReceiveOrganization() == null) {
            returnModel.addError("receiveOranization", "接收机构不能为空");
        }
        feeSeedService.saveFeeSeed(feeSeed1);
        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/confirm", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String confirm(String id) {
        ReturnModel returnModel = getReturnModel();
        try {
            feeSeedService.confirm(id);
        }catch (MessageException me){
            returnModel.setType(ReturnModel.STRING_FALSE);
            returnModel.setObj(me.getMessage());
        }

        return returnModel.toJsonString();
    }
}
