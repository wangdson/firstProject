package com.dxs.Entity;

public class CompileQueue
{   
    /**
     * 编译队列id
     */
    private int id;
    
    /**
     * 壁纸包名
     */
    private int bgId;
    
    /**
     * 壁纸名字
     */
    private String bgName;


    public String getBgName()
    {
        return bgName;
    }

    public void setBgName(String bgName)
    {
        this.bgName = bgName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getBgId()
    {
        return bgId;
    }

    public void setBgId(int bgId)
    {
        this.bgId = bgId;
    }

}
