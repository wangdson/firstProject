package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dxs.Entity.PushInfo;
import com.dxs.Service.Intf.PushService;
import com.dxs.Util.MySqlUtil;

/*
 * PushServiceImpl include:
 * getAllPushList()
 * addPush()
 * */
public class PushServiceImpl implements PushService {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// add
	public int addPush(PushInfo pi) {
		conn = MySqlUtil.getConnection();
		if (conn != null) {
			try {
				String sql = "INSERT INTO `push` (`content`, `planTime`, `pushUser`,`state`) VALUES(?,?,?,?)";
				// ���ڿؼ���������ڣ�û��ʱ�䣬����ƴ��ʱ�䣬
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
				String mydate = formatDate.format(pi.getPlanTime());
				String mytime = formatTime.format(new Date());
				String datatime = mydate + " " + mytime;
				String[] parm = { pi.getContent(), datatime, pi.getPushUser(),
						pi.getState() };
				return MySqlUtil.executeSQL(conn, sql, parm);

			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else {
			return -1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dxs.Service.Impl.PushService#getAllPushList()
	 */
	public List<PushInfo> getTodayPush(){
		conn = MySqlUtil.getConnection();
		rs = null;
		SimpleDateFormat forStartTime = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat forEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String starttime = forStartTime.format(new Date());
		String endtime = forEndTime.format(new Date());	
		if (conn != null) {
			try {
				String sql = "SELECT * FROM push where planTime between '"+starttime+"' and '"+endtime+"'"+"order by planTime desc limit 1";
				rs = MySqlUtil.getResultSet(conn, sql);
				return getPushList(rs);
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
	
	
	public List<PushInfo> getAllPushList() {
		conn = MySqlUtil.getConnection();
		rs = null;
		if (conn != null) {
			try {
				String sql = "select * from push";
				rs = MySqlUtil.getResultSet(conn, sql);
				return getPushList(rs);
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

	private List<PushInfo> getPushList(ResultSet rs) throws SQLException {
		if (rs != null) {
			List<PushInfo> pushList = new ArrayList<PushInfo>();
			while (rs.next()) {
				PushInfo pushInfo = new PushInfo();
				pushInfo.setPushId(rs.getInt("pushId"));
				pushInfo.setContent(rs.getString("content"));
				// new java.util.Date(rs.getDate("planTime").getTime())
				pushInfo.setPlanTime(rs.getTimestamp("planTime"));// ��getTimestamp����ȡmysql�е�datetime���Ͳ��ܻ�ȡȫ��
				pushInfo.setPushUser(rs.getString("pushUser"));
				pushInfo.setState(rs.getString("state"));
				pushList.add(pushInfo);
			}
			return pushList;
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
    public List<PushInfo> getlastPushInfo(int cp,int num,int pageSize)
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
                    sql = "select * from push";
                }
                else if (cp == 0)
                {
                    sql = "select * from push limit 0,"+num;// limit 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql = "select * from push limit " + rowBegin + ","+num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getPushList(rs);
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
    public List<PushInfo> getlastPushInfo(int cp,int num)
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
                    sql = "select * from push";
                }
                else if (cp == 0)
                {
                    sql = "select * from push limit 0,"+num;// limit 0,5
                }
                else
                {
                    // rowBegin=rowBegin+5;
                    sql = "select * from push limit " + rowBegin + ","+num;
                }
                rs = MySqlUtil.getResultSet(conn, sql);
                return getPushList(rs);
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
    public int findPushInfoCount()
    {
        int count = 0;
        String sql = "select count(*) from push";
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
