package com.dxs.Action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.ClassificationMangerServiceImp;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.ClassificationMangerService;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 为分类配置壁纸包
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-7-1]
 */
public class WallpaperToClassificationAction extends ActionSupport implements ServletRequestAware
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2406952507113230095L;
    
    private HttpServletRequest request;
    
    /**
     * 分类ID
     */
    private String classificationId = null;
    
    /**
     * 标签ID
     */
    private String tagId = null;
    
    /**
     * 壁纸id
     */
    private String wallPaperId = null;
    
    private List<PaperBag> paperBagList = null;
    
    private int pageNo;
    
    private int pageSize;
    
    private int tcount;
    
    private String mode = null;
    
    ClassificationMangerService classificationMangerService = new ClassificationMangerServiceImp();
    
    PaperBagService PaperBagService = new PaperBagServiceImpl();
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    public String execute()
        throws Exception
    {
        if (mode == null)
        {
            mode = (String)ActionContext.getContext().getSession().get("mode");
        }
        
        else
        {
            ActionContext.getContext().getSession().put("mode", mode);
        }
        
        // 为标签模式
        if (mode.equals("tag"))
        {
            getPaperBagListByTag();
        }
        
        // 分类模式
        else
        {
            getPaperBagListByCategory();
        }
        
        return "paperList";
    }
    
    /**
     * 根据标签获取壁纸列表
     * 
     * @return
     */
    private List<PaperBag> getPaperBagListByTag()
    {
        // 获取已经分类的壁纸ID列表
        List<String> tagWallPaperIdList = null;
        
        // sql查询条件
        String sqlCondition = "";
        
        if (tagId == null)
        {
            tagId = (String)ActionContext.getContext().getSession().get("tagId");
        }
        
        // 获取
        if (tagId != null)
        {
            // 获取分类ID所关联到的壁纸列表
            tagWallPaperIdList = classificationMangerService.getWallPaperIdListByTagId(tagId);
        }
        
        if (tagWallPaperIdList != null && !tagWallPaperIdList.isEmpty())
        {
            for (String wallPaperId : tagWallPaperIdList)
            {
                sqlCondition = sqlCondition + wallPaperId + ",";
            }
            
            // 去除最后1个逗号
            sqlCondition = sqlCondition.substring(0, sqlCondition.length() - 1);
        }
        
        // 获取配置列表
        paperBagList = PaperBagService.getPaperBagByNotInCategoryPaperIdList(sqlCondition);
        
        return paperBagList;
    }
    
    /**
     * 分类模式获取壁纸列表
     */
    private List<PaperBag> getPaperBagListByCategory()
    {
        // 获取已经分类的壁纸ID列表
        List<String> ClassificationwallPaperIdList = null;
        
        // sql查询条件
        String sqlCondition = "";
        
        if (classificationId == null)
        {
            classificationId = (String)ActionContext.getContext().getSession().get("classificationId");
        }
        
        // 获取
        if (classificationId != null)
        {
            // 获取分类ID所关联到的壁纸列表
            ClassificationwallPaperIdList =
                classificationMangerService.getWallPaperIdListByClassificationId(classificationId);
        }
        
        if (ClassificationwallPaperIdList != null && !ClassificationwallPaperIdList.isEmpty())
        {
            for (String wallPaperId : ClassificationwallPaperIdList)
            {
                sqlCondition = sqlCondition + wallPaperId + ",";
            }
            
            // 去除最后1个逗号
            sqlCondition = sqlCondition.substring(0, sqlCondition.length() - 1);
        }
        
        // 获取配置列表
        paperBagList = PaperBagService.getPaperBagByNotInCategoryPaperIdList(sqlCondition);
        
        return paperBagList;
    }
    
    /**
     * 为分类配置壁纸包
     * 
     * @return
     */
    public String classificationConfigOfWallPaper()
    {
        String[] checks = request.getParameterValues("ck");
        
        String classificationId = (String)ActionContext.getContext().getSession().get("classificationId");
        
        if (checks != null)
        {
            for (String ck : checks)
            {
                // 将分类插入到分类壁纸列表中去
                classificationMangerService.insertToCategoryPaper(classificationId, ck);
            }
        }
        return "paperList";
    }
    
    /**
     * 为标签分配壁纸
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String wallPaperConfig()
    {
        if (tagId == null)
        {
            tagId = (String)ActionContext.getContext().getSession().get("tagId");
        }
        
        // 将分类插入到分类壁纸列表中去
        if (tagId != null && wallPaperId != null)
        {
            classificationMangerService.insertToTagPaper(tagId, wallPaperId);
        }
        
        try
        {
            execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "paperList";
    }
    
    /**
     * 获取分类相关的标签列表
     * 
     * @return
     */
    public String getClassificationOfTagList()
    {
        return "tagList";
    }
    
    public String getClassificationId()
    {
        return classificationId;
    }
    
    public void setClassificationId(String classificationId)
    {
        this.classificationId = classificationId;
    }
    
    public List<PaperBag> getPaperBagList()
    {
        return paperBagList;
    }
    
    public void setPaperBagList(List<PaperBag> paperBagList)
    {
        this.paperBagList = paperBagList;
    }
    
    public HttpServletRequest getRequest()
    {
        return request;
    }
    
    public void setRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getTcount()
    {
        return tcount;
    }
    
    public void setTcount(int tcount)
    {
        this.tcount = tcount;
    }
    
    public String getMode()
    {
        return mode;
    }
    
    public void setMode(String mode)
    {
        this.mode = mode;
    }
    
    public String getTagId()
    {
        return tagId;
    }
    
    public void setTagId(String tagId)
    {
        this.tagId = tagId;
    }
    
    public String getWallPaperId()
    {
        return wallPaperId;
    }
    
    public void setWallPaperId(String wallPaperId)
    {
        this.wallPaperId = wallPaperId;
    }
}
