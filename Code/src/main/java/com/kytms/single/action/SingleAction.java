package com.kytms.single.action;/**
 * Created by nidaye on 2018/10/16.
 */

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.shipmenttrack.service.Impl.ShipmentTrackServiceImpl;
import com.kytms.single.SingleSql;
import com.kytms.single.service.SingleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2018-10-16
 */

@Controller
@RequestMapping("/single")
public class SingleAction extends BaseAction{
    private final Logger log = Logger.getLogger(SingleAction.class);//输出Log日志
    private SingleService singleService;

    /**
     * 保存派车单
     * @param sing
     * @return
     */
    @RequestMapping(value = "/saveSingle", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveSingle(String sing){
        ReturnModel returnModel = getReturnModel();
        Single s = JSONObject.parseObject(sing, Single.class);
        Single single= singleService.saveSingle(s);
        returnModel.setObj(single);
        return returnJsonForBean(returnModel);
    }
    @RequestMapping(value = "/getSingleList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getSingleList(CommModel commModel){

        String where =" and c.id='"+ SessionUtil.getOrgId()+"'";
        if(commModel.getStatus()!= null && commModel.getStatus().equals("20")){
            where+= " and (a.status=1 or a.status =2) ";
        }
        String orderBy=" order by a.create_Time desc ";

        JgGridListModel single=  singleService.getListByPageToHql(SingleSql.SINGLE_LIST,SingleSql.SINGLE_COUNT,commModel,where,orderBy);
        return single.toJSONString();
    }

    @RequestMapping(value = "/getSingleLedgerDetailList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getSingleLedgerDetailList(CommModel commModel){
        Single o = (Single) singleService.selectBean(commModel.getId());
        List<LedgerDetail> ledgerDetails = o.getLedgerDetails();
        return  returnJsonForBean(ledgerDetails);
}

    /**
     * 获取配载明细
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/SingleDetail", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String SingleDetail(CommModel commModel){
        List list = singleService.SingleDetail(commModel.getId());
        return  returnJsonForBean(list);
    }
    @RequestMapping(value = "/saveSingleAbnormal", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveSingleAbnormal(String sing,String abnormal){
        ReturnModel returnModel = getReturnModel();
        Single s = JSONObject.parseObject(sing, Single.class);
        Single single= singleService.saveSingleAbnormal(s,abnormal);
        returnModel.setObj(single);
        return returnJsonForBean(returnModel);
    }
    @RequestMapping(value = "/getAbnormalList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getAbnormalList(CommModel commModel){
        Single single = (Single) singleService.selectBean(commModel.getId());
        List<Abnormal> abnormals = single.getAbnormals();
        return  returnJsonForBean(abnormals);
    }
    @RequestMapping(value = "/trimFeeType", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String trimFeeType(String id,String ledgerDetail){
        LedgerDetail ledgerDetail1 = JSONObject.parseObject(ledgerDetail, LedgerDetail.class);
        singleService.trimFeeType(id,ledgerDetail1);
       return  getReturnModel().toJsonString();
    }
    @RequestMapping(value = "/getFeeTypeCount", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getFeeTypeCount(String id){
        Single single = (Single) singleService.selectBean(id);
        List<LedgerDetail> ledgerDetails = single.getLedgerDetails();
        double countAmount = 0;
        double countInput = 0;
        for (LedgerDetail ledgerDetail:ledgerDetails) {
            countAmount +=ledgerDetail.getAmount();
            countInput +=ledgerDetail.getInput();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount",countAmount);
        jsonObject.put("input",countInput);
        return  jsonObject.toJSONString();
    }
    @RequestMapping(value = "/getNotStowage", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getNotStowage(CommModel commModel){
        List js = singleService.getNotStowage(commModel);
        return  returnJsonForBean(js);
    }
    @RequestMapping(value = "/getMapData", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getMapData(CommModel commModel){
        List js = singleService.getMapData(commModel);
        return  returnJsonForBean(js);
    }
    @RequestMapping(value = "/addOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addOrder(String id, String data){
        singleService.addOrder(id,data);
        return  getReturnModel().toJsonString();
    }
    @RequestMapping(value = "/delOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String delOrder(String id, String data){
        singleService.delOrder(id,data);
        return  getReturnModel().toJsonString();
    }
    @RequestMapping(value = "/getStowage", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getStowage(String id){
        List list = singleService.getStowage(id);
        return  returnJsonForBean(list);
    }

    @RequestMapping(value = "/getMk", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getMk(String id){
        String s =singleService.getMk(id);
        return s;
    }

    @RequestMapping(value = "/startExe", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String startExe(CommModel commModel){
        ReturnModel returnModel = getReturnModel();
        int i = singleService.startExe(commModel);
        returnModel.setObj("成功执行 "+i +" 任务");
        return  returnModel.toJsonString();
    }
    @RequestMapping(value = "/endExe", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String endExe(CommModel commModel){
        ReturnModel returnModel = getReturnModel();
        int i = singleService.endExe(commModel);
        returnModel.setObj("结束  "+i +" 任务");
        return  returnModel.toJsonString();
    }

    @Resource
    public void setSingleService(SingleService singleService) {
        this.singleService = singleService;
    }

    /**
     * 根据派车单ID获得台账明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/getSingleLedgerDList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getSingleLedgerDList(String id){
        Single o = (Single) singleService.selectBean(id);
        List<LedgerDetail> ledgerDetails = o.getLedgerDetails();
        return  returnJsonForBean(ledgerDetails);
    }
    /**
     * 根据派车单ID获得台账明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/getSinglePrint", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getSinglePrint(String id){
        if(StringUtils.isEmpty(id)){
            throw  new MessageException("获取派车单失败");
        }
        Single single = (Single) singleService.selectBean(id);
       StringBuilder stringBuilder = new StringBuilder();
        Integer printCount = single.getPrintCount();
        int sdf  = printCount + 1;
        single.setPrintCount(printCount+1);
        String begin =
                " <col width=27 style='mso-width-source:userset;mso-width-alt:967;width:20pt'>\n" +
                " <col width=42 style='mso-width-source:userset;mso-width-alt:1479;width:31pt'>\n" +
                " <col width=62 style='mso-width-source:userset;mso-width-alt:2218;width:47pt'>\n" +
                " <col width=58 style='mso-width-source:userset;mso-width-alt:2076;width:44pt'>\n" +
                " <col width=58 style='mso-width-source:userset;mso-width-alt:2048;width:43pt'>\n" +
                " <col width=42 style='mso-width-source:userset;mso-width-alt:1479;width:31pt'>\n" +
                " <col width=38 style='mso-width-source:userset;mso-width-alt:1365;width:29pt'>\n" +
                " <col width=40 style='mso-width-source:userset;mso-width-alt:1422;width:30pt'>\n" +
                " <col width=64 style='mso-width-source:userset;mso-width-alt:2275;width:48pt'>\n" +
                " <col width=40 style='mso-width-source:userset;mso-width-alt:1422;width:30pt'>\n" +
                " <col width=42 style='mso-width-source:userset;mso-width-alt:1479;width:31pt'>\n" +
                " <col width=52 style='mso-width-source:userset;mso-width-alt:1848;width:39pt'>\n" +
                " <col width=41 style='mso-width-source:userset;mso-width-alt:1450;width:31pt'>\n" +
                " <col width=43 style='mso-width-source:userset;mso-width-alt:1536;width:32pt'>\n" +
                " <col width=42 style='mso-width-source:userset;mso-width-alt:1507;width:32pt'>\n" +
                " <col width=50 style='mso-width-source:userset;mso-width-alt:1763;width:37pt'>\n" +
                " <col width=49 style='mso-width-source:userset;mso-width-alt:1735;width:37pt'>\n" +
                " <col width=55 style='mso-width-source:userset;mso-width-alt:1962;width:41pt'>\n" +
                " <tr height=48 style='mso-height-source:userset;height:36.0pt'>\n" +
                "  <td height=48 width=27 style='height:36.0pt;width:20pt'></td>\n" +
                "  <td width=42 style='width:31pt'></td>\n" +
                "  <td width=62 style='width:47pt'></td>\n" +
                "  <td width=58 style='width:44pt'></td>\n" +
                "  <td width=58 style='width:43pt'></td>\n" +
                "  <td width=42 style='width:31pt'></td>\n" +
                "  <td colspan=7 class=xl65 width=317 style='width:238pt'>派车单</td>\n" +
                "  <td width=43 style='width:32pt'></td>\n" +
                "  <td width=42 style='width:32pt'></td>\n" +
                "  <td width=50 style='width:37pt'></td>\n" +
                "  <td width=49 style='width:37pt'></td>\n" +
                "  <td width=55 style='width:41pt'></td>\n" +
                " </tr>\n" +
                " <tr height=21 style='height:15.6pt'>\n" +
                "  <td height=21 style='height:15.6pt'></td>\n" +
                "  <td colspan=2 class=xl70 width=104 style='width:78pt'>打印次数：</td>\n" +
                "  <td class=xl66 width=58 style='width:44pt'>第"+ sdf +"次</td>\n" +
                "  <td class=xl66 width=58 style='width:43pt'></td>\n" +
                "  <td class=xl66 width=42 style='width:31pt'></td>\n" +
                "  <td class=xl66 width=38 style='width:29pt'></td>\n" +
                "  <td class=xl66 width=40 style='width:30pt'></td>\n" +
                "  <td class=xl66 width=64 style='width:48pt'></td>\n" +
                "  <td class=xl66 width=40 style='width:30pt'></td>\n" +
                "  <td class=xl66 width=42 style='width:31pt'></td>\n" +
                "  <td class=xl66 width=52 style='width:39pt'></td>\n" +
                "  <td colspan=2 class=xl70 width=84 style='width:63pt'>提派单号：</td>\n" +
                "  <td colspan=4 class=xl66 width=196 style='width:147pt'>"+single.getCode()+"</td>\n" +
                " </tr>\n" +
                " <tr height=18 style='mso-height-source:userset;height:13.8pt'>\n" +
                "  <td height=18 style='height:13.8pt'></td>\n" +
                "  <td class=xl70 width=42 style='width:31pt'>日期：</td>\n" +
                "  <td colspan=2 class=xl70 width=120 style='border-left:none;width:91pt'>"+single.getDateBilling()+"</td>\n" +
                "  <td colspan=2 class=xl70 width=100 style='border-left:none;width:74pt'>承运商/车号：</td>\n" +
                "  <td colspan=2 class=xl70 width=78 style='border-left:none;width:59pt'>"+single.getVehicleHead()+"</td>\n" +
                "   <td colspan=2 class=xl72 width=71 style='border-right:.5pt solid black;border-left:none;width:53pt'>司机姓名：</td>\n" +
                "  <td colspan=2 class=xl72 width=85 style='border-right:.5pt solid black;border-left:none;width:64pt'>"+single.getDriver()+"</td>\n" +
                "  <td colspan=2 class=xl70 width=84 style='border-left:none;width:63pt'>联系方式：</td>\n" +
                "  <td colspan=2 class=xl70 width=92 style='border-left:none;width:69pt'>"+single.getDriverIphone()+"</td>\n" +
                "  <td class=xl70 width=49 style='border-top:none;border-left:none;width:37pt'>车型：</td>\n" +
                "  <td class=xl70 width=55 style='border-top:none;border-left:none;width:41pt'>"+single.getVehicle().getCode()+"</td>\n" +
                " </tr>\n" +
                " <tr height=18 style='height:13.8pt'>\n" +
                "  <td height=18 style='height:13.8pt'></td>\n" +
                "  <td class=xl70 width=42 style='border-top:none;width:31pt'>序号</td>\n" +
                "  <td class=xl70 width=62 style='border-top:none;border-left:none;width:47pt'>地址</td>\n" +
                "  <td class=xl70 width=58 style='border-top:none;border-left:none;width:44pt'>订单号</td>\n" +
                "  <td class=xl70 width=58 style='border-top:none;border-left:none;width:43pt'>原单号</td>\n" +
                "  <td class=xl70 width=42 style='border-top:none;border-left:none;width:31pt'>联系人</td>\n" +
                "  <td class=xl70 width=38 style='border-top:none;border-left:none;width:29pt'>货名</td>\n" +
                "  <td class=xl70 width=40 style='border-top:none;border-left:none;width:30pt'>件数</td>\n" +
                "  <td class=xl70 width=64 style='border-top:none;border-left:none;width:48pt'>重量</td>\n" +
                "  <td class=xl70 width=40 style='border-top:none;border-left:none;width:30pt'>体积</td>\n" +
                "  <td class=xl70 width=42 style='border-top:none;border-left:none;width:31pt'>运费</td>\n" +
                "  <td class=xl70 width=52 style='border-top:none;border-left:none;width:39pt'>上楼费</td>\n" +
                "  <td class=xl70 width=41 style='border-top:none;border-left:none;width:31pt'>装卸费</td>\n" +
                "  <td class=xl70 width=43 style='border-top:none;border-left:none;width:32pt'>其他费</td>\n" +
                "  <td class=xl70 width=42 style='border-top:none;border-left:none;width:32pt'>代收款</td>\n" +
                "  <td class=xl70 width=50 style='border-top:none;border-left:none;width:37pt'>现/到付</td>\n" +
                "  <td class=xl70 width=49 style='border-top:none;border-left:none;width:37pt'>发票号</td>\n" +
                "  <td class=xl70 width=55 style='border-top:none;border-left:none;width:41pt'>完成情况</td>\n" +
                " </tr>\n";
        stringBuilder.append(begin);
                //循环开始
        List<Led> leds = single.getLeds();
        String zhong = null;
        String detailedAddress= null;//详细地址
        String name = null;//联系人
        String productName = null;//货名
        int number = 0;
        double volume = 0.00;
        double weight = 0.00;
        double amount = 0.00;//运费
        double slamount = 0.00;//上楼费
        double zxamount = 0.00;//装卸费
        double qtamount = 0.00;//其他费
        int znumber = 0;
        double zvolume = 0.00;
        double zweight = 0.00;
        if(leds.size()>0){
            int i  = 0;
            for (Led led :leds) {
                i =i+1;
                List<LedReceivingParty> ledReceivingParties = led.getLedReceivingParties();
                if(ledReceivingParties.size()>0){
                for (LedReceivingParty ledReceivingParty:ledReceivingParties) {
                    if(ledReceivingParty.getType() ==0){
                        detailedAddress  = ledReceivingParty.getDetailedAddress();
                         name = ledReceivingParty.getContactperson();
                    }
                }
                }
                List<LedProduct> ledProducts = led.getLedProducts();
                if(ledProducts.size()>0){
                for (LedProduct ledProduct:ledProducts) {
                    productName = ledProduct.getName();
                    number = ledProduct.getNumber();
                    volume = ledProduct.getVolume();
                    weight = ledProduct.getWeight();

                    znumber += number;
                    zvolume+=volume;
                    zweight+=weight;
                }
                }
                List<LedgerDetail> ledgerDetails = single.getLedgerDetails();
                if(ledgerDetails.size()>0){
                for (LedgerDetail ledgerDetail:ledgerDetails) {
                      if("4028f08166809ebd016680aa2e1f0001".equals(ledgerDetail.getFeeType().getId())){
                          amount = ledgerDetail.getAmount();
                      }
                      if("402881a76729f9fd01672af53af20036".equals(ledgerDetail.getFeeType().getId())){
                          slamount =ledgerDetail.getAmount();
                      }
                    if("402881a76729f9fd01672af5760d003a".equals(ledgerDetail.getFeeType().getId())){
                        zxamount =ledgerDetail.getAmount();
                    }

                    if("402881a76729f9fd01672af62f790047".equals(ledgerDetail.getFeeType().getId())){
                        qtamount =ledgerDetail.getAmount();
                    }


                }
                }
                zhong=     "<tr>\n" +
                        "  <td ></td>\n" +
                        "  <td class=xl77 style='border-top:none'>"+i+"</td>\n" +
                        "  <td class=xl77 width=63 style='border-top:none;border-left:none;width:47pt'>"+detailedAddress+"</td>\n" +
                        "  <td class=xl77 width=58 style='border-top:none;border-left:none;width:44pt'>"+led.getCode()+"</td>\n" +
                        "  <td class=xl77 width=58 style='border-top:none;border-left:none;width:43pt'>"+led.getRelatebill1()+"</td>\n" +
                        "  <td class=xl77 width=50 style='border-top:none;border-left:none;width:37pt'>"+name+"</td>\n" +
                        "  <td class=xl77 width=43 style='border-top:none;border-left:none;width:32pt'>"+productName+"</td>\n" +
                        "  <td class=xl77 width=46 style='border-top:none;border-left:none;width:35pt'>"+number+"</td>\n" +
                        "  <td class=xl77 width=45 style='border-top:none;border-left:none;width:34pt'>"+weight+"</td>\n" +
                        "  <td class=xl77 width=42 style='border-top:none;border-left:none;width:32pt'>"+volume+"</td>\n" +
                        "  <td class=xl77 width=40 style='border-top:none;border-left:none;width:30pt'></td>\n" +
                        "  <td class=xl77 width=50 style='border-top:none;border-left:none;width:38pt'></td>\n" +
                        "  <td class=xl77 width=49 style='border-top:none;border-left:none;width:37pt'></td>\n" +
                        "  <td class=xl77 width=44 style='border-top:none;border-left:none;width:33pt'></td>\n" +
                        "  <td class=xl77 width=42 style='border-top:none;border-left:none;width:32pt'>"+single.getAgent()+"</td>\n" +
                        "  <td class=xl77 width=50 style='border-top:none;border-left:none;width:37pt'></td>\n" +
                        "  <td class=xl77 width=49 style='border-top:none;border-left:none;width:37pt'></td>\n" +
                        "  <td class=xl77 width=55 style='border-top:none;border-left:none;width:41pt'><span\n" +
                        "  style='mso-spacerun:yes'>&nbsp;</span></td>\n" +
                        " </tr>\n";
                stringBuilder.append(zhong);
            }
        }
        String toSendInfo = single.getToSendInfo();
        String ti = toSendInfo.substring(0, toSendInfo.indexOf("提"));
        String pai = toSendInfo.substring(toSendInfo.indexOf("/")+1, toSendInfo.indexOf("派"));
        //循环结束
           String end =     "<tr height=19 style='mso-height-source:userset;height:14.4pt'>\n" +
                   "  <td height=19 style='height:14.4pt'></td>\n" +
                   "  <td class=xl70 width=42 style='border-top:none;width:31pt'>总运费</td>\n" +
                   "  <td class=xl70 width=63 style='border-top:none;border-left:none;width:47pt'>"+amount+"</td>\n" +
                   "  <td class=xl70 width=58 style='border-top:none;border-left:none;width:44pt'>提票数</td>\n" +
                   "  <td class=xl70 width=58 style='border-top:none;border-left:none;width:43pt'>"+ti+"</td>\n" +
                   "  <td class=xl70 width=50 style='border-top:none;border-left:none;width:37pt'>派票数</td>\n" +
                   "  <td class=xl70 width=43 style='border-top:none;border-left:none;width:32pt'>"+pai+"</td>\n" +
                   "  <td class=xl70 width=46 style='border-top:none;border-left:none;width:35pt'>总重量</td>\n" +
                   "  <td class=xl70 width=45 style='border-top:none;border-left:none;width:34pt'>"+zweight+"</td>\n" +
                   "  <td class=xl70 width=42 style='border-top:none;border-left:none;width:32pt'>总体积</td>\n" +
                   "  <td class=xl70 width=42 style='border-top:none;border-left:none;width:31pt'>"+zvolume+"</td>\n" +
                   "  <td class=xl70 width=54 style='border-top:none;border-left:none;width:40pt'>总代收款</td>\n" +
                   "  <td class=xl70 width=49 style='border-top:none;border-left:none;width:37pt'>　</td>\n" +
                   "  <td class=xl70 width=44 style='border-top:none;border-left:none;width:33pt'>总到付</td>\n" +
                   "  <td class=xl70 width=42 style='border-top:none;border-left:none;width:32pt'>　</td>\n" +
                   "  <td class=xl70 width=50 style='border-top:none;border-left:none;width:37pt'>总现付</td>\n" +
                   "  <td class=xl70 width=49 style='border-top:none;border-left:none;width:37pt'>　</td>\n" +
                   "  <td class=xl70 width=55 style='border-top:none;border-left:none;width:41pt'>　</td>\n" +
                   " </tr>\n" +
                   " <tr height=18 style='height:13.8pt'>\n" +
                   "  <td height=18 style='height:13.8pt'></td>\n" +
                   "  <td colspan=7 class=xl72 width=360 style='border-right:.5pt solid black;width:269pt'></td>\n" +
                   "  <td colspan=2 class=xl70 width=104 style='border-left:none;width:78pt'>发票数量</td>\n" +
                   "  <td class=xl70 align=right width=42 style='border-top:none;border-left:none;width:31pt'>22</td>\n" +
                   "  <td class=xl70 width=54 style='border-top:none;border-left:none;width:40pt'>代付款<span style='mso-spacerun:yes'>&nbsp;</span></td>\n" +
                   "  <td class=xl70 width=49 style='border-top:none;border-left:none;width:37pt'>　</td>\n" +
                   "  <td class=xl70 width=44 style='border-top:none;border-left:none;width:33pt'>装卸费</td>\n" +
                   "  <td class=xl70 width=42 style='border-top:none;border-left:none;width:32pt'>"+zxamount+"</td>\n" +
                   "  <td colspan=2 class=xl70 width=99 style='border-left:none;width:74pt'>其他费用</td>\n" +
                   "  <td class=xl70 width=55 style='border-top:none;border-left:none;width:41pt'>"+qtamount+"</td>\n" +
                   " </tr>\n" +
                   " <tr height=18 style='height:13.8pt'>\n" +
                   "  <td height=18 style='height:13.8pt'></td>\n" +
                   "  <td colspan=2 class=xl70 width=105 style='width:78pt'>发车时间：</td>\n" +
                   "  <td colspan=2 class=xl70 width=116 style='border-left:none;width:87pt'>"+single.getPlanStartTime()+"</td>\n" +
                   "  <td colspan=2 class=xl70 width=93 style='border-left:none;width:69pt'>门卫签字：</td>\n" +
                   "  <td colspan=2 class=xl70 width=91 style='border-left:none;width:69pt'></td>\n" +
                   "  <td colspan=2 class=xl70 width=84 style='border-left:none;width:63pt'>返回时间：</td>\n" +
                   "  <td colspan=2 class=xl70 width=103 style='border-left:none;width:77pt'>"+single.getPlanEndTime()+"</td>\n" +
                   "  <td colspan=2 class=xl70 width=86 style='width:65pt'></td>\n" +
                   "  <td class=xl77 width=50 style='width:37pt'></td>\n" +
                   "  <td class=xl77 width=49 style='width:37pt'></td>\n" +
                   "  <td class=xl77 width=55 style='width:41pt'></td>\n" +
                   " </tr>\n" +
                   " <tr height=18 style='height:13.8pt'>\n" +
                   "  <td height=18 style='height:13.8pt'></td>\n" +
                   "  <td colspan=2 class=xl70 width=105 style='width:78pt'>调度签字：</td>\n" +
                   "  <td colspan=2 class=xl70 width=116 style='border-left:none;width:87pt'></td>\n" +
                   "  <td colspan=2 class=xl70 width=93 style='border-left:none;width:69pt'>司机签字：</td>\n" +
                   "  <td colspan=2 class=xl70 width=91 style='border-left:none;width:69pt'></td>\n" +
                   "  <td class=xl67 width=42 style='width:32pt'></td>\n" +
                   "  <td class=xl67 width=42 style='width:31pt'></td>\n" +
                   "  <td class=xl67 width=54 style='width:40pt'></td>\n" +
                   "  <td class=xl67 width=49 style='width:37pt'></td>\n" +
                   "  <td class=xl67 width=44 style='width:33pt'></td>\n" +
                   "  <td class=xl67 width=42 style='width:32pt'></td>\n" +
                   "  <td class=xl67 width=50 style='width:37pt'></td>\n" +
                   "  <td class=xl67 width=49 style='width:37pt'></td>\n" +
                   "  <td class=xl67 width=55 style='width:41pt'></td>\n" +
                   " </tr>\n" +
                   " <![if supportMisalignedColumns]>\n" +
                   " <tr height=0 style='display:none'>\n" +
                   "  <td width=27 style='width:20pt'></td>\n" +
                   "  <td width=42 style='width:31pt'></td>\n" +
                   "  <td width=63 style='width:47pt'></td>\n" +
                   "  <td width=58 style='width:44pt'></td>\n" +
                   "  <td width=58 style='width:43pt'></td>\n" +
                   "  <td width=50 style='width:37pt'></td>\n" +
                   "  <td width=43 style='width:32pt'></td>\n" +
                   "  <td width=46 style='width:35pt'></td>\n" +
                   "  <td width=45 style='width:34pt'></td>\n" +
                   "  <td width=42 style='width:32pt'></td>\n" +
                   "  <td width=42 style='width:31pt'></td>\n" +
                   "  <td width=54 style='width:40pt'></td>\n" +
                   "  <td width=49 style='width:37pt'></td>\n" +
                   "  <td width=44 style='width:33pt'></td>\n" +
                   "  <td width=42 style='width:32pt'></td>\n" +
                   "  <td width=50 style='width:37pt'></td>\n" +
                   "  <td width=49 style='width:37pt'></td>\n" +
                   "  <td width=55 style='width:41pt'></td>\n" +
                   " </tr>\n" +
                   " <![endif]>";


           stringBuilder.append(end);
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj(stringBuilder);
//        i++;
//        single.setPrintCount(i);
        return  returnModel.toJsonString();
    }

}
