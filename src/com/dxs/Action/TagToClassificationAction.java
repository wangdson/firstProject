package com.dxs.Action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.ClassificationMangerServiceImp;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.ClassificationMangerService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 为分类配置壁纸包
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-7-1]
 */
public class TagToClassificationAction extends ActionSupport implements ServletRequestAware
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
    
    private List<Tag> tagList = null;
    
    private int pageNo;
    
    private int pageSize;
    
    private int tcount;
    
    ClassificationMangerService classificationMangerService = new ClassificationMangerServiceImp();
    
    TagService tagService = new TagServiceImpl();
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    public String execute()
        throws Exception
    {   
        String classificationId = (String)ActionContext.getContext().getSession().get("classificationId");
        
        // 获取已经分类的壁纸ID列表
        List<String> ClassificationwallPaperIdList = null;
        
        // sql查询条件
        String sqlCondition = "";
        
        // 获取
        if (classificationId != null)
        {
            // 获取分类标签关联列表获取数据
            ClassificationwallPaperIdList =
                classificationMangerService.getWallPaperIdListByClassificationId(classificationId);
        }
        
        if (ClassificationwallPaperIdList != null && !ClassificationwallPaperIdList.isEmpty())
        {
            for (String tagId : ClassificationwallPaperIdList)
            {
                sqlCondition = sqlCondition + tagId + ",";
            }
            
            //去除最后1个逗号
            sqlCondition = sqlCondition.substring(0,sqlCondition.length()-1);
        }
        
        // 获取不在标签列表中的标签项
        tagList = tagService.getTagIdListByNotInClassificationId(sqlCondition);
        
        return "tagList";
    }
    
    /**
     * 为分类标签
     * 
     * @return
     */
    public String classificationConfigOfTag()
    {
        String[] checks = request.getParameterValues("ck");
        
        String classificationId = (String)ActionContext.getContext().getSession().get("classificationId");
        
        if (checks != null)
        {
            for (String ck : checks)
            {
                // 将分类插入到分类壁纸列表中去
                classificationMangerService.insertToCategoryTag(classificationId, ck);
            }
        }
        return "tagList";
    }
    
    /**
     * 获取分类相关的标签列表
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

    public List<Tag> getTagList()
    {
        return tagList;
    }

    public void setTagList(List<Tag> tagList)
    {
        this.tagList = tagList;
    }
}
