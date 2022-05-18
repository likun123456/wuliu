package com.kytms.orderabnormal.service;


import com.kytms.core.entity.AbnormalDetail;
import com.kytms.core.model.CommModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 订单异常服务层接口
 *
 * @author 陈小龙
 * @create 2018-01-11
 */
public interface AbnormalService<Abnormal> extends BaseService<Abnormal> {
    Object getOrderAbnormalList(CommModel commModel);
    Object saveAbnormal(Object o);
    public List<AbnormalDetail> getAbnormalList(Object source, Object targer);
}
