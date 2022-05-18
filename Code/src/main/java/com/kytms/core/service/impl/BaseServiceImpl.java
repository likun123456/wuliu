package com.kytms.core.service.impl;

import com.kytms.core.constants.Entity;
import com.kytms.core.dao.BaseDao;
import com.kytms.core.entity.BaseEntity;
import com.kytms.core.entity.Organization;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;
import com.kytms.core.utils.*;
import com.kytms.organization.dao.OrgDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 基础服务类实现
 *
 * @author 奇趣源码
 * @create 2017-11-17
 */
@Service(value = "BaseService")
public class BaseServiceImpl<T> implements BaseService<T> {
    private final Logger log = Logger.getLogger(BaseServiceImpl.class);//输出Log日志

    private BaseDao<T> baseDao;
    private static Logger logger = Logger.getLogger(BaseServiceImpl.class);
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    public static final String ORDER_CODE = "D00000";//订单
    public static final String SHUPMENT_CODE = "S00000";//运单
    public static final String DISPATCH_CODE = "P00000";//派车单
    public static final String VEHICLE_PLAN_CODE = "V00000";//车辆任务
    public static final String PRESCO_CODE = "Y00000";//预录单

    public JgGridListModel getListByPageToHql(String hql,String countHql, CommModel commModel, String where, String orderBy) {
        JgGridListModel jglm = new JgGridListModel();
        String Hql = hql;
        if (StringUtils.isEmpty(where)){
            where = " ";
        }
        if (StringUtils.isNotEmpty(commModel.getWhereName()) && StringUtils.isNotEmpty(commModel.getWhereValue())){
            where +=  " and "+commModel.getWhereName()+" like '%"+commModel.getWhereValue()+"%'";
        }
        Hql += where;
        String orderSb = "";
        if (StringUtils.isNotEmpty(orderBy)){
            orderSb = orderBy;
        }
        if (StringUtils.isNotEmpty(commModel.getSidx()) && StringUtils.isNotEmpty(commModel.getSord())){
            orderSb = " ORDER BY "+commModel.getSidx() + " " +commModel.getSord();
        }
        Hql+=orderSb;
        long statr = System.currentTimeMillis();
        List bases =  baseDao.selectByPage(Hql,(commModel.getPage()-1) * commModel.getRows(),commModel.getRows() * commModel.getPage());
        long count = baseDao.selectCountByHql(countHql,where);
        long resultTime = System.currentTimeMillis() - statr ;
        jglm.setRows(bases);
        jglm.setRecords(count);
        jglm.setCosttime(resultTime);
        jglm.setTotal((count+commModel.getRows()-1)/commModel.getRows());
        jglm.setPage(commModel.getPage());
        return jglm;
    }

    public JgGridListModel getListByPage(CommModel commModel, String where, String orderBy) {
        JgGridListModel jglm = new JgGridListModel();
        String Hql = " FROM "+baseDao.getClassName()+"  WHERE 1=1";
        if (StringUtils.isEmpty(where)){
            where = " ";
        }
        if (StringUtils.isNotEmpty(commModel.getWhereName()) && StringUtils.isNotEmpty(commModel.getWhereValue())){
            where +=  " and "+commModel.getWhereName()+" like '%"+commModel.getWhereValue()+"%'";
        }
        Hql += where;
        String orderSb = "";
        if (StringUtils.isNotEmpty(orderBy)){
            orderSb = orderBy;
        }
        if (StringUtils.isNotEmpty(commModel.getSidx()) && StringUtils.isNotEmpty(commModel.getSord())){
            orderSb = " ORDER BY "+commModel.getSidx() + " " +commModel.getSord();
        }
        Hql+=orderSb;
        long statr = System.currentTimeMillis();
        List bases =  baseDao.selectByPage(Hql,(commModel.getPage()-1) * commModel.getRows(),commModel.getRows() * commModel.getPage());
        long count = baseDao.selectCount(null,where,null);
        long resultTime = System.currentTimeMillis() - statr ;
        jglm.setRows(bases);
        jglm.setRecords(count);
        jglm.setCosttime(resultTime);
        jglm.setTotal((count+commModel.getRows()-1)/commModel.getRows());
        jglm.setPage(commModel.getPage());
        return jglm;
    }

