package com.dxs.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import com.dxs.Entity.CompileQueue;
import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Util.FileUtil;
import com.dxs.Util.PictureOperate;
import com.dxs.common.Constants;

/**
 * 将编译添加到编译队列中
 * 
 * @author 姓名 工号
 */
public class AddCompileTask extends TimerTask
{
    // 定时任务运行状态
    private static boolean isRunning = false;
    
    private ServletContext context = null;
    
    public AddCompileTask(ServletContext context)
    {
        this.context = context;
    }
    
    public void run()
    {
        // 遍历上传目录中的zip文件
        List<File> fileList = new ArrayList<File>();
        try
        {
            if (!isRunning)
            {
                isRunning = true;
                
                CompileHelp compileHelp = new CompileHelp();
                compileHelp.createInitDir();
                // 将图片资源拷贝到保存图片的目录
                try
                {
                    FileUtil.getFile(new File(Constants.constantsCompile.wallPaperUploadDir), fileList, "zip", true);
                }
                catch (UnsupportedEncodingException e1)
                {
                    e1.printStackTrace();
                }
                
                File file = null;
                if (fileList.size() > 0)
                {
                    file = fileList.get(0);
                    if (!checkuploadOK(file.getAbsolutePath()))
                    {
                        isRunning = false;
                        return;
                    }
                    // 解压文件
                    try
                    {
                        // 将资源文件拷贝到临时编译目录里进行解压
                        File copyToFile =
                            new File(Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_COMPILE_TEMPDIR + "/"
                                + file.getName());
                        
                        FileUtil.copyFile(file, copyToFile);
                        
                        // 删除FTP下资源
                        FileUtil.del(file, null);
                        
                        try
                        {
                            FileUtil.unzip(copyToFile, new File(
                                Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_COMPILE_TEMPDIR));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        
                        // 将文件拷贝到压缩包备份目录
                        File backupFile =
                            new File(Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_BACKUPDIR + "/"
                                + file.getName());
                        
                        // 备份上传壁纸压缩包
                        FileUtil.copyFile(copyToFile, backupFile);
                        
                        // 删除拷贝到临时编译目录的压缩壁纸包
                        FileUtil.del(copyToFile, true);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    
                    // 添加编译队列
                    addCompileQueue();
                }
                
                isRunning = false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            isRunning = false;
        }
    }
    
    private Boolean checkuploadOK(String filepath)
    {
        String cmd = "lsof " + filepath;
        String res = exeCmd(cmd);
        if (res == null || res == "")
        {
            return true;
        }
        
        else
        {
            return false;
        }
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
    
    /**
     * 生成编译队列
     */
    private void addCompileQueue()
    {
        // 将文件夹放在编译目录中，添加到编译的队列中去
        List<File> fileList = new ArrayList<File>();
        
        // 获取编译临时目录中的一级文件夹
        FileUtil.getFirstFileList(fileList, new File(
            Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_COMPILE_TEMPDIR));
        
        // 将资源目录复制到编译目录下去
        
        int wallPaperId = 0;
        
        for (File filedir : fileList)
        {
            try
            {
                List<File> coverFile = new ArrayList<File>();
                // 寻找封面
                FileUtil.findFile("0.jpg", filedir, coverFile);
                
                // 在资源文件夹中找到封面了
                if (coverFile != null && coverFile.size() == 1)
                {
                    PaperBagService paperSer = new PaperBagServiceImpl();
                    
                    // 获取库内壁纸所有名字
                    PaperBag paperBag = paperSer.getPaperBagByName(filedir.getName());
                    
                    // 如果库里不存在，则需要生成1个新的ID
                    if (paperBag == null)
                    {
                        wallPaperId = paperSer.findCompileMaxId();
                        // 记录为空
                        if (wallPaperId == 0)
                        {
                            wallPaperId = paperSer.findMaxId();
                        }
                        
                        ++wallPaperId;
                    }
                    
                    else
                    {
                        wallPaperId = paperBag.getId();
                    }
                    
                    // 裁剪保存封面
                    addCoverPicture(coverFile.get(0));
                    
                    // 将临时资源文件夹的内容拷贝到编译文件夹
                    FileUtil.copyDirectiory(filedir.getAbsolutePath(),
                        Constants.constantsCompile.WAllPAPER_BUILD_RESOURCCES_MAIN_DIR + "/" + wallPaperId);
                    
                    // 删除临时资源文件夹的目录
                    FileUtil.del(filedir, true);
                    
                    // 添加到编译队列中去，入库
                    CompileHelp.addComileToDataBase(filedir.getName(), wallPaperId);
                }
                
                else
                {
                    // 删除临时资源文件夹的目录
                    FileUtil.del(filedir, true);
                }
            }
            
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 添加封面
     */
    private void addCoverPicture(File file)
    {
        // 创建封面,如果没有对应的封面目录，则先创建封面文件夹
        String coverDir = file.getParent() + "/cover";
        
        File coverDirFile = new File(coverDir);
        
        File genWallpaperJpg = new File(coverDir + "/cover.jpg");
        File genWallpaperPng = new File(coverDir + "/cover.png");
        
        // 如果封面目录没创建，则创建封面目录
        if (!coverDirFile.exists())
        {
            // 创建目录
            coverDirFile.mkdirs();
        }
        
        String picfrom = file.getAbsolutePath();
        String picto = genWallpaperJpg.getAbsolutePath();
        String toicopng = genWallpaperPng.getAbsolutePath();
        
        PictureOperate pictureOperate = new PictureOperate();
        
        // 生成jpg文件
        pictureOperate.PictureCompression(picfrom, picto, 600);
        
        // 生成png文件
        pictureOperate.PicCompress(picto, toicopng, 72);
    }
    
}
