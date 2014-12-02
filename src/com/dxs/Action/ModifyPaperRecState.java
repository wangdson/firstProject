package com.dxs.Action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionSupport;

public class ModifyPaperRecState extends ActionSupport implements ServletRequestAware
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private HttpServletRequest request;
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    private String sta;
    
    private String id;
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setSta(String sta)
    {
        this.sta = sta;
    }
    
    public String getSta()
    {
        return sta;
    }
    
    PaperBagService pgServ = new PaperBagServiceImpl();
    
    String[] boxs;
    
    // 单本超链接
    public String execute()
        throws Exception
    {
        System.out.println(getSta());// 0：未推荐=加推荐。1:已推荐=取消推荐
        if (getSta().equals("0"))
        {
            pgServ.modifyRecStateById(getId(), "取消推荐", new Date());
        }
        if (getSta().equals("1"))
        {
            pgServ.modifyRecStateById(getId(), "加推荐");
        }
        /*
         * //2是booksView_rec.jsp过来的请求。 if (getSta().equals("2")) { pgServ.modifyStateById(getIsbn(), "取消推荐",new Date());
         * return "booksView_rec"; }
         */
        return "paperbagViewAction";
    }
    
    // checkbox 批量加推荐
    public String ckExeJoin()
        throws Exception
    {
        boxs = request.getParameterValues("ck");
        if (boxs != null && boxs.length != 0)
        {
            for (int i = 0; i < boxs.length; i++)
            {
                pgServ.modifyRecStateById(boxs[i], "取消推荐", new Date());
            }
        }
        
        return "paperbagViewAction";
    }
    
    // checkbox 批量取消推荐
    public String ckExeCancel()
        throws Exception
    {
        boxs = request.getParameterValues("ck");
        if (boxs != null && boxs.length != 0)
        {
            for (int i = 0; i < boxs.length; i++)
            {
                pgServ.modifyRecStateById(boxs[i], "加推荐");
            }
        }
        return "paperbagViewAction";
    }
    
}
