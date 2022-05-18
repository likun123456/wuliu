package com.kytms.otherCost.service.impl;

import com.kytms.core.entity.OtherCost;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.otherCost.dao.OtherCostDao;
import com.kytms.otherCost.dao.impl.OtherCostDaoImpl;
import com.kytms.otherCost.service.OtherCostService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Service(value = "OtherCostService")
public class OtherCostServiceImpl extends BaseServiceImpl<OtherCost> implements OtherCostService<OtherCost> {
    private final Logger log = Logger.getLogger(OtherCostServiceImpl.class);//输出Log日志
    private OtherCostDao<OtherCost> otherCostDao;

    @Resource(name = "OtherCostDao")
    public void setOtherCostDao(OtherCostDao<OtherCost> otherCostDao) {
        this.otherCostDao = otherCostDao;
        super.setBaseDao(otherCostDao);
    }


    public JgGridListModel getList(CommModel commModel) {
        String where = " and status =1 and organization.id ='"+ SessionUtil.getOrgId()+"'";
        String orderBY = " ORDER BY  create_time desc";
        return  super.getListByPage(commModel,where,orderBY);
    }
}
