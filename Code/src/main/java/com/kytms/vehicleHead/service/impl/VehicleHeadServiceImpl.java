package com.kytms.vehicleHead.service.impl;

import com.kytms.core.entity.Driver;
import com.kytms.core.entity.VehicleHead;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.driverset.dao.DriverDao;
import com.kytms.vehicleHead.action.VehicleHeadAction;
import com.kytms.vehicleHead.dao.VehicleHeadDao;
import com.kytms.vehicleHead.service.VehicleHeadService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车头档案服务层实现类
 *
 * @author 陈小龙
 * @create 2018-01-12
 */
@Service(value = "VehicleHeadService")
public class VehicleHeadServiceImpl  extends BaseServiceImpl<VehicleHead> implements VehicleHeadService<VehicleHead> {
    private final Logger log = Logger.getLogger(VehicleHeadServiceImpl.class);//输出Log日志
    private VehicleHeadDao<VehicleHead> vehicleHeadDao;
    private DriverDao driverDao;

    @Resource
    public void setDriverDao(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Resource(name = "VehicleHeadDao")
    public void setVehicleHeadDao(VehicleHeadDao<VehicleHead> vehicleHeadDao) {
        super.setBaseDao(vehicleHeadDao);
        this.vehicleHeadDao = vehicleHeadDao;
    }





    public JgGridListModel getList(CommModel commModel) {
        String  where= " and organization.id='"+SessionUtil.getOrgId()+"'";
        if(StringUtils.isNotEmpty(commModel.getWhereValue())){
            if(commModel.getWhereValue().equals("1")){
                where= " and organization1.id='"+SessionUtil.getOrgId()+"'";
            }
        }
        if(StringUtils.isNotEmpty(commModel.getStatus())){
            where+=" and status='"+commModel.getStatus()+"'";
        }
        String orderBY = "  ORDER BY  create_Time desc";
        return super.getListByPage(commModel,where,orderBY);
    }

    public void saveVehicleHead(VehicleHead vehicleHead) {
        String sjid = vehicleHead.getDriverss().getId();
        Driver driver = (Driver) driverDao.selectBean(sjid);
                  vehicleHead.setDriverss(driver);
                 vehicleHead.setDriver(driver.getName());
                 vehicleHead.setIphone(driver.getIphone1());
        VehicleHead vehicleHead1 = vehicleHeadDao.savaBean(vehicleHead);

        //新添车头时：
//        if (StringUtils.isEmpty(vehicleHead.getId())){
//        CarriagePosition carriagePosition = new CarriagePosition();
//        carriagePosition.setCurrentAddress("等候更新地址中...");
//        carriagePosition.setDetailedAddress("等候更新地址中...");
//        carriagePosition.setVehicleHead(vehicleHead1);
//        carriagePosition.setStatus(3);
//        carriagePosition.setOrganization(vehicleHead.getOrganization());
//        carriagePositionDao.savaBean(carriagePosition);
//        }
    }

//    public List<Remind> getRemind(String vId){
//        StringBuffer  result=new StringBuffer();
//        List<Remind> remindList=vehicleHeadDao.getRemind(vId);
//        Date date=new Date();
//        for(Remind remind:remindList){
//            String remindName=remind.getName();
//            Timestamp endTime=remind.getEffectiveTime();
//            Timestamp nowTIime =new Timestamp(date.getTime());
//            long to =endTime.getTime()-nowTIime.getTime();
//            int days= (int)to/(1000*60*60*24);
//            if(days<30){
//                result.append(remindName+"还剩不足30天到期");
//            }else if(days<=3){
//                result.append(remindName+"还剩不足3天到期!!!!");
//            }else if(days<=0){
//                result.append(remindName+"已过期!!!!");
//            }
//        }
//        return remindList;
//    }

    public List getOrgVelList() {
        String hql="select org.name,count(jvh.code) from JC_VEHICLE_HEAD jvh left join jvh.organization org group by org.name ";
       List list = vehicleHeadDao.executeQueryHql(hql);

        return list;
    }

    public List selectCont() {
        String hql = "select count(jvh.code) from JC_VEHICLE_HEAD jvh left join jvh.carriagePosition vps left join jvh.organization org " +
                " where org.id ='"+SessionUtil.getOrgId()+"' and vps.status != 1  ";
        List list = vehicleHeadDao.executeQueryHql(hql);

        return list;
    }
}
