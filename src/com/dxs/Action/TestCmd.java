package com.dxs.Action;


import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.opensymphony.xwork2.ActionSupport;

public class TestCmd extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*File file = new File(CONSTANTS.SERVPATH+"java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/actlog.log");
	WriteLog wl = new WriteLog(file);*/

	
	public String execute() throws Exception {
	/*	if(file.exists()){   
			try {   
				file.delete();  
				file.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}*/
		
		//String cmd="ls -lht aaa.zip";
		//wl.writeTxt("ls -lht命令：1查看文件大小"+cmd);
		//String res=exeCmd(cmd);
		//wl.writeTxt("ls -lht命令1返回值："+res);
		
		

		return null;
	}
	
	public String exeCmd(String cmd) {
		Runtime runtime = Runtime.getRuntime();
		Process proc = null;
		String retStr = "";
		InputStreamReader insReader = null;
		char[] tmpBuffer = new char[1024];
		int nRet = 0;
		
		try {
			proc = runtime.exec(cmd);
			insReader = new InputStreamReader(proc.getInputStream(), Charset.forName("UTF-8"));
			
			while ((nRet = insReader.read(tmpBuffer, 0, 1024)) != -1) {
				retStr += new String(tmpBuffer, 0, nRet);
			}
			
			insReader.close();
			retStr = HTMLEncode(retStr);
		} catch (Exception e) {
			retStr = "<font color=\"red\">bad command \"" + cmd + "\"</font>";
		}
		return retStr;
	}
	public String HTMLEncode(String str) {
		str = str.replaceAll(" ", "&nbsp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\r\n", "<br>");
		
		return str;
	}
	
	
	
	

}
