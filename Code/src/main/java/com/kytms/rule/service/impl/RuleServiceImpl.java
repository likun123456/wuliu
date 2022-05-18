package com.kytms.rule.service.impl;

import com.kytms.core.entity.Rule;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.rule.dao.RuleDao;
import com.kytms.rule.service.RuleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2017/12/18.
 * 收发货方
 */
@Service(value = "RuleService")
public class RuleServiceImpl extends BaseServiceImpl<Rule> implements RuleService<Rule> {
    private final Logger log = Logger.getLogger(RuleServiceImpl.class);//输出Log日志
    private RuleDao<Rule> ruleRuleDao;
    @Resource(name = "RuleDao")
    public void setRuleRuleDao(RuleDao<Rule> ruleRuleDao) {
        super.setBaseDao(ruleRuleDao);
        this.ruleRuleDao = ruleRuleDao;
    }
}