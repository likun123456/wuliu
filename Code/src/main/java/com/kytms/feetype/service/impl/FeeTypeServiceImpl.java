package com.kytms.feetype.service.impl;

import com.kytms.core.entity.FeeType;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.feetype.dao.FeeTypeDao;
import com.kytms.feetype.dao.impl.FeeTypeDaoImpl;
import com.kytms.feetype.service.FeeTypeSerivce;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 费用类型service
 * @author 奇趣源码
 * @create 2018-01-04
 */
@Service(value = "FeeTypeService")
public class FeeTypeServiceImpl extends BaseServiceImpl<FeeType>  implements FeeTypeSerivce<FeeType>  {
    private final Logger log = Logger.getLogger(FeeTypeServiceImpl.class);//输出Log日志
    private FeeTypeDao feeTypeDao;
    @Resource(name = "FeeTypeDao")
    public void setFeeTypeDao(FeeTypeDao feeTypeDao) {
        super.setBaseDao(feeTypeDao);
        this.feeTypeDao = feeTypeDao;
    }




    public JgGridListModel selectFeeTypeList(CommModel commModel) {
        String orderBY = " ORDER BY status desc ,name desc";
        return super.getListByPage(commModel,null,orderBY);
    }

    public List selectOrderFeeTypeList(CommModel commModel, String where, Object o) {
        String hql = "SELECT A.id,A.name  FROM JC_FEE_TYPE A  WHERE 1=1" +where;
        List list = feeTypeDao.executeQueryHql(hql);
        return   list;
    }

    public List<FeeType> selectListByHql(String Hql) {
        return  feeTypeDao.executeQueryHql(Hql);
    }
}
