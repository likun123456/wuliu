package com.kytms.driverset.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/12/4 0004.
 */

public interface DriverService<Driver> extends BaseService<Driver> {
    JgGridListModel getDriverList(CommModel commModel);

    List<Map> getDrivea(String name);
}
