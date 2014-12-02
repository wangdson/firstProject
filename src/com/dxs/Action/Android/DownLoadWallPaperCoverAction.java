package com.dxs.Action.Android;


import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.help.WallPaperHelp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 下载APK的action
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-15]
 */
public class DownLoadWallPaperCoverAction extends ActionSupport
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
        
        
        PaperBagService paperSer = new PaperBagServiceImpl();
        
        PaperBag paperBag = paperSer.getPaperBagById(wallPaperId);
        
        if (paperBag != null)
        {
            String bagName = paperBag.getBagName();
            String path = WallPaperHelp.getWallpaperCoverDownLoadAddress(wallPaperId);
            WallPaperHelp.download(path, response);
        }
        
        else
        {
            return ERROR;
        }
        
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
