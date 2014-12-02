package com.dxs.Action.Android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dxs.Entity.PaperBag;
import com.dxs.Entity.Retrieval;
import com.dxs.Entity.Variable;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.RetrievalServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.RetrievalService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchBooksAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware
{
    /**
     * 用户输入要搜索的书名 按搜索按钮 把关键字发给我，我模糊搜索数据库，返回booklist_json
     */
    private static final long serialVersionUID = 1L;
    
    private Map<String, Object> att;
    
    private HttpServletRequest request;
    
    private HttpServletResponse response;
    
    public void setSession(Map<String, Object> att)
    {
        this.att = att;
    }
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    public void setServletResponse(HttpServletResponse response)
    {
        this.response = response;
    }
    
    public String execute()
        throws Exception
    {
        // sb:从手机侧发过来的搜索关键词
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        
        PaperBagService bookSer = new PaperBagServiceImpl();
        TagService tagSer = new TagServiceImpl();
        
        List<String> tagid = tagSer.getIdByName(sb.toString());
        String[] tagls = new String[tagid.size()];
        tagls = tagid.toArray(tagls);
        
        List<PaperBag> booklist = new ArrayList<PaperBag>();
        for (int i = 0; i < tagls.length; i++)
        {
            List<PaperBag> booklistlite = bookSer.getPaperBagByTagId(Integer.parseInt(tagls[i]));
            booklist.addAll(booklistlite);
        }
        RetrievalService retServ = new RetrievalServiceImpl();
        
        // 绝对匹配搜索，不存在这个关键词 就insert，存在就搜索记录number+1
        Retrieval ret = retServ.searchRet(sb.toString());
        if (ret != null)
        {
            retServ.modifyNumById(ret.getRetrievalId());
        }
        else
        {
            int maxid = retServ.findMaxId();
            ret = new Retrieval();
            maxid = maxid + 1;
            ret.setRetrievalId(maxid);
            ret.setUserKeyWord(sb.toString());
            ret.setSearchTime(new Date());
            ret.setNumber(0);
            ret.setCate("usr");
            retServ.addRetrieval(ret);
        }
        if (booklist.size() != 0)
        {
            JSONArray json = JSONArray.fromObject(booklist);
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("booklist", json.toString());
        }
        else
        {
            Variable var = new Variable();
            var.setVar("该壁纸没上架");
            JSONObject json = JSONObject.fromObject(var);
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("booklist", json.toString());
        }
        return "SearchReturnJson";
    }
    
}
