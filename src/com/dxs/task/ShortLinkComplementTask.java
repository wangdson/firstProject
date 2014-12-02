package com.dxs.task;

import java.io.UnsupportedEncodingException;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import com.dxs.Entity.ShortLinkComplement;
import com.dxs.Service.Impl.ShortLinkServiceImpl;
import com.dxs.Service.Intf.ShortLinkService;
import com.dxs.Util.ShortTools;
import com.dxs.common.Constants;
import com.dxs.help.WallPaperHelp;

public class ShortLinkComplementTask extends TimerTask
{
    // 定时任务运行状态
    private static boolean isRunning = false;
    
    private ServletContext context = null;
    
    public ShortLinkComplementTask(ServletContext context)
    {
        this.context = context;
    }
    
    @Override
    public void run()
    {
        try
        {
            if (!isRunning)
            {
                isRunning = true;
                genShortLink();
                isRunning = false;
            }
        }
        
        catch (Exception e)
        {
            isRunning = false;
        }
    }
    
    /**
     * 从锻炼补齐库里面获取一条短链接
     */
    private void genShortLink()
    {
        ShortLinkService shortServ = new ShortLinkServiceImpl();
        
        ShortLinkComplement shortLinkComplement = shortServ.getOneShortLinkRecordComplement();
        
        if(shortLinkComplement !=null)
        {
            String wallPaperId = shortLinkComplement.getWallpaperId();
            String longkink = Constants.APK_DOWNLOAD_ACTION + wallPaperId;
            try
            {
                ShortTools.forShortLink(wallPaperId, longkink, 1);
                
                // 删除补齐短连接表记录
                shortServ.deleteShortLinkComplement(wallPaperId);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
    }
}
