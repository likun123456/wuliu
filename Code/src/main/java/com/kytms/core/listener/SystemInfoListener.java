package com.kytms.core.listener;

import com.kytms.core.constants.Entity;
import com.kytms.core.entity.Config;
import com.kytms.core.entity.Rule;
import com.kytms.core.model.CommModel;
import com.kytms.core.utils.SpringUtils;
import com.kytms.core.utils.SystemConfigUtils;
import com.kytms.rule.core.RuleUtils;
import com.kytms.rule.service.RuleService;
import com.kytms.system.service.SystemConfigService;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;


/**
 * 奇趣源码商城 www.qiqucode.com
 * 系统启动监听类
 * @author 奇趣源码
 * @create 2017-11-17
 */
public class SystemInfoListener implements ServletContextListener {

    private final Logger log = Logger.getLogger(SystemInfoListener.class);//输出Log日志

    public void contextInitialized(ServletContextEvent sc) {
        sc.getServletContext().log("系统开始初始化");
        long starTime = System.currentTimeMillis();
        initSystem(sc);
        sc.getServletContext().log("系统初始化完毕 总共用时：" + String.valueOf(System.currentTimeMillis() - starTime) + "s");
    }

    /**
     * 初始化系统函数
     *
     * @param sc
     */
    private void initSystem(ServletContextEvent sc)  {
        ServletContext servletContext = sc.getServletContext();
        sc.getServletContext().log("获取系统配置……");
        SystemConfigService bean = SpringUtils.getBean(SystemConfigService.class);
        Config config = (Config) bean.selectBean(Entity.TREE_ROOT);
        servletContext.setAttribute(SystemConfigUtils.SYSTEM_CONFIG, config);
        //规则初始化
        sc.getServletContext().log("初始化规则");
        RuleService ruleService = SpringUtils.getBean(RuleService.class);
        List<Rule> list = ruleService.selectList(new CommModel(), " and status =1", null);
        RuleUtils instance = RuleUtils.getInstance();
        try {
            for (Rule r:list) {
                instance.ruleOilne(r.getCode(),r.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sc) {

    }
}
