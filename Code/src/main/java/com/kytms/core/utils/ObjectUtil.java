package com.kytms.core.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 对象工具类
 *
 * @author 奇趣源码
 * @create 2017-12-04
 */
public abstract class ObjectUtil {
    private static Logger logger = Logger.getLogger(ReflectionUtils.class);
    /**
     * 对象克隆工具
     * @param obj
     * @return
     * @throws Exception
     */
    public static <T>T cloneObject(Object obj, Class<T> clazz) {
        try {
            String string = JSONObject.toJSONString(obj);
            T t = JSONObject.parseObject(string, clazz);
            return t;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
       return null;
    }
}
