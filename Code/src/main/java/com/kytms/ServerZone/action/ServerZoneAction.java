package com.kytms.ServerZone.action;

import com.kytms.ServerZone.service.ServerZoneService;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.ServerZone;
import com.kytms.core.exception.AppBugException;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.registration.service.Impl.JcRegistrationServiceImpl;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/serverZone")
public class ServerZoneAction extends BaseAction {
    private final Logger log = Logger.getLogger(ServerZoneAction.class);//输出Log日志
    private ServerZoneService<ServerZone> serverZoneService;

    @Resource(name = "ServerZoneService")
    public void setServerZoneService(ServerZoneService<ServerZone> serverZoneService) {
        this.serverZoneService = serverZoneService;
    }

    /**
     * 获取服务区域列表
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getServerZoneList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getList(CommModel commModel) {
        JgGridListModel jgGridListModel = serverZoneService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    /**
     *服务区域保存方法
     */
    @RequestMapping(value = "/saveServerZone",produces = "test/json;charset=UTF-8")
    @ResponseBody
    public String saveServerZone(ServerZone serverZone){
        ReturnModel returnModel = getReturnModel();
        if(serverZone.getOrganization() == null){
            serverZone.setOrganization(SessionUtil.getOrg());
        }
        if(serverZone.getZone() == null){
            throw  new AppBugException("组织区域不能为空！");
        }
        serverZone.setType(serverZone.getType());
        serverZoneService.saveServerZone(serverZone);
        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();
    }

    /**
     * 用于获取服务区域下拉列表通用方法
     *
     * @return
     */
    @RequestMapping(value = "/getServerZoneTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getServerZoneTree(){
        List<ServerZone> rows = serverZoneService.selectList(new CommModel(), " and organization='" + SessionUtil.getOrgId() + "' and type=0", "");
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (ServerZone serverZone : rows) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(serverZone.getId());
            treeModel.setText(serverZone.getName());
            treeModels.add(treeModel);
        }
        return returnJson(treeModels);

    }

    @RequestMapping(value = "/lxupload", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String lxupload(@RequestParam("file")CommonsMultipartFile file, HttpServletRequest request){
        ReturnModel returnModel = new ReturnModel();
        DiskFileItem fi = (DiskFileItem) file.getFileItem();
        File f = fi.getStoreLocation();
        try {
            serverZoneService.lxupload(f);
        } catch (MessageException e) {
            returnModel.setResult(false);
            returnModel.setObj(e.getMessage());
        }
        return returnModel.toJsonString();
    }



}
