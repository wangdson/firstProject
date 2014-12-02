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
	
	public int getPageNum()//���ÿҳ��ʾ����
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
	//������
	public int modifyCnum(String cnum) {
		conn = MySqlUtil.getConnection();
		String sql = "update page set pageNum='"+cnum+"' where id=1";
		return MySqlUtil.executeSQL(conn, sql);
	}
	//��ð�������
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
	//�����ҳ��
	public int getlastPage()
	{
		double bagTotal=getbagCount();
		int pagenum=getPageNum();
		int lastp=0;
		if (pagenum==0) {
			lastp=(int)Math.ceil(bagTotal/bagTotal);//����/ÿҳ��ʾ��
		}else {
			lastp=(int)Math.ceil(bagTotal/pagenum);//����/ÿҳ��ʾ��
		}
		return lastp;
	}
	
	

}
