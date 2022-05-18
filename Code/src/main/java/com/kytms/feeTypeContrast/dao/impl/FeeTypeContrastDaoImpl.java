package com.kytms.feeTypeContrast.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.FeeTypeContrast;
import com.kytms.feeTypeContrast.dao.FeeTypeContrastDao;
import org.springframework.stereotype.Repository;

/**
 * @Author:sundezeng
 * @Date:2019/3/15
 */
@Repository(value = "FeeTypeContrastDao")
public class FeeTypeContrastDaoImpl extends BaseDaoImpl<FeeTypeContrast> implements FeeTypeContrastDao<FeeTypeContrast> {
}
