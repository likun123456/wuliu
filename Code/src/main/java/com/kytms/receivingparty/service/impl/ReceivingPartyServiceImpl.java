package com.kytms.receivingparty.service.impl;

import com.kytms.core.entity.ReceivingParty;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.receivingparty.dao.ReceivingPartyDao;
import com.kytms.receivingparty.dao.impl.ReceivingPartyDaoImpl;
import com.kytms.receivingparty.service.ReceivingPartyService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2017/12/18.
 * 收发货方
 */
@Service(value = "ReceivingPartyService")
public class ReceivingPartyServiceImpl extends BaseServiceImpl<ReceivingParty> implements ReceivingPartyService<ReceivingParty> {
    private final Logger log = Logger.getLogger(ReceivingPartyServiceImpl.class);//输出Log日志
    private ReceivingPartyDao<ReceivingParty> receivingPartyDao;
    public ReceivingPartyDao<ReceivingParty> getReceivingPartyDao() {
        return receivingPartyDao;
    }
    @Resource(name = "ReceivingPartyDao")
    public void setReceivingPartyDao(ReceivingPartyDao<ReceivingParty> receivingPartyDao) {
        this.receivingPartyDao = receivingPartyDao;
        super.setBaseDao(receivingPartyDao);
    }




    public JgGridListModel getReceivingPartyList(CommModel commModel) {
        String where = " and organization ='" + SessionUtil.getOrgId() + "'";
        String orderBY = " ORDER BY status desc ,create_time desc";
        return super.getListByPage(commModel, where, orderBY);
    }
}