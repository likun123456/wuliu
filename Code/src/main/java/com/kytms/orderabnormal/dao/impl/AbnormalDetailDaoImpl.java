package com.kytms.orderabnormal.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.AbnormalDetail;
import com.kytms.orderabnormal.dao.AbnormalDetailDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 订单异常Dao实现类
 *
 * @author 陈小龙
 * @create 2018-01-11
 */
@Repository(value = "AbnormalDetailDao")
public class AbnormalDetailDaoImpl extends BaseDaoImpl<AbnormalDetail> implements AbnormalDetailDao<AbnormalDetail> {
    private final Logger log = Logger.getLogger(AbnormalDetailDaoImpl.class);//输出Log日志
}

