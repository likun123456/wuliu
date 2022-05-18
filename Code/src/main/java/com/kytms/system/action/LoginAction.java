package com.kytms.system.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kytms.core.action.BaseAction;
import com.kytms.core.constants.Entity;
import com.kytms.core.entity.LoginInfo;
import com.kytms.core.entity.Menu;
import com.kytms.core.entity.Organization;
import com.kytms.core.entity.User;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.DateUtils;
import com.kytms.core.utils.MD5Util;
import com.kytms.core.utils.SessionUtil;
import com.kytms.organization.service.OrgService;
import com.kytms.rbac.service.UserService;
import com.kytms.rbac.shiro.ShiroDbRealm;
import com.kytms.system.service.ButtonService;
import com.kytms.system.service.DataBookService;
import com.kytms.system.service.LoginInfoService;
import com.kytms.system.service.MenuService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 登陆
 *
 * @author 奇趣源码
 * @create 2017-11-23
 */
@Controller
@RequestMapping("/login")
public class LoginAction extends BaseAction{
    private final Logger log = Logger.getLogger(LoginAction.class);//输出Log日志
    private MenuService sysMneuService;
    private DataBookService dataBookService;
    private ButtonService buttonManageSerive;
    private OrgService<Organization> organizationService;
    private UserService<User> userService;
    private LoginInfoService<LoginInfo> loginInfoService;
    @Resource
    public void setSysMneuService(MenuService sysMneuService) {
        this.sysMneuService = sysMneuService;
    }
    @Resource
    public void setButtonManageSerive(ButtonService buttonManageSerive) {
        this.buttonManageSerive = buttonManageSerive;
    }
    @Resource
    public void setOrganizationService(OrgService organizationService) {
        this.organizationService = organizationService;
    }
    @Resource
    public void setUserService(UserService<User> userService) {
        this.userService = userService;
    }
    @Resource
    public void setLoginInfoService(LoginInfoService<LoginInfo> loginInfoService) {
        this.loginInfoService = loginInfoService;
    }
    @Resource
    public void setDataBookService(DataBookService dataBookService) {
        this.dataBookService = dataBookService;
    }






    /**
     * 获取用户当前平台
     * @return
     */
    @RequestMapping(value = "/getUserPlan", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getUserPlan(){
        User sessionUser = SessionUtil.getUser();
        if (sessionUser == null){
            return  null;
        }
        List<Organization> organizations =  organizationService.selectUserOrgs(sessionUser);
//        List<Organization> organizations = sessionUser.getOrganizations();
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Organization org:organizations ) {
            if (org.getStatus() == Entity.ACTIVE){
            TreeModel treeModel = new TreeModel();
            treeModel.setId(org.getId());
            treeModel.setText(org.getName());
            if (org.getStatus()!=0){
                treeModels.add(treeModel);
            }
            }
        }
        String s = returnJsonFilter(treeModels);
        return s ;
    }

