package com.dxs.Util;

import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * ubuntu侧命令执行方法
 * @author  姓名 工号
 * @version  [版本号, 2014-6-25]
 */
public class ExeCmd
{
    public String exeCmd(String cmd)
    {
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        String retStr = "";
        InputStreamReader insReader = null;
        char[] tmpBuffer = new char[1024];
        int nRet = 0;
        
        try
        {
            proc = runtime.exec(cmd);
            insReader = new InputStreamReader(proc.getInputStream(), Charset.forName("UTF-8"));
            
            while ((nRet = insReader.read(tmpBuffer, 0, 1024)) != -1)
            {
                retStr += new String(tmpBuffer, 0, nRet);
            }
            
            insReader.close();
            retStr = HTMLEncode(retStr);
        }
        catch (Exception e)
        {
            retStr = "<font color=\"red\">bad command \"" + cmd + "\"</font>";
        }
        return retStr;
    }
    
    public String HTMLEncode(String str)
    {
        str = str.replaceAll(" ", "&nbsp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\r\n", "<br>");
        return str;
    }
}
