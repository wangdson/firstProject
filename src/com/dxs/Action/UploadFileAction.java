package com.dxs.Action;

import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dxs.Util.*;

import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.dxs.Util.CONSTANTS;
import com.dxs.Util.PictureOperate;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadFileAction extends ActionSupport
{
    /**
     * 上传
     */
    private static final long serialVersionUID = 1L;
    
    private String bagName;
    
    private String tag;
    
    /**
     * 上传文件
     */
    private File doc;
    
    /**
     * 上传封面
     */
    private File cov;
    
    private String fileName;// 上传文件名称
    
    private String fileNameCov;// 上传封面名称
    
    private String contentType;// 上传文件类型
    
    private String fileDir;// 保存文件路径
    
    private String targetFileName;// 保存文件名称
    
    private String content;
    
    @SuppressWarnings("deprecation")
    public String execute()
        throws Exception
    {
        if (fileName != null && !fileName.equals(""))
        {
            String fileNameWithout = "";
            int end = 0;
            end = fileName.lastIndexOf(".");
            fileNameWithout = fileName.substring(0, end);
            
            // 压缩包的名字，不带后缀。
            PaperBagService paperSer = new PaperBagServiceImpl();
            int maxid = paperSer.findMaxId();
            
            // 获得最大id+1=新id
            int newid = maxid + 1;
            String realPath = ServletActionContext.getRequest().getRealPath("/ipaper/upload/" + newid);// 一压缩包一个文件夹
            String targetDir = realPath;
            
            // 生成保存文件的文件名称
            targetFileName = fileNameWithout + ".zip"; 
            
            // 保存文件的 路径
            setFileDir(targetDir + "/" + targetFileName);
            
            // 建立一个目标文件
            File target = new File(targetDir, targetFileName);
            
            // 将临时文件复制到目标文件
            FileUtils.copyFile(doc, target);
            String exeres = "";
            if (fileNameCov != null && !fileNameCov.equals(""))
            {
                realPath = ServletActionContext.getRequest().getRealPath("/ipaper/upload/" + newid);
                targetDir = realPath;
                String targetFileName = fileNameCov;
                
                // 保存文件的 路径
                setFileDir(targetDir + "/" + targetFileName);
                
                // 建立一个目标文件
                target = new File(targetDir, targetFileName);
                
                // 将临时文件复制到目标文件
                FileUtils.copyFile(cov, target);
                
                PictureOperate po = new PictureOperate();
                
                
                String picfrom =
                    CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid + "/"
                        + fileNameCov;
                
                String picto =
                    CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid + "/"
                        + newid + ".jpg";
                
                po.PictureCompression(picfrom, picto, CONSTANTS.PICVALUE);
                
                String toicopng =
                    CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid + "/"
                        + newid + ".png";
                po.PicCompress(picto, toicopng, 72);
            }
            
            else
            {
                String rmcmd =
                    "sudo rm -rf " + CONSTANTS.SERVPATH
                        + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid + "/"
                        + fileNameWithout;
                exeCmd(rmcmd);
                String unzipcmd =
                    "sudo unzip -o " + CONSTANTS.SERVPATH
                        + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid + "/"
                        + fileNameWithout + ".zip -d " + CONSTANTS.SERVPATH
                        + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid + "/";
                
                exeres = exeCmd(unzipcmd);
                
                String lscmd =
                    "ls " + CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/"
                        + newid + "/" + fileNameWithout;
                
                // 取得第一张图片
                exeres = exeCmd(lscmd);
                String mycov = "";
                
                // 存放目录中所含的jpg名称
                String[] bookObjbox = exeres.split("\n");
                for (int k = 0; k < bookObjbox.length; k++)
                {
                    if (bookObjbox[k].equals("0.jpg"))
                    {
                        mycov = "0.jpg";
                    }
                    else
                    {
                        mycov = bookObjbox[0];
                    }
                }
                
                PictureOperate po = new PictureOperate();
                String picfrom =
                    CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid + "/"
                        + fileNameWithout + "/" + mycov;
                
                String picto =
                    CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid + "/"
                        + newid + ".jpg";
                
                po.PictureCompression(picfrom, picto, CONSTANTS.PICVALUE);
                
                String toicopng =
                    CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid + "/"
                        + newid + ".png";
                
                po.PicCompress(picto, toicopng, 72);
                
                
            }
            // -------------------------备份开始
            String backupCmd =
                "cp -r " + CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + newid
                    + " " + CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/ipaperUploadBackup/";
            exeCmd(backupCmd);
            
            TagService tagServ = new TagServiceImpl();// 创建标签服务
            String savebooktagId = "";// 存在books表的标签ID字段内。
            if (getTag() != null || getTag() != "")
            {
                String tagbox[] = getTag().toString().split(",");
                for (int i = 0; i < tagbox.length; i++)
                {
                    Tag tt = tagServ.searchTagByName(tagbox[i]);
                    int maxtagid = tagServ.findMaxTagId();
                    if (tt == null)
                    {
                        tt = new Tag();
                        tt.setTagId(maxtagid + 1);
                        savebooktagId = savebooktagId + "," + (maxtagid + 1);
                        tt.setTagName(tagbox[i]);
                        tt.setCategoryId("0");
                        tt.setHotNum(0);
                        tt.setCount(0);
                        tt.setFlag(0);// 0豆瓣 1 客户
                        tagServ.addTag(tt);
                    }
                    else
                    {
                        savebooktagId = savebooktagId + "," + tt.getTagId();
                    }
                }
                
            }
            
            PaperBag pg = null;
            pg = paperSer.getPaperBagById(String.valueOf(newid));
            if (pg == null)
            {
                pg = new PaperBag();
                pg.setId(newid);
                pg.setBagName(fileNameWithout);
                pg.setUploadTime(new Date());
                savebooktagId = savebooktagId.substring(1, savebooktagId.length());
                pg.setTagId(savebooktagId);
                pg.setState("0");// 0:下架状态，1:上架状态
                pg.setState_rec("加推荐");
                pg.setVersion("100100");
                pg.setSver("100");
                pg.setSummary(getContent());
                // 是否第一次上传//0:是新上传，1：编译中，2：等待中，3：单本编译结束，4：本次勾选编译结束=下载APK，5：编译出错
                pg.setInitial("0");
                pg.setCover("ipaper/upload/" + newid + "/" + newid + ".jpg");
                pg.setNum(0);
                pg.setCateId(0);
                // wl.writeTxt("上传前初始化值：0.ID:"+pg.getId()+"，1.包名："+pg.getBagName()+"，2.时间："+pg.getUploadTime()+"，3.上架状态："+pg.getState()+"，4.推荐状态："+pg.getState_rec()+"，5.版本："+pg.getVersion()+"，6.小版本："+pg.getSver()+"，7.备注："+pg.getSummary()+"，8.是否首次上传："+pg.getInitial()+"，9.图标路径："+pg.getCover()+"，10.num数："+pg.getNum()+"。");
                OperateId OPID = new OperateId();
                String unid = OPID.GenIdAndChangeWorldId(OPID.RadomGenID(), CONSTANTS.CHANGEID);
                pg.setUnionId(unid);
                paperSer.addPaperBag(pg);
            }
            Iterator iter = null;
            Iterator iterInner = null;
            Document document;
            String surl = "http://" + CONSTANTS.ADDR + "ipaper/appversion.xml";
            document = getRemoteXML(surl);
            if (document != null)
            {
                Element root = document.getRootElement();
                for (iter = root.elementIterator(); iter.hasNext();)
                {
                    Element element = (Element)iter.next();
                    String name = element.getName();
                    // 获取版本
                    if (name.equals("app"))
                    {
                        String ver = element.getText();
                        System.out.println("version=" + ver);
                        ActionContext.getContext().getSession().put("appver", ver);
                        
                    }
                }
            }
            
            return "paperbagAction";
        }
        else
        {
            return "paperbagAction";
        }
        
    }
    
    // 将网页转化为document
    public Document getRemoteXML(String url)
    {
        try
        {
            URL url1 = new URL(url);
            SAXReader reader = new SAXReader();
            return reader.read(url1);
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
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
    
    public String getBagName()
    {
        return bagName;
    }
    
    public void setBagName(String bagName)
    {
        this.bagName = bagName;
    }
    
    public void setDoc(File file)
    {
        this.doc = file;
    }
    
    public void setDocFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public void setCov(File cov)
    {
        this.cov = cov;
    }
    
    public void setCovFileName(String fileNameCov)
    {
        this.fileNameCov = fileNameCov;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public void setDocContentType(String contentType)
    {
        this.contentType = contentType;
    }
    
    public String getFileDir()
    {
        return fileDir;
    }
    
    public void setFileDir(String fileDir)
    {
        this.fileDir = fileDir;
    }
    
    public String getContentType()
    {
        return contentType;
    }
    
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
    
    public String getTargetFileName()
    {
        return targetFileName;
    }
    
    public void setTargetFileName(String targetFileName)
    {
        this.targetFileName = targetFileName;
    }
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    public String getTag()
    {
        return tag;
    }
}
