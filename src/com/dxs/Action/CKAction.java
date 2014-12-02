package com.dxs.Action;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Util.FileUtil;
import com.dxs.common.Constants;
import com.opensymphony.xwork2.ActionSupport;

public class CKAction extends ActionSupport implements ServletRequestAware
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8469541464505170438L;
    
    private String res;
    
    private HttpServletRequest request;
    
    PaperBagService pgServ = new PaperBagServiceImpl();
    
    String[] boxs;
    
    String[] vers;
    
    String[] tems;
    
    /**
     * 将壁纸改为上架
     * 
     * @return
     * @throws Exception
     */
    public String upBooks()
        throws Exception
    {
        boxs = request.getParameterValues("ck");
        if (boxs != null && boxs.length != 0)
        {
            for (int i = 0; i < boxs.length; i++)
            {
                // 上架改时间
                pgServ.modifyStateById(boxs[i], 1, (new Date()));
            }
        }
        else
        {
            System.out.println("no select");
        }
        return "paperbagViewAction";
    }
    
    /**
     * 改为下架
     * @return
     * @throws Exception
     */
    public String downBooks()
        throws Exception
    {
        boxs = request.getParameterValues("ck");
        if (boxs != null && boxs.length != 0)
        {
            for (int i = 0; i < boxs.length; i++)
            {
                // 下架不改时间
                pgServ.modifyStateById(boxs[i], 0);
            }
        }
        else
        {
            System.out.println("no select");
        }
        return "paperbagViewAction";
    }
    
    /**
     * 打包
     * 
     * @return
     * @throws Exception
     */
    public String downlo()
        throws Exception
    {
        boxs = request.getParameterValues("ck");
        File batchApkPackage = new File(Constants.constantsCompile.WALLPAPER_BUILD_RESOURCES_PACKAGE + "/batchPackage");
        
        // 如果批量打包目录不存在
        if (batchApkPackage.exists())
        {
            FileUtil.del(batchApkPackage, false);
        }
        
        else
        {
            batchApkPackage.mkdirs();
        }
        
        // 将编译好的不同APK的包放在一起
        if (boxs != null)
        {
            for (String wallPaperId : boxs)
            {
                // 壁纸打包出来的包
                File zipFile =
                    new File(Constants.constantsCompile.WALLPAPER_BUILD_RESOURCES_PACKAGE + "/" + wallPaperId
                        + "_package.zip");
                
                File tozipFile =
                    new File(Constants.constantsCompile.WALLPAPER_BUILD_RESOURCES_PACKAGE + "/batchPackage" + "/"
                        + wallPaperId + "_package.zip");
                
                // 将壁纸包拷贝到批量包的目录下
                FileUtil.copyFile(zipFile, tozipFile);
            }
            
            File zipFile =
                new File(Constants.constantsCompile.WALLPAPER_BUILD_RESOURCES_PACKAGE + "/batch_package.zip");
            
            // 打包压缩
            FileUtil.compress(batchApkPackage.getAbsolutePath(), zipFile);
        }
        
        return "paperbagViewAction!packok";
    }
    
    public String exeCmd(String cmd)
    {
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        String retStr = "";
        InputStreamReader insReader = null;
        char[] tmpBuffer = new char[1024];
        int nRet = 0;
        
        try
        {
            proc = runtime.exec(cmd);
            insReader = new InputStreamReader(proc.getInputStream(), Charset.forName("UTF-8"));
            
            while ((nRet = insReader.read(tmpBuffer, 0, 1024)) != -1)
            {
                retStr += new String(tmpBuffer, 0, nRet);
            }
            
            insReader.close();
            retStr = HTMLEncode(retStr);
        }
        catch (Exception e)
        {
            retStr = "<font color=\"red\">bad command \"" + cmd + "\"</font>";
        }
        return retStr;
    }
    
    public String HTMLEncode(String str)
    {
        str = str.replaceAll(" ", "&nbsp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\r\n", "<br>");
        return str;
    }
    
    public String getRes()
    {
        return res;
    }
    
    public void setRes(String res)
    {
        this.res = res;
    }
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
}
