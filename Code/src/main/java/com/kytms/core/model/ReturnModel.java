package com.kytms.core.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.kytms.core.entity.BaseEntity;
import com.kytms.core.service.BaseService;
import com.kytms.core.utils.Assert;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 返回模型类
 * @author 奇趣源码
 * @create 2017-11-19
 */
public class ReturnModel{

    private final Logger log = Logger.getLogger(ReturnModel.class);//输出Log日志

    private final Map<String,Object> map = new HashMap<String,Object>();
    @JSONField(serialize=false)
    public static final String STRING_EMPTY="执行成功";
    @JSONField(serialize=false)
    public static final String STRING_TRUE="true";
    @JSONField(serialize=false)
    public static final String STRING_FALSE="false";
    @JSONField(serialize=false)
    public static final String STRING_VALIDATOR="validator";
    @JSONField(serialize=false)
    private boolean result = true;

    private String type = STRING_TRUE;
    private Object obj = STRING_EMPTY;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        Assert.isNull(type);
        if (STRING_FALSE.equals(type)){
            result = false;
        }
        if (STRING_TRUE.equals(type)){
            result = true;
        }
        this.type = type;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String toJsonString(){
        if (map.size() != 0){
            this.setType(STRING_VALIDATOR);
            this.obj = this.map;
            return JSONObject.toJSONString(this);
        }
        return JSONObject.toJSONString(this);
    }
    public String toString(){
        return toJsonString();
    }
    public void addError(String key,Object value){
        this.result = false;
        this.map.put(key,value);
    }
    public void clearError(){
        this.map.clear();
    }

    public void setResult(boolean result) {
        if (result){
            type = STRING_TRUE;
        }else {
            type = STRING_FALSE;
        }
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    /**
     * 判断CODE是否为空和数据库中是否重复
     * @param entity
     * @param service
     */
    public void codeUniqueAndNull(BaseEntity entity, BaseService service){
        if (entity instanceof  BaseEntity){
            Assert.isTrue(true,"类型错误");}
        if (entity.getCode() == null){this.addError("code","代码不能为空");return;}
        boolean result =  service.codeUnique(entity,service);
        if (!result){
            this.addError("code","代码重复");
        }
    }

}

