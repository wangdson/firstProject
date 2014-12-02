package com.dxs.Util;

import com.opensymphony.xwork2.ActionContext;

public class CheckUser
{
    public static boolean ck_class()
    {
        if (ActionContext.getContext().getSession().get("loginId") == null)
        {
            return false;
        }
        else if (!(ActionContext.getContext().getSession().get("loginId").toString().equals("memotime")))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
