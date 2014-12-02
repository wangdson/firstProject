package com.dxs.Action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Util.CONSTANTS;
import com.dxs.Util.OperateId;
import com.opensymphony.xwork2.ActionSupport;

public class UnionIdFresh extends ActionSupport implements ServletRequestAware{
	final Log log = LogFactory.getLog(UnionIdFresh.class);
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	String boxs[];
	PaperBagService pgServ=new PaperBagServiceImpl();
	
	public String execute() throws Exception {
		
		boxs=request.getParameterValues("ck");
		if (boxs!=null && boxs.length!=0) {
			for (int i = 0; i < boxs.length; i++) {
				log.info("UnionIdFresh------------boxsId="+boxs[i]);
				PaperBag pg=pgServ.getPaperBagById(boxs[i]);
				log.info("根据ID获得paperbag,名字是="+pg.getBagName());
				OperateId OPID = new OperateId();
				String unid=OPID.GenIdAndChangeWorldId(OPID.RadomGenID(), CONSTANTS.CHANGEID);
				log.info("生成转世号="+unid);
				pg.setUnionId(unid);
				int result=pgServ.modifyById(boxs[i], pg);
				log.info("修改更新转世号结果result="+result);
				
			}
		}
		return "PaperbagAction";
	}
	
	

}
