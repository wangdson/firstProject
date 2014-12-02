package com.dxs.Action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Service.Impl.ShortLinkServiceImpl;
import com.dxs.Service.Intf.ShortLinkService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 短连接补齐action
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-20]
 */
public class ShortFresh extends ActionSupport implements ServletRequestAware
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -541451590039566439L;
    
    private HttpServletRequest request;
    
    String[] boxs;
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    public String execute()
        throws Exception
    {
        ShortLinkService shortServ = new ShortLinkServiceImpl();
        
        boxs = request.getParameterValues("ck");
        if (boxs != null && boxs.length != 0)
        {
            for (int i = 0; i < boxs.length; i++)
            {  
                //添加到补齐短链接列表
                shortServ.insertShortLinkComplement(boxs[i]);
            }
        }
        return "PaperbagAction";
    }
}
