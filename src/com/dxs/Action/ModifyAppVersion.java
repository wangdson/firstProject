package com.dxs.Action;

import java.net.URL;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.dxs.help.ProfilesParameterHelpUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * 修改编译版本号的功能
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-24]
 */
public class ModifyAppVersion extends ActionSupport
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4034528029553449982L;
    
    /**
     * 版本号
     */
    private String appver;
    
    public String execute()
        throws Exception
    {
        return "ModifyAppVersion";
        
    }
    
    /**
     * 修改版本号
     * 
     * @return
     * @throws Exception
     */
    public String modify()
        throws Exception
    {
        
        if (getAppver() != null && getAppver() != "")
        {
            String xmlpath = this.getClass().getClassLoader().getResource("/").getPath() + "config/appversion.xml";
            ProfilesParameterHelpUtils.BuildAppVersionXMLDoc(getAppver(), xmlpath);
        }
        return "PaperbagAction";
        
    }
    
    public Document getRemoteXML(String url)
    {
        try
        {
            URL url1 = new URL(url);
            SAXReader reader = new SAXReader();
            return reader.read(url1);
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public void setAppver(String appver)
    {
        this.appver = appver;
    }
    
    public String getAppver()
    {
        return appver;
    }
    
}
