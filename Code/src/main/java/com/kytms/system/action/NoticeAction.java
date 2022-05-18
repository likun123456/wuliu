package com.kytms.system.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Notice;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.system.service.NoticeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 按钮管理类
 *
 * @author 奇趣源码
 * @create 2017-11-20
 */
@Controller
@RequestMapping("/notice")
public class NoticeAction extends BaseAction{
    private final Logger log = Logger.getLogger(NoticeAction.class);//输出Log日志
    private NoticeService noticeService;
    @Resource
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }




    @RequestMapping(value = "/getNoticeList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getNoticeList(CommModel commModel){
        JgGridListModel jgGridListModel = noticeService.getNoticeList(commModel);
        return returnJson(jgGridListModel);
    }

    @RequestMapping(value = "/saveNotice", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveNotice(Notice notice){
        ReturnModel returnModel = getReturnModel();
        returnModel.codeUniqueAndNull(notice,noticeService);
        if (StringUtils.isEmpty(notice.getName())){
             returnModel.addError("name","通告名称不能为空");
        }
        if (notice.getTime()==null){
            returnModel.addError("time","时间不能为空");
        }
        boolean result = returnModel.isResult();
        if (result){
            noticeService.saveBean(notice);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getNoticeListTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getNoticeListTree(CommModel commModel){
        List<Notice> rows = noticeService.selectList(new CommModel()," and status=1"," order by time desc");
        return returnJson(rows);
    }
}
