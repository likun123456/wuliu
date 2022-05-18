package com.kytms.verification.service;

import com.kytms.core.entity.VerificationRecord;
import com.kytms.core.service.BaseService;

public interface VerificationRecordService<VerificationRecord> extends BaseService<VerificationRecord> {
    com.kytms.core.entity.VerificationRecord saveVerification(VerificationRecord verificationRecord);

}
