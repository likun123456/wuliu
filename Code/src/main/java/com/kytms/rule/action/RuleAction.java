package com.kytms.rule.action;

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Rule;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.rule.core.RuleUtils;
import com.kytms.rule.service.RuleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2017/12/18.
 * 收发货方
 */
@Controller
@RequestMapping("/rule")
public class RuleAction extends BaseAction {
    private final Logger log = Logger.getLogger(RuleAction.class);//输出Log日志

    private RuleUtils ruleUtils = RuleUtils.getInstance();
    @Autowired
    private RuleService ruleService;
    @RequestMapping(value = "/getRuleList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getRuleList(CommModel commModel){
        JgGridListModel jgGridListModel =ruleService.getListByPage(commModel,null,null);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/saveRule", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveRule(Rule rule) {
        ReturnModel returnModel = getReturnModel();
        boolean result = returnModel.isResult();
        if(StringUtils.isEmpty(rule.getId())){
            rule.setStatus(0);
        }else {
            rule.setStatus(1);
        }
        if (result){
            ruleService.saveBean(rule);
        }
        return returnModel.toJsonString();
    }
    @RequestMapping(value = "/saveRuleData", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveRuleData(String id,String data) {
        ReturnModel returnModel = getReturnModel();
        Rule rule = (Rule) ruleService.selectBean(id);
        int status = rule.getStatus();
        if(status == 0){
            rule.setDescription(data);
        }else {
            returnModel.setResult(false);
            returnModel.setType("false");
            returnModel.setObj("规则必须下线");
            return returnModel.toJsonString();
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/updateRule", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String updateRule(String id,int state) {
        ReturnModel returnModel = getReturnModel();
        String[] split = id.split(",");
        for (String i:split) {
            Rule rule = (Rule) ruleService.selectBean(id);
            if(state == 1){
                try {
                    ruleUtils.ruleOilne(rule.getCode(),rule.getDescription());
                }catch (Exception ex){
                    ex.printStackTrace();
                    returnModel.setResult(false);
                    returnModel.setType("false");
                    returnModel.setObj("上线失败，请查看编译内容"+ex.getMessage());
                    return returnModel.toJsonString();
                }
            }else {
                ruleUtils.ruleUnOilne(rule.getCode());

            }
            rule.setStatus(state);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/runRule", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String runRule(String code,String obj) {
        System.out.println("2");
        Map jsonObject = JSONObject.parseObject(obj, HashMap.class);
        System.out.println(obj);
        ReturnModel returnModel = getReturnModel();
        Object o = RuleUtils.ruleCache.get(code);
        if(o == null){
            returnModel.setType("false");
            returnModel.setResult(false);
            returnModel.setObj("规则未上线");
            return returnModel.toJsonString();
        }

        try {
            Object o1 = ruleUtils.runRule(code, jsonObject);
            returnModel.setObj(o1);
        }catch (Exception e) {
            e.printStackTrace();
            returnModel.setResult(false);
            returnModel.setType("false");
            returnModel.setObj("规则运行失败"+e.getMessage());
            return returnModel.toJsonString();
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/runRuleForId", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String runRuleForId(String id,String obj) {
        Object parse = JSONObject.parse(obj);
        ReturnModel returnModel = getReturnModel();
        Rule rule = (Rule) ruleService.selectBean(id);
        if(rule.getStatus() == 0){
            returnModel.setType("false");
            returnModel.setResult(false);
            returnModel.setObj("规则未上线");
            return returnModel.toJsonString();
        }

        try {
            Object o1 = ruleUtils.runRule(rule.getCode(), parse);
            returnModel.setObj(o1);
        } catch (Exception e) {
            returnModel.setResult(false);
            returnModel.setType("false");
            e.printStackTrace();
            returnModel.setObj("规则运行失败"+e.getMessage());
            return returnModel.toJsonString();
        }

        return returnModel.toJsonString();
    }

    /**
     * 用于获取组织机构列表通用方法
     * @return
     */
    @RequestMapping(value = "/getRuleTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getRuleTree(){
        List<Rule> rows = ruleService.selectList(new CommModel());
        // List<JcRegistration> rows = jgGridListModel.getRows();
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Rule rule:rows ){
            TreeModel treeModel = new TreeModel();
            treeModel.setId(rule.getId());
            treeModel.setText(rule.getCode());
            treeModels.add(treeModel);
        }
        return returnJson(treeModels);
    }
}
