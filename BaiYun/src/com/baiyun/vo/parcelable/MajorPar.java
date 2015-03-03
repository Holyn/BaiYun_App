package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
/**
 *招生服务-网上报名
 */
public class MajorPar implements Parcelable {
	private String id;
	private String typeId;
	private String name;
	private String creater;
	private String typeName;
	private String recuitUrl;
	private String ancommencementDate;
	private String createTime;
	private String commencementDate;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(typeId);
		dest.writeString(name);
		dest.writeString(creater);
		dest.writeString(typeName);
		dest.writeString(recuitUrl);
		dest.writeString(ancommencementDate);
		dest.writeString(createTime);
		dest.writeString(commencementDate);

	}

	public static final Parcelable.Creator<MajorPar> CREATOR = new Parcelable.Creator<MajorPar>() {
		public MajorPar createFromParcel(Parcel in) {
			MajorPar par = new MajorPar();
			par.id = in.readString();
			par.typeId = in.readString();
			par.name = in.readString();
			par.creater = in.readString();
			par.typeName = in.readString();
			par.recuitUrl = in.readString();
			par.ancommencementDate = in.readString();
			par.createTime = in.readString();
			par.commencementDate = in.readString();
			return par;
		}

		public MajorPar[] newArray(int size) {
			return new MajorPar[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRecuitUrl() {
		return recuitUrl;
	}

	public void setRecuitUrl(String recuitUrl) {
		this.recuitUrl = recuitUrl;
	}

	public String getAncommencementDate() {
		return ancommencementDate;
	}

	public void setAncommencementDate(String ancommencementDate) {
		this.ancommencementDate = ancommencementDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCommencementDate() {
		return commencementDate;
	}

	public void setCommencementDate(String commencementDate) {
		this.commencementDate = commencementDate;
	}

}
