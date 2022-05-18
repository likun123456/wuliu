package com.kytms.vehicleArrive.service.impl;

import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.shipment.dao.ShipmentDao;
import com.kytms.shipmenttrack.ShipmentTrackUtil;
import com.kytms.transportorder.OrderTrackUtil;
import com.kytms.transportorder.dao.LedDao;
import com.kytms.transportorder.dao.TransportOrderDao;
import com.kytms.vehicleArrive.VehicleDzInfoSql;
import com.kytms.vehicleArrive.action.VehicleArriveAction;
import com.kytms.vehicleArrive.dao.VehicleArriveDao;
import com.kytms.vehicleArrive.service.VehicleArriveService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:sundezeng
 * @Date:2018/10/24
 */
@Service(value = "VehicleArriveService")
public class VehicleArriveServiceImpl extends BaseServiceImpl<VehicleArrive> implements VehicleArriveService<VehicleArrive> {
    private final Logger log = Logger.getLogger(VehicleArriveServiceImpl.class);//输出Log日志
    private VehicleArriveDao<VehicleArrive> vehicleArriveDao;
    private ShipmentDao<Shipment> shipmentDao;
    private TransportOrderDao<Order> transportOrderDao;
    private LedDao<Led> ledLedDao;

    @Resource(name = "TransportOrderDao")
    public void setTransportOrderDao(TransportOrderDao<Order> transportOrderDao) {
        this.transportOrderDao = transportOrderDao;
    }
    @Resource(name = "LedDao")
    public void setLedLedDao(LedDao<Led> ledLedDao) {
        this.ledLedDao = ledLedDao;
    }

    @Resource(name = "ShipmentDao")
    public void setShipmentDao(ShipmentDao<Shipment> shipmentDao) {
        this.shipmentDao = shipmentDao;
    }

    @Resource(name = "VehicleArriveDao")
    public void setVehicleArriveDao(VehicleArriveDao<VehicleArrive> vehicleArriveDao) {
        this.vehicleArriveDao = vehicleArriveDao;
        super.setBaseDao(vehicleArriveDao);
    }


    public JgGridListModel getList(CommModel commModel) {
        return null;
    }

    /**
     * 车辆到站
     * @param vehicleArrive
     * @return
     */
    public VehicleArrive saveVehicleArrive(VehicleArrive vehicleArrive) {
           if(vehicleArrive.getOrganization() == null){
                vehicleArrive.setOrganization(SessionUtil.getOrg());
                vehicleArrive.setWhichStation(SessionUtil.getOrg().getName());
                vehicleArrive.setArriveTime(vehicleArrive.getVehicleTime());
           }
       VehicleArrive vehicleArrive1= vehicleArriveDao.savaBean(vehicleArrive);

       Shipment shipment= shipmentDao.selectBean(vehicleArrive1.getShipment().getId());
       if(!SessionUtil.getOrgId().equals(shipment.getNextOrganization().getId())){
           throw new MessageException("即将到达不是本站，车辆不到到站");
       }
              if(shipment.getToOrganization().getName().equals(SessionUtil.getOrg().getName())){
                  shipment.setStatus(5);//运抵
                  shipment.setFactArriveTime(vehicleArrive.getVehicleTime());//运抵时间
              }else{
                  List<BerthStand> berthStands = shipment.getBerthStand();
                  if(berthStands != null){
                      for (BerthStand berthStand:berthStands) {
                          String id = berthStand.getOrganization().getId();
                          if(id.equals(shipment.getNextOrganization().getId())){
                              berthStand.setIsArrive(1);
                              break;
                          }
                      }
                  }
                  shipment.setStatus(6);//车辆到站
              }
        shipment.setNewOrganization(SessionUtil.getOrg());
        shipment.setNextOrganization(null);
        //处理跟踪
        ShipmentTrackUtil.addTrack(shipment,"车辆已经到达-"+SessionUtil.getOrg().getName());
        List<Led> leds = shipment.getLeds();
        for (Led led:leds) {
            OrderTrackUtil.addTrack(led.getOrder(),"您的分段单号:"+led.getCode()+"已经到达:"+SessionUtil.getOrg().getName());
        }
        shipmentDao.savaBean(shipment);
        return vehicleArrive1;
    }

    //车辆到站时间
    public void saveShippingTime(VehicleArrive vehicleArrive) {
         if(vehicleArrive.getOperateTime()!= null){
           Shipment shipment = shipmentDao.selectBean(vehicleArrive.getShipment().getId());
              List<BerthStand> berthStands = shipment.getBerthStand();
                 if(berthStands.size()>0){
                     for (BerthStand berthStand:berthStands) {
                          shipment.setNewOrganization(SessionUtil.getOrg());
                          shipment.setNextOrganization(berthStand.getOrganization());
                     }
                 }else{
                     shipment.setStatus(4);
                     //修改分段运单状态
                     List<Led> led = shipment.getLeds();
                     for (Led led1:led) {
                         led1.setStatus(4);
                         ledLedDao.savaBean(led1);
                         //修改订单状态
                         Order order= led1.getOrder();
                         order.setStatus(4);
                         transportOrderDao.savaBean(order);
                     }
                     shipmentDao.savaBean(shipment);
                 }
         }

    }

    public void saveArriveTime(VehicleArrive vehicleArrive) {
        if(vehicleArrive.getArriveTime()!= null){
            Shipment shipment = shipmentDao.selectBean(vehicleArrive.getShipment().getId());
            shipment.setStatus(5);
            //修改分段运单状态
            List<Led> led = shipment.getLeds();
            for (Led led1:led) {
                led1.setStatus(5);
                ledLedDao.savaBean(led1);
                //修改订单状态
                Order order= led1.getOrder();
                order.setStatus(5);
                transportOrderDao.savaBean(order);
            }
            shipmentDao.savaBean(shipment);
        }

    }
/**
 * 车辆到站记录
 * */
    public JgGridListModel getDZShipmentInfo(CommModel commModel) {
        String where =" and org.id='"+SessionUtil.getOrgId()+"'";
        String orderBy="";
        return super.getListByPageToHql(VehicleDzInfoSql.VEHICLEDZ_LIST,VehicleDzInfoSql.VEHICLEDZ_COUNT,commModel,where,orderBy);
    }
}
