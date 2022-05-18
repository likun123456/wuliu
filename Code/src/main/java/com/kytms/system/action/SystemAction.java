package com.kytms.system.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.constants.SystemInfo;
import com.kytms.core.model.CommModel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 系统属性类
 *
 * @author 奇趣源码
 * @create 2017-11-19
 */
@Controller
@RequestMapping("/system")
public class SystemAction extends BaseAction{
    private final Logger log = Logger.getLogger(SystemAction.class);//输出Log日志
    @RequestMapping(value = "/getSystemInfo", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getList(CommModel commModel){
        String n = "name";
        String v = "value";
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        Map<String,String> systemMap1 = new HashMap<String, String>();
        Map<String,String> systemMap2 = new HashMap<String, String>();
        Map<String,String> systemMap3 = new HashMap<String, String>();
        Map<String,String> systemMap4 = new HashMap<String, String>();
        Map<String,String> systemMap5 = new HashMap<String, String>();
        Map<String,String> systemMap6 = new HashMap<String, String>();
        Map<String,String> systemMap7 = new HashMap<String, String>();
        Map<String,String> systemMap8 = new HashMap<String, String>();
        Map<String,String> systemMap9 = new HashMap<String, String>();
        Map<String,String> systemMap10 = new HashMap<String, String>();
        Map<String,String> systemMap11 = new HashMap<String, String>();
        Map<String,String> systemMap12 = new HashMap<String, String>();
        systemMap1.put(n,"Java 运行时环境版本");
        systemMap1.put(v, SystemInfo.JAVA_VERSION);
        list.add(systemMap1);
        systemMap2.put(n,"用户的当前工作目录");
        systemMap2.put(v, SystemInfo.JAVA_USER_DIR);
        list.add(systemMap2);
        systemMap3.put(n,"用户的主目录");
        systemMap3.put(v, SystemInfo.JAVA_USER_HOME);
        list.add(systemMap3);
        systemMap4.put(n,"用户的账户名称");
        systemMap4.put(v, SystemInfo.JAVA_USER_NAME);
        list.add(systemMap4);
        systemMap5.put(n,"行分隔符（在 UNIX 系统中是“/n”）");
        systemMap5.put(v, SystemInfo.JAVA_LINE_SEPARATOR);
        list.add(systemMap5);
        systemMap6.put(n,"路径分隔符（在 UNIX 系统中是“:”）");
        systemMap6.put(v, SystemInfo.JAVA_PATH_SEPARATOR);
        list.add(systemMap6);
        systemMap7.put(n," 文件分隔符（在 UNIX 系统中是“/”）");
        systemMap7.put(v, SystemInfo.JAVA_FILE_SEPARATOR);
        list.add(systemMap7);
        systemMap8.put(n,"操作系统的版本");
        systemMap8.put(v,SystemInfo.JAVA_OS_VERSION);
        list.add(systemMap8);
        systemMap9.put(n,"操作系统的架构");
        systemMap9.put(v, SystemInfo.JAVA_OS_ARCH);
        list.add(systemMap9);
        systemMap10.put(n,"操作系统名称");
        systemMap10.put(v, SystemInfo.JAVA_OS_NAME);
        list.add(systemMap10);
        systemMap11.put(n,"默认的临时文件路径");
        systemMap11.put(v, SystemInfo.JAVA_IO_TEMDIR);
        list.add(systemMap11);
        systemMap12.put(n,"类路径");
        systemMap12.put(v, SystemInfo.JAVA_CLASS_PATH);
        list.add(systemMap12);
        return returnJson(list);
    }
}
