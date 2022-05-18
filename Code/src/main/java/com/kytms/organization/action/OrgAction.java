package com.kytms.organization.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Organization;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.orderback.service.Impl.OrderBackServiceImpl;
import com.kytms.organization.service.OrgService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 组织机构
 *
 * @author 奇趣源码
 * @create 2017-11-23
 */
@Controller
@RequestMapping("/org")
public class OrgAction extends BaseAction{
    private final Logger log = Logger.getLogger(OrgAction.class);//输出Log日志
    private OrgService orgService;
    @Resource(name = "OrgService")
    public void setOrgService(OrgService orgService) {
        this.orgService = orgService;
    }


    @RequestMapping(value = "/getOrgTreeW", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrgTreeW(CommModel commModel){
        List<Organization> list = orgService.getOrgTreeW(commModel);
        return returnJson(list);
    }

    @RequestMapping(value = "/getOrgGrid", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrgGrid(CommModel commModel){
        List<Organization> list = orgService.getOrgGrid(commModel);
        return returnJson(list);
    }

    @RequestMapping(value = "/saveOrg", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveOrg(String tableName,String obj){
        ReturnModel returnModel = getReturnModel();
        Organization o =  JSONObject.parseObject(obj, Organization.class);
        int i = o.getName().indexOf("-");
        if (o.getName().indexOf("-")>0){
            returnModel.setObj("组织机构不允许用 - ");
            returnModel.setResult(false);
        }
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(obj)){
            returnModel.setObj("前台页面错误，传入表名，对象为空");
            returnModel.setResult(false);
        }
        returnModel.codeUniqueAndNull(o,orgService);
        boolean result = returnModel.isResult();
        if (result){
            orgService.saveBean(o);
        }
        return returnModel.toJsonString();
    }

    /**
     * 用于获取组织机构下拉列表通用方法
     * @return
     */
    @RequestMapping(value = "/getOrgTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getVehicleHeadTree(){
         String where = "and status =1 and zid != null and zid != 'root' ";
        List<Organization> rows = orgService.selectList(new CommModel(), where , null);
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Organization org:rows ){
            TreeModel treeModel = new TreeModel();
            treeModel.setId(org.getId());
            treeModel.setText(org.getName());
            treeModels.add(treeModel);
        }
        return returnJson(treeModels);
    }

    /**
     * 获取行政区域列表
     */
    @RequestMapping(value = "/getZoneTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getZoneTree(String roleId,String id){
        List<TreeModel> list = orgService.getZoneTree(roleId,id);
        return returnJson(list);
    }

    /**
     * 保存绑定行政区域
     * @param id
     * @param ids
     * @return
     */
    @RequestMapping(value = "/saveZone", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找保存按钮数据库方法
    public String  saveZone(String id,String ids){
        ReturnModel returnModel = getReturnModel();
        orgService.saveZone(id,ids);
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getOrgList",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getOrgList(CommModel commModel){
        JgGridListModel jgGridListModel =orgService.getOrgList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/getOrgForJsonArray",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getOrgForJsonArray(CommModel commModel){
        List<Organization> list = orgService.selectList(commModel," and id != 'root'",null);
        JSONArray jsonArray = new JSONArray();
        for (Organization organization: list) {
            String id = organization.getId();
            String name = organization.getName();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(id,name);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    @RequestMapping(value = "/getCurrentOrg",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getCurrentOrg(CommModel commModel){
        Organization org=SessionUtil.getOrg();
        String id = org.getId();
        String name = org.getName();
        Organization org1= new Organization();
        org1.setId(id);
        org1.setName(name);
        return returnJson(org1);
    }
}
