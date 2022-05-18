package com.kytms.orderback.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.DateUtils;
import com.kytms.orderback.service.OrderBackService;
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
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 订单回单
 *
 * @author
 * @create 2018-01-19
 */
@Controller
@RequestMapping("/orderback")
public class OrderBackAction extends BaseAction {
    private final Logger log = Logger.getLogger(OrderBackAction.class);//输出Log日志
    private OrderBackService orderBackService;
    @Resource
    public void setOrderBackService(OrderBackService orderBackService) {
        this.orderBackService = orderBackService;
    }


    @RequestMapping(value = "/getOrderBackList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrderBackList(CommModel commModel) {
        JgGridListModel jgGridListModel = orderBackService.getOrderBackList(commModel);
        return jgGridListModel.toJSONString();
    }

    @RequestMapping(value = "/receive", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String receive(CommModel commModel) {
        ReturnModel returnModel = getReturnModel();
        try {
            orderBackService.receive(commModel);
        }catch (MessageException e){
            returnModel.setObj(e.getMessage());
            returnModel.setResult(false);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/submit", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String submit(CommModel commModel) {
        ReturnModel returnModel = getReturnModel();
        try {
            orderBackService.submit(commModel);
        }catch (MessageException e){
            returnModel.setObj(e.getMessage());
            returnModel.setResult(false);
        }
        return returnModel.toJsonString();
    }
    @RequestMapping(value = "/uploadDri", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String driverUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("id")String id) throws Exception{
        ReturnModel returnModel = getReturnModel();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartRequest.getFile("fileList");
        String dataString = DateUtils.getDataString(DateUtils.yyyyMMdd);
        String path = request.getSession().getServletContext().getRealPath("images/upload/"+dataString)+"/"; //文件存储位置
        String url ="images/upload/"+dataString+"/";


        File file1=new File(path);
        if (!file1.exists()) {// 如果目录不存在
            file1.mkdirs();// 创建文件夹
        }
        try {
            file1 = orderBackService.driverUpload(file, path, id,url);
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setResult(false);
            returnModel.setObj(e.getMessage());
        }
        return returnModel.toJsonString();

    }
    @RequestMapping(value = "/getImages", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getImages(@RequestParam("id")String id) {

        List<Map> list = orderBackService.getImages(id);
        return returnJson(list);
    }
    @RequestMapping(value = "/getOrderYQS", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getOrderYQS(CommModel commModel){
        JgGridListModel jgGridListModel =orderBackService.getOrderYQS(commModel);
        return jgGridListModel.toJSONString();
    }


}
