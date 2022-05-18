package com.kytms.settlement.service.impl;

import com.kytms.core.entity.*;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.SpringUtils;
import com.kytms.settlement.SettlementHQL;
import com.kytms.settlement.dao.SettlementDao;
import com.kytms.settlement.service.SettlementService;
import com.kytms.shipment.dao.Impl.ShipmentDaoImpl;
import com.kytms.transportorder.dao.impl.TransportOrderDaoImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/5/24.
 */

@Service(value = "SettlementService")
public class SettlementServiceImpl extends BaseServiceImpl<Settlement> implements SettlementService<Settlement>{
    private final Logger log = Logger.getLogger(SettlementServiceImpl.class);//输出Log日志
    public static final String JS_CODE = "JS10000000";
    private SettlementDao settlementDao;
    @Resource(name = "SettlementDao")
    public void setSettlementDao(SettlementDao settlementDao) {
        this.settlementDao = settlementDao;
        super.setBaseDao(settlementDao);
    }



    public JgGridListModel getSettlementList(CommModel commModel) {
        String where= " and org.id = '"+SessionUtil.getOrgId()+"'";
        return super.getListByPageToHql(SettlementHQL.SETTLEENT_LIST,SettlementHQL.SETTLEENT_COUNT,commModel,where,null);
    }

    public SettlementDao getSettlementDao() {
        return settlementDao;
    }

