package com.kytms.shipmenttrack.service.Impl;

import com.kytms.core.entity.ShipmentTrack;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.shipmenttrack.dao.Impl.ShipmentTrackDaoImpl;
import com.kytms.shipmenttrack.dao.ShipmentTrackDao;
import com.kytms.shipmenttrack.service.ShipmentTrackService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/8.
 */
@Service(value = "ShipmentTrackService")
public class ShipmentTrackServiceImpl extends BaseServiceImpl<ShipmentTrack> implements ShipmentTrackService<ShipmentTrack> {
    private final Logger log = Logger.getLogger(ShipmentTrackServiceImpl.class);//输出Log日志
    private ShipmentTrackDao shipmentTrackDao;
    public static final String HR="-------------------------------------------------------------------------------\n";
    @Resource(name = "ShipmentTrackDao")
    public void setShipmentTrackDao(ShipmentTrackDao shipmentTrackDao) {
        super.setBaseDao(shipmentTrackDao);
        this.shipmentTrackDao = shipmentTrackDao;
    }




    public JgGridListModel getTrackList(CommModel commModel) {
        String where =" and shipment.id = '"+commModel.getId()+"'";
        String orderBy = " order by shipment.time ";
        return super.getListByPage(commModel,where,orderBy);
    }

    public String getTrackforString(CommModel commModel) {
        String where =" and shipment.id = '"+commModel.getId()+"'";
        String orderBy = " order by time ";
        List<ShipmentTrack> shipmentTracks = super.selectList(commModel, where, orderBy);
        StringBuilder sb = new StringBuilder(512);
        for (ShipmentTrack shipmentTrack:shipmentTracks) {
            sb.append("跟踪时间:"+shipmentTrack.getTime()+"\n ");
            sb.append("操作人:"+shipmentTrack.getPerson()+"\n ");
            sb.append("事件:"+shipmentTrack.getEvent()+"\n ");
            sb.append(HR);
        }
        return sb.toString();
    }
}
