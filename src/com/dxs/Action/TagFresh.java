package com.dxs.Action;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionSupport;

public class TagFresh extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	String boxs[];
	TagService tagServ=new TagServiceImpl();
	PaperBagService pgServ=new PaperBagServiceImpl();
	
	//File file = new File(CONSTANTS.SERVPATH+"java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/logtxt.log");
	public String execute() throws Exception {
/*		if(file.exists()){   
			try {   
				file.delete();  
				file.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		WriteLog wl = new WriteLog(file);*/
		boxs=request.getParameterValues("ck");
		if (boxs!=null && boxs.length!=0) {
			for (int i = 0; i < boxs.length; i++) {
				//wl.writeTxt("��Ҫ���ͼƬ��ID="+boxs[i]);
				PaperBag pgxx=pgServ.getPaperBagById(boxs[i]);
				int selftagid=addselftag(pgxx.getBagName());
				//wl.writeTxt("ˢ�±�ǩ������Լ���ǩID="+selftagid+"Ҫ���ĵ�ͼƬ��id="+boxs[i]);
				pgServ.modifyTagIdById(boxs[i], String.valueOf(selftagid));
			}
		}
		return "PaperbagAction";
	}
	
	
	public int addselftag(String name)
	{
		int newid=0;
		Tag tn=tagServ.searchTagByName(name);
		if (tn==null) {
			int maxid=tagServ.findMaxTagId();//���������ID
			newid=maxid+1;
			Tag tg=new Tag();
			tg.setTagId(newid);
			tg.setTagName(name);
			tg.setCategoryId("0");
			tg.setHotNum(0);
			tg.setCount(0);
			tg.setFlag(0);//0���� 1 �ͻ�
			tagServ.addTag(tg);//add��𷽷�
		}else {
			newid=tn.getTagId();
		}
		return newid;
	}
	
	
	
	

}