    public String updateSta(String prmt, String type, Timestamp start, Timestamp end) {
//        String abc = null;
//        String stat = start.toString().substring(0,10);
//        String end1 = end.toString().substring(0,10);
//        //订单台账需要结算的数量
//        String tzhql = "select a.id from JC_LEDGER a left join a.order b left join b.orderReceivingParties d left join b.projectManagement c " +
//                " where c.name = '"+ prmt + "' and a.jsStatus = 2 and d.type = 0 and d.factLeaveTime BETWEEN '"+stat+"' and '" + end1 + "'";
//        List tzlist = settlementDao.executeQueryHql(tzhql, null);
//        //订单需要结算的数量
//        String ddhql = "select b.id from JC_ORDER b left join b.orderReceivingParties d left join b.projectManagement c " +
//                " where c.name = '"+ prmt + "' and b.jsStatus = 2 and d.type = 0 and d.factLeaveTime BETWEEN '"+stat+"' and '" + end1 + "'";
//        List  ddlist= settlementDao.executeQueryHql(ddhql, null);
//        //运单需要结算的数量
//        String ydhql = "select a.id from JC_SHIPMENT a left join a.leds c left join c.order d left join d.projectManagement c " +
//                " where c.name = '"+ prmt + "' and a.jsStatus = 2 and a.factLeaveTime BETWEEN '"+ stat +"' and '" + end1 + "'";
//        List  ydlist= settlementDao.executeQueryHql(ydhql, null);
//        //运单台账需要结算的数量
//        String ydtzhlq = "select a.id from JC_LEDGER a left join a.shipment b left join b.leds c left join c.projectManagement d" +
//                "  where d.name = '"+ prmt + "' and a.jsStatus = 2 and b.factLeaveTime BETWEEN '"+ stat +"' and '" + end1 + "'";
//        List  ydtzlist= settlementDao.executeQueryHql(ydtzhlq, null);
//         if(tzlist.size()>1 && ddlist.size()>1 && ydlist.size()>1 && ydtzlist.size()>1){
//        Settlement bean;
//        long l = settlementDao.selectCount();
//        Date date = new Date();
//        Timestamp timestamp = new Timestamp(date.getTime());
//        String str = new SimpleDateFormat("yyyy年MM月dd日").format(date);
//        String y =str.substring(0,8);
//        String number = JS_CODE;
//        String orgCode = SessionUtil.getOrg().getCode();
//        String jsName = prmt+y+"份预结算表";
//        long aa = l+1;
//        String jsCode = orgCode+number+aa;
//         String zzzhql = "select count(a.id) FROM JC_SETTLEMENT a where a.name = '"+jsName+"'";
//        long sslong = settlementDao.selectCountByHql(zzzhql, null);
//        if(sslong<=0){
//            String xmhql = "select a.id from JC_PROJECTMANAGEMENT a where a.name = '"+prmt+"'";
//            List list = settlementDao.executeQueryHql(xmhql);
//            ProjectManagementDaoImpl bean1 = SpringUtils.getBean(ProjectManagementDaoImpl.class);
//            ProjectManagement projectManagement = bean1.selectBean((Serializable) list.get(0));
//            Settlement settlement = new Settlement();
//             settlement.setCode(jsCode);
//             settlement.setName(jsName);
//             settlement.setBeginTime(start);
//             settlement.setEndTime(end);
//             settlement.setJsStatus(3);
//             settlement.setWay(2);
//             settlement.setStatus(2);
//             settlement.setOrganization(SessionUtil.getOrg());
//            settlement.setProjectManagement(projectManagement);
//              bean = (Settlement) settlementDao.savaBean(settlement);
//         }else{
//            String zzzhq = "select a.id FROM JC_SETTLEMENT a where a.name = '"+jsName+"'";
//            List list = settlementDao.executeQueryHql(zzzhq);
//            bean = (Settlement) settlementDao.selectBean((Serializable) list.get(0));
//         }
//
//        //修改订单台帐的jsStatus状态为预结算
//        Object[] tzobj = tzlist.toArray();
//        List<Ledger> ddtzlist = new ArrayList();
//        if( tzobj.length>0) {
//            for (int i = 0; i < tzobj.length; i++) {
//                String id = (String) tzobj[i];
//                OrderLedgerDaoImpl bean1 = SpringUtils.getBean(OrderLedgerDaoImpl.class);
//                Ledger ledger = bean1.selectBean(id);
//                     ledger.setJsStatus(3);
//                     ledger.setSettlement(bean);
//                ddtzlist.add(ledger);
//            }
//            for(Ledger ledger:ddtzlist){
//                 settlementDao.savaBean(ledger);
//            }
//        }
//        //修改订单的jsStatus状态为预结算
//        Object[] ddobj = ddlist.toArray();
//        List<Order> ddlist1 = new ArrayList();
//        if( ddobj.length>0) {
//            for (int i = 0; i < ddobj.length; i++) {
//                String id = (String) ddobj[i];
//                TransportOrderDaoImpl bean1 = SpringUtils.getBean(TransportOrderDaoImpl.class);
//                Order order = bean1.selectBean(id);
//                   order.setSettlement(bean);
//                   order.setJsStatus(3);
//                ddlist1.add(order);
//            }
//            for (Order order:ddlist1) {
//                 settlementDao.savaBean(order);
//            }
//        }
//        //修改运单的jsStatus状态为预结算
//        Object[] ydobj = ydlist.toArray();
//        List<Shipment> ydlist1 = new ArrayList();
//        if( ydobj.length>0) {
//            for (int i = 0; i < ydobj.length; i++) {
//                String id = (String) ydobj[i];
//                ShipmentDaoImpl bean1 = SpringUtils.getBean(ShipmentDaoImpl.class);
//                Shipment shipment = bean1.selectBean(id);
//                          shipment.setJsStatus(3);
//                          shipment.setSettlement(bean);
//                          ydlist1.add(shipment);
//            }
//            for (Shipment ship:ydlist1) {
//                 settlementDao.savaBean(ship);
//            }
//        }
//        //修改运单台帐的jsStatus状态为预结算
//        Object[] ydtzobj = ydtzlist.toArray();
//        List<Ledger> ydtzlist1 = new ArrayList();
//        if( ydtzobj.length>0) {
//            for (int i = 0; i < ydtzobj.length; i++) {
//                String id = (String) ydtzobj[i];
//                ShipmentLedgerDaoImpl bean1 = SpringUtils.getBean(ShipmentLedgerDaoImpl.class);
//                Ledger ledger = bean1.selectBean(id);
//                          ledger.setSettlement(bean);
//                          ledger.setJsStatus(3);
//                          ydtzlist1.add(ledger);
//            }
//            for (Ledger led:ydtzlist1) {
//                 settlementDao.savaBean(led);
//            }
//        }
//        String hql ="select count(a.id) from JC_SETTLEMENT a where a.code ='"+bean.getCode()+"'" ;
//        long l1 = settlementDao.selectCountByHql(hql, null);
//        if(l1>0){
//            abc ="预结算成功";
//        }else{
//            abc ="预结算失败";
//        }
//         }else{
//             abc = "此发运时间段内没有需要结算的订单、运单、台账";
//         }
//        return abc;
        return null;
    }

