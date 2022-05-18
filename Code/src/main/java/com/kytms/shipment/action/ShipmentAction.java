package com.kytms.shipment.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.MathExtend;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.SpringUtils;
import com.kytms.core.utils.StringUtils;
import com.kytms.settlement.service.impl.SettlementServiceImpl;
import com.kytms.shipment.ShipmentSql;
import com.kytms.shipment.service.ShipmentService;
import com.kytms.transportorder.dao.LedDao;
import com.kytms.transportorder.dao.impl.LedDaoImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/1/8.
 */
@Controller
@RequestMapping("/shipment")
public class ShipmentAction extends BaseAction {
    private final Logger log = Logger.getLogger(ShipmentAction.class);//输出Log日志
    private ShipmentService<Shipment> shipmentService;
    private LedDao ledDao = SpringUtils.getBean(LedDaoImpl.class);
    public ShipmentService<Shipment> getShipmentService() {
        return shipmentService;
    }
    @Resource(name = "ShipmentService")
    public void setShipmentService(ShipmentService<Shipment> shipmentService) {
        this.shipmentService = shipmentService;
    }

    @RequestMapping(value = "/getAbnormalList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getAbnormalList(CommModel commModel){
        Shipment shipment = (Shipment) shipmentService.selectBean(commModel.getId());
        List<Abnormal> abnormals = shipment.getAbnormals();
        return  returnJsonForBean(abnormals);
    }


