package com.dxs.Action;

import com.dxs.Entity.ShortLink;

import com.dxs.Service.Impl.ShortLinkServiceImpl;
import com.dxs.Service.Intf.ShortLinkService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class u extends ActionSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8273603357001592401L;
    
    private String key;
    
    public String execute()
        throws Exception
    {
        String k = getKey();
        if (k != null)
        {
            ShortLinkService shortServ = new ShortLinkServiceImpl();
            ShortLink sl = shortServ.getShortLinkByName(k);
            ActionContext.getContext().getSession().put("targetURL", sl.getLongUrl());
        }
        
        return "shortlink";
    }
    
    public String getKey()
    {
        return key;
    }
    
    public void setKey(String key)
    {
        this.key = key;
    }
}
