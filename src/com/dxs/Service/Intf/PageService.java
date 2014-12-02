package com.dxs.Service.Intf;

public interface PageService {
/*	public abstract int findRetCount();
	public abstract int lastPage();*/
	public abstract int getPageNum();
	public abstract int getbagCount();
	public abstract int getlastPage();
	public abstract int modifyCnum(String cnum);
}
