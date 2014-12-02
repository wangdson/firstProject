package com.dxs.Action.Android;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetRecAction extends ActionSupport
{
    
    /**
     * 手机获得编辑推荐
     */
    private static final long serialVersionUID = 1L;
    
    private int currpage;
    
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
        String lastpage = String.valueOf(bookServ.lastPage_rec());
        List<PaperBag> recbook = bookServ.getJoinPaper(getCurrpage(),10);
        // 把总页数追加到每个对象里，放到json
        for (int i = 0; i < recbook.size(); i++)
        {
            recbook.get(i).setTotalpage(lastpage);
            recbook.set(i, recbook.get(i));
        }
        
        JSONArray recjson = JSONArray.fromObject(recbook);
        
        Map<String, Object> m;
        m = ActionContext.getContext().getSession();
        m.put("recbook", recjson.toString());
        return "getrec";
    }
}
