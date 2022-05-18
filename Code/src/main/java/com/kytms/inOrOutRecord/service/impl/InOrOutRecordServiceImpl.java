package com.kytms.inOrOutRecord.service.impl;

import com.kytms.core.entity.InOrOutRecord;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.inOrOutRecord.InOrOutRecordSql;
import com.kytms.inOrOutRecord.dao.InOrOutRecordDao;
import com.kytms.inOrOutRecord.dao.impl.InOrOutRecordDaoImpl;
import com.kytms.inOrOutRecord.service.InOrOutRecordService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2018/10/22
 */
@Service(value = "InOrOutRecordService")
public class InOrOutRecordServiceImpl extends BaseServiceImpl<InOrOutRecord> implements InOrOutRecordService<InOrOutRecord> {
    private final Logger log = Logger.getLogger(InOrOutRecordServiceImpl.class);//输出Log日志
    private InOrOutRecordDao<InOrOutRecord> inOrOutRecordDao;

    @Resource(name = "InOrOutRecordDao")
    public void setInOrOutRecordDao(InOrOutRecordDao<InOrOutRecord> inOrOutRecordDao) {
        this.inOrOutRecordDao = inOrOutRecordDao;
        super.setBaseDao(inOrOutRecordDao);
    }

    public JgGridListModel getInOrOutRecordList(CommModel commModel) {
        String where = "";
        if (commModel.getStatus() == null) {
            where += " and a.status =1 and b.id='"+ SessionUtil.getOrgId()+"'";
        }else{
            where +=" and a.status="+commModel.getStatus();
        }
//        if (StringUtils.isNotEmpty(commModel.getStatus())){
//            where = " and a.status != ";
//        }
        String orderBY = " ORDER BY  a.create_Time desc";
        return super.getListByPageToHql(InOrOutRecordSql.INOROUT_LIST,InOrOutRecordSql.INOROUT_COUNT, commModel, where, orderBY);
        //return super.getListByPage(commModel,where,orderBY);
    }
}
