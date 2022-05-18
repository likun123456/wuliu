package com.kytms.presco.service;

import com.kytms.core.entity.Presco;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

public interface PrescoService<Presco> extends BaseService<Presco> {
    JgGridListModel getList(CommModel commModel);

    void savePresco(Presco presco);

    String savaToOrder(Presco presco);

    void delBean(String id);

    String savefzxj(String id);
}
