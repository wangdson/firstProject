package com.dxs.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 字符串操作相关工具类
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-25]
 */
public class CommonUtils
{
    /**
     * 将字符串拆成list
     */
    public static List<String> StringToArrList(String sValue)
    {
        List<String> arrlist = new ArrayList<String>();
        
        if (sValue == null)
        {
            return arrlist;
        }
        
        String arr[] = sValue.split(",");
        arrlist = new ArrayList<String>(Arrays.asList(arr));
        
        return arrlist;
    }
    
    /**
     * 数组转成List
     * 
     * @param arrList
     * @return 数组
     */
    public static String[] arrToArrList(List<String> arrList)
    {
        String[] arr = null;
        
        if (arrList.isEmpty())
        {
            return null;
        }
        
        else
        {
            arr = (String[])arrList.toArray(new String[arrList.size()]);
        }
        
        return arr;
    }
    
    /**
     * 取出重复元素
     * @param sources 需要取出重复元素的List
     * @return
     */
    public static List<String> removeDuplicate(List<String> sources)
    {
        List<String> result = new ArrayList<String>();
        
        for (String s : sources)
        {
            if (Collections.frequency(result, s) < 1)
            {
                result.add(s);
            }
        }
        
        return result;
    }
    
}
