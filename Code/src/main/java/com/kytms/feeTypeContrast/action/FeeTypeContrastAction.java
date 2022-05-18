package com.kytms.feeTypeContrast.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Customer;
import com.kytms.core.entity.FeeTypeContrast;
import com.kytms.core.entity.ObjectUtils;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.feeTypeContrast.service.FeeTypeContrastService;
import com.kytms.feeTypeContrast.service.impl.FeeTypeContrastServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2019/3/15
 */
@Controller
@RequestMapping("/feeTypeContrast")
public class FeeTypeContrastAction extends BaseAction{
    private FeeTypeContrastService<FeeTypeContrast> feeTypeContrastService;
    private final Logger log = Logger.getLogger(FeeTypeContrastAction.class);//输出Log日志

    @Resource(name = "FeeTypeContrastService")
    public void setFeeTypeContrastService(FeeTypeContrastService<FeeTypeContrast> feeTypeContrastService) {
        this.feeTypeContrastService = feeTypeContrastService;
    }

    @RequestMapping(value = "/getFtcList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getFtcList(CommModel commModel){
        JgGridListModel jgGridListModel = feeTypeContrastService.getFtcList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/saveFeeTypeContrast", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveFeeTypeContrast(FeeTypeContrast feeTypeContrast){
        ReturnModel returnModel = getReturnModel();
        //唯一性校验
        Boolean isResult=returnModel.isResult();
        if(!isResult){
            return returnModel.toJsonString();
        }
        //组织机构赋值
        feeTypeContrastService.saveFeeTypeCon(feeTypeContrast);
        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();
    }

}
