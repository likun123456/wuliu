package com.kytms.ledgerdetails.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.LedgerDetail;
import com.kytms.inOrOutRecord.service.impl.InOrOutRecordServiceImpl;
import com.kytms.ledgerdetails.dao.LedgerDetailDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 费用明细设置DAO实现类
 *
 * @author 奇趣源码
 * @create 2018-01-18
 */
@Repository(value = "LedgerDetailDao")
public class LedgerDetailDaoImpl extends BaseDaoImpl<LedgerDetail> implements LedgerDetailDao<LedgerDetail> {
    private final Logger log = Logger.getLogger(LedgerDetailDaoImpl.class);//输出Log日志
}

