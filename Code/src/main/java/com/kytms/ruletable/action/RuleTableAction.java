package com.kytms.ruletable.action;

import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import com.kytms.core.action.BaseAction;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.core.utils.UUIDUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2017/12/18.
 * 收发货方
 */
@Controller
@RequestMapping("/ruletable")
public class RuleTableAction extends BaseAction {
    private final Logger log = Logger.getLogger(RuleTableAction.class);//输出Log日志
    public static final String RULE_TABLE ="ruleTable_";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //获取一个Bean
    @RequestMapping(value = "/deleteTable", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String deleteTable(String tableName) {
        String Sql = "drop table   "+RULE_TABLE+tableName;
        jdbcTemplate.execute(Sql);
        return getReturnModel().toJsonString();
    }

    @RequestMapping(value = "/getList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String tableList(CommModel commModel) {
        String Sql = "select SUBSTRING(TABLE_NAME,11) as TABLE_NAME,ENGINE,TABLE_ROWS,TABLE_COMMENT from information_schema.tables where table_schema = '"+getTableName()+"' and table_type='base table' and TABLE_NAME like '"+RULE_TABLE+"%'";
        System.out.println(Sql);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(Sql);
        return returnJson(maps);
    }

    public String getTableName(){
        Connection connection = null;
        Statement statement = null;
        String tableName = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("select database();");
            while(rs.next()){
                tableName=rs.getString(1);
            }

        }catch (SQLException ex){
            JdbcUtils.closeConnection(connection);
            JdbcUtils.closeStatement(statement);
        }finally {
            JdbcUtils.closeConnection(connection);
            JdbcUtils.closeStatement(statement);
        }
        return tableName;
    }
    //保存bean
    @RequestMapping(value = "/saveRuleTable", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveRuleTable(String name, String newName) {
        ReturnModel returnModel = getReturnModel();
        if(StringUtils.isEmpty(name)){//新增
            String SQL = "CREATE TABLE "+RULE_TABLE+newName+"( `新表` varchar(255))";
            jdbcTemplate.execute(SQL);
        }else {
            String SQL = "ALTER TABLE "+RULE_TABLE+name+" RENAME TO "+RULE_TABLE+newName ;
            jdbcTemplate.execute(SQL);
        }

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/getColumns", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getColumns(String tableName) {
        tableName = RULE_TABLE+tableName;
        String Sql = "SELECT a.COLUMN_NAME, a.DATA_TYPE, a.CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS a WHERE a.table_name = '"+tableName+"'";
        System.out.println(Sql);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(Sql);
        return returnJson(maps);
    }
    @RequestMapping(value = "/saveColumn", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveColumn(String tableName,int id, String COLUMN_NAME) {
        ReturnModel returnModel = getReturnModel();
        String Sql = "SELECT a.COLUMN_NAME, a.DATA_TYPE, a.CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS a WHERE a.table_name = '"+RULE_TABLE+tableName+"'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(Sql);
        Map<String, Object> stringObjectMap = maps.get(id - 1);
        String sql =  "ALTER TABLE "+RULE_TABLE+tableName+"  CHANGE "+stringObjectMap.get("COLUMN_NAME").toString()+" "+COLUMN_NAME + " VARCHAR(256)";
        System.out.println(sql);
        jdbcTemplate.execute(sql);

        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/addColumn", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addColumn(String tableName) {
        ReturnModel returnModel = getReturnModel();
        String sql = "ALTER  TABLE "+RULE_TABLE+tableName+" add 新字段"+ UUIDUtil.getUuidTo32()+" VARCHAR(256)";
        jdbcTemplate.execute(sql);
        return returnModel.toJsonString();
    }

    @RequestMapping(value = "/delColumn", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String delColumn(String tableName,String columnName) {
        ReturnModel returnModel = getReturnModel();
        String sql = " alter table "+RULE_TABLE+tableName+" drop column "+columnName;
        jdbcTemplate.execute(sql);
        return returnModel.toJsonString();
    }
    @RequestMapping(value = "/getTableData", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getTableData(String tableName) {
        String Sql = "select * from "+ RULE_TABLE+tableName;
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(Sql);
        return returnJson(maps);
    }
    @RequestMapping(value = "/saveTableData", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveTableData(String tableName,String data) {
        List<Map> maps = JSONObject.parseArray(data, Map.class);
        jdbcTemplate.execute("delete from "+RULE_TABLE+tableName);
        for (Map<String, String> m:maps ) {
            m.remove("COLUMN_NAME");
            m.remove("des");
            StringBuilder columnName = new StringBuilder();
            StringBuilder value = new StringBuilder();
            for (Map.Entry<String, String> sa : m.entrySet()) {
                columnName.append(sa.getKey()+",");
                value.append("'"+sa.getValue()+"',");
            }
            columnName.deleteCharAt(columnName.lastIndexOf(","));
            value.deleteCharAt(value.lastIndexOf(","));
            String sql = "INSERT INTO "+RULE_TABLE+tableName+" ("+columnName.toString()+") VALUES ("+value.toString()+")";
            System.out.println(sql);
            jdbcTemplate.execute(sql);
        }


        return getReturnModel().toJsonString();
    }
    /**
     * @Author: 孙德增
     * @Date: 2019-08-05
     * @zhushi:单价表导入
     * @return java.util.List
     **/
    @RequestMapping(value = "/ruletableUpload", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  ruletableUpload(@RequestParam("file") MultipartFile file,String tableName){
       String s = "失败";
        ReturnModel returnModel = new ReturnModel();
        boolean result = returnModel.isResult();
        List<String> dataList=new ArrayList<String>();
        String filePath = null;
       InputStream is;
        String tablename ="ruletable_"+tableName ;
        try {
            CommonsMultipartFile cf= (CommonsMultipartFile)file;
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            is=fi.getInputStream();
            BufferedReader br=null;
            try {
                br = new BufferedReader(new InputStreamReader(is,"UTF-8"));    //转码，csv文件一般是gbk码
                String line = "";
                while ((line = br.readLine()) != null) {
                       dataList.add(line);
                }
                if(dataList.size()>0){
                    jdbcTemplate.execute("delete from "+tablename);
                    StringBuilder columnName = new StringBuilder();
                    StringBuilder value = new StringBuilder();
                    String[] split = null;
                    for (int i = 1; i <dataList.size() ; i++) {
                           String s1 = dataList.get(i);
                           split = s1.split(",");
                        for (int j = 0; j <split.length ; j++) {
                            value.append("'");
                            value.append(split[j]);
                            value.append("'");
                            value.append(",");
                        }
                        value.deleteCharAt(value.lastIndexOf(","));
                        String sql = "INSERT INTO "+tablename+"  VALUES ("+value.toString()+")";
                        jdbcTemplate.execute(sql);
                        value = new StringBuilder();
                        s = "成功";
                        returnModel.setObj(s);
                    }
                }
            }catch (Exception e) {
            }finally{
                if(br!=null){
                    try {
                        br.close();
                        br=null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnModel.toJsonString();
    }
}
