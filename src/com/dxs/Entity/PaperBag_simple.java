package com.dxs.Entity;

public class PaperBag_simple {
	private int id;
	private String bagName;
	private int totalpage;
	private String cover;
	
	
	//�÷�����дObject���equals������Ϊ������list.contains(object)���� ����Ϊcontains��������Ĭ�ϵ���equals������
	//������дequals��objת��������Ҫ������ǰ �ȽϵĶ���Object ������Ҫ����list<����>.contains(list<����>)
	//GetBookObjectAction.java��tagconlist.get(j-1).getBook().contains(bosl.get(k))���õ��˴˺���
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
