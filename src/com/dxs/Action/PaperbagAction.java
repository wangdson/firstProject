package com.dxs.Action;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dxs.Service.Impl.PageServiceImpl;
import com.dxs.Service.Intf.PageService;
import com.dxs.Util.CONSTANTS;
import com.dxs.Util.CheckUser;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PaperbagAction extends ActionSupport
{
    
    /**
     * 生成paperbag.jsp视图
     */
    private static final long serialVersionUID = 1L;
    
    private String res;
    
    public String getRes()
    {
        return res;
    }
    
    public void setRes(String res)
    {
        this.res = res;
    }
    
    private String pagecnum;
    
    public String getPagecnum()
    {
        return pagecnum;
    }
    
    public void setPagecnum(String pagecnum)
    {
        this.pagecnum = pagecnum;
    }
    
    private List<String> totalpageList;
    
    public List<String> getTotalpageList()
    {
        return totalpageList;
    }
    
    public void setTotalpageList(List<String> totalpageList)
    {
        this.totalpageList = totalpageList;
    }
    
    private String tonum;
    
    public String getTonum()
    {
        return tonum;
    }
    
    public void setTonum(String tonum)
    {
        this.tonum = tonum;
    }
    
    PageService pageServ = new PageServiceImpl();
    
    int tcount = pageServ.getbagCount();
    
    int cnum = pageServ.getPageNum();// 从数据库取每页显示条数
    
    int totalpage = pageServ.getlastPage();// 总页数
    
    public void forDropTotalPageList()
    {
        totalpageList = new ArrayList<String>();// 为页面中下拉菜单准备List
        if (cnum == 0)
        {// 为页面中下拉菜单准备List
            totalpageList.add("1");
        }
        else
        {
            for (int i = 0; i < totalpage; i++)
            {
                totalpageList.add(String.valueOf(i + 1));
            }
        }
        setTotalpageList(totalpageList);
    }
    
    public void forCnum()// 从数据库取每页显示条数
    {
        if (cnum == 0)
        {
            ActionContext.getContext().getSession().put("cnum", tcount);// 设置每页数目
        }
        else
        {
            ActionContext.getContext().getSession().put("cnum", cnum);// 设置每页数目
        }
        setPagecnum(String.valueOf(cnum));// 每页显示条数
    }
    
    public void forReadAppversion()
        throws Exception
    {
        Iterator iter = null;
        Iterator iterInner = null;
        Document document;
        String surl = "http://" + CONSTANTS.ADDR + "ipaper/appversion.xml";
        document = getRemoteXML(surl);
        if (document != null)
        {
            Element root = document.getRootElement();
            for (iter = root.elementIterator(); iter.hasNext();)
            {
                Element element = (Element)iter.next();
                String name = element.getName();
                // 获取版本
                if (name.equals("app"))
                {
                    String ver = element.getText();
                    System.out.println("version=" + ver);
                    ActionContext.getContext().getSession().put("appver", ver);
                }
            }
        }
    }
    
    // 将网页转化为document
    public Document getRemoteXML(String url)
    {
        try
        {
            URL url1 = new URL(url);
            SAXReader reader = new SAXReader();
            return reader.read(url1);
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public void forPageChange(int cp)
    {
        if (cp > 1 && cp < totalpage)
        {
            ActionContext.getContext().getSession().put("prep", cp - 1);// 上一页
            ActionContext.getContext().getSession().put("nextp", cp + 1);// 下一页
        }
        if (cp > 1 && cp >= totalpage)
        {
            ActionContext.getContext().getSession().put("prep", cp - 1);// 上一页
            ActionContext.getContext().getSession().put("nextp", totalpage);// 下一页
        }
        if (cp == 1 && totalpage > 1)
        {
            ActionContext.getContext().getSession().put("prep", 1);// 上一页
            ActionContext.getContext().getSession().put("nextp", cp + 1);// 下一页
        }
        if (cp == 1 && totalpage <= 1)
        {
            ActionContext.getContext().getSession().put("prep", 1);// 上一页
            ActionContext.getContext().getSession().put("nextp", 1);// 下一页
        }
    }
    
    public String execute()
        throws Exception
    {
        if (CheckUser.ck_class())
        {
            //每页数目部分
            forCnum();
            
            //页码list部分
            forDropTotalPageList();
            
            //上下页部分
            if (getTonum() != null)
            {   
                // 当前页不为空
                ActionContext.getContext().getSession().put("cpage", getTonum());
                
                // 当前页
                int cp = Integer.parseInt(getTonum());
                forPageChange(cp);
            }
            else
            {// 当前页为空
                ActionContext.getContext().getSession().put("cpage", 1);// 当前页码
                ActionContext.getContext().getSession().put("prep", 1);// 上一页
                if (totalpage > 1)
                {
                    ActionContext.getContext().getSession().put("nextp", 2);// 下一页
                }
                if (totalpage <= 1)
                {
                    ActionContext.getContext().getSession().put("nextp", 1);// 下一页
                }
            }
            
            forReadAppversion();
            return "paperbag";
        }
        else
        {
            return "login";
        }
    }
    
    public String prep()
        throws Exception
    {
        // --每页数目部分
        forCnum();
        // --页码list部分
        forDropTotalPageList();
        String pre = ActionContext.getContext().getSession().get("prep").toString();// 上一页
        ActionContext.getContext().getSession().put("cpage", pre);// 当前页
        int cp = Integer.parseInt(pre);
        forPageChange(cp);
        forReadAppversion();
        return "paperbag";
        
    }
    
    public String nextp()
        throws Exception
    {
        // --每页数目部分
        forCnum();
        // --页码list部分
        forDropTotalPageList();
        String next = ActionContext.getContext().getSession().get("nextp").toString();// 下一页
        ActionContext.getContext().getSession().put("cpage", next);// 当前页
        int cp = Integer.parseInt(next);
        forPageChange(cp);
        forReadAppversion();
        return "paperbag";
    }
    
    public String packok()
        throws Exception
    {
        // --每页数目部分
        forCnum();
        // --页码list部分
        forDropTotalPageList();
        String next = ActionContext.getContext().getSession().get("nextp").toString();// 下一页
        ActionContext.getContext().getSession().put("cpage", next);// 当前页
        int cp = Integer.parseInt(next);
        forPageChange(cp);
        forReadAppversion();
        setRes("打包结束");
        return "paperbag";
    }
    
}
