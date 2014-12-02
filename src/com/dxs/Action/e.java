package com.dxs.Action;

import java.util.Map;

import net.sf.json.JSONObject;

import com.dxs.Entity.ShortLink;
import com.dxs.Entity.Variable;

import com.dxs.Service.Impl.ShortLinkServiceImpl;
import com.dxs.Service.Intf.ShortLinkService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class e extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    private String link;
    
    private String flag;
    
    private String id;
    
    private String qa;
    
    // a:手机下载 b:分享JSON c:从分享的短链接过来下载 d:与c功能相同，但是走urlrewrite.xml配置文件
    public String a()
        throws Exception
    {
        
        if (getId() != null && getFlag() != null)
        {
            
            ShortLinkService shortServ = new ShortLinkServiceImpl();
            // 根据ISBN 获得长连接下载。
            ShortLink sl = shortServ.getShortLinkByName(getId(), Integer.valueOf(getFlag()));
            
            ActionContext.getContext().getSession().put("targetURL", sl.getLongUrl());
        }
        return "shortlink";
    }
    
    // b:分享JSON
    public String b()
        throws Exception
    {
        if (getId() != null && getFlag() != null)
        {
            ShortLinkService shortServ = new ShortLinkServiceImpl();
            // 根据ISBN 获得长连接下载。
            ShortLink sl = shortServ.getShortLinkByName(getId(), Integer.valueOf(getFlag()));
            
            Variable var = new Variable();
            var.setVar(String.valueOf(sl.getShortUrl()));
            JSONObject json = JSONObject.fromObject(var);
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("shortjson", json.toString());
        }
        return "shortjson";
    }
    
    //c:从分享的短链接过来下载
    public String c()
        throws Exception
    {
        
        if (getLink() != null)
        {
            
            ShortLinkService shortServ = new ShortLinkServiceImpl();
            // 根据ISBN 获得长连接下载。
            ShortLink sl = shortServ.getShortLinkByName(getLink());
            
            ActionContext.getContext().getSession().put("targetURL", sl.getLongUrl());
        }
        return "shortlink";
    }
    
    public String d()
        throws Exception
    {
        String k = getQa();
        if (k != null)
        {
            ShortLinkService shortServ = new ShortLinkServiceImpl();
            // 根据ISBN 获得长连接下载。
            ShortLink sl = shortServ.getShortLinkByName(k);
            ActionContext.getContext().getSession().put("targetURL", sl.getLongUrl());
            
        }
        
        return "shortlink";
        
    }
    
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public void setLink(String link)
    {
        this.link = link;
    }
    
    public String getLink()
    {
        return link;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getId()
    {
        return id;
    }
    
    public String getQa()
    {
        return qa;
    }
    
    public void setQa(String qa)
    {
        this.qa = qa;
    }
    
}
