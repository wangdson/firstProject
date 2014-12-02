package com.dxs.Entity;

import java.util.List;

public class Tag_Contain {
	private int tagId;
	private String tagName;
	private List<PaperBag_simple> book;
	
	
	
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public void setBook(List<PaperBag_simple> book) {
		this.book = book;
	}
	public List<PaperBag_simple> getBook() {
		return book;
	}

	

}
