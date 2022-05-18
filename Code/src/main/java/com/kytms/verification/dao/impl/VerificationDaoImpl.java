package com.kytms.verification.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.VerificationZb;
import com.kytms.verification.dao.VerificationDao;
import org.springframework.stereotype.Repository;

/**
 * @Author:sundezeng
 * @Date:2018/11/2
 */
@Repository(value = "VerificationDao")
public class VerificationDaoImpl extends BaseDaoImpl<VerificationZb> implements VerificationDao<VerificationZb> {
}
