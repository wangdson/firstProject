package com.dxs.Entity;

import java.io.Serializable;
import java.util.Date;

public class PaperBag  implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int id;
    
    private String bagName;
    
    private Date uploadTime;
    
    /**
     * 上下架
     */
    private String state;
    
    /**
     * 推荐否
     */
    private String state_rec;
    
    private String version;
    
    private String sver;
    
    /**
     * 备注，简介
     */
    private String summary;
    
    /**
     * 是否第一次上传//0:是新上传，1：编译中，2：等待中，3：单本编译结束，4：本次勾选编译结束=下载APK，5：编译出错
     */
    private String initial;
    
    /**
     * 安装图标
     */
    private String cover;
    
    private int num;// 预留下载or安装量
    
    /**
     * 推荐时间
     */
    private Date recTime;
    
    private int cateId;
    
    private String tagId;
    
    /**
     * 转世ID
     */
    private String unionId;
    
    public String getUnionId()
    {
        return unionId;
    }
    
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }
    
    public boolean equals(Object obj)
    {
        if (obj instanceof PaperBag)
        {
            PaperBag u = (PaperBag)obj;
            if (this.bagName == u.bagName)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return super.equals(obj);
    }
    
    private String totalpage;
    
    public String getTotalpage()
    {
        return totalpage;
    }
    
    public void setTotalpage(String totalpage)
    {
        this.totalpage = totalpage;
    }
    
    public String getState_rec()
    {
        return state_rec;
    }
    
    public void setState_rec(String stateRec)
    {
        state_rec = stateRec;
    }
    
    public String getCover()
    {
        return cover;
    }
    
    public void setCover(String cover)
    {
        this.cover = cover;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setBagName(String bagName)
    {
        this.bagName = bagName;
    }
    
    public String getBagName()
    {
        return bagName;
    }
    
    public void setUploadTime(Date uploadTime)
    {
        this.uploadTime = uploadTime;
    }
    
    public Date getUploadTime()
    {
        return uploadTime;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public String getVersion()
    {
        return version;
    }
    
    public void setSver(String sver)
    {
        this.sver = sver;
    }
    
    public String getSver()
    {
        return sver;
    }
    
    public void setSummary(String summary)
    {
        this.summary = summary;
    }
    
    public String getSummary()
    {
        return summary;
    }
    
    public void setInitial(String initial)
    {
        this.initial = initial;
    }
    
    public String getInitial()
    {
        return initial;
    }
    
    public void setNum(int num)
    {
        this.num = num;
    }
    
    public int getNum()
    {
        return num;
    }
    
    public void setRecTime(Date recTime)
    {
        this.recTime = recTime;
    }
    
    public Date getRecTime()
    {
        return recTime;
    }
    
    public void setCateId(int cateId)
    {
        this.cateId = cateId;
    }
    
    public int getCateId()
    {
        return cateId;
    }
    
    public void setTagId(String tagId)
    {
        this.tagId = tagId;
    }
    
    public String getTagId()
    {
        return tagId;
    }
    
}
