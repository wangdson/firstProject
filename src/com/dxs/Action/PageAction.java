package com.dxs.Action;

import com.dxs.Service.Impl.PageServiceImpl;
import com.dxs.Service.Intf.PageService;
import com.opensymphony.xwork2.ActionSupport;

public class PageAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pagecnum;//��paperbag.jspҳ����ÿҳ��ʾ����
	public String getPagecnum() {
		return pagecnum;
	}
	public void setPagecnum(String pagecnum) {
		this.pagecnum = pagecnum;
	}
	private String tonum;//��paperbag.jspҳ���������ڼ�ҳ
	
	public String getTonum() {
		return tonum;
	}
	public void setTonum(String tonum) {
		this.tonum = tonum;
	}
	
	
	public String execute() throws Exception {
		
		//�����ݿ�ÿҳ��ʾ����
		PageService pageServ=new PageServiceImpl();
		int cnum=pageServ.getPageNum();
		
		if (!String.valueOf(cnum).equals(getPagecnum())) {
			pageServ.modifyCnum(getPagecnum());
		}
		return "PaperbagAction";
	}
	

	
	
	
	
	

}
