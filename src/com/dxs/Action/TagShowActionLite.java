package com.dxs.Action;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.dxs.Entity.Category;
import com.dxs.Entity.PaperBag;
import com.dxs.Entity.PaperBag_simple;
import com.dxs.Entity.Tag;
import com.dxs.Service.Impl.CategoryServiceImpl;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.CategoryService;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionSupport;

public class TagShowActionLite extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final Log log = LogFactory.getLog(TagShowActionLite.class);
	private String cateSel;//�������ֵ
	private String tagSel;//��ǩ����ֵ
	private List<PaperBag> booklist;
	private String tagsearch;//������ֵ;
	
	private String cateArea;
	private List<String> cateList;//�������list
	private List<Tag> taglist;//����ǩlist
	private List<String> tagselList;//Ϊ���tagbook.jsp�ı�ǩ�����˵�
	private List<String> leftList;//��list
	private List<String> rightList;//��list
	
	private List<PaperBag_simple> bolist;//����ǩlist
	private List<String> leftList_bo;//��list ��
	private List<String> rightList_bo;//��list ��
	
	//File file = new File(CONSTANTS.SERVPATH+"java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/logtxt.txt");
	//WriteLog wl = new WriteLog(file);
	
	PaperBagService bookServ=new PaperBagServiceImpl();
	CategoryService cateServ=new CategoryServiceImpl();
	TagService tagServ=new TagServiceImpl();
	public String execute() throws Exception {
		forcateList(cateList,cateServ.getAllCateList(0));//�����������˵�
		//---------------------------------------------------------------//����ı���+���� ��ʼ
		leftList=new ArrayList<String>();
		leftList_bo=new ArrayList<String>();
		if (getCateSel()!=null) {
			if (!getCateSel().toString().equals("----")) {
				Category cate=cateServ.searchCateByName(getCateSel());//����������ֻ�����
				taglist=tagServ.getTagByCategoryId(String.valueOf(cate.getCategoryId()),0);//�������ID ���taglist
				bolist=bookServ.getPaperBag_simpleByCateId(cate.getCategoryId());//�������ID ���booklist
				for (int i = 0; i < taglist.size(); i++) {
					leftList.add(taglist.get(i).getTagName());
				}
				setLeftList(leftList);//�������
				for (int i = 0; i < bolist.size(); i++) {
					leftList_bo.add(bolist.get(i).getBagName());
				}
				setLeftList_bo(leftList_bo);//���������
			}
		}else {
			leftList.add("--��ѡ�����--");
			setLeftList(leftList);//�������
			leftList_bo.add("--��ѡ�����--");
			setLeftList_bo(leftList_bo);//���������
		}
		//---------------------------------------------------------------//����ı���+���� ����
		fortagList(rightList,tagServ.getAllTagList());//�������
		forboList(rightList_bo,bookServ.getPaperBag_simpleNoCateId());
		//setTaglist(tagServ.getAllTagList());//�����	
		return "tag";
	}
	
	public String showTagBook() throws Exception {//������ͱ�ǩ�������˵�
		forcateList(cateList,cateServ.getAllCateList(0));//�����������˵�
		tagselList=new ArrayList<String>();
		if (getCateSel()!=null) {
			if (!getCateSel().toString().equals("--��ѡ�����--")) {
				Category cate=cateServ.searchCateByName(getCateSel());//����������ֻ�����
				taglist=tagServ.getTagByCategoryId(String.valueOf(cate.getCategoryId()),0);//�������ID ���taglist
				for (int i = 0; i < taglist.size(); i++) {
					tagselList.add(taglist.get(i).getTagName());
				}
				setTagselList(tagselList);//����ǩ
			}
		}else {
			tagselList.add("--��ѡ�����--");
			setTagselList(tagselList);
		}
		return "tagbook";
	}
	public String tagSelect() throws Exception {//ѡ���ǩ�� ���booklist �����
		if (getTagSel()!=null) {
			if (!getTagSel().toString().equals("--��ѡ�����--")) {
				Tag tag=tagServ.searchTagByName(getTagSel().toString());//����������ǩ��ֵ��ȡ��ǩ����
				List<PaperBag> booklist=bookServ.getPaperBagByTagId(tag.getTagId());//���ݱ�ǩID�����
				setBooklist(listChageCol(booklist));//ת�����е��У��ѱ�ǩ�к������ת��Ϊ����
			}
		}
		showTagBook();
		return "tagbook";
	}
	
	//ת�����е��У��ѱ�ǩ�к������ת��Ϊ����
	public List<PaperBag> listChageCol(List<PaperBag> bolis)
	{
		for (int i = 0; i < bolis.size(); i++) {
			List<Tag> taglis=tagServ.getTagByStr_tagId(bolis.get(i).getTagId());//������ı�ǩ��ID��ñ�ǩlist			
			String tagname="";
			for (int j = 0; j < taglis.size(); j++) {
				tagname=tagname+","+taglis.get(j).getTagName();//ƴ�����б�ǩ
			}
			bolis.get(i).setTagId(tagname.substring(1,tagname.length()));//��downloadnum��ֵ��Ϊ��Ӧ��ǩֵ
			
			if (bolis.get(i).getCateId()==0) {
				bolis.get(i).setSummary("δ����");
			}else {
				Category cate=cateServ.getCateById(bolis.get(i).getCateId());
				bolis.get(i).setSummary(cate.getCategoryName());
			}
		}
		return bolis;
	}
	
	//������ǩ�� ���booklist �����
	public String searchTag() throws Exception {
/*		if(file.exists()){   
			try {   
				file.delete();  
				file.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}*/
		if (getTagsearch()!=null && !getTagsearch().equals("")) {
			log.info("getTagsearch()="+getTagsearch());
			List<String> strlist=tagServ.getIdByName(getTagsearch());//������������ݣ������ر�ǩLIST
			List<PaperBag> booklist=new ArrayList<PaperBag>();//���ô���Ŀlist
			log.info("strlist.size="+strlist.size());
			for (int i = 0; i < strlist.size(); i++) {//ѭ��ÿ����ر�ǩ �������ĿLIST
				log.info("Integer.parseInt(strlist.get(i).toString())="+strlist.get(i).toString());
				List<PaperBag> templist=bookServ.getPaperBagByTagId(Integer.parseInt(strlist.get(i).toString()));//ѭ��ÿ����ر�ǩ �����С��ĿLIST
				//booklist.addAll(templist);
				log.info("templist.size()="+templist.size());
				for (int j = 0; j < templist.size(); j++) {//��ÿ��С��ĿLIST�еĶ���ŵ�����ĿLIST��
					log.info("templist.get(j)="+templist.get(j).getBagName());
					if(!booklist.contains(templist.get(j)))
					{
						booklist.add(templist.get(j));
					}
				}
			}
			setBooklist(listChageCol(booklist));
		}
		showTagBook();
		return "tagbook";
	}
	
		
	
	
	
	public String Edit() throws Exception{
/*		forcateList(cateList,cateServ.getAllCateList());//�����������˵�
		//---------------------------------------------------------------//����ı���+���� ��ʼ
		leftList=new ArrayList<String>();
		if (getCateSel()!=null) {
			if (!getCateSel().toString().equals("--��������--")) {
				String tagname="";
				Category cate=cateServ.searchCateByName(getCateSel());//����������ֻ�����
				taglist=tagServ.getTagByCategoryId(String.valueOf(cate.getCategoryId()));//�������ID ���taglist
				for (int i = 0; i < taglist.size(); i++) {
					tagname=tagname+taglist.get(i).getTagName()+"\n";
				}
				setCateArea(tagname);//����ı���
			}
		}else {
			leftList.add("--��ѡ�����--");
		}*/
		return "tagEdit";
	} 
	
	public void forcateList(List<String> fromlist,List<Category> tolist)//�����������˵�
	{
		fromlist=new ArrayList<String>();
		if (tolist!=null) {
			for (int i = 0; i < tolist.size(); i++) {
				fromlist.add(tolist.get(i).getCategoryName());
			}	
		}else {
			fromlist.add("");
		}
		setCateList(fromlist);
	}
	public void fortagList(List<String> fromlist,List<Tag> tolist)//����ǩ
	{
		fromlist=new ArrayList<String>();
		if (tolist!=null) {
			for (int i = 0; i < tolist.size(); i++) {
				fromlist.add(tolist.get(i).getTagName());
			}	
		}else {
			fromlist.add("");
		}
		setRightList(fromlist);
	}
	public void forboList(List<String> fromlist,List<PaperBag_simple> tolist)//�����
	{
		fromlist=new ArrayList<String>();
		if (tolist!=null) {
			for (int i = 0; i < tolist.size(); i++) {
				fromlist.add(tolist.get(i).getBagName());
			}	
		}else {
			fromlist.add("");
		}
		setRightList_bo(fromlist);
	}
	
	
	
	
	public String getCateSel() {
		return cateSel;
	}
	public void setCateSel(String cateSel) {
		this.cateSel = cateSel;
	}
	public List<String> getCateList() {
		return cateList;
	}
	public void setCateList(List<String> cateList) {
		this.cateList = cateList;
	}
	public List<Tag> getTaglist() {
		return taglist;
	}
	public void setTaglist(List<Tag> taglist) {
		this.taglist = taglist;
	}
	public List<String> getLeftList() {
		return leftList;
	}
	public void setLeftList(List<String> leftList) {
		this.leftList = leftList;
	}
	public void setRightList(List<String> rightList) {
		this.rightList = rightList;
	}
	public List<String> getRightList() {
		return rightList;
	}

	public void setCateArea(String cateArea) {
		this.cateArea = cateArea;
	}

	public String getCateArea() {
		return cateArea;
	}


	public void setRightList_bo(List<String> rightList_bo) {
		this.rightList_bo = rightList_bo;
	}

	public List<String> getRightList_bo() {
		return rightList_bo;
	}

	public void setBolist(List<PaperBag_simple> bolist) {
		this.bolist = bolist;
	}

	public List<PaperBag_simple> getBolist() {
		return bolist;
	}
	public List<String> getLeftList_bo() {
		return leftList_bo;
	}

	public void setLeftList_bo(List<String> leftListBo) {
		leftList_bo = leftListBo;
	}

	public void setTagselList(List<String> tagselList) {
		this.tagselList = tagselList;
	}

	public List<String> getTagselList() {
		return tagselList;
	}

	public void setTagSel(String tagSel) {
		this.tagSel = tagSel;
	}

	public String getTagSel() {
		return tagSel;
	}

	public void setBooklist(List<PaperBag> booklist) {
		this.booklist = booklist;
	}

	public List<PaperBag> getBooklist() {
		return booklist;
	}

	public void setTagsearch(String tagsearch) {
		this.tagsearch = tagsearch;
	}

	public String getTagsearch() {
		return tagsearch;
	}



}
