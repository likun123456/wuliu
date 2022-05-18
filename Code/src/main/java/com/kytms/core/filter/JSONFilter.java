package com.kytms.core.filter;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.kytms.core.exception.MessageException;
import org.apache.log4j.Logger;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

/**
 * 奇趣源码商城 www.qiqucode.com
 * FASTJSON对象过滤器
 *
 * @author 奇趣源码
 * @create 2018-01-04
 */
public class JSONFilter implements PropertyFilter {

    private final Logger log = Logger.getLogger(JSONFilter.class);//输出Log日志

    public boolean apply(Object source, String name, Object value) {
        if (value instanceof HibernateProxy) {//hibernate代理对象
            LazyInitializer initializer = ((HibernateProxy) value).getHibernateLazyInitializer();
            if (initializer.isUninitialized()) {
                return false;
            }
        } else if (value instanceof PersistentCollection) {//实体关联集合一对多等
            PersistentCollection collection = (PersistentCollection) value;
            if (!collection.wasInitialized()) {
                return false;
            }
            Object val = collection.getValue();
            if (val == null) {
                return false;
            }
        }
        return true;
    }
}