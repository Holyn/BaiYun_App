package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class LifeAssociationPar implements Parcelable {
	private String id;
	private String name;
	private String introduceId;
	private String userId;
	private String brief;
	private String img;
	private String creater;
	private String url;
	private String createTime;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(introduceId);
		dest.writeString(userId);
		dest.writeString(brief);
		dest.writeString(img);
		dest.writeString(creater);
		dest.writeString(url);
		dest.writeString(createTime);
	}

	public static final Parcelable.Creator<LifeAssociationPar> CREATOR = new Parcelable.Creator<LifeAssociationPar>() {
		public LifeAssociationPar createFromParcel(Parcel in) {
			LifeAssociationPar par = new LifeAssociationPar();
			par.id = in.readString();
			par.name = in.readString();
			par.introduceId = in.readString();
			par.userId = in.readString();
			par.brief = in.readString();
			par.img = in.readString();
			par.creater = in.readString();
			par.url = in.readString();
			par.createTime = in.readString();
			return par;
		}

		public LifeAssociationPar[] newArray(int size) {
			return new LifeAssociationPar[size];
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

	public String getIntroduceId() {
		return introduceId;
	}

	public void setIntroduceId(String introduceId) {
		this.introduceId = introduceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
