package com.kytms.customerFile.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Customer;
import com.kytms.core.entity.ObjectUtils;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.customerFile.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 客户档案控制层
 *
 * @author 陈小龙
 * @create 2018-1-5
 */

@Controller
@RequestMapping("/customer")
public class CustomerAction extends BaseAction {
    private final Logger log = Logger.getLogger(CustomerAction.class);//输出Log日志
    private CustomerService<Customer> customerService;
    @Resource
    public void setCustomerService(CustomerService<Customer> customerService) {
        this.customerService = customerService;
    }



    @RequestMapping(value = "/getCustomerList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getList(CommModel commModel){
        JgGridListModel jgGridListModel =customerService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/saveCustomer", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveCustomer(Customer customer){
        ReturnModel returnModel = getReturnModel();
        //唯一性校验
        returnModel.codeUniqueAndNull(customer,customerService);
        Boolean isResult=returnModel.isResult();
        if(!isResult){
            return returnModel.toJsonString();
        }
        //开始非空校验
        if (ObjectUtils.isEmpty(customer.getJcRegistration())){
            returnModel.addError("jcRegistration","注册公司不能为空");
            return returnModel.toJsonString();
        }
        if (StringUtils.isEmpty(customer.getCode())){
            returnModel.addError("code","合同编号不能为空");
            return returnModel.toJsonString();
        }
        if (StringUtils.isEmpty(customer.getName())){
            returnModel.addError("name","客户名称不能为空");
            return returnModel.toJsonString();
        }
        if (ObjectUtils.isEmpty(customer.getSettlementType())){
            returnModel.addError("settlementType","结款方式不能为空");
            return returnModel.toJsonString();
        }
        if (ObjectUtils.isEmpty(customer.getStartTime())){
            returnModel.addError("startTime","合同开始时间不能为空");
            return returnModel.toJsonString();
        }
        if (ObjectUtils.isEmpty(customer.getEndTime())){
            returnModel.addError("endTime","合同结束时间不能为空");
            return returnModel.toJsonString();
        }

        if (StringUtils.isEmpty(customer.getLtl())){
            returnModel.addError("ltl","发货经纬度不能为空");
            return returnModel.toJsonString();
        }
        if (StringUtils.isEmpty(customer.getIphone())){
            returnModel.addError("iphone","电话不能为空");
            return returnModel.toJsonString();
        }
        //组织机构赋值
       //  customer.setRuless(customer.getRuless());
        customer.setOrganization(SessionUtil.getOrg());
        customerService.saveBean(customer);
        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();
    }



    /**
     * 用于获取客户下拉列表通用方法
     * @return
     */
    @RequestMapping(value = "/getCustomer", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getCustomer(CommModel commModel){
        String where =  "  and status=1 and organization='" + SessionUtil.getOrgId()+"'";
        if (StringUtils.isNotEmpty(commModel.getWhereValue())){
            where = where + " and name like '%"+commModel.getWhereValue()+"%'";
        }
        List<Customer> rows = customerService.selectList(new CommModel(),where,"");
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Customer customer:rows ){
            TreeModel treeModel = new TreeModel();
            treeModel.setId(customer.getId());
            treeModel.setText(customer.getName());
            treeModels.add(treeModel);
        }
        return returnJson(treeModels);
    }

}