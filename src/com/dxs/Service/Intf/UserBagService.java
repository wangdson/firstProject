package com.dxs.Service.Intf;

import com.dxs.Entity.UserBag;

public interface UserBagService {
	public abstract int findMaxId();
	public abstract int addUserBag(UserBag pg) ;

}
