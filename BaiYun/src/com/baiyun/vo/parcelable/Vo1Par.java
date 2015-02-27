package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Vo1Par implements Parcelable {
	private String id;
	private String associationId;
	private String title;
	private String brief;
	private String type;
	private String url;
	private String sortOrder;
	private String status;
	private String viewTimes;
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
		dest.writeString(associationId);
		dest.writeString(title);
		dest.writeString(brief);
		dest.writeString(type);
		dest.writeString(url);
		dest.writeString(sortOrder);
		dest.writeString(status);
		dest.writeString(viewTimes);
		dest.writeString(creater);
		dest.writeString(createTime);

	}

	public static final Parcelable.Creator<Vo1Par> CREATOR = new Parcelable.Creator<Vo1Par>() {
		public Vo1Par createFromParcel(Parcel in) {
			Vo1Par par = new Vo1Par();
			par.id = in.readString();
			par.associationId = in.readString();
			par.title = in.readString();
			par.brief = in.readString();
			par.type = in.readString();
			par.url = in.readString();
			par.sortOrder = in.readString();
			par.status = in.readString();
			par.viewTimes = in.readString();
			par.creater = in.readString();
			par.createTime = in.readString();
			return par;
		}

		public Vo1Par[] newArray(int size) {
			return new Vo1Par[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssociationId() {
		return associationId;
	}

	public void setAssociationId(String associationId) {
		this.associationId = associationId;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
