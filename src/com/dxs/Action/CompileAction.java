package com.dxs.Action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Entity.PaperBag;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.task.CompileHelp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 页面触发编译的action
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-17]
 */
public class CompileAction extends ActionSupport implements ServletRequestAware
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8721942707526664005L;
    
    private HttpServletRequest request;
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    public String execute()
    {
        String[] checks = request.getParameterValues("ck");
        PaperBag pg = null;
        PaperBagService pgServ = new PaperBagServiceImpl();
        
        for (int i = 0; i < checks.length; i++)
        {
            // 获取壁纸包对象信息
            pg = pgServ.getPaperBagById(checks[i]);
            pg.setVersion(ActionContext.getContext().getSession().get("appver").toString() + pg.getSver());
            
            if (pg != null)
            {
                //添加到编译队列
                CompileHelp.addComileToDataBase(pg.getBagName(), pg.getId());
            }
        }
        return "paperbagAction";
    }
}
