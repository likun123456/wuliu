package com.kytms.rule

import com.alibaba.fastjson.JSONArray
import com.kytms.core.utils.MathExtend
import com.kytms.core.utils.SpringUtils
import com.kytms.rule.core.RuleMessageException
import org.apache.commons.collections.functors.FunctorUtils
import org.apache.commons.lang.StringUtils
import org.springframework.jdbc.core.JdbcTemplate

import java.text.DecimalFormat
import java.text.NumberFormat

class test {
    JdbcTemplate dao = SpringUtils.getBean(JdbcTemplate.class)
    def runRule(Object obj){

        return 123;
    }

    def sc(){
        String
        Map m = new HashMap();
        List l = new ArrayList();
        def parse = JSONArray.parse("['123']")
        JSONArray
        JSONObject
        JSONArray
    }

    Object 查表(String sql) throws Exception{
        return dao.queryForList(sql);
    }


    Object 最大(Object... objs){
        Object result = 0;
        objs.each{Object f = it;if(f > result){result = f;}}
        return result;
    }

    Object 最小(Object... objs){
        Object result = 100000000;
        objs.each{Object f = it;if(f < result){result = f;}}
        return result;
    }

    Object 属性求和(List list,String attribute){
        Object result = 0;
        list.each{result += it.get(attribute)}
        return result;
    }

    Object 求和(List list){
        Object result = 0;
        list.each{result += it}
        return result;
    }

    Object 舍尾取整(Object obj){
        return Math.floor(obj);
    }

    Object 进位取整(Object obj){
        return Math.ceil(obj);
    }

    Object 整除(Object o1,Object o2){
        return 舍尾取整(o1/o2);
    }

    Object 四舍五入(Object obj){
        return Math.round(obj);
    }

    Object 四舍五入(Object source, Object precision) {
        return MathExtend.round(source, precision);
    }



    Double 数值转换(Object str){
        return Double.parseDouble((String)str);
    }



    Date 现在(){
        return new Date();
    }
    List 分解列表(Object value){
        return FunctorUtils.parseStringToList(value);
    }



    void 打印(Object obj){
        println(obj);
    }

    Map 创建对象(){
        return new HashMap();
    }


    void 异常(Object message){
        throw new RuleMessageException((String)message);
    }



    boolean 包含(Object src ,Object desc){
        return ((String)src).contains((String)desc);
    }

    boolean 开始为(Object src ,Object desc){
        return ((String)src).startsWith((String)desc);
    }

    String 拼接字符串(Object... objs){
        String str = "";
        objs.each{
            str = str + "," + it;
        }
        return str;
    }

    Boolean 是否存在周末(Object date1,Object date2){
        int day = DateUtils.getBetweenDays((Date)date1,(Date)date2);
        if(day > 5){
            return true;
        }
        for(int i = 0; i <= day; i++){
            Date date = 添加日期小时(date1,i * 24);
            int d = 获取星期(date);
            if(d > 5){
                return true;
            }
        }
        return false;
    }


    Integer 获取随机数(Object repeatObj){
        int number = (int)repeatObj;
        int k = (int)Math.pow(10,number);
        Random r = new Random();
        return r.nextInt(k);
    }

    String 格式化字符(Object obj,Object patten,Object repeatObj){
        int repeat = (int)repeatObj;
        String pattenString = StringUtils.repeat(patten.toString(),repeat);
        NumberFormat formatter = new DecimalFormat(pattenString);
        return formatter.format(obj);
    }


}
