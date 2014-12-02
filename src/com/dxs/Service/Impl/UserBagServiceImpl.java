package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import com.dxs.Entity.UserBag;
import com.dxs.Service.Intf.UserBagService;
import com.dxs.Util.MySqlUtil;

public class UserBagServiceImpl implements UserBagService
{
    private Connection conn = null;
    
    private PreparedStatement ps = null;
    
    private ResultSet rs = null;
    
    public int findMaxId()
    {
        int count = 0;
        String sql = "SELECT max(id) FROM userbag";
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
    
    // add
    public int addUserBag(UserBag pg)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            try
            {
                String sql =
                    "INSERT INTO `userbag` (`id`, `bagName`, `uploadTime`, `tag`, `cover`, `userId`,`version`,`sver`,`initial`,`num`) VALUES (?,?,?,?,?,?,?,?,?,?)";
                SimpleDateFormat forDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datetime = forDateTime.format(pg.getUploadTime());
                String[] parm =
                    {String.valueOf(pg.getId()), pg.getBagName(), datetime, pg.getTag(), pg.getCover(),
                        String.valueOf(pg.getUserId()), pg.getVersion(), pg.getSver(), pg.getInitial(),
                        String.valueOf(pg.getNum())};
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
    
}
