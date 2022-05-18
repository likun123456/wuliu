package com.kytms.feeSeed.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.FeeSeed;
import com.kytms.feeSeed.action.FeeSeedAction;
import com.kytms.feeSeed.dao.FeeSeedDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @创建人: 陈小龙
 * @创建日期: 2018/10/29
 * @类描述:
 */
@Repository(value = "FeeSeedDao")
public class FeeSeedDaoImpl extends BaseDaoImpl<FeeSeed> implements FeeSeedDao<FeeSeed> {
    private final Logger log = Logger.getLogger(FeeSeedDaoImpl.class);//输出Log日志
}
