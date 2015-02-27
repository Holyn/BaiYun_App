package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeDepPar implements Parcelable {
	private String id;
	private String menuSubId;
	private String menuThreeId;
	private String threeMenuName;
	private String img;
	private String title;
	private String brief;
	private String type;
	private String contentUrl;
	private String sortOrder;
	private String viewTimes;
	private String status;
	private String creater;
	private String createTime;

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
		dest.writeString(threeMenuName);
		dest.writeString(img);
		dest.writeString(title);
		dest.writeString(brief);
		dest.writeString(type);
		dest.writeString(contentUrl);
		dest.writeString(sortOrder);
		dest.writeString(viewTimes);
		dest.writeString(status);
		dest.writeString(creater);
		dest.writeString(createTime);

	}

	public static final Parcelable.Creator<HomeDepPar> CREATOR = new Parcelable.Creator<HomeDepPar>() {
		public HomeDepPar createFromParcel(Parcel in) {
			HomeDepPar par = new HomeDepPar();
			par.id = in.readString();
			par.menuSubId = in.readString();
			par.menuThreeId = in.readString();
			par.threeMenuName = in.readString();
			par.img = in.readString();
			par.title = in.readString();
			par.brief = in.readString();
			par.type = in.readString();
			par.contentUrl = in.readString();
			par.sortOrder = in.readString();
			par.viewTimes = in.readString();
			par.status = in.readString();
			par.creater = in.readString();
			par.createTime = in.readString();
			return par;
		}

		public HomeDepPar[] newArray(int size) {
			return new HomeDepPar[size];
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

	public String getThreeMenuName() {
		return threeMenuName;
	}

	public void setThreeMenuName(String threeMenuName) {
		this.threeMenuName = threeMenuName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
