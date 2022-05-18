package com.kytms.feeTypeContrast.service;

import com.kytms.core.entity.FeeTypeContrast;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

public interface FeeTypeContrastService<FeeTypeContrast> extends BaseService<FeeTypeContrast> {

    void saveFeeTypeCon(FeeTypeContrast feeTypeContrast);

    JgGridListModel getFtcList(CommModel commModel);
}
