package com.dxs.Action.Android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Entity.Variable;
import com.dxs.Service.Impl.UsersServiceImpl;
import com.dxs.Service.Intf.UsersService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements ServletRequestAware
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    private HttpServletRequest request;
    
    public void setServletRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    public String execute()
        throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        
        String[] newUser = sb.toString().split(";");
        if (newUser.length == 2)
        {
            UsersService userServ = new UsersServiceImpl();
            if (userServ.checkUser(newUser[0], newUser[1]))
            {
                returnJson("��¼�ɹ�");
            }
            else
            {
                returnJson("��¼ʧ��");
            }
        }
        else
        {
            returnJson("��¼ʧ��");
        }
        return "GetReg";
    }
    
    public void returnJson(String str)
    {
        Variable var = new Variable();
        var.setVar(str);
        JSONObject json = JSONObject.fromObject(var);
        Map<String, Object> m;
        m = ActionContext.getContext().getSession();
        m.put("var", json.toString());
    }
    
}
