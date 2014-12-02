package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import com.dxs.Entity.Category;
import com.dxs.Service.Intf.CategoryService;

import com.dxs.Util.MySqlUtil;

/**
 * 壁纸分类的数据库实现类
 * @author  姓名 工号
 * @version  [版本号, 2014-6-25]
 */
public class CategoryServiceImpl implements CategoryService
{
    private Connection conn = null;
    
    private PreparedStatement ps = null;
    
    private ResultSet rs = null;
    
    /**
     * add
     * 
     * @param cate
     * @return
     */
    public int addCategory(Category cate)
    {
        conn = MySqlUtil.getConnection();
        int row = 0;
        if (conn != null)
        {
            try
            {
                String sql = "INSERT INTO `category` (`categoryId`,`categoryName`) VALUES (?,?)";
                String[] parm = {String.valueOf(cate.getCategoryId()), cate.getCategoryName()};
                row = MySqlUtil.executeSQL(conn, sql, parm);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                MySqlUtil.closeAll(conn, ps, rs);
            }
            return row;
        }
        else
        {
            return -1;
        }
    }
    
    /**
     * 删除分类
     * 
     * @param cateId
     * @return
     */
    public int DelByCateId(int cateId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "DELETE FROM category WHERE categoryId=" + cateId;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    /**
     * 获取分类的最大ID
     * 重载方法
     * @return
     */
    public int findMaxCategoryId()
    {
        int count = 0;
        String sql = "SELECT max(categoryId) FROM category";
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
     * 根据分类获取名字    
     * 重载方法
     * @param name
     * @return
     */
    public Category searchCateByName(String name)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            Category cate = null;
            try
            {
                String sql = "SELECT * FROM category where categoryName='" + name + "'";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    cate = new Category();
                    cate.setCategoryId(rs.getInt("categoryId"));
                    cate.setCategoryName(rs.getString("categoryName"));
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
            return cate;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 根据ID获取分类
     * 重载方法
     * @param id
     * @return
     */
    public Category getCateById(int id)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            Category cate = null;
            try
            {
                String sql = "SELECT * FROM category where categoryId=" + id;
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    cate = new Category();
                    cate.setCategoryId(rs.getInt("categoryId"));
                    cate.setCategoryName(rs.getString("categoryName"));
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
            return cate;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 获取分类列表指定记录条数
     * 重载方法
     * @param limit
     * @return
     */
    public List<Category> getAllCateList(int limit)
    {
        conn = MySqlUtil.getConnection();
        ResultSet rs = null;
        if (conn != null)
        {
            String sql = "";
            try
            {
                if (limit == 0)
                {
                    sql = "SELECT * FROM category order by categoryId ";
                }
                else
                {
                    sql = "SELECT * FROM category order by categoryId limit " + limit;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getCateList(rs);
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
            finally
            {
                MySqlUtil.closeAll(conn, ps, rs);
            }
        }
        
        else
        {
            return null;
        }
    }
    
    private List<Category> getCateList(ResultSet rs)
        throws SQLException
    {
        if (rs != null)
        {
            List<Category> cateList = new ArrayList<Category>();
            while (rs.next())
            {
                Category cate = new Category();
                cate.setCategoryId(rs.getInt("categoryId"));
                cate.setCategoryName(rs.getString("categoryName"));
                cateList.add(cate);
            }
            return cateList;
        }
        else
        {
            return null;
        }
    }
}
