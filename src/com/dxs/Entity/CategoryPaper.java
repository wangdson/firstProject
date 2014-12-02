package com.dxs.Entity;

/**
 * 分类与壁纸关联模型
 * @author  姓名 工号
 * @version  [版本号, 2014-6-30]
 */
public class CategoryPaper
{
    /**
     * 分类id
     */
    String categoryId = null;
    
    /**
     * 壁纸ID
     */
    String wallPaperId = null;

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
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
