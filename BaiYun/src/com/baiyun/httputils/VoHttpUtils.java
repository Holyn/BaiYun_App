package com.baiyun.httputils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.baiyun.http.HttpRecode;
import com.baiyun.vo.parcelable.Vo1Par;
import com.baiyun.vo.parcelable.Vo2Par;
import com.baiyun.vo.parcelable.VoPicPar;
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

public class VoHttpUtils extends HttpUtils{
	private Context context;
	
	public VoHttpUtils(Context context) {
		this.context = context;
	}
	
	public interface OnGetVo1Listener{
		public void onGetVo1(Vo1Par vo1Par);
	}
	
	public interface OnGetVo1ListListener{
		public void onGetVo1List(List<Vo1Par> vo1Pars);
	}
	
	public interface OnGetVo2Listener{
		public void onGetVo2(List<Vo2Par> vo2Pars);
	}
	
	public interface OnGetPicListListener{
		public void onGetPicList(ArrayList<VoPicPar> picPars);
	}
	
	public void getVo1(String url, final OnGetVo1Listener onGetVo1Listener){
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Vo1Par vo1Par = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.GET_SUCCESS)) {
							JsonElement dataEle = jsonObject.get("data");
							if (dataEle.isJsonObject()) {
								vo1Par = new Gson().fromJson(dataEle, Vo1Par.class);
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "返回数据失败", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					vo1Par = null;
					System.out.println(e);
				}
				onGetVo1Listener.onGetVo1(vo1Par);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetVo1Listener.onGetVo1(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getVo1List(String url, final OnGetVo1ListListener onGetVo1ListListener){
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
							if (dataEle.isJsonArray()) {
								JsonArray jsonArray = dataEle.getAsJsonArray();
								java.lang.reflect.Type type = new TypeToken<List<Vo1Par>>() {}.getType();
								vo1Pars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}
					}
				} catch (Exception e) {
					vo1Pars = null;
					System.out.println(e);
				}
				onGetVo1ListListener.onGetVo1List(vo1Pars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetVo1ListListener.onGetVo1List(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getVo2(String url, int page, final OnGetVo2Listener onGetVo2Listener){
		String pageStr = String.valueOf(page);
		url = url+pageStr;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<Vo2Par> vo2Pars = null;
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
//								int total = jsonObject.get("total").getAsInt();
								JsonElement itemsEle = jsonObject.get("items");
								if (itemsEle.isJsonArray()) {
									JsonArray jsonArray = itemsEle.getAsJsonArray();
									java.lang.reflect.Type type = new TypeToken<List<Vo2Par>>() {}.getType();
									vo2Pars = new Gson().fromJson(jsonArray.toString(), type);
								}
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "无更多数据", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					vo2Pars = null;
					System.out.println(e);
				}
				onGetVo2Listener.onGetVo2(vo2Pars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetVo2Listener.onGetVo2(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getPicList(String url, final OnGetPicListListener onGetPicListListener){
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				ArrayList<VoPicPar> picPars = null;
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
								java.lang.reflect.Type type = new TypeToken<ArrayList<VoPicPar>>() {}.getType();
								picPars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}
					}
				} catch (Exception e) {
					picPars = null;
					System.out.println(e);
				}
				onGetPicListListener.onGetPicList(picPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetPicListListener.onGetPicList(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
}
