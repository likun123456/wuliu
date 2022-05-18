package com.kytms.otherFee.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.OtherFee;
import com.kytms.otherFee.action.OtherFeeAction;
import com.kytms.otherFee.dao.OtherFeeDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @Author:sundezeng
 * @Date:2018/11/6
 */
@Repository(value = "OtherFeeDao")
public class OtherFeeDaoImpl extends BaseDaoImpl<OtherFee> implements OtherFeeDao<OtherFee> {
    private final Logger log = Logger.getLogger(OtherFeeDaoImpl.class);//输出Log日志
}
