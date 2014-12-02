package com.dxs.Entity;

/**
 * 短链接补全列表
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-20]
 */
public class ShortLinkComplement
{   
    /**
     * id
     */
    int id;
    
    /**
     * 壁纸id
     */
    String wallpaperId;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getWallpaperId()
    {
        return wallpaperId;
    }
    
    public void setWallpaperId(String wallpaperId)
    {
        this.wallpaperId = wallpaperId;
    }
}
