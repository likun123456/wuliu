package com.kytms.customerFile.service.impl;

import com.kytms.core.entity.Customer;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.customerFile.dao.CustomerDao;
import com.kytms.customerFile.dao.impl.CustomerDaoimpl;
import com.kytms.customerFile.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 客户档案实现类
 *
 * @author 陈小龙
 * @create 2018-1-5
 */

@Service(value = "CustomerService")
public class CustomerServiceImpl  extends BaseServiceImpl<Customer> implements CustomerService<Customer> {
    private final Logger log = Logger.getLogger(CustomerServiceImpl.class);//输出Log日志
    private CustomerDao<Customer> customerDao;
    @Resource(name = "CustomerDao")
    public void setCustomerDao(CustomerDao<Customer> customerDao) {
        super.setBaseDao(customerDao);
        this.customerDao = customerDao;
    }



    public JgGridListModel getList(CommModel commModel) {
        String where = " and organization ='" + SessionUtil.getOrgId() + "'";
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where += " and status = "+commModel.getStatus();
        }
        String orderBY = " ORDER BY  create_time desc";
        return super.getListByPage(commModel,where,orderBY);
    }
}
