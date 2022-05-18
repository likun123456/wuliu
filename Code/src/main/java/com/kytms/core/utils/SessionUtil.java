package com.kytms.core.utils;

import com.kytms.core.entity.Organization;
import com.kytms.core.entity.User;
import com.kytms.rbac.shiro.ShiroDbRealm;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * 奇趣源码商城 www.qiqucode.com
 * <p>
 * Session工具类
 *
 * @author 奇趣源码
 * @create 2017-11-23
 */
public abstract class SessionUtil {
    private static Logger logger = Logger.getLogger(SessionUtil.class);
    /**
     * 获取当前用户
     *
     * @return
     */
    public static User getUser() {
        return (User) getSession().getAttribute(ShiroDbRealm.SESSIOIN_USER_KEY);
    }

    /**
     * 获取Seesion
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取等路人名称
     *
     * @return
     */
    public static String getUserName() {
        return getUser().getName();
    }

    /**
     * 获取当前登陆组织机构
     *
     * @return
     */
    public static Organization getOrg() {
        return (Organization) getSession().getAttribute(ShiroDbRealm.SESSION_ORG_ID);
    }

    /**
     * 获取组织机构ID
     *
     * @return
     */
    public static String getOrgId() {
        Organization organization = (Organization) getSession().getAttribute(ShiroDbRealm.SESSION_ORG_ID);
        return organization.getId();
    }

    /**
     * 获取组织机构名称
     *
     * @return
     */
    public static String getOrgName() {
        Organization organization = (Organization) getSession().getAttribute(ShiroDbRealm.SESSION_ORG_ID);
        return organization.getName();
    }
}
