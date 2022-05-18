package com.kytms.settlement.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Settlement;
import com.kytms.settlement.action.SettlementAction;
import com.kytms.settlement.dao.SettlementDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/5/24.
 */
@Repository(value = "SettlementDao")
public class SettlementDaoImpl extends BaseDaoImpl<Settlement> implements SettlementDao<Settlement> {
    private final Logger log = Logger.getLogger(SettlementDaoImpl.class);//输出Log日志
}
