package com.kytms.core.aop;

import com.kytms.core.entity.BaseEntity;
import com.kytms.core.entity.ObjectUtils;
import com.kytms.core.entity.User;
import com.kytms.core.utils.DateUtils;
import com.kytms.core.utils.SessionUtil;
import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 用于修改Bean树形的AOP类
 *
 * @author
 * @create 2017-11-26
 */
@Component(value = "HiberAspect")
public class HiberAspect extends EmptyInterceptor {
    private static final Logger logger = Logger.getLogger(HiberAspect.class);

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, org.hibernate.type.Type[] types) {
        User currentUser = null;
        try {
            currentUser = SessionUtil.getUser();
        } catch (RuntimeException e) {
            logger.warn("当前session为空,无法获取用户");
        }
        if(currentUser==null){
            return true;
        }
        try {
            //添加数据
            for (int index=0;index<propertyNames.length;index++)
            {
		     /*找到名为"创建时间"的属性*/
                if (BaseEntity.CREATE_TIME.equals(propertyNames[index]))
                {
		         /*使用拦截器将对象的"创建时间"属性赋上值*/
                    if(ObjectUtils.isEmpty(state[index])){
                        state[index] = DateUtils.gettimestamp();
                    }
                    continue;
                }
		     /*找到名为"创建人"的属性*/
                else if (BaseEntity.CREATE_NAME.equals(propertyNames[index]))
                {
		         /*使用拦截器将对象的"创建人"属性赋上值*/
                    if(ObjectUtils.isEmpty(state[index])){
                        state[index] = currentUser.getName();
                    }
                    continue;
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, org.hibernate.type.Type[] types) {
        User currentUser = null;
        try {
            currentUser = SessionUtil.getUser();
        } catch (RuntimeException e1) {
            //logger.warn("当前session为空,无法获取用户");
        }
        if(currentUser==null){
            return true;
        }
        //添加数据
        for (int index=0;index<propertyNames.length;index++)
        {
         /*找到名为"修改时间"的属性*/
            if (BaseEntity.MODIFY_NAME.equals(propertyNames[index]) )
            {
             /*使用拦截器将对象的"修改时间"属性赋上值*/
                currentState[index] = currentUser.getName();
                continue;
            }
         /*找到名为"修改人"的属性*/
            else if (BaseEntity.MODIFY_TIME.equals(propertyNames[index]))
            {
             /*使用拦截器将对象的"修改人"属性赋上值*/
                currentState[index] = DateUtils.gettimestamp();
                continue;
            }
         /*找到名为"修改人名称"的属性*/
        }
        return true;
    }
}
