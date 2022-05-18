package com.kytms.rbac.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Role;
import com.kytms.rbac.action.UserAction;
import com.kytms.rbac.dao.RoleDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建用户 Administrator
 * 创建日期2017/11/20 0020.
 */
@Repository(value = "RoleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao<Role> {
    private final Logger log = Logger.getLogger(RoleDaoImpl.class);//输出Log日志
}
