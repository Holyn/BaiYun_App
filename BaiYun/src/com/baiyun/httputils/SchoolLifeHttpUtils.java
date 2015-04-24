package com.baiyun.httputils;

import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.baiyun.http.HttpRecode;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.LNewsPar;
import com.baiyun.vo.parcelable.LifeAssociationNewsPar;
import com.baiyun.vo.parcelable.LifeAssociationPar;
import com.baiyun.vo.parcelable.LifeGuidePar;
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
	
	public interface onGetNewsAdListener{//校园生活--（获取学工动态、体育创新、科技创新上方的图片）
		public void onGetNewsAd(List<VoPicPar> picPars);
	}
	
	public interface onGetNewsListListener{//校园生活--（获取学工动态、体育创新、科技创新下方的新闻）
		public void onGetNewsList(List<LNewsPar> lNewsPars);
	}
	
	public interface onGetAssociationListener{//校园生活--学生社团(包含社团解析的图文链接)
		public void onGetAssociation(List<LifeAssociationPar> associationPars);
	}
	
	public interface onGetAssociationNewsListener{//校园生活--学生社团--社团动态新闻
		public void onGetAssociationNews(List<LifeAssociationNewsPar> newsPars);
	}
	
	public interface onGetModelListener{//校园生活--榜样白云(List<Vo2Par>)
		public void onGetModel(List<Vo2Par> models);
	}
	
	public interface onGetGuideListener{//校园生活--服务指南
		public void onGetGuide(List<LifeGuidePar> guidePars);
	}
	
	public void getNewsAd(String id, final onGetNewsAdListener oGetNewsAdListener){
		String url = HttpURL.LIFE_NEWS_AD+id;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<VoPicPar> picPars = null;
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
					System.out.println(e);
				}
				oGetNewsAdListener.onGetNewsAd(picPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				oGetNewsAdListener.onGetNewsAd(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}

	public void getNewsList(String id, final int page, final onGetNewsListListener onGetNewsListListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.LIFE_NEWS_LIST+id+HttpURL.PAGE_PARAM+pageStr;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<LNewsPar> newsPars = null;
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
								if (headlineListEle.isJsonObject()) {
									jsonObject = headlineListEle.getAsJsonObject();
									JsonElement itemEle = jsonObject.get("items");
									if (itemEle.isJsonArray()) {
										JsonArray jsonArray = itemEle.getAsJsonArray();
										java.lang.reflect.Type type = new TypeToken<List<LNewsPar>>() {}.getType();
										newsPars = new Gson().fromJson(jsonArray.toString(), type);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					newsPars = null;
					System.out.println(e);
				}
				if (newsPars == null) {
					if (page == 1) {
						Toast.makeText(context, "服务器无数据", Toast.LENGTH_SHORT).show();
					}else if (page > 1) {
						Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
					}
				}
				onGetNewsListListener.onGetNewsList(newsPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetNewsListListener.onGetNewsList(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getAssociation(final onGetAssociationListener associationListener){
		send(HttpMethod.GET, HttpURL.LIFE_ASSOCIATION, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<LifeAssociationPar> associationPars = null;
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
								java.lang.reflect.Type type = new TypeToken<List<LifeAssociationPar>>() {}.getType();
								associationPars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "服务器无数据", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					associationPars = null;
					System.out.println(e);
				}
				associationListener.onGetAssociation(associationPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				associationListener.onGetAssociation(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getAssociationNews(String id, final int page, final onGetAssociationNewsListener associationNewsListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.LIFE_ASSOCIATION_NEWS+id+HttpURL.PAGE_PARAM+pageStr;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
//				System.out.println("====> responseInfo = "+responseInfo.result);
				List<LifeAssociationNewsPar> associationNewsPars = null;
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
								java.lang.reflect.Type type = new TypeToken<List<LifeAssociationNewsPar>>() {}.getType();
								associationNewsPars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "返回数据失败", Toast.LENGTH_SHORT).show();
						}
					}
					if (associationNewsPars == null) {
						if (page == 1) {
							Toast.makeText(context, "服务器无数据", Toast.LENGTH_SHORT).show();
						}else if (page > 1) {
							Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					associationNewsPars = null;
					Toast.makeText(context, "返回数据失败", Toast.LENGTH_SHORT).show();
					System.out.println(e);
				}
				associationNewsListener.onGetAssociationNews(associationNewsPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				associationNewsListener.onGetAssociationNews(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getModel(int page, final onGetModelListener getModelListener){
		String pageStr = String.valueOf(page);
		String url = HttpURL.LIFE_MODEL+pageStr;
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
				getModelListener.onGetModel(vo2Pars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				getModelListener.onGetModel(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getGuide(final onGetGuideListener getGuideListener){
		String url = HttpURL.LIFE_GUIDE;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<LifeGuidePar> guidePars = null;
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
								java.lang.reflect.Type type = new TypeToken<List<LifeGuidePar>>() {}.getType();
								guidePars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}else if (recode.equalsIgnoreCase(HttpRecode.GET_ERROR)) {
							Toast.makeText(context, "无更多数据", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					guidePars = null;
					System.out.println(e);
				}
				getGuideListener.onGetGuide(guidePars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				getGuideListener.onGetGuide(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
}
