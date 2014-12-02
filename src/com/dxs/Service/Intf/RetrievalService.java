package com.dxs.Service.Intf;

import java.util.List;

import com.dxs.Entity.Retrieval;

public interface RetrievalService {


	public abstract int modifyCateById(String ret);
	public abstract int modifyNumById(int retId);
	public abstract int modifyNumById(int retId,int num);
	public abstract int modifyWordCateById(int retId, String word,String cate);
	public abstract int addRetrieval(Retrieval ret);
	public abstract Retrieval searchRet(String keyword);
	public abstract Retrieval getRetrievalById(int retrievalId);
	public abstract List<Retrieval> getALLRetrieval();
	public abstract List<Retrieval> getRetrievalByCate(String cate) ;
	public abstract int findMaxId();
	public abstract List<Retrieval> getlastRetrieval(int cp,int num,int pageSize);
	public abstract List<Retrieval> getlastRetrieval(int cp,int num);
	public abstract int findRetrievalCount();
	
	
	

}