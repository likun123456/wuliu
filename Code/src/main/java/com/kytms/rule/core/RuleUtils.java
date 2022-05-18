package com.kytms.rule.core;

import com.kytms.core.utils.StringUtils;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.util.HashMap;
import java.util.Map;

/**
 * QQ 165149324
 * 奇趣源码
 *
 * @author
 * @create 2019-07-22
 */
public class RuleUtils {
    public static final Map ruleCache = new HashMap();
    public  ClassLoader parent = getClass().getClassLoader();
    public  GroovyClassLoader loader = new GroovyClassLoader(parent);

    private RuleUtils(){};
    public static  RuleUtils ruleUtils = new RuleUtils();
    public static RuleUtils getInstance(){
        return ruleUtils;
    }


    /**
     * 计算
     * @param ruleName
     * @param ages
     * @return
     */
    public  Object runRule(String ruleName,Object ages) {
        GroovyObject groovyObject = (GroovyObject) ruleCache.get(ruleName);
        Object o = groovyObject.invokeMethod("runRule", ages);
        return o;
    }
    /**
     * 规则上线
     * @param ruleName
     * @param rule
     */
    public  void ruleOilne(String ruleName,String rule) throws Exception {
        if(StringUtils.isEmpty(rule)){
            throw new Exception("规则不能为空");
        }
        String ruleTempler ="import com.kytms.core.utils.SpringUtils\n" +
                "import com.alibaba.fastjson.JSONArray\n" +
                "import com.alibaba.fastjson.JSONObject\n"+
                " import com.kytms.core.utils.MathExtend\n"+
                "import java.util.ArrayList\n"+
                "import java.lang.*\n"+
                "import com.kytms.rule.core.RuleMessageException\n" +
                "import org.apache.commons.lang.StringUtils\n" +
                "import org.springframework.jdbc.core.JdbcTemplate\n" +
                "\n" +
                "import java.text.DecimalFormat\n" +
                "import java.text.NumberFormat\n" +
                "\n" +
                "class "+ruleName+" {\n" +
                "    JdbcTemplate dao = SpringUtils.getBean(JdbcTemplate.class)\n" +
                "    def runRule(Object obj){\n" +
                "\n" +
                "       "+rule+"\n" +
                "    }\n" +
                "\n" +
                "    Object 查表(String sql) throws Exception{\n" +
                "        return dao.queryForList(sql);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    Object 最大(Object... objs){\n" +
                "        Object result = 0;\n" +
                "        objs.each{Object f = it;if(f > result){result = f;}}\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    Object 最小(Object... objs){\n" +
                "        Object result = 100000000;\n" +
                "        objs.each{Object f = it;if(f < result){result = f;}}\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    Object 属性求和(List list,String attribute){\n" +
                "        Object result = 0;\n" +
                "        list.each{result += it.get(attribute)}\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    Object 求和(List list){\n" +
                "        Object result = 0;\n" +
                "        list.each{result += it}\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    Object 舍尾取整(Object obj){\n" +
                "        return Math.floor(obj);\n" +
                "    }\n" +
                "\n" +
                "    Object 进位取整(Object obj){\n" +
                "        return Math.ceil(obj);\n" +
                "    }\n" +
                "\n" +
                "    Object 整除(Object o1,Object o2){\n" +
                "        return 舍尾取整(o1/o2);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    Object 四舍五入(Object obj){\n" +
                "        return Math.round(obj);\n" +
                "    }\n" +
                "\n" +
                "    Object 四舍五入(Object source, Object precision) {\n" +
                "        return MathExtend.round(source, precision);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    Double 数值转换(Object str){\n" +
                "        return Double.parseDouble((String)str);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    Date 现在(){\n" +
                "        return new Date();\n" +
                "    }\n" +
                "    List 分解列表(Object value){\n" +
                "        return FunctorUtils.parseStringToList(value);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    void 打印(Object obj){\n" +
                "        println(obj);\n" +
                "    }\n" +
                "\n" +
                "    Map 创建对象(){\n" +
                "        return new HashMap();\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    void 异常(Object message){\n" +
                "        throw new RuleMessageException((String)message);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "\n" +
                "    boolean 包含(Object src ,Object desc){\n" +
                "        return ((String)src).contains((String)desc);\n" +
                "    }\n" +
                "\n" +
                "    boolean 开始为(Object src ,Object desc){\n" +
                "        return ((String)src).startsWith((String)desc);\n" +
                "    }\n" +
                "\n" +
                "    String 拼接字符串(Object... objs){\n" +
                "        String str = \"\";\n" +
                "        objs.each{\n" +
                "            str = str + \",\" + it;\n" +
                "        }\n" +
                "        return str;\n" +
                "    }\n" +
                "\n" +
                "    Boolean 是否存在周末(Object date1,Object date2){\n" +
                "        int day = DateUtils.getBetweenDays((Date)date1,(Date)date2);\n" +
                "        if(day > 5){\n" +
                "            return true;\n" +
                "        }\n" +
                "        for(int i = 0; i <= day; i++){\n" +
                "            Date date = 添加日期小时(date1,i * 24);\n" +
                "            int d = 获取星期(date);\n" +
                "            if(d > 5){\n" +
                "                return true;\n" +
                "            }\n" +
                "        }\n" +
                "        return false;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    Integer 获取随机数(Object repeatObj){\n" +
                "        int number = (int)repeatObj;\n" +
                "        int k = (int)Math.pow(10,number);\n" +
                "        Random r = new Random();\n" +
                "        return r.nextInt(k);\n" +
                "    }\n" +
                "\n" +
                "    String 格式化字符(Object obj,Object patten,Object repeatObj){\n" +
                "        int repeat = (int)repeatObj;\n" +
                "        String pattenString = StringUtils.repeat(patten.toString(),repeat);\n" +
                "        NumberFormat formatter = new DecimalFormat(pattenString);\n" +
                "        return formatter.format(obj);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "}";
        Class groovyClass = loader.parseClass(ruleTempler);
        GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
        ruleCache.put(ruleName,groovyObject);

    }

    /**
     * 规则下线
     * @param ruleName
     */
    public  void ruleUnOilne(String ruleName){
        ruleCache.remove(ruleName);
    }
}
