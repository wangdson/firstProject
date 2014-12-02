package com.dxs.Util;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PictureOperate
{
    final Log log = LogFactory.getLog(PictureOperate.class);
    
    /**
     * 
     * 描述：用来缩放图片大小
     * @param FileFrom  原始大图片的路径
     * @param FileTo 生成压缩图片后的路径
     * @param nw 压缩后正方形的边长
     */
    public void PictureCompression(String FileFrom, String FileTo, int nw)
    {
        int w = 0;
        int h = 0;
        int nh = 0;
        double sx = 0.0;
        double sy = 0.0;
        File fi = new File(FileFrom);
        File fo = new File(FileTo);
        AffineTransform transform = new AffineTransform();
        BufferedImage bis;
        try
        {
            bis = ImageIO.read(fi);
            w = bis.getWidth();
            h = bis.getHeight();
            nh = (nw * h) / w;
            sx = (double)nw / w;
            sy = (double)nh / h;
            transform.setToScale(sx, sy);
            
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            ato.filter(bis, bid);
            ImageIO.write(bid, "jpg", fo);
            CalcCutting(FileTo, nw, nh);
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void PicCompress(String FileFrom, String FileTo, int nw)
    {
        int w = 0;
        int h = 0;
        int nh = 0;
        double sx = 0.0;
        double sy = 0.0;
        File fi = new File(FileFrom);
        File fo = new File(FileTo);
        AffineTransform transform = new AffineTransform();
        BufferedImage bis;
        try
        {
            bis = ImageIO.read(fi);
            w = bis.getWidth();
            h = bis.getHeight();
            nh = (nw * h) / w;
            sx = (double)nw / w;
            sy = (double)nh / h;
            transform.setToScale(sx, sy);
            
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            ato.filter(bis, bid);
            ImageIO.write(bid, "png", fo);
            CalcCutting(FileTo, nw, nh);
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 计算裁剪的坐标
     * @param FilePath 文件的路径
     * @param width 待剪裁的图片的宽
     * @param height 待剪裁图片的高
     */
    public void CalcCutting(String FilePath, int width, int height)
    {
        File file = new File(FilePath);
        int px = 0;
        int py = 0;
        int cutlength = 0;
        if (width > height)
        {
            px = (width - height) / 2;
            py = 0;
            cutlength = height;
        }
        
        else if (width == height)
        {
            px = 0;
            py = 0;
            cutlength = height;
        }
        
        else
        {
            px = 0;
            py = (height - width) / 2;
            cutlength = width;
        }
        cut(file, px, py, cutlength, cutlength);
    }
    
    /**
     * 描述：用来剪裁图片,裁剪出一个最大正方形
     * @param file 裁剪的文件
     * @param px 剪裁的起点x
     * @param py 剪裁的起点y
     * @param lx 剪裁的宽
     * @param ly 剪裁的高
     */
    private void cut(File file, int px, int py, int lx, int ly)
    {
        try
        {
            String endName = file.getName();
            endName = endName.substring(endName.lastIndexOf(".") + 1);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(endName);
            ImageReader reader = (ImageReader)readers.next();
            InputStream is = new FileInputStream(file);
            ImageInputStream iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(px, py, lx, ly);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageOutputStream out = ImageIO.createImageOutputStream(new FileOutputStream(file));
            ImageIO.write(bi, endName, out);
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public File cutting(String FilePath, int x, int y, int width, int height)
    {
        File file = new File(FilePath);
        try
        {
            String endName = file.getName();
            endName = endName.substring(endName.lastIndexOf(".") + 1);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(endName);
            ImageReader reader = (ImageReader)readers.next();
            InputStream is = new FileInputStream(file);
            ImageInputStream iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageOutputStream out = ImageIO.createImageOutputStream(new FileOutputStream(file));
            ImageIO.write(bi, endName, out);
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return file;
    }
    
}
