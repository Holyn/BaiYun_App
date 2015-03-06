package com.baiyun.httputils;

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

public class SchoolLifeHttpUtils extends HttpUtils{
	private Context context;
	
	public SchoolLifeHttpUtils(Context context) {
		this.context = context;
	}
	
	public interface onGetNewsListener{//校园生活--（获取学工动态、体育创新、科技创新上方的图片和下方的新闻）
		public void onGetNews(List<VoPicPar> picPars, List<Vo2Par> vo2Pars);
	}

	public void getNews(String url, final onGetNewsListener onGetNewsListener){
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<VoPicPar> picPars = null;
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
								
								JsonElement headlineListEle = jsonObject.get("headlineList");//新闻list
								
								if (headlineListEle != null) {
//									System.out.println("====> headlineListEle = "+headlineListEle);
									if (headlineListEle.isJsonArray()) {
										JsonArray jsonArray = headlineListEle.getAsJsonArray();
										java.lang.reflect.Type type = new TypeToken<List<Vo2Par>>() {}.getType();
										vo2Pars = new Gson().fromJson(jsonArray.toString(), type);
									}
								}
								
								JsonElement pictureListEle = jsonObject.get("pictureList");//头部广告list
								if (pictureListEle != null) {
//									System.out.println("====> pictureListEle = "+pictureListEle);
									if (pictureListEle.isJsonArray()) {
										JsonArray jsonArray = pictureListEle.getAsJsonArray();
										java.lang.reflect.Type type = new TypeToken<List<VoPicPar>>() {}.getType();
										picPars = new Gson().fromJson(jsonArray.toString(), type);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					picPars = null;
					vo2Pars = null;
					System.out.println(e);
				}
				onGetNewsListener.onGetNews(picPars, vo2Pars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetNewsListener.onGetNews(null, null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
}
