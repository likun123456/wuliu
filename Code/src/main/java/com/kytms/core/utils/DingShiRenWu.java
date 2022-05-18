package com.kytms.core.utils;


import com.kytms.core.entity.Organization;
import com.kytms.organization.dao.OrgDao;
import javafx.concurrent.Task;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.*;

/**
 * @Author:sundezeng
 * @Date:2018/12/20
 */
public class DingShiRenWu {
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    private OrgDao orgDao;

    @Resource(name = "OrgDao")
    public void setOrgDao(OrgDao orgDao) {
        this.orgDao = orgDao;
    }

    public DingShiRenWu() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1); //凌晨1点
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); //第一次执行定时任务的时间
        //如果第一次执行定时任务的时间 小于当前的时间
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }
        Timer timer = new Timer();
       TimerTask task = new TimerTask() {
            public void run() {
             String hql ="update JC_SYS_ORGANIZATION set field1 ='hello'";
                   orgDao.executeHql(hql,null);
            }
        };
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(task,date,PERIOD_DAY);
    }
    // 增加或减少天数
    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

}
