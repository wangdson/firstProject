package com.dxs.Action.Android;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetBookByCateIdAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    private int currpage;
    
    private int cateId;
    
    public int getCateId()
    {
        return cateId;
    }
    
    public void setCateId(int cateId)
    {
        this.cateId = cateId;
    }
    
    public void setCurrpage(int currpage)
    {
        this.currpage = currpage;
    }
    
    public int getCurrpage()
    {
        return currpage;
    }
    
    public String execute()
        throws Exception
    {
        
        PaperBagService bookServ = new PaperBagServiceImpl();
        List<PaperBag> catbook = bookServ.getPaperBagByCateId_cutsum(getCurrpage(), getCateId());
        int lastpage = bookServ.lastPage_cate(getCateId());
        
        // 把总页数追加到每个对象里，放到json
        for (int i = 0; i < catbook.size(); i++)
        {
            catbook.get(i).setTotalpage(String.valueOf(lastpage));
            catbook.set(i, catbook.get(i));
        }
        JSONArray topjson = JSONArray.fromObject(catbook);
        
        Map<String, Object> m;
        m = ActionContext.getContext().getSession();
        m.put("varlist", topjson.toString());
        
        return "GetTag";
    }
    
}
