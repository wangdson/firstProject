package com.dxs.Service.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Users;
import com.dxs.Service.Intf.UsersService;
import com.dxs.Util.MySqlUtil;

public class UsersServiceImpl implements UsersService{
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	// add
	public int addUser(Users user) {
		conn = MySqlUtil.getConnection();
		if (conn != null) {
			try {
				String sql = "INSERT INTO `users` (`userId`, `userName`, `password`,`mail`) VALUES (?,?,?,?)";
				String[] parm = {String.valueOf(user.getUserId()), user.getUserName(),user.getPassword(),user.getMail()};
				return MySqlUtil.executeSQL(conn, sql, parm);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else {
			return -1;
		}
	}
	
	
	//������ID
	public int findMaxId()
	{
		int count=0;
		String sql="SELECT max(userId) FROM users";
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
	
	public boolean checkUserName(String name)
	{
		if (getUserByName(name)==null) {//���ؿ�˵������
			return true;
		}else {
			return false;
		}
	}
	public boolean checkUser(String name,String psw)
	{
		Users user=getUserByName(name);
		if (user!=null && user.getPassword().equals(psw)) {//���ز�Ϊ��˵���û�����		
			return true;
		}else {
			return false;
		}
	}
	
	//���name����
	public Users getUserByName(String name)
	{
		conn = MySqlUtil.getConnection();
		if (conn != null) {
			Users user=null;
			try {
				String sql = "SELECT * FROM users where userName='"+name+"'";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					user = new Users();
					user.setUserId(rs.getInt("userId"));
					user.setUserName(rs.getString("userName"));
					user.setPassword(rs.getString("password"));
					user.setMail(rs.getString("mail"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MySqlUtil.closeAll(conn, ps, rs);
			}
			return user;
		} else {
			return null;
		}
	}

}
