package com.dxs.Action.Android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.dxs.Entity.UserBag;
import com.dxs.Entity.Users;
import com.dxs.Service.Impl.UserBagServiceImpl;
import com.dxs.Service.Impl.UsersServiceImpl;
import com.dxs.Service.Intf.UserBagService;
import com.dxs.Service.Intf.UsersService;
import com.dxs.Util.CONSTANTS;
import com.dxs.Util.PictureOperate;

import com.opensymphony.xwork2.ActionSupport;

public class UpFile extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    final Log log = LogFactory.getLog(UpFile.class);
    
    // 上传文件域
    private File image;
    
    // 上传文件类型
    private String imageContentType;
    
    // 封装上传文件名
    private String imageFileName;
    
    // 接受依赖注入的属性
    private String savePath;
    
    UsersService userServ = new UsersServiceImpl();
    
    public String execute()
        throws Exception
    {
        
        HttpServletRequest request = ServletActionContext.getRequest();
        String usename = request.getParameter("username");
        String filename = request.getParameter("fileName");
        
        String tag = request.getParameter("tag");
        // 1.数据库存记录
        UserBagService userBagServ = new UserBagServiceImpl();
        Users user = userServ.getUserByName(usename);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = format.format(new Date());
        log.info(user.getUserId() + "的上传时间：" + formatDate);
        
        int maxid = userBagServ.findMaxId();
        int ubid = 0;// 用户包ID
        if (maxid == 0)
        {
            ubid = maxid + 1001;
        }
        else
        {
            ubid = maxid + 1;
        }
        UserBag userbag = new UserBag();
        userbag.setId(ubid);
        int end = filename.lastIndexOf(".");
        String shortFilename = filename.substring(0, end);
        userbag.setBagName(shortFilename);
        userbag.setUploadTime(new Date());
        userbag.setTag(tag);
        userbag.setCover("ipaper/uploaduser/" + user.getUserId() + "/" + ubid + "/" + ubid + ".jpg");
        userbag.setUserId(user.getUserId());
        userbag.setVersion("100100");
        userbag.setSver("100");
        // 是否第一次上传//0:是新上传，1：编译中，2：等待中，3：单本编译结束，4：本次勾选编译结束=下载APK，5：编译出错
        userbag.setInitial("0");
        userbag.setNum(0);
        
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try
        {
            log.info(user.getUserId() + "的获取Android端传过来的普通信息：");
            log.info(user.getUserId() + "的用户名：" + usename);
            log.info(user.getUserId() + "的标签:" + tag);
            log.info(user.getUserId() + "的文件名：" + filename);
            log.info(user.getUserId() + "的获取Android端传过来的文件信息：");
            log.info(user.getUserId() + "的文件存放目录: " + getSavePath());
            log.info(ubid + "的文件名称: " + imageFileName);
            log.info(ubid + "的文件大小: " + image.length());
            log.info(ubid + "的文件类型: " + imageContentType);
            // 2.在服务器上建目录 目录结构：uploaduser/用户ID/文件ID/文件.zip
            File path = new File(getSavePath() + "/" + user.getUserId() + "/" + ubid + "/");
            log.info(ubid + "的文件存放目录是否存在：" + path.getPath().toString());
            Timer timer = new Timer();
            if (path.exists())
            {
                // 配置文件中的存放目录：getSavePath()<!-- 动态设置savePath的属性值 -->
                // 这里放在改动后的目录。path.getPath()
                fos = new FileOutputStream(path.getPath() + "/" + getImageFileName());
                fis = new FileInputStream(getImage());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fis.read(buffer)) != -1)
                {
                    fos.write(buffer, 0, len);
                }
                log.info(ubid + "的文件上传成功");
                // 数据库插入
                userBagServ.addUserBag(userbag);
                // 解压取第一张图片
                getFirstPaper(path.getPath() + "/", ubid);
                // 编译
//                timer.schedule(new Compile(userbag, "/WallPaperWeb/ipaper/uploaduser/" + user.getUserId() + "/" + ubid
//                    + "/"), 0);
            }
            
            else
            {
                String cmd = "";
                cmd = "mkdir -p " + path.getPath() + "/";
                log.info(ubid + "的创建目录命令：" + cmd);
                exeCmd(cmd);
                fos = new FileOutputStream(path.getPath() + "/" + getImageFileName());
                fis = new FileInputStream(getImage());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fis.read(buffer)) != -1)
                {
                    fos.write(buffer, 0, len);
                }
                log.info(ubid + "的文件上传成功");
                // 数据库插入
                userBagServ.addUserBag(userbag);
                // 解压取第一张图片
                getFirstPaper(path.getPath() + "/", ubid);
                // 编译
//                timer.schedule(new Compile(userbag, "/WallPaperWeb/ipaper/uploaduser/" + user.getUserId() + "/" + ubid
//                    + "/"), 0);
            }
        }
        catch (Exception e)
        {
            log.info(ubid + "的文件上传失败");
            e.printStackTrace();
        }
        finally
        {
            close(fos, fis);
        }
        return "SUCCESS";
    }
    
    // 解压取第一张图片 path=/home/cloud/java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/uploaduser/用户ID/文件ID/
    public void getFirstPaper(String path, int userbagId)
    {
        String cmd = "";
        String res = "";
        cmd = "sudo unzip -o " + path + "make.zip" + " -d " + path;
        log.info(userbagId + "的解压命令：" + cmd);
        exeCmd(cmd);// 解压
        cmd = "ls " + path + "make";
        log.info(userbagId + "的LS命令：" + cmd);
        res = exeCmd(cmd);// ls命令
        String[] imgbox = res.split(" ");// 存放目录中所含的jpg名称
        if (imgbox.length != 0)
        {
            String mycov = "";
            for (int k = 0; k < imgbox.length; k++)
            {
                if (imgbox[k] == "0.jpg")
                {
                    mycov = "0.jpg";
                }
                else
                {
                    mycov = imgbox[0];
                }
            }
            
            log.info(userbagId + "的第一张图片：" + mycov);
            
            PictureOperate po = new PictureOperate();
            String picfrom = CONSTANTS.CONVERT + path + "make/" + mycov;
            String picto = path + userbagId + ".jpg";
            po.PictureCompression(picfrom, picto, CONSTANTS.PICVALUE);
            
            String toicopng = path + userbagId + ".png";
            po.PicCompress(picto, toicopng, 72);
        }
    }
    
    /**
     * 文件存放目录
     * 
     * @return
     */
    public String getSavePath()
        throws Exception
    {
        return ServletActionContext.getServletContext().getRealPath(savePath);
    }
    
    public void setSavePath(String savePath)
    {
        this.savePath = savePath;
    }
    
    public File getImage()
    {
        return image;
    }
    
    public void setImage(File image)
    {
        this.image = image;
    }
    
    public String getImageContentType()
    {
        return imageContentType;
    }
    
    public void setImageContentType(String imageContentType)
    {
        this.imageContentType = imageContentType;
    }
    
    public String getImageFileName()
    {
        return imageFileName;
    }
    
    public void setImageFileName(String imageFileName)
    {
        this.imageFileName = imageFileName;
    }
    
    private void close(FileOutputStream fos, FileInputStream fis)
    {
        // WriteLog wl = new WriteLog(file);
        if (fis != null)
        {
            try
            {
                fis.close();
                fis = null;
            }
            catch (IOException e)
            {
                log.info("FileInputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (fos != null)
        {
            try
            {
                fos.close();
                fis = null;
            }
            catch (IOException e)
            {
                log.info("FileOutputStream关闭失败");
                e.printStackTrace();
            }
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
    
}
