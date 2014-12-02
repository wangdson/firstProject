package com.dxs.Action;


import java.util.List;


import com.dxs.Entity.Category;
import com.dxs.Entity.PaperBag_simple;

import com.dxs.Service.Impl.CategoryServiceImpl;
import com.dxs.Service.Impl.PaperBagServiceImpl;
import com.dxs.Service.Impl.TagServiceImpl;
import com.dxs.Service.Intf.CategoryService;
import com.dxs.Service.Intf.PaperBagService;
import com.dxs.Service.Intf.TagService;
import com.opensymphony.xwork2.ActionSupport;

public class TagCateActionLite extends ActionSupport{

	/**
	 * ��ǩ������࣬����ɾ��
	 */
	private static final long serialVersionUID = 1L;

	private String cateName;//�ı��� �������
	private String cateSel;//�������ֵ
	private List<String> leftSideRecords;//��list  ����TagShowAction,��Ϊ�Ǳ������list ��ȡҪ��jsp�ؼ��е�nameֵ
	private List<String> rightSideRecords;//��list ����TagShowAction,��Ϊ�Ǳ������list ��ȡҪ��jsp�ؼ��е�nameֵ
	
	public String AddCate() throws Exception {
		if (getCateName()!=null) {
			CategoryService cateServ=new CategoryServiceImpl();//����������
			Category cate=cateServ.searchCateByName(getCateName().trim().toString());//�鿴������Ƿ���ڣ������ڲ����
			if (cate==null) {
				int maxid=cateServ.findMaxCategoryId();//���������ID
				cate=new Category();//��������𲢸�ֵ
				cate.setCategoryId(maxid+1);
				cate.setCategoryName(getCateName().trim().toString());
				cateServ.addCategory(cate);//add��𷽷�
			}
		}
		return "TagShowAction";
	}
	
	//File file = new File(CONSTANTS.SERVPATH+"java/apache-tomcat-7.0.27/webapps/WallPaperWeb/ipaper/logtxt.log");
	//WriteLog wl = new WriteLog(file);
	
	public String AddTag() throws Exception {
		if (getCateSel()!=null && getLeftSideRecords()!=null) {//1.�������Ϊ�գ����в�Ϊ��ʱ
			if(!getCateSel().toString().equals("--��������--")) {//2.�������Ϊ����������
				PaperBagService bookServ=new PaperBagServiceImpl();
				int boxs[] = new int[getLeftSideRecords().size()]; 
				//wl.writeTxt("�����б�ѡ���ֵΪ��"+getCateSel().toString());
				CategoryService cateServ=new CategoryServiceImpl();//����������
				TagService tagServ=new TagServiceImpl();//������ǩ����
				Category cate=cateServ.searchCateByName(getCateSel().toString());//����������ֻ��������,�Ӷ��������ID
				//wl.writeTxt("Ҫ�޸ĵı�ǩ���ID��ֵΪ��"+cate.getCategoryId());
				tagServ.updateTagCateByCatId(cate.getCategoryId());//3.�Ƴ�����������б�ǩ
				//int maxid=tagServ.findMaxTagId();//��ñ�ǩ���ֵ
				//int maxcount=tagServ.findMaxTagCount();//��ñ�ǩcount���ֵ
				//String tagArea=getCateArea().toString();//��ñ�ǩ�ı����ֵ
				//wl.writeTxt("��ñ�ǩ�ı����ֵΪ��"+tagArea);
				for (int i = 0; i < getLeftSideRecords().size(); i++) {//�������е�ֵ��ȡid
					//wl.writeTxt("��"+i+"������б�ǩ��ֵΪ��"+getLeftSideRecords().get(i));
					//boxs����Ǳ�ǩID
					boxs[i]=tagServ.searchTagByName(getLeftSideRecords().get(i).toString()).getTagId();
					//wl.writeTxt("box("+i+"):"+boxs[i]);
				}
				//wl.writeTxt("boxs.length��"+boxs.length);
				
				//String[] taglist=leftValue.split(",");
				for (int i = 0; i < boxs.length; i++) {//4.��ȡ���б�ǩid��ѭ���޸�
					tagServ.updateTagCateAndHotById(cate.getCategoryId(),boxs.length-i ,boxs[i]);
				}
				for (int i = 0; i < boxs.length; i++) {
					List<PaperBag_simple> bolist=bookServ.getPaperBag_simpleByTagId(boxs[i]);//5.���ݱ�ǩID �����LIST
					for (int j = 0; j < bolist.size(); j++) {//6.������ID������ķ���
						bookServ.modifyPaperBagCateIdById(bolist.get(j).getId(), cate.getCategoryId());
					}
				}
			}
		}else {
			return "error";
		}
		return "TagShowAction";
	}
	
/*	public String AddTag_Edit() throws Exception{
		if(file.exists()){   
			try {   
				file.delete();  
				file.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		if (getCateSel()!=null && getCateArea()!=null) {//1.�������Ϊ�գ��ı���Ϊ��ʱ
			if(!getCateSel().toString().equals("--��������--")) {//2.�������Ϊ����������
				wl.writeTxt("�����б�ѡ���ֵΪ��"+getCateSel().toString());
				CategoryService cateServ=new CategoryServiceImpl();//����������
				TagService tagServ=new TagServiceImpl();//������ǩ����
				Category cate=cateServ.searchCateByName(getCateSel().toString());//����������ֻ��������,�Ӷ��������ID
				wl.writeTxt("Ҫ�޸ĵı�ǩ���ID��ֵΪ��"+cate.getCategoryId());
			}
		}
		
		return "TagShowAction!Edit";
	}*/
	
	public String DELCate() throws Exception {
		if (getCateSel()!=null) {
			if (!getCateSel().toString().equals("--��������--")) {
				CategoryService cateServ=new CategoryServiceImpl();//����������
				TagService tagServ=new TagServiceImpl();//������ǩ����
				Category cate=cateServ.searchCateByName(getCateSel().toString());
				if (cate!=null) {
					tagServ.DelAllByCategoryId(cate.getCategoryId());
					cateServ.DelByCateId(cate.getCategoryId());
				}
			}
			
		}
		
		return "TagShowAction";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	public String getCateSel() {
		return cateSel;
	}
	public void setCateSel(String cateSel) {
		this.cateSel = cateSel;
	}

	public void setLeftSideRecords(List<String> leftSideRecords) {
		this.leftSideRecords = leftSideRecords;
	}

	public List<String> getLeftSideRecords() {
		return leftSideRecords;
	}

	public void setRightSideRecords(List<String> rightSideRecords) {
		this.rightSideRecords = rightSideRecords;
	}

	public List<String> getRightSideRecords() {
		return rightSideRecords;
	}

	

}
