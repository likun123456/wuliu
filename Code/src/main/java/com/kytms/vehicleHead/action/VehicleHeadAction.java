package com.kytms.vehicleHead.action;

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.*;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.vehicleArrive.service.impl.VehicleArriveServiceImpl;
import com.kytms.vehicleHead.service.VehicleHeadService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车头档案控制层
 *
 * @author 陈小龙
 * @create 2018-01-12
 */
@Controller
@RequestMapping("/vehicleHead")
public class VehicleHeadAction extends BaseAction {
    private final Logger log = Logger.getLogger(VehicleHeadAction.class);//输出Log日志
    private VehicleHeadService<VehicleHead> vehicleHeadService;
    @Resource
    public void setVehicleHeadService(VehicleHeadService<VehicleHead> vehicleHeadService) {
        this.vehicleHeadService = vehicleHeadService;
    }




    @RequestMapping(value = "/getVehicleHeadList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getList(CommModel commModel) {
        JgGridListModel jgGridListModel = vehicleHeadService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    /**
     * 孙得增改
     *
     * @param vehicleHead
     * @return
     */
    @RequestMapping(value = "/saveVehicleHead", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveVehicleHead(VehicleHead vehicleHead) {
        ReturnModel returnModel = getReturnModel();
        //组织机构赋值 (如果组织机构不填，默认当前组织机构，别瞎改）
        //亲们不能用StringUtils.isEmpty(vehicleHead.getOrganization().getID()) 来判断，getOrganization 它本身就是空
        if (vehicleHead.getOrganization() == null) {
            vehicleHead.setOrganization(SessionUtil.getOrg());
        }
        //唯一性校验
        returnModel.codeUniqueAndNull(vehicleHead, vehicleHeadService);
        Boolean isResult = returnModel.isResult();
        if (!isResult) {
            return returnModel.toJsonString();
        }
        //开始非空校验

        vehicleHeadService.saveVehicleHead(vehicleHead);

        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();

    }

    /**
     * 用于获取车头下拉列表通用方法
     *
     * @return
     */
    @RequestMapping(value = "/getVehicleHeadTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getVehicleHeadTree() {
        List<VehicleHead> rows = vehicleHeadService.selectList(new CommModel(), " and organization='" + SessionUtil.getOrgId() + "' and status=1", "");
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (VehicleHead vehicleHead : rows) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(vehicleHead.getId());
            treeModel.setText(vehicleHead.getCode());
            treeModels.add(treeModel);
        }
        return returnJson(treeModels);
    }

    /**
     * 用于获取车头关联司机 车挂 车厢信息的表
     *
     * @return
     */
    @RequestMapping(value = "/getVehicleHeadInfo", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getVehicleHeadInfo(String id) {
        VehicleHead vehicleHead = vehicleHeadService.selectBean(id); //获取车头
        JSONObject head = new JSONObject();
        JSONObject headObj = new JSONObject();//保存车头信息
        headObj.put("id", vehicleHead.getId());
        headObj.put("code", vehicleHead.getCode());
        head.put("vehicleHead", headObj);
//        //处理司机信息
//        if (vehicleHead.getDrivers() != null && vehicleHead.getDrivers().size() > 0) {
//            Driver driver = vehicleHead.getDrivers().get(0);
//            JSONObject em = new JSONObject();//保存司机信息
//            em.put("id", driver.getId());
//            em.put("name", driver.getName());
//            em.put("tel",driver.getIphone1());
//            head.put("driver", em);
//        }

        return head.toJSONString();
    }


    /**
     * 主页面显示组织机构拥有车的数量
     */
    @RequestMapping(value = "/getOrgVelList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrgVelList() {
        List list = vehicleHeadService.getOrgVelList();
        //Object[] obj = list.toArray();
        return returnJson(list);
    }

    /**
     * 主页面显示当前组织机构闲置车辆
     */
    @RequestMapping(value = "/selectCont", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String selectCont() {
        List list = vehicleHeadService.selectCont();
        return returnJson(list);
    }
}
