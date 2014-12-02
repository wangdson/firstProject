package com.dxs.Action.Android;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dxs.Entity.Users;
import com.dxs.Entity.Variable;
import com.dxs.Service.Impl.UsersServiceImpl;
import com.dxs.Service.Intf.UsersService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport implements ServletRequestAware
{
    
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
        
        SimpleDateFormat forDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = forDateTime.format(new Date());
        String[] newUser = sb.toString().split(";");
        UsersService userServ = new UsersServiceImpl();
        if (newUser.length == 2)
        {
            if (userServ.checkUserName(newUser[0]))
            {
                int maxid = userServ.findMaxId();
                Users user = new Users(maxid + 1, newUser[0], newUser[1], "");
                userServ.addUser(user);
                // 返回JSON 成功
                returnJson("注册成功");
            }
            else
            {
                // 返回JSON 用户已存在
                returnJson("用户已存在");
            }
        }
        else if (newUser.length == 3)
        {
            if (userServ.checkUserName(newUser[0]))
            {
                int maxid = userServ.findMaxId();
                Users user = new Users(maxid + 1, newUser[0], newUser[1], newUser[2]);
                userServ.addUser(user);
                // 返回JSON 成功
                returnJson("注册成功");
            }
            else
            {
                // 返回JSON 用户已存在
                returnJson("用户已存在");
            }
        }
        else
        {
            returnJson("用户发来数据 格式不正确:" + sb.toString() + ",时间：" + datetime);
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
