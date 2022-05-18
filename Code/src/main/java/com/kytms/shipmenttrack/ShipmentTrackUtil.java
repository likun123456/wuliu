package com.kytms.shipmenttrack;

import com.kytms.core.entity.Shipment;
import com.kytms.core.entity.ShipmentTrack;
import com.kytms.core.utils.DateUtils;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.SpringUtils;
import com.kytms.shipmenttrack.dao.Impl.ShipmentTrackDaoImpl;
import com.kytms.shipmenttrack.dao.ShipmentTrackDao;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 工具类
 *
 * @author
 * @create 2018-01-26
 */
public abstract class ShipmentTrackUtil {


    /**
     * 添加一个事件
     * @param shipment
     * @param event
     * @return
     */
    public static ShipmentTrack addTrack(Shipment shipment,String event){
        ShipmentTrackDao shipmentTrackDao= SpringUtils.getBean(ShipmentTrackDaoImpl.class);
        ShipmentTrack shipmentTrack = new ShipmentTrack();
        shipmentTrack.setShipment(shipment);
        shipmentTrack.setTime(DateUtils.getTimestamp());
        shipmentTrack.setEvent(event);
        shipmentTrack.setType(0);
        shipmentTrack.setPerson(SessionUtil.getUserName());
        Object o = shipmentTrackDao.savaBean(shipmentTrack);
        return (ShipmentTrack) o;
    }

}
