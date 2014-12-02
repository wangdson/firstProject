package com.dxs.Action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.dxs.help.WallPaperHelp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 下载apk包的action
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-18]
 */
public class DownLoadApkPackageAction extends ActionSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1811057872987085050L;
    
    private String id = null;
    
    public String execute()
    {
        ActionContext context = ActionContext.getContext();
        
        HttpServletResponse response = (HttpServletResponse)context.get(ServletActionContext.HTTP_RESPONSE);
        
        String path = WallPaperHelp.getWallpaperApkPackageDownLoadAddress(id);
        
        try
        {
            path = new String(path.getBytes("iso-8859-1"));
            WallPaperHelp.download(path, response);
        }
        catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
