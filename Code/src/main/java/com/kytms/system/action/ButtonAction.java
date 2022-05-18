package com.kytms.system.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Button;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.single.SingleSql;
import com.kytms.system.service.ButtonService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 按钮管理类
 *
 * @author
 * @create 2017-11-20
 */
@Controller
@RequestMapping("/button")
public class ButtonAction extends BaseAction{
    private final Logger log = Logger.getLogger(ButtonAction.class);//输出Log日志
    private ButtonService buttonService;
    @Resource
    public void setButtonService(ButtonService buttonService) {
        this.buttonService = buttonService;
    }




    @RequestMapping(value = "/getButtonList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getButtonList(CommModel commModel){
        List<Button> buttons = buttonService.selectButtonList(commModel);
        return returnJsonForBean(buttons);
    }

    @RequestMapping(value = "/saveButton", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveButton(Button button){
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj("保存成功");
        returnModel.codeUniqueAndNull(button,buttonService);//验证CODE
        if (StringUtils.isEmpty(button.getName())){
            returnModel.addError("name","按钮名称不能为空");
        }
        boolean result = returnModel.isResult();
        if (result){
            buttonService.saveBean(button);
        }
        return returnJson(returnModel);
    }
}
