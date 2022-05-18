package com.kytms.shipmentTemplate.service.impl;

import com.kytms.core.constants.Symbol;
import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.SpringUtils;
import com.kytms.core.utils.StringUtils;
import com.kytms.ledgerdetails.dao.LedgerDetailDao;
import com.kytms.organization.dao.OrgDao;
import com.kytms.shipment.dao.BerthStandDao;
import com.kytms.shipmentTemplate.dao.TemplateDao;
import com.kytms.shipmentTemplate.service.TemplateService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 运单模板SERVICE实现类
 * @author 陈小龙
 * @create 2018-04-09
 */
@Service(value = "TemplateService")
public class TemplateServiceImpl extends BaseServiceImpl<ShipmentTemplate> implements TemplateService<ShipmentTemplate> {
    private final Logger log = Logger.getLogger(TemplateServiceImpl.class);//输出Log日志
    private TemplateDao<ShipmentTemplate> templateDao;
    private BerthStandDao berthStandDao;
    private OrgDao orgDao;
    private LedgerDetailDao<LedgerDetail> ledgerDetailDao;
    @Resource(name = "TemplateDao")
    public void setTemplateDao(TemplateDao<ShipmentTemplate> templateDao) {
        super.setBaseDao(templateDao);
        this.templateDao = templateDao;
    }

    @Resource(name = "BerthStandDao")
    public void setBerthStandDao(BerthStandDao berthStandDao) {
        this.berthStandDao = berthStandDao;
    }

    @Resource(name = "OrgDao")
    public void setOrgDao(OrgDao orgDao) {
        this.orgDao = orgDao;
    }

    @Resource(name = "LedgerDetailDao")
    public void setLedgerDetailDao(LedgerDetailDao<LedgerDetail> ledgerDetailDao) {
        this.ledgerDetailDao = ledgerDetailDao;
    }




    public JgGridListModel getList(CommModel commModel) {
        String where = " and organization.id ='" + SessionUtil.getOrgId() +"'";
        String orderBY = " ORDER BY  create_time desc";
        return super.getListByPage(commModel,where,orderBY);
    }

    public ShipmentTemplate saveTemplate(ShipmentTemplate shipmentTemplate) {
        shipmentTemplate.setOrganization(SessionUtil.getOrg());//绑定组织机构
        List<LedgerDetail> ledgerDetails = shipmentTemplate.getTemplateLedgers();//获取台账明细
        shipmentTemplate.setTemplateLedgers(null);//阻断关系
        ShipmentTemplate shipmentTemplate1 = templateDao.savaBean(shipmentTemplate);

        //处理经由站
        String orgIds = shipmentTemplate1.getOrgIds();
        berthStandDao.executeHql("delete from JC_BERTH_STAND where shipmentTemplate.id = '"+shipmentTemplate1.getId()+"'",null);
        List<String> sp = new ArrayList<String>();  // 站点不一致验证
        sp.add(shipmentTemplate1.getFromOrganization().getId());
        sp.add(shipmentTemplate1.getToOrganization().getId());
        if(StringUtils.isNotEmpty(orgIds)){
            String[] split = orgIds.split(Symbol.COMMA);

            shipmentTemplate1.setBerthStand(null);

            for (int i = 0; i <split.length ; i++) {
                String s = split[i];
                sp.add(s);
                Organization org = (Organization) orgDao.selectBean(s);
                int type = org.getType();
                if(type ==3){
                    throw  new MessageException("途径站不能是落地派机构");
                }
                BerthStand berthStand = new BerthStand();
                berthStand.setOrganization(org);
                berthStand.setShipmentTemplate(shipmentTemplate1);
                berthStand.setOrderBy(i);
                berthStand.setStatus(0);
                berthStandDao.savaBean(berthStand);
            }
        }
        Set<String> set = new HashSet<String>();
        for (String s:sp) {
            set.add(s);
        }
        if(set.size() != sp.size()){
            throw new MessageException("线路站点不能重复");
        }


        ledgerDetailDao.executeHql(" Delete FROM JC_LEDGER_DETAIL Where shipmentTemplate.id = '"+shipmentTemplate1.getId()+"'",null);

        for (LedgerDetail ledgerDetail : ledgerDetails) {
            double amount = ledgerDetail.getAmount();
            if(amount != 0){
                ledgerDetail.setShipmentTemplate(shipmentTemplate1);
                ledgerDetail.setType(1);
                ledgerDetail.setCost(0);
                ledgerDetailDao.savaBean(ledgerDetail);
            }
        }

        return shipmentTemplate1;
    }
}
