package com.dxs.Entity;

public class Users {
	private int userId;
	private String userName;
	private String password;
	private String mail;
	
	
	
	public Users(int userId, String userName, String password, String mail) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.mail = mail;
	}
	
	
	public Users() {
		super();
	}


	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMail() {
		return mail;
	}
	

}
