package com.dxs.Entity;

/**
 * 标签的数据结构
 * @author  姓名 工号
 * @version  [版本号, 2014-6-27]
 */
public class Tag
{   
    /**
     * 标签ID
     */
    private int tagId;
    
    /**
     *  标签名
     */
    private String tagName;
    
    /**
     * 分类ID
     */
    private String categoryId;
    
    private int hotNum;
    
    private int count;
    
    /**
     * 区别是豆瓣的标签还是自己的标签
     */
    private int flag;
    
    public int getTagId()
    {
        return tagId;
    }
    
    public void setTagId(int tagId)
    {
        this.tagId = tagId;
    }
    
    public String getTagName()
    {
        return tagName;
    }
    
    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }
    
    public String getCategoryId()
    {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }
    
    public void setHotNum(int hotNum)
    {
        this.hotNum = hotNum;
    }
    
    public int getHotNum()
    {
        return hotNum;
    }
    
    public void setCount(int count)
    {
        this.count = count;
    }
    
    public int getCount()
    {
        return count;
    }
    
    public void setFlag(int flag)
    {
        this.flag = flag;
    }
    
    public int getFlag()
    {
        return flag;
    }
}
