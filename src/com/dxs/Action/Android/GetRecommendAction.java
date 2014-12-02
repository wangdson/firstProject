package com.dxs.Action.Android;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.dxs.Entity.NewRecommend;
import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Retrieval;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.RetrievalServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.RetrievalService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetRecommendAction extends ActionSupport
{
    /**
     * 打开搜索Activity，获得最新批次推荐词（为了填充搜索页面浮动关键词,仅关键词） 测试查看 获得信息 的页面：“GetNewRecommend”：android/GetNewRecommend.jsp
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String flag;
    
    public String getFlag()
    {
        return flag;
    }
    
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    
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
        if (getFlag() != null && getFlag().equals("retrieval"))
        {// 点击搜索标签，返回热门关键词JSON（仅关键词）
            // RecommendService recService=new RecommendServiceImpl();
            RetrievalService retService = new RetrievalServiceImpl();
            List<Retrieval> retadmlist = retService.getRetrievalByCate("adm");// 获得手机显示的检索字
            // 获得最新批次的推荐表的关键词
            // String[]
            // keyName={"美女","帅哥","法拉利","保时捷","龙泽洛拉","苍井空","曾小贤","胡一菲","张伟","爱情公寓3","唐悠悠","秦羽墨","秦始皇","爱新觉罗玄烨","喇嘛三世","色戒","男儿本色","恋爱通告","偷窥无罪","大话西游"};
            List<NewRecommend> recList = new ArrayList<NewRecommend>();// recService.getNewRecommend();
            for (int i = 0; i < retadmlist.size(); i++)
            {
                NewRecommend nrec = new NewRecommend();
                nrec.setKeyword(retadmlist.get(i).getUserKeyWord());
                recList.add(nrec);
            }
            JSONArray json = JSONArray.fromObject(recList);
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("recList", json.toString());
        }
        else if (getFlag() != null && getFlag().equals("books"))
        {// 点击地球，返回下载量最高的5本书
            // 热门主打（下载量降序）、新书上架（最新APK，时间倒序）
            
            PaperBagService bookServ = new PaperBagServiceImpl();
            String lastpage = String.valueOf(bookServ.lastPage());
            List<PaperBag> newbook = bookServ.getlastPaper(getCurrpage(),10);
            // 把总页数追加到每个对象里，放到json
            for (int i = 0; i < newbook.size(); i++)
            {
                newbook.get(i).setTotalpage(lastpage);
                newbook.set(i, newbook.get(i));
            }
            JSONArray newjson = JSONArray.fromObject(newbook);
            
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("recList", newjson.toString());
        }
        return "GetNewRecommend";
        
    }
    
}
