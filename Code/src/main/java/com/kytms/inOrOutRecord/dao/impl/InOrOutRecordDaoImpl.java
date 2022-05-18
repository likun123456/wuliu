package com.kytms.inOrOutRecord.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.InOrOutRecord;
import com.kytms.inOrOutRecord.action.InOrOutRecordAction;
import com.kytms.inOrOutRecord.dao.InOrOutRecordDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @Author:sundezeng
 * @Date:2018/10/22
 */
@Repository(value = "InOrOutRecordDao")
public class InOrOutRecordDaoImpl extends BaseDaoImpl<InOrOutRecord> implements InOrOutRecordDao<InOrOutRecord> {
    private final Logger log = Logger.getLogger(InOrOutRecordDaoImpl.class);//输出Log日志
}
