package com.kytms.verification.action;

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.LedgerDetail;
import com.kytms.core.entity.Shipment;
import com.kytms.core.entity.VerificationRecord;
import com.kytms.core.entity.VerificationZb;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.DateUtils;
import com.kytms.core.utils.MoneyUtils;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.ledgerdetails.dao.LedgerDetailDao;
import com.kytms.shipment.service.ShipmentService;
import com.kytms.vehicleHead.service.impl.VehicleHeadServiceImpl;
import com.kytms.verification.VerificationSql;
import com.kytms.verification.service.VerificationRecordService;
import com.kytms.verification.service.VerificationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @Author:sundezeng
 * @Date:2018/11/2
 */
@Controller
@RequestMapping("/verification")
public class VerificationAction extends BaseAction {
    private final Logger log = Logger.getLogger(VerificationAction.class);//输出Log日志
    private VerificationService<VerificationZb> verificationService;
    private VerificationRecordService<VerificationRecord> verificationRecordService;
    private ShipmentService<Shipment> shipmentService;

    @Resource(name = "ShipmentService")
    public void setShipmentService(ShipmentService<Shipment> shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Resource(name = "VerificationRecordService")
    public void setVerificationRecordService(VerificationRecordService<VerificationRecord> verificationRecordService) {
        this.verificationRecordService = verificationRecordService;
    }

    @Resource(name = "VerificationService")
    public void setVerificationService(VerificationService<VerificationZb> verificationService) {
        this.verificationService = verificationService;
    }

    /**
     * 查询 收入核销
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getVerificationList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getVerificationList(CommModel commModel){

        String where =" and b.id='"+ SessionUtil.getOrgId()+"'";
        String orderBy =" order by a.create_Time desc";
        if("weiVerification".equals(commModel.getId())){//未核销 type =0 收入
            where += " and a.hxStatus = 0 and a.type =0 and a.order != null";
        }
        if("bufenVerification".equals(commModel.getId())){//部分核销
            where += " and a.hxStatus = 2 and a.type =0 and a.order != null";
        }
        if("yiVerification".equals(commModel.getId())){//已核销
            where += " and a.hxStatus = 1 and a.type =0 and a.order != null";
        }
        if(StringUtils.isEmpty(commModel.getId())){
            where += " and a.hxStatus =9999 and a.order != null";
        }
        JgGridListModel verification = verificationService.getListByPageToHql(VerificationSql.VERIFICATION_LIST,VerificationSql.VERIFICATION_COUNT,commModel,where,orderBy);
        return verification.toJSONString();
    }

    /**
     * 成本核销
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getVerCostList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getVerCostList(CommModel commModel){

        String where =" and b.id='"+ SessionUtil.getOrgId()+"'";
        String orderBy =" order by a.create_Time desc";
        if("weiVerification".equals(commModel.getId())){//未核销 type =1 成本
            where += " and a.hxStatus = 0 and a.type =1 and a.shipment != null ";
        }
        if("bufenVerification".equals(commModel.getId())){//部分核销
            where += " and a.hxStatus = 2 and a.type =1 and a.shipment != null ";
        }
        if("yiVerification".equals(commModel.getId())){//已核销
            where += " and a.hxStatus = 1 and a.type =1 and a.shipment != null ";
        }
        if(StringUtils.isEmpty(commModel.getId())){
            where += " and a.hxStatus =9999 and a.shipment != null";
        }
        JgGridListModel verification = verificationService.getListByPageToHql(VerificationSql.VERIFICATIONCB_LIST,VerificationSql.VERIFICATIONCB_COUNT,commModel,where,orderBy);
        return verification.toJSONString();
    }

    /**
     * 派车单核销
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getVerTPList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getVerTPList(CommModel commModel){

        String where =" and b.id='"+ SessionUtil.getOrgId()+"'";
        String orderBy =" order by a.create_Time desc";
        if("weiVerification".equals(commModel.getId())){//未核销 type =1 成本
            where += " and a.hxStatus = 0 and a.type =1 and a.single!= null";
        }
        if("bufenVerification".equals(commModel.getId())){//部分核销
            where += " and a.hxStatus = 2 and a.type =1 and a.single!= null";
        }
        if("yiVerification".equals(commModel.getId())){//已核销
            where += " and a.hxStatus = 1 and a.type =1 and a.single!= null";
        }
        if(StringUtils.isEmpty(commModel.getId())){
            where += " and a.hxStatus =9999 and a.single!= null ";
        }
        JgGridListModel verification = verificationService.getListByPageToHql(VerificationSql.VERIFICATIONTP_LIST,VerificationSql.VERIFICATIONTP_COUNT,commModel,where,orderBy);
        return verification.toJSONString();
    }

    @RequestMapping(value = "/saveVerification", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveVerification(VerificationRecord verificationRecord){
        ReturnModel returnModel = new ReturnModel();
        if(verificationRecord.getYhxmoney().doubleValue()>verificationRecord.getZmoney().doubleValue()){
            throw new MessageException("核销金额不能大于未核销金额");
        }else if(verificationRecord.getHxmoney().doubleValue()<0){
            throw new MessageException("核销金额不能为负数");
        }else{
            //System.currentTimeMillis(); 获取当前时间
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            verificationRecord.setOperator(SessionUtil.getUserName());
            if(verificationRecord.getZmoney() == verificationRecord.getHxmoney()){
                verificationRecord.setHxtype(1);
            }
            if(verificationRecord.getZmoney() > verificationRecord.getHxmoney()){
                verificationRecord.setHxtype(2);
            }
            verificationRecord.setHxTime(timestamp);

            VerificationRecord s = verificationRecordService.saveVerification(verificationRecord);

            return returnJsonForBean(s);
        }
    }

    @RequestMapping(value = "/getVerificationRecordList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getVerificationRecordList(CommModel commModel){
        String where=" and JC_VERIFICATIONZB_ID ='" + commModel.getId() +"'";
        String orderBy = null;

        JgGridListModel listByPage = verificationRecordService.getListByPageToHql(VerificationSql.RECORD_LIST,VerificationSql.RECORD_COUNT,commModel,where,orderBy);

        return listByPage.toJSONString();

                    }
//    @RequestMapping(value = "/getLedgerDetailList", produces = "text/json;charset=UTF-8")
//    @ResponseBody
//    public String getLedgerDetailList(String id){
//        CommModel commModel = new CommModel();
//        String where=" and JC_ORDER_ID ='" + id +"'";
//        String orderBy = null;
//      //  JgGridListModel listByPage = verificationRecordService.selectBean(id);
//        JgGridListModel listByPage = verificationService.getListByPageToHql(VerificationSql.LEDGERD_LIST,VerificationSql.LEDGERD_COUNT,commModel,where,orderBy);
//
//        return listByPage.toJSONString();
//
//    }
@RequestMapping(value = "/getAccountPrint", produces = "text/json;charset=UTF-8")
@ResponseBody
private String getAccountPrint(String id,String shipid){
    VerificationRecord verificationRecord = verificationRecordService.selectBean(id);
    Shipment shipment = shipmentService.selectBean(shipid);
    double amount= 0.00;
    String fname1 = null;
    String tname1 = null;
    String bumen = null;
         if(shipment != null){
             String fname = shipment.getFromOrganization().getName();
              fname1= fname.substring(0, 2);
             String tname = shipment.getToOrganization().getName();
             tname1 = tname.substring(0, 2);

         }
         if(fname1 =="上海" && tname1 =="广州"){
             bumen = "沪广专线部";
         }
    String s = MoneyUtils.toUppercase(verificationRecord.getHxmoney());
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    int year1 = DateUtils.getYear();
    int month = timestamp.getMonth();
    int date = timestamp.getDay();
    amount = verificationRecord.getHxmoney().doubleValue();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("bumen",bumen);
    jsonObject.put("year",year1);
    jsonObject.put("month",month);
    jsonObject.put("date",date);
    jsonObject.put("from",fname1);
    jsonObject.put("to",tname1);
    jsonObject.put("code",shipment.getCode());
    jsonObject.put("chepaihao",shipment.getLiense());
    jsonObject.put("amount",amount);
    jsonObject.put("dx",s);
    return jsonObject.toJSONString();
}



}
