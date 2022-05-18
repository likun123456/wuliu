package com.kytms.transportorder.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

import java.sql.Timestamp;

public interface LedService<Led> extends BaseService<Led> {
    JgGridListModel getList(CommModel commModel);

    JgGridListModel getDZLedList(String id);

    String getOrderDy(CommModel commModel);

    void saveLed(String id, String qsperson, Timestamp qsTime);

    JgGridListModel getOrderYS(CommModel commModel);

    JgGridListModel getOrderYQS(CommModel commModel);

    String updateJZC(String id, Integer oldnumber, Double oldweight, Double oldvolume, Integer newnumber, Double newweight, Double newvolume);
}
