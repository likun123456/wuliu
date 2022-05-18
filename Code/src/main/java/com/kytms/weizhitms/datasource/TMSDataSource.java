package com.kytms.weizhitms.datasource;

import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.SpringUtils;
import org.apache.commons.dbcp.BasicDataSource;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:sundezeng
 * @Date:2019/3/14
 */
public class TMSDataSource {
    private TMSDataSource(){

    }

    private final BasicDataSource basicDataSource = SpringUtils.getBean("tmsDataSource");
    private static final TMSDataSource tmsDataSource = new TMSDataSource();

    public static TMSDataSource getInstance(){
            return  tmsDataSource;
    }
    public Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }

    public boolean exeSql(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();  // 首先要获取连接，即连接到数据库
            ps = conn.prepareStatement(sql);
            ps.executeUpdate(sql);
            ps.close();
            conn.close();  //关闭数据库连接
            conn = null;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                ps.close();
                conn.close();  //关闭数据库连接
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
        return false;
    }
    public boolean exeQuerSql(String sql,Object obj[]) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();  // 首先要获取连接，即连接到数据库
            ps = conn.prepareStatement(sql);
            for(int i=0;i<obj.length;i++){
//              给sql语句占位符赋值
                ps.setObject(i+1, obj[i]);
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                ps.close();
                conn.close();  //关闭数据库连接
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    public List<Map> exeQuerySql(String sql) {
        Connection conn = null;
        ResultSet resule = null;
        Statement statement = null;
        List<Map> beans = new ArrayList<Map>();
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            resule = statement.executeQuery(sql);
            while (resule.next()) {
                Map bean = new HashMap();
                ResultSetMetaData metaData = resule.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    bean.put(metaData.getColumnLabel(i), resule.getString(i));
                }
                beans.add(bean);
            }
            resule.close();
            statement.close();
            conn.close();
            return beans;
        } catch (SQLException e1) {
            try {
                resule.close();
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            e1.printStackTrace();
        }
        return null;
    }

}
