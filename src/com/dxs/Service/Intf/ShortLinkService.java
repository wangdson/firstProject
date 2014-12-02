package com.dxs.Service.Intf;

import com.dxs.Entity.ShortLink;
import com.dxs.Entity.ShortLinkComplement;

public interface ShortLinkService
{
    /**
     * 添加短链接
     */
    public abstract int addObject(ShortLink sl);
    
    /**
     * 根据ID删除短链接
     */
    public abstract int DelById(int id);
    
    public abstract ShortLink getShortLinkByName(String name, int flag);
    
    public abstract ShortLink getShortLinkByName(String shortname);
    
    /**
     * 找到短连接最大编号
     */
    public abstract int findMaxId();
    
    /**
     * 获取需要补充锻炼接的记录
     */
    public abstract ShortLinkComplement getOneShortLinkRecordComplement();
    
    /**
     * 添加需要补充短链接的壁纸ID
     */
    public abstract int insertShortLinkComplement(String wallPaperId);
    
    /**
     * 删除短连接表的记录
     */
    public abstract int deleteShortLinkComplement(String wallPaperId);
}
