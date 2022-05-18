package com.kytms.rule.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Rule;
import com.kytms.rule.dao.RuleDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2017/12/18.
 * 收发货方DAO实现类
 */
@Repository(value = "RuleDao")
public class RuleDaoImpl extends BaseDaoImpl<Rule> implements RuleDao<Rule> {
    private final Logger log = Logger.getLogger(RuleDaoImpl.class);//输出Log日志
}
