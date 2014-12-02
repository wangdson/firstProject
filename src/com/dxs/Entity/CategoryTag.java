package com.dxs.Entity;

/**
 * 分类和标签关联模型
 * @author  姓名 工号
 * @version  [版本号, 2014-6-30]
 */
public class CategoryTag
{    
    /**
     * 分类id
     */
    String categoryId = null;
    
    /**
     * 标签Id
     */
    String tagId = null;

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
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
