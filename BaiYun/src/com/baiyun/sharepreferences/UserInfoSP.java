package com.baiyun.sharepreferences;

import com.baiyun.constants.Constants;
import com.baiyun.vo.parcelable.UserInfoPar;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 使用SharePreference存储一些app的设置信息,xml的文件名为APP_SETTING = "appSetting"
 * 
 * @author Holyn
 * @create 2015-1-18
 * @modified
 */
public class UserInfoSP {
	public static UserInfoSP singleInstance;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public UserInfoSP(Context context) {
		sp = context.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public static synchronized UserInfoSP getSingleInstance(Context context) {
		if (singleInstance == null) {
			singleInstance = new UserInfoSP(context);
		}
		return singleInstance;
	}
	
	//清空数据
	public void clear() {
		editor.clear();
		editor.commit();
	}
	
	public void setUserInfoPar(UserInfoPar userInfoPar) {
		// TODO Auto-generated method stub
		setId(userInfoPar.getId());
		setAccount(userInfoPar.getAccount());
		setRealName(userInfoPar.getRealName());
		setGender(userInfoPar.getGender());
		setMobile(userInfoPar.getMobile());
		setClassName(userInfoPar.getClassName());
		setImg(userInfoPar.getImg());
	}

	//id
	public void setId(String id) {
		editor.putString("id", id);
		editor.commit();
	}

	public String getId() {
		return sp.getString("id", "");
	}

	//account
	public void setAccount(String account) {
		editor.putString("account", account);
		editor.commit();
	}

	public String getAccount() {
		return sp.getString("account", "");
	}
	
	//realName
	public void setRealName(String realName) {
		editor.putString("realName", realName);
		editor.commit();
	}

	public String getRealName() {
		return sp.getString("realName", "");
	}
	
	//gender
	public void setGender(String gender) {
		editor.putString("gender", gender);
		editor.commit();
	}

	public String getGender() {
		return sp.getString("gender", "");
	}
	
	//mobile
	public void setMobile(String mobile) {
		editor.putString("mobile", mobile);
		editor.commit();
	}

	public String getMobile() {
		return sp.getString("mobile", "");
	}
	
	//className
	public void setClassName(String className) {
		editor.putString("className", className);
		editor.commit();
	}

	public String getClassName() {
		return sp.getString("className", "");
	}
	
	//img
	public void setImg(String img) {
		editor.putString("img", img);
		editor.commit();
	}

	public String getImg() {
		return sp.getString("img", "");
	}
	
	//userName
	public void setUserName(String userName) {
		editor.putString("userName", userName);
		editor.commit();
	}

	public String getUserName() {
		return sp.getString("userName", "");
	}
	
	//password
	public void setPassword(String password) {
		editor.putString("password", password);
		editor.commit();
	}

	public String getPassword() {
		return sp.getString("password", "");
	}
	
	//mobileUserId 百度推送
	public void setMobileUserId(String mobileUserId) {
		editor.putString("mobileUserId", mobileUserId);
		editor.commit();
	}

	public String getMobileUserId() {
		return sp.getString("mobileUserId", "");
	}
	
	//mobileChannelId 百度推送
	public void setMobileChannelId(String mobileChannelId) {
		editor.putString("mobileChannelId", mobileChannelId);
		editor.commit();
	}

	public String getMobileChannelId() {
		return sp.getString("mobileChannelId", "");
	}
	
}
