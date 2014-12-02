package com.dxs.Action;

import java.util.List;


import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Util.CheckUser;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetPaperViewAction extends ActionSupport{

    /**
     * 提供管理员查看当前最新上架?flag=new，热门主打?flag=hot，编辑推荐?flag=rec
     */
    private static final long serialVersionUID = 1L;
    private String flag;
    private List<PaperBag> paperlist;
    
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
    public List<PaperBag> getPaperlist() {
        return paperlist;
    }
    public void setPaperlist(List<PaperBag> paperlist) {
        this.paperlist = paperlist;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String execute() throws Exception {
        if(CheckUser.ck_class()){
            PaperBagService paperServ=new PaperBagServiceImpl();
            if (pageNo == 0)
            {
                setPageNo(1);
            }
            if (pageSize == 0)
            {
                setPageSize(10);
            }
            setFlag(flag);
            if (getFlag().equals("new")) 
            {
                tcount = paperServ.findstandCount();
                ActionContext.getContext().getSession().put("count", tcount);
                ActionContext.getContext().getSession().put("totalPage", (tcount+pageSize-1)/pageSize);
                List<PaperBag> newpaper= null;
                if (pageNo==((tcount+pageSize-1)/pageSize))
                {
                    newpaper = paperServ.getlastPaper(pageNo,tcount-((pageNo-1)*pageSize),pageSize);
                }
                else
                {
                    newpaper = paperServ.getlastPaper(pageNo,pageSize);
                }
                setPaperlist(newpaper);
                return "paperView";
            }
            else if (getFlag().equals("hot")) 
            {
                tcount = paperServ.findHotPaperCount();
                ActionContext.getContext().getSession().put("count", tcount);
                ActionContext.getContext().getSession().put("totalPage", (tcount+pageSize-1)/pageSize);
                List<PaperBag> hotpaper= null;
                if (pageNo==(tcount+pageSize-1)/pageSize)
                {
                    hotpaper = paperServ.getlastPaper(pageNo,tcount-((pageNo-1)*pageSize),pageSize);
                }
                else
                {
                    hotpaper = paperServ.getlastPaper(pageNo,pageSize);
                }
                setPaperlist(hotpaper);
                return "paperView";
            }
            else if (getFlag().equals("rec")) 
            {
                tcount = paperServ.findRecCount();
                ActionContext.getContext().getSession().put("count", tcount);
                ActionContext.getContext().getSession().put("totalPage", (tcount+pageSize-1)/pageSize);
                List<PaperBag> recpaper= null;
                if (pageNo==(tcount+pageSize-1)/pageSize)
                {
                    recpaper = paperServ.getJoinPaper(pageNo,tcount-((pageNo-1)*pageSize),pageSize);
                }
                else
                {
                    recpaper = paperServ.getJoinPaper(pageNo,pageSize);
                }
                setPaperlist(recpaper);
                return "paperView_rec";
            }else {
                return "paperView";
            }
            
        }else {
            return "login";
        }

        
        
    }


    
    

}
