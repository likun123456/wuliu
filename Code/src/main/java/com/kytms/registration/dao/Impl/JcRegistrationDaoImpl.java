package com.kytms.registration.dao.Impl;

import com.kytms.core.dao.impl.BaseDaoImpl;
import com.kytms.core.entity.JcRegistration;
import com.kytms.registration.action.JcRegistrationAction;
import com.kytms.registration.dao.JcRegistrationDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**辽宁捷畅物流有限公司  集团信息技术中心
 * 孙德增
 * Created by sundezeng on 2017/12/8.
 */
@Repository(value = "JcRegistrationDao")
public class JcRegistrationDaoImpl extends BaseDaoImpl<JcRegistration> implements JcRegistrationDao<JcRegistration> {
    private final Logger log = Logger.getLogger(JcRegistrationDaoImpl.class);//输出Log日志
}
