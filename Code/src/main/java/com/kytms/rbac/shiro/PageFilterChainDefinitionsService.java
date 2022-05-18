package com.kytms.rbac.shiro;

import com.kytms.core.entity.User;
import com.kytms.rbac.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * 奇趣源码商城 www.qiqucode.com
 * <p>
 * 页面过滤器
 *
 * @author 奇趣源码
 * @create 2017-11-23
 */
public class PageFilterChainDefinitionsService extends AuthorizationFilter {
    private final Logger log = Logger.getLogger(PageFilterChainDefinitionsService.class);//输出Log日志
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        User sys_user = (User) subject.getSession().getAttribute(ShiroDbRealm.SESSIOIN_USER_KEY);
        if (sys_user == null) {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.sendRedirect("/jsp/login/index.jsp");
            PrintWriter writer = response.getWriter();
            writer.println("会话超时，请重新登陆~~~~");
            writer.close();

            return false;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String reqUrl = httpRequest.getRequestURI().toString();
        String s = reqUrl.replaceFirst("//", "/");
        boolean b = subject.isPermitted(s);
        //boolean permitted = subject.isPermitted(reqUrl);

        //System.out.println(permitted);
        return true;
    }
}
