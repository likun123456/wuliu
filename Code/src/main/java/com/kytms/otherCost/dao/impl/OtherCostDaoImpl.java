package com.kytms.otherCost.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.OtherCost;
import com.kytms.otherCost.action.OtherCostAction;
import com.kytms.otherCost.dao.OtherCostDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "OtherCostDao")
public class OtherCostDaoImpl extends BaseDaoImpl<OtherCost> implements OtherCostDao<OtherCost> {
    private final Logger log = Logger.getLogger(OtherCostDaoImpl.class);//输出Log日志
}
