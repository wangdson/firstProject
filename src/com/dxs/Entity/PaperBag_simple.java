package com.dxs.Entity;

public class PaperBag_simple {
	private int id;
	private String bagName;
	private int totalpage;
	private String cover;
	
	
	//该方法重写Object类的equals方法，为了用于list.contains(object)方法 ，因为contains方法里面默认调用equals方法，
	//而不重写equals把obj转换成所需要的类型前 比较的都是Object ，我需要的是list<类型>.contains(list<类型>)
	//GetBookObjectAction.java：tagconlist.get(j-1).getBook().contains(bosl.get(k))处用到了此函数
	public boolean equals(Object obj) {   
        if (obj instanceof PaperBag_simple) {   
        	PaperBag_simple u = (PaperBag_simple) obj; 
        	if (this.id==u.id) {
				return true;
			}else {
				return false;
			}
        }   
        return super.equals(obj);  
    }
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	public int getTotalpage() {
		return totalpage;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}

	public String getBagName() {
		return bagName;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}




}
