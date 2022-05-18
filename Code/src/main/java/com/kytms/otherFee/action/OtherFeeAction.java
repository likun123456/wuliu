package com.kytms.otherFee.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.OtherFee;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.otherCost.service.impl.OtherCostServiceImpl;
import com.kytms.otherFee.service.OtherFeeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2018/11/6
 */
@Controller
@RequestMapping("/otherFee")
public class OtherFeeAction extends BaseAction {
    private final Logger log = Logger.getLogger(OtherFeeAction.class);//输出Log日志
    private OtherFeeService<OtherFee> otherFeeService;

    @Resource(name = "OtherFeeService")
    public void setOtherFeeService(OtherFeeService<OtherFee> otherFeeService) {
        this.otherFeeService = otherFeeService;
    }

    @RequestMapping(value = "/getOtherFeeList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOtherFeeList(CommModel commModel){
                String where = null;
                String orderBy = null;
        JgGridListModel jgGridListModel = otherFeeService.getListByPage(commModel,where,orderBy);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/saveOtherFee", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveOtherFee(OtherFee otherFee){
        ReturnModel returnModel = new ReturnModel();
         if(returnModel.isResult()){
             otherFee.setTaxRate(otherFee.getTaxRate());
             otherFeeService.saveBean(otherFee);
         }

        return  returnModel.toJsonString();
    }
}
