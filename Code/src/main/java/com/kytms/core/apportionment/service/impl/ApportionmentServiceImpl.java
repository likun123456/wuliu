package com.kytms.core.apportionment.service.impl;

import com.kytms.core.apportionment.dao.ApportionmentDao;
import com.kytms.core.apportionment.service.ApportionmentService;
import com.kytms.core.entity.Apportionment;
import com.kytms.core.service.impl.BaseServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2019/1/2
 */
@Service(value = "ApportionmentService")
public class ApportionmentServiceImpl extends BaseServiceImpl<Apportionment> implements ApportionmentService<Apportionment>{
     private ApportionmentDao apportionmentDao;
    private Logger log = Logger.getLogger(ApportionmentServiceImpl.class);//输出Log日志

     @Resource(name = "ApportionmentDao")
    public void setApportionmentDao(ApportionmentDao apportionmentDao) {
        this.apportionmentDao = apportionmentDao;
    }
}
