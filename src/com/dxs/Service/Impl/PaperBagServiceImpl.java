package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dxs.Entity.CompileQueue;
import com.dxs.Entity.PaperBag;
import com.dxs.Entity.PaperBag_simple;
import com.dxs.Service.Intf.PaperBagService;

import com.dxs.Util.MySqlUtil;
import com.dxs.Util.OperateId;

public class PaperBagServiceImpl implements PaperBagService
{
    private Connection conn = null;
    
    private PreparedStatement ps = null;
    
    private ResultSet rs = null;
    
    /**
     * 根基ID删除 重载方法
     * 
     * @param id
     * @return
     */
    public int DelById(String id)
    {
        conn = MySqlUtil.getConnection();
        String sql = "DELETE FROM paperbag WHERE id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    // 改VERSION
    public int modifyVerSionById(String id, String ver)
    {
        conn = MySqlUtil.getConnection();
        String sql = "update paperbag set version=" + ver + ",initial='3' where id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    public int modifyVerNumById_noInitial(String id, String ver)
    {
        conn = MySqlUtil.getConnection();
        String sql = "update paperbag set version=" + ver + " where id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    /**
     * 修改资源版本号
     * 
     * @param id
     * @param sver
     * @return
     */
    public int modifySverById(String id, String sver)
    {
        conn = MySqlUtil.getConnection();
        String sql = "update paperbag set sver=" + sver + " where id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    /**
     * 查看资源版本号 重载方法
     * 
     * @param id
     * @param recstate
     * @param recDate
     * @return
     */
    public String getSverById(String id)
    {
        String version = null;
        String sql = "select sver from paperbag where id=" + id;
        
        try
        {
            conn = MySqlUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next())
            {
                version = rs.getString(1);
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
        return version;
    }
    
    // 改加推荐
    public int modifyRecStateById(String id, String recstate, Date recDate)
    {
        SimpleDateFormat forDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        conn = MySqlUtil.getConnection();
        String datetime = forDateTime.format(recDate);
        String sql =
            "update paperbag set state_rec='" + recstate + "',recTime='" + datetime + "' where state=1 and id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    // 改不推荐
    public int modifyRecStateById(String id, String recstate)
    {
        conn = MySqlUtil.getConnection();
        String sql = "update paperbag set state_rec='" + recstate + "' where state=1 and id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    public int modifyPaperBagCateIdById(int id, int tocateId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "UPDATE paperbag SET `cateId`=" + tocateId + " WHERE `id`=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    public int modifyPaperBagCateId(int tocateId, int fromcateId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "UPDATE paperbag SET `cateId`=" + tocateId + " WHERE `cateId`=" + fromcateId;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    // 改上架下架状态，不带时间，为下架
    public int modifyStateById(String id, int a)
    {
        conn = MySqlUtil.getConnection();
        String sql = "update paperbag set state=" + a + " where id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    // 改上架下架状态，带时间，为上架
    public int modifyStateById(String id, int a, Date dd)
    {
        SimpleDateFormat forDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = forDateTime.format(dd);
        conn = MySqlUtil.getConnection();
        String sql = "update paperbag set state=" + a + ",uploadTime='" + datetime + "' where id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    /**
     * 更改编译状态 重载方法
     * 
     * @param id
     * @param a
     * @return
     */
    public int modifyInitById(String id, String a)
    {
        conn = MySqlUtil.getConnection();
        String sql = "update paperbag set initial=" + a + " where id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    // 根据对象改
    public int modifyById(String id, PaperBag pg)
    {
        conn = MySqlUtil.getConnection();
        String sql =
            "update paperbag set bagName='" + pg.getBagName() + "',state='" + pg.getState() + "',version='"
                + pg.getVersion() + "',summary='" + pg.getSummary() + "',sver='" + pg.getSver() + "',unionId='"
                + pg.getUnionId() + "',tagId='" + pg.getTagId() + "' where id=" + id;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    public int modifyTagIdById(String pgid, String pgTagId, int cateId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "update paperbag set tagId='" + pgTagId + "',cateId='" + cateId + "' where id=" + pgid;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    public int modifyTagIdById(String pgid, String pgTagId)
    {
        conn = MySqlUtil.getConnection();
        String sql = "update paperbag set tagId='" + pgTagId + "' where id=" + pgid;
        return MySqlUtil.executeSQL(conn, sql);
    }
    
    // add
    public int addPaperBag(PaperBag pg)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            try
            {
                String sql =
                    "INSERT INTO `paperbag` (`id`,`bagName`,`uploadTime`,`state`,`state_rec`,`version`,`sver`,`summary`,`initial`,`cover`,`num`,`cateId`,`tagId`,`unionId`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                SimpleDateFormat forDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datetime = forDateTime.format(pg.getUploadTime());
                String[] parm =
                    {String.valueOf(pg.getId()), pg.getBagName(), datetime, pg.getState(), pg.getState_rec(),
                        pg.getVersion(), pg.getSver(), pg.getSummary(), pg.getInitial(), pg.getCover(),
                        String.valueOf(pg.getNum()), String.valueOf(pg.getCateId()), pg.getTagId(), pg.getUnionId()};
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
    
    // 获得最大ID
    public int findMaxId()
    {
        int count = 0;
        String sql = "SELECT max(id) FROM paperbag";
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
    
    // 根据id找包
    public PaperBag getPaperBagById(String id)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null && null != id)
        {
            OperateId OPID = new OperateId();
            PaperBag pg = null;
            String sql = "select * from paperbag limit 1 ";
            if (12 == id.length())
            {
                String union8 = OPID.ParseIdAndChangeWorldId(id).getId();
                String union6 = union8.substring(0, 6);
                String union2 = union8.substring(6, 8);
                sql = "select * from paperbag where unionId like '" + union6 + "__" + union2 + "__" + "'";
            }
            else
            {
                sql = "select * from paperbag where id=" + id;
            }
            try
            {
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                String pgname = "";
                while (rs.next())
                {
                    pg = new PaperBag();
                    pg.setId(rs.getInt("id"));
                    pgname = rs.getString("bagName");
                    if (pgname.contains(" "))
                    {
                        pgname = pgname.replace(" ", "_");
                    }
                    pg.setBagName(pgname);
                    pg.setUploadTime(rs.getTimestamp("uploadTime"));
                    pg.setState(rs.getString("state"));
                    pg.setState_rec(rs.getString("state_rec"));
                    pg.setVersion(rs.getString("version"));
                    pg.setSver(rs.getString("sver"));
                    pg.setSummary(rs.getString("summary"));
                    pg.setInitial(rs.getString("initial"));
                    pg.setCover(rs.getString("cover"));
                    pg.setNum(rs.getInt("num"));
                    pg.setRecTime(rs.getTimestamp("recTime"));
                    pg.setCateId(rs.getInt("cateId"));
                    pg.setTagId(rs.getString("tagId"));
                    pg.setUnionId(rs.getString("unionId"));
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
            return pg;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 添加到编译队列 重载方法
     */
    public void deleteCompileQueue(String bgid)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            try
            {
                // 插入注册信息的SQL语句(使用?占位符)
                String sql = "delete from compilelist where bagId=" + bgid;
                // 创建PreparedStatement对象
                PreparedStatement ps = conn.prepareStatement(sql);
                // 执行更新操作
                ps.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                MySqlUtil.closeAll(conn, ps, rs);
            }
        }
    }
    
    // 获得所有包
    public List<PaperBag> getAllPaperBag()
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "select * from paperbag order by uploadTime desc";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    // 获得所有包（当前页，每页显示多少条）
    public List<PaperBag> getAllPaperBag(int cpage, int cnum)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                int rowBegin = 0;
                rowBegin = cnum * (cpage - 1);
                String sql = "select * from paperbag order by uploadTime desc limit " + rowBegin + "," + cnum;
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    // 获得总量
    public int findbagCount()
    {
        int count = 0;
        String sql = "select count(*) from paperbag";
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
    
    // 获得上架量
    public int findstandCount()
    {
        int count = 0;
        String sql = "select count(*) from paperbag where state=1";
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
    
    // 获得推荐量
    public int findRecCount()
    {
        int count = 0;
        String sql = "select count(*) from paperbag where state=1 and state_rec='取消推荐'";
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
    
    public int lastPage_cate(int cateId)
    {
        double pageTemp = findStandCateCount(cateId);
        int cei = (int)Math.ceil(pageTemp / 15);
        return cei;
    }
    
    public int lastPage_cate_total(int subtotal)
    {
        double pageTemp = subtotal;
        int cei = (int)Math.ceil(pageTemp / 15);
        return cei;
    }
    
    // 获得上架且分类数量，专为lastPage_cate根据类别获得书籍，绑定页数。
    public int findStandCateCount(int cateId)
    {
        int count = 0;
        String sql = "select count(*) from paperbag where state='1' and cateId = " + cateId;
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
    
    // 专为新书上架 提供的总页数函数
    public int lastPage()
    {
        double pageTemp = findstandCount();
        /*
         * int cei=0; if (pageTemp>5) { double abs=Math.abs(pageTemp-5); cei=(int)Math.ceil(abs/20); }else { cei=0; }
         */
        int cei = 0;
        double abs = Math.abs(pageTemp);
        cei = (int)Math.ceil(abs / 15);
        return cei;
    }
    
    // 专为gethotaction 提供的总页数函数
    public int lastPage_hot()
    {
        double pageTemp = findHotPaperCount();
        /*
         * int cei=0; if (pageTemp>5) { double abs=Math.abs(pageTemp-5); cei=(int)Math.ceil(abs/20); }else { cei=0; }
         */
        int cei = 0;
        double abs = Math.abs(pageTemp);
        cei = (int)Math.ceil(abs / 15);
        return cei;
    }
    
    // 专为gethotaction 提供的总页数函数
    public int findHotPaperCount()
    {
        int count = 0;
        String sql =
            "select count(distinct id) from paperbag join mart on paperbag.id=mart.paperId and paperbag.state=1";
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
    
    public int lastPage_rec()
    {
        double pageTemp = findRecCount();
        /*
         * int cei=0; if (pageTemp>5) { double abs=Math.abs(pageTemp-5); cei=(int)Math.ceil(abs/20); }else { cei=0; }
         */
        int cei = 0;
        double abs = Math.abs(pageTemp);
        cei = (int)Math.ceil(abs / 15);
        return cei;
    }
    
    public int lastPage_tag(int tagId)
    {
        double pageTemp = findStandTagCount(tagId);
        int cei = (int)Math.ceil(pageTemp / 20);
        return cei;
    }
    
    public int findStandTagCount(int tagId)
    {
        int count = 0;
        String sql =
            "select count(*) from paperbag where state='1' and tagId like '" + tagId + ",%' or tagId like '%," + tagId
                + ",%' or tagId like '%," + tagId + "' or tagId like '" + tagId + "'";
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
    
    public List<PaperBag> getlastPaper(int cp, int num, int pageSize)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "";
                int rowBegin = 0;
                rowBegin = pageSize * (cp - 1);
                if (cp < 0)
                {
                    sql = "select * from paperbag where state=1 order by uploadTime desc";
                }
                else if (cp == 0)
                {
                    sql = "select * from paperbag where state=1 order by uploadTime desc limit 0," + num;// limit 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql = "select * from paperbag where state=1 order by uploadTime desc limit " + rowBegin + "," + num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    public List<PaperBag> getlastPaper(int cp, int num)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "";
                int rowBegin = 0;
                rowBegin = num * (cp - 1);
                if (cp < 0)
                {
                    sql = "select * from paperbag where state=1 order by uploadTime desc";
                }
                else if (cp == 0)
                {
                    sql = "select * from paperbag where state=1 order by uploadTime desc limit 0," + num;// limit 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql = "select * from paperbag where state=1 order by uploadTime desc limit " + rowBegin + "," + num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    public List<PaperBag> getTopPaper(int cp, int num, int pageSize)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "";
                int rowBegin = 0;
                rowBegin = pageSize * (cp - 1);
                if (cp < 0)
                {
                    sql =
                        "select paperbag.* from paperbag,mart where paperbag.id=mart.paperId and paperbag.state=1 group by paperbag.bagName order by sum(mart.num) desc";
                }
                else if (cp == 0)
                {
                    sql =
                        "select paperbag.* from paperbag,mart where paperbag.id=mart.paperId and paperbag.state=1 group by paperbag.bagName order by sum(mart.num) desc limit 0,"
                            + num;// limit
                    // 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql =
                        "select paperbag.* from paperbag,mart where paperbag.id=mart.paperId and paperbag.state=1 group by paperbag.bagName order by sum(mart.num) desc limit "
                            + rowBegin + "," + num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    public List<PaperBag> getTopPaper(int cp, int num)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "";
                int rowBegin = 0;
                rowBegin = num * (cp - 1);
                if (cp < 0)
                {
                    sql =
                        "select paperbag.* from paperbag,mart where paperbag.id=mart.paperId and paperbag.state=1 group by paperbag.bagName order by sum(mart.num) desc";
                }
                else if (cp == 0)
                {
                    sql =
                        "select paperbag.* from paperbag,mart where paperbag.id=mart.paperId and paperbag.state=1 group by paperbag.bagName order by sum(mart.num) desc limit 0,"
                            + num;// limit
                    // 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql =
                        "select paperbag.* from paperbag,mart where paperbag.id=mart.paperId and paperbag.state=1 group by paperbag.bagName order by sum(mart.num) desc limit "
                            + rowBegin + "," + num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    // 获得所有包（当前页，每页显示多少条）
    public List<PaperBag> getAllPaperBag(int cpage, int cnum, int pageSize)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                int rowBegin = 0;
                rowBegin = pageSize * (cpage - 1);
                String sql = "select * from paperbag order by uploadTime desc limit " + rowBegin + "," + cnum;
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    public List<PaperBag> getJoinPaper(int cp, int num, int pageSize)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "";
                int rowBegin = 0;
                rowBegin = pageSize * (cp - 1);
                if (cp < 0)
                {
                    sql = "select * from paperbag where state=1 and state_rec='取消推荐' order by recTime desc";
                }
                else if (cp == 0)
                {
                    sql =
                        "select * from paperbag where state=1 and state_rec='取消推荐' order by recTime desc limit 0,"
                            + num;// limit
                    // 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql =
                        "select * from paperbag where state=1 and state_rec='取消推荐' order by recTime desc limit "
                            + rowBegin + "," + num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    public List<PaperBag> getJoinPaper(int cp, int num)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "";
                int rowBegin = 0;
                rowBegin = num * (cp - 1);
                if (cp < 0)
                {
                    sql = "select * from paperbag where state=1 and state_rec='取消推荐' order by recTime desc";
                }
                else if (cp == 0)
                {
                    sql =
                        "select * from paperbag where state=1 and state_rec='取消推荐' order by recTime desc limit 0,"
                            + num;// limit
                    // 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql =
                        "select * from paperbag where state=1 and state_rec='取消推荐' order by recTime desc limit "
                            + rowBegin + "," + num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    public List<PaperBag_simple> getPaperBag_simpleByCateId(int cateId)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "select * from paperbag where cateId=" + cateId + " order by uploadTime desc";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getSimplePaperBagList(rs);
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
    
    public List<PaperBag_simple> getPaperBag_simpleByCateIdAndpng(int cateId)
    {
        // File file = new
        // File(CONSTANTS.SERVPATH+"java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/GetBookObject.log");
        // WriteLog wl = new WriteLog(file);
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql =
                    "select * from paperbag where cateId=" + cateId + " and state=1 order by uploadTime desc limit 1";
                // wl.writeTxt("sql="+sql);
                rs = MySqlUtil.getResultSet(conn, sql);
                return getSimplePaperBagListAndpng(rs);
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
    
    public List<PaperBag_simple> getPaperBag_simpleNoCateId()
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "select * from paperbag where cateId=0 order by uploadTime desc";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getSimplePaperBagList(rs);
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
    
    // ���TAGID���book
    public List<PaperBag> getPaperBagByTagId_cutsum(int cp, int tagId)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                
                String sql = "";
                int rowBegin = 0;
                if (cp <= 0)
                {
                    sql =
                        "select bk.* from paperbag bk where bk.state='1' and tagId like '" + tagId
                            + ",%' or tagId like '%," + tagId + ",%' or tagId like '%," + tagId + "' or tagId like '"
                            + tagId + "' order by uploadTime desc";
                }
                else
                {
                    rowBegin = 20 * (cp - 1);
                    sql =
                        "select bk.* from paperbag bk where bk.state='1' and tagId like '" + tagId
                            + ",%' or tagId like '%," + tagId + ",%' or tagId like '%," + tagId + "' or tagId like '"
                            + tagId + "' order by uploadTime desc limit " + rowBegin + ",20";
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    // ���TAGID�����ID���book
    public List<PaperBag_simple> getPaperBag_simpleByTagId(int cateId, int tagId, int limit)
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
                    sql =
                        "select bk.* from paperbag bk " + "where bk.cateId = " + cateId + " and (" + "tagId like '"
                            + tagId + ",%' or tagId like '%," + tagId + ",%' or tagId like '%," + tagId
                            + "' or tagId like '" + tagId + "') order by uploadTime desc";
                }
                else
                {
                    sql =
                        "select bk.* from paperbag bk where bk.cateId = " + cateId + " and (tagId like '" + tagId
                            + ",%' or tagId like '%," + tagId + ",%' or tagId like '%," + tagId + "' or tagId like '"
                            + tagId + "') order by uploadTime desc limit " + limit;
                }
                
                rs = MySqlUtil.getResultSet(conn, sql);
                return getSimplePaperBagList(rs);
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
    
    // ���TAGID�����ID���book
    public List<PaperBag_simple> getPaperBag_simpleByTagIdAndpng(int cateId, int tagId, int limit)
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
                    sql =
                        "select bk.* from paperbag bk " + "where bk.cateId = " + cateId + " and (" + "tagId like '"
                            + tagId + ",%' or tagId like '%," + tagId + ",%' or tagId like '%," + tagId
                            + "' or tagId like '" + tagId + "') order by uploadTime desc";
                }
                else
                {
                    sql =
                        "select bk.* from paperbag bk where bk.cateId = " + cateId + " and (tagId like '" + tagId
                            + ",%' or tagId like '%," + tagId + ",%' or tagId like '%," + tagId + "' or tagId like '"
                            + tagId + "') order by uploadTime desc limit " + limit;
                }
                
                rs = MySqlUtil.getResultSet(conn, sql);
                return getSimplePaperBagListAndpng(rs);
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
    
    public List<PaperBag_simple> getPaperBag_simpleByTagId(int tagId)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql =
                    "select bk.* from paperbag bk " + "where tagId like '" + tagId + ",%' or tagId like '%," + tagId
                        + ",%' or tagId like '%," + tagId + "' or tagId like '" + tagId + "' order by uploadTime desc";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getSimplePaperBagList(rs);
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
    
    private List<PaperBag_simple> getSimplePaperBagList(ResultSet rs)
        throws SQLException
    {
        if (rs != null)
        {
            List<PaperBag_simple> simplebook = new ArrayList<PaperBag_simple>();
            while (rs.next())
            {
                PaperBag_simple bookInfo = new PaperBag_simple();
                bookInfo.setId(rs.getInt("id"));
                bookInfo.setBagName(rs.getString("bagName"));
                
                simplebook.add(bookInfo);
            }
            return simplebook;
        }
        else
        {
            return null;
        }
    }
    
    private List<PaperBag_simple> getSimplePaperBagListAndpng(ResultSet rs)
        throws SQLException
    {
        if (rs != null)
        {
            List<PaperBag_simple> simplebook = new ArrayList<PaperBag_simple>();
            while (rs.next())
            {
                PaperBag_simple bookInfo = new PaperBag_simple();
                bookInfo.setId(rs.getInt("id"));
                bookInfo.setBagName(rs.getString("bagName"));
                bookInfo.setCover(rs.getString("cover"));
                
                simplebook.add(bookInfo);
            }
            return simplebook;
        }
        else
        {
            return null;
        }
    }
    
    public List<PaperBag> getPaperBagByTagId(int tagId)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql =
                    "select bk.* from paperbag bk " + "where tagId like '" + tagId + ",%' or tagId like '%," + tagId
                        + ",%' or tagId like '%," + tagId + "' or tagId like '" + tagId + "' order by uploadTime desc";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    public List<PaperBag> getPaperBagByCateId_cutsum(int cp, int cateId)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "";
                int rowBegin = 0;
                if (cp <= 0)
                {
                    sql = "select * from paperbag where cateId=" + cateId + " order by uploadTime desc";
                }
                else
                {
                    rowBegin = 15 * (cp - 1);
                    sql =
                        "select * from paperbag where cateId=" + cateId + " order by uploadTime desc limit " + rowBegin
                            + ",15";
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    public List<PaperBag_simple> getPaperBag_simpleByCateIdAndpng(int cp, int cateId)
    {
        // File file = new
        // File(CONSTANTS.SERVPATH+"java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/GetBookObject.log");
        // WriteLog wl = new WriteLog(file);
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "";
                int rowBegin = 0;
                if (cp <= 0)
                {
                    sql = "select * from paperbag where cateId=" + cateId + " and state=1 order by uploadTime desc";
                }
                else
                {
                    rowBegin = 15 * (cp - 1);
                    sql =
                        "select * from paperbag where cateId=" + cateId
                            + " and state=1 order by uploadTime desc limit " + rowBegin + ",15";
                }
                // wl.writeTxt("sql="+sql);
                rs = MySqlUtil.getResultSet(conn, sql);
                return getSimplePaperBagListAndpng(rs);
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
    
    public int getCountPaperBycateId(int cateId)
    {
        int count = 0;
        String sql = "SELECT count(*) FROM paperbag where cateId=" + cateId + " and state=1";
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
    
    public PaperBag_simple getPaperBag_simpleByName(String name)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            PaperBag_simple bookInfo = null;
            try
            {
                String sql = "select * from paperbag where bagName='" + name + "'";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    bookInfo = new PaperBag_simple();
                    bookInfo.setId(rs.getInt("id"));
                    bookInfo.setBagName(rs.getString("bagName"));
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
            return bookInfo;
        }
        else
        {
            return null;
        }
    }
    
    public List<PaperBag> searchBook(String keyword)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "SELECT * FROM paperbag WHERE bagName REGEXP '" + keyword + "' and state='1'";
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
    
    private List<PaperBag> getBagList(ResultSet rs)
        throws SQLException, ParseException
    {
        if (rs != null)
        {
            List<PaperBag> pgList = new ArrayList<PaperBag>();
            while (rs.next())
            {
                PaperBag pg = new PaperBag();
                pg.setId(rs.getInt("id"));
                pg.setBagName(rs.getString("bagName"));
                pg.setUploadTime(rs.getTimestamp("uploadTime"));
                pg.setState(rs.getString("state"));
                pg.setState_rec(rs.getString("state_rec"));
                pg.setVersion(rs.getString("version"));
                pg.setSver(rs.getString("sver"));
                pg.setSummary(rs.getString("summary"));
                pg.setInitial(rs.getString("initial"));
                pg.setCover(rs.getString("cover"));
                pg.setNum(rs.getInt("num"));
                pg.setRecTime(rs.getTimestamp("recTime"));
                pg.setCateId(rs.getInt("cateId"));
                pg.setTagId(rs.getString("tagId"));
                pg.setUnionId(rs.getString("unionId"));
                pgList.add(pg);
            }
            return pgList;
        }
        
        else
        {
            return null;
        }
    }
    
    public List<CompileQueue> getCompileQueueList()
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            List<CompileQueue> compileQueueList = new ArrayList<CompileQueue>();
            try
            {
                String sql = "select * from compilelist";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    CompileQueue compileQueue = new CompileQueue();
                    compileQueue.setBgName(rs.getString("bagName"));
                    compileQueue.setBgId(rs.getInt("bagId"));
                    compileQueueList.add(compileQueue);
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
            return compileQueueList;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 添加到编译队列 重载方法
     */
    public void addCompileQueue(String name, int id)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null)
        {
            try
            {
                // 插入注册信息的SQL语句(使用?占位符)
                String sql = "insert into compilelist(bagId,bagName) " + "values(?,?)";
                // 创建PreparedStatement对象
                PreparedStatement ps = conn.prepareStatement(sql);
                // 对SQL语句中的参数动态赋值
                ps.setInt(1, id);
                ps.setString(2, name);
                // 执行更新操作
                ps.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                MySqlUtil.closeAll(conn, ps, rs);
            }
        }
    }
    
    /**
     * 获取所有书本的名字列表
     * 
     * @return
     */
    public List<String> getAllPageNameList()
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = "select * from paperbag order by uploadTime desc";
                rs = MySqlUtil.getResultSet(conn, sql);
                if (rs != null)
                {
                    List<String> pgNameList = new ArrayList<String>();
                    while (rs.next())
                    {
                        pgNameList.add(rs.getString("bagName"));
                    }
                    return pgNameList;
                }
                
                else
                {
                    return null;
                }
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
    
    // 根据id找包
    public PaperBag getPaperBagByName(String name)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null && null != name)
        {
            String sql = "select * from paperbag where bagname=" + "'" + name + "'";
            PaperBag pg = null;
            
            try
            {
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                String pgname = "";
                while (rs.next())
                {
                    pg = new PaperBag();
                    pg.setId(rs.getInt("id"));
                    pgname = rs.getString("bagName");
                    if (pgname.contains(" "))
                    {
                        pgname = pgname.replace(" ", "_");
                    }
                    pg.setBagName(pgname);
                    pg.setUploadTime(rs.getTimestamp("uploadTime"));
                    pg.setState(rs.getString("state"));
                    pg.setState_rec(rs.getString("state_rec"));
                    pg.setVersion(rs.getString("version"));
                    pg.setSver(rs.getString("sver"));
                    pg.setSummary(rs.getString("summary"));
                    pg.setInitial(rs.getString("initial"));
                    pg.setCover(rs.getString("cover"));
                    pg.setNum(rs.getInt("num"));
                    pg.setRecTime(rs.getTimestamp("recTime"));
                    pg.setCateId(rs.getInt("cateId"));
                    pg.setTagId(rs.getString("tagId"));
                    pg.setUnionId(rs.getString("unionId"));
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
            return pg;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 获取编译队列里面的最大ID值 重载方法
     * 
     * @return
     */
    public int findCompileMaxId()
    {
        int count = -1;
        String sql = "SELECT max(bagId) FROM compilelist";
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
    
    public Boolean wallPaperExistOrNot(String id)
    {
        conn = MySqlUtil.getConnection();
        if (conn != null && null != id)
        {
            OperateId OPID = new OperateId();
            String sql = "select * from paperbag limit 1 ";
            if (12 == id.length())
            {
                String union8 = OPID.ParseIdAndChangeWorldId(id).getId();
                String union6 = union8.substring(0, 6);
                String union2 = union8.substring(6, 8);
                sql = "select * from paperbag where unionId like '" + union6 + "__" + union2 + "__" + "'";
            }
            else
            {
                sql = "select * from paperbag where id=" + id;
            }
            try
            {
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                return rs.next();
            }
            catch (Exception e)
            {
                e.printStackTrace();
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
        return false;
    }
    
    /**
     * 获取壁纸列表
     */
    public List<PaperBag> getPaperBagByInCategoryPaperIdList(String slistCondition)
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
                    sql = "select * from paperbag";
                }
                
                else
                {
                    sql = "select * from paperbag where id in (" + slistCondition + ")";
                }
                
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
     * 获取壁纸列表(排除条件)
     */
    public List<PaperBag> getPaperBagByNotInCategoryPaperIdList(String slistCondition)
    {
        conn = MySqlUtil.getConnection();
        rs = null;
        if (conn != null)
        {
            try
            {
                String sql = null;
                if (slistCondition == null || slistCondition.equals(""))
                {
                    sql = "select * from paperbag";
                }
                
                else
                {
                    sql = "select * from paperbag where id not in (" + slistCondition + ")";
                }
                
                rs = MySqlUtil.getResultSet(conn, sql);
                return getBagList(rs);
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
