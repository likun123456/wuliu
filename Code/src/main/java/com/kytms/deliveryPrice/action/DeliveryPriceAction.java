package com.kytms.deliveryPrice.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.DeliveryPrice;
import com.kytms.core.entity.ZoneStoreroom;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.deliveryPrice.service.DeliveryPriceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/deleveryPrice")
public class DeliveryPriceAction extends BaseAction {
    private final Logger log = Logger.getLogger(DeliveryPriceAction.class);//输出Log日志
     private DeliveryPriceService<DeliveryPrice> deliveryPriceService;

     @Resource
    public void setDeliveryPriceService(DeliveryPriceService<DeliveryPrice> deliveryPriceService) {
        this.deliveryPriceService = deliveryPriceService;
    }

    /**
     * 获取库存区域列表
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getDeleveryPriceList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getList(CommModel commModel) {
        JgGridListModel jgGridListModel = deliveryPriceService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    /**
     *库存区域保存方法
     */
    @RequestMapping(value = "/saveDeleveryPrice",produces = "test/json;charset=UTF-8")
    @ResponseBody
    public String saveDeleveryPrice(DeliveryPrice deliveryPrice){
        ReturnModel returnModel = getReturnModel();
        deliveryPriceService.saveBean(deliveryPrice);
        returnModel.setObj("保存成功……");
        return returnModel.toJsonString();

    }
}
