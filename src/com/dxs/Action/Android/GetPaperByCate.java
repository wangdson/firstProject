package com.dxs.Action.Android;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.dxs.Entity.PaperBag_simple;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetPaperByCate extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    private int cateId;
    
    private int currpage;
    
    public String execute()
        throws Exception
    {
        PaperBagService paperServ = new PaperBagServiceImpl();
        int lastpage = paperServ.lastPage_cate(getCateId());
        
        List<PaperBag_simple> bosl = paperServ.getPaperBag_simpleByCateIdAndpng(getCurrpage(), getCateId());
        if (bosl != null)
        {
            for (int j = 0; j < bosl.size(); j++)
            {
                bosl.get(j).setTotalpage(lastpage);
                bosl.set(j, bosl.get(j));
            }
            
            JSONArray catejson = JSONArray.fromObject(bosl);
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("varlist", catejson.toString());
        }
        return "GetTag";
    }
    
    public void setCateId(int cateId)
    {
        this.cateId = cateId;
    }
    
    public int getCateId()
    {
        return cateId;
    }
    
    public void setCurrpage(int currpage)
    {
        this.currpage = currpage;
    }
    
    public int getCurrpage()
    {
        return currpage;
    }
}
