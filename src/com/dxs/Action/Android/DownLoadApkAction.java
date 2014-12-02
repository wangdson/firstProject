package com.dxs.Action.Android;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.dxs.help.WallPaperHelp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 下载APK的action
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-15]
 */
public class DownLoadApkAction extends ActionSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5464975020325290025L;
    
    private String wallPaperId = null;
    
    @Override
    public String execute()
        throws Exception
    {
        ActionContext context = ActionContext.getContext();
        
        HttpServletResponse response = (HttpServletResponse)context.get(ServletActionContext.HTTP_RESPONSE);
        
        String path = WallPaperHelp.getWallpaperApkDownLoadAddress(wallPaperId, null);
        
        path = new String(path.getBytes("iso-8859-1"));
        
        WallPaperHelp.download(path, response);
        
        return SUCCESS;
    }

    public String getWallPaperId()
    {
        return wallPaperId;
    }

    public void setWallPaperId(String wallPaperId)
    {
        this.wallPaperId = wallPaperId;
    }
}
