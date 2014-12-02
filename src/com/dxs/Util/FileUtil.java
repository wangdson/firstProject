package com.dxs.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件操作工具类
 * 
 * @author 姓名 工号
 * @version [版本号, 2014-6-25]
 */
public class FileUtil
{
    /**
     * 拷贝文件方法
     * 
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile)
        throws IOException
    {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try
        {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1)
            {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        }
        finally
        {
            if (inBuff != null)
            {
                inBuff.close();
            }
            
            if (outBuff != null)
            {
                outBuff.close();
            }
        }
    }
    
    /**
     * 拷贝目录方法
     * 
     * @param sourceDir
     * @param targetDir
     * @throws IOException
     */
    public static void copyDirectiory(String sourceDir, String targetDir)
        throws IOException
    {
        (new File(targetDir)).mkdirs();
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++)
        {
            if (file[i].isFile())
            {
                File sourceFile = file[i];
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory())
            {
                String dir1 = sourceDir + "/" + file[i].getName();
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }
    
    /**
     * 删除目录方法
     * 
     * @param f 需要删除目录的文件对象
     * @param delparaent 是否需要将父目录一并删除
     * @throws IOException
     */
    public static void del(File f, Boolean delparaent)
        throws IOException
    {
        if (f.exists() && f.isFile())
        {
            f.delete();
            return;
        }
        
        if (f.exists() && f.isDirectory())
        {
            if (f.listFiles().length == 0)
            {
                f.delete();
            }
            
            else
            {
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++)
                {
                    if (delFile[j].isDirectory())
                    {
                        del(new File(delFile[j].getAbsolutePath()), delparaent);
                    }
                    delFile[j].delete();
                }
                
                if (delparaent)
                {
                    if (f.listFiles().length == 0)
                    {
                        f.delete();
                    }
                }
            }
        }
    }
    
    /**
     * 重命名
     * 
     * @param src
     * @param dest
     * @return
     */
    public static boolean renameToNewFile(String src, String dest)
    {
        File srcDir = new File(src);
        boolean isOk = srcDir.renameTo(new File(dest));
        return isOk;
    }
    
