package com.kytms.rbac.service.impl;

import com.kytms.core.constants.Icon;
import com.kytms.core.entity.Organization;
import com.kytms.core.entity.Role;
import com.kytms.core.entity.User;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SpringUtils;
import com.kytms.core.utils.StringUtils;
import com.kytms.organization.service.OrgService;
import com.kytms.rbac.dao.Impl.RoleDaoImpl;
import com.kytms.rbac.dao.UserDao;
import com.kytms.rbac.service.RoleService;
import com.kytms.rbac.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 实现类
 *
 * @author 奇趣源码
 * @create 2017-11-18
 */
@Service(value = "UserService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService<User>{
    private final Logger log = Logger.getLogger(UserServiceImpl.class);//输出Log日志
    private UserDao<User> userDao;
    private OrgService<Organization> orgService;
    @Resource(name = "UserDao")
    public void setUserDao(UserDao<User> userDao) {
        super.setBaseDao(userDao);
        this.userDao = userDao;
    }
    @Resource(name = "OrgService")
    public void setOrgService(OrgService orgService) {
        this.orgService = orgService;
    }




    public JgGridListModel getUserList(CommModel commModel) {
        String orderBY = " ORDER BY status desc ,create_time desc";
        return super.getListByPage(commModel,null,orderBY);
    }

    public List<TreeModel> getRoleTree(String id2) {
        //查询所有角色。做成树
        RoleService roleDao = SpringUtils.getBean(RoleService.class);
        List<Role> sys_roles = roleDao.selectList(new CommModel());
        Map<String,Integer> counter = new HashMap<String, Integer>(); //优化计数器
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (int i = 0; i <sys_roles.size() ; i++) {
            TreeModel treeModel = new TreeModel();
            Role sys_role = sys_roles.get(i);
            treeModel.setId(sys_role.getId());
            treeModel.setText(sys_role.getName());
            treeModel.setHasChildren(false);
            treeModel.setCheckstate(0);
            treeModel.setShowcheck(true);
            treeModel.setIsexpand(true);
            treeModel.setImg(Icon.ROLE_TREE);
            treeModels.add(treeModel);
            counter.put(treeModel.getId(),i); //存储键和list中的顺序
        }
        //查询用户角色 赋值check
        User sys_user = userDao.selectBean(id2);
        List<Role> sys_roles1 = sys_user.getRoles();
        if (sys_roles1 != null && sys_roles1.size()>0){
            for (int i = 0; i <sys_roles1.size() ; i++) {
                Role sys_role = sys_roles1.get(i);
                String id = sys_role.getId();
                Integer number = counter.get(id);
                TreeModel treeModel = treeModels.get(number);
                treeModel.setCheckstate(1);//修改状态
            }
        }
        return treeModels;
    }

    public void saveRole(String id, String ids) {
        RoleDaoImpl bean = SpringUtils.getBean(RoleDaoImpl.class);
        String[] split = ids.split(",");
        List<Role> list = new ArrayList<Role>();
        for (int i = 0; i < split.length; i++) {

            Role menu = bean.selectBean(split[i]);
            list.add(menu);
        }
        User user = userDao.selectBean(id);
        user.setRoles(list);
    }

    public List<TreeModel> getOrgTree(String id) {
        Map<String,String> cache = new HashMap<String, String>();
        User user = userDao.selectBean(id);
        List<Organization> organizations = user.getOrganizations();
        for (Organization org : organizations) {
            String id1 = org.getId();
            cache.put(id1,"");
        }
        List<Organization> list = orgService.selectList(new CommModel(), " and id !='root'", null);
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Organization org :list) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(org.getId());
            treeModel.setText(org.getName());
            treeModel.setComplete(false);
            treeModel.setHasChildren(false);
            treeModel.setIsexpand(false);
            treeModel.setShowcheck(true);
            if (cache.get(org.getId()) != null){
                treeModel.setCheckstate(1);
            }else {
                treeModel.setCheckstate(0);
            }
            treeModels.add(treeModel);
        }
        return treeModels;
    }

    public void saveUserOrg(String id, String ids) {
        String[] split = ids.split(",");
        List<Organization> list = new ArrayList<Organization>();
        for (int i = 0; i < split.length; i++) {

            Organization org = orgService.selectBean(split[i]);
            list.add(org);
        }
        User user = userDao.selectBean(id);
        user.setOrganizations(list);
    }

    public User userLogin(String code, String password) {
        String Hql = " FROM JC_SYS_USER WHERE CODE= '"+code+"' and password = '"+password+"' and status = 1";
        List<User> users = userDao.executeQueryHql(Hql);
        if(users == null || users.size() != 1){
            return null;
        }
        return users.get(0);
    }

    public User updatePassWord(String tableName, String[] id) {
        //密码是：JetChain_123456_xxzx
        String pw = "9FA169E6E03607DADDE25D1C3205EFC6";
        if (id != null || id.length !=0){
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE ");
            sb.append(tableName);
            sb.append(" SET password = '"+pw+"' WHERE id in (");
            sb.append( StringUtils.BySqlIn(id));
            sb.append(")");
            userDao.executeHql(sb.toString(),null);
        }
        return null;
    }

}
