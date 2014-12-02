package com.dxs.Action;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import com.dxs.Entity.PushInfo;
import com.dxs.Service.Impl.PushServiceImpl;
import com.dxs.Service.Intf.PushService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class pushAction extends ActionSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5179700566717557860L;

    private String content;
    
    private Date planTime;
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public Date getPlanTime()
    {
        return planTime;
    }
    
    public void setPlanTime(Date planTime)
    {
        this.planTime = planTime;
    }
    
    
    public String execute()
        throws Exception
    {
        if (content != null && planTime != null)
        {
            ActionContext.getContext().getSession().put("res", null);
            PushInfo pi = new PushInfo();
            pi.setContent(getContent().toString());
            pi.setPlanTime(getPlanTime());// 这个planTime里面暂且只有日期，没有时间，该日历控件获取不到时间
            pi.setPushUser("memotime");
            pi.setState("未推送");
            PushService service = new PushServiceImpl();
            service.addPush(pi);// 返回值 影响行数int类型，暂时不做校验判断。
            
            JSONObject json = JSONObject.fromObject(pi);
            Map<String, Object> m;
            m = ActionContext.getContext().getSession();
            m.put("jsonPushInfo", json.toString());
            ActionContext.getContext().getSession().put("res", "推送成功");
            ActionContext.getContext().getSession().put("count1", 2);
            return "pushSuccess";
        }
        else
        {
            return "error";
        }
    }
}
