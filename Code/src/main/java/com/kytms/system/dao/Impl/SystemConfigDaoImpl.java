package com.kytms.system.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Config;
import com.kytms.system.dao.SystemConfigDao;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 按钮DAO
 *
 * @author
 * @create 2017-11-20
 */
@Repository(value = "SystemConfigDao")
public class SystemConfigDaoImpl extends BaseDaoImpl<Config> implements SystemConfigDao<Config> {
}
