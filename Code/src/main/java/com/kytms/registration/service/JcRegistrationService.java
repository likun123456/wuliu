package com.kytms.registration.service;

import com.kytms.core.entity.JcRegistration;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

import java.util.List;

/**辽宁捷畅物流有限公司  集团信息技术中心
 * 孙德增
 * Created by sundezeng on 2017/12/8.
 */
public interface JcRegistrationService<JcRegistration> extends BaseService<JcRegistration> {
    JgGridListModel getJcRegistrationList(CommModel commModel);

    List<com.kytms.core.entity.JcRegistration> getUserForExcel(JcRegistration jcRegistration);
}
