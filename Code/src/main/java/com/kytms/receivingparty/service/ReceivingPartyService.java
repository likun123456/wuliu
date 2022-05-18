package com.kytms.receivingparty.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;


/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/9.
 */
public interface ReceivingPartyService<ReceivingParty> extends BaseService<ReceivingParty> {
    JgGridListModel getReceivingPartyList(CommModel commModel);
}
