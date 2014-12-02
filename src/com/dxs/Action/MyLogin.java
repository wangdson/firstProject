package com.dxs.Action;

import org.apache.struts2.ServletActionContext;

import com.dxs.Service.Impl.UsersServiceImpl;
import com.dxs.Service.Intf.UsersService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MyLogin extends ActionSupport
{
    /**
     * ��¼
     */
    private static final long serialVersionUID = 1L;
    
    private String logName;
    
    private String psw;
    
    private String msg;
    
    public String getMsg()
    {
        return msg;
    }
    
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
    public String getLogName()
    {
        return logName;
    }
    
    public void setLogName(String logName)
    {
        this.logName = logName;
    }
    
    public String getPsw()
    {
        return psw;
    }
    
    public void setPsw(String psw)
    {
        this.psw = psw;
    }
    
    public String checkUser()
        throws Exception
    {
        UsersService service = new UsersServiceImpl();
        String username = ServletActionContext.getRequest().getParameter("logName");
        boolean isExsit = service.checkUserName(username);
        
        if (!isExsit)
        {
            ActionContext.getContext().getSession().put("result", "用户名登陆中...");
        }
        else
        {
            ActionContext.getContext().getSession().put("result", "<font color='red'>用户名不存在，请重新输入！</font>");
        }
        return "check";
    }
    
    public String execute()
        throws Exception
    {
        UsersService service = new UsersServiceImpl();
        if (service.checkUser(logName, psw))
        {
            ActionContext.getContext().getSession().put("loginId", "memotime");
            return "index";
        }
        else
        {
            return "login";
        }
    }
}
