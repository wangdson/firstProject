package com.dxs.Util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConvertImg {
	public  static boolean narrowAndFormateTransfer(String srcPath, String destPath, int height, int width, String formate) {  
	      boolean flag = false;  
		        try {  
        File file = new File(srcPath);  
	            File destFile = new File(destPath);  
	           if (!destFile.getParentFile().exists()) {  
	              destFile.getParentFile().mkdir();  
	           }  
	            BufferedImage src = ImageIO.read(file); // 读入文件  
	           Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);  
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
	           Graphics g = tag.getGraphics();  
         g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
        g.dispose();  
	           flag = ImageIO.write(tag, formate, new FileOutputStream(destFile));// 输出到文件流  
	       } catch (IOException e) {  
	           e.printStackTrace();  
	       }  
	        return flag;  

	    } 
}
