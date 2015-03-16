package com.baiyun.vo.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 招生服务-招生类别
 */
public class RecruitTypePar implements Parcelable {
	private String menuSubId;
	private String subMenuName;
	private String subMenuType;
	private String menuThreeId;
	private String threeMenuName;
	private String threeMenuType;
	
	private String planContentId;
	private String planTitle;
	private String planBrief;
	private String planUrl;
	private String planViewTimes;
	private String planCreater;
	
	private String introContentId;
	private String introTitle;
	private String introBrief;
	private String introUrl;
	private String introViewTimes;
	private String introCreater;
	
	private String planCreateTime;
	private String introCreateTime;


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(menuSubId);
		dest.writeString(subMenuName);
		dest.writeString(subMenuType);
		dest.writeString(menuThreeId);
		dest.writeString(threeMenuName);
		dest.writeString(threeMenuType);
		
		dest.writeString(planContentId);
		dest.writeString(planTitle);
		dest.writeString(planBrief);
		dest.writeString(planUrl);
		dest.writeString(planViewTimes);
		dest.writeString(planCreater);
		
		dest.writeString(introContentId);
		dest.writeString(introTitle);
		dest.writeString(introBrief);
		dest.writeString(introUrl);
		dest.writeString(introViewTimes);
		dest.writeString(introCreater);
		
		dest.writeString(planCreateTime);
		dest.writeString(introCreateTime);

	}

	public static final Parcelable.Creator<RecruitTypePar> CREATOR = new Parcelable.Creator<RecruitTypePar>() {
		public RecruitTypePar createFromParcel(Parcel in) {
			RecruitTypePar par = new RecruitTypePar();
			par.menuSubId = in.readString();
			par.subMenuName = in.readString();
			par.subMenuType = in.readString();
			par.menuThreeId = in.readString();
			par.threeMenuName = in.readString();
			par.threeMenuType = in.readString();
			
			par.planContentId = in.readString();
			par.planTitle = in.readString();
			par.planBrief = in.readString();
			par.planUrl = in.readString();
			par.planViewTimes = in.readString();
			par.planCreater = in.readString();
			
			par.introContentId = in.readString();
			par.introTitle = in.readString();
			par.introBrief = in.readString();
			par.introUrl = in.readString();
			par.introViewTimes = in.readString();
			par.introCreater = in.readString();
			
			par.planCreateTime = in.readString();
			par.introCreateTime = in.readString();
			
			return par;
		}

		public RecruitTypePar[] newArray(int size) {
			return new RecruitTypePar[size];
		}
	};


	public String getMenuSubId() {
		return menuSubId;
	}

	public void setMenuSubId(String menuSubId) {
		this.menuSubId = menuSubId;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

	public String getSubMenuType() {
		return subMenuType;
	}

	public void setSubMenuType(String subMenuType) {
		this.subMenuType = subMenuType;
	}

	public String getMenuThreeId() {
		return menuThreeId;
	}

	public void setMenuThreeId(String menuThreeId) {
		this.menuThreeId = menuThreeId;
	}

	public String getThreeMenuName() {
		return threeMenuName;
	}

	public void setThreeMenuName(String threeMenuName) {
		this.threeMenuName = threeMenuName;
	}

	public String getThreeMenuType() {
		return threeMenuType;
	}

	public void setThreeMenuType(String threeMenuType) {
		this.threeMenuType = threeMenuType;
	}

	public String getPlanContentId() {
		return planContentId;
	}

	public void setPlanContentId(String planContentId) {
		this.planContentId = planContentId;
	}

	public String getPlanTitle() {
		return planTitle;
	}

	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}

	public String getPlanBrief() {
		return planBrief;
	}

	public void setPlanBrief(String planBrief) {
		this.planBrief = planBrief;
	}

	public String getPlanUrl() {
		return planUrl;
	}

	public void setPlanUrl(String planUrl) {
		this.planUrl = planUrl;
	}

	public String getPlanViewTimes() {
		return planViewTimes;
	}

	public void setPlanViewTimes(String planViewTimes) {
		this.planViewTimes = planViewTimes;
	}

	public String getPlanCreater() {
		return planCreater;
	}

	public void setPlanCreater(String planCreater) {
		this.planCreater = planCreater;
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

	public String getPlanCreateTime() {
		return planCreateTime;
	}

	public void setPlanCreateTime(String planCreateTime) {
		this.planCreateTime = planCreateTime;
	}

	public String getIntroCreateTime() {
		return introCreateTime;
	}

	public void setIntroCreateTime(String introCreateTime) {
		this.introCreateTime = introCreateTime;
	}

	
}
