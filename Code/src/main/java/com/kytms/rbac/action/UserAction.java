package com.kytms.rbac.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.User;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.MD5Util;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.rbac.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 用户控制层
 *
 * @author 奇趣源码
 * @create 2017-11-18
 */
@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction{
    private final Logger log = Logger.getLogger(UserAction.class);//输出Log日志
    private UserService<User> userService;
    @Resource
    public void setUserService(UserService<User> userService) {
        this.userService = userService;
    }




    @RequestMapping(value = "/upPassword", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  upPassword(String str){
        User user = SessionUtil.getUser();
        User user2 = userService.selectBean(user.getId());
        user2.setPassword(MD5Util.MD5(str));
        userService.saveBean(user2);
        return returnJson(getReturnModel());
    }

    @RequestMapping(value = "/saveUserOrg", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  saveUserOrg(String id,String ids){
        userService.saveUserOrg(id,ids);
        return returnJson(getReturnModel());
    }

    @RequestMapping(value = "/getOrgTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找数据库方法
    public String  getOrgTree(String id){
        List<TreeModel> treeModels = userService.getOrgTree(id);
        return returnJson(treeModels);
    }

    @RequestMapping(value = "/saveRole", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找数据库方法
    public String  saveRole(String id,String ids){
        userService.saveRole(id,ids);
        return returnJson(getReturnModel());
    }

    @RequestMapping(value = "/getRoleTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getRoleTree(String id){
        List<TreeModel> user = userService.getRoleTree(id);
        return returnJson(user);
    }

    @RequestMapping(value = "/updatePassWord", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String updatePassWord(String tableName,String id){
        ReturnModel returnModel = getReturnModel();
        Object o = userService.updatePassWord(tableName,id.split(SPLIT_COMMA));
        returnModel.setObj("密码修改成功");
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/selectUser", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String selectUser(String id){
        User user = userService.selectBean(id);
        return returnJson(user);
    }

    @RequestMapping(value = "/saveUser", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveUser(User user){
        //JetChain_123456_xxzx 默认密码 加密后：9FA169E6E03607DADDE25D1C3205EFC6
//        String pwd ="JetChain_123456_xxzx";
//        String s = MD5Util.MD5(pwd);
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj("保存成功……");
        returnModel.codeUniqueAndNull(user,userService);
        if (StringUtils.isEmpty(user.getName())){
            returnModel.addError("name","用户名不能为空");
        }
        if(returnModel.isResult()){
            if (StringUtils.isNotEmpty(user.getId())){
                User o = userService.selectBean(user.getId());
                user.setRoles(o.getRoles());
                user.setOrganizations(o.getOrganizations());
                user.setPassword(o.getPassword());
            }else {
                user.setPassword("9FA169E6E03607DADDE25D1C3205EFC6");
            }

            userService.saveBean(user);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getList(CommModel commModel){
        JgGridListModel jgGridListModel =userService.getUserList(commModel);
        return returnJson(jgGridListModel);
    }

    @RequestMapping(value = "/initUserData", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  initUserData(CommModel commModel){
//        JSONObject object = new JSONObject();
//        object.put("authorizeMenu",menus);
//        object.toJSONString();
        return
                "{\n" +
                "  \n" +
                "    \"authorizeMenu\": [\n" +
                "        {\n" +
                "            \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "            \"code\": \"BaseData\",\n" +
                "            \"name\": \"基础数据\",\n" +
                "            \"img\": \"fa fa-folder-open-o\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/1\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"0\",\n" +
                "                \"code\": \"0\",\n" +
                "                \"url\": \"2\"\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045cd2e5f8015cd2ea16cd0000\",\n" +
                "            \"code\": \"Sys_Organization\",\n" +
                "            \"name\": \"组织机构\",\n" +
                "            \"img\": \"fa fa-sitemap\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"jsp/BaseData/Organization/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                \"code\": \"BaseData\",\n" +
                "                \"name\": \"基础数据\",\n" +
                "                \"img\": \"fa fa-folder-open-o\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 0\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cdd0b15015cdd4ba47b0006\",\n" +
                "            \"code\": \"ProductInfo\",\n" +
                "            \"name\": \"货品信息\",\n" +
                "            \"img\": \"fa fa-shopping-basket\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/2\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                \"code\": \"BaseData\",\n" +
                "                \"name\": \"基础数据\",\n" +
                "                \"img\": \"fa fa-folder-open-o\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 0\n" +
                "            },\n" +
                "            \"orderBy\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cdd0b15015cdd4cbf890007\",\n" +
                "            \"code\": \"ProductUnit\",\n" +
                "            \"name\": \"包装类型\",\n" +
                "            \"img\": \"fa fa-cube\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/ProductInfo/ProductUnit/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e95cdd0b15015cdd4ba47b0006\",\n" +
                "                \"code\": \"ProductInfo\",\n" +
                "                \"name\": \"货品信息\",\n" +
                "                \"img\": \"fa fa-shopping-basket\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/2\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                    \"code\": \"BaseData\",\n" +
                "                    \"name\": \"基础数据\",\n" +
                "                    \"img\": \"fa fa-folder-open-o\",\n" +
                "                    \"target\": \"expand\",\n" +
                "                    \"url\": \"/1\",\n" +
                "                    \"sys_systemMenu\": {\n" +
                "                        \"id\": \"0\",\n" +
                "                        \"code\": \"0\",\n" +
                "                        \"url\": \"2\"\n" +
                "                    },\n" +
                "                    \"orderBy\": 0\n" +
                "                },\n" +
                "                \"orderBy\": 2\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cdd0b15015cdd4de83e0008\",\n" +
                "            \"code\": \"ProductType\",\n" +
                "            \"name\": \"货品类型\",\n" +
                "            \"img\": \"fa fa-sliders\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/ProductInfo/ProductType/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e95cdd0b15015cdd4ba47b0006\",\n" +
                "                \"code\": \"ProductInfo\",\n" +
                "                \"name\": \"货品信息\",\n" +
                "                \"img\": \"fa fa-shopping-basket\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/2\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                    \"code\": \"BaseData\",\n" +
                "                    \"name\": \"基础数据\",\n" +
                "                    \"img\": \"fa fa-folder-open-o\",\n" +
                "                    \"target\": \"expand\",\n" +
                "                    \"url\": \"/1\",\n" +
                "                    \"sys_systemMenu\": {\n" +
                "                        \"id\": \"0\",\n" +
                "                        \"code\": \"0\",\n" +
                "                        \"url\": \"2\"\n" +
                "                    },\n" +
                "                    \"orderBy\": 0\n" +
                "                },\n" +
                "                \"orderBy\": 2\n" +
                "            },\n" +
                "            \"orderBy\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cdd0b15015cdd4ef2d50009\",\n" +
                "            \"code\": \"Product\",\n" +
                "            \"name\": \"货品管理\",\n" +
                "            \"img\": \"fa fa-cubes\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/ProductInfo/Product/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e95cdd0b15015cdd4ba47b0006\",\n" +
                "                \"code\": \"ProductInfo\",\n" +
                "                \"name\": \"货品信息\",\n" +
                "                \"img\": \"fa fa-shopping-basket\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/2\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                    \"code\": \"BaseData\",\n" +
                "                    \"name\": \"基础数据\",\n" +
                "                    \"img\": \"fa fa-folder-open-o\",\n" +
                "                    \"target\": \"expand\",\n" +
                "                    \"url\": \"/1\",\n" +
                "                    \"sys_systemMenu\": {\n" +
                "                        \"id\": \"0\",\n" +
                "                        \"code\": \"0\",\n" +
                "                        \"url\": \"2\"\n" +
                "                    },\n" +
                "                    \"orderBy\": 0\n" +
                "                },\n" +
                "                \"orderBy\": 2\n" +
                "            },\n" +
                "            \"orderBy\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e55c8cfc3f015c8d3d92ba0001\",\n" +
                "            \"code\": \"GeologicalLocation\",\n" +
                "            \"name\": \"地理位置\",\n" +
                "            \"img\": \"fa fa-university\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/2\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                \"code\": \"BaseData\",\n" +
                "                \"name\": \"基础数据\",\n" +
                "                \"img\": \"fa fa-folder-open-o\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 0\n" +
                "            },\n" +
                "            \"orderBy\": 4\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045cafd7b1015cafdc85270000\",\n" +
                "            \"code\": \"Districts\",\n" +
                "            \"name\": \"行政区域\",\n" +
                "            \"img\": \"fa fa-cubes\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Location/District/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e55c8cfc3f015c8d3d92ba0001\",\n" +
                "                \"code\": \"GeologicalLocation\",\n" +
                "                \"name\": \"地理位置\",\n" +
                "                \"img\": \"fa fa-university\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/2\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                    \"code\": \"BaseData\",\n" +
                "                    \"name\": \"基础数据\",\n" +
                "                    \"img\": \"fa fa-folder-open-o\",\n" +
                "                    \"target\": \"expand\",\n" +
                "                    \"url\": \"/1\",\n" +
                "                    \"sys_systemMenu\": {\n" +
                "                        \"id\": \"0\",\n" +
                "                        \"code\": \"0\",\n" +
                "                        \"url\": \"2\"\n" +
                "                    },\n" +
                "                    \"orderBy\": 0\n" +
                "                },\n" +
                "                \"orderBy\": 4\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045ce8b5a0015ce8b85ca30000\",\n" +
                "            \"code\": \"Transport\",\n" +
                "            \"name\": \"运输管理\",\n" +
                "            \"img\": \"fa fa-truck\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                \"code\": \"BaseData\",\n" +
                "                \"name\": \"基础数据\",\n" +
                "                \"img\": \"fa fa-folder-open-o\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 0\n" +
                "            },\n" +
                "            \"orderBy\": 6\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045ce8b5a0015ce8b9b7450001\",\n" +
                "            \"code\": \"driven\",\n" +
                "            \"name\": \"司机管理\",\n" +
                "            \"img\": \"fa fa-user\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Transport/Driven/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045ce8b5a0015ce8b85ca30000\",\n" +
                "                \"code\": \"Transport\",\n" +
                "                \"name\": \"运输管理\",\n" +
                "                \"img\": \"fa fa-truck\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                    \"code\": \"BaseData\",\n" +
                "                    \"name\": \"基础数据\",\n" +
                "                    \"img\": \"fa fa-folder-open-o\",\n" +
                "                    \"target\": \"expand\",\n" +
                "                    \"url\": \"/1\",\n" +
                "                    \"sys_systemMenu\": {\n" +
                "                        \"id\": \"0\",\n" +
                "                        \"code\": \"0\",\n" +
                "                        \"url\": \"2\"\n" +
                "                    },\n" +
                "                    \"orderBy\": 0\n" +
                "                },\n" +
                "                \"orderBy\": 6\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045ce8b5a0015ce8bb9b160002\",\n" +
                "            \"code\": \"Vehicle\",\n" +
                "            \"name\": \"车辆管理\",\n" +
                "            \"img\": \"fa fa-car\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Transport/Vehicle/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045ce8b5a0015ce8b85ca30000\",\n" +
                "                \"code\": \"Transport\",\n" +
                "                \"name\": \"运输管理\",\n" +
                "                \"img\": \"fa fa-truck\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                    \"code\": \"BaseData\",\n" +
                "                    \"name\": \"基础数据\",\n" +
                "                    \"img\": \"fa fa-folder-open-o\",\n" +
                "                    \"target\": \"expand\",\n" +
                "                    \"url\": \"/1\",\n" +
                "                    \"sys_systemMenu\": {\n" +
                "                        \"id\": \"0\",\n" +
                "                        \"code\": \"0\",\n" +
                "                        \"url\": \"2\"\n" +
                "                    },\n" +
                "                    \"orderBy\": 0\n" +
                "                },\n" +
                "                \"orderBy\": 6\n" +
                "            },\n" +
                "            \"orderBy\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95d68a65b015d6932b95b0000\",\n" +
                "            \"code\": \"Carrier\",\n" +
                "            \"name\": \"承运商管理\",\n" +
                "            \"img\": \"fa fa-th\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Carrier/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                \"code\": \"BaseData\",\n" +
                "                \"name\": \"基础数据\",\n" +
                "                \"img\": \"fa fa-folder-open-o\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 0\n" +
                "            },\n" +
                "            \"orderBy\": 11\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045ce327ee015ce35e493a0002\",\n" +
                "            \"code\": \"Receivers\",\n" +
                "            \"name\": \"收发货方管理\",\n" +
                "            \"img\": \"fa fa-circle-o-notch\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Receivers/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                \"code\": \"BaseData\",\n" +
                "                \"name\": \"基础数据\",\n" +
                "                \"img\": \"fa fa-folder-open-o\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 0\n" +
                "            },\n" +
                "            \"orderBy\": 12\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cf44818015cf44b3e7c0000\",\n" +
                "            \"code\": \"ProductService\",\n" +
                "            \"name\": \"服务产品\",\n" +
                "            \"img\": \"fa fa-thumbs-o-up\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/ProductServer/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                \"code\": \"BaseData\",\n" +
                "                \"name\": \"基础数据\",\n" +
                "                \"img\": \"fa fa-folder-open-o\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 0\n" +
                "            },\n" +
                "            \"orderBy\": 13\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d399b32015d39c36e420001\",\n" +
                "            \"code\": \"Rule\",\n" +
                "            \"name\": \"规则管理\",\n" +
                "            \"img\": \"fa fa-superscript\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/1\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                \"code\": \"BaseData\",\n" +
                "                \"name\": \"基础数据\",\n" +
                "                \"img\": \"fa fa-folder-open-o\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 0\n" +
                "            },\n" +
                "            \"orderBy\": 14\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d399b32015d39e045a90006\",\n" +
                "            \"code\": \"RuleProject\",\n" +
                "            \"name\": \"规则设计\",\n" +
                "            \"img\": \"fa fa-random\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Rule/RuleProject/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c36e420001\",\n" +
                "                \"code\": \"Rule\",\n" +
                "                \"name\": \"规则管理\",\n" +
                "                \"img\": \"fa fa-superscript\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                    \"code\": \"BaseData\",\n" +
                "                    \"name\": \"基础数据\",\n" +
                "                    \"img\": \"fa fa-folder-open-o\",\n" +
                "                    \"target\": \"expand\",\n" +
                "                    \"url\": \"/1\",\n" +
                "                    \"sys_systemMenu\": {\n" +
                "                        \"id\": \"0\",\n" +
                "                        \"code\": \"0\",\n" +
                "                        \"url\": \"2\"\n" +
                "                    },\n" +
                "                    \"orderBy\": 0\n" +
                "                },\n" +
                "                \"orderBy\": 14\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d3ea6b2015d3ed36a720000\",\n" +
                "            \"code\": \"RuleTableManage\",\n" +
                "            \"name\": \"单价管理\",\n" +
                "            \"img\": \"fa fa-bar-chart-o\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Rule/RuleTableManage/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c36e420001\",\n" +
                "                \"code\": \"Rule\",\n" +
                "                \"name\": \"规则管理\",\n" +
                "                \"img\": \"fa fa-superscript\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"402881e55c8cfc3f015c8d007ba90000\",\n" +
                "                    \"code\": \"BaseData\",\n" +
                "                    \"name\": \"基础数据\",\n" +
                "                    \"img\": \"fa fa-folder-open-o\",\n" +
                "                    \"target\": \"expand\",\n" +
                "                    \"url\": \"/1\",\n" +
                "                    \"sys_systemMenu\": {\n" +
                "                        \"id\": \"0\",\n" +
                "                        \"code\": \"0\",\n" +
                "                        \"url\": \"2\"\n" +
                "                    },\n" +
                "                    \"orderBy\": 0\n" +
                "                },\n" +
                "                \"orderBy\": 14\n" +
                "            },\n" +
                "            \"orderBy\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cddd2f9015cdf0aea650000\",\n" +
                "            \"code\": \"service\",\n" +
                "            \"name\": \"客服中心\",\n" +
                "            \"img\": \"fa fa-female\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/1\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"0\",\n" +
                "                \"code\": \"0\",\n" +
                "                \"url\": \"2\"\n" +
                "            },\n" +
                "            \"orderBy\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cddd2f9015cdf0c55510001\",\n" +
                "            \"code\": \"OrderManage\",\n" +
                "            \"name\": \"订单管理\",\n" +
                "            \"img\": \"fa fa-file\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Order/OrderManage/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e95cddd2f9015cdf0aea650000\",\n" +
                "                \"code\": \"service\",\n" +
                "                \"name\": \"客服中心\",\n" +
                "                \"img\": \"fa fa-female\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 1\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cddd2f9015cdf0d885e0002\",\n" +
                "            \"code\": \"OrderHistory\",\n" +
                "            \"name\": \"历史订单\",\n" +
                "            \"img\": \"fa fa-map-o\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Order/OrderManage/History/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e95cddd2f9015cdf0aea650000\",\n" +
                "                \"code\": \"service\",\n" +
                "                \"name\": \"客服中心\",\n" +
                "                \"img\": \"fa fa-female\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 1\n" +
                "            },\n" +
                "            \"orderBy\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cddd2f9015cdf0ea85c0003\",\n" +
                "            \"code\": \"OrderTrace\",\n" +
                "            \"name\": \"订单追踪\",\n" +
                "            \"img\": \"fa fa-automobile\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/1\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e95cddd2f9015cdf0aea650000\",\n" +
                "                \"code\": \"service\",\n" +
                "                \"name\": \"客服中心\",\n" +
                "                \"img\": \"fa fa-female\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 1\n" +
                "            },\n" +
                "            \"orderBy\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95cddd2f9015cdf0ff7040004\",\n" +
                "            \"code\": \"OrderSing\",\n" +
                "            \"name\": \"回单管理\",\n" +
                "            \"img\": \"fa fa-building-o\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Order/Back/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881e95cddd2f9015cdf0aea650000\",\n" +
                "                \"code\": \"service\",\n" +
                "                \"name\": \"客服中心\",\n" +
                "                \"img\": \"fa fa-female\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 1\n" +
                "            },\n" +
                "            \"orderBy\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d399b32015d39c9af860004\",\n" +
                "            \"code\": \"Transportation\",\n" +
                "            \"name\": \"运输作业\",\n" +
                "            \"img\": \"fa fa-truck\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/1\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"0\",\n" +
                "                \"code\": \"0\",\n" +
                "                \"url\": \"2\"\n" +
                "            },\n" +
                "            \"orderBy\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95d652cf8015d658121640000\",\n" +
                "            \"code\": \"SubOrder\",\n" +
                "            \"name\": \"分段运作\",\n" +
                "            \"img\": \"fa fa-hand-scissors-o\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/OrderSub/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c9af860004\",\n" +
                "                \"code\": \"Transportation\",\n" +
                "                \"name\": \"运输作业\",\n" +
                "                \"img\": \"fa fa-truck\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 2\n" +
                "            },\n" +
                "            \"orderBy\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"ff8081815d55a0d2015d55b839340000\",\n" +
                "            \"code\": \"StationCheck\",\n" +
                "            \"name\": \"到站登记\",\n" +
                "            \"img\": \"fa fa-anchor\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Arrive/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c9af860004\",\n" +
                "                \"code\": \"Transportation\",\n" +
                "                \"name\": \"运输作业\",\n" +
                "                \"img\": \"fa fa-truck\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 2\n" +
                "            },\n" +
                "            \"orderBy\": 4\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d7c6ef2015d7d0176670000\",\n" +
                "            \"code\": \"Transmittals\",\n" +
                "            \"name\": \"提送作业单\",\n" +
                "            \"img\": \"fa fa-automobile\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/1\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c9af860004\",\n" +
                "                \"code\": \"Transportation\",\n" +
                "                \"name\": \"运输作业\",\n" +
                "                \"img\": \"fa fa-truck\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 2\n" +
                "            },\n" +
                "            \"orderBy\": 5\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"ff8081815d55a0d2015d55bbf51a0001\",\n" +
                "            \"code\": \"WaybillManage\",\n" +
                "            \"name\": \"运单管理\",\n" +
                "            \"img\": \"fa fa-columns\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Waybill/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c9af860004\",\n" +
                "                \"code\": \"Transportation\",\n" +
                "                \"name\": \"运输作业\",\n" +
                "                \"img\": \"fa fa-truck\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 2\n" +
                "            },\n" +
                "            \"orderBy\": 6\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d7c6ef2015d7d038feb0001\",\n" +
                "            \"code\": \"ArriveStation\",\n" +
                "            \"name\": \"到站车辆管理\",\n" +
                "            \"img\": \"fa fa-mars-double\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/ArriveStation/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c9af860004\",\n" +
                "                \"code\": \"Transportation\",\n" +
                "                \"name\": \"运输作业\",\n" +
                "                \"img\": \"fa fa-truck\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 2\n" +
                "            },\n" +
                "            \"orderBy\": 7\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e95d652cf8015d658249460001\",\n" +
                "            \"code\": \"VirtualWarehouse\",\n" +
                "            \"name\": \"虚拟仓储\",\n" +
                "            \"img\": \"fa fa-database\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Storage/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c9af860004\",\n" +
                "                \"code\": \"Transportation\",\n" +
                "                \"name\": \"运输作业\",\n" +
                "                \"img\": \"fa fa-truck\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 2\n" +
                "            },\n" +
                "            \"orderBy\": 10\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d399b32015d39cdf8350005\",\n" +
                "            \"code\": \"Trace\",\n" +
                "            \"name\": \"在途追踪\",\n" +
                "            \"img\": \"fa fa-map-pin\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/1\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"0\",\n" +
                "                \"code\": \"0\",\n" +
                "                \"url\": \"2\"\n" +
                "            },\n" +
                "            \"orderBy\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881e85defc497015deff9afe00000\",\n" +
                "            \"code\": \"WaybillPassage\",\n" +
                "            \"name\": \"运单在途跟踪\",\n" +
                "            \"img\": \"fa fa-eye\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/OnPassage/WaybillPassage/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39cdf8350005\",\n" +
                "                \"code\": \"Trace\",\n" +
                "                \"name\": \"在途追踪\",\n" +
                "                \"img\": \"fa fa-map-pin\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 3\n" +
                "            },\n" +
                "            \"orderBy\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d399b32015d39c83fc20003\",\n" +
                "            \"code\": \"Exception\",\n" +
                "            \"name\": \"异常\",\n" +
                "            \"img\": \"fa fa-warning\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/OnPassage/Abnormal/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39cdf8350005\",\n" +
                "                \"code\": \"Trace\",\n" +
                "                \"name\": \"在途追踪\",\n" +
                "                \"img\": \"fa fa-map-pin\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 3\n" +
                "            },\n" +
                "            \"orderBy\": 5\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d399b32015d39c4ec1b0002\",\n" +
                "            \"code\": \"Ledger\",\n" +
                "            \"name\": \"财务结算\",\n" +
                "            \"img\": \"fa fa-cny\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/1\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"0\",\n" +
                "                \"code\": \"0\",\n" +
                "                \"url\": \"2\"\n" +
                "            },\n" +
                "            \"orderBy\": 5\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"ff8081815d5ab05d015d5af4edec0004\",\n" +
                "            \"code\": \"IncomeLeager\",\n" +
                "            \"name\": \"应收台帐\",\n" +
                "            \"img\": \"fa fa-arrow-left\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Leager/Income/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c4ec1b0002\",\n" +
                "                \"code\": \"Ledger\",\n" +
                "                \"name\": \"财务结算\",\n" +
                "                \"img\": \"fa fa-cny\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 5\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"ff8081815d5ab05d015d5af60ad50005\",\n" +
                "            \"code\": \"CostLeager\",\n" +
                "            \"name\": \"应付台帐\",\n" +
                "            \"img\": \"fa fa-arrow-right\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Leager/Cost/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045d399b32015d39c4ec1b0002\",\n" +
                "                \"code\": \"Ledger\",\n" +
                "                \"name\": \"财务结算\",\n" +
                "                \"img\": \"fa fa-cny\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/1\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 5\n" +
                "            },\n" +
                "            \"orderBy\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045d399b32015d39c1df4e0000\",\n" +
                "            \"code\": \"Report\",\n" +
                "            \"name\": \"报表\",\n" +
                "            \"img\": \"fa fa-pie-chart\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/1\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"0\",\n" +
                "                \"code\": \"0\",\n" +
                "                \"url\": \"2\"\n" +
                "            },\n" +
                "            \"orderBy\": 6\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045befe9a9015bf03a378b0000\",\n" +
                "            \"code\": \"SysManage\",\n" +
                "            \"name\": \"系统管理\",\n" +
                "            \"img\": \"fa fa fa-desktop\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/0\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"0\",\n" +
                "                \"code\": \"0\",\n" +
                "                \"url\": \"2\"\n" +
                "            },\n" +
                "            \"orderBy\": 8\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045befe9a9015bf03fd0c40002\",\n" +
                "            \"code\": \"UserManage\",\n" +
                "            \"name\": \"用户管理\",\n" +
                "            \"img\": \"fa fa fa-user\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/RBAC/User/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045befe9a9015bf03a378b0000\",\n" +
                "                \"code\": \"SysManage\",\n" +
                "                \"name\": \"系统管理\",\n" +
                "                \"img\": \"fa fa fa-desktop\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/0\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 8\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045befe9a9015bf040baf30003\",\n" +
                "            \"code\": \"RoleManage\",\n" +
                "            \"name\": \"角色管理\",\n" +
                "            \"img\": \"fa fa-paw\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/RBAC/Role/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045befe9a9015bf03a378b0000\",\n" +
                "                \"code\": \"SysManage\",\n" +
                "                \"name\": \"系统管理\",\n" +
                "                \"img\": \"fa fa fa-desktop\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/0\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 8\n" +
                "            },\n" +
                "            \"orderBy\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045bfb96de015bfbad8d750000\",\n" +
                "            \"code\": \"DataBook\",\n" +
                "            \"name\": \"数据字典\",\n" +
                "            \"img\": \"fa fa fa-book\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"jsp/System/DD/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045befe9a9015bf03a378b0000\",\n" +
                "                \"code\": \"SysManage\",\n" +
                "                \"name\": \"系统管理\",\n" +
                "                \"img\": \"fa fa fa-desktop\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/0\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 8\n" +
                "            },\n" +
                "            \"orderBy\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045c86496c015c865639770000\",\n" +
                "            \"code\": \"Sys_State\",\n" +
                "            \"name\": \"系统状态\",\n" +
                "            \"img\": \"fa fa-laptop\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/System/Menu/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045befe9a9015bf03a378b0000\",\n" +
                "                \"code\": \"SysManage\",\n" +
                "                \"name\": \"系统管理\",\n" +
                "                \"img\": \"fa fa fa-desktop\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/0\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 8\n" +
                "            },\n" +
                "            \"orderBy\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045befe9a9015bf03ce0b20001\",\n" +
                "            \"code\": \"RapidDevelopment\",\n" +
                "            \"name\": \"快速开发\",\n" +
                "            \"img\": \"fa fa fa-code\",\n" +
                "            \"target\": \"expand\",\n" +
                "            \"url\": \"/0\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"0\",\n" +
                "                \"code\": \"0\",\n" +
                "                \"url\": \"2\"\n" +
                "            },\n" +
                "            \"orderBy\": 9\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045befe9a9015bf04425940004\",\n" +
                "            \"code\": \"GirdDevelopment\",\n" +
                "            \"name\": \"列表开发\",\n" +
                "            \"img\": \"fa fa-bars\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/AuthorizeManage/GridManage/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045befe9a9015bf03ce0b20001\",\n" +
                "                \"code\": \"RapidDevelopment\",\n" +
                "                \"name\": \"快速开发\",\n" +
                "                \"img\": \"fa fa fa-code\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/0\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 9\n" +
                "            },\n" +
                "            \"orderBy\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045befe9a9015bf045a3e20005\",\n" +
                "            \"code\": \"From\",\n" +
                "            \"name\": \"表单开发\",\n" +
                "            \"img\": \"fa fa-building-o\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/AuthorizeManage/ModuleForm/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045befe9a9015bf03ce0b20001\",\n" +
                "                \"code\": \"RapidDevelopment\",\n" +
                "                \"name\": \"快速开发\",\n" +
                "                \"img\": \"fa fa fa-code\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/0\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 9\n" +
                "            },\n" +
                "            \"orderBy\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045befe9a9015bf047e72d0006\",\n" +
                "            \"code\": \"ButtonManage\",\n" +
                "            \"name\": \"按钮管理\",\n" +
                "            \"img\": \"fa fa-th\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"jsp/System/Button/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045befe9a9015bf03ce0b20001\",\n" +
                "                \"code\": \"RapidDevelopment\",\n" +
                "                \"name\": \"快速开发\",\n" +
                "                \"img\": \"fa fa fa-code\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/0\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 9\n" +
                "            },\n" +
                "            \"orderBy\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045befe9a9015bf047e72d0002\",\n" +
                "            \"code\": \"MenuManage\",\n" +
                "            \"name\": \"菜单管理\",\n" +
                "            \"img\": \"fa fa fa-navicon\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/System/Menu/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045befe9a9015bf03ce0b20001\",\n" +
                "                \"code\": \"RapidDevelopment\",\n" +
                "                \"name\": \"快速开发\",\n" +
                "                \"img\": \"fa fa fa-code\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/0\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 9\n" +
                "            },\n" +
                "            \"orderBy\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045c66cbd8015c66cef31b0000\",\n" +
                "            \"code\": \"ClassManage\",\n" +
                "            \"name\": \"类生成器\",\n" +
                "            \"img\": \"fa fa-file-text-o\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/AuthorizeManage/ClassManage/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"402881045befe9a9015bf03ce0b20001\",\n" +
                "                \"code\": \"RapidDevelopment\",\n" +
                "                \"name\": \"快速开发\",\n" +
                "                \"img\": \"fa fa fa-code\",\n" +
                "                \"target\": \"expand\",\n" +
                "                \"url\": \"/0\",\n" +
                "                \"sys_systemMenu\": {\n" +
                "                    \"id\": \"0\",\n" +
                "                    \"code\": \"0\",\n" +
                "                    \"url\": \"2\"\n" +
                "                },\n" +
                "                \"orderBy\": 9\n" +
                "            },\n" +
                "            \"orderBy\": 4\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"402881045c4289d3015c428c1dd80000\",\n" +
                "            \"code\": \"ExpressManage\",\n" +
                "            \"name\": \"快递管理\",\n" +
                "            \"img\": \"fa fa-paper-plane-o\",\n" +
                "            \"target\": \"iframe\",\n" +
                "            \"url\": \"/jsp/Express/Index.jsp\",\n" +
                "            \"sys_systemMenu\": {\n" +
                "                \"id\": \"0\",\n" +
                "                \"code\": \"0\",\n" +
                "                \"url\": \"2\"\n" +
                "            },\n" +
                "            \"orderBy\": 10\n" +
                "        }\n" +
                "    ]\n" +
                "   \n" +
                "}";
    }
}
