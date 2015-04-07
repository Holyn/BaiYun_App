package com.baiyun.httputils;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.baiyun.http.HttpRecode;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.LostPar;
import com.baiyun.vo.parcelable.Vo1Par;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class SchoolServiceHttpUtils extends HttpUtils {
	private Context context;

	public SchoolServiceHttpUtils(Context context) {
		this.context = context;
	}

	public interface OnGetNoticeListener {
		public void onGetNotice(List<Vo1Par> vo1Pars);
	}

	public interface OnGetLostParListener {
		public void onGetLostPar(List<LostPar> lostPars);
	}

	public void getNotice(int page, final OnGetNoticeListener onGetVo1ListListener) {
		String pageStr = String.valueOf(page);
		String url = HttpURL.S_NOTICE + pageStr;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<Vo1Par> vo1Pars = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.GET_SUCCESS)) {
							JsonElement dataEle = jsonObject.get("data");
							if (dataEle.isJsonObject()) {
								jsonObject = dataEle.getAsJsonObject();
								// int total =
								// jsonObject.get("total").getAsInt();
								JsonElement itemsEle = jsonObject.get("items");
								if (itemsEle.isJsonArray()) {
									JsonArray jsonArray = itemsEle.getAsJsonArray();
									java.lang.reflect.Type type = new TypeToken<List<Vo1Par>>() {
									}.getType();
									vo1Pars = new Gson().fromJson(jsonArray.toString(), type);
								}
							}
						}
					}
				} catch (Exception e) {
					vo1Pars = null;
					System.out.println(e);
				}
				onGetVo1ListListener.onGetNotice(vo1Pars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetVo1ListListener.onGetNotice(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}

		});
	}

	public void getLostPar(String name, final OnGetLostParListener onGetLostParListener) {
		String url = null;
		if (TextUtils.isEmpty(name)) {
			url = HttpURL.S_LOST_FOUND;
		} else {
			url = HttpURL.S_LOST_FOUND + "?userName=" + name;
		}
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<LostPar> lostPars = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.GET_SUCCESS)) {
							JsonElement dataEle = jsonObject.get("data");
							if (dataEle.isJsonArray()) {
								JsonArray jsonArray = dataEle.getAsJsonArray();
								java.lang.reflect.Type type = new TypeToken<List<LostPar>>() {
								}.getType();
								lostPars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}
					}
				} catch (Exception e) {
					lostPars = null;
					System.out.println(e);
				}
				onGetLostParListener.onGetLostPar(lostPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetLostParListener.onGetLostPar(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}

		});
	}
}
