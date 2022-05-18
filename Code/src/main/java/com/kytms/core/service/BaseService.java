package com.kytms.core.service;

import com.kytms.core.entity.BaseEntity;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.TreeModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 基础服务层接口,他的做用是用于 所有服务层提供基础方法，并对基础业务逻辑进行封装
 * 他会有基础的CRUD 并注入基础的Dao层
 *
 * @author 奇趣源码
 * @create 2017-11-17
 */
public interface BaseService<T> {

    /**
     * 分页查询，使用基础SQL语句 包含1=1
     * @param Hql
     * @param commModel
     * @param where
     * @param orderBy
     * @return
     */
    JgGridListModel getListByPageToHql(String Hql,String countHql,CommModel commModel,String where,String orderBy);


    /**
     * 基础分页查询，不包含组织机构
     * @param commModel
     * @return
     */
    JgGridListModel getListByPage(CommModel commModel,String where,String orderBy);

    void deleteBean(T id);
    /**
     * 删除一个Bean
     * @param table
     * @param id
     */
    void deleteBean(String table, String id);
    /**
     * 保存一个Bean
     * @param t
     */
    void saveBean(T t);
    /**
     * 代码唯一性验证
     * @param entity
     * @param service
     * @return
     */
    boolean codeUnique(BaseEntity entity, BaseService service);

    /**
     * 查找一个Bean
     * @param id
     * @param <T>
     * @return
     */
    <T> T selectBean(Serializable id);

    /**
     * 根据表明获取Bean
     * @param tableName
     * @param id
     * @param <T>
     * @return
     */
    <T> T selectBean(String tableName,String id);

    Object updateStatus(String tableNamen, String age[], int status);

    /**
     * 常规查询不分页
     * @param comm
     * @return
     */
    List<T> selectList(CommModel comm);
    /**
     * 常规查询不分页
     * @param comm
     * @return
     */
    List<T> selectList(CommModel comm,String where,String orderBy);

    /**
     * 获取所有实体类对象
     * @return
     */
    Map getEntityAll();

    /**
     * 通用获取树形
     * @param tableName
     * @param obj
     * @return
     */
    List<TreeModel> getTree(String tableName, String obj,String status);
    /**
     * 获取订单编码
     */
    String getOrderCode();
    /**
     * 获取任务单编码
     */

    String getVehiclePlanCode();
}
