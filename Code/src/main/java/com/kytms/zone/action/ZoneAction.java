package com.kytms.zone.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Zone;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.verification.service.impl.VerificationServiceImpl;
import com.kytms.zone.service.ZoneService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 行政区域
 *
 * @author 奇趣源码
 * @create 2017-11-23
 */
@Controller
@RequestMapping("/zone")
public class ZoneAction extends BaseAction{
    private final Logger log = Logger.getLogger(ZoneAction.class);//输出Log日志
    private ZoneService zoneService;
    @Resource
    public void setZoneService(ZoneService zoneService) {
        this.zoneService = zoneService;
    }




    @RequestMapping(value = "/saveZone", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveZone(Zone zone){
        ReturnModel returnModel = getReturnModel();
        returnModel.codeUniqueAndNull(zone,zoneService);
        if (StringUtils.isEmpty(zone.getName())){
            returnModel.addError("name","区域名称不能为空");
        }
        if (StringUtils.isEmpty(zone.getCityCode())){
            returnModel.addError("cityCode","城市代码不能为空");
        }
        if (StringUtils.isEmpty(zone.getLevel())){
            returnModel.addError("level","等级不能为空");
        }
        if (zone.getZone() == null || StringUtils.isEmpty(zone.getZone().getId())){
            returnModel.addError("zone","上级机构不能为空");
        }
        boolean result = returnModel.isResult();
        if (result){
           zoneService.saveBean(zone);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getZoneTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getZoneTree(String id){
        List<TreeModel> treeModelList = zoneService.getZoneTree(id);
        return returnJson(treeModelList);
    }

    @RequestMapping(value = "/getZoneGrid", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getZoneGrid(CommModel commModel){
        List<Zone> zones = zoneService.getZoneGrid(commModel);
        return returnJson(zones);
    }

    /**
     * 获取城市 奇趣源码编写 包含直辖市
     * @return
     */
    @RequestMapping(value = "/getCity", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getCity(CommModel commModel){
        List<TreeModel> treeModelList = zoneService.getCity(commModel);
        return returnJson(treeModelList);
    }


    /**
     * 获取所有的
     * @return
     */
    @RequestMapping(value = "/getAll", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getAll(CommModel commModel){
        List<TreeModel> treeModelList = zoneService.getAll(commModel);
        return returnJson(treeModelList);
    }



}
