package com.baiyun.httputils;

import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.baiyun.http.HttpRecode;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.OveDepPar;
import com.baiyun.vo.parcelable.HomeAdPar;
import com.baiyun.vo.parcelable.HomeNewsPar;
import com.baiyun.vo.parcelable.OveDepTeacherPar;
import com.baiyun.vo.parcelable.OvePicPar;
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

public class HomeHttpUtils extends HttpUtils{
	private Context context;
	public HomeHttpUtils(Context context) {
		this.context = context;
	}
	
	public interface OnGetHomeAdParsListener{
		public void onGetHomeAdPars(List<HomeAdPar>  homeAdPars);
	}
	
	public interface OnGetHomeNewsListener{
		public void onGerHomeNews(List<HomeNewsPar> homeNewsPars);
	}
	
	public interface OnGetOnlineUrlListener{
		public void onGetOnlineUrl(String url);
	}
	
	public interface OnGetOveDepParsListener{
		public void onGetOveDepPars(List<OveDepPar> oveDepPars);
	}
	
	public interface OnGetOveDepTeacherParsListener{
		public void onGetOveDepTeacherPars(List<OveDepTeacherPar> teacherPars);
	}
	
	public interface OnGetOvePicParListener{
		public void onGetOvePicPar(OvePicPar ovePicPar);
	}
	
	public void getHomeAdPar(final OnGetHomeAdParsListener onGetHomeAdParListener) {
		
		send(HttpMethod.GET, HttpURL.HOME_AD, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();
					if (jsonObject.get("data").isJsonArray()) {
						JsonArray jsonArray = jsonObject.getAsJsonArray("data"); 
						java.lang.reflect.Type type = new TypeToken<List<HomeAdPar>>() {}.getType();
						List<HomeAdPar> homeAdPars = new Gson().fromJson(jsonArray.toString(), type);
						onGetHomeAdParListener.onGetHomeAdPars(homeAdPars);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				//提示
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void getHomeNewsPar(final OnGetHomeNewsListener onGetHomeNewsListener){
		send(HttpMethod.GET, HttpURL.HOME_NEWS, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();
					if (jsonObject.get("data").isJsonArray()) {
						JsonArray jsonArray = jsonObject.getAsJsonArray("data"); 
						java.lang.reflect.Type type = new TypeToken<List<HomeNewsPar>>() {}.getType();
						List<HomeNewsPar> homeNewsPars = new Gson().fromJson(jsonArray.toString(), type);
						onGetHomeNewsListener.onGerHomeNews(homeNewsPars);
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void getOnlineUrl(final OnGetOnlineUrlListener onGetOnlineUrlListener){
		send(HttpMethod.GET, HttpURL.HOME_ONLINE, new RequestCallBack<String>() {

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
									url = urlEle.getAsString();
								}
							}
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				onGetOnlineUrlListener.onGetOnlineUrl(url);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetOnlineUrlListener.onGetOnlineUrl(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
		});
	
	}
	
	public void getOveDepPars(int page, final OnGetOveDepParsListener onGetOveDepParsListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.SCHOOL_TEACHERS_LIST+pageStr;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<OveDepPar> depPars = null;
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
									java.lang.reflect.Type type = new TypeToken<List<OveDepPar>>() {}.getType();
									depPars = new Gson().fromJson(jsonArray.toString(), type);
								}
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "无更多数据", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					depPars = null;
					System.out.println(e);
				}
				onGetOveDepParsListener.onGetOveDepPars(depPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetOveDepParsListener.onGetOveDepPars(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getOveDepTeacherPars(String id, int page, final OnGetOveDepTeacherParsListener depTeacherParsListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.SCHOOL_TEACHERS_LIST_MORE+HttpURL.PAGE_PARAM+pageStr+HttpURL.ID_PARAM+id;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<OveDepTeacherPar> teacherPars = null;
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
									java.lang.reflect.Type type = new TypeToken<List<OveDepTeacherPar>>() {}.getType();
									teacherPars = new Gson().fromJson(jsonArray.toString(), type);
								}
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "无更多数据", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					teacherPars = null;
					System.out.println(e);
				}
				depTeacherParsListener.onGetOveDepTeacherPars(teacherPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				depTeacherParsListener.onGetOveDepTeacherPars(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getOvePicPar(final OnGetOvePicParListener onGetOvePicParListener) {
		
		send(HttpMethod.GET, HttpURL.SCHOOL_INTRO_PIC, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				OvePicPar ovePicPar = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();
					
					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.GET_SUCCESS)) {
							JsonElement dataEle = jsonObject.get("data");
							if (dataEle.isJsonObject()) {
								ovePicPar = new Gson().fromJson(dataEle, OvePicPar.class);
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "返回数据失败", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					ovePicPar = null;
					System.out.println(e);
				}
				onGetOvePicParListener.onGetOvePicPar(ovePicPar);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetOvePicParListener.onGetOvePicPar(null);
				//提示
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
