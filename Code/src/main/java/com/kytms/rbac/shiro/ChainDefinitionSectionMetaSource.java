package com.kytms.rbac.shiro;

import com.kytms.core.entity.Menu;
import com.kytms.core.utils.StringUtils;
import com.kytms.system.service.MenuService;
import org.apache.log4j.Logger;
import org.apache.shiro.config.Ini;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * <p>
 * 自定义配置
 *
 * @author 奇趣源码
 * @create 2017-11-23
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
    private final Logger log = Logger.getLogger(ChainDefinitionSectionMetaSource.class);//输出Log日志
    private MenuService<Menu> sysMenuService;
    private String filterChainDefinitions;

    /**
     * 默认premission字符串
     */
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";


    public Ini.Section getObject() throws BeansException {
        //获取所有Resource
        List<Menu> systemMenus = sysMenuService.selectMenuList(null);
        Ini ini = new Ini();
        //加载默认的url
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        //循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
        //里面的键就是链接URL,值就是存在什么条件才能访问该链接
        for (Iterator<Menu> it = systemMenus.iterator(); it.hasNext(); ) {
            Menu resource = it.next();
            //如果不为空值添加到section中
            if (StringUtils.isNotEmpty(resource.getUrl()) && StringUtils.isNotEmpty(resource.getId())) {
                section.put(resource.getUrl(), MessageFormat.format(PREMISSION_STRING, resource.getId()));
            }

        }
        return section;
    }

    /**
     * 通过filterChainDefinitions对默认的url过滤定义
     *
     * @param filterChainDefinitions 默认的url过滤定义
     */
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    public Class<?> getObjectType() {
        return this.getClass();
    }

    public boolean isSingleton() {
        return false;
    }

    @Resource
    public void setSysMenuService(MenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }
}
