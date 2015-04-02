package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class AdmissionResultPar implements Parcelable {
	private String id;
	private String menuThreeId;
	private String menuThreeName;
	private String name;
	private String telephone;
	private String baseCourseId;
	private String baseCourseName;
	private String registerTime;
	private String status;
	private String result;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(menuThreeId);
		dest.writeString(menuThreeName);
		dest.writeString(name);
		dest.writeString(telephone);
		dest.writeString(baseCourseId);
		dest.writeString(baseCourseName);
		dest.writeString(registerTime);
		dest.writeString(status);
		dest.writeString(result);

	}

	public static final Parcelable.Creator<AdmissionResultPar> CREATOR = new Parcelable.Creator<AdmissionResultPar>() {
		public AdmissionResultPar createFromParcel(Parcel in) {
			AdmissionResultPar par = new AdmissionResultPar();
			par.id = in.readString();
			par.menuThreeId = in.readString();
			par.menuThreeName = in.readString();
			par.name = in.readString();
			par.telephone = in.readString();
			par.baseCourseId = in.readString();
			par.baseCourseName = in.readString();
			par.registerTime = in.readString();
			par.status = in.readString();
			par.result = in.readString();
			return par;
		}

		public AdmissionResultPar[] newArray(int size) {
			return new AdmissionResultPar[size];
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuThreeId() {
		return menuThreeId;
	}

	public void setMenuThreeId(String menuThreeId) {
		this.menuThreeId = menuThreeId;
	}

	public String getMenuThreeName() {
		return menuThreeName;
	}

	public void setMenuThreeName(String menuThreeName) {
		this.menuThreeName = menuThreeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBaseCourseId() {
		return baseCourseId;
	}

	public void setBaseCourseId(String baseCourseId) {
		this.baseCourseId = baseCourseId;
	}

	public String getBaseCourseName() {
		return baseCourseName;
	}

	public void setBaseCourseName(String baseCourseName) {
		this.baseCourseName = baseCourseName;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
