package com.dxs.Action.Android;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetHotAction extends ActionSupport
{
    
    /**
     * * 打开搜索Activity，获得最新批次推荐词（为了填充搜索页面浮动关键词,仅关键词） 测试查看 获得信息 的页面：“GetNewRecommend”：android/GetNewRecommend.jsp
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
        PaperBagService paperServ = new PaperBagServiceImpl();
        String lastpage = String.valueOf(paperServ.lastPage_hot());
        List<PaperBag> topbook = paperServ.getAllPaperBag(getCurrpage(),10);
        if (topbook != null)
        {
            // 把总页数追加到每个对象里，放到json
            for (int i = 0; i < topbook.size(); i++)
            {
                topbook.get(i).setTotalpage(lastpage);
                topbook.set(i, topbook.get(i));
            }
            JSONArray topjson = JSONArray.fromObject(topbook);
            
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("hotList", topjson.toString());
        }
        else
        {
            JSONArray topjson = JSONArray.fromObject(topbook);
            
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("hotList", topjson.toString());
        }
        
        return "GetHot";
    }
    
}
