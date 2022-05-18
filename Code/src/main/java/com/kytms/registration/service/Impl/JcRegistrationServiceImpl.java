package com.kytms.registration.service.Impl;

import com.kytms.core.entity.JcRegistration;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.registration.dao.Impl.JcRegistrationDaoImpl;
import com.kytms.registration.dao.JcRegistrationDao;
import com.kytms.registration.service.JcRegistrationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**辽宁捷畅物流有限公司  集团信息技术中心
 * 孙德增
 * Created by sundezeng on 2017/12/8.
 */
@Service(value = "JcRegistrationService")
public class JcRegistrationServiceImpl extends BaseServiceImpl<JcRegistration> implements JcRegistrationService<JcRegistration> {
    private final Logger log = Logger.getLogger(JcRegistrationServiceImpl.class);//输出Log日志
    private JcRegistrationDao<JcRegistration> jcRegistrationDao;
    @Resource(name = "JcRegistrationDao")
    public void setJcRegistrationDao(JcRegistrationDao<JcRegistration> jcRegistrationDao) {
        super.setBaseDao(jcRegistrationDao);
        this.jcRegistrationDao = jcRegistrationDao;
    }




    public JgGridListModel getJcRegistrationList(CommModel commModel) {
        String orderBY = " ORDER BY status desc ,create_time desc";
        return super.getListByPage(commModel,null,orderBY);
    }

    public List<JcRegistration> getUserForExcel(JcRegistration jcRegistration) {
        List<JcRegistration> list = new ArrayList();
        Integer order;
        for (int i = 0; i < list.size(); i++) {
            order = i + 1;
            list.get(i).setId(jcRegistration.getId());
            list.get(i).setCode(jcRegistration.getCode());
            list.get(i).setName(jcRegistration.getName());
    }
        return list;
    }
}
