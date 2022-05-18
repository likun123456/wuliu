package com.kytms.rbac.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.User;
import com.kytms.rbac.dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-18
 */
@Repository(value = "UserDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao<User> {
    private final Logger log = Logger.getLogger(UserDaoImpl.class);//输出Log日志
}
