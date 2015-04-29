package com.holyn.selectlocalpiclib;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class LocalImageVo implements Parcelable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String path;
	String name;
	double size;
	int bucketId;
	String bucketName;
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(path);
		dest.writeString(name);
		dest.writeDouble(size);
		dest.writeInt(bucketId);
		dest.writeString(bucketName);

	}

	public static final Parcelable.Creator<LocalImageVo> CREATOR = new Parcelable.Creator<LocalImageVo>() {
		public LocalImageVo createFromParcel(Parcel in) {
			LocalImageVo par = new LocalImageVo();
			par.id = in.readInt();
			par.path = in.readString();
			par.name = in.readString();
			par.size = in.readDouble();
			par.bucketId = in.readInt();
			par.bucketName = in.readString();
			return par;
		}

		public LocalImageVo[] newArray(int size) {
			return new LocalImageVo[size];
		}
	};
	
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBucketId() {
		return bucketId;
	}
	public void setBucketId(int bucketId) {
		this.bucketId = bucketId;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
