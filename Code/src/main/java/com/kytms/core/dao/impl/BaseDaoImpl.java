package com.kytms.core.dao.impl;

import com.kytms.core.dao.BaseDao;
import com.kytms.core.entity.BaseEntity;
import com.kytms.core.utils.EntityUtil;
import com.kytms.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 基础Dao接口实现类
 *
 * @author
 * @create 2017-11-17
 */
public  class BaseDaoImpl<T> extends AbstractBaseDao  implements BaseDao<T> {
    public static final String HQL_SELECT_COUNT ="SELECT COUNT(*) FROM ";
    public static final String HQL_WHERE=" WHERE 1=1 ";
    public static final Map<String,Class> SYSTEM_ENTITY_ALL = new HashMap<String, Class>(100);
    private SessionFactory sessionFactory;
    private Logger log = Logger.getLogger(BaseDaoImpl.class);//输出Log日志


    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    protected Session openSession() {
        return sessionFactory.getCurrentSession();
    }



    public Map getEntityAll() {
        if (SYSTEM_ENTITY_ALL.size() == 0){
            Map<String, ClassMetadata> allClassMetadata = sessionFactory.getAllClassMetadata();
            for (Map.Entry<String,ClassMetadata> entity:allClassMetadata.entrySet()){
                ClassMetadata value = entity.getValue();
                Class mappedClass = value.getMappedClass();
                String entityTableName = EntityUtil.getEntityTableName(mappedClass);
                SYSTEM_ENTITY_ALL.put(entityTableName,mappedClass);
            }
        }
        return SYSTEM_ENTITY_ALL;
    }

    public String getClassName(){
        return EntityUtil.getEntityTableName(entityClass);
    }
    public Class<T> entityClass;
    public BaseDaoImpl(){
        //class OrgDao extends BaseDaoImpl<Organization, String> implements OrgDao {}
        Class c = getClass(); //返回的是new的那个类
//        Type[] genericInterfaces = c.getGenericInterfaces();
        Type type = c.getGenericSuperclass(); //取得泛型
        //取得泛型对应的真正的class放到数组中
        // Type type = genericInterfaces[0];
        Type[] types = ((ParameterizedType)type).getActualTypeArguments();
        entityClass = (Class<T>) types[0];

    }
//    public BaseDaoImpl(Class cc) {
//        //class OrgDao extends BaseDaoImpl<Organization, String> implements OrgDao {}
//        Class c = cc.getClass(); //返回的是new的那个类
////        Type[] genericInterfaces = c.getGenericInterfaces();
//        Type type = c.getGenericSuperclass(); //取得泛型
//        //取得泛型对应的真正的class放到数组中
//        // Type type = genericInterfaces[0];
//        Type[] types = ((ParameterizedType)type).getActualTypeArguments();
//        entityClass = (Class<T>) types[0];
//    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public List<T> executeQueryHql(String Hql, List<String> ages){
        Query query = openSession().createQuery(Hql);
        
        if (ages != null && ages.size() != 0){
            for (int i = 0; i < ages.size(); i++) {
                query.setParameter(i,ages.get(i));
            }
        }
        List list = query.list();
        return list;
    }
    public List<T> executeQuerySql(String Sql, List<String> ages) {
        SQLQuery sqlQuery = openSession().createSQLQuery(Sql);
        if (ages != null && ages.size() != 0){
            for (int i = 0; i < ages.size(); i++) {
                sqlQuery.setParameter(i,ages.get(i));
            }
        }
        List list = sqlQuery.list();
        return list;
    }

    public T selectBean(Serializable id) {
        return this.selectBean(entityClass,id);
    }

    public T selectBean(Class c, Serializable id) {
        return (T) openSession().get(c,id);
    }

    public T selectBean(String TableName, String id) {
        String Hql = "FROM "+ TableName + " WHERE 1=1 and id = ?";
        Query query = openSession().createQuery(Hql);
        query.setParameter(0,id);
        Object o = query.list().get(0);
        return (T) o;
    }

    /**未完成*/
    public T savaBean(T t) {
        if (t instanceof BaseEntity){
        }
        T merge = (T) openSession().merge(t);
        return merge;
    }
    @Deprecated
    public T saveBean(Object o, Class clazz) {
        this.entityClass =clazz;
        T merge = (T) openSession().merge(o);
        return merge;
    }

    public void deleteBean(T t) {
        openSession().delete(t);
    }
    public List<T> selectByPage(String hql, int start, int limit) {
        Query query = openSession().createQuery(hql);
        List list = query.setFirstResult(start).setMaxResults(limit).list();
        return list;
    }

    public List<T> seelcetByPageSql(String sql, int start, int limit) {
        return null;
    }

    public long selectCount() {
       return selectCount(null);
    }

    public long selectCount(T t) {
       return selectCount(t,null,null);
    }

    public long selectCount(T t, String where,String[] ages) {
        StringBuilder sb = new StringBuilder(HQL_SELECT_COUNT);
        if (t == null){
            sb.append(getClassName());
        }else {
            sb.append(EntityUtil.getEntityTableName(t.getClass()));
        }
        sb.append(HQL_WHERE);
        if (StringUtils.isNotEmpty(where)){
            sb.append(where);
        }

        Query query = openSession().createQuery(sb.toString());
        if (ages != null){
            for (int i = 0; i <ages.length ; i++) {
                query.setParameter(i,ages[i]);
            }
        }
        List list = query.list();
        if (list == null)
            return 0;
        Long result = (Long) list.get(0);
        return result;
    }

    public long selectCountByHql(String hql, String where) {
        StringBuilder sb = new StringBuilder();
        sb.append(hql);
        if (StringUtils.isNotEmpty(where)){
            sb.append(where);
        }

        Query query = openSession().createQuery(sb.toString());
        List list = query.list();
        if (list == null || list.size() ==0)
            return 0;

        Long result = (Long) list.get(0);
        return result;
    }

    public List<T>  executeQuerySql(String Sql) {
        return this.executeQuerySql(Sql,null);
    }
    public List<T>  executeQueryHql(String Hql) {
        return this.executeQueryHql(Hql,null);
    }

    public void executeHql(String hql, List<String> age) {
        Query query = openSession().createQuery(hql);
        if ( age !=null  && age.size()>0) {
            for (int i = 0; i < age.size(); i++) {
                query.setParameter(i, age.get(i));
            }
        }
        query.executeUpdate();
    }

}
