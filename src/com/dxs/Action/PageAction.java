package com.dxs.Action;

import com.dxs.Service.Impl.PageServiceImpl;
import com.dxs.Service.Intf.PageService;
import com.opensymphony.xwork2.ActionSupport;

public class PageAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pagecnum;//从paperbag.jsp页面获得每页显示条数
	public String getPagecnum() {
		return pagecnum;
	}
	public void setPagecnum(String pagecnum) {
		this.pagecnum = pagecnum;
	}
	private String tonum;//从paperbag.jsp页面获得跳至第几页
	
	public String getTonum() {
		return tonum;
	}
	public void setTonum(String tonum) {
		this.tonum = tonum;
	}
	
	
	public String execute() throws Exception {
		
		//设数据库每页显示条数
		PageService pageServ=new PageServiceImpl();
		int cnum=pageServ.getPageNum();
		
		if (!String.valueOf(cnum).equals(getPagecnum())) {
			pageServ.modifyCnum(getPagecnum());
		}
		return "PaperbagAction";
	}
	

	
	
	
	
	

}
