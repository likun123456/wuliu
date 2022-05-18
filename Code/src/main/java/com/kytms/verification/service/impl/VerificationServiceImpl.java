package com.kytms.verification.service.impl;

import com.kytms.core.entity.VerificationZb;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.verification.dao.VerificationDao;
import com.kytms.verification.service.VerificationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2018/11/2
 */
@Service(value = "VerificationService")
public class VerificationServiceImpl extends BaseServiceImpl<VerificationZb> implements VerificationService<VerificationZb> {
    private final Logger log = Logger.getLogger(VerificationServiceImpl.class);//输出Log日志
     private VerificationDao<VerificationZb> verificationDao;

     @Resource(name = "VerificationDao")
    public void setVerificationDao(VerificationDao<VerificationZb> verificationDao) {
        this.verificationDao = verificationDao;
        super.setBaseDao(verificationDao);
    }
}
