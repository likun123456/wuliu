package com.kytms.system.service.Impl;

import com.kytms.core.entity.Menu;
import com.kytms.core.entity.User;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.system.dao.MenuDao;
import com.kytms.system.service.MenuService;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 实现类
 *
 * @author 奇趣源码
 * @create 2017-11-18
 */
@Service(value = "MenuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService<Menu> {
    private final Logger log = Logger.getLogger(MenuServiceImpl.class);//输出Log日志
    private final Boolean COMPLETE = true;
    private final Boolean IS_EXPANED=true;
    private final Boolean SHOW_CHECK=false;
    private MenuDao<Menu> menuDao;
    @Resource(name = "MenuDao")
    public void setMenuDao(MenuDao<Menu> menuDao) {
        super.setBaseDao(menuDao);
        this.menuDao = menuDao;
    }




    public List<TreeModel> TreeRecursion(List<Menu> Menu) {
        List<TreeModel> treeList = new ArrayList<TreeModel>();
        for (int i = 0; i < Menu.size(); i++) {
            Menu menu = Menu.get(i);
            TreeModel tree = new TreeModel();
            tree.setId(menu.getId());
            tree.setText(menu.getName());
            tree.setComplete(COMPLETE);
            tree.setImg(menu.getImg());
            tree.setIsexpand(IS_EXPANED);
            tree.setShowcheck(SHOW_CHECK);
            if (menu.getMenu()!=null){
                tree.setParentnodes(menu.getMenu().getId());
            }
            //tree.setHasChildren();
            if (menu.getMenus().size()>0){
                tree.setHasChildren(true);
                List<Menu> sys_systemMenus = menu.getMenus();
                List<TreeModel> treeModels = TreeRecursion(sys_systemMenus);//开始递归
                tree.setChildNodes(treeModels);
            }else{
                tree.setHasChildren(false);
            }
            treeList.add(tree);
        }
        return treeList;
    }

    public List<TreeModel> getMneuTree() {
        List<String> str = new ArrayList<String>();
        str.add("root");
        List<Menu> sys_systemMenu =  menuDao.executeQueryHql(" FROM "+ menuDao.getClassName() +" WHERE menu.id = ?  ORDER BY orderBy",str);
        return TreeRecursion(sys_systemMenu);
    }

    public List<Menu> selectMenuList(CommModel comm) {
        Session session = menuDao.getSessionFactory().openSession();
        String Hql = "FROM JC_SYS_MENU WHERE id != 'root'";
        List<Menu> menus = session.createQuery(Hql).list();
        session.close();
        return menus;
    }

    public List<Menu> selectUserMenuList() {
        User user = SessionUtil.getUser();
        List<String> age = new ArrayList<String>();
        age.add(user.getId());
        List<Menu> systemMenus = menuDao.executeQueryHql("SELECT s FROM JC_SYS_MENU s join s.roles r join r.users u where u.id=? and r.status != 0 group by s.id order by s.orderBy", age);
        return systemMenus;
    }

    public List<Menu> selectMenuListMain(CommModel comm) {
        String where = " and menu.id = 'root'";
        String order = " ORDER BY orderBy";
        if (comm.getId() != null){
            where = " AND menu.id  = '" + comm.getId()+"'";
        }
        return super.selectList(comm,where,order);
    }
}
