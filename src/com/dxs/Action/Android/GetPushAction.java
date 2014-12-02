package com.dxs.Action.Android;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.dxs.Entity.PushInfo;

import com.dxs.Service.Impl.PushServiceImpl;
import com.dxs.Service.Intf.PushService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetPushAction extends ActionSupport
{
    
    /**
     * 打开apk，取push消息 条件：推送 时间 > 今天凌晨零点 且 < 当前时间
     */
    private static final long serialVersionUID = 1L;
    
    public String execute()
        throws Exception
    {
        PushService service = new PushServiceImpl();
        List<PushInfo> pushlist = service.getTodayPush();
        PushInfo pp = new PushInfo();
        pp = pushlist.get(0);
        JSONObject json = JSONObject.fromObject(pp);
        Map<String, Object> m;
        m = ActionContext.getContext().getSession();
        m.put("pushlist", json.toString());
        return "GetPushInfo";
    }
}
