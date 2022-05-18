package com.kytms.driverset.service.Impl;

import com.kytms.core.entity.Driver;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.StringUtils;
import com.kytms.driverset.dao.DriverDao;
import com.kytms.driverset.dao.Impl.DriverDaoImpl;
import com.kytms.driverset.service.DriverService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/12/4 0004.
 */
@Service(value = "DriverService")
public class DriverServiceImpl extends BaseServiceImpl<Driver> implements DriverService<Driver> {
    private final Logger log = Logger.getLogger(DriverServiceImpl.class);//输出Log日志
    private DriverDao<Driver> driverDao;
    @Resource(name = "DriverDao")
    public void setDriverDao(DriverDao<Driver> driverDao) {
        super.setBaseDao(driverDao);
        this.driverDao = driverDao;
    }




    public JgGridListModel getDriverList(CommModel commModel) {
        String where="";
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where += " and status = "+commModel.getStatus();
        }
        String orderBY = " ORDER BY status desc ,create_time desc";
        return super.getListByPage(commModel,where,orderBY);
    }

    public List<Map> getDrivea(String name) {
        List list = driverDao.selectByPage("FROM JC_DRIVER  where status = 1",0,300);
        List<Map> lists = new ArrayList<Map>();
        for (Object obj: list) {
            Driver p = (Driver) obj;
            Map<String,String> map = new HashMap<String, String>();
            map.put("id",p.getName());
            map.put("text",p.getName());
            lists.add(map);
        }
        return lists;
    }
}
