package com.kytms.orderabnormal.service.impl;

import com.kytms.core.entity.Abnormal;
import com.kytms.core.entity.AbnormalDetail;
import com.kytms.core.entity.DictionaryDetail;
import com.kytms.core.exception.AppBugException;
import com.kytms.core.model.CommModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.ReflectionUtils;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.SpringUtils;
import com.kytms.core.utils.StringUtils;
import com.kytms.orderabnormal.dao.AbnormalDao;
import com.kytms.orderabnormal.dao.impl.AbnormalDetailDaoImpl;
import com.kytms.orderabnormal.service.AbnormalService;
import com.kytms.system.dao.DataBookItemDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 订单异常单服务层实现类
 *
 * @author 陈小龙
 * @create 2018-01-11
 */
@Service(value = "AbnormalService")
public class AbnormalServiceImpl extends BaseServiceImpl<Abnormal> implements AbnormalService<Abnormal> {
    private final Logger log = Logger.getLogger(AbnormalServiceImpl.class);//输出Log日志
    private AbnormalDao abnormalDao;
    @Resource(name = "AbnormalDao")
    public void setAbnormalDao(AbnormalDao abnormalDao) {
        this.setBaseDao(abnormalDao);
        this.abnormalDao = abnormalDao;
    }



    public Object getOrderAbnormalList(CommModel commModel) {
        String where = " and organization.id = '"+ SessionUtil.getOrgId()+"' and type = 0";
        if(StringUtils.isNotEmpty(commModel.getWhereValue())){
            where+=" and type="+commModel.getWhereValue();
        }
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where += " and status = "+commModel.getStatus();
        }
        String orderBY = " ORDER BY  create_time desc";
        return super.getListByPage(commModel,where,orderBY);
    }

    public Object saveAbnormal(Object o) {
        return abnormalDao.savaBean(o);
    }



    /**
     * 利用反射和注解循环对比类是否一致 返回值个异常明细集合
     * 并将targer 和 source不同的值 赋值给source
     * @param source
     * @param targer
     * @return
     */
    private static final DataBookItemDao dataBookItemDao = SpringUtils.getBean(DataBookItemDao.class);


    public List<AbnormalDetail> getAbnormalList(Object source, Object targer){
        Map<String,Field> targerMap = new HashMap<String, Field>(); //目标缓存变量
        List<AbnormalDetail> abnormalDetails = new ArrayList<AbnormalDetail>();
        Field[] declaredFields = targer.getClass().getDeclaredFields();
        for (Field id:declaredFields) {
            targerMap.put(id.getName(),id);
        }
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field:fields) {
            com.kytms.orderabnormal.Abnormal annotation = field.getAnnotation(com.kytms.orderabnormal.Abnormal.class);
            if (annotation!=null){ //说明注解有
                AbnormalDetail abnormalDetail = new AbnormalDetail();
                String name = field.getName();
                Field targerField = targerMap.get(name);
                if (field.getGenericType().toString().equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    String sourceValue = (String) ReflectionUtils.invokeGetMethod(source, name, null);
                    String targerValue = (String) ReflectionUtils.invokeGetMethod(targer, name, null);
                    if (StringUtils.isEmpty(sourceValue)){
                        sourceValue = "空";
                    }
                    if (StringUtils.isEmpty(targerValue)){
                        targerValue = "空";
                    }
                    if (!sourceValue.equals(targerValue)){
                        abnormalDetail.setSource(name);
                        abnormalDetail.setTarger(annotation.name());
                        abnormalDetail.setSourceValue(sourceValue);
                        abnormalDetail.setTargerValue(targerValue);
                        abnormalDetails.add(abnormalDetail);
                        ReflectionUtils.invokeSetMethod(source,name,new Object[]{targerValue});
                    }
                    continue; //跳出本次循环
                }
                // 如果类型是Integer
                if (field.getGenericType().toString().equals( "class java.lang.Integer") || field.getGenericType().toString().equals("int")) {
                    Integer sourceValue = (Integer) ReflectionUtils.invokeGetMethod(source, name, null);
                    Integer targerValue = (Integer) ReflectionUtils.invokeGetMethod(targer, name, null);
                    if(sourceValue == null){
                        sourceValue = 0;
                    }
                    if(targerValue == null){
                        targerValue = 0;
                    }
                    if (!sourceValue.equals(targerValue)){
                        abnormalDetail.setSource(name);
                        abnormalDetail.setTarger(annotation.name());
                        String s = annotation.dataBookName();
                        if (StringUtils.isEmpty(s)){
                            abnormalDetail.setSourceValue(sourceValue.toString());
                            abnormalDetail.setTargerValue(targerValue.toString());
                        }else {
                            abnormalDetail.setSourceValue(getDataBookValue(s,sourceValue.toString()));
                            abnormalDetail.setTargerValue(getDataBookValue(s,targerValue.toString()));
                        }


                        abnormalDetails.add(abnormalDetail);
                        ReflectionUtils.invokeSetMethod(source,name,new Object[]{targerValue.intValue()});
                    }
                    continue; //跳出本次循环

                }

                // 如果类型是Double
                if (field.getGenericType().toString().equals( "class java.lang.Double") || field.getGenericType().toString().equals("double") ) {
                    Double sourceValue = (Double) ReflectionUtils.invokeGetMethod(source, name, null);
                    Double targerValue = (Double) ReflectionUtils.invokeGetMethod(targer, name, null);
                    if(sourceValue == null){
                        sourceValue = 0.0;
                    }
                    if(targerValue == null){
                        targerValue = 0.0;
                    }
                    if (!sourceValue.equals(targerValue)){
                        abnormalDetail.setSource(name);
                        abnormalDetail.setTarger(annotation.name());
                        abnormalDetail.setSourceValue(sourceValue.toString());
                        abnormalDetail.setTargerValue(targerValue.toString());
                        abnormalDetails.add(abnormalDetail);
                        ReflectionUtils.invokeSetMethod(source,name,new Object[]{targerValue});
                    }
                    continue; //跳出本次循环
                }
                if (field.getGenericType().toString().equals( "class java.sql.Timestamp")) {
                    Timestamp sourceValue = (Timestamp) ReflectionUtils.invokeGetMethod(source, name, null);
                    Timestamp targerValue = (Timestamp) ReflectionUtils.invokeGetMethod(targer, name, null);
                    if (sourceValue == null){
                        if (targerValue == null){
                            continue; //跳出本次循环
                        }else {
                            abnormalDetail.setSource(name);
                            abnormalDetail.setTarger(annotation.name());
                            abnormalDetail.setSourceValue("无");
                            abnormalDetail.setTargerValue(targerValue.toString());
                            abnormalDetails.add(abnormalDetail);
                            ReflectionUtils.invokeSetMethod(source,name,new Object[]{targerValue});
                        }
                    }
                    if(sourceValue ==null){
                        abnormalDetail.setSource(name);
                        abnormalDetail.setTarger(annotation.name());
                        abnormalDetail.setSourceValue("空");
                        abnormalDetail.setTargerValue(targerValue.toString());
                        abnormalDetails.add(abnormalDetail);
                        ReflectionUtils.invokeSetMethod(source,name,new Object[]{targerValue});

                    }else {
                        if (!sourceValue.equals(targerValue)){
                            abnormalDetail.setSource(name);
                            abnormalDetail.setTarger(annotation.name());
                            abnormalDetail.setSourceValue(sourceValue.toString());
                            abnormalDetail.setTargerValue(targerValue.toString());
                            abnormalDetails.add(abnormalDetail);
                            ReflectionUtils.invokeSetMethod(source,name,new Object[]{targerValue});
                        }
                    }

                    continue; //跳出本次循环
                }
                Class<?>[] clszz = annotation.clszz();
                if (annotation.clszz().getClass() != null) {
                    Class<?>[] clszzs = annotation.clszz();
                    Object sourceObj = ReflectionUtils.invokeGetMethod(source, name, null);
                    Object targerObj = ReflectionUtils.invokeGetMethod(targer, name, null);
                    if(sourceObj == null && targerObj== null){
                        continue;
                    }


                    if(sourceObj == null){
                        String targerid = (String) ReflectionUtils.invokeGetMethod(targerObj, "id", null);
                        Entity annotation1 = targerObj.getClass().getAnnotation(Entity.class); //获取表明
                        String tableName = annotation1.name();
                        List list = dataBookItemDao.executeQueryHql("FROM " + tableName+" WHERE id='" + targerid + "'");
                        if (list.size()!=1){
                            throw new AppBugException(" abnormalUtls 类 程序出先BUG,请联系管理员");
                        }
                        Object o = list.get(0);
                        String targerName = (String) ReflectionUtils.invokeGetMethod(o, annotation.clazzName(), null);
                        abnormalDetail.setSource(name);
                        abnormalDetail.setTarger(annotation.name());
                        abnormalDetail.setSourceValue("空");
                        abnormalDetail.setTargerValue(targerName);
                        abnormalDetails.add(abnormalDetail);
                        ReflectionUtils.invokeSetMethod(source,name,new Object[]{o});
                        continue; //跳出本次循环
                    }
                    if(targerObj == null){
                        String sourceName = (String) ReflectionUtils.invokeGetMethod(sourceObj, annotation.clazzName(), null);
                        abnormalDetail.setSource(name);
                        abnormalDetail.setTarger(annotation.name());
                        abnormalDetail.setSourceValue(sourceName);
                        abnormalDetail.setTargerValue("空");
                        abnormalDetails.add(abnormalDetail);
                        ReflectionUtils.invokeSetMethod(source,name,new Object[]{null});
                        continue; //跳出本次循环
                    }
                    String sourceid = (String) ReflectionUtils.invokeGetMethod(sourceObj, "id", null);
                    String targerid = (String) ReflectionUtils.invokeGetMethod(targerObj, "id", null);

                    boolean equals = sourceid.equals(targerid);

                    if(!equals){ //如果ID不相等
                        Entity annotation1 = targerObj.getClass().getAnnotation(Entity.class); //获取表明
                        String tableName = annotation1.name();
                        List list = dataBookItemDao.executeQueryHql("FROM " + tableName+" WHERE id='" + targerid + "'");
                        if (list.size()!=1){
                            throw new AppBugException(" abnormalUtls 类 程序出先BUG,请联系管理员");
                        }
                        Object o = list.get(0);
                        String sourceName = (String) ReflectionUtils.invokeGetMethod(sourceObj, annotation.clazzName(), null);
                        String targerName = (String) ReflectionUtils.invokeGetMethod(o, annotation.clazzName(), null);
                        abnormalDetail.setSource(name);
                        abnormalDetail.setTarger(annotation.name());
                        abnormalDetail.setSourceValue(sourceName);
                        abnormalDetail.setTargerValue(targerName);
                        abnormalDetails.add(abnormalDetail);
                        ReflectionUtils.invokeSetMethod(source,name,new Object[]{o});
                        continue; //跳出本次循环
                    }
                }




            }
        }

        return abnormalDetails;
    }
    private  String getDataBookValue(String dataBook,String value){
        String Hql = "SELECT a FROM JC_SYS_DATA_DICTIONARY_DETAIL a LEFT JOIN a.dataBook b where a.name='"+value+"' and b.code = '"+dataBook+"'";
        List list = dataBookItemDao.executeQueryHql(Hql);
        DictionaryDetail o = (DictionaryDetail) list.get(0);
        return o.getValue();
    }
}
