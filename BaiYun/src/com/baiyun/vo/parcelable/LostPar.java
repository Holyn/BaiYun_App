package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class LostPar implements Parcelable {
	private String id;
	private String menuSubId;
	private String menuThreeId;
	private String title;
	private String brief;
	private String sortOrder;
	private String contentUrl;
	private String contentType;
	private String creater;
	private String viewTimes;
	private String status;
	
	private String userId;
	private String userAccount;
	private String userRealName;
	private String userImg;
	private String comment_count;
	private String picUrl;
	private String contentCreateTime;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(menuSubId);
		dest.writeString(menuThreeId);
		dest.writeString(title);
		dest.writeString(brief);
		dest.writeString(sortOrder);
		dest.writeString(contentUrl);
		dest.writeString(contentType);
		dest.writeString(creater);
		dest.writeString(viewTimes);
		dest.writeString(status);
		
		dest.writeString(userId);
		dest.writeString(userAccount);
		dest.writeString(userRealName);
		dest.writeString(userImg);
		dest.writeString(comment_count);
		dest.writeString(picUrl);
		dest.writeString(contentCreateTime);

	}

	public static final Parcelable.Creator<LostPar> CREATOR = new Parcelable.Creator<LostPar>() {
		public LostPar createFromParcel(Parcel in) {
			LostPar par = new LostPar();
			
			par.id = in.readString();
			par.menuSubId = in.readString();
			par.menuThreeId = in.readString();
			par.title = in.readString();
			par.brief = in.readString();
			par.sortOrder = in.readString();
			par.contentUrl = in.readString();
			par.contentType = in.readString();
			par.creater = in.readString();
			par.viewTimes = in.readString();
			par.status = in.readString();
			
			par.userId = in.readString();
			par.userAccount = in.readString();
			par.userRealName = in.readString();
			par.userImg = in.readString();
			par.comment_count = in.readString();
			par.picUrl = in.readString();
			par.contentCreateTime = in.readString();
			return par;
		}

		public LostPar[] newArray(int size) {
			return new LostPar[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuSubId() {
		return menuSubId;
	}

	public void setMenuSubId(String menuSubId) {
		this.menuSubId = menuSubId;
	}

	public String getMenuThreeId() {
		return menuThreeId;
	}

	public void setMenuThreeId(String menuThreeId) {
		this.menuThreeId = menuThreeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(String viewTimes) {
		this.viewTimes = viewTimes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getComment_count() {
		return comment_count;
	}

	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getContentCreateTime() {
		return contentCreateTime;
	}

	public void setContentCreateTime(String contentCreateTime) {
		this.contentCreateTime = contentCreateTime;
	}

	
}
