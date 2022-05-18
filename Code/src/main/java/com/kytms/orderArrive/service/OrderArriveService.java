package com.kytms.orderArrive.service;

import com.kytms.core.entity.OrderArrive;
import com.kytms.core.service.BaseService;

import java.sql.Timestamp;

public interface OrderArriveService<OrderArrive>  extends BaseService<OrderArrive> {

    void saveOrderArrive1(String date, String ids);

}
