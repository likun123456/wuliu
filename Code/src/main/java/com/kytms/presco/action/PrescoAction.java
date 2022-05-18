package com.kytms.presco.action;

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.*;
import com.kytms.core.exception.AppBugException;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.otherFee.service.impl.OtherFeeServiceImpl;
import com.kytms.presco.service.PrescoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/presco")
public class PrescoAction extends BaseAction {
    private final Logger log = Logger.getLogger(PrescoAction.class);//输出Log日志
    private PrescoService<Presco> prescoService;


    @Resource
    public void setPrescoService(PrescoService<Presco> prescoService) {
        this.prescoService = prescoService;
    }

    /**
     * 获取计划单列表
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getPrescoList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getPrescoList(CommModel commModel) {
        JgGridListModel jgGridListModel = prescoService.getList(commModel);
        return jgGridListModel.toJSONString();
    }
    /**
     * 获取台账明细
     * */
    @RequestMapping(value = "/getPrescoLdList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getPrescoLdList(CommModel commModel){
        Presco o = (Presco) prescoService.selectBean(commModel.getId());
        List<LedgerDetail> ledgerDetail = o.getLedgerDetail();
        return  returnJsonForBean(ledgerDetail);
    }
    /**
     *服务区域保存方法
     */
    @RequestMapping(value = "/savePresco",produces = "test/json;charset=UTF-8")
    @ResponseBody
    public String savePresco(String presco){
        Presco presco1 = JSONObject.parseObject(presco,Presco.class);
        ReturnModel returnModel = getReturnModel();
        if(presco1.getOrganization() == null){
            presco1.setOrganization(SessionUtil.getOrg());
        }
        if(presco1.getCostomerType() == 1){
            if(presco1.getCustomer().getId() != null){
                presco1.setCustomer(presco1.getCustomer());
            }else{
                throw new MessageException("客户类型是【合同】，请填写客户名称");
            }

        }else if(presco1.getCostomerType() == 0){
             presco1.setCostomerNameLs(presco1.getCostomerNameLs());
             presco1.setCustomer(null);
        }
        if(presco1.getServerZone().getId() == null){
            throw new MessageException("【提派区域】不能为空");
        }
        if(presco1.getDateAccepted() ==null){
            returnModel.addError("dateAccepted","受理时间不能为空");
        }
        if(presco1.getFH_name()  == null){
            returnModel.addError("FH_name","发货单位不能为空");
        }
        if(presco1.getFH_person() == null){
            returnModel.addError("FH_person","发货人不能为空");
        }
        if(presco1.getFH_iphone() == null){
            returnModel.addError("FH_iphone","发货人电话不能为空");
        }
        if(presco1.getFH_address() == null){
            returnModel.addError("FH_address","发货地址不能为空");
        }
        List<PrescoProduct> prescoProducts = presco1.getPrescoProducts();
        for (PrescoProduct prescoProduct : prescoProducts) {
            if (StringUtils.isEmpty(prescoProduct.getName())) {
                returnModel.addError("code", "货品名称不能为空");
            }
            if (StringUtils.isEmpty(prescoProduct.getUnit())) {
                returnModel.addError("code", "货品单位不能为空");
            }
        }

        if(returnModel.isResult()){
            prescoService.savePresco(presco1);
            returnModel.setObj("保存成功……");
        }
        return returnModel.toJsonString();
    }

    /**
     * 获取所有的预录单
     * */
    @RequestMapping(value = "/getPresco", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getPresco(CommModel commModel) {
        //返回预录表数据
        String json = null;
        if(commModel.getId() != null){
        Presco presco = prescoService.selectBean(commModel.getId());
            json = returnJsonForBean(presco);
        }
        return json;
    }

    /**
     * 逻辑删除预录单
     * */
    @RequestMapping(value = "/delStatus", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String delStatus(String id){
        ReturnModel returnModel = getReturnModel();
         String idd=null;
        String[] ids = id.split(",");

        for (String aa:ids) {
           Presco presco=prescoService.selectBean(aa);
            if(presco.getStatus() !=1){
                returnModel.addError("status","预录单状态不是生效,不能删除!");
            }else{
              //  presco.setStatus(99);
                prescoService.delBean(id);
               // prescoService.saveBean(presco);
                returnModel.setObj("删除成功……");
            }
        }
        return returnModel.toJsonString();
    }
    @RequestMapping(value = "/confirmationOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
     public String  confirmationOrder(String id){
        ReturnModel returnModel = getReturnModel();
        Presco presco = prescoService.selectBean(id);
        String orderId = prescoService.savaToOrder(presco);
        returnModel.setObj(orderId);
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/savefzxj", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String savefzxj(String id){
        ReturnModel returnModel = new ReturnModel();
       String aa = prescoService.savefzxj(id);
         returnModel.setObj(aa);
        return returnModel.toJsonString();

    }

}
