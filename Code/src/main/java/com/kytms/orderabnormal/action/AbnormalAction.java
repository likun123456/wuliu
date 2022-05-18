package com.kytms.orderabnormal.action;

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Abnormal;
import com.kytms.core.entity.AbnormalDetail;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.ledgerdetails.dao.impl.LedgerDetailDaoImpl;
import com.kytms.orderabnormal.service.AbnormalService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 异常单
 *
 * @author 陈小龙
 * @create 2018-01-11
 */
@Controller
@RequestMapping("/abnormal")
public class AbnormalAction extends BaseAction {
    private final Logger log = Logger.getLogger(AbnormalAction.class);//输出Log日志
    private AbnormalService orderAbnormalService;
    @Resource
    public void setOrderAbnormalService(AbnormalService orderAbnormalService) {
        this.orderAbnormalService = orderAbnormalService;
    }



    @RequestMapping(value = "/getOrderAbnormalList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrderAbnormalList(CommModel commModel){
        Object o = orderAbnormalService.getOrderAbnormalList(commModel);
        return returnJsonForBean(o);
    }

    @RequestMapping(value = "/getOrderAbnormalDetailList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrderAbnormalDetailList(CommModel commModel){
        Abnormal abnormal = (Abnormal) orderAbnormalService.selectBean(commModel.getId());
        List<AbnormalDetail> abnormalDetails = abnormal.getAbnormalDetails();
        return returnJsonForBean(abnormalDetails);
    }

    @RequestMapping(value = "/saveAbnormal", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveAbnormal(String tableName,String obj){
        ReturnModel returnModel = getReturnModel();
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(obj)){
            returnModel.setObj("前台页面错误，传入表名，对象为空");
            returnModel.setResult(false);
        }
        boolean result = returnModel.isResult();
        if (result){
            Map<String,Class> entityAll = orderAbnormalService.getEntityAll();
            Object o = JSONObject.parseObject(obj, entityAll.get(tableName));
            Abnormal a = (Abnormal) o;
            a.setOrganization(SessionUtil.getOrg());
            Object s = orderAbnormalService.saveAbnormal(a);
            returnModel.setObj(s);
        }
        return returnModel.toJsonString();
    }
}
