package com.kytms.settlement.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.SelltlmentDetails;
import com.kytms.settlement.dao.SettlementDetailsDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/5/28.
 */
@Repository(value = "SettlementDetailsDao")
public class SettlementDetailsDaoImpl extends BaseDaoImpl<SelltlmentDetails> implements SettlementDetailsDao<SelltlmentDetails>{
    private final Logger log = Logger.getLogger(SettlementDetailsDaoImpl.class);//输出Log日志
}
