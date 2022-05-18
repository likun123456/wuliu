package com.kytms.registration.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.JcRegistration;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.ExportExcel;
import com.kytms.core.utils.ExportExcelUtil;
import com.kytms.core.utils.ExportExcelWrapper;
import com.kytms.core.utils.StringUtils;
import com.kytms.receivingparty.service.impl.ReceivingPartyServiceImpl;
import com.kytms.registration.service.JcRegistrationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/** 辽宁捷畅物流有限公司  集团信息技术中心
 * 孙德增
 * Created by sundezeng on 2017/12/8.
 * 注册公司ACTION
 */
@Controller
@RequestMapping("/jcRegistration")
public class JcRegistrationAction extends BaseAction {
    private final Logger log = Logger.getLogger(JcRegistrationAction.class);//输出Log日志
    private JcRegistrationService<JcRegistration> jcRegistrationService;
    public JcRegistrationService<JcRegistration> getJcRegistrationService() {
        return jcRegistrationService;
    }
    @Resource(name = "JcRegistrationService")
    public void setJcRegistrationService(JcRegistrationService<JcRegistration> jcRegistrationService) {
        this.jcRegistrationService = jcRegistrationService;
    }




    /**
     * 用于获取组织机构列表通用方法
     * @return
     */
    @RequestMapping(value = "/getJcRegistrationTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getJcRegistrationTree(){
        List<JcRegistration> rows = jcRegistrationService.selectList(new CommModel());
        // List<JcRegistration> rows = jgGridListModel.getRows();
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (JcRegistration jcRegistration:rows ){
            TreeModel treeModel = new TreeModel();
            treeModel.setId(jcRegistration.getId());
            treeModel.setText(jcRegistration.getName());
            treeModels.add(treeModel);
        }
        return returnJson(treeModels);
    }

    @RequestMapping(value = "/getJcRegistrationList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getJcRegistrationList(CommModel commModel){
        JgGridListModel jgGridListModel =jcRegistrationService.getJcRegistrationList(commModel);

        return returnJson(jgGridListModel);
    }

    @RequestMapping(value = "/saveJcRegistrationBean", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveJcRegistrationBean(JcRegistration jcRegistration) {
        ReturnModel returnModel = getReturnModel();
        returnModel.codeUniqueAndNull(jcRegistration,jcRegistrationService);
        if (StringUtils.isEmpty(jcRegistration.getName())){
            returnModel.addError("name","名称不能为空");
        }

        boolean result = returnModel.isResult();
        if (result){
            jcRegistrationService.saveBean(jcRegistration);
        }
        return returnModel.toJsonString();
    }

//    /**
//     * 导出
//     */
//    @RequestMapping(value = "/getExcel", produces = "text/json;charset=UTF-8")
//    @ResponseBody
//    public void getExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        // 准备数据
//        List<JcRegistration> list = new ArrayList<JcRegistration>();
//        for (int i = 0; i < 10; i++) {
//            JcRegistration jcRegistration = new JcRegistration();
//                 jcRegistration.setId("123");
//                 jcRegistration.setCode("aaaa");
//                 jcRegistration.setName("sdf");
//            list.add(jcRegistration);
//        }
//        String[] columnNames = { "ID", "代码", "名称"};
//        String fileName = "excel1";
//        ExportExcelWrapper<JcRegistration> util = new ExportExcelWrapper<JcRegistration>();
//        util.exportExcel(fileName, fileName, columnNames, list, response, ExportExcelUtil.EXCEL_FILE_2003);
//    }


    @ResponseBody
    @RequestMapping(value = "/getExport",produces = "text/json;charset=UTF-8")
    public void export(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "queryJson") String queryJson) throws UnsupportedEncodingException {
        //如果出现中文乱码请添加下面这句
        queryJson = URLDecoder.decode(queryJson, "utf-8");
        //需要导入alibaba的fastjson包
        JcRegistration jcRegistration = com.alibaba.fastjson.JSON.parseObject(queryJson, JcRegistration.class);
        List<JcRegistration> userlList = jcRegistrationService.getUserForExcel(jcRegistration);
        ExportExcel<JcRegistration> ee = new ExportExcel<JcRegistration>();
        String[] headers = {"ID", "代码", "名称"};
        String fileName = "注册公司";
        ee.exportExcel(headers, userlList, fileName, response);

    }
}
