package com.kytms.core.utils;

import org.apache.log4j.Logger;
import java.lang.reflect.Method;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 反射工具类
 *
 * @author 奇趣源码
 * @create 2017-11-23
 */
public abstract class ReflectionUtils {
    private static Logger logger = Logger.getLogger(ReflectionUtils.class);
    /**
     * 根据实体名称获取Class对象
     * @param className
     * @return
     */
    public static Class<?> getClassByName(String className){
        Class<?> c=null;
        try{
            c=Class.forName(className);
        }catch(Exception e){
            logger.error(className+"类没有找到");
        }
        return c;
    }
    /**
     * 为一个对象的某个字段执行get方法
     * @param owner
     * @param fieldName
     * @param args
     * @return
     */
    public static Object invokeGetMethod(Object owner, String fieldName, Object[] args) {
        String readMethod = EntityUtil.getReadMethod(fieldName);
        return invokeMethod(owner, readMethod, args);
    }

    /**
     * 为一个对象的某个字段执行set方法
     * @param owner
     * @param fieldName
     * @param args
     * @return
     */
    public static void invokeSetMethod(Object owner, String fieldName, Object[] args) {
        String writeMethod = EntityUtil.getWriteMethod(fieldName);
        invokeMethod(owner, writeMethod, args);
    }
    /**
     * 执行无参数函数  异常抛出
     * @param owner
     * @param methodName
     * @return
     */
    public static Object invokeMethod(Object owner, String methodName) throws Exception{
        Class ownerClass = owner.getClass();
        Method method = ownerClass.getMethod(methodName, null);
        Object object = method.invoke(owner, null);
        return object;
    }
    /**
     * 反射执行类中函数
     * @param owner  函数所在的类
     * @param methodName 函数名称
     * @param args 参数
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object invokeMethod(Object owner, String methodName, Object[] args){
        Class ownerClass = owner.getClass();
        Class[] argsClass = null;
        if(args != null){
            argsClass = new Class[args.length];
            for (int i = 0, j = args.length; i < j; i++) {
                if(args[i] != null){
                    if("class java.lang.Boolean".equals(""+args[i].getClass())){
                        argsClass[i] = boolean.class;
                    } else {
                        argsClass[i] = args[i].getClass();
                    }
                }else{
                    argsClass[i] = Object.class;
                }
            }
        }
        //后的函数对象
        Method method = null;
        try {
            method = ownerClass.getMethod(methodName, argsClass);
        } catch (Exception e) { //异常出现默认就为Object对象
            try {
                method = ownerClass.getMethod(methodName, Object.class);
            } catch (Exception e2) {
                logger.error("获得类函数失败: " + e2);
            }
        }
        if(method == null){
            Method[] methods = ownerClass.getMethods();
            for (Method me:methods) {
                boolean equals = me.getName().equals(methodName);
                if(equals){
                    method = me;
                }
            }
        }
        Object object = null;
        try {
                object = method.invoke(owner, args);


        } catch (Exception e) {
            try {
                Object[] argss= new Object[] {null};
                object = method.invoke(owner,argss);
            }catch (Exception es){
                logger.error("执行函数["+method.getName()+"]出错: "+ es);
                es.printStackTrace();
            }


        }
        return object;
    }
}
