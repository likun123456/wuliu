package com.kytms.system.service.Impl;

import com.kytms.core.entity.LoginInfo;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.system.dao.LoginInfoDao;
import com.kytms.system.service.LoginInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 实现类
 *
 * @author
 * @create 2017-11-18
 */
@Service(value = "LoginService")
public class LoginInfoServiceImpl extends BaseServiceImpl<LoginInfo> implements LoginInfoService<LoginInfo> {
    private final Logger log = Logger.getLogger(LoginInfoServiceImpl.class);//输出Log日志
    private LoginInfoDao loginInfoDao;
    @Resource(name = "LoginDao")
    public void setLoginInfoDao(LoginInfoDao loginInfoDao) {
        super.setBaseDao(loginInfoDao);
        this.loginInfoDao = loginInfoDao;
    }




    public JgGridListModel getLoginInfoList(CommModel comm) {
        JgGridListModel listByPage = super.getListByPage(comm, null, null);
        return listByPage;
    }
}
