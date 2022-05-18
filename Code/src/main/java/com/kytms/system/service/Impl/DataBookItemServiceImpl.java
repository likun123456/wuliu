package com.kytms.system.service.Impl;

import com.kytms.core.entity.DictionaryDetail;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.system.dao.DataBookItemDao;
import com.kytms.system.service.DataBookItemService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/23 0023.
 */
@Service(value = "DataBookItemService")
public class DataBookItemServiceImpl extends BaseServiceImpl<DictionaryDetail> implements DataBookItemService<DictionaryDetail> {
    private final Logger log = Logger.getLogger(DataBookItemServiceImpl.class);//输出Log日志
    private final Boolean COMPLETE = true;
    private final Boolean IS_EXPANED=true;
    private final Boolean SHOW_CHECK=true;
    private DataBookItemDao dataBookItemDao;
    @Resource(name = "DataBookItemDao")
    public void setDataBookItemDao(DataBookItemDao dataBookItemDao) {
        super.setBaseDao(dataBookItemDao);
        this.dataBookItemDao = dataBookItemDao;
    }




   /* public List<DictionaryDetail> getDataBookItemList(CommModel commModel) {
        Object o = dataBookItemDao.selectBean(commModel.getId());
        return dataBookItemDao.selectBean(commModel.getId());
    }*/

}
