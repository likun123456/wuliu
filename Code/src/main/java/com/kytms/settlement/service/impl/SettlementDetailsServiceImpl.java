package com.kytms.settlement.service.impl;

import com.kytms.core.entity.SelltlmentDetails;
import com.kytms.core.entity.Settlement;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.settlement.dao.SettlementDetailsDao;
import com.kytms.settlement.dao.impl.SettlementDetailsDaoImpl;
import com.kytms.settlement.service.SettlementDetailsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/5/28.
 */
@Service(value = "SettlementDetailsService")
public class SettlementDetailsServiceImpl extends BaseServiceImpl<SelltlmentDetails> implements SettlementDetailsService<SelltlmentDetails> {
    private final Logger log = Logger.getLogger(SettlementDetailsServiceImpl.class);//输出Log日志
    private SettlementDetailsDao settlementDetailsDao;
    public SettlementDetailsDao getSettlementDetailsDao() {
        return settlementDetailsDao;
    }
    @Resource(name = "SettlementDetailsDao")
    public void setSettlementDetailsDao(SettlementDetailsDao settlementDetailsDao) {
        this.settlementDetailsDao = settlementDetailsDao;
        super.setBaseDao(settlementDetailsDao);
    }



    public void saveD(List list){
        String hql = "select a.id,b.id from JC_LEDGER a left join a.order b  left join a.organization d where b.id !='' and a.jsStatus = 2 and d.id = '"+ SessionUtil.getOrgId()+"'";
        List<SelltlmentDetails> list111 = settlementDetailsDao.executeQueryHql(hql);
        for (Object sds1:list111) {
            String s = sds1.toString();
            SelltlmentDetails sds = new SelltlmentDetails();
            sds.setSettlement((Settlement) list);
           settlementDetailsDao.savaBean(sds);
        }
    }
}
