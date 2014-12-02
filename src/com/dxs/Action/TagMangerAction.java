package com.dxs.Action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 标签配置管理类
 * @author  姓名 工号
 * @version  [版本号, 2014-7-4]
 */
public class TagMangerAction extends ActionSupport
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7512575990412626842L;
    
    /**
     * 标签列表
     */
    private List<Tag> taglist;
    
    /**
     * 标签名
     */
    private String tagName = null;
    
    /**
     * 标签ID
     */
    private String tagId = null;
    
    private int pageNo;
    
    private int pageSize;
    
    private int tcount;
    
    /**
     * 标签管理类
     */
    TagService tagService = new TagServiceImpl();
    
    public String execute() throws Exception
    {   
        //获取标签列表
        taglist = tagService.getAllTagList();
        return "tagList";
    }
    
    /**
     * 添加标签
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    public String newAddTag()
        throws UnsupportedEncodingException
    {
        if (tagName != null)
        {
            tagName = new String(tagName.getBytes("ISO-8859-1"), "utf-8");
            
            // 查看该类别是否存在，不存在才添加
            Tag tag = tagService.searchTagByName(tagName.trim());
            
            if (tag == null)
            {
                // 获得类别最大ID
                int maxid = tagService.findMaxTagId();
                String classificationId = (String)ActionContext.getContext().getSession().get("classificationId");
                
                tag = new Tag();
                tag.setTagId(maxid + 1);
                tag.setTagName(tagName.trim());
                tag.setCategoryId(classificationId);
                tag.setHotNum(0);
                tag.setCount(0);
                
                // 0豆瓣 1 客户
                tag.setFlag(0);
                
                // add类别方法
                tagService.addTag(tag);
            }
        }
        
        try
        {
            execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return "tagList";
    }
    
    /**
     * 删除标签
     * 
     * @return
     */
    public String deleteTag()
    {
        tagService.DelByTagId(Integer.valueOf(tagId));
        
        try
        {
            execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return "tagList";
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

    public List<Tag> getTaglist()
    {
        return taglist;
    }

    public void setTaglist(List<Tag> taglist)
    {
        this.taglist = taglist;
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setTagName(String tagName)
    {
        this.tagName = tagName;
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
