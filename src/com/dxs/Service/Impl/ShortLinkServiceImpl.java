package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dxs.Entity.ShortLink;
import com.dxs.Entity.ShortLinkComplement;
import com.dxs.Service.Intf.ShortLinkService;
import com.dxs.Util.MySqlUtil;

/**
 * 短连接执行类
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-17]
 */
public class ShortLinkServiceImpl implements ShortLinkService
{
    private Connection conn = null;
    
    private PreparedStatement ps = null;
    
    private ResultSet rs = null;
    
    /**
     * 添加短链接
     * 
     * @param sl
     * @return
     */
    public int addObject(ShortLink sl)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            try
            {
                String sql =
                    "INSERT INTO `wallpaper_db`.`shortLink` (`id`, `name`,`shortName`,`longUrl`, `shortUrl`,`flag`) VALUES (?,?,?,?,?,?)";
                String[] parm =
                    {String.valueOf(sl.getId()), sl.getName(), sl.getShortName(), sl.getLongUrl(), sl.getShortUrl(),
                        String.valueOf(sl.getFlag())};
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
    
    /**
     * 删除短链接
     * 
     * @param id
     * @return
     */
    public int DelById(int id)
    {
        conn = MySqlUtil.getConnection();
        String sql = "DELETE FROM shortLink WHERE id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    /**
     * 根据壁纸名字进行搜索 重载方法
     * 
     * @param name
     * @param flag
     * @return
     */
    public ShortLink getShortLinkByName(String name, int flag)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            ShortLink sl = null;
            try
            {
                String sql = "SELECT * FROM shortLink where name='" + name + "' and flag=" + flag;
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    sl = new ShortLink();
                    sl.setId(rs.getInt("id"));
                    sl.setName(rs.getString("name"));
                    sl.setShortName(rs.getString("shortName"));
                    sl.setLongUrl(rs.getString("longUrl"));
                    sl.setShortUrl(rs.getString("shortUrl"));
                    sl.setFlag(rs.getInt("flag"));
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
            return sl;
        }
        else
        {
            return null;
        }
    }
    
    public ShortLink getShortLinkByName(String shortname)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            ShortLink sl = null;
            try
            {
                String sql = "SELECT * FROM shortLink where shortName='" + shortname + "'";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    sl = new ShortLink();
                    sl.setId(rs.getInt("id"));
                    sl.setName(rs.getString("name"));
                    sl.setShortName(rs.getString("shortName"));
                    sl.setLongUrl(rs.getString("longUrl"));
                    sl.setShortUrl(rs.getString("shortUrl"));
                    sl.setFlag(rs.getInt("flag"));
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
            return sl;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 获取最大ID 重载方法
     * 
     * @return
     */
    public int findMaxId()
    {
        int count = 0;
        String sql = "SELECT max(id) FROM shortLink";
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
    
    /**
     * 获取需要补齐的短连接
     */
    public ShortLinkComplement getOneShortLinkRecordComplement()
    {
        String sql = "SELECT * FROM shortLinkComplement order by id LIMIT 1";
        ShortLinkComplement shortLinkComplement = null;
        try
        {
            conn = MySqlUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next())
            {
                shortLinkComplement = new ShortLinkComplement();
                shortLinkComplement.setWallpaperId(rs.getString("wallpaperId"));
            }
            return shortLinkComplement;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            MySqlUtil.closeAll(conn, ps, rs);
        }
        return null;
    }
    
    /**
     * 获取需要补齐的短连接
     */
    public int insertShortLinkComplement(String wallPaperId)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            try
            {
                String sql =
                    "INSERT INTO `shortLinkComplement` (`wallpaperId`) VALUES (?)";
                String[] parm =  {wallPaperId};
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
    
    /**
     * 删除补齐短连接记录
     */
    public int deleteShortLinkComplement(String wallPaperId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "DELETE FROM shortLinkComplement WHERE wallpaperId=" + wallPaperId;
        return MySqlUtil.executeSQL(conn, sql);
    }
}
