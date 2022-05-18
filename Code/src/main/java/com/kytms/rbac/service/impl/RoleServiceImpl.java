package com.kytms.rbac.service.impl;

import com.kytms.core.entity.Button;
import com.kytms.core.entity.Menu;
import com.kytms.core.entity.Role;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SpringUtils;
import com.kytms.rbac.dao.Impl.UserDaoImpl;
import com.kytms.rbac.dao.RoleDao;
import com.kytms.rbac.service.RoleService;
import com.kytms.system.dao.Impl.ButtonDaoImpl;
import com.kytms.system.dao.Impl.MenuDaoImpl;
import com.kytms.system.service.ButtonService;
import com.kytms.system.service.MenuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/20 0020.
 */
@Service(value = "RoleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService<Role> {
    private final Logger log = Logger.getLogger(RoleServiceImpl.class);//输出Log日志
    private final Boolean COMPLETE = true;
    private final Boolean IS_EXPANED = true;
    private final Boolean SHOW_CHECK = true;
    private RoleDao<Role> roleDao;
    private MenuService<Menu> menuService;
    @Resource(name = "RoleDao")
    public void setRoleDao(RoleDao<Role> roleDao) {
        super.setBaseDao(roleDao);
        this.roleDao = roleDao;
    }
    @Resource
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }





    public JgGridListModel getRoleList(CommModel commModel) {
        String roleBy = " ORDER BY status desc ,create_time desc";
        return super.getListByPage(commModel, null, roleBy);
    }

    /**
     * 获取角色拥有菜单方法
     *
     * @param ‘menuService’
     */
    public List<TreeModel> getRoleMenuTree(CommModel commModel) {
        //查询权限 形成树
        List<Menu> menus = menuService.selectMenuListMain(new CommModel());
        List<TreeModel> treeModels = TreeRecursionToRole(menus, commModel.getId());
        return treeModels;
    }

    //sd ssd
    public void saveMenu(String id, String ids) {
        MenuDaoImpl bean = SpringUtils.getBean(MenuDaoImpl.class);
        String[] split = ids.split(",");
        List<Menu> list = new ArrayList<Menu>();
        for (int i = 0; i < split.length; i++) {
            Menu menu = bean.selectBean(split[i]);
            list.add(menu);
        }
        Role role = roleDao.selectBean(id);
        role.setMenus(list);
    }

    public void saveButton(String id, String ids) {
        ButtonDaoImpl buttonBean = SpringUtils.getBean(ButtonDaoImpl.class);
        String[] sSplit = ids.split(",");
        List<Button> list = new ArrayList<Button>();
        for (int i = 0; i < sSplit.length; i++) {
            Button buttonss = buttonBean.selectBean(sSplit[i]);
            list.add(buttonss);
        }
        Role role = roleDao.selectBean(id);
        role.setButtons(list);
    }

    /**
     * 菜单递归函数
     *
     * @return
     */
    public List<TreeModel> TreeRecursionToRole(List<Menu> sys_systemMenu, String id) {
        List<TreeModel> treeList = new ArrayList<TreeModel>();

        for (int i = 0; i < sys_systemMenu.size(); i++) {
            Menu menu = sys_systemMenu.get(i);
            String name = menu.getName();
            TreeModel tree = new TreeModel();
            tree.setId(menu.getId());
            tree.setText(menu.getName());
            tree.setComplete(COMPLETE);
            tree.setImg(menu.getImg());
            tree.setIsexpand(IS_EXPANED);
            tree.setShowcheck(SHOW_CHECK);
            List<Role> sys_roles = menu.getRoles();
            for (int j = 0; j < sys_roles.size(); j++) {
                Role sys_role = sys_roles.get(j);
                if (sys_role.getId().equals(id)) {
                    tree.setCheckstate(1);
                    break;
                } else {
                    tree.setCheckstate(0);
                }
            }
            if (menu.getMenu() != null) {
                tree.setParentnodes(menu.getMenu().getId());
            }
            //tree.setHasChildren();
            if (menu.getMenus().size() > 0) {
                tree.setHasChildren(true);
                List<Menu> sys_systemMenus = menu.getMenus();
                List<TreeModel> treeModels = TreeRecursionToRole(sys_systemMenus, id);//开始递归
                tree.setChildNodes(treeModels);
            } else {
                tree.setHasChildren(false);
            }
            treeList.add(tree);
        }
        return treeList;
    }


    /**
     * 按钮递归算法
     *
     * @param commModel
     * @return
     */
    public List<TreeModel> getButtonFucntionTree(CommModel commModel) {
        //角色查询
        Map<String, Integer> setFunction = new HashMap<String, Integer>();
        Role sys_role = roleDao.selectBean(commModel.getId());
        List<Button> sys_buttonManages2 = sys_role.getButtons();
        for (int j = 0; j < sys_buttonManages2.size(); j++) {
            Button sys_buttonManage = sys_buttonManages2.get(j);
            setFunction.put(sys_buttonManage.getId(), 1);
        }
        //权限列表
        MenuDaoImpl sysMenuDao = SpringUtils.getBean(MenuDaoImpl.class);
        List<Menu> systemMenus = sysMenuDao.executeQueryHql("From JC_SYS_MENU WHERE 1=1 and id != 'root'");
        List<TreeModel> treeList = new ArrayList<TreeModel>();
        for (int i = 0; i < systemMenus.size(); i++) {// 菜单循环
            Menu systemMenu = systemMenus.get(i);
            TreeModel tree = new TreeModel();
            tree.setId(systemMenu.getId());
            tree.setText(systemMenu.getName());
            tree.setComplete(COMPLETE);
            tree.setImg(systemMenu.getImg());
            tree.setIsexpand(IS_EXPANED);
            tree.setShowcheck(false);
            List<TreeModel> buttonList = new ArrayList<TreeModel>();
            List<Button> sys_buttonManages = systemMenu.getButtons();
            //如果有按钮则展开
            if (sys_buttonManages.size() > 0) {
                tree.setHasChildren(true);
            }
            for (int j = 0; j < sys_buttonManages.size(); j++) {//按钮循环
                Button sys_buttonManage = sys_buttonManages.get(j);
                TreeModel button = new TreeModel();
                button.setId(sys_buttonManage.getId());
                button.setText(sys_buttonManage.getName());
                button.setComplete(COMPLETE);
                button.setIsexpand(IS_EXPANED);
                button.setShowcheck(SHOW_CHECK);
                //权限授权
                boolean b = setFunction.get(sys_buttonManage.getId()) != null;
                if (setFunction.get(sys_buttonManage.getId()) != null) {
                    button.setCheckstate(1);
                } else {
                    button.setCheckstate(0);
                }
                buttonList.add(button);
            }
            tree.setChildNodes(buttonList);
            treeList.add(tree);
        }
        return treeList;
    }
}