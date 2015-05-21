package com.baiyun.httputils;

import android.content.Context;
import android.widget.Toast;

import com.baiyun.http.HttpRecode;
import com.baiyun.http.HttpURL;
import com.baiyun.sharepreferences.UserInfoSP;
import com.baiyun.util.Base64Util;
import com.baiyun.vo.parcelable.UserInfoPar;
import com.baiyun.vo.parcelable.VersionPar;
import com.baiyun.vo.parcelable.Vo1Par;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class SlideMenuHttpUtils extends HttpUtils {

	private Context context;

	public SlideMenuHttpUtils(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public interface OnGetVersionListener {// 获取版本信息
		public void onGetVersion(VersionPar versionPar);
	}

	public interface OnPostBaiduPushListener {// 导入百度推送所需的mobileChannelId和mobileUserId接口
		public void onPostBaiduPush(boolean isSuccess);
	}

	public interface OnPostLoginListener {//登录
		public void onPostLogin(UserInfoPar userInfoPar);
	}
	
	public interface OnUploadUserImgListener{//上传头像
		public void onUploadUserImg(boolean isSuccess);
	}
	
	public interface OnPostAdvice{//意见反馈
		
	}

	public void getVersion(String version, final OnGetVersionListener onGetVersionListener) {
		send(HttpMethod.GET, HttpURL.R_VERSION + version, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				VersionPar versionPar = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.GET_SUCCESS)) {
							JsonElement dataEle = jsonObject.get("data");
							if (dataEle.isJsonObject()) {
								versionPar = new Gson().fromJson(dataEle, VersionPar.class);
							}
						} else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "服务器无数据", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					versionPar = null;
					System.out.println(e);
				}
				onGetVersionListener.onGetVersion(versionPar);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetVersionListener.onGetVersion(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}

		});
	}

	public void postBaiduPush(String mobileUserId, String mobileChannelId, final OnPostBaiduPushListener baiduPushListener) {
		final RequestParams params = new RequestParams();
		params.addBodyParameter(HttpURL.PARAM_MOBILE_USER_ID, mobileUserId);
		params.addBodyParameter(HttpURL.PARAM_MOBILE_CHANNEL_ID, mobileChannelId);

		send(HttpMethod.POST, HttpURL.R_SET_PUSH, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				boolean isSuccess = false;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.INSERT_SUCCESS)) {
							System.out.println("====> 成功导入百度推送所需的两个id");
							isSuccess = true;
						}
					}
				} catch (Exception e) {
					isSuccess = false;
					System.out.println(e);
				}
				if (baiduPushListener != null) {
					baiduPushListener.onPostBaiduPush(isSuccess);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				if (baiduPushListener != null) {
					baiduPushListener.onPostBaiduPush(false);
				}
			}

		});
	}

	public void postLogin(final String userName, final String password, String randomString, String mobileUserId, String mobileChannelId,
			final OnPostLoginListener onPostLoginListener) {
		final RequestParams params = new RequestParams();
		params.addBodyParameter(HttpURL.PARAM_USER_NAME, userName);
		params.addBodyParameter(HttpURL.PARAM_PASSWORD, password);
		params.addBodyParameter(HttpURL.PARAM_RANDOM_STRING, randomString);
		params.addBodyParameter(HttpURL.PARAM_MOBILE_USER_ID, mobileUserId);
		params.addBodyParameter(HttpURL.PARAM_MOBILE_CHANNEL_ID, mobileChannelId);

		send(HttpMethod.POST, HttpURL.R_LOGIN, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				UserInfoPar userInfoPar = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recode1Ele = jsonObject.get("recode1");
					if (recode1Ele.isJsonPrimitive()) {
						String recode1 = recode1Ele.getAsString();
						if (recode1.equalsIgnoreCase(HttpRecode.LOGIN_SUCCESS)) {
							Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
							JsonElement dataEle = jsonObject.get("data");
							if (dataEle.isJsonObject()) {
								userInfoPar = new Gson().fromJson(dataEle, UserInfoPar.class);

								// 存储到sharepreference
								UserInfoSP userInfoSP = UserInfoSP.getSingleInstance(context);
								userInfoSP.setUserInfoPar(userInfoPar);
								userInfoSP.setUserName(userName);
								userInfoSP.setPassword(password);

							}
						} else if (recode1.equalsIgnoreCase(HttpRecode.ERROR_NAME_EXIST)) {
							Toast.makeText(context, "用户名不存在", Toast.LENGTH_LONG).show();
						} else if (recode1.equalsIgnoreCase(HttpRecode.ERROR_NAME_PASSWORD)) {
							Toast.makeText(context, "用户名不存在或密码错误", Toast.LENGTH_LONG).show();
						} else if (recode1.equalsIgnoreCase(HttpRecode.ERROR_RANDOM_STRING)) {
							Toast.makeText(context, "验证码出错", Toast.LENGTH_LONG).show();
						}
					}
				} catch (Exception e) {
					userInfoPar = null;
					System.out.println(e);
				}
				onPostLoginListener.onPostLogin(userInfoPar);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onPostLoginListener.onPostLogin(null);
				Toast.makeText(context, "数据发送失败", Toast.LENGTH_SHORT).show();
			}

		});
	}
	
	public void uploadUserImg(String id, String imgFilePath, final OnUploadUserImgListener onUploadUserImgListener) {
		final RequestParams params = new RequestParams();
		params.addBodyParameter(HttpURL.PARAM_ID, id);
		params.addBodyParameter(HttpURL.PARAM_IMG, Base64Util.getImageStr(imgFilePath));
		
		send(HttpMethod.POST, HttpURL.R_UPLOAD_USER_IMG, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				boolean isSuccess = false;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.INSERT_SUCCESS)) {
							Toast.makeText(context, "图片上传成功", Toast.LENGTH_SHORT).show();
							isSuccess = true;
						}
					}
				} catch (Exception e) {
					isSuccess = false;
					System.out.println(e);
				}
				if (onUploadUserImgListener != null) {
					onUploadUserImgListener.onUploadUserImg(isSuccess);;
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				if (onUploadUserImgListener != null) {
					onUploadUserImgListener.onUploadUserImg(false);;
				}
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
				super.onLoading(total, current, isUploading);
				System.out.println("====> totlal:"+total+"/ current:"+current);
			}

		});
	}
}
