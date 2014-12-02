package com.dxs.Service.Intf;

import com.dxs.Entity.Imei;

public interface ImeiService {
	public abstract int addImei(Imei imei);
	public abstract int findImeiCount();
	public abstract Imei getImeiById(String imei);

}
