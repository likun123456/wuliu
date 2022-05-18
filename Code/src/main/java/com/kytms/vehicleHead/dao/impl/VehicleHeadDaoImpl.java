package com.kytms.vehicleHead.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.VehicleHead;
import com.kytms.vehicleHead.dao.VehicleHeadDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车头档案DAO层实现类
 *
 * @author 陈小龙
 * @create 2018-01-12
 */
@Repository(value = "VehicleHeadDao")
public class VehicleHeadDaoImpl extends BaseDaoImpl<VehicleHead> implements VehicleHeadDao<VehicleHead> {
//    public List<Remind> getRemind(String vId){
//        String Hql = "FROM JC_REMIND WHERE 1=1 and JC_VEHICLE_HEAD_ID = ?";
//        Query query = openSession().createQuery(Hql);
//        query.setParameter(0,vId);
//        List<Remind> remindList=query.list();
//        return  remindList;
//    }
}
