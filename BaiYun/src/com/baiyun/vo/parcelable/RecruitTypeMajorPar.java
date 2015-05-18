package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 招生服务-招生类别
 */
public class RecruitTypeMajorPar implements Parcelable {
	private String professionId;
	private String menuThreeId;
	private String menuThreeName;
	private String professionName;
	
	private String recuitUrl;
	private String introContentId;
	private String introTitle;
	private String introSubTitle;
	
	private String introBrief;
	private String introUrl;
	private String introSortOrder;
	private String introViewTimes;
	
	private String introCreater;
	private String commencementDate;
	private String introCreateTime;


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(professionId);
		dest.writeString(menuThreeId);
		dest.writeString(menuThreeName);
		dest.writeString(professionName);
		dest.writeString(recuitUrl);
		dest.writeString(introContentId);
		dest.writeString(introTitle);
		dest.writeString(introSubTitle);
		dest.writeString(introBrief);
		dest.writeString(introUrl);
		dest.writeString(introSortOrder);
		dest.writeString(introViewTimes);
		dest.writeString(introCreater);
		dest.writeString(commencementDate);
		dest.writeString(introCreateTime);

	}

	public static final Parcelable.Creator<RecruitTypeMajorPar> CREATOR = new Parcelable.Creator<RecruitTypeMajorPar>() {
		public RecruitTypeMajorPar createFromParcel(Parcel in) {
			RecruitTypeMajorPar par = new RecruitTypeMajorPar();
			par.professionId = in.readString();
			par.menuThreeId = in.readString();
			par.menuThreeName = in.readString();
			par.professionName = in.readString();
			
			par.recuitUrl = in.readString();
			par.introContentId = in.readString();
			par.introTitle = in.readString();
			par.introSubTitle = in.readString();
			
			par.introBrief = in.readString();
			par.introUrl = in.readString();
			par.introSortOrder = in.readString();
			par.introViewTimes = in.readString();
			
			par.introCreater = in.readString();
			par.commencementDate = in.readString();
			par.introCreateTime = in.readString();
			
			return par;
		}

		public RecruitTypeMajorPar[] newArray(int size) {
			return new RecruitTypeMajorPar[size];
		}
	};

	public String getProfessionId() {
		return professionId;
	}

	public void setProfessionId(String professionId) {
		this.professionId = professionId;
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

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public String getRecuitUrl() {
		return recuitUrl;
	}

	public void setRecuitUrl(String recuitUrl) {
		this.recuitUrl = recuitUrl;
	}

	public String getIntroContentId() {
		return introContentId;
	}

	public void setIntroContentId(String introContentId) {
		this.introContentId = introContentId;
	}

	public String getIntroTitle() {
		return introTitle;
	}

	public void setIntroTitle(String introTitle) {
		this.introTitle = introTitle;
	}

	public String getIntroSubTitle() {
		return introSubTitle;
	}

	public void setIntroSubTitle(String introSubTitle) {
		this.introSubTitle = introSubTitle;
	}

	public String getIntroBrief() {
		return introBrief;
	}

	public void setIntroBrief(String introBrief) {
		this.introBrief = introBrief;
	}

	public String getIntroUrl() {
		return introUrl;
	}

	public void setIntroUrl(String introUrl) {
		this.introUrl = introUrl;
	}

	public String getIntroSortOrder() {
		return introSortOrder;
	}

	public void setIntroSortOrder(String introSortOrder) {
		this.introSortOrder = introSortOrder;
	}

	public String getIntroViewTimes() {
		return introViewTimes;
	}

	public void setIntroViewTimes(String introViewTimes) {
		this.introViewTimes = introViewTimes;
	}

	public String getIntroCreater() {
		return introCreater;
	}

	public void setIntroCreater(String introCreater) {
		this.introCreater = introCreater;
	}

	public String getCommencementDate() {
		return commencementDate;
	}

	public void setCommencementDate(String commencementDate) {
		this.commencementDate = commencementDate;
	}

	public String getIntroCreateTime() {
		return introCreateTime;
	}

	public void setIntroCreateTime(String introCreateTime) {
		this.introCreateTime = introCreateTime;
	}

}
