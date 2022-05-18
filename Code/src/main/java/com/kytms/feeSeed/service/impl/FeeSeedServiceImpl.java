package com.kytms.feeSeed.service.impl;

import com.kytms.core.constants.Symbol;
import com.kytms.core.entity.FeeSeed;
import com.kytms.core.entity.LedgerDetail;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.SpringUtils;
import com.kytms.core.utils.StringUtils;
import com.kytms.feeSeed.dao.FeeSeedDao;
import com.kytms.feeSeed.dao.impl.FeeSeedDaoImpl;
import com.kytms.feeSeed.service.FeeSeedService;
import com.kytms.ledgerdetails.dao.impl.LedgerDetailDaoImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @创建人: 陈小龙
 * @创建日期: 2018/10/29
 * @类描述:
 */
@Service(value = "FeeSeedService")
public class FeeSeedServiceImpl extends BaseServiceImpl<FeeSeed> implements FeeSeedService<FeeSeed> {
    private final Logger log = Logger.getLogger(FeeSeedServiceImpl.class);//输出Log日志
    private FeeSeedDao<FeeSeed> feeSeedDao;
    @Resource(name = "FeeSeedDao")
    public void setFeeSeedDao(FeeSeedDao<FeeSeed> feeSeedDao) {
        super.setBaseDao(feeSeedDao);
        this.feeSeedDao = feeSeedDao;
    }




    public JgGridListModel getList(CommModel commModel) {
        String where="";
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where += " and status = "+commModel.getStatus();
        }
        String orderBY = " ORDER BY status desc ,create_time desc";
        return super.getListByPage(commModel,where,orderBY);
    }

    public void confirm(String id) throws MessageException {
        String[] split = id.split(Symbol.COMMA);
        for (String splitId : split) {
            FeeSeed feeSeed =feeSeedDao.selectBean(splitId);
            if(!feeSeed.getReceiveOrganization().getId().equals(SessionUtil.getOrgId())){
                throw new MessageException("只有接收机构可以确认费用");
            }
            if(feeSeed.getConfirmStatus()==1){
                throw new MessageException("此费用已经确认");
            }
            feeSeed.setConfirmStatus(1);
        }
    }

    public void saveFeeSeed(FeeSeed feeSeed) {
        feeSeed.setOrganization(SessionUtil.getOrg());
        feeSeed.setStartOrganization(SessionUtil.getOrg());
        feeSeed.setConfirmStatus(0);
        if (StringUtils.isEmpty(feeSeed.getId())) { // 如果是空 说明是新建
            feeSeed.setCode(getOrderCode());
        }
        List<LedgerDetail> ledgerDetails = feeSeed.getLedgerDetails();//获取台账信息
        Double amount=0.0;
        Double input=0.0;
        for(LedgerDetail ledgerDetail:ledgerDetails){
            amount+=ledgerDetail.getAmount();
            input+=ledgerDetail.getInput();
        }
        feeSeed.setAmount(amount);
        feeSeed.setInput(input);
        feeSeed.setLedgerDetails(null);
        FeeSeed feeSeed1 = feeSeedDao.savaBean(feeSeed);
        LedgerDetailDaoImpl ledgerDetailDao = SpringUtils.getBean(LedgerDetailDaoImpl.class); //存储明细
        for(LedgerDetail ledgerDetail:ledgerDetails){
            ledgerDetail.setFeeSeed(feeSeed1);
            ledgerDetailDao.savaBean(ledgerDetail);
        }

    }
}
