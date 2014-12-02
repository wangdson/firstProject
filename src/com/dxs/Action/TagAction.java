package com.dxs.Action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Entity.Category;
import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.CategoryServiceImpl;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.CategoryService;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionSupport;

public class TagAction extends ActionSupport implements ServletRequestAware
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3009278078213258000L;
    
    private String tagNamein;// 文本框 新标签名
    
    private List<Tag> taglist;
    
    private List<PaperBag> paperbaglist;
    
    String[] tagboxs;// 获取勾选标签
    
    String[] pgboxs;// 获取勾选图片包
    
    private String cateSel;// 类别下拉值
    
    private List<String> cateList;// 类别下拉list
    
    private String cateName;// 文本框 新类别名
    
    TagService tagServ = new TagServiceImpl();
    
    CategoryService cateServ = new CategoryServiceImpl();
    
    PaperBagService pgServ = new PaperBagServiceImpl();
    
    private HttpServletRequest request;
    
    private int pageNo;
    
    private int pageSize;
    
    private int tcount;
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    public String execute()
        throws Exception
    {
        forcateList(cateList, cateServ.getAllCateList(0));// 填充类别下拉菜单
        
        taglist = tagServ.getAllTagList();
        paperbaglist = pgServ.getAllPaperBag();
        
        List<Object> strpap = new ArrayList<Object>();
        List<Object> strxp = new ArrayList<Object>();
        
        for (int i = 0; i < paperbaglist.size(); i++)
        {
            strpap.add(paperbaglist.get(i).getBagName());
        }
        for (int i = 0; i < taglist.size(); i++)
        {
            strxp.add(taglist.get(i).getTagName());
            
            if (strpap.contains(strxp.get(i)))
            {
                taglist.remove(i);
            }
        }
        setTaglist(taglist);
        setPaperbaglist(paperbaglist);
        return "addtag";
    }
    
    public String addtag()
        throws Exception
    {
        if (getTagNamein() != null)
        {
            TagService tagServ = new TagServiceImpl();// 创建类别服务
            Tag tag = tagServ.searchTagByName(getTagNamein().trim().toString());// 查看该类别是否存在，不存在才添加
            if (tag == null)
            {
                int maxid = tagServ.findMaxTagId();// 获得类别最大ID
                tag = new Tag();// 创建新类别并赋值
                tag.setTagId(maxid + 1);
                tag.setTagName(getTagNamein());
                tag.setCategoryId("0");
                tag.setHotNum(0);
                tag.setCount(0);
                tag.setFlag(0);// 0豆瓣 1 客户
                tagServ.addTag(tag);// add类别方法
            }
        }
        execute();
        return "addtag";
    }
    
    public String distribution()
        throws Exception
    {
        tagboxs = request.getParameterValues("ck_tag");
        pgboxs = request.getParameterValues("ck_pg");
        
        if (tagboxs != null && pgboxs != null && getCateSel() != null && tagboxs.length != 0 && pgboxs.length != 0)
        {
            if (!getCateSel().toString().equals("--添加新类别--"))
            {
                Category cate = cateServ.searchCateByName(getCateSel().toString());
                if (cate != null)
                {
                    String savePgtagId = "";
                    for (int i = 0; i < tagboxs.length; i++)
                    {
                        savePgtagId = savePgtagId + "," + tagboxs[i];
                    }
                    
                    for (int i = 0; i < pgboxs.length; i++)
                    {
                        String jointag = "";
                        PaperBag pgg = pgServ.getPaperBagById(pgboxs[i]);
                        String oldtag = pgg.getTagId();
                        if (oldtag != null)
                        {   
                            // 4种情况 “0”“12,5”“12”“0,14”
                            if (oldtag.equals("0"))
                            {   
                                // +自己+新的
                                int newtagid = addselftag(pgg.getBagName());
                                jointag = String.valueOf(newtagid) + savePgtagId;
                            }
                            else if (oldtag.contains(","))
                            {   
                                // “12,5”“0,14”
                                String cutoldtag = oldtag.substring(0, oldtag.indexOf(","));
                                if (cutoldtag.equals("0"))
                                {   
                                    // +自己+新的
                                    int newtagid = addselftag(pgg.getBagName());
                                    jointag = String.valueOf(newtagid) + savePgtagId;
                                }
                                else
                                {   
                                    // 保留第一个+新的
                                    jointag = cutoldtag + savePgtagId;
                                }
                            }
                            else
                            {
                                // 既不=0又没有，那就+新的
                                jointag = oldtag + savePgtagId;
                            }
                        }
                        else
                        {
                            int newtagid = addselftag(pgg.getBagName());
                            jointag = String.valueOf(newtagid) + savePgtagId;
                        }
                        
                        pgServ.modifyTagIdById(pgboxs[i], jointag, cate.getCategoryId());
                    }
                }
            }
        }
        execute();
        return "addtag";
    }
    
    public String deltag()
        throws Exception
    {
        tagboxs = request.getParameterValues("ck_tag");
        if (tagboxs != null && tagboxs.length != 0)
        {
            for (int i = 0; i < tagboxs.length; i++)
            {
                tagServ.DelByTagId(Integer.valueOf(tagboxs[i]));
            }
        }
        execute();
        return "addtag";
    }
    
    public String delcate()
        throws Exception
    {
        if (getCateSel() != null)
        {
            if (!getCateSel().toString().equals("--添加新类别--"))
            {
                Category cate = cateServ.searchCateByName(getCateSel().toString());
                if (cate != null)
                {
                    tagServ.DelAllByCategoryId(cate.getCategoryId());
                    cateServ.DelByCateId(cate.getCategoryId());
                }
            }
        }
        execute();
        return "addtag";
    }
    
    public String addcate()
        throws Exception
    {
        if (getCateName() != null)
        {
            Category cate = cateServ.searchCateByName(getCateName().trim().toString());// 查看该类别是否存在，不存在才添加
            if (cate == null)
            {
                int maxid = cateServ.findMaxCategoryId();// 获得类别最大ID
                cate = new Category();// 创建新类别并赋值
                cate.setCategoryId(maxid + 1);
                cate.setCategoryName(getCateName().trim().toString());
                cateServ.addCategory(cate);// add类别方法
            }
        }
        execute();
        return "addtag";
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
    
    public int addselftag(String name)
    {
        int newid = 0;
        Tag tn = tagServ.searchTagByName(name);
        if (tn == null)
        {
            int maxid = tagServ.findMaxTagId();// 获得类别最大ID
            newid = maxid + 1;
            Tag tg = new Tag();
            tg.setTagId(newid);
            tg.setTagName(name);
            tg.setCategoryId("0");
            tg.setHotNum(0);
            tg.setCount(0);
            tg.setFlag(0);// 0豆瓣 1 客户
            tagServ.addTag(tg);// add类别方法
        }
        else
        {
            newid = tn.getTagId();
        }
        return newid;
    }
    
    public void setTagNamein(String tagNamein)
    {
        this.tagNamein = tagNamein;
    }
    
    public String getTagNamein()
    {
        return tagNamein;
    }
    
    public List<String> getCateList()
    {
        return cateList;
    }
    
    public void setCateList(List<String> cateList)
    {
        this.cateList = cateList;
    }
    
    public void setTaglist(List<Tag> taglist)
    {
        this.taglist = taglist;
    }
    
    public List<Tag> getTaglist()
    {
        return taglist;
    }
    
    public void setPaperbaglist(List<PaperBag> paperbaglist)
    {
        this.paperbaglist = paperbaglist;
    }
    
    public List<PaperBag> getPaperbaglist()
    {
        return paperbaglist;
    }
    
    public void setCateSel(String cateSel)
    {
        this.cateSel = cateSel;
    }
    
    public String getCateSel()
    {
        return cateSel;
    }
    
    public void setCateName(String cateName)
    {
        this.cateName = cateName;
    }
    
    public String getCateName()
    {
        return cateName;
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
}
