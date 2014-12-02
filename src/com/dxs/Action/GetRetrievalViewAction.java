package com.dxs.Action;

import java.util.List;

import com.dxs.Entity.Retrieval;
import com.dxs.Service.Impl.RetrievalServiceImpl;
import com.dxs.Service.Intf.RetrievalService;
import com.dxs.Util.CheckUser;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetRetrievalViewAction extends ActionSupport
{
    
    /**
     * 提供管理员查看检索统计
     */
    private static final long serialVersionUID = 1L;
    
    private List<Retrieval> retrievalList;
    
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
    
    public List<Retrieval> getRetrievalList()
    {
        return retrievalList;
    }
    
    public void setRetrievalList(List<Retrieval> retrievalList)
    {
        this.retrievalList = retrievalList;
    }
    
    public String execute()
        throws Exception
    {
        if (CheckUser.ck_class())
        {
            RetrievalService retrievalSer = new RetrievalServiceImpl();
            if (pageNo == 0)
            {
                setPageNo(1);
            }
            if (pageSize == 0)
            {
                setPageSize(10);
            }
            tcount = retrievalSer.findRetrievalCount();
            ActionContext.getContext().getSession().put("count", tcount);
            ActionContext.getContext().getSession().put("totalPage", (tcount+pageSize-1) / pageSize);
            List<Retrieval> retrievalList = null;
            if (pageNo == ((tcount+pageSize-1) / pageSize))
            {
                retrievalList = retrievalSer.getlastRetrieval(pageNo, tcount - ((pageNo - 1) * pageSize),pageSize);
            }
            else
            {
                retrievalList = retrievalSer.getlastRetrieval(pageNo, pageSize);
            }
            ActionContext.getContext().getSession().put("retrievalList", retrievalList);
            ActionContext.getContext().getSession().put("pageNo", pageNo);
            ActionContext.getContext().getSession().put("pageSize", pageSize);
            ActionContext.getContext().getSession().put("ret", null);
            ActionContext.getContext().getSession().put("result", null);
            setRetrievalList(retrievalList);
            return "retrievalView";
            
        }
        else
        {
            return "error";
        }
        
    }
    
}
