package com.dxs.help;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.dxs.Util.MySqlUtil;

/**
 * 配置文件读取工具类
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-25]
 */
public class ProfilesParameterHelpUtils
{
    /**
     * 读取app版本号
     * 
     * @return 版本号
     */
    @SuppressWarnings("rawtypes")
    public static String readAppversion(String xmlpath)
        throws DocumentException
    {
        String ver = "100";
        Iterator iter = null;
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(xmlpath));
        if (document != null)
        {
            Element root = document.getRootElement();
            for (iter = root.elementIterator(); iter.hasNext();)
            {
                Element element = (Element)iter.next();
                String name = element.getName();
                
                if (name.equals("app"))
                {
                    ver = element.getText();
                }
            }
        }
        return ver;
    }
    
    /**
     * 更改APP版本号
     * 
     * @param appver
     * @param xmlpath
     * @throws Exception
     */
    public static void BuildAppVersionXMLDoc(String appver, String xmlpath)
        throws Exception
    {
        
        // 创建saxReader对象
        SAXReader reader = new SAXReader();
        
        // 通过read方法读取一个文件 转换成Document对象
        Document document = reader.read(new File(xmlpath));
        
        // 获取根节点元素对象
        Element node = document.getRootElement();
        
        // 遍历所有的元素节点
        listNodes(node);
        
        Element element = node.element("app");
        element.setText(appver);
        writer(document, xmlpath);
    }
    
    /**
     * 把document对象写入新的文件
     * 
     * @param document
     * @throws Exception
     */
    public static void writer(Document document, String path)
        throws Exception
    {
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置编码
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(path)), "UTF-8"), format);
        // 写入
        writer.write(document);
        // 立即写入
        writer.flush();
        // 关闭操作
        writer.close();
    }
    
    /**
     * 遍历当前节点元素下面的所有(元素的)子节点
     * 
     * @param node
     */
    @SuppressWarnings("unchecked")
    public static void listNodes(Element node)
    {
        // 获取当前节点的所有属性节点
        List<Attribute> list = node.attributes();
        // 遍历属性节点
        for (Attribute attr : list)
        {
            System.out.println(attr.getText() + "-----" + attr.getName() + "---" + attr.getValue());
        }
        
        if (!(node.getTextTrim().equals("")))
        {
            System.out.println("文本内容：：：：" + node.getText());
        }
        
        // 当前节点下面子节点迭代器
        Iterator<Element> it = node.elementIterator();
        
        // 遍历
        while (it.hasNext())
        {
            // 获取某个子节点对象
            Element e = it.next();
            // 对子节点进行遍历
            listNodes(e);
        }
    }
    
    /**
     * 配置文件加载
     */
    public static Properties loadConfigFile(String configFileName)
    {
        Properties properties = new Properties();
        try
        {
            InputStream inputStream = MySqlUtil.class.getClassLoader().getResourceAsStream(configFileName);
            properties.load(inputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return properties;
    }
    
    /**
     * 根据属性获取属性值
     * 
     * @return
     */
    public static String getConfig(Properties properties, String property)
    {
        String propertyValue = null;
        
        if (properties != null && !properties.isEmpty())
        {
            propertyValue = properties.getProperty(property);
        }
        
        return propertyValue;
    }
}
