package com.baiyun.httputils;

import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.HomeAdPar;
import com.baiyun.vo.parcelable.HomeNewsPar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeNewsHttpUtils extends HttpUtils{
	private Context context;
	
	public HomeNewsHttpUtils(Context context) {
		this.context = context;
	}
	
	public interface OnGetPageHomeNewsListener{
		public void onGerPageHomeNews(List<HomeNewsPar> homeNewsPars);
	}
	
	public interface OnGetDetailUrlListener{
		public void onGetDetailUrl(String url);
	}
	
	public void getPageHomeNews(final int page, final OnGetPageHomeNewsListener onGetPageHomeNewsListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.SCHOOL_NEWS+pageStr;
//		System.out.println("====> url = "+url);
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<HomeNewsPar> homeNewsPars = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					
					if (jsonObject.get("data").isJsonObject()) {
//						System.out.println("====> data-isJsonObject = true");
						jsonObject = jsonObject.getAsJsonObject("data");
						if (jsonObject.get("items").isJsonArray()) {
//							System.out.println("====> items-isJsonArray = true");
							JsonArray jsonArray = jsonObject.getAsJsonArray("items"); 
//							System.out.println("====> jsonArray = "+jsonArray.toString());
							java.lang.reflect.Type type = new TypeToken<List<HomeNewsPar>>() {}.getType();
							homeNewsPars = new Gson().fromJson(jsonArray.toString(), type);
						}else {
//							System.out.println("====> items-isJsonArray = false");
						}
					}else {
//						System.out.println("====> data-isJsonObject = false");
					}
					
				} catch (Exception e) {
					homeNewsPars = null;
					System.out.println(e);
				}
				if (homeNewsPars == null) {
					if (page == 1) {
						Toast.makeText(context, "服务器无数据", Toast.LENGTH_SHORT).show();
					}else if (page > 1) {
						Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
					}
				}
				
				onGetPageHomeNewsListener.onGerPageHomeNews(homeNewsPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetPageHomeNewsListener.onGerPageHomeNews(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void getDetailUrl(String id, final OnGetDetailUrlListener onGetDetailUrlListener) {
		String url = HttpURL.SCHOOL_NEWS_DETAILS+id;
//		System.out.println("====> url = "+url);
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String contentUrl = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();
					if (jsonObject.get("url").isJsonPrimitive()) {
						contentUrl = HttpURL.HOST+jsonObject.get("url").getAsString();
					}else {
						Toast.makeText(context, "无图文链接", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				onGetDetailUrlListener.onGetDetailUrl(contentUrl);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetDetailUrlListener.onGetDetailUrl(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
}
