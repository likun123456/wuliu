package com.kytms.zone.service.impl;
import com.kytms.core.constants.Entity;
import com.kytms.core.entity.Organization;
import com.kytms.core.entity.Zone;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.StringUtils;
import com.kytms.zone.action.ZoneAction;
import com.kytms.zone.dao.ZoneDao;
import com.kytms.zone.service.ZoneService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2017-11-23
 */
@Service(value = "ZoneService")
public class ZoneServiceImpl extends BaseServiceImpl<Zone> implements ZoneService<Zone> {
    private final Logger log = Logger.getLogger(ZoneServiceImpl.class);//输出Log日志
    private ZoneDao<Zone> zoneDao;
    @Resource(name = "ZoneDao")
    public void setZoneDao(ZoneDao zoneDao) {
        super.setBaseDao(zoneDao);
        this.zoneDao = zoneDao;
    }



    public List<TreeModel> getZoneTree(String id) {
        String selectId= id;
        if (StringUtils.isEmpty(selectId)){
            selectId = Entity.TREE_ROOT;
        }
        Zone zone = zoneDao.selectBean(selectId);
        List<Zone> zones = zone.getZones();
        List<TreeModel> treeModels = ZoneToTree(zones,null);
        return treeModels;
    }

    public List<TreeModel> getZoneTree(String roleId,String id) {
        String selectId= id;
        if (StringUtils.isEmpty(selectId)){
            selectId = Entity.TREE_ROOT;
        }
        Zone zone = zoneDao.selectBean(selectId);
        List<Zone> zones = zone.getZones();
        List<TreeModel> treeModels = ZoneToTree(zones,roleId);
        return treeModels;
    }

    public List<Zone> getZoneGrid(CommModel commModel) {
        String where;
        if (StringUtils.isNotEmpty(commModel.getId())){
            where = " and zid = '"+commModel.getId()+"'";
        }else {
            where = " and zid = 'root'";

        }
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where += " and status = "+commModel.getStatus();
        }
        List<Zone> zones = selectList(commModel,where,null);
        return zones;
    }

    public List<TreeModel> getCity(CommModel commModel) {
        String where = " and (level = 'city' or level = 'province')";
        if (StringUtils.isNotEmpty(commModel.getWhereValue())){
            where = where + " and name like '%"+commModel.getWhereValue()+"%'";
        }
        //List<Zone> zones = super.selectList(commModel, where, null);
        List zones = zoneDao.executeQueryHql("select id,name from JC_ZONE where 1=1 " + where);
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Object zone:zones) {
            Object[] zo = (Object[]) zone;
            TreeModel treeModel = new TreeModel();
            treeModel.setText((String) zo[1]);
            treeModel.setId((String)zo[0]);
            treeModel.setValue((String) zo[1]);
            treeModels.add(treeModel);
        }
        return treeModels;
    }

    private List<TreeModel> ZoneToTree(List<Zone> zones,String roleId ) {
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Zone zone:zones) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(zone.getId());
            treeModel.setText(zone.getName());
            treeModel.setIsexpand(false);
            treeModel.setComplete(false);

            if(!StringUtils.isEmpty(roleId)){
                treeModel.setShowcheck(true);
                List<Organization> organizations=zone.getOrganizations();
                for (int j = 0; j <organizations.size() ; j++) {
                    Organization organization = organizations.get(j);
                    if ( organization.getId().equals(roleId)){
                        treeModel.setCheckstate(1);
                        break;
                    }else {
                        treeModel.setCheckstate(0);
                    }
                }

                /*if(zone.getZones().size() >0){
                    treeModel.setShowcheck(false);
                }*/
            }

            if (zone.getZones() != null && zone.getZones().size() >0 ){
                treeModel.setHasChildren(true);
                treeModel.setComplete(false);
            }else {
                treeModel.setComplete(true);
                treeModel.setHasChildren(false);
            }
            treeModels.add(treeModel);
        }
        return treeModels;
    }

    public List<Zone> selectAllZone() {
        return zoneDao.selectAllZone();
    }

    public List<TreeModel> getAll(CommModel commModel) {
        String where = "";
        if (StringUtils.isNotEmpty(commModel.getWhereValue())){
            where = where + " and name like '%"+commModel.getWhereValue()+"%'";
        }
        List zones = zoneDao.executeQueryHql("select id,name from JC_ZONE where 1=1 " + where);
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Object zone:zones) {
            Object[] zo = (Object[]) zone;
            TreeModel treeModel = new TreeModel();
            treeModel.setText((String) zo[1]);
            treeModel.setId((String)zo[0]);
            treeModel.setValue((String) zo[1]);
            treeModels.add(treeModel);
        }
        return treeModels;
    }
}
