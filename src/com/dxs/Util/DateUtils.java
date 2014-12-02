package com.dxs.Util;

import java.util.Calendar;

/**
 * 日期相关的工具类
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-19]
 */
public class DateUtils
{
    /**
     * 获取日期数字组成的字符串
     * 
     * @return
     */
    public static String getDateString()
    {
        String y, m, d, h, mi, s;
        String result;
        Calendar cal = Calendar.getInstance();
        y = String.valueOf(cal.get(Calendar.YEAR));
        m = String.valueOf(cal.get(Calendar.MONTH));
        d = String.valueOf(cal.get(Calendar.DATE));
        h = String.valueOf(Calendar.HOUR_OF_DAY);
        mi = String.valueOf(Calendar.MINUTE);
        s = String.valueOf(cal.get(Calendar.SECOND));
        result = y + m + d + h + mi + s;
        return result;
    }
}
