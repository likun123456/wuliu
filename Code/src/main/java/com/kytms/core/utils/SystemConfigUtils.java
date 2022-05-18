package com.kytms.core.utils;

import com.kytms.core.entity.Config;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 系统配置获取工具;
 *
 * @author 奇趣源码
 * @create 2018-01-08
 */
public abstract class SystemConfigUtils {
    public static final String SYSTEM_CONFIG = "SystemConfig";

    /**
     * 获取系统配置工具
     * @return
     */
    public static Config getSystemConfig(){
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        Config config = (Config) servletContext.getAttribute(SYSTEM_CONFIG);
        return config;
    }

    /**
     * 获取系统名称
     * @return
     */
    public static String getSystemName(){
        return getSystemConfig().getSystemName();
    }

    /**
     * 获取公司名称
     * @return
     */
    public static String getCompanyNmae(){
        return getSystemConfig().getCompanyName();
    }

    /**
     * 获取系统金额省略位数
     * @return
     */
    public static int getMoneyRound(){
        return getSystemConfig().getMoneyRoundNumber();
    }
    /**
     * 获取运量金额省略位数
     * @return
     */
    public static int getTrafficRound(){
        return getSystemConfig().getTrafficRoundNumber();
    }
}
