package com.dxs.Action.Android;

import java.util.Map;
import net.sf.json.JSONObject;

import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetBookByIdAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    private int Id;
    
    public void setId(int id)
    {
        Id = id;
    }
    
    public int getId()
    {
        return Id;
    }
    
    public String execute()
        throws Exception
    {
        PaperBagService bookServ = new PaperBagServiceImpl();
        PaperBag book = bookServ.getPaperBagById(String.valueOf(getId()));
        
        JSONObject json = JSONObject.fromObject(book);
        Map<String, Object> m;
        m = ActionContext.getContext().getSession();
        m.put("varlist", json.toString());
        
        return "GetTag";
    }
}
