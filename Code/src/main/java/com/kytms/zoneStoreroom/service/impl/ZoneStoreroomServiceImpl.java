package com.kytms.zoneStoreroom.service.impl;

import com.kytms.core.entity.ZoneStoreroom;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.zoneStoreroom.action.ZoneStoreroomAction;
import com.kytms.zoneStoreroom.dao.ZoneStoreroomDao;
import com.kytms.zoneStoreroom.service.ZoneStoreroomService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service(value = "ZoneStoreroomService")
public class ZoneStoreroomServiceImpl extends BaseServiceImpl<ZoneStoreroom> implements ZoneStoreroomService<ZoneStoreroom> {
    private final Logger log = Logger.getLogger(ZoneStoreroomServiceImpl.class);//输出Log日志
    private ZoneStoreroomDao<ZoneStoreroom> zoneStoreroomDao;

    @Resource(name = "ZoneStoreroomDao")
    public void setZoneStoreroomDao(ZoneStoreroomDao<ZoneStoreroom> zoneStoreroomDao) {
        this.zoneStoreroomDao = zoneStoreroomDao;
         super.setBaseDao(zoneStoreroomDao);
    }






    public JgGridListModel getList(CommModel commModel) {
        String where = " and organization.id='"+ SessionUtil.getOrgId()+"'";
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where += " and status = "+commModel.getStatus();
        }
        String orderBY = " ORDER BY  create_Time desc";
        return super.getListByPage(commModel,where,orderBY);
    }

    public void saveZoneStoreroom(ZoneStoreroom zoneStoreroom) {
        ZoneStoreroom zoneStoreroom1 = zoneStoreroomDao.savaBean(zoneStoreroom);
    }

    public  List<TreeModel>  getZoneStoreroomMap(String orgId) {
        List<ZoneStoreroom> zoneStorerooms = zoneStoreroomDao.executeQueryHql("from JC_ZONE_STOREROOM where organization.id = '" + orgId + "' and status =1");
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        if(zoneStorerooms !=null || zoneStorerooms.size() !=0 ){
        for (ZoneStoreroom zoneStoreroom:zoneStorerooms) {
            TreeModel treeModel  = new TreeModel();
            treeModel.setId(zoneStoreroom.getId());
            treeModel.setText(zoneStoreroom.getName());
            treeModels.add(treeModel);
        }
        }
        return treeModels;
    }
}
