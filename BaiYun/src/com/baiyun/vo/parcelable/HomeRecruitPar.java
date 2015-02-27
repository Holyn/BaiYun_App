package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeRecruitPar implements Parcelable {
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
	private String picName;
	private String picContent;
	private String picWidth;
	private String picHeight;
	private String picUrl;
	private String picType;
	private String associationId;
	private String contentCreateTime;
	private String picCreateTime;

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
		dest.writeString(picName);
		dest.writeString(picContent);
		dest.writeString(picWidth);
		dest.writeString(picHeight);
		dest.writeString(picUrl);
		dest.writeString(picType);
		dest.writeString(associationId);
		dest.writeString(contentCreateTime);
		dest.writeString(picCreateTime);

	}

	public static final Parcelable.Creator<HomeRecruitPar> CREATOR = new Parcelable.Creator<HomeRecruitPar>() {
		public HomeRecruitPar createFromParcel(Parcel in) {
			HomeRecruitPar par = new HomeRecruitPar();
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
			par.picName = in.readString();
			par.picContent = in.readString();
			par.picWidth = in.readString();
			par.picHeight = in.readString();
			par.picUrl = in.readString();
			par.picType = in.readString();
			par.associationId = in.readString();
			par.contentCreateTime = in.readString();
			par.picCreateTime = in.readString();
			return par;
		}

		public HomeRecruitPar[] newArray(int size) {
			return new HomeRecruitPar[size];
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

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicContent() {
		return picContent;
	}

	public void setPicContent(String picContent) {
		this.picContent = picContent;
	}

	public String getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(String picWidth) {
		this.picWidth = picWidth;
	}

	public String getPicHeight() {
		return picHeight;
	}

	public void setPicHeight(String picHeight) {
		this.picHeight = picHeight;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	public String getAssociationId() {
		return associationId;
	}

	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}

	public String getContentCreateTime() {
		return contentCreateTime;
	}

	public void setContentCreateTime(String contentCreateTime) {
		this.contentCreateTime = contentCreateTime;
	}

	public String getPicCreateTime() {
		return picCreateTime;
	}

	public void setPicCreateTime(String picCreateTime) {
		this.picCreateTime = picCreateTime;
	}

}
