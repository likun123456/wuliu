package com.kytms.deliveryPrice.service.impl;

import com.kytms.core.entity.DeliveryPrice;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.StringUtils;
import com.kytms.deliveryPrice.dao.DeliveryPriceDao;
import com.kytms.deliveryPrice.dao.impl.DeliveryPriceDaoImpl;
import com.kytms.deliveryPrice.service.DeliveryPriceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "DeliveryPriceService")
public class DeliveryPriceServiceImpl extends BaseServiceImpl<DeliveryPrice> implements DeliveryPriceService<DeliveryPrice> {
    private final Logger log = Logger.getLogger(DeliveryPriceServiceImpl.class);//输出Log日志
    private DeliveryPriceDao<DeliveryPrice> deliveryPriceDao;

    @Resource(name = "DeliveryPriceDao")
    public void setDeliveryPriceDao(DeliveryPriceDao<DeliveryPrice> deliveryPriceDao) {
        this.deliveryPriceDao = deliveryPriceDao;
        super.setBaseDao(deliveryPriceDao);
    }

    public JgGridListModel getList(CommModel commModel) {
        commModel.setRows(60);
        String where = " and serverZone.id = '" + commModel.getId()+"'";
        String orderBY = " ORDER BY  create_time desc";
        return super.getListByPage(commModel,where,orderBY);
    }
}
