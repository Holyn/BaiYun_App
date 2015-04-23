package com.baiyun.httputils;

import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.baiyun.http.HttpRecode;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.HomeVideoPar;
import com.baiyun.vo.parcelable.HomeVideoStylePar;
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

public class HomeVideoHttpUtils extends HttpUtils{
	private Context context;
	
	public HomeVideoHttpUtils(Context context) {
		this.context = context;
	}
	
	public interface OnGetVideoStyleListener{
		public void onGetVideoStyle(List<HomeVideoStylePar> videoStylePars);
	}
	
	public interface OnGetVideoListener{
		public void onGetVideo(List<HomeVideoPar> videoPars);
	}
	
	public void getVideoStyle(int page, final OnGetVideoStyleListener videoStyleListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.SCHOOL_VIDEO_STYLE+pageStr;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<HomeVideoStylePar> videoStylePars = null;
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
									java.lang.reflect.Type type = new TypeToken<List<HomeVideoStylePar>>() {}.getType();
									videoStylePars = new Gson().fromJson(jsonArray.toString(), type);
								}
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "无更多数据", Toast.LENGTH_SHORT).show();
						}
					}
					
				} catch (Exception e) {
					videoStylePars = null;
					System.out.println(e);
				}
				videoStyleListener.onGetVideoStyle(videoStylePars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				videoStyleListener.onGetVideoStyle(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void getVideos(String id, int page, final OnGetVideoListener onGetVideoListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.SCHOOL_VIDEO+HttpURL.PAGE_PARAM+pageStr+HttpURL.ID_PARAM+id;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<HomeVideoPar> videoPars = null;
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
									java.lang.reflect.Type type = new TypeToken<List<HomeVideoPar>>() {}.getType();
									videoPars = new Gson().fromJson(jsonArray.toString(), type);
								}
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "无更多数据", Toast.LENGTH_SHORT).show();
						}
					}
					
				} catch (Exception e) {
					videoPars = null;
					System.out.println(e);
				}
				
				onGetVideoListener.onGetVideo(videoPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetVideoListener.onGetVideo(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
