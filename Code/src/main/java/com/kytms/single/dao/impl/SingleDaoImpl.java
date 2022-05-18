package com.kytms.single.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Single;
import com.kytms.single.action.SingleAction;
import com.kytms.single.dao.SingleDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "SingleDao")
public class SingleDaoImpl extends BaseDaoImpl<Single> implements SingleDao<Single> {
    private final Logger log = Logger.getLogger(SingleDaoImpl.class);//输出Log日志
}
