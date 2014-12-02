package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dxs.Entity.Tag;
import com.dxs.Entity.Tag_simple;
import com.dxs.Entity.Variable;
import com.dxs.Service.Intf.TagService;
import com.dxs.Util.MySqlUtil;

public class TagServiceImpl implements TagService
{
    private Connection conn = null;
    
    private PreparedStatement ps = null;
    
    private ResultSet rs = null;
    
    /**
     * add
     * 
     * @param tag
     * @return
     */
    public int addTag(Tag tag)
    {
        conn = MySqlUtil.getConnection();
        int row = 0;
        if (conn != null)
        {
            try
            {
                String sql =
                    "INSERT INTO `tag` (`tagId`,`tagName`, `categoryId`,`hotNum`,`count`,`flag`) VALUES (?,?,?,?,?,?)";
                String[] parm =
                    {String.valueOf(tag.getTagId()), tag.getTagName(), tag.getCategoryId(), String.valueOf(0),
                        String.valueOf(tag.getCount()), String.valueOf(tag.getFlag())};
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
     * delete
     * 
     * @param tagId
     * @return
     */
    public int DelByTagId(int tagId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "DELETE FROM tag WHERE tagId=" + tagId;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    public int DelByTagName(String name)
    {
        conn = MySqlUtil.getConnection();
        String sql = "DELETE FROM tag WHERE tagName='" + name + "'";
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    /**
     * ɾ��һ��tag�� ��ֱ��ɾ�����ʱ
     * 
     * @param categoryId
     * @return
     */
    public int DelAllByCategoryId(int categoryId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "DELETE FROM tag WHERE categoryId='" + categoryId + "'";
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    // update ��ǩ�����
    public int updateTagCateById(int cate, int id)
    {
        conn = MySqlUtil.getConnection();
        String sql = "UPDATE `tag` SET `categoryId`='" + cate + "' WHERE `tagId`=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    // update ��ǩ�������ȶ�
    public int updateTagCateAndHotById(int cate, int hotNum, int id)
    {
        conn = MySqlUtil.getConnection();
        String sql = "UPDATE `tag` SET `categoryId`='" + cate + "', `hotNum`=" + hotNum + " WHERE `tagId`=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    // update ��ǩ���Ƴ�ԭ���������ı�ǩ
    public int updateTagCateByCatId(int cateId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "UPDATE `tag` SET `categoryId`='0' WHERE `categoryId`='" + cateId + "'";
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    public int updateTagHotById(int id)
    {
        conn = MySqlUtil.getConnection();
        String sql = "UPDATE `tag` SET `hotNum`=hotNum+1 WHERE `tagId`=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    /**
     * ��ݴ���tagId�ַ���tagNameList
     * 
     * @param tagId �������ַ� ��12��13��14��15
     * @return
     */
    public List<Variable> getTagNameByStr_tagId(String tagId)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "select tag.tagName from tag where tag.tagId in(" + tagId + ")";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getVariableList(rs);
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
    
    public List<Tag> getTagByStr_tagId(String tagId)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "select * from tag where tag.tagId in(" + tagId + ")";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getTagList(rs);
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
    
    /**
     * ������ID ���TagList
     * 
     * @param categoryId
     * @return
     */
    public List<Tag> getTagByCategoryId(String categoryId, int limit)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            String sql = "";
            try
            {
                if (limit == 0)
                {
                    sql = "select * from tag where categoryId='" + categoryId + "' order by hotNum desc";
                }
                else
                {
                    sql = "select * from tag where categoryId='" + categoryId + "' order by hotNum desc limit " + limit;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getTagList(rs);
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
    
    public List<Tag_simple> getSimpleTagByCategoryId(String categoryId, int limit)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            String sql = "";
            try
            {
                if (limit == 0)
                {
                    sql = "select * from tag where categoryId='" + categoryId + "' order by hotNum desc";
                }
                else
                {
                    sql = "select * from tag where categoryId='" + categoryId + "' order by hotNum desc limit " + limit;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getsimpleTagList(rs);
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
    
    public List<Tag_simple> getSimpleTag(int limit)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            String sql = "";
            try
            {
                if (limit == 0)
                {
                    sql = "select * from tag order by hotNum desc";
                }
                else
                {
                    sql = "select * from tag order by hotNum desc limit " + limit;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getsimpleTagList(rs);
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
    
    // ������ID
    public int findMaxTagId()
    {
        int count = 0;
        String sql = "SELECT max(tagId) FROM tag";
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
    
    // ������count
    public int findMaxTagCount()
    {
        int count = 0;
        String sql = "SELECT max(count) FROM tag";
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
    
    // ����������
    public Tag searchTagByName(String name)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            Tag tag = null;
            try
            {
                String sql = "SELECT * FROM tag where tagName='" + name + "'";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    tag = new Tag();
                    tag.setTagId(rs.getInt("tagId"));
                    tag.setTagName(rs.getString("tagName"));
                    tag.setCategoryId(rs.getString("categoryId"));
                    tag.setHotNum(rs.getInt("hotNum"));
                    tag.setCount(rs.getInt("count"));
                    tag.setFlag(rs.getInt("flag"));
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
            return tag;
        }
        else
        {
            return null;
        }
    }
    
    public List<String> getIdByName(String name)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "SELECT tagId FROM tag where tagName like '%" + name + "%'";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getStringList(rs);
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
    
    public int getintIdByName(String name)
    {
        int count = 0;
        String sql = "SELECT tagId FROM tag where tagName = '" + name + "'";
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
     * 获取分类列表 重载方法
     * 
     * @return
     */
    public List<Tag> getAllTagList()
    {
        conn = MySqlUtil.getConnection();
        ResultSet rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "SELECT * FROM tag order by tagId,categoryId desc";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getTagList(rs);
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
    
    public List<Tag> getAllTagOrderByHotNum()
    {
        conn = MySqlUtil.getConnection();
        ResultSet rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "SELECT * FROM tag order by hotNum desc";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getTagList(rs);
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
    
    private List<Tag> getTagList(ResultSet rs)
        throws SQLException
    {
        if (rs != null)
        {
            List<Tag> tagList = new ArrayList<Tag>();
            while (rs.next())
            {
                Tag tag = new Tag();
                tag.setTagId(rs.getInt("tagId"));
                tag.setTagName(rs.getString("tagName"));
                tag.setCategoryId(rs.getString("categoryId"));
                tag.setHotNum(rs.getInt("hotNum"));
                tag.setCount(rs.getInt("count"));
                tag.setFlag(rs.getInt("flag"));
                tagList.add(tag);
            }
            return tagList;
        }
        else
        {
            return null;
        }
    }
    
    private List<String> getStringList(ResultSet rs)
        throws SQLException
    {
        if (rs != null)
        {
            List<String> strList = new ArrayList<String>();
            while (rs.next())
            {
                String str = String.valueOf(rs.getInt("tagId"));
                strList.add(str);
            }
            return strList;
        }
        else
        {
            return null;
        }
    }
    
    private List<Tag_simple> getsimpleTagList(ResultSet rs)
        throws SQLException
    {
        if (rs != null)
        {
            List<Tag_simple> tagList = new ArrayList<Tag_simple>();
            while (rs.next())
            {
                Tag_simple tag = new Tag_simple();
                tag.setTagId(rs.getInt("tagId"));
                tag.setTagName(rs.getString("tagName"));
                tagList.add(tag);
            }
            return tagList;
        }
        else
        {
            return null;
        }
    }
    
    private List<Variable> getVariableList(ResultSet rs)
        throws SQLException
    {
        if (rs != null)
        {
            List<Variable> variableList = new ArrayList<Variable>();
            while (rs.next())
            {
                Variable newvar = new Variable();
                newvar.setVar(rs.getString("tagName"));
                variableList.add(newvar);
            }
            return variableList;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 根基分类ID获取存在于分类ID关联的标签ID列表 重载方法
     * 
     * @param classificationId
     * @return
     */
    public List<Tag> getTagIdListByInClassificationId(String slistCondition)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "select * from tag where tagId in (" + slistCondition + ")";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getTagList(rs);
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
    
    /**
     * 根基分类ID获取存在于分类ID关联的标签非关联的ID列表 重载方法
     * 
     * @param classificationId
     * @return
     */
    public List<Tag> getTagIdListByNotInClassificationId(String slistCondition)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = null;
                if (slistCondition.isEmpty())
                {
                    sql = "select * from tag";
                }
                
                else
                {
                    sql = "select * from tag where tagId not in (" + slistCondition + ")";
                }
                
                rs = MySqlUtil.getResultSet(conn, sql);
                return getTagList(rs);
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
}
