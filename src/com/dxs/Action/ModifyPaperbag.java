package com.dxs.Action;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.dxs.Util.CONSTANTS;
import com.dxs.Util.IdChangeWorldIdCombination;
import com.dxs.Util.OperateId;
import com.dxs.Util.PictureOperate;
import com.opensymphony.xwork2.ActionSupport;

public class ModifyPaperbag extends ActionSupport
{
    /**
     * 图书详细信息，按了确认修改后提交更改数据库
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String bagName;
    
    private String content;
    
    private String tag;
    
    private String bookVer;
    
    private String appVer;
    
    private String uptime;
    
    private String recommendState;
    
    private String state;
    
    // 上传封面
    private File diy;
    
    private File repub;// 上传epub
    
    private String fileDir;
    
    private String fileName;// 上传封面名
    
    private String fileNameEp;// 替换epub
    
    private String unionChangeId;
    
    // 这个set方法的命名 一定要跟着“private File diy”这个的名称来命名，才能获得fileName
    public void setDiyFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public void setRepubFileName(String fileNameEp)
    {
        this.fileNameEp = fileNameEp;
    }
    
    @SuppressWarnings("deprecation")
    public String execute()
        throws Exception
    {
        // 1.修改数据库，2上传封面
        if (fileNameEp != null && !fileNameEp.equals(""))
        {
            // --------------------------------------------------------------------------------------上传包包开始
            String fileNameWithout = "";
            int end = 0;
            end = fileNameEp.lastIndexOf(".");
            fileNameWithout = fileNameEp.substring(0, end);// 压缩包的名字，不带后缀。
            // 获得upload的绝对路径//ibook/upload/xxxx
            String realPath = ServletActionContext.getRequest().getRealPath("/ipaper/upload/" + getId());
            String targetDir = realPath;
            // 生成保存文件的文件名称
            String targetFileName = fileNameWithout + ".zip"; // generateFileName(fileName);
            // 保存文件的 路径
            setFileDir(targetDir + "\\" + targetFileName);
            // 建立一个目标文件
            File target = new File(targetDir, targetFileName);
            // 将临时文件复制到目标文件
            FileUtils.copyFile(repub, target);
            // --------------------------------------------------------------------------------------上传包包结束，开始获取包内第一张图片。如果下面有自定义上传图片会覆盖
            String rmcmd =
                "sudo rm -rf " + CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/"
                    + getId() + "/" + fileNameWithout;
            exeCmd(rmcmd);
            // 解压缩图片包到ID目录
            String unzipcmd =
                "sudo unzip -o " + CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/"
                    + getId() + "/" + fileNameWithout + ".zip -d " + CONSTANTS.SERVPATH
                    + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + getId() + "/";
            exeCmd(unzipcmd);
            // 取得第一张图片(目前改为取其中名字为0.jpg的图片)
            String lscmd =
                "ls " + CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + getId()
                    + "/" + fileNameWithout;
            String exeres = exeCmd(lscmd);
            String mycov = "";
            String[] bookObjbox = exeres.split("\n");// 存放目录中所含的jpg名称
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
                CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + getId() + "/"
                    + fileNameWithout + "/" + mycov;
            String picto =
                CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + getId() + "/"
                    + getId() + ".jpg";
            po.PictureCompression(picfrom, picto, CONSTANTS.PICVALUE);
            String convertcmd =
                CONSTANTS.CONVERT + picto + " " + CONSTANTS.SERVPATH
                    + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + getId() + "/" + getId()
                    + ".png";
            exeCmd(convertcmd);// 转换图片命令
            
        }
        if (fileName != null && !fileName.equals(""))
        {
            // --------------------------------------------------------------------------------------上传封面开始
            String realPath = ServletActionContext.getRequest().getRealPath("/ipaper/upload/" + getId());
            String targetDir = realPath;
            // 生成保存文件的文件名称
            String targetFileName = fileName; // generateFileName(fileName);
            // 保存文件的 路径
            setFileDir(targetDir + "\\" + targetFileName);
            // 建立一个目标文件
            File target = new File(targetDir, targetFileName);
            // 将临时文件复制到目标文件
            FileUtils.copyFile(diy, target);
            
            PictureOperate po = new PictureOperate();
            String picfrom =
                CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + getId() + "/"
                    + fileName;
            String picto =
                CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + getId() + "/"
                    + getId() + ".jpg";
            po.PictureCompression(picfrom, picto, CONSTANTS.PICVALUE);
            String convertcmd =
                CONSTANTS.CONVERT + picto + " " + CONSTANTS.SERVPATH
                    + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + getId() + "/" + getId()
                    + ".png";
            exeCmd(convertcmd);// 转换图片命令
            
        }
        
        String backupCmd =
            "cp -r " + CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/upload/" + getId()
                + " " + CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/ipaperUploadBackup/";
        exeCmd(backupCmd);
        
        TagService tagServ = new TagServiceImpl();// 创建标签服务
        String savebooktagId = "";// 存在paperbag表的标签tagID字段内。
        if (getTag() != null || getTag() != "")
        {
            String tagbox[] = getTag().toString().split(",");
            for (int i = 0; i < tagbox.length; i++)
            {
                Tag tt = tagServ.searchTagByName(tagbox[i]);
                if (tt == null)
                {
                    int maxid = tagServ.findMaxTagId();
                    // wl.writeTxt("编辑图书详细页面中，标签有所改动，有新添加标签，新ID是="+(maxid+1));
                    tt = new Tag();
                    tt.setTagId(maxid + 1);
                    savebooktagId = savebooktagId + "," + (maxid + 1);
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
        
        PaperBag pgInfo = new PaperBag();
        PaperBagService pgServ = new PaperBagServiceImpl();
        pgInfo = pgServ.getPaperBagById(getId());
        pgInfo.setBagName(getBagName());
        pgInfo.setSver(getBookVer());
        
        savebooktagId = savebooktagId.substring(1, savebooktagId.length());
        
        pgInfo.setTagId(savebooktagId);
        pgInfo.setSummary(getContent());
        
        OperateId OPID = new OperateId();
        IdChangeWorldIdCombination value = OPID.ParseIdAndChangeWorldId(pgInfo.getUnionId());
        
        if (null != getUnionChangeId() && !"".equals(getUnionChangeId()))
        {
            String unionNewid = OPID.GenIdAndChangeWorldId(value.getId(), getUnionChangeId());
            pgInfo.setUnionId(unionNewid);
        }
        
        pgServ.modifyById(String.valueOf(pgInfo.getId()), pgInfo);
        
        
        return "paperbagAction";
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
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getBagName()
    {
        return bagName;
    }
    
    public void setBagName(String bagName)
    {
        this.bagName = bagName;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public File getRepub()
    {
        return repub;
    }
    
    public void setRepub(File repub)
    {
        this.repub = repub;
    }
    
    public String getFileDir()
    {
        return fileDir;
    }
    
    public void setFileDir(String fileDir)
    {
        this.fileDir = fileDir;
    }
    
    public File getDiy()
    {
        return diy;
    }
    
    public void setDiy(File diy)
    {
        this.diy = diy;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getBookVer()
    {
        return bookVer;
    }
    
    public void setBookVer(String bookVer)
    {
        this.bookVer = bookVer;
    }
    
    public String getAppVer()
    {
        return appVer;
    }
    
    public void setAppVer(String appVer)
    {
        this.appVer = appVer;
    }
    
    public String getUptime()
    {
        return uptime;
    }
    
    public void setUptime(String uptime)
    {
        this.uptime = uptime;
    }
    
    public String getRecommendState()
    {
        return recommendState;
    }
    
    public void setRecommendState(String recommendState)
    {
        this.recommendState = recommendState;
    }
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    public String getTag()
    {
        return tag;
    }
    
    public String getUnionChangeId()
    {
        return unionChangeId;
    }
    
    public void setUnionChangeId(String unionChangeId)
    {
        this.unionChangeId = unionChangeId;
    }
}
