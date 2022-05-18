package com.kytms.feetype.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.FeeType;
import com.kytms.feeSeed.service.impl.FeeSeedServiceImpl;
import com.kytms.feetype.dao.FeeTypeDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;


/**
 * 奇趣源码商城 www.qiqucode.com
 * 费用类型dao
 * @author 奇趣源码
 * @create 2018-01-04
 */
@Repository(value = "FeeTypeDao")
public class FeeTypeDaoImpl extends BaseDaoImpl<FeeType> implements FeeTypeDao<FeeType>  {
    private final Logger log = Logger.getLogger(FeeTypeDaoImpl.class);//输出Log日志
}
