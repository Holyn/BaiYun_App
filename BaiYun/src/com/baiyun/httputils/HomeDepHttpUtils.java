package com.baiyun.httputils;

import java.util.List;

import com.baiyun.http.HttpRecode;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.HomeDepPar;
import com.baiyun.vo.parcelable.HomeVideoPar;
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

import android.content.Context;
import android.widget.Toast;

public class HomeDepHttpUtils extends HttpUtils{
	private Context context;
	
	public HomeDepHttpUtils(Context context) {
		this.context = context;
	}
	
	public interface OnGetListListener{
		public void OnGetList(List<HomeDepPar> depPars);
	}
	
	public interface OnGetUrlListener{
		public void OnGetUrl(String url);
	}
	
	public void getList(final OnGetListListener onGetListListener){
		send(HttpMethod.GET, HttpURL.SCHOOL_DEPARTMENT, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<HomeDepPar> depPars = null;
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
								java.lang.reflect.Type type = new TypeToken<List<HomeDepPar>>() {}.getType();
								depPars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}
					}
					
				} catch (Exception e) {
					depPars = null;
					System.out.println(e);
				}
				onGetListListener.OnGetList(depPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetListListener.OnGetList(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void getUrl(String id, final OnGetUrlListener onGetUrlListener){
		String url = HttpURL.SCHOOL_DEPARTMENT_DETAIL+id;
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
				onGetUrlListener.OnGetUrl(url);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetUrlListener.OnGetUrl(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
}
