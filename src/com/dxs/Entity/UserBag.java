package com.dxs.Entity;

import java.util.Date;

public class UserBag {
	private int id;
	private Date uploadTime;
	private String tag;
	private String cover;
	private int userId;
	
	private String version;
	private String sver;
	private String initial;//是否第一次上传//0:是新上传，1：编译中，2：等待中，3：单本编译结束，4：本次勾选编译结束=下载APK，5：编译出错
	private int num;//预留下载or安装量
	
	public UserBag(int id, Date uploadTime, String tag, String cover,
			int userId, String version, String sver, String initial, int num,
			String bagName) {
		super();
		this.id = id;
		this.uploadTime = uploadTime;
		this.tag = tag;
		this.cover = cover;
		this.userId = userId;
		this.version = version;
		this.sver = sver;
		this.initial = initial;
		this.num = num;
		this.bagName = bagName;
	}
	public UserBag() {
		super();
	}
	private String bagName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBagName() {
		return bagName;
	}
	public void setBagName(String bagName) {
		this.bagName = bagName;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return version;
	}
	public void setSver(String sver) {
		this.sver = sver;
	}
	public String getSver() {
		return sver;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public String getInitial() {
		return initial;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNum() {
		return num;
	}
	
}
