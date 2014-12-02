package com.dxs.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;

/**
 * sql执行器
 * @author  姓名 工号
 * @version  [版本号, 2014-6-24]
 */
public class MySqlUtil
{
    /**
     * 数据库sql的配置文件
     */
    private static Properties mysqlConfigProperties = null;
    
    public static Properties loadMysqlConfig(String configFileName)
    {
        if (mysqlConfigProperties == null)
        {
            String name = "mysql_config.properties";
            if (configFileName != null && !configFileName.equals(""))
            {
                name = configFileName;
            }
            Properties properties = new Properties();
            try
            {
                InputStream inputStream = MySqlUtil.class.getClassLoader().getResourceAsStream(name);
                properties.load(inputStream);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
            mysqlConfigProperties = properties;
            return properties;
        }
        else
        {
            return mysqlConfigProperties;
        }
        
    }
    
    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection()
    {
        Properties properties = loadMysqlConfig(null);
        Connection conn = null;
        if (properties != null && !properties.isEmpty())
        {
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, username, password);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return conn;
    }
    
    public static int executeSQL(Connection conn, String psSql, String[] param)
    {
        if (conn != null)
        {
            PreparedStatement ps = null;
            int num = 0;
            try
            {
                ps = conn.prepareStatement(psSql);
                if (param != null)
                {
                    for (int i = 0; i < param.length; i++)
                    {
                        ps.setString(i + 1, param[i]);
                    }
                }
                num = ps.executeUpdate();
                return num;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return num;
            }
            
        }
        else
        {
            return -1;
        }
    }
    
    public static int executeSQL(Connection conn, String psSql)
    {
        
        if (conn != null)
        {
            PreparedStatement ps = null;
            int num = 0;
            try
            {
                ps = conn.prepareStatement(psSql);
                num = ps.executeUpdate();
                return num;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return num;
            }
        }
        else
        {
            return -1;
        }
    }
    
    public static ResultSet getResultSet(Connection conn, String sqlStr)
    {
        ResultSet rs = null;
        if (conn != null)
        {
            try
            {
                
                PreparedStatement pstmt = conn.prepareStatement(sqlStr);
                rs = pstmt.executeQuery();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return rs;
    }
    
    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs)
    {
        try
        {
            if (rs != null)
            {
                rs.close();
            }
            if (ps != null)
            {
                ps.close();
            }
            if (conn != null)
            {
                conn.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
