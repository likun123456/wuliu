package com.kytms.shipmentTemplate.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.ShipmentTemplate;
import com.kytms.shipmentTemplate.action.TemplateAction;
import com.kytms.shipmentTemplate.dao.TemplateDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 运单模板DAO实现类
 *
 * @author 陈小龙
 * @create 2018-04-09
 */
@Repository(value = "TemplateDao")
public class TemplateDaoImpl extends BaseDaoImpl<ShipmentTemplate> implements TemplateDao<ShipmentTemplate> {
    private final Logger log = Logger.getLogger(TemplateDaoImpl.class);//输出Log日志
}
