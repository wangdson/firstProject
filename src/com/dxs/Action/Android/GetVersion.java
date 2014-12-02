package com.dxs.Action.Android;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Variable;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 获取版本号的action
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-20]
 */
public class GetVersion extends ActionSupport
{
    
    /**
     * 用户打开软件 请求版本号
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    public String execute()
        throws Exception
    {
        // 原始ID
        String wallpaperId = null;
        
        PaperBagService bookServ = new PaperBagServiceImpl();
        
        PackageInfo packageInfo = getPackageInfo(getId());
        
        // 匹配上了，是转世后的ID,则要提取原始ID
        if (packageInfo != null)
        {
            wallpaperId = packageInfo.getWallPaperPackageId();
        }
        
        else
        {
            wallpaperId = getId();
        }
        
        // 根据ID获取版本信息
        PaperBag book = bookServ.getPaperBagById(wallpaperId);
        
        Variable var = new Variable();
        
        if (null != book)
        {
            // 获取版本号
            var.setVar(String.valueOf(book.getVersion()));
            
            // 放置转世ID
            var.setVar_union(book.getUnionId());
            
            // 放壁纸ID
            var.setVar_id(String.valueOf(book.getId()));
        }
        
        JSONObject json = JSONObject.fromObject(var);
        Map<String, Object> m;
        m = ActionContext.getContext().getSession();
        m.put("book_ver", json.toString());
        return "GetVersion";
    }
    
    /**
     * 获取壁纸包的包信息
     * 
     * @param spackage
     * @return
     */
    private PackageInfo getPackageInfo(String spackage)
    {
        PackageInfo packageInfo = null;
        if (spackage == null)
        {
            return null;
        }
        Pattern p = Pattern.compile("(\\d+)_(\\d+)");
        Matcher m = p.matcher(spackage);
        if (m.matches())
        {
            packageInfo = new PackageInfo();
            packageInfo.setWallPaperPackageId(m.group(1));
            packageInfo.setWallPaperPackageUnid(m.group(2));
        }
        
        else
        {
            return null;
        }
        
        return packageInfo;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    class PackageInfo
    {
        String wallPaperPackageId = null;
        
        String wallPaperPackageUnid = null;
        
        public String getWallPaperPackageId()
        {
            return wallPaperPackageId;
        }
        
        public void setWallPaperPackageId(String wallPaperPackageId)
        {
            this.wallPaperPackageId = wallPaperPackageId;
        }
        
        public String getWallPaperPackageUnid()
        {
            return wallPaperPackageUnid;
        }
        
        public void setWallPaperPackageUnid(String wallPaperPackageUnid)
        {
            this.wallPaperPackageUnid = wallPaperPackageUnid;
        }
        
    }
}
