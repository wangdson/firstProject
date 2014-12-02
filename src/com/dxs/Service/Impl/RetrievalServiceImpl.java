package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.dxs.Entity.Retrieval;
import com.dxs.Service.Intf.RetrievalService;
import com.dxs.Util.MySqlUtil;

public class RetrievalServiceImpl implements RetrievalService {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	//modify
	//�б���Ϊϵͳ���ֻ�ǰ��ʾ������ݼ���ID
	public int modifyCateById(String ret) {
		conn = MySqlUtil.getConnection();
		String sql = "update retrieval set cate='adm' where retrievalId in("+ret+")";
		return MySqlUtil.executeSQL(conn, sql);
	}
	public int modifyNumById(int retId) {
		conn = MySqlUtil.getConnection();
		String sql = "update retrieval set number=number+1 where retrievalId ="+retId;
		return MySqlUtil.executeSQL(conn, sql);
	}
	public int modifyNumById(int retId,int num) {
		conn = MySqlUtil.getConnection();
		String sql = "update retrieval set number="+num+" where retrievalId ="+retId;
		return MySqlUtil.executeSQL(conn, sql);
	}
	
	public int modifyWordCateById(int retId, String word,String cate) {
		conn = MySqlUtil.getConnection();
		String sql = "UPDATE `retrieval` SET `userKeyWord`='"+word+"', `cate`='"+cate+"' WHERE `retrievalId`='"+retId+"'";
		return MySqlUtil.executeSQL(conn, sql);
	}
	
