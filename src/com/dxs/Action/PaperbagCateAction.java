package com.dxs.Action;

import java.util.List;

import com.dxs.Entity.Category;
import com.dxs.Service.Impl.CategoryServiceImpl;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.CategoryService;
import com.dxs.Service.Intf.PaperBagService;
import com.opensymphony.xwork2.ActionSupport;

public class PaperbagCateAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    // 类别下拉值
    private String cateSel;
    
    // 左list 区别TagShowAction,因为那边是填充list 获取要用jsp控件中的name值
    private List<String> leftSideRecords_bo;
    
    // 右list 区别TagShowAction,因为那边是填充list 获取要用jsp控件中的name值
    private List<String> rightSideRecords_bo;
    
    public String AddBook()
        throws Exception
    {
        if (getCateSel() != null && getLeftSideRecords_bo() != null)
        {   
            // 1.下拉类别不为空，左列不为空时
            if (!getCateSel().toString().equals("--添加新类别--"))
            {   
                // 2.下拉类别不为“添加新类别”
                int boxs[] = new int[getLeftSideRecords_bo().size()];
                CategoryService cateServ = new CategoryServiceImpl();// 创建类别服务
                PaperBagService bookServ = new PaperBagServiceImpl();
                Category cate = cateServ.searchCateByName(getCateSel().toString());// 根据类别名字获得类别对象,从而获得所属ID
                bookServ.modifyPaperBagCateId(0, cate.getCategoryId());// 3.把该类别下所有书的类别设置为0
                for (int i = 0; i < getLeftSideRecords_bo().size(); i++)
                {   
                    // 获得左列的值
                    boxs[i] = bookServ.getPaperBag_simpleByName(getLeftSideRecords_bo().get(i).toString()).getId();
                }
                for (int i = 0; i < boxs.length; i++)
                {   
                    // 4.获取标签左列的值，循环修改
                    bookServ.modifyPaperBagCateIdById(boxs[i], cate.getCategoryId());
                }
            }
        }
        else
        {
            return "error";
        }
        return "TagShowAction";
    }
    
    public void setCateSel(String cateSel)
    {
        this.cateSel = cateSel;
    }
    
    public String getCateSel()
    {
        return cateSel;
    }
    
    public void setLeftSideRecords_bo(List<String> leftSideRecords_bo)
    {
        this.leftSideRecords_bo = leftSideRecords_bo;
    }
    
    public List<String> getLeftSideRecords_bo()
    {
        return leftSideRecords_bo;
    }
    
    public void setRightSideRecords_bo(List<String> rightSideRecords_bo)
    {
        this.rightSideRecords_bo = rightSideRecords_bo;
    }
    
    public List<String> getRightSideRecords_bo()
    {
        return rightSideRecords_bo;
    }
    
}
