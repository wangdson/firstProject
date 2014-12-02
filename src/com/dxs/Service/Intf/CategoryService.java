package com.dxs.Service.Intf;

import java.util.List;

import com.dxs.Entity.Category;

/**
 * 分类的service
 * @author  姓名 工号
 * @version  [版本号, 2014-6-25]
 */
public interface CategoryService
{
    public abstract int addCategory(Category cate);
    
    public abstract int DelByCateId(int cateId);
    
    public abstract Category searchCateByName(String name);
    
    public abstract List<Category> getAllCateList(int limit);
    
    public abstract int findMaxCategoryId();
    
    public abstract Category getCateById(int id);
    
}
