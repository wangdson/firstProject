package com.dxs.task;

import java.io.File;
import java.util.List;

import com.dxs.Entity.CompileQueue;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.common.Constants;

public class CompileHelp
{
    /**
     * 初始文件夹创建
     */
    public void createInitDir()
    {
        // apk编译完的目录
        File apkDir = new File(Constants.constantsCompile.WEB_WALLPAPER_RESOURCES_APK);
        
        // 壁纸资源包备份目录
        File wallPaperBuildResourcesBackupDir =
            new File(Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_BACKUPDIR);
        
        // 壁纸编译目录
        File pictureResourcesCompile = new File(Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_MAIN_DIR);
        
        // 编译资源解压目录
        File wallPaperBuildResourcesCompileTempDir =
            new File(Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_COMPILE_TEMPDIR);
        
        File wallPaperBuildResourcesPackage = new File(Constants.constantsCompile.WALLPAPER_BUILD_RESOURCES_PACKAGE);
        
        if (!apkDir.exists())
        {
            apkDir.mkdirs();
        }
        
        if (!wallPaperBuildResourcesBackupDir.exists())
        {
            wallPaperBuildResourcesBackupDir.mkdirs();
        }
        
        if (!pictureResourcesCompile.exists())
        {
            pictureResourcesCompile.mkdirs();
        }
        
        if (!wallPaperBuildResourcesCompileTempDir.exists())
        {
            wallPaperBuildResourcesCompileTempDir.mkdirs();
        }
        
        if (!wallPaperBuildResourcesPackage.exists())
        {
            wallPaperBuildResourcesPackage.mkdirs();
        }
    }
    
    /**
     * 添加到编译队列
     */
    public static void addComileToDataBase(String name, int id)
    {
        PaperBagService paperSer = new PaperBagServiceImpl();
        
        // 获取编译队列信息
        List<CompileQueue> compileQueueList = paperSer.getCompileQueueList();
        
        if (compileQueueList != null)
        {
            // 检查编译是否在队列中
            for (CompileQueue compileQueue : compileQueueList)
            {
                if (compileQueue.getBgName() != null && compileQueue.getBgName().equals(name))
                {
                    return;
                }
            }
            
            // 新增添加到编译队列
            paperSer.addCompileQueue(name, id);
            
            // 如果壁纸库里面已经存在了
            if (paperSer.wallPaperExistOrNot(String.valueOf(id)))
            {
                // 修改状态为等待编译
                paperSer.modifyInitById(String.valueOf(id), Constants.constantsCompile.COMPILE_STATUS_WAIT);
            }
        }
    }
}
