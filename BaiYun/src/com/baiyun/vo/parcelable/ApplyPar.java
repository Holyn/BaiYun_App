package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class ApplyPar implements Parcelable {
	private String id;
	private String name;
	private String type;
	private String img;
	private String creater;
	private String updateUserName;
	private String sortOrder;
	private String createTime;
	private String updateTime;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(type);
		dest.writeString(img);
		dest.writeString(creater);
		dest.writeString(updateUserName);
		dest.writeString(sortOrder);
		dest.writeString(createTime);
		dest.writeString(updateTime);

	}

	public static final Parcelable.Creator<ApplyPar> CREATOR = new Parcelable.Creator<ApplyPar>() {
		public ApplyPar createFromParcel(Parcel in) {
			ApplyPar par = new ApplyPar();
			par.id = in.readString();
			par.name = in.readString();
			par.type = in.readString();
			par.img = in.readString();
			par.creater = in.readString();
			par.updateUserName = in.readString();
			par.sortOrder = in.readString();
			par.createTime = in.readString();
			par.updateTime = in.readString();
			return par;
		}

		public ApplyPar[] newArray(int size) {
			return new ApplyPar[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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

}
