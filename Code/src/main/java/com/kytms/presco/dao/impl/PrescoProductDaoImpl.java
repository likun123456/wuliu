package com.kytms.presco.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.PrescoProduct;
import com.kytms.presco.dao.PrescoProductDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "PrescoProductDao")
public class PrescoProductDaoImpl extends BaseDaoImpl<PrescoProduct> implements PrescoProductDao<PrescoProduct> {
    private final Logger log = Logger.getLogger(PrescoProductDaoImpl.class);//输出Log日志
}
