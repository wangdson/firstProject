package com.dxs.Entity;

/**
 * 短链接类
 * @author  姓名 工号
 * @version  [版本号, 2014-6-17]
 */
public class ShortLink
{  
    private int id;
    
    private String name;
    
    private String shortName;
    
    /**
     * 长链接
     */
    private String longUrl;
    
    /**
     * 短链接
     */
    private String shortUrl;
    
    private int flag;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getLongUrl()
    {
        return longUrl;
    }
    
    public void setLongUrl(String longUrl)
    {
        this.longUrl = longUrl;
    }
    
    public String getShortUrl()
    {
        return shortUrl;
    }
    
    public void setShortUrl(String shortUrl)
    {
        this.shortUrl = shortUrl;
    }
    
    public void setFlag(int flag)
    {
        this.flag = flag;
    }
    
    public int getFlag()
    {
        return flag;
    }
    
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    public String getShortName()
    {
        return shortName;
    }
}
