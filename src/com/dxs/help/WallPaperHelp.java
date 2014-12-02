package com.dxs.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;
import com.dxs.Util.CommonUtils;
import com.dxs.common.Constants;

/**
 * 壁纸服务端帮助类
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-17]
 */
public class WallPaperHelp
{
    /**
     * 生成apk下载地址
     * 
     * @return
     */
    public static String getWallpaperApkDownLoadAddress(String wallPaperId, String marketType)
    {
        // 如果传的市场名为空,默认为C00
        String apkPath = Constants.constantsCompile.WEB_WALLPAPER_RESOURCES_APK;
        
        if (wallPaperId == null)
        {
            return null;
        }
        
        if (marketType == null)
        {
            apkPath = apkPath + "/C00/" + wallPaperId + ".apk";
        }
        
        else
        {
            apkPath = apkPath + marketType + wallPaperId + ".apk";
        }
        
        return apkPath;
    }
    
    /**
     * 生成apkpackage下载地址
     * 
     * @return
     */
    public static String getWallpaperApkPackageDownLoadAddress(String wallPaperId)
    {
        String apkPackagePath = Constants.constantsCompile.WALLPAPER_BUILD_RESOURCES_PACKAGE;
        
        if (wallPaperId == null)
        {
            return null;
        }
        apkPackagePath = apkPackagePath + "/" + wallPaperId + "_package.zip";
        
        return apkPackagePath;
    }
    
    /**
     * 获取壁纸封面下载地址
     */
    public static String getWallpaperCoverDownLoadAddress(String wallPaperId)
    {
        // 先指向编译目录
        String coverPath = Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_MAIN_DIR;
        
        if (wallPaperId == null)
        {
            return null;
        }
        
        else
        {
            coverPath = coverPath + "/" + wallPaperId + "/cover/cover.jpg";
        }
        
        return coverPath;
    }
    
    /**
     * 下载
     * 
     * @throws IOException
     */
    public static void download(String path, HttpServletResponse response)
        throws IOException
    {
        path = new String(path.getBytes("iso-8859-1"));
        
        // 根据该路径创建文件对象
        File file = new File(path);
        
        // 创建文件字节输入流
        InputStream in = new FileInputStream(file);
        
        // 创建输出流对象
        OutputStream os = response.getOutputStream();
        
        // 设置应答头信息
        response.addHeader("Content-Disposition", "attachment;filename="
            + new String(file.getName().getBytes("gbk"), "iso-8859-1"));
        
        response.addHeader("Content-Length", file.length() + "");
        
        response.setCharacterEncoding("gbk");
        
        response.setContentType("application/octet-stream");
        
        int data = 0;
        
        while ((data = in.read()) != -1)
        {
            // 循环读取文件
            os.write(data);
        }
        os.close();
        in.close();
    }
    
    /**
     * 获取市场的配置项
     */
    public static List<String> getMarketList()
    {
        // 读取全局配置文件
        Properties properties =
            ProfilesParameterHelpUtils.loadConfigFile(Constants.ConstantsConfigFile.COMMON_CONFIG_FILENAME);
        
        String marketProperty =
            ProfilesParameterHelpUtils.getConfig(properties, Constants.ConstantsConfigFile.MARKET_CONFIG_PROPERTY);
        
        List<String> marketList = CommonUtils.StringToArrList(marketProperty);
        
        if (marketList.isEmpty())
        {
            marketList.add("C00");
        }
        
        return marketList;
    }
    
    /**
     * 获取市场的配置项
     */
    public static String getServerAddress()
    {   
        // 读取全局配置文件
        Properties properties =
            ProfilesParameterHelpUtils.loadConfigFile(Constants.ConstantsConfigFile.COMMON_CONFIG_FILENAME);
        
        String serverAddress =
            ProfilesParameterHelpUtils.getConfig(properties, Constants.ConstantsConfigFile.SERVER_ADDRESS_PROPERTY);
        
        return serverAddress;
    }
    
}
