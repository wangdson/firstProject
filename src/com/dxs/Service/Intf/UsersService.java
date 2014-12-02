package com.dxs.Service.Intf;

import com.dxs.Entity.Users;

public interface UsersService {
	public abstract int findMaxId();
	public abstract int addUser(Users user) ;
	public abstract Users getUserByName(String name);
	public abstract boolean checkUserName(String name);
	public abstract boolean checkUser(String name,String psw);
	

}