    public String updateSta1(String prmt, String type) {
        String abc = null;
//        //查询订单台账需要结算的数量
//        String tzhql = "select a.id from JC_LEDGER a left join a.order b left join b.projectManagement c where c.name = '"+ prmt + "' and a.jsStatus = 2";
//        List tzlist = settlementDao.executeQueryHql(tzhql, null);
//        //查询订单需要结算的数量
//        String ddhql = "select b.id from JC_ORDER b left join b.projectManagement c where c.name = '"+ prmt + "' and b.jsStatus = 2";
//        List  ddlist= settlementDao.executeQueryHql(ddhql, null);
//        //查询运单需要结算的数量
//        String ydhql = "select a.id from JC_SHIPMENT a left join a.leds c left join c.order d left join d.projectManagement c where c.name = '"+ prmt + "' and a.jsStatus = 2";
//        List  ydlist= settlementDao.executeQueryHql(ydhql, null);
//        //查询运单台账需要结算的数量
//        String ydtzhlq = "select a.id from JC_LEDGER a left join a.shipment b left join b.leds c left join c.projectManagement d" +
//                " where d.name = '"+ prmt + "' and a.jsStatus = 2";
//        List  ydtzlist= settlementDao.executeQueryHql(ydtzhlq, null);
//        if(tzlist.size()>1 && ddlist.size()>1 && ydlist.size()>1 && ydtzlist.size()>1) {
//            Settlement bean;
//            long l = settlementDao.selectCount();
//            Date date = new Date();
//            Timestamp timestamp = new Timestamp(date.getTime());
//            String str = new SimpleDateFormat("yyyy年MM月dd日").format(date);
//            String y = str.substring(0, 8);
//            String number = JS_CODE;
//            String orgCode = SessionUtil.getOrg().getCode();
//            String jsName = prmt + y + "份预结算表";
//            long aa = l + 1;
//            String jsCode = orgCode + number + aa;
//            String zzzhql = "select count(a.id) FROM JC_SETTLEMENT a where a.name = '" + jsName + "'";
//            long sslong = settlementDao.selectCountByHql(zzzhql, null);
//            if (sslong <= 0) {
//                String xmhql = "select a.id from JC_PROJECTMANAGEMENT a where a.name = '" + prmt + "'";
//                List list = settlementDao.executeQueryHql(xmhql);
//                ProjectManagementDaoImpl bean1 = SpringUtils.getBean(ProjectManagementDaoImpl.class);
//                ProjectManagement projectManagement = bean1.selectBean((Serializable) list.get(0));
//                Settlement settlement = new Settlement();
//                settlement.setCode(jsCode);
//                settlement.setJsStatus(3);
//                settlement.setName(jsName);
//                settlement.setWay(1);
//                settlement.setStatus(2);
//                settlement.setProjectManagement(projectManagement);
//                settlement.setOrganization(SessionUtil.getOrg());
//                bean = (Settlement) settlementDao.savaBean(settlement);
//            } else {
//                String zzzhq = "select a.id FROM JC_SETTLEMENT a where a.name = '" + jsName + "'";
//                List list = settlementDao.executeQueryHql(zzzhq);
//                bean = (Settlement) settlementDao.selectBean((Serializable) list.get(0));
//            }
//
//            //修改订单台帐的jsStatus状态为预结算
//            Object[] tzobj = tzlist.toArray();
//            if (tzobj.length > 0) {
//                List<Ledger> ddtzlist = new ArrayList();
//                for (int i = 0; i < tzobj.length; i++) {
//                    String id = (String) tzobj[i];
//                    OrderLedgerDaoImpl ledgerDao = SpringUtils.getBean(OrderLedgerDaoImpl.class);
//                    Ledger ledger = ledgerDao.selectBean(id);
//                    ledger.setJsStatus(3);
//                    ledger.setSettlement(bean);
//                    ddtzlist.add(ledger);
//                }
//                for (Ledger ledger : ddtzlist) {
//                    settlementDao.savaBean(ledger);
//                }
//            }
//
//            //修改订单的jsStatus状态为预结算
//            Object[] ddobj = ddlist.toArray();
//            List<Order> ddlist1 = new ArrayList();
//            if (ddobj.length > 0) {
//                for (int i = 0; i < ddobj.length; i++) {
//                    String id = (String) ddobj[i];
//                    TransportOrderDaoImpl bean1 = SpringUtils.getBean(TransportOrderDaoImpl.class);
//                    Order order = bean1.selectBean(id);
//                    order.setSettlement(bean);
//                    order.setJsStatus(3);
//                    ddlist1.add(order);
//                }
//                for (Order order : ddlist1) {
//                    settlementDao.savaBean(order);
//                }
//            }
//
//            //修改运单的jsStatus状态为预结算
//            Object[] ydobj = ydlist.toArray();
//            List<Shipment> ydlist1 = new ArrayList();
//            if (ydobj.length > 0) {
//                for (int i = 0; i < ydobj.length; i++) {
//                    String id = (String) ydobj[i];
//                    ShipmentDaoImpl bean1 = SpringUtils.getBean(ShipmentDaoImpl.class);
//                    Shipment shipment = bean1.selectBean(id);
//                    shipment.setJsStatus(3);
//                    shipment.setSettlement(bean);
//                    ydlist1.add(shipment);
//                }
//                for (Shipment ship : ydlist1) {
//                    settlementDao.savaBean(ship);
//                }
//            }
//
//            //修改运单台帐的jsStatus状态为预结算
//            Object[] ydtzobj = ydtzlist.toArray();
//            List<Ledger> ydtzlist1 = new ArrayList();
//            if (ydtzobj.length > 0) {
//                for (int i = 0; i < ydtzobj.length; i++) {
//                    String id = (String) ydtzobj[i];
//                    ShipmentLedgerDaoImpl bean1 = SpringUtils.getBean(ShipmentLedgerDaoImpl.class);
//                    Ledger ledger = bean1.selectBean(id);
//                    ledger.setSettlement(bean);
//                    ledger.setJsStatus(3);
//                    ydtzlist1.add(ledger);
//                }
//                for (Ledger led : ydtzlist1) {
//                    settlementDao.savaBean(led);
//                }
//            }
//            String hql ="select count(a.id) from JC_SETTLEMENT a where a.code ='"+bean.getCode()+"'" ;
//            long l1 = settlementDao.selectCountByHql(hql, null);
//            if(l1>0 ){
//                abc ="预结算成功";
//            }else{
//                abc ="预结算失败";
//            }
//        }else{
//            abc= "没有需要结算的订单、运单、台账";
//        }

        return abc;
    }

