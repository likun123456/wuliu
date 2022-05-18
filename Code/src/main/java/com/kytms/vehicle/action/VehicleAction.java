package com.kytms.vehicle.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Vehicle;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.transportorder.service.Impl.TransportOrderServiceImpl;
import com.kytms.vehicle.service.VehicleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 车型控制层
 *
 * @author陈小龙
 * @create 2018-1-4
 */

@Controller
@RequestMapping("/vehicle")
public class VehicleAction extends BaseAction {
    private final Logger log = Logger.getLogger(VehicleAction.class);//输出Log日志
    private VehicleService<Vehicle> vehicleService;
    @Resource
    public void setVehicleService(VehicleService<Vehicle> vehicleService) {
        this.vehicleService = vehicleService;
    }




    @RequestMapping(value = "/getVehicleList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getVehicleList(CommModel commModel){
        JgGridListModel jgGridListModel =vehicleService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/saveVehicle", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveVehicle(Vehicle vehicle){
        ReturnModel returnModel = getReturnModel();
        //非空验证
        if (StringUtils.isEmpty(vehicle.getCode())){
            returnModel.addError("code","车型不能为空");
            return returnModel.toJsonString();
        }
        //唯一性校验
        returnModel.codeUniqueAndNull(vehicle,vehicleService);
        Boolean isResult=returnModel.isResult();
        if(!isResult) {
            return returnModel.toJsonString();
        }
        vehicleService.saveBean(vehicle);
        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();
    }

    /**
     * 获取车型 孙德增编写
     */
    @RequestMapping(value = "/getVehicle", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getVehicle(CommModel commModel){
        List<TreeModel> treeModelList = vehicleService.getVehicle(commModel);
        return returnJson(treeModelList);
    }
}
