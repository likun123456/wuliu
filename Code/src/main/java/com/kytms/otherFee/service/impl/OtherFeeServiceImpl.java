package com.kytms.otherFee.service.impl;

import com.kytms.core.entity.OtherFee;
import com.kytms.core.service.BaseService;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.otherFee.dao.OtherFeeDao;
import com.kytms.otherFee.dao.impl.OtherFeeDaoImpl;
import com.kytms.otherFee.service.OtherFeeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2018/11/6
 */
@Service(value = "OtherFeeService")
public class OtherFeeServiceImpl extends BaseServiceImpl<OtherFee> implements OtherFeeService<OtherFee> {
    private final Logger log = Logger.getLogger(OtherFeeServiceImpl.class);//输出Log日志
    private OtherFeeDao<OtherFee> otherFeeDao;

    @Resource(name = "OtherFeeDao")
    public void setOtherFeeDao(OtherFeeDao<OtherFee> otherFeeDao) {
        this.otherFeeDao = otherFeeDao;
        super.setBaseDao(otherFeeDao);
    }
}
