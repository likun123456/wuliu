package com.kytms.receivingparty.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.ReceivingParty;
import com.kytms.receivingparty.action.ReceivingPartyAction;
import com.kytms.receivingparty.dao.ReceivingPartyDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2017/12/18.
 * 收发货方DAO实现类
 */
@Repository(value = "ReceivingPartyDao")
public class ReceivingPartyDaoImpl extends BaseDaoImpl<ReceivingParty> implements ReceivingPartyDao<ReceivingParty> {
    private final Logger log = Logger.getLogger(ReceivingPartyDaoImpl.class);//输出Log日志
}
