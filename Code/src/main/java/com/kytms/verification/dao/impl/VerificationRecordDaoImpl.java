package com.kytms.verification.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.VerificationRecord;
import com.kytms.verification.dao.VerificationDao;
import com.kytms.verification.dao.VerificationRecordDao;
import org.springframework.stereotype.Repository;

/**
 * @Author:sundezeng
 * @Date:2018/11/3
 */
@Repository(value = "VerificationRecordDao")
public class VerificationRecordDaoImpl extends BaseDaoImpl<VerificationRecord> implements VerificationRecordDao<VerificationRecord> {
}
