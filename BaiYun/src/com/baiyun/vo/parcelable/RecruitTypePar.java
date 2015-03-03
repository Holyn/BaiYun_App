package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 招生服务-招生类别
 */
public class RecruitTypePar implements Parcelable {
	private String contentId;
	private String menuSubId;
	private String subMenuName;
	private String subMenuType;
	private String menuThreeId;
	private String threeMenuName;
	private String threeMenuType;
	private String creater;
	private String updateUserName;
	private String sortOrder;
	private String title;
	private String brief;
	private String contentType;
	private String url;
	private String contentSort;
	private String status;
	private String viewTimes;
	private String contentCreater;
	private String createTime;
	private String updateTime;
	private String contentCreateTime;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(contentId);
		dest.writeString(menuSubId);
		dest.writeString(subMenuName);
		dest.writeString(subMenuType);
		dest.writeString(menuThreeId);
		dest.writeString(threeMenuName);
		dest.writeString(threeMenuType);
		dest.writeString(creater);
		dest.writeString(updateUserName);
		dest.writeString(sortOrder);
		dest.writeString(title);
		dest.writeString(brief);
		dest.writeString(contentType);
		dest.writeString(url);
		dest.writeString(contentSort);
		dest.writeString(status);
		dest.writeString(viewTimes);
		dest.writeString(contentCreater);
		dest.writeString(createTime);
		dest.writeString(updateTime);
		dest.writeString(contentCreateTime);

	}

	public static final Parcelable.Creator<RecruitTypePar> CREATOR = new Parcelable.Creator<RecruitTypePar>() {
		public RecruitTypePar createFromParcel(Parcel in) {
			RecruitTypePar par = new RecruitTypePar();
			
			par.contentId = in.readString();
			par.menuSubId = in.readString();
			par.subMenuName = in.readString();
			par.subMenuType = in.readString();
			par.menuThreeId = in.readString();
			par.threeMenuName = in.readString();
			par.threeMenuType = in.readString();
			par.creater = in.readString();
			par.updateUserName = in.readString();
			par.sortOrder = in.readString();
			par.title = in.readString();
			par.brief = in.readString();
			par.contentType = in.readString();
			par.url = in.readString();
			par.contentSort = in.readString();
			par.status = in.readString();
			par.viewTimes = in.readString();
			par.contentCreater = in.readString();
			par.createTime = in.readString();
			par.updateTime = in.readString();
			par.contentCreateTime = in.readString();
			
			return par;
		}

		public RecruitTypePar[] newArray(int size) {
			return new RecruitTypePar[size];
		}
	};

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getMenuSubId() {
		return menuSubId;
	}

	public void setMenuSubId(String menuSubId) {
		this.menuSubId = menuSubId;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

	public String getSubMenuType() {
		return subMenuType;
	}

	public void setSubMenuType(String subMenuType) {
		this.subMenuType = subMenuType;
	}

	public String getMenuThreeId() {
		return menuThreeId;
	}

	public void setMenuThreeId(String menuThreeId) {
		this.menuThreeId = menuThreeId;
	}

	public String getThreeMenuName() {
		return threeMenuName;
	}

	public void setThreeMenuName(String threeMenuName) {
		this.threeMenuName = threeMenuName;
	}

	public String getThreeMenuType() {
		return threeMenuType;
	}

	public void setThreeMenuType(String threeMenuType) {
		this.threeMenuType = threeMenuType;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContentSort() {
		return contentSort;
	}

	public void setContentSort(String contentSort) {
		this.contentSort = contentSort;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getContentCreateTime() {
		return contentCreateTime;
	}

	public void setContentCreateTime(String contentCreateTime) {
		this.contentCreateTime = contentCreateTime;
	}

}
