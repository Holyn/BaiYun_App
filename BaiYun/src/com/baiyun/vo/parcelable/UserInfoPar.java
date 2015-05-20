package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoPar implements Parcelable {
	private String id;
	private String account;
	private String realName;
	private String gender;
	private String mobile;
	private String className;
	private String img;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(account);
		dest.writeString(realName);
		dest.writeString(gender);
		dest.writeString(mobile);
		dest.writeString(className);
		dest.writeString(img);

	}

	public static final Parcelable.Creator<UserInfoPar> CREATOR = new Parcelable.Creator<UserInfoPar>() {
		public UserInfoPar createFromParcel(Parcel in) {
			UserInfoPar par = new UserInfoPar();
			par.id = in.readString();
			par.account = in.readString();
			par.realName = in.readString();
			par.gender = in.readString();
			par.mobile = in.readString();
			par.className = in.readString();
			par.img = in.readString();
			return par;
		}

		public UserInfoPar[] newArray(int size) {
			return new UserInfoPar[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
