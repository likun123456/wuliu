package com.kytms.settlement.action;

import com.kytms.ServerZone.service.impl.ServerZoneServiceImpl;
import com.kytms.core.action.BaseAction;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.settlement.service.SettlementDetailsService;
import com.kytms.settlement.service.SettlementService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/5/24.
 * 结算action
 */
@Controller
@RequestMapping("/settlement")
public class SettlementAction extends BaseAction  {
    private final Logger log = Logger.getLogger(SettlementAction.class);//输出Log日志
    private SettlementService settlementService;
    private SettlementDetailsService settlementDetailsService;
    public SettlementDetailsService getSettlementDetailsService() {
        return settlementDetailsService;
    }
    @Resource(name = "SettlementDetailsService")
    public void setSettlementDetailsService(SettlementDetailsService settlementDetailsService) {
        this.settlementDetailsService = settlementDetailsService;
    }
    public SettlementService getSettlementService() {
        return settlementService;
    }
    @Resource(name = "SettlementService")
    public void setSettlementService(SettlementService settlementService) {
        this.settlementService = settlementService;
    }



    @ResponseBody
    @RequestMapping(value = "getSettlementList",produces = "text/json;charset=UTF-8")
    public String getSettlementList(CommModel commModel){
        JgGridListModel jgGridListModel = settlementService.getSettlementList(commModel);
        return jgGridListModel.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/updateSta", produces = "text/json;charset=UTF-8")
    public String updateSta(String prmt, String type, Timestamp start, Timestamp end){
        String s = settlementService.updateSta(prmt, type, start, end);
        return returnJson(s);
    }

    @ResponseBody
    @RequestMapping(value = "/updateSta1", produces = "text/json;charset=UTF-8")
    public String updateSta1(String prmt, String type){
        String s = settlementService.updateSta1(prmt, type);
        return  returnJson(s);
    }

    @ResponseBody
    @RequestMapping(value = "/updateAllSta", produces = "text/json;charset=UTF-8")
    public String updateAllSta(String id){
        String s = settlementService.updateAllSta(id);
        return  returnJson(s);
    }
}
