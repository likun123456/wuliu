package com.kytms.vehicleArrive.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.MathExtend;
import com.kytms.core.utils.StringUtils;
import com.kytms.shipment.service.ShipmentService;
import com.kytms.vehicle.service.impl.VehicleServiceImpl;
import com.kytms.vehicleArrive.service.VehicleArriveService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author:sundezeng
 * @Date:2018/10/24
 */
@Controller
@RequestMapping("/vehicleArrive")
public class VehicleArriveAction extends BaseAction {
    private final Logger log = Logger.getLogger(VehicleArriveAction.class);//输出Log日志
     private VehicleArriveService<VehicleArrive> vehicleArriveService;
     private ShipmentService<Shipment> shipmentService;

     @Resource(name = "ShipmentService")
    public void setShipmentService(ShipmentService<Shipment> shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Resource(name = "VehicleArriveService")
    public void setVehicleArriveService(VehicleArriveService<VehicleArrive> vehicleArriveService) {
        this.vehicleArriveService = vehicleArriveService;
    }

    @RequestMapping(value = "/getVehicleArriveList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getList(CommModel commModel) {
        JgGridListModel jgGridListModel = vehicleArriveService.getList(commModel);
        return jgGridListModel.toJSONString();
    }

    //到站记录
    @RequestMapping(value = "/getDZShipmentInfo", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getDZShipmentInfo(CommModel commModel) {
        JgGridListModel jgGridListModel = vehicleArriveService.getDZShipmentInfo(commModel);
        return jgGridListModel.toJSONString();
    }


    /**
     * 车辆到站操作
     * @param vehicleArrive
     * @return
     */
    @RequestMapping(value = "/saveVehicleArrive", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveVehicleArrive(VehicleArrive vehicleArrive) {
        ReturnModel returnModel = getReturnModel();
        boolean result = returnModel.isResult();

        if (result){
             vehicleArriveService.saveVehicleArrive(vehicleArrive);
        }
        return returnModel.toJsonString();
    }
    @RequestMapping(value = "/saveShippingTime", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveShippingTime(VehicleArrive vehicleArrive) {
        ReturnModel returnModel = getReturnModel();
           boolean result = returnModel.isResult();
               if(StringUtils.isEmpty(vehicleArrive.getOperateTime().toString())){
                   returnModel.addError("operateTime","发运时间不能为空！");
               }
               if(vehicleArrive.getShipment().getStatus() == 6){
                   if (result){
                       vehicleArriveService.saveShippingTime(vehicleArrive);
                   }
               }else {
                   returnModel.addError("status","发运时间不能为空！");
               }
        return returnModel.toJsonString();
    }
    @RequestMapping(value = "/saveArriveTime", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveArriveTime(VehicleArrive vehicleArrive) {
        ReturnModel returnModel = getReturnModel();
        boolean result = returnModel.isResult();
        if(StringUtils.isEmpty(vehicleArrive.getArriveTime().toString())){
            returnModel.addError("arriveTime","运抵时间不能为空！");
        }
            if (result){
                vehicleArriveService.saveArriveTime(vehicleArrive);
            }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getVehiclePrint", produces = "text/json;charset=UTF-8")
    @ResponseBody
    private String getVehiclePrint(String id){
        if(StringUtils.isEmpty(id)){
            throw  new MessageException("获取派车单失败");
        }
        Shipment shipment = (Shipment) shipmentService.selectBean(id);
        StringBuilder stringBuilder = new StringBuilder();
        Timestamp factLeaveTime = shipment.getFactLeaveTime();
        String begin ="  <col width=43 style='mso-width-source:userset;mso-width-alt:1536;width:32pt'>\n" +
                " <col width=42 style='mso-width-source:userset;mso-width-alt:1479;width:31pt'>\n" +
                " <col width=61 style='mso-width-source:userset;mso-width-alt:2161;width:46pt'>\n" +
                " <col width=50 style='mso-width-source:userset;mso-width-alt:1792;width:38pt'>\n" +
                " <col width=59 style='mso-width-source:userset;mso-width-alt:2104;width:44pt'>\n" +
                " <col width=65 style='mso-width-source:userset;mso-width-alt:2304;width:49pt'>\n" +
                " <col width=86 style='mso-width-source:userset;mso-width-alt:3072;width:65pt'>\n" +
                " <col width=62 style='mso-width-source:userset;mso-width-alt:2218;width:47pt'>\n" +
                " <col width=51 style='mso-width-source:userset;mso-width-alt:1820;width:38pt'>\n" +
                " <col width=55 style='mso-width-source:userset;mso-width-alt:1962;width:41pt'>\n" +
                " <col width=57 style='mso-width-source:userset;mso-width-alt:2019;width:43pt'>\n" +
                " <col width=31 style='mso-width-source:userset;mso-width-alt:1109;width:23pt'>\n" +
                " <col width=48 style='mso-width-source:userset;mso-width-alt:1706;width:36pt'>\n" +
                " <col width=44 style='mso-width-source:userset;mso-width-alt:1564;width:33pt'>\n" +
                " <col width=75 style='mso-width-source:userset;mso-width-alt:2673;width:56pt'>\n" +
                " <tr height=37 style='mso-height-source:userset;height:27.6pt'>\n" +
                "  <td colspan=14 height=37 class=xl69 width=754 style='height:27.6pt;\n" +
                "  width:566pt'>货物配载清单</td>\n" +
                "  <td width=75 style='width:56pt'></td>\n" +
                " </tr>\n" +
                " <tr height=30 style='mso-height-source:userset;height:22.2pt'>\n" +
                "  <td colspan=2 height=30 class=xl80 style='height:22.2pt'>发车时间</td>\n" +
                "  <td colspan=2 class=xl76>"+factLeaveTime+"</td>\n" +
                "  <td class=xl81>车牌号：</td>\n" +
                "  <td class=xl77>"+shipment.getLiense()+"</td>\n" +
                "  <td class=xl82>司机：</td>\n" +
                "  <td class=xl78>"+shipment.getOutDriver()+"</td>\n" +
                "  <td class=xl79></td>\n" +
                "  <td class=xl83>电话：</td>\n" +
                "  <td colspan=3 class=xl76>"+shipment.getOutIphpne()+"</td>\n" +
                "  <td class=xl78></td>\n" +
                "  <td class=xl66></td>\n" +
                " </tr>\n" +
                " <tr height=30 style='mso-height-source:userset;height:22.8pt'>\n" +
                "  <td height=30 class=xl72 style='height:22.8pt;border-top:none'>起运站</td>\n" +
                "  <td class=xl72 style='border-top:none;border-left:none'>到达站</td>\n" +
                "  <td class=xl72 style='border-top:none;border-left:none'>发货人</td>\n" +
                "  <td class=xl72 style='border-top:none;border-left:none'>原单号</td>\n" +
                "  <td class=xl72 style='border-top:none;border-left:none'>收货人</td>\n" +
                "  <td class=xl72 style='border-top:none;border-left:none'>电话</td>\n" +
                "  <td class=xl72 style='border-left:none'>地址</td>\n" +
                "  <td class=xl72 style='border-left:none'>货物</td>\n" +
                "  <td class=xl72 style='border-left:none'>件数</td>\n" +
                "  <td class=xl72 style='border-left:none'>重量</td>\n" +
                "  <td class=xl72 style='border-top:none;border-left:none'>体积</td>\n" +
                "  <td class=xl73 width=31 style='border-top:none;border-left:none;width:23pt'>交货方式</td>\n" +
                "  <td class=xl73 width=48 style='border-top:none;border-left:none;width:36pt'>到付款</td>\n" +
                "  <td class=xl72 style='border-left:none'>备注</td>\n" +
                "  <td class=xl67></td>\n" +
                " </tr>";

        stringBuilder.append(begin);
        //循环开始
        List<Led> leds = shipment.getLeds();
        String zhong = null;
        String shouhuoren= null;//收货人
        String fahuoren = null;//发货人
        String productName = null;//货名
        String lianxidianhua = null;//联系电话
        String shdizhi = null;//收货地址
        int number = 0;
        double volume = 0.00;
        double weight = 0.00;
        double amount = 0.00;//运费
        Integer znumber = 0;
        Double zvolume = 0.00;
        Double zweight = 0.00;
        Double arrivePay =0.00;//到付
        String jiaofufangshi = null;
        String formorg = shipment.getFromOrganization().getName().substring(0,2);
        String toorg = shipment.getToOrganization().getName().substring(0, 2);
        if(leds.size()>0){
            int i  = 0;
            for (Led led :leds) {
                Order order = led.getOrder();
                i =i+1;
                Integer handoverType = order.getHandoverType();
                 if(handoverType == 0){
                     jiaofufangshi = "自提";
                 }else{
                     jiaofufangshi = "送货";
                 }
                List<OrderReceivingParty> orderReceivingParties = order.getOrderReceivingParties();
               // List<LedReceivingParty> ledReceivingParties = led.getLedReceivingParties();
                if(orderReceivingParties.size()>0){
                    for (OrderReceivingParty orderReceivingParty:orderReceivingParties) {
                        if(orderReceivingParty.getType() ==0){
                            fahuoren = orderReceivingParty.getContactperson();
                        }
                        if(orderReceivingParty.getType() ==1){
                            shouhuoren=orderReceivingParty.getContactperson();
                            shdizhi = orderReceivingParty.getDetailedAddress();
                            lianxidianhua =orderReceivingParty.getIphone();
                        }
                    }
                }
                List<OrderProduct> orderProducts = order.getOrderProducts();
               // List<LedProduct> ledProducts = led.getLedProducts();
                if(orderProducts.size()>0){
                    for (OrderProduct orderProduct:orderProducts) {
                        productName = orderProduct.getName();
                        number = orderProduct.getNumber();
                        volume = orderProduct.getVolume();
                        weight = orderProduct.getWeight();
                        znumber += number;
                        zvolume+=volume;
                        zweight+=weight;
                    }
                }
                List<LedgerDetail> ledgerDetails = order.getLedgerDetails();
                for (LedgerDetail ledgerDetail:ledgerDetails) {
                        arrivePay = ledgerDetail.getArrivePay();
                }
                if(arrivePay == null){
                    arrivePay = 0.00;
                }
                String description = order.getDescription();//备注
                if(description == null){
                     description = "";
                }
                zhong= " <tr height=18 style='height:13.8pt'>\n" +
                        "  <td height=18 class=xl70 width=43 style='height:13.8pt;border-top:none;width:32pt'>"+formorg+"</td>\n" +
                        "  <td class=xl70 width=42 style='border-top:none;border-left:none;width:31pt'>"+toorg+"</td>\n" +
                        "  <td class=xl70 width=61 style='border-top:none;border-left:none;width:46pt'>"+fahuoren+"</td>\n" +
                        "  <td class=xl70 width=50 style='border-top:none;border-left:none;width:38pt'>"+order.getRelatebill1()+"</td>\n" +
                        "  <td class=xl70 width=59 style='border-top:none;border-left:none;width:44pt'>"+shouhuoren+"</td>\n" +
                        "  <td class=xl70 width=65 style='border-top:none;border-left:none;width:49pt'>"+lianxidianhua+"</td>\n" +
                        "  <td class=xl70 width=86 style='border-top:none;border-left:none;width:65pt'>"+shdizhi+"</td>\n" +
                        "  <td class=xl70 width=62 style='border-top:none;border-left:none;width:47pt'>"+productName+"</td>\n" +
                        "  <td class=xl70 width=51 style='border-top:none;border-left:none;width:38pt'>"+number+"</td>\n" +
                        "  <td class=xl70 width=55 style='border-top:none;border-left:none;width:41pt'>"+weight+"</td>\n" +
                        "  <td class=xl70 width=57 style='border-top:none;border-left:none;width:43pt'>"+volume+"</td>\n" +
                        "  <td class=xl71 width=31 style='border-top:none;border-left:none;width:23pt'>"+jiaofufangshi+"</td>\n" +
                        "  <td class=xl71 width=48 style='border-top:none;border-left:none;width:36pt'>"+arrivePay+"</td>\n" +
                        "  <td class=xl71 width=44 style='border-top:none;border-left:none;width:33pt'>"+description+"</td>\n" +
                        "  <td class=xl68 width=75 style='width:56pt'></td>\n" +
                        " </tr>";
                stringBuilder.append(zhong);
            }
        }
        //循环结束
        double round = MathExtend.round(znumber.intValue(), 2);
        double round1 = MathExtend.round(zweight.doubleValue(), 2);
        double round2 = MathExtend.round(zvolume.doubleValue(), 2);
        String end = "<tr height=18 style='height:13.8pt'>\n" +
                "  <td height=18 class=xl72 style='height:13.8pt;border-top:none'>合计</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl75 style='border-top:none;border-left:none'>"+round+"</td>\n" +
                "  <td class=xl75 style='border-top:none;border-left:none'>"+round1+"</td>\n" +
                "  <td class=xl75 style='border-top:none;border-left:none'>"+round2+"</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl74 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td></td>\n" +
                " </tr>\n" +
                " <tr height=126 style='height:96.6pt;mso-xlrowspan:7'>\n" +
                "  <td height=126 colspan=15 style='height:96.6pt;mso-ignore:colspan'></td>\n" +
                " </tr>\n" +
                " <tr height=18 style='height:13.8pt'>\n" +
                "  <td height=18 colspan=7 style='height:13.8pt;mso-ignore:colspan'></td>\n" +
                "  <td class=xl65></td>\n" +
                "  <td colspan=7 style='mso-ignore:colspan'></td>\n" +
                " </tr>\n" +
                " <![if supportMisalignedColumns]>\n" +
                " <tr height=0 style='display:none'>\n" +
                "  <td width=43 style='width:32pt'></td>\n" +
                "  <td width=42 style='width:31pt'></td>\n" +
                "  <td width=61 style='width:46pt'></td>\n" +
                "  <td width=50 style='width:38pt'></td>\n" +
                "  <td width=59 style='width:44pt'></td>\n" +
                "  <td width=65 style='width:49pt'></td>\n" +
                "  <td width=86 style='width:65pt'></td>\n" +
                "  <td width=62 style='width:47pt'></td>\n" +
                "  <td width=51 style='width:38pt'></td>\n" +
                "  <td width=55 style='width:41pt'></td>\n" +
                "  <td width=57 style='width:43pt'></td>\n" +
                "  <td width=31 style='width:23pt'></td>\n" +
                "  <td width=48 style='width:36pt'></td>\n" +
                "  <td width=44 style='width:33pt'></td>\n" +
                "  <td width=75 style='width:56pt'></td>\n" +
                " </tr>\n" +
                " <![endif]>";


        stringBuilder.append(end);
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj(stringBuilder);
        return  returnModel.toJsonString();

    }



}
