package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dxs.Service.Intf.ClassificationMangerService;
import com.dxs.Util.MySqlUtil;

/**
 * 分类管理的实现类
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-30]
 */
public class ClassificationMangerServiceImp implements ClassificationMangerService
{
    private Connection conn = null;
    
    private PreparedStatement ps = null;
    
    private ResultSet rs = null;
    
    /**
     * 分类壁纸关联表中插入数据 重载方法
     * 
     * @param categoryId
     * @param wallpaperId
     */
    public void insertToCategoryPaper(String categoryId, String wallpaperId)
    {
        conn = MySqlUtil.getConnection();
        try
        {
            String sql = "INSERT INTO `categoryPaper` (`categoryId`,`wallpaperId`) VALUES (?,?)";
            String[] parm = {categoryId, wallpaperId};
            MySqlUtil.executeSQL(conn, sql, parm);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 标签壁纸关联表中插入数据 重载方法
     * 
     * @param categoryId
     * @param wallpaperId
     */
    public void insertToTagPaper(String tagId, String wallpaperId)
    {
        conn = MySqlUtil.getConnection();
        try
        {
            String sql = "INSERT INTO `tagpaper` (`tagId`,`wallpaperId`) VALUES (?,?)";
            String[] parm = {tagId, wallpaperId};
            MySqlUtil.executeSQL(conn, sql, parm);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 根据分类ID获取 重载方法
     * 
     * @param classificationId
     * @return
     */
    public List<String> getWallPaperIdListByClassificationId(String classificationId)
    {
        List<String> wallPaperIdList = new ArrayList<String>();
        conn = MySqlUtil.getConnection();
        
        if (conn != null)
        {
            try
            {
                String sql = "SELECT * FROM categoryPaper where categoryId='" + classificationId + "'";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    wallPaperIdList.add(rs.getString("wallpaperId"));
                    // categoryPaper.setCategoryId(rs.getInt("categoryId"));
                    // categoryPaper.setCategoryName(rs.getString("categoryName"));
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
            return wallPaperIdList;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 根据标签Id获取标签壁纸关联中的ID列表
     * 重载方法
     * @param tagId
     * @return
     */
    public List<String> getWallPaperIdListByTagId(String tagId)
    {   
        List<String> wallPaperIdList = new ArrayList<String>();
        conn = MySqlUtil.getConnection();
        
        if (conn != null)
        {
            try
            {
                String sql = "SELECT * FROM tagpaper where tagId='" + tagId + "'";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    wallPaperIdList.add(rs.getString("wallpaperId"));
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
            return wallPaperIdList;
        }
        else
        {
            return null;
        }
    }
    
    public List<String> getTagIdList(String classificationId)
    {
        return null;
    }
    
    /**
     * 根据壁纸ID删除分类壁纸关联表中的数据
     * 重载方法
     * @param wallPaperId
     * @return
     */
    public int deleteCategoryPaperByPaperId(String wallPaperId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "DELETE FROM categorypaper WHERE wallpaperId=" + wallPaperId;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    /**
     * 根据分类ID获取标签列表
     * 重载方法
     * @param classificationId
     * @return
     */
    public List<String> getTagIdListByClassificationId(String classificationId)
    {   
        List<String> tagIdList = new ArrayList<String>();
        conn = MySqlUtil.getConnection();
        
        if (conn != null)
        {
            try
            {
                String sql = "SELECT * FROM categoryTag where categoryId='" + classificationId + "'";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    tagIdList.add(rs.getString("wallpaperId"));
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
            return tagIdList;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 将分类ID和标签ID插入到分类标签关联表中去
     * 重载方法
     * @param categoryId
     * @param tagId
     */
    public void insertToCategoryTag(String categoryId, String tagId)
    {
        conn = MySqlUtil.getConnection();
        try
        {
            String sql = "INSERT INTO `categorytag` (`categoryId`,`tagId`) VALUES (?,?)";
            String[] parm = {categoryId, tagId};
            MySqlUtil.executeSQL(conn, sql, parm);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
