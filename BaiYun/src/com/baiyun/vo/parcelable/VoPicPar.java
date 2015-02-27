package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class VoPicPar implements Parcelable {
	private String id;
	private String url;
	private String type;
	private String name;
	private String content;
	private String width;
	private String height;
	private String sortOrder;
	private String createTime;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(url);
		dest.writeString(type);
		dest.writeString(name);
		dest.writeString(content);
		dest.writeString(width);
		dest.writeString(height);
		dest.writeString(sortOrder);
		dest.writeString(createTime);

	}

	public static final Parcelable.Creator<VoPicPar> CREATOR = new Parcelable.Creator<VoPicPar>() {
		public VoPicPar createFromParcel(Parcel in) {
			VoPicPar par = new VoPicPar();
			par.id = in.readString();
			par.url = in.readString();
			par.type = in.readString();
			par.name = in.readString();
			par.content = in.readString();
			par.width = in.readString();
			par.height = in.readString();
			par.sortOrder = in.readString();
			par.createTime = in.readString();
			return par;
		}

		public VoPicPar[] newArray(int size) {
			return new VoPicPar[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
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
	

}
