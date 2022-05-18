package com.kytms.system.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.LoginInfo;
import com.kytms.system.dao.LoginInfoDao;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2017-11-18
 */
@Repository(value = "LoginDao")
public class LoginInfoDaoImpl extends BaseDaoImpl<LoginInfo> implements LoginInfoDao<LoginInfo> {
}
