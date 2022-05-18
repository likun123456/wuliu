package com.kytms.presco.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Presco;
import com.kytms.presco.action.PrescoAction;
import com.kytms.presco.dao.PrescoDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "PrescoDao")
public class PrescoDaoImpl extends BaseDaoImpl<Presco> implements PrescoDao<Presco> {
    private final Logger log = Logger.getLogger(PrescoDaoImpl.class);//输出Log日志
}
