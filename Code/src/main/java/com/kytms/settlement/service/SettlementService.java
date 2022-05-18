package com.kytms.settlement.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

import java.sql.Timestamp;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/5/24.
 */
public interface SettlementService<Settlement> extends BaseService<Settlement> {
    String updateSta(String prmt, String type, Timestamp start, Timestamp end);

    String updateSta1(String prmt, String type);

    JgGridListModel getSettlementList(CommModel commModel);

    String updateAllSta(String id);
}