    /**初始化客户数据*/
    @RequestMapping(value = "/initData", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String initData(){
        CommModel commModel = new CommModel();
        //初始化菜单
        User user = SessionUtil.getUser();
        List<Menu> systemMenus = sysMneuService.selectUserMenuList();//
//        //初始化按钮
        Map<String,String> buttonJson = buttonManageSerive.selectUserButtonList(user.getId());
        //初始化数据字典
        Map<String,Map<String,String>> databookJson = dataBookService.getDataBookJson();
        Map<String,Object> jsonMap=new HashMap<String, Object>();
        jsonMap.put("authorizeMenu",systemMenus);
        jsonMap.put("dataItem", databookJson);
        jsonMap.put("authorizeButton",buttonJson);
        return JSON.toJSONString(jsonMap, SerializerFeature.DisableCircularReferenceDetect);
    }

    @RequestMapping(value = "/login", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String login(HttpServletRequest request,User userInfo) {
        userInfo.setPassword(MD5Util.MD5(userInfo.getPassword()));//2次加密
        if (SecurityUtils.getSubject().getSession() != null)
            SecurityUtils.getSubject().logout();
        String info = loginUser(userInfo);
        if (!"SUCC".equals(info)) {
            return returnFailed(stringToJson(info));
        }
        //model.addAttribute("account", userInfo.getAccount());
        // 这里使用重定向，是为了避免浏览器一直地址栏定位在登录方法，这样每次刷新页面就等于是重新登录了一次
        //保存登陆信息
        LoginInfo loginInfo = new LoginInfo();
        User user = SessionUtil.getUser();
        loginInfo.setCode(user.getCode());
        loginInfo.setName(user.getName());
        loginInfo.setLoginTime(DateUtils.getTimestamp());
        loginInfo.setIp(request.getRemoteAddr());
        loginInfoService.saveBean(loginInfo);
        return returnSuccess(null);
    }

    public String returnSuccess(String str){
        StringBuilder sb = new StringBuilder(128);
        sb.append("{\"type\":true,\"obj\":");
        if (str != null){
            sb.append(str);
        }else{
            sb.append("\"成功\"");
        }
        sb.append("}");
        return sb.toString();
    }

    public String stringToJson(String sta){
        return "\""+sta+"\"";
    }

    public String returnFailed(String str){
        StringBuilder sb = new StringBuilder(128);
        sb.append("{\"type\":false,\"obj\":");
        if (str != null){
            sb.append(str);
        }else{
            sb.append("\"失败\"");
        }
        sb.append("}");
        return sb.toString();
    }

    @RequestMapping(value = "/logout", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj("登出成功");
        return returnJson(returnModel);
    }

    @RequestMapping(value = "/getOrg", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrg() {
        Organization org = (Organization) SessionUtil.getSession().getAttribute(ShiroDbRealm.SESSION_ORG_ID);
        return returnJsonFilter(org);
    }

    @RequestMapping(value = "/getOrgList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrgList() {
        String id = SessionUtil.getUser().getId();
        User user = userService.selectBean(id);
        List<Organization> organizations = user.getOrganizations();
        List<TreeModel> treeModelList = new ArrayList<TreeModel>();
        for (Organization bean : organizations) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(bean.getId());
            treeModel.setText(bean.getName());
            treeModel.setComplete(false);
            treeModel.setHasChildren(false);
            treeModel.setIsexpand(false);
            treeModel.setShowcheck(true);
            treeModelList.add(treeModel);
        }
        return  returnJson(treeModelList);
    }

    @RequestMapping(value = "/setOrg", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String setOrg(CommModel commModel) {
        Organization org = organizationService.selectBean(commModel.getId());
        if (org.getStatus() == 0){
            ReturnModel returnModel = getReturnModel();
            returnModel.addError("","");
            returnModel.setObj("平台已失效,不可切换");
            return returnJson(returnModel);
        }
        SessionUtil.getSession().setAttribute(ShiroDbRealm.SESSION_ORG_ID,org);
        return returnJson(getReturnModel());
    }

    private String loginUser(User user) {
        if (isRelogin(user)) return "SUCC";// 如果已经登录，则无需再登录
        return shiroLogin(user);
    }

    private String shiroLogin(User user) {
        // 组装 token，包括用户名、密码、角色、权限等等
        UsernamePasswordToken token = new UsernamePasswordToken(user.getCode(), user.getPassword().toCharArray(), null);
        token.setRememberMe(true);
        // shiro 验证登录
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException ex){
            return "帐号或密码错误";
        } catch (IncorrectCredentialsException ex){
            return "帐号不存在或者密码错误";
        } catch (AuthenticationException ex) {
            return ex.getMessage();
        } catch (Exception e) {
            return "内部错误，请重新尝试";
        }
        return "SUCC";
    }

    private boolean isRelogin(User user) {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated();// true：参数未改变，无需重新登录，默认为已经登录成功；false：需重新登录
    }


}
