package com.kytms.shipment.service;

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.entity.Apportionment;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/8.
 */
public interface ShipmentService<Shipment> extends BaseService<Shipment> {
    JgGridListModel getShipmentList(CommModel commModel);

    Shipment saveShipment(Shipment shipment);
    //异常修改
    Shipment saveAbnormalShipment(Shipment shipment,String text);

    void addLed(String ledId, String shipment);

    void delLed(String ledId, String shipment);

    List getAddressInfo(String id);

    /**
     * 运单确认
     * @param id
     * @return
     * @throws MessageException
     */
    Shipment confirmShipment(String id) throws MessageException;

    void updateRegTime(String id, Timestamp date,String shipment);

    /**
     * 批量确认运单
     * @param id
     */
    void confirmShipmentAll(String id) throws MessageException;

    Shipment cancelShipment(String id);

    JgGridListModel getRVShipmentList(CommModel commModel);

    JgGridListModel getDZShipmentList(CommModel commModel);
    String getOrg();


    void addLedyc(String ledId, String shipment);

    void delLedyc(String ledId, String shipment);

    String saveff(String str);

    String getOrgww(String aaa);

    void updateYDTime(String id, Timestamp date, String shipment);

    JgGridListModel getLedgerList(CommModel commModel);

    void confirmLedger(String id,String time) throws MessageException;

    void backConfirmLedger(String id) throws MessageException;

}
