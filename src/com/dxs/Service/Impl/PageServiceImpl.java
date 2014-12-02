package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dxs.Service.Intf.PageService;
import com.dxs.Util.MySqlUtil;

public class PageServiceImpl implements PageService{
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
/*	public int findRetCount()
	{
		int count=0;
		String sql="select count(*) from retrieval";
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
	public int lastPage()
	{
		double pageTemp=findRetCount();
		int lastp=(int)Math.ceil(pageTemp)/10;
		return lastp;
	}*/
	
	public int getPageNum()//获得每页显示条数
	{
		int count=0;
		String sql="select pageNum from page where id=1";
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
	//改条数
	public int modifyCnum(String cnum) {
		conn = MySqlUtil.getConnection();
		String sql = "update page set pageNum='"+cnum+"' where id=1";
		return MySqlUtil.executeSQL(conn, sql);
	}
	//获得包包总量
	public int getbagCount()
	{
		int count=0;
		String sql="select count(*) from paperbag";
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
	//获得总页码
	public int getlastPage()
	{
		double bagTotal=getbagCount();
		int pagenum=getPageNum();
		int lastp=0;
		if (pagenum==0) {
			lastp=(int)Math.ceil(bagTotal/bagTotal);//总数/每页显示数
		}else {
			lastp=(int)Math.ceil(bagTotal/pagenum);//总数/每页显示数
		}
		return lastp;
	}
	
	

}
