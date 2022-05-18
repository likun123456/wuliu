package com.kytms.rbac.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Role;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.presco.service.impl.PrescoServiceImpl;
import com.kytms.rbac.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/20 0020.
 * 角色Action
 */
@Controller
@RequestMapping( "/role")
public class RoleAction extends BaseAction {
    private final Logger log = Logger.getLogger(RoleAction.class);//输出Log日志
    private RoleService<Role> roleService;
    @Resource(name = "RoleService")
    public void setRoleService(RoleService<Role> roleService) {
        this.roleService = roleService;
    }




    @RequestMapping(value = "/saveMenu", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找保存按钮数据库方法
    public String  saveMenu(String id,String ids){
        roleService.saveMenu(id,ids);
        return returnJson(getReturnModel());
    }

    @RequestMapping(value = "/getRoleMenuTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找菜单数据库方法
    public String  getRoleMenuTree(CommModel commModel){
        List<TreeModel> list = roleService.getRoleMenuTree(commModel);
        return returnJson(list);
    }

    @RequestMapping(value = "/getRoleList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找角色数据库方法
    public String  getRoleList(CommModel commModel){
        JgGridListModel jgGridListModel =roleService.getRoleList(commModel);
        return returnJson(jgGridListModel);
    }

    @RequestMapping(value = "/saveButton", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveButton(String id,String ids){
        roleService.saveButton(id,ids);
        return returnJson(getReturnModel());

    }

    @RequestMapping(value = "/getRoleButtonList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找按钮数据库方法
    public String  getRoleButtonList(CommModel commModel){
        List<TreeModel> list = roleService.getButtonFucntionTree(commModel);
        return returnJson(list);
    }

    //编辑中的保存方法
    @RequestMapping(value = "/saveRole", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveRole(Role role){
        ReturnModel returnModel = getReturnModel();
         returnModel.setObj("保存成功... ...");
       returnModel.codeUniqueAndNull(role,roleService);
        if (StringUtils.isEmpty(role.getName())){
            returnModel.addError("name","用户名不能为空");
        }
        if(returnModel.isResult()){
            if (StringUtils.isNotEmpty(role.getId())){
                Role o = roleService.selectBean(role.getId());
                role.setMenus(o.getMenus());
                role.setButtons(o.getButtons());
            }
            roleService.saveBean(role);
        }
        return returnModel.toJsonString();
    }
}
