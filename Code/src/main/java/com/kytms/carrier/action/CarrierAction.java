package com.kytms.carrier.action;

import com.kytms.carrier.service.CarrierService;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Carrier;
import com.kytms.core.entity.ObjectUtils;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.DateUtils;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.transportorder.action.TransportOrderAction;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 承运商设置控制层
 * @author 陈小龙
 * @create 2018-01-15
 */
@Controller
@RequestMapping("/carrier")
public class CarrierAction extends BaseAction {
    private CarrierService<Carrier> carrierService;
    private Logger log = Logger.getLogger(CarrierAction.class);//输出Log日志


    @Resource
    public void setCarrierService(CarrierService<Carrier> carrierService) {
        this.carrierService = carrierService;
    }


    @RequestMapping(value = "/getCarrierList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getList(CommModel commModel){
        JgGridListModel jgGridListModel =carrierService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/saveCarrier", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveCarrier(Carrier carrier){
        ReturnModel returnModel = getReturnModel();

        //唯一性校验
        returnModel.codeUniqueAndNull(carrier,carrierService);
        Boolean isResult=returnModel.isResult();
        if(!isResult){
            return returnModel.toJsonString();
        }

        //开始非空校验
        if (StringUtils.isEmpty(carrier.getName())){
            returnModel.addError("name","承运商名称不能为空");
        }
        if (ObjectUtils.isEmpty(carrier.getCarrierType())){
            returnModel.addError("carrierType","承运商类型不能为空");
        }
        if (ObjectUtils.isEmpty(carrier.getStartTime())){
            returnModel.addError("startTime","合同开始时间不能为空");
        }
        if (ObjectUtils.isEmpty(carrier.getEndTime())){
            returnModel.addError("endTime","合同结束时间不能为空");
        }
        if(carrier.getCarrierType()==1){
            carrier.setZdOrganization(null);
        }
        carrier.setOrganization(SessionUtil.getOrg());
        carrierService.saveBean(carrier);
        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();
    }


    @RequestMapping(value = "/getCarrier", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getCarrier() {
        String carrierList=carrierService.getCarrier();
        return carrierList;
    }
}
