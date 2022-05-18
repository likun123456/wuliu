package com.kytms.core.entity;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 简单工具类
 *
 * @author 奇趣源码
 * @create 2017-11-26
 */
public abstract class ObjectUtils {
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return (true);
        }
        if (object.equals("")) {
            return (true);
        }
        if (object.equals("null")) {
            return (true);
        }
        return (false);
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
}