    public void deleteBean(T id) {
        Assert.isNull(id);
        deleteBean(id);
    }

    public void deleteBean(String table, String id) {
       if (StringUtils.isEmpty(table) || StringUtils.isEmpty(id)){
           Assert.isTrue(true,"table or id is null");
       }
        String s = StringUtils.BySqlIn(id);
        String Hql = " DELETE FROM "+ table + " WHERE ID in ("+ s +")";
       baseDao.executeHql(Hql,null);

    }

    public void saveBean(T t) {
        baseDao.savaBean(t);
    }

    public boolean codeUnique(BaseEntity entity, BaseService service) {
        Assert.isNull(entity);
        String where;

        if (StringUtils.isEmpty(entity.getId())){
            where = " AND code = ?";
            String[] age = new String[]{entity.getCode()};
            long count = baseDao.selectCount((T) entity, where, age);
            if (count ==0){
                return true;
            }
        }else{
            BaseEntity t = (BaseEntity) baseDao.selectBean(EntityUtil.getEntityTableName(entity.getClass()), entity.getId());
            where = " AND code = ? and code != ?";
            String[] age = new String[]{entity.getCode(),t.getCode()};
            long count = baseDao.selectCount((T) entity, where, age);
            if (count ==0){
                return true;
            }
        }
        return false;
    }

    public <T1> T1 selectBean(Serializable id) {
        return (T1) baseDao.selectBean(id);
    }

    public <T1> T1 selectBean(String tableName, String id) {
        Assert.isNull(tableName,"tableName null");
        Assert.isNull(id,"table null");
        T t = baseDao.selectBean(tableName, id);
        return (T1) t;
    }

    public Object updateStatus(String tableNamen, String[] id, int status) {
        if (id != null || id.length !=0){
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE ");
            sb.append(tableNamen);
            sb.append(" SET status ="+status+" WHERE id in (");
            sb.append( StringUtils.BySqlIn(id));
            sb.append(")");
            baseDao.executeHql(sb.toString(),null);
        }
        return null;
    }

    public List<T> selectList(CommModel comm) {
        return this.selectList(comm,null,null);
    }

    public List<T> selectList(CommModel commModel, String where, String orderBy) {
        String Hql = " FROM "+baseDao.getClassName()+" WHERE 1=1";
        if (where != null){
            Hql += where;
        }
        if (commModel != null &&  StringUtils.isNotEmpty(commModel.getId())){
            if (!Entity.TREE_ROOT.equals(commModel.getId())){
                //Hql += " and id = '"+commModel.getId()+"'";
            }else {
                Hql += " and zid = '"+commModel.getId()+"'";
            }
        }
        if (StringUtils.isNotEmpty(commModel.getWhereName()) && StringUtils.isNotEmpty(commModel.getWhereValue())){
            Hql += " and "+commModel.getWhereName()+" like '%"+commModel.getWhereValue()+"%'";
        }
        String orderSb = "";
        if (StringUtils.isNotEmpty(orderBy)){
            orderSb =orderBy;
        }
        if (StringUtils.isNotEmpty(commModel.getSidx()) && StringUtils.isNotEmpty(commModel.getSord())){
            orderSb = " ORDER BY "+commModel.getSidx() + " " +commModel.getSord();
        }
        Hql+=orderSb;
        List bases =  baseDao.executeQueryHql(Hql);
        return bases;
    }

    public Map getEntityAll() {
        return baseDao.getEntityAll();
    }

