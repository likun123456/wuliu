package com.kytms.transportorder.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.*;
import com.kytms.ledgerdetails.dao.LedgerDetailDao;
import com.kytms.ledgerdetails.dao.impl.LedgerDetailDaoImpl;
import com.kytms.system.service.DataBookService;
import com.kytms.transportorder.OrderStatus;
import com.kytms.transportorder.service.LedService;
import com.kytms.transportorder.service.TransportOrderService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 *订单
 * @author 奇趣源码
 * @create 2018-01-05
 */
@Controller
@RequestMapping("/transportorder")
public class TransportOrderAction extends BaseAction {
    private TransportOrderService transportOrderService;
    private LedService<Led> ledService;
    private DataBookService dataBookService;
    private Logger log = Logger.getLogger(TransportOrderAction.class);//输出Log日志




    @Resource
    public void setLedService(LedService<Led> ledService) {
        this.ledService = ledService;
    }
    @Resource
    public void setTransportOrderService(TransportOrderService transportOrderService) {
        this.transportOrderService = transportOrderService;
    }
    @Resource
    public void setDataBookService(DataBookService dataBookService) {
        this.dataBookService = dataBookService;
    }

    /**
     * 查找收货方发货方
     * @param name
     * @return
     */
    @RequestMapping(value = "/queryRP", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String queryRP(String name) {
        JSONArray o =transportOrderService.queryRP(name);
        return o.toJSONString();
    }

    @RequestMapping(value = "/getAbnormalList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getAbnormalList(CommModel commModel){
        Order order = (Order) transportOrderService.selectBean(commModel.getId());
        List<Abnormal> abnormals = order.getAbnormals();
        return  returnJsonForBean(abnormals);
    }

    @RequestMapping(value = "/saveOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveOrder(String order) {
        Order order1 = JSONObject.parseObject(order, Order.class);
        ReturnModel returnModel = orderValid(order1);

        if (returnModel.isResult()) {
            Order o = (Order) transportOrderService.saveOrder(order1);
            returnModel.setObj(o);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/saveAbnormalOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveAbnormalOrder(String order, Timestamp time,String text) {
        Order order1 = JSONObject.parseObject(order, Order.class);
        ReturnModel returnModel = orderValid(order1);
        Order o = null;
            try {
                if (returnModel.isResult()) {
                    o =(Order) transportOrderService.saveAbnormalOrder(order1,time,text);
                    returnModel.setObj(o);
                }
            }catch (Exception e){
                e.printStackTrace();
                returnModel.addError("code",e.getMessage());
                returnModel.setResult(false);
            }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getReceivingParty", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getReceivingParty(String id) {
        StringUtils.isEmpty(id);
        return getReturnModel().toJsonString();
    }

    @RequestMapping(value = "/getOrderGrid", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrderGrid(CommModel commModel) {
        JgGridListModel jgGridListModel = transportOrderService.getOrderGrid(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/confirmOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
        public String confirmOrder(String id) {
        ReturnModel returnModel = getReturnModel();
        try {
            transportOrderService.confirmOrder(id);
        }catch (MessageException me){
            returnModel.setType(ReturnModel.STRING_FALSE);
            returnModel.setObj(me.getMessage());
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrder(CommModel commModel) {
        Order order = (Order) transportOrderService.selectBean(commModel.getId());
        String json = returnJsonForBean(order);
        return json;
    }

    /**
     * 订单打印
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getOrderPrint", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrderPrint(CommModel commModel) {
        Order order = (Order) transportOrderService.selectBean(commModel.getId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("relatebill1",order.getRelatebill1());
        Timestamp time = order.getTime();
        jsonObject.put("timeYear",time.getYear()+1900);
        jsonObject.put("timeMonht",time.getMonth()+1);
        jsonObject.put("timeDay",time.getDate());
        Timestamp planArriveTime = order.getPlanArriveTime();
        if(planArriveTime !=null){
            jsonObject.put("planArriveTimeYear",planArriveTime.getYear()+1900);
            jsonObject.put("planArriveTimeMonht",planArriveTime.getMonth()+1);
            jsonObject.put("planArriveTimeDay",planArriveTime.getDate());
        }
        Zone zone = order.getStartZone().getZone();
        String sz = selectZone(zone);
        Zone zone1 = order.getEndZone().getZone();
        String ez = selectZone(zone1);
        jsonObject.put("startZone",sz);
        jsonObject.put("endZone",ez);
        List<OrderReceivingParty> orderReceivingParties = order.getOrderReceivingParties();
        for (OrderReceivingParty orderReceivingParty:orderReceivingParties) {
            if(orderReceivingParty.getType() == 0){
                jsonObject.put("FHF",orderReceivingParty);
            }else {
                jsonObject.put("SHF",orderReceivingParty);
            }
        }
        double weight = 0; //重量
        double volume = 0; //体积
        double value= 0; //货值
        double number = 0; //数量
        List<OrderProduct> orderProducts = order.getOrderProducts();
        StringBuilder unit = new StringBuilder();
        StringBuffer name = new StringBuffer();
        for (OrderProduct orderProduct: orderProducts) {
            weight += orderProduct.getWeight();
            volume += orderProduct.getVolume();
            value += orderProduct.getValue();
            number += orderProduct.getNumber();
            name.append(orderProduct.getName()+"/");
            unit.append(orderProduct.getUnit()+"/");
        }
        unit.deleteCharAt(unit.lastIndexOf("/"));
        name.deleteCharAt(name.lastIndexOf("/"));
        jsonObject.put("weight",weight);
        jsonObject.put("volume",volume);
        jsonObject.put("value",value);
        jsonObject.put("number",number);
        jsonObject.put("unit",unit.toString());
        jsonObject.put("productNmae",name.toString());
        jsonObject.put("description",order.getDescription());
        jsonObject.put("transportPro",dataBookService.getDataBookValue("TransportPro",order.getTransportPro().toString()));
        jsonObject.put("create_Name",order.getCreate_Name());
        jsonObject.put("handoverType",dataBookService.getDataBookValue("HandoverType",order.getHandoverType().toString()));
        List<LedgerDetail> ledgerDetails = order.getLedgerDetails();
        double zongji = 0;
        double daishou = 0;
        double znowPay =0.0;// 现付
        double zarrivePay =0.0;// 到付
        double zbackPay =0.0;// 回付
        double zmonthPay =0.0;// 月结
        for (LedgerDetail ledgerDetail : ledgerDetails) {
            if(ledgerDetail.getFeeType().getIsCount() == 0){
                zongji = zongji + ledgerDetail.getAmount();
                if(ledgerDetail.getNowPay() != null){
                    znowPay += ledgerDetail.getNowPay().doubleValue();
                }
                if(ledgerDetail.getArrivePay() != null){
                    zarrivePay += ledgerDetail.getArrivePay().doubleValue();
                }
                if(ledgerDetail.getBackPay() != null){
                    zbackPay += ledgerDetail.getBackPay().doubleValue();
                }
                if(ledgerDetail.getMonthPay() != null){
                    zmonthPay += ledgerDetail.getMonthPay().doubleValue();
                }
                jsonObject.put(ledgerDetail.getFeeType().getName(),ledgerDetail.getAmount());
            }else {
                daishou +=ledgerDetail.getAmount();
            }
        }
        jsonObject.put("znowPay",znowPay);
        jsonObject.put("zarrivePay",zarrivePay);
        jsonObject.put("zbackPay",zbackPay);
        jsonObject.put("zmonthPay",zmonthPay);
        jsonObject.put("zongji",zongji);
        jsonObject.put("daishou",daishou);
        jsonObject.put("zongjiDZ", MoneyUtils.toUppercase(zongji));
        jsonObject.put("backNumber",order.getBackNumber());
        jsonObject.put("isBack",order.getIsBack());
        jsonObject.put("relatebill1",order.getRelatebill1());

        return jsonObject.toJSONString();
    }

    private String selectZone(Zone zone) {
        if(zone.getLevel().equals("province") || zone.getLevel().equals("city")||zone.getLevel().equals("country")){
                return zone.getName();
        }else {
            return selectZone(zone.getZone());
        }
    }

    @RequestMapping(value = "/deleteOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String deleteOrder(String tableName,String id,String status){
        ReturnModel returnModel = getReturnModel();
        Order order = (Order) transportOrderService.selectBean(id);
        if (order.getStatus() != OrderStatus.ACTIVE.getValue()){
            returnModel.setResult(false);
            returnModel.setObj("只能删除保存状态下的订单");
        }
        if (returnModel.isResult()){
            order.setStatus(0);
            returnModel.setObj("状态修改成功");
            transportOrderService.saveBean(order);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getTrack", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getTrack(String id){
        Order order = (Order) transportOrderService.selectBean(id);
        return returnJsonForBean(order.getOrderTracks());
    }

    //订单验证
    private ReturnModel orderValid(Order order1){
        ReturnModel returnModel = getReturnModel();
        if (order1.getCustomer() == null || StringUtils.isEmpty(order1.getCustomer().getId())){//处理客户名称为空
            order1.setCustomer(null);
        }
        if (order1.getTime() == null) {
            returnModel.addError("time", "受理时间不能为空");
        }
        if(order1.getId() == null){
        String hql1 ="  and relatebill1='"+order1.getRelatebill1()+"'";
        // List<Order> orders = transportOrderDao.executeQueryHql(hql1);
          CommModel commModel = new CommModel();
          List list = transportOrderService.selectList(commModel, hql1, null);
           if(list.size()>=1){
              returnModel.addError("relatebill1","发运单号重复，请重新录入");
           }
        }
        if (order1.getCostomerType() == 1 && order1.getCustomer() == null) {
            returnModel.addError("costomerType", "客户类型为合同事必须选择客户");
        }

        if (order1.getFeeType() == 0 && order1.getCostomerType() == 0) {
            returnModel.addError("feeType", "月结必须是合同客户");
        }
        List<OrderReceivingParty> orderReceivingParties = order1.getOrderReceivingParties();
        if (orderReceivingParties == null || orderReceivingParties.size() < 2) {
            returnModel.addError("code", "收发货放信息不全");
        }
        if(order1.getStartZone()==null){
            returnModel.addError("startZone","出发运点必须填写");
        }
        if(order1.getToOrganization() == null){
            returnModel.addError("toOrganization","目的网站必须填写");
        }

        if(order1.getEndZone() == null){
             returnModel.addError("endZone","目的运点必须填写");
        }
      /*  for (OrderReceivingParty orderReceivingParty : orderReceivingParties) {
            if (StringUtils.isEmpty(orderReceivingParty.getName())) {
                returnModel.addError("code", "收发货放单位名称不全");
            }
            if (StringUtils.isEmpty(orderReceivingParty.getAddress())) {
                returnModel.addError("code", "地址信息不全");
            }
            if (StringUtils.isEmpty(orderReceivingParty.getLtl())) {
                returnModel.addError("code", "坐标信息不全");
            }
            if (StringUtils.isEmpty(orderReceivingParty.getContactperson())) {
                returnModel.addError("code", "联系人信息不全");
            }
            if (StringUtils.isEmpty(orderReceivingParty.getIphone())) {
                returnModel.addError("code", "电话信息不全");
            }
            if (orderReceivingParty.getZone() == null) {
                returnModel.addError("code", "城市信息不全");
            }
        }*/
        List<OrderProduct> orderProducts = order1.getOrderProducts();

        if (orderProducts == null || orderProducts.size() < 1) {
            returnModel.addError("code", "货品信息资料不全");
        }
        for (OrderProduct orderProduct : orderProducts) {
            if (StringUtils.isEmpty(orderProduct.getName())) {
                returnModel.addError("code", "货品名称不能为空");
            }
            if (orderProduct.getNumber() == 0) {
                returnModel.addError("code", "货品数量不能为0");
            }
            if (StringUtils.isEmpty(orderProduct.getUnit())) {
                returnModel.addError("code", "货品单位不能为空");
            }
        }
        //台账验证
        List<LedgerDetail> ledgerDetails = order1.getLedgerDetails();
        if(ledgerDetails.size() ==0){
            returnModel.addError("gridTable2","费用必须填写");
        }
        for (LedgerDetail ledgerDetail:ledgerDetails){
            double amount = ledgerDetail.getAmount();
            double arrivePay =0.0;
            double nowPay =0.0;
            double monthPay=0.0;
            double backPay =0.0;
            if(ledgerDetail.getArrivePay() != null){
                arrivePay = ledgerDetail.getArrivePay();
            }
            if(ledgerDetail.getNowPay() != null){
                nowPay = ledgerDetail.getNowPay();
            }
            if(ledgerDetail.getMonthPay() != null){
                monthPay = ledgerDetail.getMonthPay();
            }
            if(ledgerDetail.getBackPay() != null){
                backPay = ledgerDetail.getBackPay();
            }
            /*if(amount <(arrivePay+nowPay+monthPay+backPay) ){
                returnModel.addError("code","现付，到付，回付，还有一个什么付加一起不能超过费用金额");
            }*/
            if(order1.getHandoverType()== 0 ){
                FeeType feeType = ledgerDetail.getFeeType();
                 if(feeType.getId().equals("402881a5670b969e01670bea7cf80050")){
                       if(amount>0){
                            returnModel.addError("salePersion","交付方式是自提的，没有送货费");
                       }
                 }
            }
        }

        //处理客户类型
        if (order1.getCostomerType() == 0) { //如果是零散客户，客户设为空
            order1.setCustomer(null);
        }
        return returnModel;
    }

    //复制新建
    @RequestMapping(value = "/copyOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String copyOrder(String id) {
        ReturnModel returnModel = getReturnModel();
        try {
            transportOrderService.copyOrder(id);
        }catch (MessageException me){
            returnModel.setType(ReturnModel.STRING_FALSE);
            returnModel.setObj(me.getMessage());
        }

        return returnModel.toJsonString();
    }

    //获取分段订单
    @RequestMapping(value = "/getLedList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getLedList(CommModel commModel){
        JgGridListModel jgGridListModel =ledService.getList(commModel);
        return jgGridListModel.toJSONString();
    }
    //获取运单所配载的分段订单
    @RequestMapping(value = "/getDZLedList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getDZLedList(String id){
        JgGridListModel jgGridListModel =ledService.getDZLedList(id);
        return jgGridListModel.toJSONString();
    }
    /**
     * 获取待发运分段订单
     * */
    @RequestMapping(value = "/getOrderDy", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public  String getOrderDy(CommModel commModel){
        String orderDy = ledService.getOrderDy(commModel);
        return orderDy;
    }

    @RequestMapping(value = "/getOrderLedProductList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrderLedProductList(CommModel commModel){
        Led led =  ledService.selectBean(commModel.getId());
        List<LedProduct> ledProducts = led.getLedProducts();
        return returnJsonForBean(ledProducts);
    }

    @RequestMapping(value = "/getOrderYS", produces = "text/json;charset=UTF-8")
    @ResponseBody
   public String getOrderYS(CommModel commModel){
        JgGridListModel jgGridListModel =ledService.getOrderYS(commModel);
        return jgGridListModel.toJSONString();
   }
    @RequestMapping(value = "/getOrderYQS", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrderYQS(CommModel commModel){
        JgGridListModel jgGridListModel =ledService.getOrderYQS(commModel);
        return jgGridListModel.toJSONString();
    }


    @RequestMapping(value = "/peizaiShip", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String peizaiShip(String[] ids,String templateId) {
        ReturnModel returnModel = getReturnModel();
        try {
            transportOrderService.peizaiShip(ids,templateId);
        }catch (MessageException me){
            returnModel.setType(ReturnModel.STRING_FALSE);
            returnModel.setObj(me.getMessage());
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getFreeList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getFreeList(String id){
        LedgerDetailDao ledgerDetailDao = SpringUtils.getBean(LedgerDetailDaoImpl.class);
        List<LedgerDetail> ledgerDetails = ledgerDetailDao.executeQueryHql("select a from JC_LEDGER_DETAIL a left join a.order b  where b.id = '"+id+"'");
        return returnJsonForBean(ledgerDetails);
    }

    @RequestMapping(value = "/uploadGuoJ", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String uploadGuoJ(String id, @RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        ReturnModel returnModel = getReturnModel();
        DiskFileItem fi = (DiskFileItem) file.getFileItem();
        File f = fi.getStoreLocation();
        try {
            transportOrderService.upload(id, f);
        } catch (RuntimeException e) {
            returnModel.setResult(false);
            returnModel.setObj(e.getMessage());
        }
        return returnModel.toJsonString();
    }

    /**
     * 求订单总数量
     * @return
     */
    @RequestMapping(value = "/selectCont", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String selectCont(){
     Long aaa = transportOrderService.selectCont();
         String abc = aaa.toString();
        return  returnJson(abc);
    }
    /**
     * 自提签收（签收的是分段）
     */
    @RequestMapping(value = "/setqsr", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String setqsr(String id,String qsperson,Timestamp qsTime){
        ReturnModel returnModel = new ReturnModel();
             if(StringUtils.isEmpty(id)){
                 throw  new MessageException("ID是空，不能保存");
             }
             if(StringUtils.isEmpty(qsperson)){
                 throw  new MessageException("收货人不能为空");
             }
        if(qsTime == null){
            throw  new MessageException("收货时间不能为空");
        }
         if(returnModel.isResult()){
             ledService.saveLed(id,qsperson,qsTime);
         }
                return returnModel.toJsonString();
    }


    /**
     * 修改件重尺(主要针对拆分的单子)
     * getOrderLedger
     */

    @RequestMapping(value = "/updateJZC", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrderLedger(String id,Integer oldnumber,Double oldweight,Double oldvolume,Integer newnumber,Double newweight, Double newvolume){
      ReturnModel returnModel = new ReturnModel();
      String  ledjzc  = ledService.updateJZC(id,oldnumber,oldweight,oldvolume,newnumber,newweight,newvolume);
       if(ledjzc.toString() !="成功"){
           returnModel.addError("nuber","修改失败");
       }
        return returnModel.toJsonString();
    }

    /**
     * 订单导入
     * @return
     */
    @RequestMapping(value = "/orderFileupload", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String orderFileupload( HttpServletRequest request,HttpServletResponse response){
        InputStream inputStream=null;
        String substring = null;
        String s = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out= null;
        try {
            out = response.getWriter();
            response.reset();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
      //  MultipartFile servletContext = (MultipartFile) request.getSession().getServletContext();
        if(multipartResolver.isMultipart(request)) {//判断是否为空或者是否用的post请求
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multiRequest.getFileNames();
            while (iterator.hasNext()) {
                MultipartFile multipartFile = multiRequest.getFile(iterator.next());
                if (multipartFile != null) {
                    inputStream = new ByteArrayInputStream(multipartFile.getBytes());
                }
            }
        }
       XSSFWorkbook  sdsds    = new XSSFWorkbook(inputStream);
            List<Map<String, Object>> result = new ArrayList();
            result = transportOrderService.orderUploadNew(sdsds);
         s = returnJson(result);
        out.print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
        return s;
    }


//    /**
//     * 导出
//     * @param response
//     * @param classId
//     */
//    @RequestMapping(value = "/orderExport", produces = "text/json;charset=UTF-8")
//    @ResponseBody
//    public void exportStu(HttpServletResponse response, String classId) {
//        try {
//String export="账号#username,姓名#name,联系方式#phone";//此处为标题，excel首行的title，按照此格式即可，格式无需改动，但是可以增加或者减少项目。
//String[] excelHeader = export.split(",");
//List<Order> projectList = new ArrayList<Order>();//声明数据集合
////根据班级id获取班级名称和班级内的学生信息：学生的username,name,phone
//String className="班级名称";//className:生成的excel默认文件名和sheet页
//projectList=this.transportOrderService.exportStusByCid(classId);//获取数据
//ExcelUtils.export(response, className, excelHeader, projectList);//调用封装好的导出方法，具体方法在下面
//} catch (Exception e) {
//e.printStackTrace();
//}
//
//    }

    /**
     * 导出
     */
//    @RequestMapping(value = "/getExcel", produces = "text/json;charset=UTF-8")
//    @ResponseBody
//    public void getExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        // 准备数据
//        List<JcRegistration> list = new ArrayList<JcRegistration>();
//        for (int i = 0; i < 10; i++) {
//            list.add(new Student(111,"张三asdf","男"));
//            list.add(new Student(111,"李四asd","男"));
//            list.add(new Student(111,"王五","女"));
//        }
//        String[] columnNames = { "ID", "姓名", " 性别"};
//        String fileName = "excel1";
//        ExportExcelWrapper<Student> util = new ExportExcelWrapper<Student>();
//        util.exportExcel(fileName, fileName, columnNames, list, response, ExportExcelUtil.EXCEL_FILE_2003);
//    }

    /**
     * 同步TMS数据
     * @return
     */
    @RequestMapping(value = "/tongbuTMS", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String tongbuTMS(String ids){
        String abc = null;
      List<Map<String, Object>> list =  transportOrderService.tongbuTMS(ids);
        return returnJson(list);

    }

    //需要收入确认的费用明细
    @RequestMapping(value = "/getLedgerdetailsList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getLedgerdetailsList(CommModel commModel){
        JgGridListModel jgGridListModel =transportOrderService.getLedgerdetailsList(commModel);
        return jgGridListModel.toJSONString();
    }
    //需要收入确认的费用明细
    @RequestMapping(value = "/affirmOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  affirmOrder(String id,Timestamp date){
        ReturnModel returnModel = getReturnModel();
        try {
            transportOrderService.affirmOrder(id,date);
        }catch (MessageException me){
            returnModel.setObj(me.getMessage());
        }

        return returnModel.toJsonString();
    }
    @RequestMapping(value = "/fanaffirmOrder", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  fanaffirmOrder(String id){
        ReturnModel returnModel = getReturnModel();
        try {
            transportOrderService.fanaffirmOrder(id);
        }catch (MessageException me){
            returnModel.setObj(me.getMessage());
        }

        return returnModel.toJsonString();
    }


    /**
     * 新增明细
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/addSubJgGird", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addSubJgGird(String id, Boolean shipmentSign) {
        ReturnModel returnModel = getReturnModel();
        try {
            transportOrderService.addSubJgGird(id, shipmentSign);
        } catch (RuntimeException e) {
            returnModel.setObj(e.getMessage());
            returnModel.setResult(false);
        }
        return returnModel.toJsonString();
    }

    /**
     * 删除明细
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/dellSubJgGird", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String dellSubJgGird(String id) {
        ReturnModel returnModel = getReturnModel();
        try {
            transportOrderService.dellSubJgGird(id);
        } catch (RuntimeException e) {
            returnModel.setObj(e.getMessage());
            returnModel.setResult(false);
        }
        return returnModel.toJsonString();
    }

    /**
     * 编辑明细
     */
    @RequestMapping(value = "/editFree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String editFree(HttpServletRequest request) {
        try {
            transportOrderService.editFree(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getReturnModel().toJsonString();
    }



}
