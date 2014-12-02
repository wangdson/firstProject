package com.dxs.Action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dxs.Entity.Category;
import com.dxs.Entity.PaperBag_simple;

import com.dxs.Service.Impl.CategoryServiceImpl;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.CategoryService;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionSupport;

public class TagCateAction extends ActionSupport
{
    /**
     * 标签类别处理类，增加删除
     */
    private static final long serialVersionUID = 1L;
    
    final Log log = LogFactory.getLog(TagCateAction.class);
    
    // 文本框 新类别名
    private String cateName;
    
    // 类别下拉值
    private String cateSel;
    
    // 左list 区别TagShowAction,因为那边是填充list 获取要用jsp控件中的name值
    private List<String> leftSideRecords;
    
    // 右list 区别TagShowAction,因为那边是填充list 获取要用jsp控件中的name值
    private List<String> rightSideRecords;
    
    public String AddCate()
        throws Exception
    {
        if (getCateName() != null)
        {   
            // 创建类别服务
            CategoryService cateServ = new CategoryServiceImpl();
            
            // 查看该类别是否存在，不存在才添加
            Category cate = cateServ.searchCateByName(getCateName().trim().toString());
            if (cate == null)
            {   
                // 获得类别最大ID
                int maxid = cateServ.findMaxCategoryId();
                
                // 创建新类别并赋值
                cate = new Category();
                cate.setCategoryId(maxid + 1);
                cate.setCategoryName(getCateName().trim().toString());
                
                // add类别方法
                cateServ.addCategory(cate);
            }
        }
        return "TagShowAction";
    }
    
    public String AddTag()
        throws Exception
    {
        
        if (getCateSel() != null && getLeftSideRecords() != null)
        {   
            // 1.下拉类别不为空，左列不为空时
            if (!getCateSel().toString().equals("--添加新类别--"))
            {   
                // 2.下拉类别不为“添加新类别”
                PaperBagService bookServ = new PaperBagServiceImpl();
                int boxs[] = new int[getLeftSideRecords().size()];
                log.info("下拉列表选择的值为：" + getCateSel().toString());
                CategoryService cateServ = new CategoryServiceImpl();// 创建类别服务
                TagService tagServ = new TagServiceImpl();// 创建标签服务
                Category cate = cateServ.searchCateByName(getCateSel().toString());// 根据类别名字获得类别对象,从而获得所属ID
                log.info("要修改的标签类别ID的值为：" + cate.getCategoryId());
                tagServ.updateTagCateByCatId(cate.getCategoryId());// 3.移除该类别下所有标签
             
                for (int i = 0; i < getLeftSideRecords().size(); i++)
                {   
                    // 根据左列的值获取id
                    log.info("第" + i + "获得左列标签的值为：" + getLeftSideRecords().get(i));
                    
                    // boxs存的是标签ID
                    boxs[i] = tagServ.searchTagByName(getLeftSideRecords().get(i).toString()).getTagId();
                    
                    log.info("box(" + i + "):" + boxs[i]);
                }
                
                for (int i = 0; i < boxs.length; i++)
                {   
                    // 4.获取左列标签id，循环修改
                    tagServ.updateTagCateAndHotById(cate.getCategoryId(), boxs.length - i, boxs[i]);
                }
                for (int i = 0; i < boxs.length; i++)
                {   
                    // 5.根据标签ID 获得书LIST
                    List<PaperBag_simple> bolist = bookServ.getPaperBag_simpleByTagId(boxs[i]);
                    for (int j = 0; j < bolist.size(); j++)
                    {   
                        // 6.根据书ID更新书的分类
                        bookServ.modifyPaperBagCateIdById(bolist.get(j).getId(), cate.getCategoryId());
                    }
                }
            }
        }
        else
        {
            return "error";
        }
        return "TagShowAction";
    }
    
    
    public String DELCate()
        throws Exception
    {
        if (getCateSel() != null)
        {
            if (!getCateSel().toString().equals("--添加新类别--"))
            {   
                // 创建类别服务
                CategoryService cateServ = new CategoryServiceImpl();
                
                // 创建标签服务
                TagService tagServ = new TagServiceImpl();
                Category cate = cateServ.searchCateByName(getCateSel().toString());
                if (cate != null)
                {
                    tagServ.DelAllByCategoryId(cate.getCategoryId());
                    cateServ.DelByCateId(cate.getCategoryId());
                }
            }
        }
        
        return "TagShowAction";
    }
    
    public String getCateName()
    {
        return cateName;
    }
    
    public void setCateName(String cateName)
    {
        this.cateName = cateName;
    }
    
    public String getCateSel()
    {
        return cateSel;
    }
    
    public void setCateSel(String cateSel)
    {
        this.cateSel = cateSel;
    }
    
    public void setLeftSideRecords(List<String> leftSideRecords)
    {
        this.leftSideRecords = leftSideRecords;
    }
    
    public List<String> getLeftSideRecords()
    {
        return leftSideRecords;
    }
    
    public void setRightSideRecords(List<String> rightSideRecords)
    {
        this.rightSideRecords = rightSideRecords;
    }
    
    public List<String> getRightSideRecords()
    {
        return rightSideRecords;
    }
}
