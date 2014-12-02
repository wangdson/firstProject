/*package com.dxs.Action.Android;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.sf.json.JSONObject;


import com.dxs.Entity.PaperBag;
import com.dxs.Entity.PaperBag_simple;
import com.dxs.Entity.Category;
import com.dxs.Entity.Category_Contain;
import com.dxs.Entity.DataObject;
import com.dxs.Entity.Tag_Contain;
import com.dxs.Entity.Tag_simple;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.CategoryServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.CategoryService;
import com.dxs.Service.Intf.TagService;
import com.dxs.Util.CONSTANTS;
import com.dxs.Util.WriteLog;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetBookObjectActionLite extends ActionSupport{

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;
	

	File file = new File(CONSTANTS.SERVPATH+"java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/GetBookObject.log");
	WriteLog wl = new WriteLog(file);
	 (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 
	public String execute() throws Exception {
		
		if(file.exists()){   
			try {   
				file.delete();  
				file.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
			
		//CategoryService cateServ=new CategoryServiceImpl();
		TagService tagServ=new TagServiceImpl();
		PaperBagService bookServ=new PaperBagServiceImpl();

		
		DataObject DataObj=new DataObject();
		
		//List<Category_Contain> cateconlist=new ArrayList<Category_Contain>();
		List<Tag_Contain> tagconlist=new ArrayList<Tag_Contain>();
		
		
		//获得6个类别 
		List<Tag_simple> taglist=tagServ.getSimpleTag(6);//0是获得所有，>0 是限制获得具体多少条
		wl.writeTxt("循环标签开始,循环代码i");
			for (int i = 0; i < taglist.size(); i++) {
				wl.writeTxt("获得标签循环进来，下面判断标签下是否有壁纸包，第"+i+"个标签："+taglist.get(i).getTagName());
				List<PaperBag> tagbook=bookServ.getPaperBagByTagId_cutsum(0,taglist.get(i).getTagId());
				if (tagbook!=null && tagbook.size()!=0) {
					wl.writeTxt("tagbook.size="+tagbook.size());
					wl.writeTxt("第"+i+"个标签："+taglist.get(i).getTagName()+"有有有有有有，包名是："+tagbook.get(0).getBagName());
					Tag_Contain tagcon=new Tag_Contain();
					
					tagcon.setTagId(taglist.get(i).getTagId());
					tagcon.setTagName(taglist.get(i).getTagName());
					
					
					
					
					if (i==0) 
					{
						wl.writeTxt("标签循环代码J==0，即第一个标签下的书直接插入");
						//获得1本书
						//wl.writeTxt("获得某个标签一本书，参数是：类="+catlist.get(i).getCategoryId()+",标签="+taglist.get(j).getTagId());
						tagcon.setBook(bookServ.getPaperBag_simpleByTagId(catlist.get(i).getCategoryId(),taglist.get(j).getTagId(),1));
						tagconlist.add(tagcon);
						//wl.writeTxt("得到标签结果后返回手动输出："+tagconlist.get(j).getTagName());
					}else 
					{
						wl.writeTxt("标签循环代码J>0,即第二个标签开始都进该else块");
						//获得1本书
						//wl.writeTxt("获得某个标签一本书，参数是：类="+catlist.get(i).getCategoryId()+",标签="+taglist.get(j).getTagId());
						List<PaperBag_simple> bosl=bookServ.getPaperBag_simpleByTagIdAndpng(catlist.get(i).getCategoryId(),taglist.get(j).getTagId(),0);
						wl.writeTxt("判断第二个标签开始，此标签下所含书的数目是否>？1");
						if (bosl.size()>1) 
						{
							wl.writeTxt("1本以上,接着做循环");
							for (int k = 0; k < bosl.size(); k++) 
							{
								wl.writeTxt("循环判断上一个标签的书，是否含有当前标签下的书");
								if (tagconlist.get(i-1).getBook().contains(bosl.get(k))) 
								{
									wl.writeTxt("包含，remove该书");
									bosl.remove(k);
									
								}else {
									wl.writeTxt("不包含，保留");
								}
								
							}
							tagcon.setBook(bosl);
							tagconlist.add(tagcon);
						}else 
						{
							wl.writeTxt("只有1本，或0本，直接插入");
							tagcon.setBook(bosl);
							tagconlist.add(tagcon);
						}
						
						//wl.writeTxt("得到标签结果后返回手动输出："+tagconlist.get(j).getTagName());
					}
					
					
					
					
					
					//根据类别ID获得3个标签
					//现在是前面2个管理员排列顺序，最后一个随机获得。在ADD进去
					List<Tag_simple> taglist=tagServ.getSimpleTagByCategoryId(String.valueOf(catlist.get(i).getCategoryId()),2);//0是获得所有，>0 是限制获得具体多少条
					if (taglist!=null && taglist.size()!=0) {
						
						Tag_simple ts=getRandTag(taglist,catlist.get(i).getCategoryId());//
						taglist.add(ts);
						List<Tag_Contain> tagconlist=new ArrayList<Tag_Contain>();
						wl.writeTxt("循环标签开始,循环代码J");
						for (int j = 0; j < taglist.size(); j++) {
							Tag_Contain tagcon=new Tag_Contain();
							tagcon.setTagId(taglist.get(j).getTagId());
							tagcon.setTagName(taglist.get(j).getTagName());
							wl.writeTxt("判断标签循环代码J=？0");
							if (j==0) 
							{
								wl.writeTxt("标签循环代码J==0，即第一个标签下的书直接插入");
								//获得1本书
								//wl.writeTxt("获得某个标签一本书，参数是：类="+catlist.get(i).getCategoryId()+",标签="+taglist.get(j).getTagId());
								tagcon.setBook(bookServ.getPaperBag_simpleByTagId(catlist.get(i).getCategoryId(),taglist.get(j).getTagId(),1));
								tagconlist.add(tagcon);
								//wl.writeTxt("得到标签结果后返回手动输出："+tagconlist.get(j).getTagName());
							}else 
							{
								wl.writeTxt("标签循环代码J>0,即第二个标签开始都进该else块");
								//获得1本书
								//wl.writeTxt("获得某个标签一本书，参数是：类="+catlist.get(i).getCategoryId()+",标签="+taglist.get(j).getTagId());
								List<PaperBag_simple> bosl=bookServ.getPaperBag_simpleByTagIdAndpng(catlist.get(i).getCategoryId(),taglist.get(j).getTagId(),0);
								wl.writeTxt("判断第二个标签开始，此标签下所含书的数目是否>？1");
								if (bosl.size()>1) 
								{
									wl.writeTxt("1本以上,接着做循环");
									for (int k = 0; k < bosl.size(); k++) 
									{
										wl.writeTxt("循环判断上一个标签的书，是否含有当前标签下的书");
										if (tagconlist.get(j-1).getBook().contains(bosl.get(k))) 
										{
											wl.writeTxt("包含，remove该书");
											bosl.remove(k);
											
										}else {
											wl.writeTxt("不包含，保留");
										}
										
									}
									tagcon.setBook(bosl);
									tagconlist.add(tagcon);
								}else 
								{
									wl.writeTxt("只有1本，或0本，直接插入");
									tagcon.setBook(bosl);
									tagconlist.add(tagcon);
								}
								
								//wl.writeTxt("得到标签结果后返回手动输出："+tagconlist.get(j).getTagName());
							}

						}
						catecon.setTag(tagconlist);
					}else {
						catecon.setTag(null);
					}

					
					
					cateconlist.add(catecon);
					
				}else {
					wl.writeTxt("第"+i+"个类："+catlist.get(i).getCategoryName()+"，没有没有没有没有没有没有没有");
					cateconlist.add(null);
				}				
			}
			DataObj.setCate(cateconlist);
		

		
		for (int i = 0; i < cateconlist.size(); i++) {
			//wl.writeTxt("得到类别结果后返回手动输出："+cateconlist.get(i).getCategoryName());
			
		}
		
		
		
		
		
		
		JSONObject json = JSONObject.fromObject(DataObj);
		Map<String, Object> m;
		m = ActionContext.getContext().getSession();
		m.put("varlist", json.toString());

		return "GetTag";
	}
	
	public Tag_simple getRandTag(List<Tag_simple> taglist,int cateId)
	{
		TagService tagServ=new TagServiceImpl();
		//根据类别获得所有子标签
		List<Tag_simple> allSubTag=tagServ.getSimpleTagByCategoryId(String.valueOf(cateId), 0);
		Tag_simple ts=new Tag_simple();
		//随机获得其中一个子标签 ，并且与传来的taglist不相同
		*//**
		 * 生成某一区间的随机数算法：
		 * int a=(max-min)*rand()+min;
		 * 也可在数据库中直接做。但是这里只能在数组下标中取一个随机下标。
		 * SELECT * FROM tag WHERE tagId = floor(((SELECT MAX(tagId) FROM tag where categoryId='9')-(SELECT MIN(tagId) FROM tag where categoryId='9')) * RAND() + (SELECT MIN(tagId) FROM tag where categoryId='9')) limit 1
		 *//*
		do {
			Random rad=new Random();
			int subId=rad.nextInt(allSubTag.size());
			ts=allSubTag.get(subId);
			
		} while (taglist.contains(ts));
		return ts;
	}
	
	
	

}
*/