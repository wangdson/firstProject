package com.dxs.Action.Android;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import com.dxs.Entity.Category;
import com.dxs.Service.Impl.CategoryServiceImpl;
import com.dxs.Service.Intf.CategoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetCategoryAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    public String execute()
        throws Exception
    {
        
        CategoryService cateServ = new CategoryServiceImpl();
        
        // 0是获得所有，>0 是限制获得具体多少条
        List<Category> cateList = cateServ.getAllCateList(0);
        JSONArray json = JSONArray.fromObject(cateList);
        Map<String, Object> m;
        m = ActionContext.getContext().getSession();
        m.put("cateList", json.toString());
        
        return "GetCategory";
    }
}
