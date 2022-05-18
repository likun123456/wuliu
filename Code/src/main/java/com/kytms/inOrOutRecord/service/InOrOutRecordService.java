package com.kytms.inOrOutRecord.service;

import com.kytms.core.entity.InOrOutRecord;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

public interface InOrOutRecordService<InOrOutRecord> extends BaseService<InOrOutRecord> {
    JgGridListModel getInOrOutRecordList(CommModel commModel);
}
