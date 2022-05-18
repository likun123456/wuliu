package com.kytms.system.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Config;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.system.service.SystemConfigService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 按钮管理类
 *
 * @author
 * @create 2017-11-20
 */
@Controller
@RequestMapping("/systemconfig")
public class SystemConfigAction extends BaseAction{
    private final Logger log = Logger.getLogger(SystemConfigAction.class);//输出Log日志
    private SystemConfigService systemConfigService;
    @Resource
    public void setSystemConfigService(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }
    public static final String SYSTEM_CONFIG = "SystemConfig";





    @RequestMapping(value = "/saveSystemConfig", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveSystemConfig(HttpServletRequest request, Config config){
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj("保存成功……");
        if (StringUtils.isEmpty(config.getSystemName())){
            returnModel.addError("name","系统名称不能为空");
        }
        if (StringUtils.isEmpty(config.getCompanyName())){
            returnModel.addError("name","公司名称不能为空");
        }
        if(returnModel.isResult()){
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();
            servletContext.setAttribute(SYSTEM_CONFIG,config);
            systemConfigService.saveBean(config);
        }
        return returnModel.toJsonString();
    }
}
