package com.kytms.feeTypeContrast.service.impl;

import com.kytms.core.entity.FeeTypeContrast;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.feeTypeContrast.dao.FeeTypeContrastDao;
import com.kytms.feeTypeContrast.service.FeeTypeContrastService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2019/3/15
 */
@Service(value = "FeeTypeContrastService")
public class FeeTypeContrastServiceImpl extends BaseServiceImpl<FeeTypeContrast> implements FeeTypeContrastService<FeeTypeContrast> {
    private FeeTypeContrastDao<FeeTypeContrast> feeTypeContrastDao;

    private final Logger log = Logger.getLogger(FeeTypeContrastServiceImpl.class);//输出Log日志

    @Resource(name = "FeeTypeContrastDao")
    public void setFeeTypeContrastDao(FeeTypeContrastDao<FeeTypeContrast> feeTypeContrastDao) {
        this.feeTypeContrastDao = feeTypeContrastDao;
        super.setBaseDao(feeTypeContrastDao);
    }

    public void saveFeeTypeCon(FeeTypeContrast feeTypeContrast) {
         feeTypeContrast.setOrganization(SessionUtil.getOrg());
           feeTypeContrastDao.savaBean(feeTypeContrast);
    }

    public JgGridListModel getFtcList(CommModel commModel) {
        return super.getListByPage(commModel,null,null);
    }
}
