package com.dxs.Action;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.dom4j.Document;

import org.dom4j.io.SAXReader;

import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateSver extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	
	public String execute() throws Exception {
		//把sver全部+1
		String[] boxs;
		boxs=request.getParameterValues("ck");
		PaperBagService pgServ=new PaperBagServiceImpl();
		PaperBag bo=new PaperBag();
		
		if (boxs!=null) {
			for (int i = 0; i < boxs.length; i++) {
				bo=pgServ.getPaperBagById(boxs[i]);
				pgServ.modifySverById(boxs[i],String.valueOf(Integer.parseInt(bo.getSver())+1));
			}
		}

		return "paperbagAction";
	}
	//将网页转化为document
	public  Document getRemoteXML(String url)
	{
	    try
	    {
	        URL url1 = new URL(url);
	        SAXReader reader = new SAXReader();
	        return reader.read(url1);
	    }
	    
	    catch(Exception e )
	    {
	        e.printStackTrace();
	    }
	    return null;
	}

	

}
