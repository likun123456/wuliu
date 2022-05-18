package com.kytms.rbac.shiro;

import com.kytms.core.entity.Organization;
import com.kytms.core.entity.Role;
import com.kytms.core.entity.User;
import com.kytms.organization.service.OrgService;
import com.kytms.rbac.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-23
 */
public class ShiroDbRealm extends AuthorizingRealm {
    private final Logger log = Logger.getLogger(ShiroDbRealm.class);//输出Log日志
    private UserService<User> userService;
    private OrgService<Organization> orgService;
    public static final String SESSIOIN_USER_KEY = "userInfo";
    public static final String SESSION_ROLE_KEY="roleInfo";
    public static final String SESSION_PAGE_KEY="pageInfo";
    public static final String SESSION_ORG_ID = "orgId";

    /**
     * 授权查询回调函数，进行鉴权但缓存中无用户的授权信息时调用，负责在应用程序中决定用户访问控制的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录时输入的帐号
        //String account = (String) principalCollection.fromRealm(getName()).iterator().next();
        // 从session中获取用户
        // 注意：该 session 并不是 客户端 session，而是 Shiro 的session
        User sys_user = (User) SecurityUtils.getSubject().getSession().getAttribute(ShiroDbRealm.SESSIOIN_USER_KEY);
        List<String> roles  = (List<String>) SecurityUtils.getSubject().getSession().getAttribute(ShiroDbRealm.SESSION_ROLE_KEY);
        List<String> page = (List<String>) SecurityUtils.getSubject().getSession().getAttribute(ShiroDbRealm.SESSION_PAGE_KEY);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 保存角色
        info.addRoles(roles);
        // 保存权限
        info.addStringPermissions(page);
        return info;
    }

    /**
     * 认证回调函数，登录信息和用户信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        // 把 token 转成 user
        User userInfo = tokenToUser((UsernamePasswordToken)authToken);
        // 验证用户是否可以登录
        User user = userService.userLogin(userInfo.getCode(), userInfo.getPassword());


        if (user == null)
            return null;// 找不到数据
        // 设置 session
        Session session = SecurityUtils.getSubject().getSession();
        List<Role> sys_roles = user.getRoles();
        List<String> page = new ArrayList<String>();
        List<String> roles= new ArrayList<String>();
        for (Role role : sys_roles) {
            List<String> auths = role.getAuthorityUrl();
            roles.add(role.getId());
            auths.removeAll(page);// 权限去重
            page.addAll(auths);
        }
        session.setAttribute(ShiroDbRealm.SESSIOIN_USER_KEY, user);
        session.setAttribute(ShiroDbRealm.SESSION_ROLE_KEY, roles);
        session.setAttribute(ShiroDbRealm.SESSION_PAGE_KEY, page);
        //开始处理登陆组织机构---------------------------------------------------------------------
        //查找用户生效的组织机构
        List<Organization> organizations = orgService.selectUserOrgs(user);
        if (organizations == null || organizations.size() ==0 ){
            throw new AuthenticationException("用户没有绑定组织机构");
        }
        Organization organization = organizations.get(0);
        session.setAttribute(SESSION_ORG_ID, organization);
        // 当前 Realm 的 name
        String realmName = this.getName();
        // 登录的主要信息：可以是一个实体类对象，但实体类的对象一定是根据 token 的 username 查询得到的
        // Object principal = user.getAccount();
        Object principal = authToken.getPrincipal();
        ByteSource credentialsSalt = ByteSource.Util.bytes("test");
        return new SimpleAuthenticationInfo(principal, userInfo.getPassword(),credentialsSalt, realmName);
    }
    private User tokenToUser(UsernamePasswordToken authToken) {
        User user = new User();
        user.setCode(authToken.getUsername());
        user.setPassword(String.valueOf(authToken.getPassword()));
        return user;
    }
    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Resource
    public void setOrgService(OrgService<Organization> orgService) {
        this.orgService = orgService;
    }
}
