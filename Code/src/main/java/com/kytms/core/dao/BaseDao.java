package com.kytms.core.dao;

import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 基础Dao接口
 *
 * @author
 * @create 2017-11-17
 */
public interface BaseDao<T> {

    Map getEntityAll();

    /**获取反省类名*/
    String getClassName();
    /**
     * 获取Session工厂
     * @return
     */
     SessionFactory getSessionFactory();
    /***
     * 执行查询HQL语句 带参数
     * @param Sql
     * @param age
     * @return
     */
     List<T>  executeQuerySql(String Sql,List<String> age);

    /**
     * 执行查询Hql
     * @param Hql
     * @param ages
     * @return
     */
    List<T> executeQueryHql(String Hql, List<String> ages);
    /**
     * 通过泛型获取Bean
     * @param id
     * @return
     */
    T selectBean(Serializable id);

    /**
     * getBean
     * @param c
     * @param id
     * @return
     */
     T selectBean(Class c,Serializable id);

    /**
     * 通过
     * @param TableName
     * @param id
     * @return
     */
     T selectBean(String TableName,String id);
    /**
     * 保存一个Bean
     * @param t
     * @return
     */
     T savaBean(T t);
    @Deprecated
     T saveBean(Object o,Class clazz);


    /**
     * 删除一个Bean
     * @param t
     */
     void deleteBean(T t);
    /***
     * 不带参数
     * @param Sql
     * @return
     */
     List<T> executeQuerySql(String Sql);

    /**
     * 执行HQL来查询
     * @param Hql
     * @return
     */
    List<T>  executeQueryHql(String Hql);
    /**
     * 执行SQL语句
     * @param hql
     * @param age
     */
     void executeHql(String hql, List<String> age);

    /***
     * 通用分页查询
     * @param hql
     * @param limit
     * @param start
     * @return
     */
    List<T> selectByPage(String hql, int start, int limit);
    List<T> seelcetByPageSql(String sql, int start, int limit);

    /**
     * 查询一个Bean的总和
     * @return
     */
    long selectCount();
    /**查询一个实体类是否为总行数*/
    long selectCount(T t);
    /**加入条件*/
    long selectCount(T t,String where,String[] ages);

    long selectCountByHql(String hql,String where);
}
