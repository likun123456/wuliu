package com.kytms.core.utils;

import com.kytms.core.annotation.Tree;
import com.kytms.core.entity.BaseEntity;
import com.kytms.core.model.TreeModel;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 实体类工具
 *
 * @author 奇趣源码
 * @create 2017-11-18
 */
public abstract class EntityUtil {
    private static Logger logger = Logger.getLogger(EntityUtil.class);
    /**Table缓存*/
    private static final Map<String,String> tableNameCache = new HashMap<String, String>(100);

    /**用于获取实体类的@Entity注解 获取NAME属性的注解值
     *
     * @param clazz
     * @return
     */
    public static String getEntityTableName(Class<?> clazz){
        logger.info("EntityUtil 全局缓存:"+tableNameCache.size());
        if (tableNameCache.get(clazz.getSimpleName()) != null){
            return tableNameCache.get(clazz.getSimpleName());
        }
        String entityName = null;
        if(clazz.isAnnotationPresent(Entity.class)){
            Entity myAnnotation = clazz.getAnnotation(Entity.class);
            entityName = myAnnotation.name();
        }
        Assert.isNull(entityName,"entityName Annotaion error is null");
        tableNameCache.put(clazz.getSimpleName(),entityName);
        return entityName;
    }

    /**
     * 用于获取树形递归，行程TreeModel 的工具，传进来的类必须持久化支持
     * @param obj
     * @return
     */
    public static List<TreeModel> getTreeMoidel(Object obj,String status) throws Exception {
        Assert.isNull(obj);
        /**
         * 反射得到Tree name属性
         */
        String entityName = null;
        Class<?> aClass = obj.getClass();
        if(aClass.isAnnotationPresent(Tree.class)){
            Tree annotation = aClass.getAnnotation(Tree.class);
            entityName = annotation.name();
        }
        if (StringUtils.isEmpty(entityName)){
            Assert.isTrue(true,"entity is empty");
        }
        List list = (List) ReflectionUtils.invokeMethod(obj, getReadMethod(entityName));
        return   RecursionTreeModel(list,entityName,status);
    }

    /**
     * 递归方法
     * @param obj
     * @param entityMethod
     * @return
     */
    private static List<TreeModel> RecursionTreeModel(List obj,String entityMethod,String status){
        List<TreeModel> treeList = new ArrayList<TreeModel>();
        try {
        for (int i = 0; i < obj.size(); i++) {
            Object o = obj.get(i);
            TreeModel tree = new TreeModel();
            BaseEntity superclass = (BaseEntity) o;

            tree.setId(superclass.getId());
            tree.setText(superclass.getName());
            tree.setComplete(true);
            tree.setIsexpand(true);
            tree.setShowcheck(false);
            List list = (List) ReflectionUtils.invokeMethod(o, getReadMethod(entityMethod));
            if (list !=null){
                tree.setParentnodes(superclass.getId());
            }
            //tree.setHasChildren();
            if (list.size() > 0){
                tree.setHasChildren(true);
                List<TreeModel> treeModels = RecursionTreeModel(list,entityMethod,status);//开始递归
                tree.setChildNodes(treeModels);
            }else{
                tree.setHasChildren(false);
            }
            if(StringUtils.isEmpty(status)){
                treeList.add(tree);
            }else {
                int beanStatus = superclass.getStatus();
                int intStatus = Integer.parseInt(status);
                if(beanStatus==intStatus){
                    treeList.add(tree);
                }
            }

        }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return treeList;
    }

    /**
     * 得到属性的get方法
     * 2011-8-30 下午05:52:15
     * @param fName
     * @return
     */
    public  static  String getReadMethod(String fName){
        return "get"+(fName.substring(0,1)).toUpperCase()+fName.substring(1, fName.length());
    }

    /**
     * 得到属性的set方法
     * 2011-8-30 下午05:52:37
     * @param fName
     * @return
     */
    public static String getWriteMethod(String fName){
        return "set"+(fName.substring(0,1)).toUpperCase()+fName.substring(1, fName.length());
    }
}
