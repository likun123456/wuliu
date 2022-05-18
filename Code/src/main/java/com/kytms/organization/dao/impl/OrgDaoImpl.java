package com.kytms.organization.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Organization;
import com.kytms.organization.action.OrgAction;
import com.kytms.organization.dao.OrgDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-23
 */
@Repository(value = "OrgDao")
public class OrgDaoImpl extends BaseDaoImpl<Organization> implements OrgDao<Organization> {
    private final Logger log = Logger.getLogger(OrgDaoImpl.class);//输出Log日志
}
