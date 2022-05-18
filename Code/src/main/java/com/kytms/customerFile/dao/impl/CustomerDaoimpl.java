package com.kytms.customerFile.dao.impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.Customer;
import com.kytms.customerFile.action.CustomerAction;
import com.kytms.customerFile.dao.CustomerDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 客户档案DAO实现类
 *
 * @author 陈小龙
 * @create 2018-1-5
 */

@Repository(value = "CustomerDao")
public class CustomerDaoimpl extends BaseDaoImpl<Customer> implements CustomerDao<Customer>{
    private final Logger log = Logger.getLogger(CustomerDaoimpl.class);//输出Log日志
}

