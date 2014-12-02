package com.dxs.Entity;

import java.util.List;

public class Category_Contain {
	private int categoryId;
	private String categoryName;
	//private List<Tag_Contain> tag;
	private List<PaperBag_simple> book;
	
	
	
	
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryName() {
		return categoryName;
	}
/*	public void setTag(List<Tag_Contain> tag) {
		this.tag = tag;
	}
	public List<Tag_Contain> getTag() {
		return tag;
	}*/
	public void setBook(List<PaperBag_simple> book) {
		this.book = book;
	}
	public List<PaperBag_simple> getBook() {
		return book;
	}
	
	

}
