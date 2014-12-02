package com.dxs.Action;

import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 删除壁纸的action
 * @author  姓名 工号
 * @version  [版本号, 2014-6-24]
 */
public class Deldata extends ActionSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7556274720132233916L;
    
    private String id;
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getId()
    {
        return id;
    }
    
    public String execute()
        throws Exception
    {
        PaperBagService pgServ = new PaperBagServiceImpl();
        pgServ.DelById(getId());
        return "paperbagAction";
    }
}
