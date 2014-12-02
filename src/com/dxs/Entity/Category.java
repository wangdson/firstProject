package com.dxs.Entity;

/**
 * 分类Model
 * @author  姓名 工号
 * @version  [版本号, 2014-6-25]
 */
public class Category
{   
    /**
     * 分类ID
     */
    private int categoryId;
    
    /**
     * 分类名
     */
    private String categoryName;
    
    /**
     * 标签ID
     */
    private String tagId;
    
    /**
     * 壁纸ID
     */
    private String wallPaperId;
    
    public int getCategoryId()
    {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }
    
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
    
    public String getCategoryName()
    {
        return categoryName;
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
