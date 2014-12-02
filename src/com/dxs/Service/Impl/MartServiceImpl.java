package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dxs.Entity.Mart;
import com.dxs.Entity.MartResult;
import com.dxs.Service.Intf.MartService;
import com.dxs.Util.MySqlUtil;

public class MartServiceImpl implements MartService{
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	//start---modify
	public int modifyNumById(String paperId,String mid) {
		conn = MySqlUtil.getConnection();
		String sql ="update mart set num=num+1 where paperId='"+paperId+"' and martFlag='"+mid+"'";
		return MySqlUtil.executeSQL(conn, sql);
	}
	//end-----modify
	
	//start---add
	public int addmart(Mart mart) {
		conn = MySqlUtil.getConnection();
		if (conn != null) {
			try {
				String sql = "INSERT INTO `mart` (`paperId`, `martFlag`,`num`) VALUES(?,?,?)";
				String[] parm = {mart.getPaperId(),mart.getMartFlag(),String.valueOf(mart.getNum())};
				return MySqlUtil.executeSQL(conn, sql, parm);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else {
			return -1;
		}
	}
	//end-----add
	
	
	//start---select
	public Mart getMartByPaperId(String paperId,String mid)
	{
		conn = MySqlUtil.getConnection();
		if (conn != null) {
			Mart mar=null;
			try {
				String sql = "select * from mart where paperId='"+paperId+"' and martFlag='"+mid+"'";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					mar = new Mart();
					mar.setPaperId(rs.getString("paperId"));
					mar.setMartFlag(rs.getString("martFlag"));
					mar.setNum(rs.getInt("num"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
			return mar;
		} else {
			return null;
		}
	}
	
	public List<MartResult> getMartResult()
	{
		conn = MySqlUtil.getConnection();
		rs = null;
		if (conn != null) {
			try {
				String sql = "select paperbag.bagName," +
						"sum(case mart.martFlag when 'C00' then mart.num else 0 end) as 'C00'," +
						"sum(case mart.martFlag when 'C01' then mart.num else 0 end) as 'C01'," +
						"sum(case mart.martFlag when 'C02' then mart.num else 0 end) as 'C02'," +
						"sum(case mart.martFlag when 'C03' then mart.num else 0 end) as 'C03'," +
						"sum(case mart.martFlag when 'C04' then mart.num else 0 end) as 'C04'," +
						"sum(case mart.martFlag when 'C05' then mart.num else 0 end) as 'C05'," +
						"sum(mart.num) as total from paperbag,mart " +
						"where paperbag.id=mart.paperId group by paperbag.bagName order by total desc";
				rs = MySqlUtil.getResultSet(conn, sql);
				return getMartList(rs);
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
	
	private List<MartResult> getMartList(ResultSet rs) throws SQLException {
		if (rs != null) {
			List<MartResult> martList = new ArrayList<MartResult>();
			while (rs.next()) {
				MartResult mar = new MartResult();
				mar.setBagName(rs.getString("bagName"));
				mar.setC00(rs.getInt("c00"));
				mar.setC01(rs.getInt("c01"));
				mar.setC02(rs.getInt("c02"));
				mar.setC03(rs.getInt("c03"));
				mar.setC04(rs.getInt("c04"));
				mar.setC05(rs.getInt("c05"));
				mar.setTotal(rs.getInt("total"));
				
				martList.add(mar);
			}
			return martList;
		} else {
			return null;
		}
	}
	
	
	
	
	
	
	//end-----select
	

}
