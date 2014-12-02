package com.dxs.Action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.ClassificationMangerServiceImp;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.ClassificationMangerService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 标签配置管理
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-27]
 */
public class TagConfigAction extends ActionSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4064420129315279642L;
    
    private int pageNo;
    
    private int pageSize;
    
    private int tcount;
    
    /**
     * 分类ID
     */
    private String classificationId = null;
    
    /**
     * 标签ID
     */
    private String tagId = null;
    
    /**
     * 标签ID
     */
    private String tagName = null;
    
    /**
     * 标签id列表
     */
    private List<String> tagIdList = null;
    
    private List<Tag> tagList = null;
    
    TagService tagService = new TagServiceImpl();
    
    ClassificationMangerService classificationMangerService = new ClassificationMangerServiceImp();
    
    public String execute()
        throws Exception
    {
        String sqlCondition = "";
        
        if (classificationId != null)
        {
            classificationId = (String)ActionContext.getContext().getSession().get("classificationId");
        }
        
        if (classificationId != null)
        {
            // 获取分类ID所关联到的壁纸列表
            tagIdList = classificationMangerService.getWallPaperIdListByClassificationId(classificationId);
            
            ActionContext.getContext().getSession().put("classificationId", classificationId);
        }
        
        if (tagIdList != null)
        {
            // 将壁纸ID拼接成in条件
            for (String tagId : tagIdList)
            {
                sqlCondition = sqlCondition + tagId + ",";
            }
            
            // 去除最后1个逗号
            sqlCondition = sqlCondition.substring(0, sqlCondition.length() - 1);
        }
        
        // 获取标签列表
        tagList = tagService.getTagIdListByInClassificationId(sqlCondition);
        
        return "taglist";
    }
    
    
    /**
     * 删除分配标签
     * 
     * @return
     */
    public String deleteConfigedTag()
    {
        tagService.DelByTagId(Integer.valueOf(tagId));
        return "taglist";
    }
    
    public TagService getTagService()
    {
        return tagService;
    }
    
    public void setTagService(TagService tagService)
    {
        this.tagService = tagService;
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
    
    public String getTagName()
    {
        return tagName;
    }
    
    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }
    
    public String getClassificationId()
    {
        return classificationId;
    }
    
    public void setClassificationId(String classificationId)
    {
        this.classificationId = classificationId;
    }
    
    public String getTagId()
    {
        return tagId;
    }
    
    public void setTagId(String tagId)
    {
        this.tagId = tagId;
    }
}
