package com.kytms.inOrOutRecord.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.InOrOutRecord;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.feetype.service.impl.FeeTypeServiceImpl;
import com.kytms.inOrOutRecord.service.InOrOutRecordService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author:sundezeng
 * @Date:2018/10/22
 */
@Controller
@RequestMapping("/inOrOutRecord")
public class InOrOutRecordAction extends BaseAction {
    private final Logger log = Logger.getLogger(InOrOutRecordAction.class);//输出Log日志
    private InOrOutRecordService<InOrOutRecord> inOrOutRecordService;

    @Resource(name = "InOrOutRecordService")
    public void setInOrOutRecordService(InOrOutRecordService<InOrOutRecord> inOrOutRecordService) {
        this.inOrOutRecordService = inOrOutRecordService;
    }

    @RequestMapping(value = "/getInOrOutRecordList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getInOrOutRecordList(CommModel commModel){
        JgGridListModel jgGridListModel = inOrOutRecordService.getInOrOutRecordList(commModel);
        return jgGridListModel.toJSONString();
    }

}
