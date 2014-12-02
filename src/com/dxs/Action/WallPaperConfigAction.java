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
 * 壁纸配置的action
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-30]
 */
public class WallPaperConfigAction extends ActionSupport implements ServletRequestAware
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -456155550016248291L;
    
    /**
     * 模式，用来区分是分类还是标签
     */
    private String mode = null;
    
    /**
     * 标签ID
     */
    private String tagId = null;
    
    /**
     * 分类ID
     */
    private String classificationId = null;
    
    /**
     * 选中的壁纸列表
     */
    private List<PaperBag> paperBagList = null;
    
    /**
     * 壁纸ID
     */
    private String wallPaperId = null;
    
    /**
     * 壁纸的service类
     */
    PaperBagService pgServ = new PaperBagServiceImpl();
    
    /**
     * 壁纸包发servies
     */
    PaperBagService PaperBagService = new PaperBagServiceImpl();
    
    ClassificationMangerService classificationMangerService = new ClassificationMangerServiceImp();
    
    private HttpServletRequest request;
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    public String execute()
        throws Exception
    {
        List<String> wallPaperIdList = null;
        
        String sqlCondition = "";
        
        if (classificationId == null)
        {
            classificationId = (String)ActionContext.getContext().getSession().get("classificationId");
        }
        
        if (classificationId != null)
        {
            // 获取分类ID所关联到的壁纸列表
            wallPaperIdList = classificationMangerService.getWallPaperIdListByClassificationId(classificationId);
            
            ActionContext.getContext().getSession().put("classificationId", classificationId);
        }
        
        if (wallPaperIdList != null)
        {
            // 将壁纸ID拼接成in条件
            for (String wallPaperId : wallPaperIdList)
            {
                sqlCondition = sqlCondition + wallPaperId + ",";
            }
            
            // 去除最后1个逗号
            if (sqlCondition != null)
            {
                sqlCondition = sqlCondition.substring(0, sqlCondition.length() - 1);
            }
        }
        
        paperBagList = PaperBagService.getPaperBagByInCategoryPaperIdList(sqlCondition);
        
        return "paperList";
    }
    
    /**
     * 删除分配的壁纸
     * 
     * @return
     */
    public String deleteConfigCategoryPaper()
    {
        if (classificationId == null)
        {
            classificationId = (String)ActionContext.getContext().getSession().get("classificationId");
        }
        
        if (classificationId != null)
        {
            classificationMangerService.deleteCategoryPaperByPaperId(wallPaperId);
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
     * 为标签分配壁纸资源
     * 
     * @return
     */
    public String getTagWallpaperList()
    {
        mode = "tag";
        String sqlCondition = "";
        if (tagId == null)
        {
            tagId = (String)ActionContext.getContext().getSession().get("tagId");
        }
        
        if (tagId != null)
        {
            ActionContext.getContext().getSession().put("tagId", tagId);
            
            // 从标签壁纸关联表中获取已经分类的壁纸列表
            List<String> wallpaperIdList = classificationMangerService.getWallPaperIdListByTagId(tagId);
            
            // 根据壁纸id拼接出查询分配的sql条件
            for (String wallPaperId : wallpaperIdList)
            {
                sqlCondition = sqlCondition + wallPaperId + ",";
            }
            
            if (!sqlCondition.equals(""))
            {
                // 去除最后1个逗号
                sqlCondition = sqlCondition.substring(0, sqlCondition.length() - 1);
            }
        }
        
        // 查询条件存在于壁纸列表中的数据
        paperBagList = PaperBagService.getPaperBagByInCategoryPaperIdList(sqlCondition);
        
        return "paperList";
    }
    
    public String getTagId()
    {
        return tagId;
    }
    
    public void setTagId(String tagId)
    {
        this.tagId = tagId;
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
    
    public String getWallPaperId()
    {
        return wallPaperId;
    }
    
    public void setWallPaperId(String wallPaperId)
    {
        this.wallPaperId = wallPaperId;
    }
    
    public String getMode()
    {
        return mode;
    }
    
    public void setMode(String mode)
    {
        this.mode = mode;
    }
}
