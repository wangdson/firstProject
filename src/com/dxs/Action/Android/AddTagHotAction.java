package com.dxs.Action.Android;

import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionSupport;

public class AddTagHotAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tagId;

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagId() {
		return tagId;
	}

	public String execute() throws Exception {
		if (getTagId()!=null&&getTagId()!="") {
			TagService tagServ=new TagServiceImpl();
			tagServ.updateTagHotById(Integer.parseInt(getTagId().toString()));
		}
		return null;
	}
	
	
	
	
	

}
