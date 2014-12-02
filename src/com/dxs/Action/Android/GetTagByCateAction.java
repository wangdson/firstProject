package com.dxs.Action.Android;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetTagByCateAction extends ActionSupport
{
    
    private static final long serialVersionUID = 1L;
    
    private String cateId;
    
    public String execute()
        throws Exception
    {
        TagService tagServ = new TagServiceImpl();
        
        // 0是获得所有，>0 是限制获得具体多少条
        List<Tag> taglist = tagServ.getTagByCategoryId(getCateId(), 0);
        JSONArray json = JSONArray.fromObject(taglist);
        Map<String, Object> m;
        m = ActionContext.getContext().getSession();
        m.put("varlist", json.toString());
        
        return "GetTag";
    }
    
    public void setCateId(String cateId)
    {
        this.cateId = cateId;
    }
    
    public String getCateId()
    {
        return cateId;
    }
    
}
