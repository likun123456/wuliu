package com.kytms.carrier.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kytms.carrier.action.CarrierAction;
import com.kytms.carrier.dao.CarrierDao;
import com.kytms.carrier.service.CarrierService;
import com.kytms.core.entity.Carrier;
import com.kytms.core.entity.Shipment;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.shipment.dao.ShipmentDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *承运商设置服务层实现类
 * @author 陈小龙
 * @create 2018-01-15
 */
@Service(value = "CarrierService")
public class CarrierServiceImpl  extends BaseServiceImpl<Carrier> implements CarrierService<Carrier> {
    private CarrierDao<Carrier> carrierDao;
    private Logger log = Logger.getLogger(CarrierServiceImpl.class);//输出Log日志

    public String getCarrier() {
        String HQL  ="SELECT a.id as id,a.name as name FROM JC_CARRIER a left join a.organization b where a.status =1 and b.id='"+SessionUtil.getOrgId()+"'";
        List list = carrierDao.executeQueryHql(HQL);
        JSONObject jsonObject = new JSONObject();
        for (Object object:list) {
            Object[] o = (Object[]) object;
            jsonObject.put(o[0].toString(),o[1].toString());
        }
        return jsonObject.toJSONString();
    }

    @Resource(name = "CarrierDao")
    public void setCarrierDao(CarrierDao<Carrier> carrierDao) {
        super.setBaseDao(carrierDao);
        this.carrierDao = carrierDao;
    }



    public JgGridListModel getList(CommModel commModel) {
        String where = " and organization.id = '"+ SessionUtil.getOrgId()+"'";
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where += " and status = "+commModel.getStatus();
        }
        String orderBY = " ORDER BY  create_time desc";
        return super.getListByPage(commModel,where,orderBY);
    }

}
