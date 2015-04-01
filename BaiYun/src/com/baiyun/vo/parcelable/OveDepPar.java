package com.baiyun.vo.parcelable;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class OveDepPar implements Parcelable{
	private String id;
	private String type;
	private String name;
	private String creater;
	private String sortOrder;
	private List<OveDepTeacherPar> gAppContentPicViewList;
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(type);
		dest.writeString(name);
		dest.writeString(creater);
		dest.writeString(sortOrder);
		dest.writeList(gAppContentPicViewList);
	}
	
	public static final Parcelable.Creator<OveDepPar> CREATOR = new Parcelable.Creator<OveDepPar>() {
		public OveDepPar createFromParcel(Parcel in) {
			OveDepPar par = new OveDepPar();
			par.id = in.readString();
			par.type = in.readString();
			par.name = in.readString();
			par.creater = in.readString();
			par.sortOrder = in.readString();
			par.gAppContentPicViewList = in.readArrayList((List.class.getClassLoader()));
			return par;
		}

		public OveDepPar[] newArray(int size) {
			return new OveDepPar[size];
		}
	};

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public List<OveDepTeacherPar> getgAppContentPicViewList() {
		return gAppContentPicViewList;
	}
	public void setgAppContentPicViewList(List<OveDepTeacherPar> gAppContentPicViewList) {
		this.gAppContentPicViewList = gAppContentPicViewList;
	}
	
	
}