    public List<TreeModel> getTree(String tableName, String id,String status) {
        Assert.isNull(tableName);
        Object bean;
        if (StringUtils.isEmpty(id)){
            bean = selectBean(tableName, Entity.TREE_ROOT);
        }else {
            bean = selectBean(tableName, id);
        }
        try {
            return  EntityUtil.getTreeMoidel(bean,status);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }
    /**
     * 获取订单号
     * @return
     */
    public synchronized String getOrderCode() {
        Organization organization = (Organization) baseDao.selectBean("JC_SYS_ORGANIZATION", SessionUtil.getOrgId());
        String zone = ORDER_CODE;
        String orderCode = String.valueOf(organization.getOrderCode());
        zone = zone.substring(0,zone.length()-orderCode.length()) + orderCode;
        String dataString = DateUtils.getDataString(DateUtils.yyyyMMdd);
//        long l = System.currentTimeMillis();
//        boolean b = DateUtils.belongCalendar(new Date(), new Date(), new Date());
//           if(b){
//
//           }
        String substring = dataString.substring(2, 8);
        String code=SessionUtil.getOrg().getCode()+substring+zone;
        int i = organization.getOrderCode() + 1;
        OrgDao bean = SpringUtils.getBean(OrgDao.class);
        organization.setOrderCode(i);
        bean.savaBean(organization);
        return code;
    }

    /**
     * 获取运单号
     * @return
     */
    public synchronized String getShipmentCode() {
        Organization organization = (Organization) baseDao.selectBean("JC_SYS_ORGANIZATION", SessionUtil.getOrgId());
        String zone = SHUPMENT_CODE;
        String orderCode = String.valueOf(organization.getShipmentCode());
        zone = zone.substring(0,zone.length()-orderCode.length()) + orderCode;
        String dataString = DateUtils.getDataString(DateUtils.yyyyMMdd);
        String substring = dataString.substring(2, 8);
        String code=SessionUtil.getOrg().getCode()+substring+zone;
        int i = organization.getShipmentCode() + 1;
        OrgDao bean = SpringUtils.getBean(OrgDao.class);
        organization.setShipmentCode(i);
        bean.savaBean(organization);
        return code;
    }

    /**
     * 获取派车单号
     * @return
     */
    public synchronized String getDispatchCode() {
        Organization organization = (Organization) baseDao.selectBean("JC_SYS_ORGANIZATION", SessionUtil.getOrgId());
        String zone = DISPATCH_CODE;
        String orderCode = String.valueOf(organization.getDispatchCode());
        zone = zone.substring(0,zone.length()-orderCode.length()) + orderCode;
        String dataString = DateUtils.getDataString(DateUtils.yyyyMMdd);
        String substring = dataString.substring(2, 8);
        String code=SessionUtil.getOrg().getCode()+substring+zone;
        int i = organization.getDispatchCode() + 1;
        OrgDao bean = SpringUtils.getBean(OrgDao.class);
        organization.setDispatchCode(i);
        bean.savaBean(organization);
        return code;
    }
    /**
     * 获取任务号
     * @return
     */
    public synchronized String getVehiclePlanCode() {
        Organization organization = (Organization) baseDao.selectBean("JC_SYS_ORGANIZATION", SessionUtil.getOrgId());
        String zone = VEHICLE_PLAN_CODE;
        String orderCode = String.valueOf(organization.getVehiclePlanCode());
        zone = zone.substring(0,zone.length()-orderCode.length()) + orderCode;
        String code=SessionUtil.getOrg().getCode()+DateUtils.getDataString(DateUtils.yyyyMMdd)+zone;
        int i = organization.getVehiclePlanCode() + 1;
        OrgDao bean = SpringUtils.getBean(OrgDao.class);
        organization.setVehiclePlanCode(i);
        bean.savaBean(organization);
        return code;
    }
    /**
     * 预录单号
     * @return
     */
    public synchronized String getPrescoCode() {
        Organization organization = (Organization) baseDao.selectBean("JC_SYS_ORGANIZATION", SessionUtil.getOrgId());
        String zone = PRESCO_CODE;
        String prescoCode = String.valueOf(organization.getPrescoCode());
        zone = zone.substring(0,zone.length()-prescoCode.length()) + prescoCode;
        String dataString = DateUtils.getDataString(DateUtils.yyyyMMdd);
        String substring = dataString.substring(2, 8);
        String code=SessionUtil.getOrg().getCode()+substring+zone;
        int i = organization.getPrescoCode() + 1;
        OrgDao bean = SpringUtils.getBean(OrgDao.class);
        organization.setPrescoCode(i);
        bean.savaBean(organization);
        return code;
    }


}
