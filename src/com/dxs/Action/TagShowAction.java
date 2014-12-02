package com.dxs.Action;

import java.util.ArrayList;

import java.util.List;

import com.dxs.Entity.Category;
import com.dxs.Entity.PaperBag;
import com.dxs.Entity.PaperBag_simple;
import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.CategoryServiceImpl;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.CategoryService;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionSupport;

public class TagShowAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    
    private String cateSel;// 类别下拉值
    
    private String tagSel;// 标签下拉值
    
    private List<PaperBag> booklist;
    
    private String tagsearch;// 搜索框值;
    
    private String cateArea;
    
    private List<String> cateList;// 类别下拉list
    
    private List<Tag> taglist;// 表格标签list
    
    private List<String> tagselList;// 为填充tagbook.jsp的标签下拉菜单
    
    private List<String> leftList;// 左list
    
    private List<String> rightList;// 右list
    
    private List<PaperBag_simple> bolist;// 表格标签list
    
    private List<String> leftList_bo;// 左list 书
    
    private List<String> rightList_bo;// 右list 书
    
    PaperBagService bookServ = new PaperBagServiceImpl();
    
    CategoryService cateServ = new CategoryServiceImpl();
    
    TagService tagServ = new TagServiceImpl();
    
    public String execute()
        throws Exception
    {
        forcateList(cateList, cateServ.getAllCateList(0));// 填充类别下拉菜单
        leftList = new ArrayList<String>();
        leftList_bo = new ArrayList<String>();
        if (getCateSel() != null)
        {
            if (!getCateSel().toString().equals("--添加新类别--"))
            {
                Category cate = cateServ.searchCateByName(getCateSel());// 根据类别名字获得类别
                taglist = tagServ.getTagByCategoryId(String.valueOf(cate.getCategoryId()), 0);// 根据类别ID 获得taglist
                bolist = bookServ.getPaperBag_simpleByCateId(cate.getCategoryId());// 根据类别ID 获得booklist
                for (int i = 0; i < taglist.size(); i++)
                {
                    leftList.add(taglist.get(i).getTagName());
                }
                setLeftList(leftList);// 填充左列
                for (int i = 0; i < bolist.size(); i++)
                {
                    leftList_bo.add(bolist.get(i).getBagName());
                }
                setLeftList_bo(leftList_bo);// 填充书左列
            }
        }
        else
        {
            leftList.add("--请选择类别--");
            setLeftList(leftList);// 填充左列
            leftList_bo.add("--请选择类别--");
            setLeftList_bo(leftList_bo);// 填充书左列
        }
        //填充文本域+左列 结束
        fortagList(rightList, tagServ.getAllTagList());// 填充右列
        forboList(rightList_bo, bookServ.getPaperBag_simpleNoCateId());
        return "tag";
    }
    
    public String showTagBook()
        throws Exception
    {// 填充类别和标签的下拉菜单
        forcateList(cateList, cateServ.getAllCateList(0));// 填充类别下拉菜单
        tagselList = new ArrayList<String>();
        if (getCateSel() != null)
        {
            if (!getCateSel().toString().equals("--请选择类别--"))
            {
                Category cate = cateServ.searchCateByName(getCateSel());// 根据类别名字获得类别
                taglist = tagServ.getTagByCategoryId(String.valueOf(cate.getCategoryId()), 0);// 根据类别ID 获得taglist
                for (int i = 0; i < taglist.size(); i++)
                {
                    tagselList.add(taglist.get(i).getTagName());
                }
                setTagselList(tagselList);// 填充标签
            }
        }
        else
        {
            tagselList.add("--请选择类别--");
            setTagselList(tagselList);
        }
        return "tagbook";
    }
    
    public String tagSelect()
        throws Exception
    {// 选择标签后 获得booklist 填充表格
        if (getTagSel() != null)
        {
            if (!getTagSel().toString().equals("--请选择类别--"))
            {
                Tag tag = tagServ.searchTagByName(getTagSel().toString());// 根据下拉标签的值获取标签对象
                List<PaperBag> booklist = bookServ.getPaperBagByTagId(tag.getTagId());// 根据标签ID获得书
                setBooklist(listChageCol(booklist));// 转换书中的列，把标签列和类别列转换为文字
            }
        }
        showTagBook();
        return "tagbook";
    }
    
    // 转换书中的列，把标签列和类别列转换为文字
    public List<PaperBag> listChageCol(List<PaperBag> bolis)
    {
        for (int i = 0; i < bolis.size(); i++)
        {
            List<Tag> taglis = tagServ.getTagByStr_tagId(bolis.get(i).getTagId());// 根据书的标签列ID获得标签list
            String tagname = "";
            for (int j = 0; j < taglis.size(); j++)
            {
                tagname = tagname + "," + taglis.get(j).getTagName();// 拼凑所有标签
            }
            bolis.get(i).setTagId(tagname.substring(1, tagname.length()));// 把downloadnum的值改为对应标签值
            
            if (bolis.get(i).getCateId() == 0)
            {
                bolis.get(i).setSummary("未分类");
            }
            else
            {
                Category cate = cateServ.getCateById(bolis.get(i).getCateId());
                bolis.get(i).setSummary(cate.getCategoryName());
            }
        }
        return bolis;
    }
    
    // 搜索标签后 获得booklist 填充表格
    public String searchTag()
        throws Exception
    {
        if (getTagsearch() != null && !getTagsearch().equals(""))
        {
            // wl.writeTxt("getTagsearch()="+getTagsearch());
            List<String> strlist = tagServ.getIdByName(getTagsearch());// 根据输入的内容，获得相关标签LIST
            List<PaperBag> booklist = new ArrayList<PaperBag>();// 设置大书目list
            // wl.writeTxt("strlist.size="+strlist.size());
            for (int i = 0; i < strlist.size(); i++)
            {// 循环每个相关标签 ，获得书目LIST
                // wl.writeTxt("Integer.parseInt(strlist.get(i).toString())="+strlist.get(i).toString());
                List<PaperBag> templist = bookServ.getPaperBagByTagId(Integer.parseInt(strlist.get(i).toString()));// 循环每个相关标签
                                                                                                                   // ，获得小书目LIST
                // booklist.addAll(templist);
                // wl.writeTxt("templist.size()="+templist.size());
                for (int j = 0; j < templist.size(); j++)
                {// 把每个小书目LIST中的对象放到大数目LIST中
                    // wl.writeTxt("templist.get(j)="+templist.get(j).getBagName());
                    if (!booklist.contains(templist.get(j)))
                    {
                        booklist.add(templist.get(j));
                    }
                }
            }
            setBooklist(listChageCol(booklist));
        }
        showTagBook();
        return "tagbook";
    }
    
    public String Edit()
        throws Exception
    {
        /*
         * forcateList(cateList,cateServ.getAllCateList());//填充类别下拉菜单
         * //---------------------------------------------------------------//填充文本域+左列 开始 leftList=new
         * ArrayList<String>(); if (getCateSel()!=null) { if (!getCateSel().toString().equals("--添加新类别--")) { String
         * tagname=""; Category cate=cateServ.searchCateByName(getCateSel());//根据类别名字获得类别
         * taglist=tagServ.getTagByCategoryId(String.valueOf(cate.getCategoryId()));//根据类别ID 获得taglist for (int i = 0; i
         * < taglist.size(); i++) { tagname=tagname+taglist.get(i).getTagName()+"\n"; } setCateArea(tagname);//填充文本域 }
         * }else { leftList.add("--请选择类别--"); }
         */
        return "tagEdit";
    }
    
    public void forcateList(List<String> fromlist, List<Category> tolist)// 填充类别下拉菜单
    {
        fromlist = new ArrayList<String>();
        if (tolist != null)
        {
            for (int i = 0; i < tolist.size(); i++)
            {
                fromlist.add(tolist.get(i).getCategoryName());
            }
        }
        else
        {
            fromlist.add("");
        }
        setCateList(fromlist);
    }
    
    public void fortagList(List<String> fromlist, List<Tag> tolist)// 填充标签
    {
        fromlist = new ArrayList<String>();
        if (tolist != null)
        {
            for (int i = 0; i < tolist.size(); i++)
            {
                fromlist.add(tolist.get(i).getTagName());
            }
        }
        else
        {
            fromlist.add("");
        }
        setRightList(fromlist);
    }
    
    public void forboList(List<String> fromlist, List<PaperBag_simple> tolist)// 填充书
    {
        fromlist = new ArrayList<String>();
        if (tolist != null)
        {
            for (int i = 0; i < tolist.size(); i++)
            {
                fromlist.add(tolist.get(i).getBagName());
            }
        }
        else
        {
            fromlist.add("");
        }
        setRightList_bo(fromlist);
    }
    
    public String getCateSel()
    {
        return cateSel;
    }
    
    public void setCateSel(String cateSel)
    {
        this.cateSel = cateSel;
    }
    
    public List<String> getCateList()
    {
        return cateList;
    }
    
    public void setCateList(List<String> cateList)
    {
        this.cateList = cateList;
    }
    
    public List<Tag> getTaglist()
    {
        return taglist;
    }
    
    public void setTaglist(List<Tag> taglist)
    {
        this.taglist = taglist;
    }
    
    public List<String> getLeftList()
    {
        return leftList;
    }
    
    public void setLeftList(List<String> leftList)
    {
        this.leftList = leftList;
    }
    
    public void setRightList(List<String> rightList)
    {
        this.rightList = rightList;
    }
    
    public List<String> getRightList()
    {
        return rightList;
    }
    
    public void setCateArea(String cateArea)
    {
        this.cateArea = cateArea;
    }
    
    public String getCateArea()
    {
        return cateArea;
    }
    
    public void setRightList_bo(List<String> rightList_bo)
    {
        this.rightList_bo = rightList_bo;
    }
    
    public List<String> getRightList_bo()
    {
        return rightList_bo;
    }
    
    public void setBolist(List<PaperBag_simple> bolist)
    {
        this.bolist = bolist;
    }
    
    public List<PaperBag_simple> getBolist()
    {
        return bolist;
    }
    
    public List<String> getLeftList_bo()
    {
        return leftList_bo;
    }
    
    public void setLeftList_bo(List<String> leftListBo)
    {
        leftList_bo = leftListBo;
    }
    
    public void setTagselList(List<String> tagselList)
    {
        this.tagselList = tagselList;
    }
    
    public List<String> getTagselList()
    {
        return tagselList;
    }
    
    public void setTagSel(String tagSel)
    {
        this.tagSel = tagSel;
    }
    
    public String getTagSel()
    {
        return tagSel;
    }
    
    public void setBooklist(List<PaperBag> booklist)
    {
        this.booklist = booklist;
    }
    
    public List<PaperBag> getBooklist()
    {
        return booklist;
    }
    
    public void setTagsearch(String tagsearch)
    {
        this.tagsearch = tagsearch;
    }
    
    public String getTagsearch()
    {
        return tagsearch;
    }
    
}
