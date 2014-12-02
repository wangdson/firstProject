package com.dxs.Action.Android;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Variable;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetTagAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String tagId;
    
    public String getTagId()
    {
        return tagId;
    }
    
    public void setTagId(String tagId)
    {
        this.tagId = tagId;
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
        // 根据id 获得标签
        if (getId() != null && getTagId() == null)
        {
            PaperBagService bookServ = new PaperBagServiceImpl();
            PaperBag book = bookServ.getPaperBagById(getId());
            if (book != null && !book.getTagId().equals('0'))
            {
                TagService tagServ = new TagServiceImpl();
                List<Variable> varlist = tagServ.getTagNameByStr_tagId(book.getTagId());
                JSONArray json = JSONArray.fromObject(varlist);
                Map<String, Object> m;
                m = ActionContext.getContext().getSession();
                m.put("varlist", json.toString());
            }
        }
        // 根据标签ID获得书
        if (getTagId() != null)
        {
            PaperBagService bookServ = new PaperBagServiceImpl();
            List<PaperBag> booklist =
                bookServ.getPaperBagByTagId_cutsum(getCurrpage(), Integer.parseInt(getTagId().toString()));
            int lastpage = bookServ.lastPage_tag(Integer.parseInt(getTagId().toString()));
            // 把总页数追加到每个对象里，放到json
            for (int i = 0; i < booklist.size(); i++)
            {
                booklist.get(i).setTotalpage(String.valueOf(lastpage));
                booklist.set(i, booklist.get(i));
            }
            
            JSONArray json = JSONArray.fromObject(booklist);
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("varlist", json.toString());
        }
        
        return "GetTag";
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getId()
    {
        return id;
    }
}
