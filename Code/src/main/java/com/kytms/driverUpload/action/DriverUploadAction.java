package com.kytms.driverUpload.action;

import com.alibaba.fastjson.JSONObject;
import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.DriverUpload;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.driverUpload.service.DriverUploadService;
import com.kytms.driverset.service.Impl.DriverServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;


@Controller
@RequestMapping(value = "driverupload")
public class DriverUploadAction extends BaseAction {
    private final Logger log = Logger.getLogger(DriverUploadAction.class);//输出Log日志
    private DriverUploadService driverUploadService;

    public DriverUploadService getDriverUploadService() {
        return driverUploadService;
    }
    @Resource(name = "DriverUploadService")
    public void setDriverUploadService(DriverUploadService driverUploadService) {
        this.driverUploadService = driverUploadService;
    }

    @RequestMapping(value = "/uploadDri", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String driverUpload(HttpServletRequest request,HttpServletResponse response,@RequestParam("driverId")String driverId) throws Exception{
        ReturnModel returnModel = getReturnModel();
        // MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;

        MultipartFile file = multipartRequest.getFile("fileList");
        //String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/images/driverUpload/";//存储路径
        String path = request.getSession().getServletContext().getRealPath("images/driverUpload"); //文件存储位置
        File file1=null;
        try {
             file1 = driverUploadService.driverUpload(file, path, driverId);
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setResult(false);
            returnModel.setObj(e.getMessage());
        }
        return returnModel.toJsonString();

    }

    @RequestMapping(value = "/showCredentials", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String showCredentials(@RequestParam("driverId")String driverId) throws Exception{
        List<DriverUpload> dulist = null;
        dulist=driverUploadService.showCredentials(driverId);
        return returnJson(dulist);
    }
}
