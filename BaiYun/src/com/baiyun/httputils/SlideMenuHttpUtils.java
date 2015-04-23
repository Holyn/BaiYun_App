package com.baiyun.httputils;

import android.content.Context;
import android.widget.Toast;

import com.baiyun.http.HttpRecode;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.VersionPar;
import com.baiyun.vo.parcelable.Vo1Par;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class SlideMenuHttpUtils extends HttpUtils{
	
	private Context context;
	
	public SlideMenuHttpUtils(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public interface OnGetVersionListener{//获取版本信息
		public void onGetVersion(VersionPar versionPar);
	}
	
	public void getVersion(String version, final OnGetVersionListener onGetVersionListener){
		send(HttpMethod.GET, HttpURL.R_VERSION+version, new RequestCallBack<String>() {

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
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
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
}
