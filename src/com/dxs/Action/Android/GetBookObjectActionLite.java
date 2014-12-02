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
		
		
		//���6����� 
		List<Tag_simple> taglist=tagServ.getSimpleTag(6);//0�ǻ�����У�>0 �����ƻ�þ��������
		wl.writeTxt("ѭ����ǩ��ʼ,ѭ������i");
			for (int i = 0; i < taglist.size(); i++) {
				wl.writeTxt("��ñ�ǩѭ�������������жϱ�ǩ���Ƿ��б�ֽ������"+i+"����ǩ��"+taglist.get(i).getTagName());
				List<PaperBag> tagbook=bookServ.getPaperBagByTagId_cutsum(0,taglist.get(i).getTagId());
				if (tagbook!=null && tagbook.size()!=0) {
					wl.writeTxt("tagbook.size="+tagbook.size());
					wl.writeTxt("��"+i+"����ǩ��"+taglist.get(i).getTagName()+"�����������У������ǣ�"+tagbook.get(0).getBagName());
					Tag_Contain tagcon=new Tag_Contain();
					
					tagcon.setTagId(taglist.get(i).getTagId());
					tagcon.setTagName(taglist.get(i).getTagName());
					
					
					
					
					if (i==0) 
					{
						wl.writeTxt("��ǩѭ������J==0������һ����ǩ�µ���ֱ�Ӳ���");
						//���1����
						//wl.writeTxt("���ĳ����ǩһ���飬�����ǣ���="+catlist.get(i).getCategoryId()+",��ǩ="+taglist.get(j).getTagId());
						tagcon.setBook(bookServ.getPaperBag_simpleByTagId(catlist.get(i).getCategoryId(),taglist.get(j).getTagId(),1));
						tagconlist.add(tagcon);
						//wl.writeTxt("�õ���ǩ����󷵻��ֶ������"+tagconlist.get(j).getTagName());
					}else 
					{
						wl.writeTxt("��ǩѭ������J>0,���ڶ�����ǩ��ʼ������else��");
						//���1����
						//wl.writeTxt("���ĳ����ǩһ���飬�����ǣ���="+catlist.get(i).getCategoryId()+",��ǩ="+taglist.get(j).getTagId());
						List<PaperBag_simple> bosl=bookServ.getPaperBag_simpleByTagIdAndpng(catlist.get(i).getCategoryId(),taglist.get(j).getTagId(),0);
						wl.writeTxt("�жϵڶ�����ǩ��ʼ���˱�ǩ�����������Ŀ�Ƿ�>��1");
						if (bosl.size()>1) 
						{
							wl.writeTxt("1������,������ѭ��");
							for (int k = 0; k < bosl.size(); k++) 
							{
								wl.writeTxt("ѭ���ж���һ����ǩ���飬�Ƿ��е�ǰ��ǩ�µ���");
								if (tagconlist.get(i-1).getBook().contains(bosl.get(k))) 
								{
									wl.writeTxt("������remove����");
									bosl.remove(k);
									
								}else {
									wl.writeTxt("������������");
								}
								
							}
							tagcon.setBook(bosl);
							tagconlist.add(tagcon);
						}else 
						{
							wl.writeTxt("ֻ��1������0����ֱ�Ӳ���");
							tagcon.setBook(bosl);
							tagconlist.add(tagcon);
						}
						
						//wl.writeTxt("�õ���ǩ����󷵻��ֶ������"+tagconlist.get(j).getTagName());
					}
					
					
					
					
					
					//�������ID���3����ǩ
					//������ǰ��2������Ա����˳�����һ�������á���ADD��ȥ
					List<Tag_simple> taglist=tagServ.getSimpleTagByCategoryId(String.valueOf(catlist.get(i).getCategoryId()),2);//0�ǻ�����У�>0 �����ƻ�þ��������
					if (taglist!=null && taglist.size()!=0) {
						
						Tag_simple ts=getRandTag(taglist,catlist.get(i).getCategoryId());//
						taglist.add(ts);
						List<Tag_Contain> tagconlist=new ArrayList<Tag_Contain>();
						wl.writeTxt("ѭ����ǩ��ʼ,ѭ������J");
						for (int j = 0; j < taglist.size(); j++) {
							Tag_Contain tagcon=new Tag_Contain();
							tagcon.setTagId(taglist.get(j).getTagId());
							tagcon.setTagName(taglist.get(j).getTagName());
							wl.writeTxt("�жϱ�ǩѭ������J=��0");
							if (j==0) 
							{
								wl.writeTxt("��ǩѭ������J==0������һ����ǩ�µ���ֱ�Ӳ���");
								//���1����
								//wl.writeTxt("���ĳ����ǩһ���飬�����ǣ���="+catlist.get(i).getCategoryId()+",��ǩ="+taglist.get(j).getTagId());
								tagcon.setBook(bookServ.getPaperBag_simpleByTagId(catlist.get(i).getCategoryId(),taglist.get(j).getTagId(),1));
								tagconlist.add(tagcon);
								//wl.writeTxt("�õ���ǩ����󷵻��ֶ������"+tagconlist.get(j).getTagName());
							}else 
							{
								wl.writeTxt("��ǩѭ������J>0,���ڶ�����ǩ��ʼ������else��");
								//���1����
								//wl.writeTxt("���ĳ����ǩһ���飬�����ǣ���="+catlist.get(i).getCategoryId()+",��ǩ="+taglist.get(j).getTagId());
								List<PaperBag_simple> bosl=bookServ.getPaperBag_simpleByTagIdAndpng(catlist.get(i).getCategoryId(),taglist.get(j).getTagId(),0);
								wl.writeTxt("�жϵڶ�����ǩ��ʼ���˱�ǩ�����������Ŀ�Ƿ�>��1");
								if (bosl.size()>1) 
								{
									wl.writeTxt("1������,������ѭ��");
									for (int k = 0; k < bosl.size(); k++) 
									{
										wl.writeTxt("ѭ���ж���һ����ǩ���飬�Ƿ��е�ǰ��ǩ�µ���");
										if (tagconlist.get(j-1).getBook().contains(bosl.get(k))) 
										{
											wl.writeTxt("������remove����");
											bosl.remove(k);
											
										}else {
											wl.writeTxt("������������");
										}
										
									}
									tagcon.setBook(bosl);
									tagconlist.add(tagcon);
								}else 
								{
									wl.writeTxt("ֻ��1������0����ֱ�Ӳ���");
									tagcon.setBook(bosl);
									tagconlist.add(tagcon);
								}
								
								//wl.writeTxt("�õ���ǩ����󷵻��ֶ������"+tagconlist.get(j).getTagName());
							}

						}
						catecon.setTag(tagconlist);
					}else {
						catecon.setTag(null);
					}

					
					
					cateconlist.add(catecon);
					
				}else {
					wl.writeTxt("��"+i+"���ࣺ"+catlist.get(i).getCategoryName()+"��û��û��û��û��û��û��û��");
					cateconlist.add(null);
				}				
			}
			DataObj.setCate(cateconlist);
		

		
		for (int i = 0; i < cateconlist.size(); i++) {
			//wl.writeTxt("�õ�������󷵻��ֶ������"+cateconlist.get(i).getCategoryName());
			
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
		//��������������ӱ�ǩ
		List<Tag_simple> allSubTag=tagServ.getSimpleTagByCategoryId(String.valueOf(cateId), 0);
		Tag_simple ts=new Tag_simple();
		//����������һ���ӱ�ǩ �������봫����taglist����ͬ
		*//**
		 * ����ĳһ�����������㷨��
		 * int a=(max-min)*rand()+min;
		 * Ҳ�������ݿ���ֱ��������������ֻ���������±���ȡһ������±ꡣ
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