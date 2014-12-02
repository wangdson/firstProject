package com.dxs.Action;

import java.util.List;

import com.dxs.Entity.PushInfo;
import com.dxs.Service.Impl.PushServiceImpl;
import com.dxs.Service.Intf.PushService;
import com.dxs.Util.CheckUser;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetPushMsgViewAction extends ActionSupport
{
    
    /**
     * 提供管理员查看检索统计
     */
    private static final long serialVersionUID = 1L;
    
    private List<PushInfo> pushList;
    
    private int pageNo;
    
    private int pageSize;
    
    private int tcount;
    
    public int getTcount()
    {
        return tcount;
    }
    
    public void setTcount(int tcount)
    {
        this.tcount = tcount;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public List<PushInfo> getPushList()
    {
        return pushList;
    }

    public void setPushList(List<PushInfo> pushList)
    {
        this.pushList = pushList;
    }

    public String execute()
        throws Exception
    {
        if (CheckUser.ck_class())
        {
            PushService service=new PushServiceImpl();
            if (pageNo == 0)
            {
                setPageNo(1);
            }
            if (pageSize == 0)
            {
                setPageSize(10);
            }
            tcount = service.findPushInfoCount();
            ActionContext.getContext().getSession().put("count", tcount);
            ActionContext.getContext().getSession().put("totalPage", (tcount+pageSize-1) / pageSize);
            List<PushInfo> pushList = null;
            if (pageNo == (tcount+pageSize-1) / pageSize)
            {
                pushList = service.getlastPushInfo(pageNo, tcount - ((pageNo - 1) * pageSize),pageSize);
            }
            else
            {
                pushList = service.getlastPushInfo(pageNo, pageSize);
            }
            setPushList(pushList);
            
            if (ActionContext.getContext().getSession().get("count1") == null || ActionContext.getContext().getSession().get("count1").equals("0"))
            {
                ActionContext.getContext().getSession().put("count1", 0);
            }
            else
            {
                ActionContext.getContext().getSession().put("count1", (Integer)ActionContext.getContext().getSession().get("count1")-1); 
            }
            
            return "pushMsgView";
            
        }
        else
        {
            return "error";
        }
        
    }
    
}
