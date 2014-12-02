package com.dxs.Action;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Util.CheckUser;
import com.dxs.help.ProfilesParameterHelpUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PaperbagViewAction extends ActionSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3501014489453480839L;

    private List<PaperBag> pgList;
    
    private int pageNo;
    
    private int pageSize;
    
    private int tcount;
    
    private String res;
    
    PaperBagService pageBagServ = new PaperBagServiceImpl();
    
    public String execute()
        throws Exception
    {
        if (CheckUser.ck_class())
        {
            doAction();
            forReadAppversion();
            return "paperbag";
        }
        else
        {
            return "login";
        }
    }
    
    /**
     * 读取app版本号
     * 
     * @throws Exception
     */
    public void forReadAppversion()
        throws Exception
    {
        
        String version =
            ProfilesParameterHelpUtils.readAppversion(URLDecoder.decode(this.getClass().getClassLoader().getResource("/").getPath()
                + "config/appversion.xml"));
        
        if (version != null)
        {
            version = version.substring(0, 3);
            ActionContext.getContext().getSession().put("appver", version);
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
    
    private void doAction()
    {
        if (pageNo == 0)
        {
            pageNo = 1;
        }
        if (pageSize == 0)
        {
            pageSize = 10;
        }
        setPageNo(pageNo);
        setPageSize(pageSize);
        tcount = pageBagServ.findbagCount();
        setTcount(tcount);
        ActionContext.getContext().getSession().put("totalPage", (tcount+pageSize+1 )/ pageSize);
        List<PaperBag> pgList = null;
        if (pageNo == (tcount+pageSize+1 )/ pageSize)
        {
            pgList = pageBagServ.getAllPaperBag(pageNo, tcount - ((pageNo - 1) * pageSize),pageSize);
        }
        else
        {
            pgList = pageBagServ.getAllPaperBag(pageNo, pageSize);
        }
        ActionContext.getContext().getSession().put("pgList", pgList);
        
    }
    
    public String packok()
        throws Exception
    {
        doAction();
        setRes("打包结束");
        return "paperbag";
    }
    
    public String getRes()
    {
        return res;
    }
    
    public void setRes(String res)
    {
        this.res = res;
    }
    
    public List<PaperBag> getPgList()
    {
        return pgList;
    }
    
    public void setPgList(List<PaperBag> pgList)
    {
        this.pgList = pgList;
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
