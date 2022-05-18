package com.kytms.driverset.action;

import com.alibaba.fastjson.annotation.JSONField;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Driver;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.DateUtils;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.core.utils.ValidUtils;
import com.kytms.deliveryPrice.service.impl.DeliveryPriceServiceImpl;
import com.kytms.driverset.service.DriverService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/12/4 0004.
 * 司机Action
 */
@Controller
@RequestMapping("/driver")
public class DriverAction extends BaseAction{
    private final Logger log = Logger.getLogger(DriverAction.class);//输出Log日志
    private DriverService<Driver> driverService;
    private final Map<String,Object> map = new HashMap<String,Object>();
    @JSONField(serialize=false)
    private boolean result = true;
    public DriverService<Driver> getDriverService() {
        return driverService;
    }
    @Resource(name = "DriverService")
    public void setDriverService(DriverService<Driver> driverService) {

        this.driverService = driverService;
    }




    @RequestMapping(value = "/getDriverList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getDriverList(CommModel commModel){
        JgGridListModel jgGridListModel =driverService.getDriverList(commModel);
        return jgGridListModel.toJSONString();
    }

    public void addError(String key,Object value){
        this.result = false;
        this.map.put(key,value);
    }

    @RequestMapping(value = "/saveDriverBean", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveDriverBean(Driver driver) {
        ReturnModel returnModel = getReturnModel();
        returnModel.codeUniqueAndNull(driver,driverService);
        if (StringUtils.isEmpty(driver.getName())){
            returnModel.addError("name","名称不能为空");
        }
        int aa =0;
        int iAge = 0;
        Timestamp birthday2 ;
        String sGender = "";
        String card = driver.getCard();//获取身份证;
//        String abcd = driver.getOilCards();
//        System.out.print(abcd);
        if (card != null){//健壮性判定

         boolean idCard = ValidUtils.isIDCard(card);//身份证 验证 18位
        if (idCard) {// 如果身份证正确 验证成功 返回位TRUE
           String year1 = card.substring(6, 10);//获取身份证年
           String month1 = card.substring(10, 12);//获取身份证月
           String day1 = card.substring(12, 14);//获取身份证月
           String birthday3 = year1+"-"+month1+"-"+day1;

            Timestamp birthday1= null;
            try {
                birthday1 = DateUtils.parseTimestamp(birthday3,"yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            driver.setBirthday(birthday1);
            //计算年纪
            Calendar cal = Calendar.getInstance();
            String year = card.substring(6, 10);
            int iCurrYear = cal.get(Calendar.YEAR);
            iAge = iCurrYear - Integer.valueOf(year);
             //计算性别
            String sCardNum = card.substring(16, 17);
            if (Integer.parseInt(sCardNum) % 2 != 0) {
                sGender = "男";//男
            } else {
                sGender = "女";//女
            }
            driver.setAge(iAge);
            driver.setSex(sGender);
        }
        }
        boolean result = returnModel.isResult();
        if (result){
            driver.setOrganization(SessionUtil.getOrg());
            driverService.saveBean(driver);
        }
        return returnModel.toJsonString();
    }

    /**
     * 用于获取司机下拉列表通用方法（陈小龙）
     * @return
     */
    @RequestMapping(value = "/getDriveTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getDriveTree(){
         String where= " and status != 0";
        List<Driver> rows = driverService.selectList(new CommModel(),where,null);
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (Driver driver:rows ){
            TreeModel treeModel = new TreeModel();
            treeModel.setId(driver.getId());
            treeModel.setText(driver.getName());
            treeModels.add(treeModel);
        }
        return returnJson(treeModels);
    }

    @RequestMapping(value = "/getDrivea", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getDrivea(String name){
        List<Map> list  = driverService.getDrivea(name);
        return returnJson(list);
    }



}