    /**
     * 功能：把 sourceDir 目录下的所有文件进行 zip 格式的压缩，保存为指定 zip 文件 create date:2009- 6- 9 author:Administrator
     * 
     * @param sourceDir E:// 我的备份
     * @param zipFile 格式： E://stu //zipFile.zip 注意：加入 zipFile 我们传入的字符串值是 ： "E://stu //" 或者 "E://stu " 如果 E 盘已经存在 stu
     *            这个文件夹的话，那么就会出现 java.io.FileNotFoundException: E:/stu ( 拒绝访问。 ) 这个异常，所以要注意正确传参调用本函数哦
     * 
     */
    public static void zip(String sourceDir, String zipFile)
    {
        OutputStream os;
        try
        {
            os = new FileOutputStream(zipFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            ZipOutputStream zos = new ZipOutputStream(bos);
            
            File file = new File(sourceDir);
            
            String basePath = null;
            if (file.isDirectory())
            {
                basePath = file.getPath();
            }
            else
            {
                basePath = file.getParent();
            }
            
            zipFile(file, basePath, zos);
            
            zos.closeEntry();
            zos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 压缩文件 create date:2009- 6- 9 author:Administrator
     * 
     * @param source
     * @param basePath
     * @param zos
     * @throws IOException
     */
    private static void zipFile(File source, String basePath, ZipOutputStream zos)
    {
        File[] files = new File[0];
        
        if (source.isDirectory())
        {
            files = source.listFiles();
        }
        else
        {
            files = new File[1];
            files[0] = source;
        }
        
        String pathName;
        byte[] buf = new byte[1024];
        int length = 0;
        try
        {
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    pathName = file.getPath().substring(basePath.length() + 1) + "/";
                    zos.putNextEntry(new ZipEntry(pathName));
                    zipFile(file, basePath, zos);
                }
                else
                {
                    pathName = file.getPath().substring(basePath.length() + 1);
                    InputStream is = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    zos.putNextEntry(new ZipEntry(pathName));
                    while ((length = bis.read(buf)) > 0)
                    {
                        zos.write(buf, 0, length);
                    }
                    is.close();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 解压 zip 文件，注意不能解压 rar 文件哦，只能解压 zip 文件 解压 rar 文件 会出现 java.io.IOException: Negative seek offset 异常 create date:2009-
     * 6- 9 author:Administrator
     * 
     * @param zipfile zip 文件，注意要是正宗的 zip 文件哦，不能是把 rar 的直接改为 zip 这样会出现 java.io.IOException: Negative seek offset 异常
     * @param destDir
     * @throws IOException
     */
    public static void unZip(String zipfile, String destDir)
        throws IOException
    {
        
        destDir = destDir.endsWith("//") ? destDir : destDir + "//";
        byte b[] = new byte[1024];
        int length;
        
        ZipFile zipFile;
        zipFile = new ZipFile(new File(zipfile));
        Enumeration enumeration = zipFile.getEntries();
        ZipEntry zipEntry = null;
        
        while (enumeration.hasMoreElements())
        {
            zipEntry = (ZipEntry)enumeration.nextElement();
            File loadFile = new File(destDir + zipEntry.getName());
            
            if (zipEntry.isDirectory())
            {
                // 这段都可以不要，因为每次都貌似从最底层开始遍历的
                loadFile.mkdirs();
            }
            else
            {
                if (!loadFile.getParentFile().exists())
                {
                    loadFile.getParentFile().mkdirs();
                }
                
                OutputStream outputStream = new FileOutputStream(loadFile);
                InputStream inputStream = zipFile.getInputStream(zipEntry);
                
                while ((length = inputStream.read(b)) > 0)
                {
                    outputStream.write(b, 0, length);
                }
            }
        }
    }
    
    /**
     * 递归调用此方法列出目录下的所有目录和文件
     * 
     * @param file
     * @param fileList
     * @param type 文件类型 如果为null则不区分
     * @param one 是否只取1个
     * @return 文件列表
     * @throws UnsupportedEncodingException
     */
    public static void getFile(File file, List<File> fileList, String type, Boolean one)
        throws UnsupportedEncodingException
    {
        if (file != null)
        {
            if (file.isDirectory())
            {
                // 列出全部的文件
                File f[] = file.listFiles();
                if (f != null)
                {
                    for (int i = 0; i < f.length; i++)
                    {
                        getFile(f[i], fileList, type, one);// 递归调用自身
                    }
                }
            }
            else
            {
                // 如果没有限制类型，则直接加入
                if (type == null)
                {
                    fileList.add(file);
                }
                
                else
                {
                    // 如果是要获取的文件后缀名
                    if (getFileExtension(file).equals(type))
                    {
                        fileList.add(file);
                        // 取了1个直接退出
                        if (one)
                        {
                            return;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 查找文件
     * 
     * @param name 名称
     * @param dir 查找目录
     * @return 查找结果
     */
    public static void findFile(String findname, File file, List<File> coverFile)
    {
        if (file != null)
        {
            if (file.isDirectory())
            {
                // 列出全部的文件
                File f[] = file.listFiles();
                if (f != null)
                {
                    for (int i = 0; i < f.length; i++)
                    {
                        // 递归调用自身
                        findFile(findname, f[i], coverFile);
                    }
                }
            }
            
            else
            {
                // 如果找到对应的文件
                if (file.getName().equals(findname))
                {
                    coverFile.add(file);
                }
            }
        }
    }
    
    /**
     * 获取文件后缀名
     * 
     * @param file 文件对象
     * @return 文件后缀名
     */
    public static String getFileExtension(File file)
    {
        String filename = file.getName();
        
        // 截取到文件的后缀
        String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);
        return fileExtension;
    }
    
    /**
     * 获取一级文件目录
     */
    public static void getFirstFileList(List<File> filelist, File home)
    {
        if (home.isDirectory())
        {
            // 列出全部的文件
            File f[] = home.listFiles();
            if (f != null)
            {
                for (int i = 0; i < f.length; i++)
                {
                    if (f[i].isDirectory())
                    {
                        filelist.add(f[i]);
                    }
                }
            }
        }
    }
    
    /**
     * 解压.zip文件
     * 
     * @param sourceZip
     * @param destDir
     * @throws Exception
     */
    public static void unzip(File sourceZip, File destDir)
        throws Exception
    {
        try
        {
            Project p = new Project();
            Expand e = new Expand();
            e.setProject(p);
            e.setSrc(sourceZip);
            e.setOverwrite(false);
            e.setDest(destDir);
            
            // 根据上传时候文件编码去设置
            e.setEncoding("GBK");
            e.execute();
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    
    /**
     * 压缩文件
     */
    public static void compress(String srcPathName, File toZipFile)
    {
        File srcdir = new File(srcPathName);
        if (!srcdir.exists())
        {
            throw new RuntimeException(srcPathName + "不存在！");
        }
        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(toZipFile);
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(srcdir);
        zip.addFileset(fileSet);
        zip.execute();
    }
}
