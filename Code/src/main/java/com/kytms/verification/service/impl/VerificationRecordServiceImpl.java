package com.kytms.verification.service.impl;

import com.kytms.core.entity.VerificationRecord;
import com.kytms.core.entity.VerificationZb;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.verification.action.VerificationAction;
import com.kytms.verification.dao.VerificationDao;
import com.kytms.verification.dao.VerificationRecordDao;
import com.kytms.verification.service.VerificationRecordService;
import com.kytms.verification.service.VerificationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2018/11/3
 */
@Service(value = "VerificationRecordService")
public class VerificationRecordServiceImpl extends BaseServiceImpl<VerificationRecord> implements VerificationRecordService<VerificationRecord> {
    private final Logger log = Logger.getLogger(VerificationRecordServiceImpl.class);//输出Log日志
     private VerificationRecordDao<VerificationRecord> verificationRecordDao;
     private VerificationDao<VerificationZb> verificationDao;

    @Resource(name = "VerificationDao")
    public void setVerificationDao(VerificationDao<VerificationZb> verificationDao) {
        this.verificationDao = verificationDao;
    }

    @Resource(name = "VerificationRecordDao")
    public void setVerificationRecordDao(VerificationRecordDao<VerificationRecord> verificationRecordDao) {
        this.verificationRecordDao = verificationRecordDao;
        super.setBaseDao(verificationRecordDao);
    }

    public VerificationRecord saveVerification(VerificationRecord verificationRecord) {
        VerificationZb verificationZb = verificationDao.selectBean(verificationRecord.getVerificationZb().getId());
                  if(verificationRecord.getZmoney()-verificationRecord.getYhxmoney() == 0){
                      verificationZb.setStatus(1);
                      verificationZb.setHxStatus(1);
                      verificationRecord.setHxtype(1);
                  }else if(verificationRecord.getZmoney()-verificationRecord.getYhxmoney()>0){
                      verificationZb.setStatus(2);
                      verificationZb.setHxStatus(2);
                      verificationRecord.setHxtype(2);
                  }
        verificationRecord.setYhxmoney(verificationRecord.getYhxmoney());
        verificationRecord.setWhxmoney(verificationRecord.getWhxmoney());
        VerificationRecord verificationRecord1 = verificationRecordDao.savaBean(verificationRecord);
        String where = " and JC_VERIFICATIONZB_ID='"+verificationZb.getId()+"'";
        long hecx = verificationRecordDao.selectCount(verificationRecord1, where, null);
        verificationZb.setHxMoney(verificationRecord.getYhxmoney());
        verificationZb.setWhxMoney(verificationRecord.getWhxmoney());
        verificationZb.setOrganization(SessionUtil.getOrg());
        verificationZb.setHxCount((int) hecx);
        verificationDao.savaBean(verificationZb);

        return verificationRecord1;
    }
}
