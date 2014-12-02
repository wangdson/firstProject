package com.dxs.Action;

import java.util.List;

import com.dxs.Entity.MartResult;
import com.dxs.Service.Impl.ImeiServiceImpl;
import com.dxs.Service.Impl.MartServiceImpl;
import com.dxs.Service.Intf.ImeiService;
import com.dxs.Service.Intf.MartService;
import com.dxs.Util.CheckUser;
import com.opensymphony.xwork2.ActionSupport;

public class MartAnalyse extends ActionSupport{

	/**
	 * 市场安装量界面
	 */
	private static final long serialVersionUID = 1L;
	private List<MartResult> mar;
	private int imei;
	
	
	public int getImei() {
		return imei;
	}
	public void setImei(int imei) {
		this.imei = imei;
	}
	public List<MartResult> getMar() {
		return mar;
	}
	public void setMar(List<MartResult> mar) {
		this.mar = mar;
	}
	



	public String execute() throws Exception {
		
		if(CheckUser.ck_class()){
			MartService martServ=new MartServiceImpl();
			List<MartResult> mar=martServ.getMartResult();
			setMar(mar);
			ImeiService imeiServ=new ImeiServiceImpl();
			int imei = imeiServ.findImeiCount();
			setImei(imei);
			return "mart";
		}else {
			return "login";
		}
		

	}
	
	

}
