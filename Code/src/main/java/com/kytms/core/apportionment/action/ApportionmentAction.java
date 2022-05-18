package com.kytms.core.apportionment.action;

import com.kytms.core.action.ActionExeLogAction;
import com.kytms.core.action.BaseAction;
import com.kytms.core.apportionment.service.ApportionmentService;
import com.kytms.core.entity.Apportionment;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2019/1/2
 */
@Controller
@RequestMapping("/apportionment")
public class ApportionmentAction extends BaseAction {
     private ApportionmentService<Apportionment> apportionmentService;
    private Logger log = Logger.getLogger(ApportionmentAction.class);//输出Log日志

    @Resource(name = "ApportionmentService")
    public void setApportionmentService(ApportionmentService<Apportionment> apportionmentService) {
        this.apportionmentService = apportionmentService;
    }
}
