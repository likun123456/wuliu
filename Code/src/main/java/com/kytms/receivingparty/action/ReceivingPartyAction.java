package com.kytms.receivingparty.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.ReceivingParty;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.rbac.shiro.ShiroDbRealm;
import com.kytms.receivingparty.service.ReceivingPartyService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2017/12/18.
 * 收发货方
 */
@Controller
@RequestMapping("/receivingParty")
public class ReceivingPartyAction extends BaseAction {
    private final Logger log = Logger.getLogger(ReceivingPartyAction.class);//输出Log日志
    private ReceivingPartyService<ReceivingParty> receivingPartyService ;
    public ReceivingPartyService<ReceivingParty> getReceivingPartyService() {
        return receivingPartyService;
    }
    @Resource(name = "ReceivingPartyService")
    public void setReceivingPartyService(ReceivingPartyService<ReceivingParty> receivingPartyService) {
        this.receivingPartyService = receivingPartyService;
    }




    @RequestMapping(value = "/getReceivingPartyList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getReceivingPartyList(CommModel commModel){
        JgGridListModel jgGridListModel =receivingPartyService.getReceivingPartyList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/saveReceivingParty", produces = "text/json;charset=UTF-8")
    @ResponseBody
     public String saveReceivingParty(ReceivingParty receivingParty){
        ReturnModel returnModel = getReturnModel();
        boolean result = returnModel.isResult();
        if (result){
            receivingParty.setOrganization(SessionUtil.getOrg());
            receivingPartyService.saveBean(receivingParty);
        }
        return returnModel.toJsonString();
     }
}
