package com.dxs.Action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import com.dxs.Entity.Category;
import com.dxs.Service.Impl.CategoryServiceImpl;
import com.dxs.Service.Intf.CategoryService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 分类action
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-25]
 */
public class ClassificationAction extends ActionSupport
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -451394352695629380L;
    
    /**
     * 分类列表
     */
    private List<Category> categoryList = null;
    
    /**
     * 分类名称
     */
    private String classificationName = null;
    
    private int pageNo;
    
    private int pageSize;
    
    private int tcount;
    
    public String execute() throws Exception
    {   
        CategoryService cateServ = new CategoryServiceImpl();
        
        //获取类别列表
        categoryList = cateServ.getAllCateList(0);
        
        return "ClassificationList";
    }
    
    /**
     * 
     * 添加类别
     * 
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String addClassification() throws UnsupportedEncodingException
    {   
        CategoryService cateServ = new CategoryServiceImpl();
        if (classificationName != null)
        {   
            classificationName = new String(classificationName.getBytes("ISO-8859-1"), "utf-8");
            
            // 查看该类别是否存在，不存在才添加
            Category cate = cateServ.searchCateByName(classificationName.trim().toString());
            
            if (cate == null)
            {   
                //获取类别最大的ID
                int maxid = cateServ.findMaxCategoryId();
                
                // 创建新类别并赋值
                cate = new Category();
                cate.setCategoryId(maxid + 1);
                
                cate.setCategoryName(classificationName.trim().toString());
                
                // add类别方法
                cateServ.addCategory(cate);
            }
        }
        //获取类别列表
        categoryList = cateServ.getAllCateList(0);
        return "ClassificationList";
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getTcount()
    {
        return tcount;
    }
    
    public void setTcount(int tcount)
    {
        this.tcount = tcount;
    }

    public String getClassificationName()
    {
        return classificationName;
    }

    public void setClassificationName(String classificationName)
    {
        this.classificationName = classificationName;
    }

    public List<Category> getCategoryList()
    {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList)
    {
        this.categoryList = categoryList;
    }
    
}
