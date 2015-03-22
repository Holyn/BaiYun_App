package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class VersionPar implements Parcelable {
	private String id;
	private String content;
	private String latestUrl;
	private String latestVersion;
	private String updateTime;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(content);
		dest.writeString(latestUrl);
		dest.writeString(latestVersion);
		dest.writeString(updateTime);

	}

	public static final Parcelable.Creator<VersionPar> CREATOR = new Parcelable.Creator<VersionPar>() {
		public VersionPar createFromParcel(Parcel in) {
			VersionPar par = new VersionPar();
			par.id = in.readString();
			par.content = in.readString();
			par.latestUrl = in.readString();
			par.latestVersion = in.readString();
			par.updateTime = in.readString();;
			return par;
		}

		public VersionPar[] newArray(int size) {
			return new VersionPar[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLatestUrl() {
		return latestUrl;
	}

	public void setLatestUrl(String latestUrl) {
		this.latestUrl = latestUrl;
	}

	public String getLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
