package com.dxs.Entity;

import java.util.Date;

public class Retrieval implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int retrievalId;
	private String userKeyWord;
	private Date searchTime;
	private int number;
	private String cate;

	public Retrieval() {
		super();
	}

	public Retrieval(int retrievalId, String userKeyWord, Date searchTime,
			int number, String cate) {
		super();
		this.retrievalId = retrievalId;
		this.userKeyWord = userKeyWord;
		this.searchTime = searchTime;
		this.number = number;
		this.setCate(cate);
	}

	public int getRetrievalId() {
		return retrievalId;
	}

	public void setRetrievalId(int retrievalId) {
		this.retrievalId = retrievalId;
	}

	public String getUserKeyWord() {
		return userKeyWord;
	}

	public void setUserKeyWord(String userKeyWord) {
		this.userKeyWord = userKeyWord;
	}

	public Date getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(Date searchTime) {
		this.searchTime = searchTime;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public String getCate() {
		return cate;
	}

}
