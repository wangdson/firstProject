package com.dxs.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.ParJSON;
import com.dxs.Entity.ShortLink;
import com.dxs.Service.Impl.ShortLinkServiceImpl;
import com.dxs.Service.Intf.ShortLinkService;
import com.dxs.common.Constants;

/**
 * 短链接生成器
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-17]
 */
public class ShortTools
{
    public static void forShortLink(String apkId, String sLongUrl, int flag)
        throws UnsupportedEncodingException
    {
        
        sLongUrl = URLEncoder.encode(sLongUrl, "utf-8");
        // 根据APK的ID拼接出下载地址
        ShortLinkService shortServ = new ShortLinkServiceImpl();
        String httpUrl = Constants.SHORTRQU + sLongUrl + "&access_token=" + Constants.ACCESS_TOKEN;
        String htmlres = getHTML(httpUrl);
        String sShortUrl = "";
        
        try
        {
            sShortUrl = ParJSON.jsonparse(htmlres);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        ShortLink sl = new ShortLink();
        sl.setId(Integer.parseInt(apkId));
        sl.setName(apkId);
        sl.setShortName("");
        sl.setLongUrl(sLongUrl);
        sl.setShortUrl(sShortUrl);
        sl.setFlag(flag);
        
        // 添加短链接
        shortServ.addObject(sl);
    }
    
    public static String getHTML(String httpUrl)
    {
        String html = "";
        try
        {
            URL url = new URL(httpUrl.toString());
            StringBuffer document = new StringBuffer();
            try
            {
                URLConnection urlCon = (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
                String Result = "";
                while ((Result = reader.readLine()) != null)
                {
                    document.append(Result);
                }
                
                html = document.toString();
            }
            catch (IOException e)
            {
                html = "服务未响应";
                
            }
        }
        catch (MalformedURLException e)
        {
            html = "不支持的协议";
            
        }
        return html;
    }
}
