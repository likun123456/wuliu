package com.kytms.system.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.LoginInfo;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.system.service.LoginInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 按钮管理类
 *
 * @author
 * @create 2017-11-20
 */
@Controller
@RequestMapping("/loginInfo")
public class LoginInfoAction extends BaseAction{
    private final Logger log = Logger.getLogger(LoginInfoAction.class);//输出Log日志
    private LoginInfoService<LoginInfo> loginInfoService;
    @Resource
    public void setLoginInfoService(LoginInfoService<LoginInfo> loginInfoService) {
        this.loginInfoService = loginInfoService;
    }



    @RequestMapping(value = "/getLoginInfoList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getLoginInfoList(CommModel comm){
        JgGridListModel loginInfos = loginInfoService.getLoginInfoList(comm);
        return loginInfos.toJSONString();
    }
}
