package com.kytms.otherCost.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.OtherCost;
import com.kytms.core.entity.ZoneStoreroom;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.organization.service.impl.OrgServiceImpl;
import com.kytms.otherCost.service.OtherCostService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/otherCost")
public class OtherCostAction extends BaseAction {
    private final Logger log = Logger.getLogger(OtherCostAction.class);//输出Log日志
    private OtherCostService<OtherCost> otherCostService;

    @Resource(name = "OtherCostService")
    public void setOtherCostService(OtherCostService<OtherCost> otherCostService) {
        this.otherCostService = otherCostService;
    }
    /**
     * 获取其他单价列表
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getOtherCostList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getList(CommModel commModel) {
        JgGridListModel jgGridListModel = otherCostService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    /**
     *其他单价表保存方法
     */
    @RequestMapping(value = "/saveOtherCost",produces = "test/json;charset=UTF-8")
    @ResponseBody
    public String saveOtherCost(OtherCost otherCost){
        ReturnModel returnModel = getReturnModel();
        if(otherCost.getOrganization() == null){
            otherCost.setOrganization(SessionUtil.getOrg());
        }
        otherCostService.saveBean(otherCost);
        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();

    }
}
