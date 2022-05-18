package com.kytms.system.service.Impl;

import com.kytms.core.entity.Config;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.system.dao.SystemConfigDao;
import com.kytms.system.service.SystemConfigService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-20
 */
@Service(value = "SystemConfigService")
public class SystemConfigServiceImpl extends BaseServiceImpl<Config> implements SystemConfigService<Config> {
    private final Logger log = Logger.getLogger(SystemConfigServiceImpl.class);//输出Log日志
    private SystemConfigDao systemConfigDao;
    @Resource(name = "SystemConfigDao")
    public void setSystemConfigDao(SystemConfigDao systemConfigDao) {
        super.setBaseDao(systemConfigDao);
        this.systemConfigDao = systemConfigDao;
    }
}