	// add
	public int addRetrieval(Retrieval ret) {
		conn = MySqlUtil.getConnection();
		int row = 0;
		if (conn != null) {
			try {
				String sql = "INSERT INTO retrieval (`retrievalId`,`userKeyWord`,`searchTime`,`number`,`cate`) VALUES(?,?,?,?,?)";
				SimpleDateFormat forDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String datetime = forDateTime.format(ret.getSearchTime());
				String[] parm = {String.valueOf(ret.getRetrievalId()), ret.getUserKeyWord(),datetime,String.valueOf(ret.getNumber()),ret.getCate() };
				row = MySqlUtil.executeSQL(conn, sql, parm);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
			return row;
		} else {
			return -1;
		}
	}
	
	
	//search
	public Retrieval searchRet(String keyword){
		conn = MySqlUtil.getConnection();
		if (conn != null) {
			Retrieval ret = null;
			try {
				String sql = "select * from retrieval where userKeyWord='"+ keyword+"'";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					ret = new Retrieval();
					ret.setRetrievalId(rs.getInt("retrievalId"));
					ret.setUserKeyWord(rs.getString("userKeyWord"));
					ret.setNumber(rs.getInt("number"));
					ret.setSearchTime(rs.getTimestamp("searchTime"));
					ret.setCate(rs.getString("cate"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
			return ret;
		} else {
			return null;
		}
	}
	//������ID
	public int findMaxId()
	{
		int count=0;
		String sql="SELECT max(retrievalId) FROM retrieval";
		try {
			conn = MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if (rs.next()) {
				count=rs.getInt(1);
			}
		} catch (Exception e) {e.printStackTrace();}
		finally{MySqlUtil.closeAll(conn, ps, rs);}
		return count;
	}
	
	
	
/*	public List<Retrieval> getRetJoinRecBybatchId(String batId)
	{
		conn = MySqlUtil.getConnection();
		rs = null;
		if (conn != null) {
			try {
				String sql = "select ret.* from recommend rec join retrieval ret on rec.retrievalId=ret.retrievalId where rec.batchId ="+batId;
				rs = MySqlUtil.getResultSet(conn, sql);
				return getRetrievalList(rs);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
		} else {
			return null;
		}
	}*/
	

	public Retrieval getRetrievalById(int retrievalId) {
		conn = MySqlUtil.getConnection();
		if (conn != null) {
			Retrieval ret = null;
			try {
				String sql = "select * from retrieval where retrievalId="
						+ retrievalId;
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					ret = new Retrieval();
					ret.setRetrievalId(rs.getInt("retrievalId"));
					ret.setUserKeyWord(rs.getString("userKeyWord"));
					ret.setNumber(rs.getInt("number"));
					ret.setSearchTime(rs.getTimestamp("searchTime"));
					ret.setCate(rs.getString("cate"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
			return ret;
		} else {
			return null;
		}
	}

/*	public List<Retrieval> getSimilarityRetrieval(int page) {
		conn = MySqlUtil.getConnection();
		rs = null;
		if (conn != null) {
			try {
				int rowBegin=0;
				rowBegin=10*(page-1);
				String sql = "select * from retrieval where number > 100 order by userKeyWord,cate limit "+rowBegin+",10";
				rs = MySqlUtil.getResultSet(conn, sql);
				return getRetrievalList(rs);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
		} else {
			return null;
		}
	}*/

	public List<Retrieval> getALLRetrieval() {
		conn = MySqlUtil.getConnection();
		rs = null;
		if (conn != null) {
			try {
				String sql = "select * from retrieval order by cate asc,number desc";
				rs = MySqlUtil.getResultSet(conn, sql);
				return getRetrievalList(rs);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
		} else {
			return null;
		}
	}
	public List<Retrieval> getRetrievalByCate(String cate) {
		conn = MySqlUtil.getConnection();
		rs = null;
		if (conn != null) {
			try {
				String sql = "select * from retrieval where cate='"+cate+"' order by number desc";
				rs = MySqlUtil.getResultSet(conn, sql);
				return getRetrievalList(rs);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
		} else {
			return null;
		}
	}

	/*
	 * public List<Retrieval> getRetrievalListSort(String orderByName,String ad)
	 * { Connection conn=MySqlUtil.getConnection(); ResultSet rs=null; if
	 * (conn!=null) { try { if (orderByName.equals("userKeyWord")) { String
	 * sql="select * from retrieval where number > 100 order by userKeyWord "
	 * +orderByName+" "+ad; rs=MySqlUtil.getResultSet(conn, sql); return
	 * getRetrievalList(rs); } if (orderByName.equals("number")) { String
	 * sql="select * from retrieval where number > 100 order by number "
	 * +orderByName+" "+ad; rs=MySqlUtil.getResultSet(conn, sql); return
	 * getRetrievalList(rs); } if (orderByName.equals("searchTime")) { String
	 * sql="select * from retrieval where number > 100 order by searchTime "+
	 * orderByName+" "+ad; rs=MySqlUtil.getResultSet(conn, sql); return
	 * getRetrievalList(rs); } if (orderByName.equals("state")) { String
	 * sql="select * from retrieval where number > 100 order by state "
	 * +orderByName+" "+ad; rs=MySqlUtil.getResultSet(conn, sql); return
	 * getRetrievalList(rs); }
	 * 
	 * 
	 * ������Impl��������***************************************************************
	 * ****
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); return null; }finally{
	 * MySqlUtil.closeAll(conn, null, rs); } }else { return null; } }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dxs.Service.Impl.RetrievalService#getRetrievalListOver100()
	 */
/*	public List<Retrieval> getRetrievalListOver100(int page) {
		conn = MySqlUtil.getConnection();
		rs = null;
		if (conn != null) {
			try {
				int rowBegin=0;
				rowBegin=10*(page-1);
				String sql = "select * from retrieval where number > 0 order by number desc limit "+rowBegin+",10";
				rs = MySqlUtil.getResultSet(conn, sql);
				return getRetrievalList(rs);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
		} else {
			return null;
		}
	}*/

	private List<Retrieval> getRetrievalList(ResultSet rs) throws SQLException {
		if (rs != null) {
			List<Retrieval> retrievalList = new ArrayList<Retrieval>();
			while (rs.next()) {
				Retrieval retrieval = new Retrieval();
				retrieval.setRetrievalId(rs.getInt("retrievalId"));
				retrieval.setUserKeyWord(rs.getString("userKeyWord"));
				retrieval.setSearchTime(rs.getTimestamp("searchTime"));// ��getTimestamp����ȡmysql�е�datetime���Ͳ��ܻ�ȡȫ��
				retrieval.setNumber(rs.getInt("number"));
				retrieval.setCate(rs.getString("cate"));
				retrievalList.add(retrieval);
			}
			return retrievalList;
		} else {
			return null;
		}
	}
	
	/**
     * 分页查询
     * @param cp 页码
     * @param num 每页展示的记录个数
     * @return Retrieval列表
     */
    public List<Retrieval> getlastRetrieval(int cp,int num,int pageSize)
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
                    sql = "select * from retrieval order by cate asc,number desc";
                }
                else if (cp == 0)
                {
                    sql = "select * from retrieval order by cate asc,number desc limit 0,"+num;// limit 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql = "select * from retrieval order by cate asc,number desc limit " + rowBegin + ","+num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getRetrievalList(rs);
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
     * 分页查询
     * @param cp 页码
     * @param num 每页展示的记录个数
     * @return Retrieval列表
     */
    public List<Retrieval> getlastRetrieval(int cp,int num)
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
                    sql = "select * from retrieval order by cate asc,number desc";
                }
                else if (cp == 0)
                {
                    sql = "select * from retrieval order by cate asc,number desc limit 0,"+num;// limit 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql = "select * from retrieval order by cate asc,number desc limit " + rowBegin + ","+num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getRetrievalList(rs);
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
    
    // 获得检索量
    public int findRetrievalCount()
    {
        int count = 0;
        String sql = "select count(*) from retrieval";
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
}
