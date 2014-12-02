package com.dxs.Util;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.util.List;

import com.dxs.Entity.PaperBag;
import com.dxs.Entity.UserBag;

public class XmlOut
{
    
    public void BuildPackConfigXMLDoc(String path, String[] boxs)
        throws IOException, JDOMException
    {
        Element root = new Element("list");
        Document doc = new Document(root);
        for (int i = 0; i < boxs.length; i++)
        {
            Element elements = new Element("bag");
            elements.addContent(new Element("path").setText(path + boxs[i] + ".tar"));
            root.addContent(elements);
        }
        Format format = Format.getPrettyFormat();
        XMLOutputter xout = new XMLOutputter(format);
        FileWriter writer =
            new FileWriter(CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/packaged.xml");
        xout.output(doc, writer);
        writer.close();
    }
    
    // path:/WallPaperWeb/ipaper/upload/
    public void BuildObjectXMLDoc(String path, PaperBag pgInfo)
        throws IOException, JDOMException
    {
        Element root = new Element("list");
        Document doc = new Document(root);
        Element elements = new Element("bag");
        elements.addContent(new Element("path").setText(path + pgInfo.getId() + "/" + pgInfo.getBagName()));
        elements.addContent(new Element("id").setText(String.valueOf(pgInfo.getId())));
        elements.addContent(new Element("name").setText(pgInfo.getBagName()));
        elements.addContent(new Element("conver").setText(path + pgInfo.getId() + "/"));
        // elements.addContent(new Element("conver").setText(path+pgInfo.getId()+"/"+pgInfo.getId()+".jpg"));
        elements.addContent(new Element("version").setText(pgInfo.getVersion()));
        elements.addContent(new Element("unionId").setText(pgInfo.getUnionId()));
        
        root.addContent(elements);
        Format format = Format.getPrettyFormat();
        XMLOutputter xout = new XMLOutputter(format);
        FileWriter writer =
            new FileWriter(CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/config.xml");
        xout.output(doc, writer);
        writer.close();
    }
    
    // path:/WallPaperWeb/ipaper/uploaduser/�û�ID/�ļ�ID/
    public void BuildObjectXMLDoc(String path, UserBag pgInfo)
        throws IOException, JDOMException
    {
        Element root = new Element("list");
        Document doc = new Document(root);
        Element elements = new Element("bag");
        elements.addContent(new Element("path").setText(path));
        elements.addContent(new Element("id").setText(String.valueOf(pgInfo.getId())));
        elements.addContent(new Element("name").setText(pgInfo.getBagName()));
        elements.addContent(new Element("version").setText(pgInfo.getVersion()));
        root.addContent(elements);
        Format format = Format.getPrettyFormat();
        XMLOutputter xout = new XMLOutputter(format);
        FileWriter writer =
            new FileWriter(CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps" + path + "config.xml");
        xout.output(doc, writer);
        writer.close();
    }
    
    // path:/WallPaperWeb/ipaper/upload/id/
    public void BuildAutoObjectXMLDoc(String path, PaperBag bookInfo)
        throws IOException, JDOMException
    {
        Element root = new Element("list");
        Document doc = new Document(root);
        Element elements = new Element("bag");
        
        elements.addContent(new Element("id").setText(String.valueOf(bookInfo.getId())));
        if (bookInfo.getBagName().contains(" "))
        {
            
            String bookName = bookInfo.getBagName().replace(" ", "_");
            elements.addContent(new Element("path").setText(path + bookName));
            elements.addContent(new Element("name").setText(bookName));
        }
        else
        {
            elements.addContent(new Element("path").setText(path + bookInfo.getBagName()));
            elements.addContent(new Element("name").setText(bookInfo.getBagName()));
            
        }
        elements.addContent(new Element("conver").setText(path));
        // elements.addContent(new Element("conver").setText(path+bookInfo.getId()+".jpg"));
        elements.addContent(new Element("version").setText(bookInfo.getVersion()));
        elements.addContent(new Element("unionId").setText(bookInfo.getUnionId()));
        root.addContent(elements);
        Format format = Format.getPrettyFormat();
        XMLOutputter xout = new XMLOutputter(format);
        FileWriter writer =
            new FileWriter(CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/config.xml");
        xout.output(doc, writer);
        writer.close();
    }
    
  
    
    public void BuildIsbn2PG(List<PaperBag> bookList)
        throws IOException, JDOMException
    {
        Element root = new Element("list");
        Document doc = new Document(root);
        for (int i = 0; i < bookList.size(); i++)
        {
            Element elements = new Element("paperbag");
            elements.addContent(new Element("id").setText(String.valueOf(bookList.get(i).getId())));
            elements.addContent(new Element("name").setText(bookList.get(i).getBagName()));
            root.addContent(elements);
        }
        Format format = Format.getPrettyFormat();
        // format.setEncoding("utf8");
        XMLOutputter xout = new XMLOutputter(format);
        // ��� user.xml �ļ���
        // xout.output(doc, new FileOutputStream("woshidu.xml"));
        FileWriter writer =
            new FileWriter(CONSTANTS.SERVPATH + "java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/id2pb.xml");
        
        xout.output(doc, writer);
        writer.close();
    }
    
}
