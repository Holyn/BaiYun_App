package com.baiyun.httputils;

import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.baiyun.http.HttpRecode;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.HomeCooNewsPar;
import com.baiyun.vo.parcelable.HomeCooSumPar;
import com.baiyun.vo.parcelable.HomeRecruitPar;
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

public class HomeJobHttpUtils extends HttpUtils{
	private Context context;
	
	public HomeJobHttpUtils(Context context) {
		this.context = context;
	}
	
	public interface OnGetSumUrlListener{//就业概况的图文链接
		public void OnGetSumUrl(String url);
	}
	
	public interface OnGetRecListListener{//招聘信息列表
		public void OnGetRecList(List<HomeRecruitPar> recruitPars);
	}
	
	public interface OnGetCooSumListener{//校企合作简介
		public void OnGetCooSum(HomeCooSumPar cooSumPar);
	}
	
	public interface OnGetCooNewsListener{//校企合作新闻
		public void OnGetCooNews(List<HomeCooNewsPar> newsPars);
	}
	
	public void getSumUrl(final OnGetSumUrlListener onGetSumUrlListener){
		String url = HttpURL.HOME_JOB_SUMMARY;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String url = null;
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
								JsonElement urlEle = jsonObject.get("url");
								if (urlEle.isJsonPrimitive()) {
									String urlLast = urlEle.getAsString();
									if (urlLast != null && !(urlLast.equalsIgnoreCase(""))) {
										url = HttpURL.HOST+urlLast;
									}else {
										Toast.makeText(context, "图文链接为空", Toast.LENGTH_SHORT).show();
									}
								}
							}
						}else {
							Toast.makeText(context, "无图文链接", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					url = null;
					System.out.println(e);
				}
				onGetSumUrlListener.OnGetSumUrl(url);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetSumUrlListener.OnGetSumUrl(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getRecList(int page, final OnGetRecListListener onGetRecListListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.HOME_JOB_RECRUIT+pageStr;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<HomeRecruitPar> recruitPars = null;
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
									java.lang.reflect.Type type = new TypeToken<List<HomeRecruitPar>>() {}.getType();
									recruitPars = new Gson().fromJson(jsonArray.toString(), type);
								}
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "无更多数据", Toast.LENGTH_SHORT).show();
						}
					}
					
				} catch (Exception e) {
					recruitPars = null;
					System.out.println(e);
				}
				onGetRecListListener.OnGetRecList(recruitPars);
				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetRecListListener.OnGetRecList(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getCooSum(final OnGetCooSumListener onGetCooSumListener){
		String url = HttpURL.HOME_JOB_COO_SUMMARY;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				HomeCooSumPar cooSumPar = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.GET_SUCCESS)) {
							JsonElement dataEle = jsonObject.get("data");
							if (dataEle.isJsonObject()) {
								cooSumPar = new Gson().fromJson(dataEle, HomeCooSumPar.class);
							}
						}
					}
				} catch (Exception e) {
					cooSumPar = null;
					System.out.println(e);
				}		
				onGetCooSumListener.OnGetCooSum(cooSumPar);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetCooSumListener.OnGetCooSum(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getCooNews(int page, final OnGetCooNewsListener onGetCooNewsListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.HOME_JOB_COO_NEWS+pageStr;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<HomeCooNewsPar> cooNewsPars = null;
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
									java.lang.reflect.Type type = new TypeToken<List<HomeCooNewsPar>>() {}.getType();
									cooNewsPars = new Gson().fromJson(jsonArray.toString(), type);
								}
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "无更多数据", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					cooNewsPars = null;
					System.out.println(e);
				}
				onGetCooNewsListener.OnGetCooNews(cooNewsPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetCooNewsListener.OnGetCooNews(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
}
