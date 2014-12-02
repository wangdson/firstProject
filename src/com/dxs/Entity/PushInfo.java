package com.dxs.Entity;

import java.util.Date;

public class PushInfo {
	private int pushId;
	private String content;
	private Date planTime;
	private String pushUser;
	private String state;

	public PushInfo() {
		super();
	}

	public PushInfo(int pushId, String content, Date planTime, String pushUser,
			String state) {
		super();
		this.pushId = pushId;
		this.content = content;
		this.planTime = planTime;
		this.pushUser = pushUser;
		this.state = state;
	}

	public int getPushId() {
		return pushId;
	}

	public void setPushId(int pushId) {
		this.pushId = pushId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public String getPushUser() {
		return pushUser;
	}

	public void setPushUser(String pushUser) {
		this.pushUser = pushUser;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
