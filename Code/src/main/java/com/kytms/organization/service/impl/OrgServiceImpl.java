package com.kytms.organization.service.impl;

import com.kytms.core.constants.Entity;
import com.kytms.core.entity.Organization;
import com.kytms.core.entity.User;
import com.kytms.core.entity.Zone;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SpringUtils;
import com.kytms.core.utils.StringUtils;
import com.kytms.organization.dao.OrgDao;
import com.kytms.organization.dao.impl.OrgDaoImpl;
import com.kytms.organization.service.OrgService;
import com.kytms.zone.dao.impl.ZoneDaoImpl;
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
@Service(value = "OrgService")
public class OrgServiceImpl extends BaseServiceImpl<Organization> implements OrgService<Organization> {
    private final Logger log = Logger.getLogger(OrgServiceImpl.class);//输出Log日志
    private OrgDao<Organization> orgDao;
    private ZoneService<Zone> zoneService;
    @Resource
    public void setZoneService(ZoneService zoneService) {
        this.zoneService = zoneService;
    }
    @Resource(name = "OrgDao")
    public void setOrgDao(OrgDao<Organization> orgDao) {
        super.setBaseDao(orgDao);
        this.orgDao = orgDao;
    }




    public List<Organization> getOrgGrid(CommModel commModel) {
        String where = "";
        if (commModel == null || "".equals(commModel.getId() )||commModel.getId() == null){
            commModel.setId(Entity.TREE_ROOT);
        }else {
            where = " and ZID = '"+commModel.getId()+"'";
        }
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where += " and status = "+commModel.getStatus();
        }
        String order = " Order by create_Time";
        return super.selectList(commModel,where,order);
    }

    public List<TreeModel> getZoneTree(String roleId, String id)  {
        List<TreeModel> treeModels = zoneService.getZoneTree(roleId,id);
        return treeModels;
    }

    public void saveZone(String id, String ids) {
        ZoneDaoImpl bean = SpringUtils.getBean(ZoneDaoImpl.class);
        String[] split = ids.split(",");
        List<Zone> list = new ArrayList<Zone>();
        for (int i = 0; i < split.length; i++) {
            Zone zone = bean.selectBean(split[i]);
            List<Zone> zones=zone.getZones();
            if(zones.size()!=0){
                for(Zone z:zones){
                    List<Zone> zones1=z.getZones();
                    if(zones1.size()!=0){
                        for(Zone zz:zones1){
                            list.add(zz);
                        }
                    }
                    list.add(z);
                }
            }
            list.add(zone);
        }
        Organization organization = orgDao.selectBean(id);
        organization.setZones(list);
    }

    public JgGridListModel getOrgList(CommModel commModel) {
        String where = " and  status = 1 and level != 'root'";
        String orderBY = " ORDER BY create_Time desc ";
        return super.getListByPage(commModel,where,orderBY);
    }

    public List<Organization> selectUserOrgs(User sessionUser) {
        List<Organization> organizations = orgDao.executeQueryHql("select a from JC_SYS_ORGANIZATION a  left join a.users b where a.status != 0 and b.id = '"+sessionUser.getId()+"' ");
        return organizations;
    }

    public List<Organization> getOrgTreeW(CommModel commModel) {
        List<Organization> organizations = orgDao.executeQueryHql("select a from JC_SYS_ORGANIZATION a where a.status != 0");
        return organizations;
    }
}
