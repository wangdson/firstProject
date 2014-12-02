package com.dxs.Action.Android;

import com.dxs.Entity.Imei;
import com.dxs.Service.Impl.ImeiServiceImpl;
import com.dxs.Service.Intf.ImeiService;
import com.opensymphony.xwork2.ActionSupport;

public class ImeiAction extends ActionSupport{

	/**
	 * 用户量统计
	 */
	private static final long serialVersionUID = 1L;
	private String imei;
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}

	public String execute() throws Exception {
		if(getImei()!=null&&!getImei().equals(""))
		{
			ImeiService imeiServ=new ImeiServiceImpl();
			Imei imi=imeiServ.getImeiById(getImei());
			if (imi==null) {
				//如果为空，插入
				imi=new Imei();
				imi.setImei(getImei());
				imeiServ.addImei(imi);
			}
			return null;
		}else
		{
			return "error";
		}
		
	}
	
	
	

}
