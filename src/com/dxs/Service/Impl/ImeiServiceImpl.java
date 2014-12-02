package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dxs.Entity.Imei;
import com.dxs.Service.Intf.ImeiService;
import com.dxs.Util.MySqlUtil;

public class ImeiServiceImpl implements ImeiService
{
    private Connection conn = null;
    
    private PreparedStatement ps = null;
    
    private ResultSet rs = null;
    
    // add
    public int addImei(Imei imei)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            try
            {
                String sql = "INSERT INTO `imei` (`imeiId`) VALUES(?)";
                String[] parm = {imei.getImei()};
                return MySqlUtil.executeSQL(conn, sql, parm);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return -1;
            }
        }
        else
        {
            return -1;
        }
    }
    
    // ͳ������
    public int findImeiCount()
    {
        int count = 0;
        String sql = "select count(*) from imei";
        try
        {
            conn = MySqlUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            MySqlUtil.closeAll(conn, ps, rs);
        }
        return count;
    }
    
    // ���Ҵ��ڷ�
    public Imei getImeiById(String imei)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            Imei imi = null;
            try
            {
                String sql = "select * from imei where imeiId='" + imei + "'";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    imi = new Imei();
                    imi.setImei(rs.getString("imeiId"));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                MySqlUtil.closeAll(conn, ps, rs);
            }
            return imi;
        }
        else
        {
            return null;
        }
    }
    
}
