package com.dxs.task;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.dom4j.DocumentException;
import com.dxs.Entity.CompileQueue;
import com.dxs.Entity.PaperBag;
import com.dxs.Entity.ShortLink;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.ShortLinkServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.ShortLinkService;
import com.dxs.Util.FileUtil;
import com.dxs.Util.ShortTools;
import com.dxs.Util.CommonUtils;
import com.dxs.common.Constants;
import com.dxs.help.ProfilesParameterHelpUtils;
import com.dxs.help.WallPaperHelp;

/**
 * 编译task
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-9]
 */
public class GetAndCompileTask extends TimerTask
{
    // 定时任务运行状态
    private static boolean isCompileRunning = false;
    
    private ServletContext context = null;
    
    private String wallPaperName = null;
    
    private String wallPaperId = null;
    
    /**
     * 壁纸资源目录
     */
    private String wallPaperResourcesDir = null;
    
    /**
     * 壁纸版本号
     */
    private String wallPaperVersion = null;
    
    /**
     * 壁纸新的包名
     */
    private String newPackageName = null;
    
    public GetAndCompileTask(ServletContext context)
    {
        this.context = context;
    }
    
    @Override
    public void run()
    {
        try
        {
            if (!isCompileRunning)
            {
                isCompileRunning = true;
                
                // 创建初始化目录
                CompileHelp compileHelp = new CompileHelp();
                compileHelp.createInitDir();
                
                PaperBagService paperSer = new PaperBagServiceImpl();
                List<CompileQueue> compileQueueList = paperSer.getCompileQueueList();
                
                for (CompileQueue compileQueue : compileQueueList)
                {
                    wallPaperName = compileQueue.getBgName();
                    wallPaperId = String.valueOf(compileQueue.getBgId());
                    
                    if (wallPaperName != null && wallPaperId != null)
                    {
                        doCompile();
                    }
                }
            }
            
            isCompileRunning = false;
        }
        catch (Exception e)
        {
            isCompileRunning = false;
        }
    }
    
    /**
     * 编译
     * 
     * @param name
     */
    public void doCompile()
    {
        // 数据初始化
        try
        {
            dataInit();
            
            //获取市场列表
            List<String> marketList = WallPaperHelp.getMarketList();
            
            // List<String> marketList = new ArrayList<String>(Arrays.asList("C00"));
            
            for (String marketId : marketList)
            {
                // 编译前的初始化
                buildInit();
                
                // 拷贝封面
                copyWallpaperCover();
                
                // 拷贝壁纸资源文件
                copyWallpaperResources();
                
                // 编译生成apk
                antbuild(marketId);
                
                // 拷贝APK
                copyApk(marketId, wallPaperId);
                
                // 打包APK
                packageApk(wallPaperId);
                
                // 更新壁纸数据库记录
                updateBookInfo(wallPaperId, wallPaperName, wallPaperVersion);
                
                // 删除编译队列
                deleteCompileRecord(wallPaperId);
                
                // 修改编译完成，将编译改为下载状态
                modifyCompileFinishStatus(wallPaperId);
            }
        }
        
        catch (DocumentException e)
        {
            // 删除编译队列
            deleteCompileRecord(wallPaperId);
            e.printStackTrace();
        }
        
        catch (Exception e)
        {
            // 删除编译队列
            deleteCompileRecord(wallPaperId);
            e.printStackTrace();
        }
    }
    
