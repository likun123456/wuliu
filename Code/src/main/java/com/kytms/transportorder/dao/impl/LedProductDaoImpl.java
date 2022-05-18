package com.kytms.transportorder.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.LedProduct;
import com.kytms.transportorder.dao.LedProductDao;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 陈小龙
 * 分段订单货品明细
 *  2018-03-23
 */
@Repository(value = "LedProductDao")
public class LedProductDaoImpl extends BaseDaoImpl<LedProduct> implements LedProductDao<LedProduct> {
}
