package com.kytms.system.service.Impl;

import com.kytms.core.entity.Notice;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.system.dao.NoticeDao;
import com.kytms.system.service.NoticeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 *
 * @author 奇趣源码
 * @create 2017-11-20
 */
@Service(value = "NoticeService")
public class NoticeServiceImpl extends BaseServiceImpl<Notice> implements NoticeService<Notice> {
    private final Logger log = Logger.getLogger(NoticeServiceImpl.class);//输出Log日志
    private NoticeDao noticeDao;
    @Resource(name = "NoticeDao")
    public void setNoticeDao(NoticeDao noticeDao) {
        super.setBaseDao(noticeDao);
        this.noticeDao = noticeDao;
    }




    public JgGridListModel getNoticeList(CommModel commModel) {
        JgGridListModel listByPage = super.getListByPage(commModel, null, " order by create_Time");
        return listByPage;
    }
}
