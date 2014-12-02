package com.dxs.Service.Intf;

import java.util.List;

import com.dxs.Entity.PushInfo;

public interface PushService {

	public abstract List<PushInfo> getAllPushList();

	public abstract int addPush(PushInfo pi);
	public abstract List<PushInfo> getTodayPush();
	public abstract List<PushInfo> getlastPushInfo(int cp,int num);
	public abstract List<PushInfo> getlastPushInfo(int cp,int num,int pageSize);
	public abstract int findPushInfoCount();

}