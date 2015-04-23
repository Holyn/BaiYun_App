package com.baiyun.vo.parcelable;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeVideoStylePar implements Parcelable{
	private String id;
	private String name;
	private String sortOrder;
	private String creater;
	private List<HomeVideoPar> gAppContentPicViewList;
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
		dest.writeString(sortOrder);
		dest.writeString(creater);
		dest.writeList(gAppContentPicViewList);
		dest.writeString(createTime);
	}
	
	public static final Parcelable.Creator<HomeVideoStylePar> CREATOR = new Parcelable.Creator<HomeVideoStylePar>() {
		public HomeVideoStylePar createFromParcel(Parcel in) {
			HomeVideoStylePar par = new HomeVideoStylePar();
			par.id = in.readString();
			par.name = in.readString();
			par.sortOrder = in.readString();
			par.creater = in.readString();
			par.gAppContentPicViewList = in.readArrayList((List.class.getClassLoader()));
			par.createTime = in.readString();
			return par;
		}

		public HomeVideoStylePar[] newArray(int size) {
			return new HomeVideoStylePar[size];
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<HomeVideoPar> getgAppContentPicViewList() {
		return gAppContentPicViewList;
	}
	public void setgAppContentPicViewList(List<HomeVideoPar> gAppContentPicViewList) {
		this.gAppContentPicViewList = gAppContentPicViewList;
	}
	
}
