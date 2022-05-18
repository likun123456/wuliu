package com.kytms.core.action;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kytms.core.filter.JSONFilter;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;
import com.kytms.core.utils.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 基础Action
 *
 * @author 奇趣源码
 * @create 2017-11-17
 */
public abstract class BaseAction {
    /**
     * 空字符串
     */
    public static final String STRING_EMPTY = "";
    public static final String SPLIT_COMMA = ",";
    private BaseService baseService;
    protected BaseService getBaseService() {
        return baseService;
    }
    @Resource(name = "UserService")
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }




    //将对象序列化程JSON返回
    protected String returnJson(Object object) {
        return JSONObject.toJSONString(object);
    }

    //如果你的序列化过程中，JAVA BEAN还在持久化状态，序列化，两个对象循环引用，请尝试此功能
    protected String returnJsonForBean(Object obj) {
        return JSONObject.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

    //对Bean树形进行过滤
    protected String returnJsonFilter(Object object) {
        JSONFilter jsonFilter = new JSONFilter();
        if (object == null) {
            return STRING_EMPTY;
        }
        return JSONObject.toJSONString(object, jsonFilter);
    }

    //获取returnModel
    protected ReturnModel getReturnModel() {
        return new ReturnModel();
    }

    //获取一个Bean
    @RequestMapping(value = "/selectBean", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String selectBean(String tableName, String id) {
        Object o = baseService.selectBean(tableName, id);
        return returnJsonForBean(o);
    }

    //删除Bean
    @RequestMapping(value = "/deleteBean", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String deleteBean(String tableName, String id) {
        ReturnModel returnModel = getReturnModel();
        baseService.deleteBean(tableName, id);
        returnModel.setObj("成功删除");
        return returnJson(returnModel);
    }

    //状态修改
    @RequestMapping(value = "/updateStatus", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String updateStatus(String tableName, String id, int status) {
        ReturnModel returnModel = getReturnModel();
        Object o = baseService.updateStatus(tableName, id.split(SPLIT_COMMA), status);
        returnModel.setObj("状态修改成功");
        return returnModel.toJsonString();
    }

    //保存bean
    @RequestMapping(value = "/saveBean", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveBean(String tableName, String obj) {
        ReturnModel returnModel = getReturnModel();
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(obj)) {
            returnModel.setObj("前台页面错误，传入表名，对象为空");
            returnModel.setResult(false);
        }
        boolean result = returnModel.isResult();
        if (result) {
            Map<String, Class> entityAll = baseService.getEntityAll();
            Object o = JSONObject.parseObject(obj, entityAll.get(tableName));
            baseService.saveBean(o);
        }
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getTree(String tableName, String id,String status) {
        List<TreeModel> list = baseService.getTree(tableName, id,status);
        return returnJson(list);
    }
}
