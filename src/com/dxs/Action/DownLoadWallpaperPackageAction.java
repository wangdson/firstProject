package com.dxs.Action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.dxs.common.Constants;
import com.dxs.help.WallPaperHelp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 下载打包压缩的APK包
 * @author  姓名 工号
 * @version  [版本号, 2014-6-19]
 */
public class DownLoadWallpaperPackageAction extends ActionSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3322761607261806111L;
    
    public String execute()
    {
        ActionContext context = ActionContext.getContext();
        
        HttpServletResponse response = (HttpServletResponse)context.get(ServletActionContext.HTTP_RESPONSE);
        
        String path = Constants.constantsCompile.WALLPAPER_BUILD_RESOURCES_PACKAGE + "/batch_package.zip";
        
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
}
