package com.dxs.Action.Android;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.dxs.Entity.PaperBag_simple;
import com.dxs.Entity.Category;
import com.dxs.Entity.Category_Contain;
import com.dxs.Entity.DataObject;

import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.CategoryServiceImpl;

import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.CategoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetBookObjectAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    public String execute()
        throws Exception
    {
        CategoryService cateServ = new CategoryServiceImpl();
        PaperBagService bookServ = new PaperBagServiceImpl();
        
        DataObject DataObj = new DataObject();
        
        List<Category_Contain> cateconlist = new ArrayList<Category_Contain>();
        
        // 获得6个类别
        List<Category> catlist = cateServ.getAllCateList(0);// 0是获得所有，>0 是限制获得具体多少条
        for (int i = 0; i < catlist.size(); i++)
        {
            int papercount = bookServ.getCountPaperBycateId(catlist.get(i).getCategoryId());
            
            if (papercount != 0)
            {
                Category_Contain catecon = new Category_Contain();
                catecon.setCategoryId(catlist.get(i).getCategoryId());
                catecon.setCategoryName(catlist.get(i).getCategoryName());
                List<PaperBag_simple> bosl = bookServ.getPaperBag_simpleByCateIdAndpng(catlist.get(i).getCategoryId());
                catecon.setBook(bosl);
                cateconlist.add(catecon);
                
            }
            else
            {
                cateconlist.add(null);
            }
        }
        DataObj.setCate(cateconlist);
        
        JSONObject json = JSONObject.fromObject(DataObj);
        Map<String, Object> m;
        m = ActionContext.getContext().getSession();
        m.put("varlist", json.toString());
        return "GetTag";
    }
}
