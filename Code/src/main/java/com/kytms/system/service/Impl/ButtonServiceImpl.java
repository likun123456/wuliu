package com.kytms.system.service.Impl;

import com.kytms.core.entity.Button;
import com.kytms.core.model.CommModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.system.action.SystemConfigAction;
import com.kytms.system.dao.ButtonDao;
import com.kytms.system.service.ButtonService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2017-11-20
 */
@Service(value = "ButtonService")
public class ButtonServiceImpl extends BaseServiceImpl<Button> implements ButtonService<Button> {
    private final Logger log = Logger.getLogger(ButtonServiceImpl.class);//输出Log日志
    private ButtonDao<Button> buttonDao;
    @Resource(name = "ButtonDao")
    public void setButtonDao(ButtonDao buttonDao) {
        super.setBaseDao(buttonDao);
        this.buttonDao = buttonDao;
    }




    public List<Button> selectButtonList(CommModel commModel) {
        String where = " and menu.id = 'rood'";
        String order = " ORDER BY orderBy";
        if (commModel.getId() != null){
            where = " AND menu.id = '" + commModel.getId()+"'";
        }
        return super.selectList(commModel,where,order);
    }

    public Map<String, String> selectUserButtonList(String userId) {
        String hql = "SELECT a FROM JC_SYS_BUTTON a join a.roles b join b.users c where b.status != 0 and c.id ='"+userId+"'";
        Map<String, String> json = new HashMap<String, String>();
        List<Button> buttons = buttonDao.executeQueryHql(hql);
        for (int i = 0; i < buttons.size(); i++) {
            Button sys_buttonManage = buttons.get(i);
            json.put(sys_buttonManage.getCode(), sys_buttonManage.getId());
        }
        return json;
    }
}
