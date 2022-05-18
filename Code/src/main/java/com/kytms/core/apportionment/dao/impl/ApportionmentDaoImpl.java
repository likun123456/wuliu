package com.kytms.core.apportionment.dao.impl;

import com.kytms.core.apportionment.dao.ApportionmentDao;
import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Apportionment;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @Author:sundezeng
 * @Date:2019/1/2
 */
@Repository(value = "ApportionmentDao")
public class ApportionmentDaoImpl extends BaseDaoImpl<Apportionment> implements ApportionmentDao<Apportionment> {
    private Logger log = Logger.getLogger(ApportionmentDaoImpl.class);//输出Log日志
}
