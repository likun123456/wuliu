package com.kytms.orderabnormal;/**
 * Created by nidaye on 2018/10/18.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 注解
 *
 * @author
 * @create 2018-10-18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({  ElementType.FIELD})
public @interface Abnormal {
    String name(); //字段名称
    String dataBookName() default ""; //数据字典名称
    Class<?>[] clszz() default { }; //对象类型
    String clazzName() default "name"; //反射对象变更的名称
}