    public String updateAllSta(String id) {
        String abc = null;
//        Date date = new Date();
//        Timestamp timestamp = new Timestamp(date.getTime());
//       // String str = new SimpleDateFormat("yyyy年MM月dd日").format(date);
//        Settlement settlement = (Settlement) settlementDao.selectBean(id);
//                settlement.setJsStatus(1);
//                settlement.setTime(timestamp);
//        Settlement settlement1 = (Settlement) settlementDao.savaBean(settlement);
//
//        //修改订单台帐的jsStatus状态为预结算
//        String tzhql = "select a.id from JC_LEDGER a left join a.order b left join b.projectManagement c " +
//                " where c.name = '"+ settlement1.getProjectManagement().getName() + "' and a.jsStatus = 3";
//        List tzlist = settlementDao.executeQueryHql(tzhql, null);
//        Object[] tzobj = tzlist.toArray();
////        List<SelltlmentDetails> list1 = new ArrayList<SelltlmentDetails>();
//        if( tzobj.length>0) {
//            for (int i = 0; i < tzobj.length; i++) {
//                String ddtzid = (String) tzobj[i];
//                OrderLedgerDaoImpl ledgerDao = SpringUtils.getBean(OrderLedgerDaoImpl.class);
//                Ledger ledger = ledgerDao.selectBean(ddtzid);
//                String hql3 = "update JC_LEDGER d set d.jsStatus = 1 where d.id = '" + ddtzid + "'";
//                settlementDao.executeHql(hql3, null);
//            }
//        }
//        //修改订单的jsStatus状态为预结算
//        String ddhql = "select b.id from JC_ORDER b left join b.projectManagement c " +
//                " where c.name = '"+ settlement1.getProjectManagement().getName() + "' and b.jsStatus = 3";
//        List  ddlist= settlementDao.executeQueryHql(ddhql, null);
//        Object[] ddobj = ddlist.toArray();
//        if( ddobj.length>0) {
//            for (int i = 0; i < ddobj.length; i++) {
//                String ddid = (String) ddobj[i];
//                String hql3 = "update JC_ORDER d set d.jsStatus = 1 where d.id = '" + ddid + "'";
//                settlementDao.executeHql(hql3, null);
//            }
//        }
//        //修改运单的jsStatus状态为预结算
//        String ydhql = "select a.id from JC_SHIPMENT a left join a.leds c left join c.order d left join d.projectManagement c " +
//                " where c.name = '"+ settlement1.getProjectManagement().getName() + "' and a.jsStatus = 2";
//        List  ydlist= settlementDao.executeQueryHql(ydhql, null);
//        Object[] ydobj = ydlist.toArray();
//        if( ydobj.length>0) {
//            for (int i = 0; i < ydobj.length; i++) {
//                String ydid = (String) ydobj[i];
//                String hql3 = "update JC_SHIPMENT d set d.jsStatus = 1 where d.id = '" + ydid + "'";
//                settlementDao.executeHql(hql3, null);
//            }
//        }
//        //修改运单台帐的jsStatus状态为预结算
//        String ydtzhlq = "select a.id from JC_LEDGER a left join a.shipment b left join b.leds c left join c.projectManagement d" +
//                " where d.name = '"+ settlement1.getProjectManagement().getName() + "' and a.jsStatus = 3";
//        List  ydtzlist= settlementDao.executeQueryHql(ydtzhlq, null);
//        Object[] ydtzobj = ydtzlist.toArray();
//        if( ydtzobj.length>0) {
//            for (int i = 0; i < ydtzobj.length; i++) {
//                String ydtzid = (String) ydtzobj[i];
//                String ydtzhql ="update JC_LEDGER a set a.jsStatus = 1 where a.id = '" + ydtzid + "'";
//                settlementDao.executeHql(ydtzhql, null);
//            }
//        }
//
//        abc = "结算成功";
        return abc;
    }
}
