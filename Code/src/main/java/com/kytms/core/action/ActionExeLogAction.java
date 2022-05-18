package com.kytms.core.action;/**
 * Created by nidaye on 2018/11/9.
 */

import com.kytms.core.entity.ActionLog;
import com.kytms.core.service.BaseService;
import com.kytms.core.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 所有请求日志
 *
 * @author
 * @create 2018-11-09
 */
public class ActionExeLogAction implements HandlerInterceptor {
    private BaseService baseService;
    private Logger log = Logger.getLogger(ActionExeLogAction.class);//输出Log日志


    @Resource(name = "UserService")
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    // before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    // after the handler is executed
    public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        ActionLog actionLog = new ActionLog();
        if (handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod) handler;
            actionLog.setMethodName( h.getMethod().getName());
            actionLog.setUrl( request.getRequestURI());
            actionLog.setActionName(h.getBean().getClass().getName());
        }
        actionLog.setTime(DateUtils.getTimestamp());
        actionLog.setExeTime(time);
        baseService.saveBean(actionLog);
    }

    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }
}
