package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class LifeAssociationNewsPar implements Parcelable {
	private String contentId;
	private String userId;
	private String organizationId;
	private String name;
	
	private String associationBrief;
	private String creater;
	private String associationId;
	private String contentTitle;//社团动态新闻标题
	
	private String type;
	private String contentUrl;//动态新闻链接地址 
	private String sortOrder;
	private String status;
	
	private String viewTimes;
	private String contentCreater;
	private String picId;
	private String picUrl;//缩略图url
	
	private String picType;
	private String picName;
	private String picContent;
	private String picWidth;
	
	private String picHeight;
	private String contentBrief;
	private String createTime;
	private String contentCreateTime;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(contentId);
		dest.writeString(userId);
		dest.writeString(organizationId);
		dest.writeString(name);
		
		dest.writeString(associationBrief);
		dest.writeString(creater);
		dest.writeString(associationId);
		dest.writeString(contentTitle);
		
		dest.writeString(type);
		dest.writeString(contentUrl);
		dest.writeString(sortOrder);
		dest.writeString(status);
		
		dest.writeString(viewTimes);
		dest.writeString(contentCreater);
		dest.writeString(picId);
		dest.writeString(picUrl);
		
		dest.writeString(picType);
		dest.writeString(picName);
		dest.writeString(picContent);
		dest.writeString(picWidth);
		
		dest.writeString(picHeight);
		dest.writeString(contentBrief);
		dest.writeString(createTime);
		dest.writeString(contentCreateTime);

	}

	public static final Parcelable.Creator<LifeAssociationNewsPar> CREATOR = new Parcelable.Creator<LifeAssociationNewsPar>() {
		public LifeAssociationNewsPar createFromParcel(Parcel in) {
			LifeAssociationNewsPar par = new LifeAssociationNewsPar();
			
			par.contentId = in.readString();
			par.userId = in.readString();
			par.organizationId = in.readString();
			par.name = in.readString();
			
			par.associationBrief = in.readString();
			par.creater = in.readString();
			par.associationId = in.readString();
			par.contentTitle = in.readString();
			
			par.type = in.readString();
			par.contentUrl = in.readString();
			par.sortOrder = in.readString();
			par.status = in.readString();
			
			par.viewTimes = in.readString();
			par.contentCreater = in.readString();
			par.picId = in.readString();
			par.picUrl = in.readString();
			
			par.picType = in.readString();
			par.picName = in.readString();
			par.picContent = in.readString();
			par.picWidth = in.readString();
			
			par.picHeight = in.readString();
			par.contentBrief = in.readString();
			par.createTime = in.readString();
			par.contentCreateTime = in.readString();
			
			return par;
		}

		public LifeAssociationNewsPar[] newArray(int size) {
			return new LifeAssociationNewsPar[size];
		}
	};

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssociationBrief() {
		return associationBrief;
	}

	public void setAssociationBrief(String associationBrief) {
		this.associationBrief = associationBrief;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getAssociationId() {
		return associationId;
	}

	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(String viewTimes) {
		this.viewTimes = viewTimes;
	}

	public String getContentCreater() {
		return contentCreater;
	}

	public void setContentCreater(String contentCreater) {
		this.contentCreater = contentCreater;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
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

	public String getContentBrief() {
		return contentBrief;
	}

	public void setContentBrief(String contentBrief) {
		this.contentBrief = contentBrief;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContentCreateTime() {
		return contentCreateTime;
	}

	public void setContentCreateTime(String contentCreateTime) {
		this.contentCreateTime = contentCreateTime;
	}

}