    @RequestMapping(value = "/getShipmentList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getShipmentList(CommModel commModel) {
        JgGridListModel jgGridListModel = shipmentService.getShipmentList(commModel);
        return jgGridListModel.toJSONString();
    }
    @RequestMapping(value = "/getOrg", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrg() {
        String orgList=shipmentService.getOrg();
       return orgList;
    }

    @RequestMapping(value = "/getOrgww", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrgww(String  aaa) {
        String orgList=shipmentService.getOrgww(aaa);
        return orgList;
    }

    @RequestMapping(value = "/saveShipment", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveShipment(String str) {
        ReturnModel returnModel = getReturnModel();
        Shipment shipment = JSONObject.parseObject(str, Shipment.class);
        if (shipment.getTime() == null){
            returnModel.addError("time","运单时间不能为空");
        }
        if(shipment.getVehicle() == null){
            throw new MessageException("车型不能为空");
         }
        int status = shipment.getStatus();
        if (status == 0 || status ==1){

        }else {
            returnModel.addError("code","运单不能保存，请联系管理员");
        }
//            List<LedgerDetail> ledgerDetails = shipment.getLedgerDetails();
//          if(ledgerDetails.size()>0){
//              for (LedgerDetail ledgerDetail:ledgerDetails){
//                  double amount = ledgerDetail.getAmount();
//              }
//          }
        boolean result = returnModel.isResult();
        if (result){
            Shipment shipment1 = shipmentService.saveShipment(shipment);
            returnModel.setObj(shipment1);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/saveAbnormalShipment", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveAbnormalShipment(String str,String text) {
        ReturnModel returnModel = getReturnModel();
        Shipment shipment = JSONObject.parseObject(str, Shipment.class);
        if (shipment.getTime() == null){
            returnModel.addError("time","运单时间不能为空");
        }
        if(shipment.getVehicle() == null){
            throw new MessageException("车型不能为空");
        }
        boolean result = returnModel.isResult();
        if (result){
            Shipment shipment1 = shipmentService.saveAbnormalShipment(shipment,text);
            returnModel.setObj(shipment1);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/mTips", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String mTips(String id) {
        String messageMtips = "";
        if (StringUtils.isEmpty(id)){
            return null;
        }
        Led led = (Led) ledDao.selectBean(id);
        ReturnModel returnModel = getReturnModel();
        List<LedProduct> ledProducts = led.getLedProducts();
        StringBuilder sb = new StringBuilder(256);
        for (LedProduct ledProduct:ledProducts) {
            sb.append("货品名称:");
            sb.append(ledProduct.getName());
            sb.append("  ");
            sb.append("货品数量:");
            sb.append(ledProduct.getNumber());
            sb.append("  ");
            sb.append("货品重量:");
            sb.append(ledProduct.getWeight());
            sb.append("  ");
            sb.append("货品体积:");
            sb.append(ledProduct.getVolume());
            sb.append("  ");
            sb.append("货品货值:");
            sb.append(ledProduct.getValue());
            sb.append("  ");
            sb.append("货品备注:");
            sb.append(ledProduct.getDescription());
            sb.append("------------------");
        }
        returnModel.setObj(sb.toString());
        return returnModel.toJsonString();
    }


    @RequestMapping(value = "/getWbStowage", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getWbStowage(CommModel commModel) {
        if (StringUtils.isEmpty(commModel.getId())){
            return null;
        }
        String where = "";
        if(StringUtils.isNotEmpty(commModel.getWhereValue())){
            where =  " and "+commModel.getWhereName()+" like '%"+commModel.getWhereValue()+"%'";
        }
        LedDaoImpl ledDao = SpringUtils.getBean(LedDaoImpl.class);
        String hql1 = "SELECT a.id,a.code,aor.relatebill1," +
                "alps.name,aor.time,sze.name,eze.name,alps.contactperson," +
                "lps.name,a.number,a.volume,a.weight,a.value,aps.thAmount,aps.gxAmount,aps.shAmount,aps.qtAmount,aps.zAmount," +
                " sum(lls.amount),a.status,aor.description" +
                "  FROM JC_LED a left join a.organization b left join a.order aor  left join aor.startZone sz " +
                "left join sz.zone sze left join aor.endZone ez left join ez.zone eze " +
                "  LEFT JOIN aor.customer c left join a.shipments sts " +
                " left join aor.ledgerDetails lls " +
                "left join a.ledProducts lps " +
                " left join aor.orderReceivingParties alps left join a.apportionments aps" +
                " WHERE alps.type =1 and sts.id ='" +commModel.getId()+ "'" +  where +" group by a.code";
        List<Led> objs = ledDao.executeQueryHql(hql1);

        return returnJsonForBean(objs);
    }


    @RequestMapping(value = "/getStowage", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getStowage(CommModel commModel) {
        if (StringUtils.isEmpty(commModel.getId())){
        return null;
    }
    String where = "";
        if(StringUtils.isNotEmpty(commModel.getWhereValue())){
        where =  " and "+commModel.getWhereName()+" like '%"+commModel.getWhereValue()+"%'";
    }
    LedDaoImpl ledDao = SpringUtils.getBean(LedDaoImpl.class);
    // String hql = "FROM JC_LED a left join a.shipments b  WHERE b.id ='" +commModel.getId()+ "'" +  where;
    //case when a.costomerType=1 then c.name when a.costomerType=0 then a.costomerName end,
    String hql1 = "SELECT a.id,a.code,aor.relatebill1," +
            "alps.name,aor.time,sze.name,eze.name,alps.contactperson," +
            "lps.name,a.number,a.volume,a.weight,a.value,sum(lls.amount),a.status,aor.description" +
            "  FROM JC_LED a left join a.organization b left join a.order aor  left join aor.startZone sz " +
            "left join sz.zone sze left join aor.endZone ez left join ez.zone eze " +
            "  LEFT JOIN aor.customer c left join a.shipments sts " +
            " left join aor.ledgerDetails lls " +
            "left join a.ledProducts lps " +
            " left join aor.orderReceivingParties alps" +
            " WHERE alps.type =1 and sts.id ='" +commModel.getId()+ "'" +  where +" group by a.code";
    //        Shipment shipment = shipmentService.selectBean(commModel.getId());
//        List<Led> leds = shipment.getLeds();
    List<Led> objs = ledDao.executeQueryHql(hql1);
    // List<Led> list = new ArrayList<Led>();
//        for (Object object:objs) {
//            Object[] obj = (Object[]) object;
//            for (Object o:obj) {
//              //  if(o instanceof Led){
//                    Led led = (Led) o;
//                    list.add(led);
//              //  }
//            }
//        }
        return returnJsonForBean(objs);
    }

    @RequestMapping(value = "/getNotStowage", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getNotStowage(CommModel commModel) {
        commModel.setRows(1000);
        if (StringUtils.isEmpty(commModel.getId())){
            return null;
        }
        String where = " and a.status in (2,11) and b.id='"+SessionUtil.getOrgId()+"' and a.shipment is null" ;
        String orderBy = " group by a.code";
        //String orderBy = "";
        if(StringUtils.isNotEmpty(commModel.getWhereValue())){
            where =  " and "+commModel.getWhereName()+" like '%"+commModel.getWhereValue()+"%'";
        }
       // LedDaoImpl ledDao = SpringUtils.getBean(LedDaoImpl.class);
       // List<Led> leds = ledDao.executeQueryHql("FROM JC_LED WHERE (status= 2 or status =11)and organization.id = '" + SessionUtil.getOrgId() + "'" +where);
        JgGridListModel listByPageToHql = shipmentService.getListByPageToHql(ShipmentSql.SHIP_LEDLIST, ShipmentSql.SHIP_LEDCOUNT, commModel, where, orderBy);
//        List rows = listByPageToHql.getRows();
//        String s = JSONObject.toJSONString(rows);
//        List<Led> leds = JSONObject.parseArray(s, Led.class);
        return listByPageToHql.toJSONString();
    }

    @RequestMapping(value = "/addLed", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addLed(String ledId,String shipment) {
        ReturnModel returnModel = getReturnModel();
        try {
            shipmentService.addLed(ledId,shipment);
        }catch (RuntimeException e){
            returnModel.setResult(false);
            returnModel.setObj(e.getMessage());
        }
        return returnModel.toJsonString();
    }
    @RequestMapping(value = "/addLedyc", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addLedyc(String ledId,String shipment) {
        ReturnModel returnModel = getReturnModel();
        try {
            shipmentService.addLedyc(ledId,shipment);
        }catch (RuntimeException e){
            returnModel.setResult(false);
            returnModel.setObj(e.getMessage());
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/delLedyc", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String delLedyc(String ledId,String shipment) {
        shipmentService.delLedyc(ledId,shipment);
        return getReturnModel().toJsonString();
    }


    @RequestMapping(value = "/delLed", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String delLed(String ledId,String shipment) {
        shipmentService.delLed(ledId,shipment);
        return getReturnModel().toJsonString();
    }

    @RequestMapping(value = "/getAddressInfo", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getAddressInfo(String id) {
        if (StringUtils.isEmpty(id)){
            return null;
        }
        List list = shipmentService.getAddressInfo(id);
        return returnJsonForBean(list);
    }

    @RequestMapping(value = "/confirmShipment", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String confirmShipment(String id) {
        if (StringUtils.isEmpty(id)){
            return null;
        }
        ReturnModel returnModel = getReturnModel();
        try {
            Shipment shipment = shipmentService.confirmShipment(id);
            returnModel.setObj(shipment);
        }catch (MessageException msg){
            returnModel.addError("code",msg.getMessage());
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/cancelShipment", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String cancelShipment(String id) {
        ReturnModel returnModel = getReturnModel();
        try {
            Shipment shipment = shipmentService.cancelShipment(id);
            returnModel.setObj(shipment);
        }catch (MessageException msg){
            returnModel.addError("code",msg.getMessage());
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getShipment", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getShipment(String id) {
        String json = null;
        if (StringUtils.isEmpty(id)){
            return null;
        }
        Shipment shipment = shipmentService.selectBean(id);
              json = returnJsonForBean(shipment);
        return json;
    }

    @RequestMapping(value = "/updateRegTime", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String updateRegTime(String id, Timestamp date,String shipment) {
        shipmentService.updateRegTime(id,date,shipment);
        return getReturnModel().toJsonString();
    }

    @RequestMapping(value = "/updateYDTime", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String updateYDTime(String id, Timestamp date,String shipment) {
        shipmentService.updateYDTime(id,date,shipment);
        return getReturnModel().toJsonString();
    }


    @RequestMapping(value = "/selectDispatchFomShipmentBean", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String selectDispatchFomShipmentBean(String id) {
        Shipment o = shipmentService.selectBean(id);
        JSONObject jsonObject = new JSONObject();
        if (o.getCarrierType() ==1){ //如果是外部承运商，并且在当前组织机构下，自己发运
            if (o.getOrganization().getId().equals(SessionUtil.getOrgId())){
                jsonObject.put("isTrue",true);
            }else {
                jsonObject.put("isTrue",false);
            }
        }else {
            int specifyOrg = 0; //如果是内部承运商
            if (specifyOrg == 0){ //如果是派车方维护
                    jsonObject.put("isTrue",false);

            }else {
                if (o.getOrganization().getId().equals(SessionUtil.getOrgId())){
                    jsonObject.put("isTrue",true);
                }else {
                    jsonObject.put("isTrue",false);
                }
            }
        }
        jsonObject.put("obj",null);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/confirmShipmentAll", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String confirmShipmentAll(String id) {
        ReturnModel returnModel = getReturnModel();
        try {
            shipmentService.confirmShipmentAll(id);
        }catch (MessageException me){
            returnModel.setType(ReturnModel.STRING_FALSE);
            returnModel.setObj(me.getMessage());
        }

        return returnModel.toJsonString();
    }


    @RequestMapping(value = "/getFreeList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getFreeList(String id){
        Shipment o = shipmentService.selectBean(id);
        List<LedgerDetail> ledgerDetails = o.getLedgerDetails();
        return returnJsonForBean(ledgerDetails);
    }

    /**
     * 专门为别的机构查看到站车辆*/
    @RequestMapping(value = "/getRVShipmentList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getRVShipmentList(CommModel commModel) {
        JgGridListModel jgGridListModel = shipmentService.getRVShipmentList(commModel);
        return jgGridListModel.toJSONString();
    }
    /**
     * 专门为别的机构查看到站车辆*/
    @RequestMapping(value = "/getDZShipmentList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getDZShipmentList(CommModel commModel) {
        JgGridListModel jgGridListModel = shipmentService.getDZShipmentList(commModel);
        return jgGridListModel.toJSONString();
    }

    /**
     * 保存外包费用
     * @param str
     * @return
     */
    @RequestMapping(value = "/saveFY",produces = "text/json;charset=UTF-8")
    @ResponseBody
   public String saveFY(String str){
        ReturnModel returnModel = getReturnModel();
        String saveff = shipmentService.saveff(str);
          if(saveff.equals("成功")){
              returnModel.setObj("保存成功");
          }else{
              returnModel.setObj("保存失败");
          }

        return returnModel.toJsonString();
   }
    @RequestMapping(value = "/getShipmentPrint", produces = "text/json;charset=UTF-8")
    @ResponseBody
private String getShipmentPrint(String id){
        if(StringUtils.isEmpty(id)){
            throw  new MessageException("获取派车单失败");
        }
        Shipment shipment = (Shipment) shipmentService.selectBean(id);
        StringBuilder stringBuilder = new StringBuilder();
        String begin =" <col width=20 style='mso-width-source:userset;mso-width-alt:711;width:15pt'>\n" +
                " <col width=58 style='mso-width-source:userset;mso-width-alt:2048;width:43pt'>\n" +
                " <col width=60 style='mso-width-source:userset;mso-width-alt:2133;width:45pt'>\n" +
                " <col width=98 style='mso-width-source:userset;mso-width-alt:3498;width:74pt'>\n" +
                " <col width=60 style='mso-width-source:userset;mso-width-alt:2133;width:45pt'>\n" +
                " <col width=64 style='width:48pt'>\n" +
                " <col width=53 span=2 style='mso-width-source:userset;mso-width-alt:1877;\n" +
                " width:40pt'>\n" +
                " <col width=58 style='mso-width-source:userset;mso-width-alt:2048;width:43pt'>\n" +
                " <col width=57 style='mso-width-source:userset;mso-width-alt:2019;width:43pt'>\n" +
                " <col width=18 style='mso-width-source:userset;mso-width-alt:625;width:13pt'>\n" +
                " <tr height=18 style='height:13.8pt'>\n" +
                "  <td height=18 width=20 style='height:13.8pt;width:15pt'></td>\n" +
                "  <td class=xl66 width=58 style='width:43pt'>司机</td>\n" +
                "  <td class=xl66 width=58 style='width:43pt'>电话</td>\n" +
                "  <td class=xl66 width=58 style='width:43pt'>起运站</td>\n" +
                "  <td class=xl66 width=60 style='border-left:none;width:45pt'>到达站</td>\n" +
                "  <td class=xl66 width=98 style='border-left:none;width:110pt'>发货人</td>\n" +
                "  <td class=xl66 width=60 style='border-left:none;width:55pt'>原始单号</td>\n" +
                "  <td class=xl66 width=64 style='border-left:none;width:48pt'>收货人</td>\n" +
                "  <td class=xl66 width=53 style='border-left:none;width:55pt'>货物</td>\n" +
                "  <td class=xl66 width=53 style='border-left:none;width:40pt'>件数</td>\n" +
                "  <td class=xl66 width=58 style='border-left:none;width:43pt'>重量</td>\n" +
                "  <td class=xl66 width=57 style='border-left:none;width:43pt'>体积</td>\n" +
                "  <td class=xl66 width=200 style='border-left:none;width:200pt'>备注</td>\n" +
                " </tr>";

        stringBuilder.append(begin);
        //循环开始
        List<Led> leds = shipment.getLeds();
        String zhong = null;
        String shouhuoren= null;//收货人
        String fahuoren = null;//发货人
        String productName = null;//货名
        String siji = shipment.getOutDriver();//司机
        String dianhua =  shipment.getOutIphpne();//司机电话
        int number = 0;
        double volume = 0.00;
        double weight = 0.00;
        double amount = 0.00;//运费
        Integer znumber = 0;
        Double zvolume = 0.00;
        Double zweight = 0.00;
        String formorg = shipment.getFromOrganization().getName().substring(0,2);
        String toorg = shipment.getToOrganization().getName().substring(0, 2);

        if(leds.size()>0){
            int i  = 0;
            for (Led led :leds) {
                i =i+1;
                List<LedReceivingParty> ledReceivingParties = led.getLedReceivingParties();
                if(ledReceivingParties.size()>0){
                    for (LedReceivingParty ledReceivingParty:ledReceivingParties) {
                        if(ledReceivingParty.getType() ==0){
                            fahuoren = ledReceivingParty.getContactperson();
                        }
                        if(ledReceivingParty.getType() ==1){
                            shouhuoren=ledReceivingParty.getContactperson();
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
                zhong= "<tr height=18 style='height:13.8pt'>\n" +
                        "  <td height=18 style='height:13.8pt'></td>\n" +
                        "  <td class=xl68 style='border-top:none'>"+siji+"</td>\n" +
                        "  <td class=xl68 style='border-top:none'>"+dianhua+"</td>\n" +
                        "  <td class=xl68 style='border-top:none'>"+formorg+"</td>\n" +
                        "  <td class=xl68 style='border-top:none;border-left:none'>"+toorg+"</td>\n" +
                        "  <td class=xl68 style='border-top:none;border-left:none'>"+fahuoren+"</td>\n" +
                        "  <td class=xl68 style='border-top:none;border-left:none'>"+led.getRelatebill1()+"</td>\n" +
                        "  <td class=xl68 style='border-top:none;border-left:none'>"+shouhuoren+"</td>\n" +
                        "  <td class=xl68 style='border-top:none;border-left:none'>"+productName+"</td>\n" +
                        "  <td class=xl68 style='border-top:none;border-left:none'>"+number+"</td>\n" +
                        "  <td class=xl68 style='border-top:none;border-left:none'>"+weight+"</td>\n" +
                        "  <td class=xl68 style='border-top:none;border-left:none'>"+volume+"</td>\n" +
                        "  <td  contentEditable='true' class=xl68 style='border-top:none;border-left:none'></td>\n" +
                        " </tr>";
                stringBuilder.append(zhong);
            }
        }
        //循环结束
        double round = MathExtend.round(znumber.intValue(), 2);
        double round1 = MathExtend.round(zweight.doubleValue(), 2);
        double round2 = MathExtend.round(zvolume.doubleValue(), 2);
        String end = " <tr height=21 style='height:15.6pt'>\n" +
                "  <td height=21 style='height:15.6pt'></td>\n" +
                "  <td class=xl69 style='border-top:none'>合计</td>\n" +
                "  <td class=xl68 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl68 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl68 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl68 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl68 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl68 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl68 style='border-top:none;border-left:none'>　</td>\n" +
                "  <td class=xl67 style='border-top:none;border-left:none'>"+round+"</td>\n" +
                "  <td class=xl67 style='border-top:none;border-left:none'>"+round1+"</td>\n" +
                "  <td class=xl67 style='border-top:none;border-left:none'>"+round2+"</td>\n" +
                "  <td class=xl68 contentEditable='true' style='border-top:none;border-left:none'></td>\n" +
                " </tr>\n" +
                " <tr height=126 style='height:96.6pt;mso-xlrowspan:7'>\n" +
                "  <td height=126 colspan=10 style='height:96.6pt;mso-ignore:colspan'></td>\n" +
                " </tr>\n" +
                " <tr height=18 style='height:13.8pt'>\n" +
                "  <td height=18 colspan=6 style='height:13.8pt;mso-ignore:colspan'></td>\n" +
                "  <td class=xl65></td>\n" +
                "  <td colspan=3 style='mso-ignore:colspan'></td>\n" +
                " </tr>\n" +
                " <![if supportMisalignedColumns]>\n" +
                " <tr height=0 style='display:none'>\n" +
                "  <td width=20 style='width:15pt'></td>\n" +
                "  <td width=58 style='width:43pt'></td>\n" +
                "  <td width=60 style='width:45pt'></td>\n" +
                "  <td width=98 style='width:74pt'></td>\n" +
                "  <td width=60 style='width:45pt'></td>\n" +
                "  <td width=64 style='width:48pt'></td>\n" +
                "  <td width=53 style='width:40pt'></td>\n" +
                "  <td width=53 style='width:40pt'></td>\n" +
                "  <td width=58 style='width:43pt'></td>\n" +
                "  <td width=57 style='width:43pt'></td>\n" +

                " </tr>\n" +
                " <![endif]>";


        stringBuilder.append(end);
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj(stringBuilder);
        return  returnModel.toJsonString();

}

    @RequestMapping(value = "/getLedgerList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getLedgerList(CommModel commModel) {
        JgGridListModel jgGridListModel = shipmentService.getLedgerList(commModel);
        return jgGridListModel.toJSONString();
    }


    @RequestMapping(value = "/confirmLedger", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String confirmLedger(String id,String time) {
        ReturnModel returnModel = getReturnModel();
        try {
            shipmentService.confirmLedger(id,time);
        }catch (MessageException me){
            returnModel.setType(ReturnModel.STRING_FALSE);
            returnModel.setObj(me.getMessage());
        }

        return returnModel.toJsonString();
    }

    //反确认台账
    @RequestMapping(value = "/backConfirmLedger", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String backConfirmLedger(String id) {
        ReturnModel returnModel = getReturnModel();
        try {
            shipmentService.backConfirmLedger(id);
        }catch (MessageException me){
            returnModel.setType(ReturnModel.STRING_FALSE);
            returnModel.setObj(me.getMessage());
        }

        return returnModel.toJsonString();
    }
}
