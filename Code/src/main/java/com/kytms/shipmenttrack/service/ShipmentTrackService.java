package com.kytms.shipmenttrack.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/8.
 */
public interface ShipmentTrackService<ShipmentTrack> extends BaseService<ShipmentTrack> {
    JgGridListModel getTrackList(CommModel commModel);

    String getTrackforString(CommModel commModel);
}
