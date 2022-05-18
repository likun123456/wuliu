package com.kytms.system.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.DictionaryDetail;
import com.kytms.system.dao.DataBookItemDao;
import org.springframework.stereotype.Repository;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/23 0023.
 */

@Repository(value = "DataBookItemDao")
public class DataBookItemDaoImpl extends BaseDaoImpl<DictionaryDetail> implements DataBookItemDao<DictionaryDetail> {
}
