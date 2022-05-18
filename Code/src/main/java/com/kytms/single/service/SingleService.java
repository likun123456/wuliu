package com.kytms.single.service;

import com.kytms.core.entity.LedgerDetail;
import com.kytms.core.model.CommModel;
import com.kytms.core.service.BaseService;

import java.util.List;


public interface SingleService<Single> extends BaseService<Single> {

    com.kytms.core.entity.Single saveSingle(Single sing);

    List SingleDetail(String id);

    com.kytms.core.entity.Single saveSingleAbnormal(Single s, String abnormal);

    void trimFeeType(String id,LedgerDetail ledgerDetail);

    List getNotStowage(CommModel commModel);

    void addOrder(String id, String data);

    void delOrder(String id, String delIds);

    List getStowage(String id);

    int startExe(CommModel commModel);

    int endExe(CommModel commModel);

    List getMapData(CommModel commModel);

    String getMk(String id);
}
