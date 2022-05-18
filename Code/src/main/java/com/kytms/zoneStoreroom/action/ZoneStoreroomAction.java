package com.kytms.zoneStoreroom.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.ZoneStoreroom;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.zone.service.impl.ZoneServiceImpl;
import com.kytms.zoneStoreroom.service.ZoneStoreroomService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/zoneStoreroom")
public class ZoneStoreroomAction extends BaseAction {
    private final Logger log = Logger.getLogger(ZoneStoreroomAction.class);//输出Log日志
     private ZoneStoreroomService<ZoneStoreroom> zoneStoreroomService;

    @Resource
    public void setZoneStoreroomService(ZoneStoreroomService<ZoneStoreroom> zoneStoreroomService) {
        this.zoneStoreroomService = zoneStoreroomService;
    }


    /**
     * 获取服务区域列表
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getZoneStoreroomList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getList(CommModel commModel) {
        JgGridListModel jgGridListModel = zoneStoreroomService.getList(commModel);
        return jgGridListModel.toJSONString();
    }
    @RequestMapping(value = "/getZoneStoreroomMap", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getZoneStoreroomMap() {
        List<TreeModel> zoneStoreroomMap = zoneStoreroomService.getZoneStoreroomMap(SessionUtil.getOrgId());
        return returnJsonForBean(zoneStoreroomMap);
    }

    /**
     *服务区域保存方法
     */
    @RequestMapping(value = "/saveZoneStoreroom",produces = "test/json;charset=UTF-8")
    @ResponseBody
    public String saveZoneStoreroom(ZoneStoreroom zoneStoreroom){
                ReturnModel returnModel = getReturnModel();
          if(zoneStoreroom.getOrganization() == null){
               zoneStoreroom.setOrganization(SessionUtil.getOrg());
          }
         zoneStoreroomService.saveZoneStoreroom(zoneStoreroom);
        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();

    }
}
