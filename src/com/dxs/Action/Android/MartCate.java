package com.dxs.Action.Android;

import com.dxs.Entity.Mart;
import com.dxs.Service.Impl.MartServiceImpl;
import com.dxs.Service.Intf.MartService;
import com.opensymphony.xwork2.ActionSupport;

public class MartCate extends ActionSupport{

	/**
	 * sao哥发来市场字符串，我接收字符串匹配数据库，分别市场量+1
	 */
	private static final long serialVersionUID = 1L;
	
	private String paperId;
	public String getPaperId() {
		return paperId;
	}
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}


	private String mid;
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}

	
	public String execute() throws Exception {
		
		//根据发来的isbn 查一下 存在否，存在 modify，不存在insert
		if (getMid()!=null&&!getMid().equals("")&&getPaperId()!=null&&!getPaperId().equals("")) {
			MartService martServ=new MartServiceImpl();
			Mart mar=martServ.getMartByPaperId(getPaperId(),getMid());
			if (mar!=null) {
				martServ.modifyNumById(getPaperId(), getMid());
			}else{
				mar=new Mart();
				mar.setPaperId(getPaperId());
				mar.setMartFlag(getMid());
				mar.setNum(1);
				martServ.addmart(mar);
			}
		}

		
		
		return null;
	}
	
	
	

}