    /**
     * 打包APK
     */
    private void packageApk(String wallPaperId)
    {
        // 在打包目录创建目录ID的文件夹
        File wallPaperIdDir =
            new File(Constants.constantsCompile.WALLPAPER_BUILD_RESOURCES_PACKAGE + "/" + wallPaperId);
        
        // 如果打包目录不存在
        if (!wallPaperIdDir.exists())
        {
            wallPaperIdDir.mkdirs();
        }
        
        List<String> marketList = new ArrayList<String>(Arrays.asList("C00"));
        
        for (String market : marketList)
        {
            String copyMarketDir = Constants.constantsCompile.WEB_WALLPAPER_RESOURCES_APK + "/" + market;
            File apk = new File(copyMarketDir + "/" + wallPaperId + ".apk");
            File apkMarket = new File(wallPaperIdDir + "/" + wallPaperId + "_" + market + ".apk");
            
            try
            {
                FileUtil.copyFile(apk, apkMarket);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        File zipFile =
            new File(Constants.constantsCompile.WALLPAPER_BUILD_RESOURCES_PACKAGE + "/" + wallPaperId + "_package.zip");
        
        // 压缩APK
        FileUtil.compress(wallPaperIdDir.getAbsolutePath(), zipFile);
    }
    
    /**
     * 编译完更新数据库
     */
    private void updateBookInfo(String wallPaperId, String wallPaperName, String version)
    {
        // 先查询wallPaperId在数据库内是否存在
        PaperBagService paperSer = new PaperBagServiceImpl();
        
        // 如果ID对应的壁纸存在
        if (paperSer.getPaperBagById(wallPaperId) != null)
        {
            // 更新壁纸版本号
            paperSer.modifyVerSionById(wallPaperId, version);
        }
        
        else
        {
            // 新增记录
            PaperBag bookinfo = new PaperBag();
            bookinfo.setId(Integer.parseInt(wallPaperId));
            bookinfo.setBagName(wallPaperName);
            bookinfo.setUploadTime(new Date());
            // 0:下架状态，1:上架状态
            bookinfo.setState("0");
            bookinfo.setState_rec("加推荐");
            bookinfo.setVersion(version);
            bookinfo.setSver("100");
            bookinfo.setSummary("");
            // 设置为编译中
            bookinfo.setInitial(Constants.constantsCompile.COMPILE_STATUS_COMPILEING);
            // 安装量为0
            bookinfo.setNum(0);
            bookinfo.setCateId(0);
            // 生成转世ID
            // String unid = DateUtils.getDateString();
            bookinfo.setUnionId(null);
            
            String imgpath = Constants.SERVER_MIAN_PATH + "/DownLoadWallPaperCoverAction?wallPaperId=" + wallPaperId;
            
            // 设置壁纸路径
            bookinfo.setCover(imgpath);
            
            paperSer.addPaperBag(bookinfo);
            
            // 生成短链接
            genShortLink(wallPaperId);
        }
    }
    
    /**
     * 生成短链接
     */
    private void genShortLink(String wallPaperId)
    {
        // 查出短链接是否存在
        ShortLinkService shortServ = new ShortLinkServiceImpl();
        
        ShortLink shortLink = shortServ.getShortLinkByName(wallPaperId);
        
        if (shortLink == null)
        {
            String longkink = Constants.APK_DOWNLOAD_ACTION + wallPaperId;
            
            try
            {
                ShortTools.forShortLink(wallPaperId, longkink, 1);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 获取资源版本号
     * 
     * @return
     */
    private String getSver(String wallPaperId)
    {
        String version = null;
        PaperBagService paperSer = new PaperBagServiceImpl();
        version = paperSer.getSverById(wallPaperId);
        
        if (version == null)
        {
            version = "100";
        }
        return version;
    }
    
    /**
     * 删除编译列表记录
     */
    private void deleteCompileRecord(String bgId)
    {
        PaperBagService paperSer = new PaperBagServiceImpl();
        
        // 新增添加到编译队列
        paperSer.deleteCompileQueue(bgId);
    }
    
    /**
     * 修改壁纸库表内的状态
     */
    private void modifyCompileFinishStatus(String wallPaperId)
    {
        PaperBagService paperSer = new PaperBagServiceImpl();
        
        // 如果壁纸库里面已经存在了
        if (paperSer.wallPaperExistOrNot(wallPaperId))
        {
            // 修改状态为下载
            paperSer.modifyInitById(wallPaperId, Constants.constantsCompile.DOWNLOAD_STATUS);
        }
    }
    
    /**
     * copy apk到相应目录
     * 
     * @param market
     * @param appId
     * @throws IOException
     */
    private void copyApk(String market, String appId)
        throws IOException
    {
        // 编译出的APK目录
        File apk = new File(Constants.constantsCompile.WEB_APP_COMPILE_PATH + "/bin/wallpaper-final.apk");
        
        String copyMarketDir = Constants.constantsCompile.WEB_WALLPAPER_RESOURCES_APK + "/" + market;
        
        File toCopyApk = new File(copyMarketDir + "/" + appId + ".apk");
        
        // 拷贝apk到相应位置
        
        File dir = new File(copyMarketDir);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        
        FileUtil.copyFile(apk, toCopyApk);
        
    }
    
    /**
     * 数据初始化
     * 
     * @throws DocumentException
     * 
     * @throws Exception
     */
    private void dataInit()
        throws DocumentException
    {
        // 单个壁纸的目录
        wallPaperResourcesDir = Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_MAIN_DIR + "/" + wallPaperId;
        
        /**
         * 获取app版本号
         */
        wallPaperVersion =
            ProfilesParameterHelpUtils.readAppversion(this.getClass().getClassLoader().getResource("/").getPath()
                + "config/appversion.xml");
        
        // 获取系统版本
        String appVersion = wallPaperVersion.substring(0, 3);
        
        // 获取资源版本号
        String resourcesVersion = getSver(wallPaperId);
        
        wallPaperVersion = appVersion + resourcesVersion;
        
        // 新创建的包名
        newPackageName = Constants.constantsCompile.PACKAGE_REPLACE_NAME + wallPaperId;
    }
    
    /**
     * 编译初始化
     * 
     * @throws IOException
     */
    private void buildInit()
        throws IOException
    {
        File file = new File(Constants.constantsCompile.WEB_APP_PATH);
        if (file.exists())
        {
            
            // 删除旧的编译目录
            FileUtil.del(new File(Constants.constantsCompile.WEB_APP_COMPILE_PATH), true);
            
            // 将原来目录文件拷贝一份新的
            FileUtil.copyDirectiory(Constants.constantsCompile.WEB_APP_PATH,
                Constants.constantsCompile.WEB_APP_COMPILE_PATH);
            
        }
    }
    
    /**
     * 拷贝封面
     * 
     * @throws IOException
     */
    private void copyWallpaperCover()
        throws IOException
    {
        File wallpaperCover = new File(wallPaperResourcesDir + "/cover/cover.png");
        
        File wallpaperCoverDrawable = new File(Constants.constantsCompile.ANDROID_DRAWABLE_DIR + "/" + "icon.png");
        
        File wallpaperCoverDrawableHdpi =
            new File(Constants.constantsCompile.ANDROID_DRAWABLE_HDPI_DIR + "/" + "icon.png");
        
        File wallpaperCoverDrawableLdpi =
            new File(Constants.constantsCompile.ANDROID_DRAWABLE_LDPI_DIR + "/" + "icon.png");
        
        File wallpaperCoverDrawableMdpi =
            new File(Constants.constantsCompile.ANDROID_DRAWABLE_MDPI_DIR + "/" + "icon.png");
        
        FileUtil.copyFile(wallpaperCover, wallpaperCoverDrawable);
        FileUtil.copyFile(wallpaperCover, wallpaperCoverDrawableHdpi);
        FileUtil.copyFile(wallpaperCover, wallpaperCoverDrawableLdpi);
        FileUtil.copyFile(wallpaperCover, wallpaperCoverDrawableMdpi);
        
    }
    
    /**
     * 拷贝图片资源
     * 
     * @throws IOException
     */
    private void copyWallpaperResources()
        throws IOException
    {
        File file = new File(Constants.constantsCompile.ANDROID_ASSETS_WALLPAPER_DIR);
        if (file.exists())
        {
            // 删除bizhi目录
            FileUtil.del(new File(Constants.constantsCompile.ANDROID_ASSETS_WALLPAPER_DIR), false);
            
            // 拷贝图片资源到壁纸目录
            FileUtil.copyDirectiory(wallPaperResourcesDir, Constants.constantsCompile.ANDROID_ASSETS_WALLPAPER_DIR);
            
            // 删除cover目录
            FileUtil.del(new File(Constants.constantsCompile.ANDROID_ASSETS_WALLPAPER_DIR + "/cover"), true);
            
        }
    }
    
    /**
     * 编译生成apk
     * 
     * @throws IOException
     */
    private void antbuild(String marketId)
        throws IOException
    {
        String path = this.getClass().getClassLoader().getResource("/").getPath() + "config/build.xml";
        
        PrintStream out = null;
        
        out = new PrintStream(new File("/home/environment/apache-tomcat-7/webapps/WallPaperWeb/antlog"));
        
        File buildFile = new File(path);
        
        Project p = new Project();
        
        DefaultLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(System.err);
        consoleLogger.setOutputPrintStream(out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        p.addBuildListener(consoleLogger);
        
        try
        {
            p.fireBuildStarted();
            
            p.init();
            ProjectHelper helper = ProjectHelper.getProjectHelper();
            
            helper.parse(p, buildFile);
            
            // 替换String.xml中的app_name
            p.setProperty(Constants.constantsCompile.APP_NAME_MATCH_KEY,
                Constants.constantsCompile.APP_NAME_MATCH_VALUE);
            p.setProperty(Constants.constantsCompile.APP_NAME_REPLACE_KEY, wallPaperName);
            
            // 替换String.xml中的Market_type
            p.setProperty(Constants.constantsCompile.MARKET_TYPE_MATCH_KEY,
                Constants.constantsCompile.MARKET_TYPE_MATCH_VALUE);
            p.setProperty(Constants.constantsCompile.MARKET_TYPE_REPLACE_KEY, marketId);
            
            // 替换AndroidManifest.xml中的 android:versionCode
            p.setProperty(Constants.constantsCompile.ANDROIDMANIFEST_VERSIONCODE_REPLACE_KEY, wallPaperVersion);
            
            // 替换AndroidManifest.xml中的 android:versionCode
            p.setProperty(Constants.constantsCompile.ANDROIDMANIFEST_PACKAGE_REPLACE_KEY, newPackageName);
            
            // 设置build.xml中原先包名的key值为com.iss.cp
            p.setProperty(Constants.constantsCompile.PACKAGE_MATCH_KEY, Constants.constantsCompile.PACKAGE_REPLACE_NAME);
            
            // 替换AndroidManifest.xml中友盟的key
            p.setProperty(Constants.constantsCompile.ANDROIDMANIFEST_UMCHANNEL_KEY_REPLACE_KEY,
                Constants.constantsCompile.ANDROIDMANIFEST_UMCHANNEL_KEY_REPLACE_KEY_VALUE);
            
            // 替换AndroidManifest.xml中友盟的渠道名称
            p.setProperty(Constants.constantsCompile.ANDROIDMANIFEST_UMCHANNEL_REPLACE_KEY, marketId);
            
            p.setProperty("basedir", Constants.constantsCompile.WEB_APP_COMPILE_PATH);
            
            // 替换代码工程中的
            p.executeTarget("preparefiles");
            
            p.executeTarget("10zipalign");
            
            p.fireBuildFinished(null);
        }
        catch (BuildException be)
        {
            p.fireBuildFinished(be);
            
            // 删除编译队列
            deleteCompileRecord(wallPaperId);
        }
    }
}
