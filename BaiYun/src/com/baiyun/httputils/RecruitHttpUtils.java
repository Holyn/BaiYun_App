package com.baiyun.httputils;

import java.util.List;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun.http.HttpRecode;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.AdmissionResultPar;
import com.baiyun.vo.parcelable.ApplyPar;
import com.baiyun.vo.parcelable.HomeCooSumPar;
import com.baiyun.vo.parcelable.MajorPar;
import com.baiyun.vo.parcelable.RecruitTypePar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class RecruitHttpUtils  extends HttpUtils{
	private Context context;
	
	public RecruitHttpUtils(Context context) {
		this.context = context;
	}
	
	public interface OnGetTypeListListener{
		public void OnGetTypeList(List<RecruitTypePar> typePars);
	}
	
	public interface OnGetApplyListListener{
		public void OnGetApplyList(List<ApplyPar> applyPars);
	}
	
	public interface OnGetMajorListListener{
		public void OnGetMajorList(List<MajorPar> majorPars);
	}
	
	public interface OnPostForm1Listener{
		public void OnPostForm1(boolean isSuccess);
	}
	
	public interface OnPostForm2Listener{
		public void OnPostForm2(boolean isSuccess);
	}
	
	public interface OnResultCheckListener{
		public void onResultCheck(AdmissionResultPar resultPar);
	}
	
	public void getTypeList(final OnGetTypeListListener onGetTypeListListener) {
		send(HttpMethod.GET, HttpURL.RECRUIT_INFOR_16, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<RecruitTypePar> typePars = null;
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
								java.lang.reflect.Type type = new TypeToken<List<RecruitTypePar>>() {}.getType();
								typePars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}
					}
				} catch (Exception e) {
					typePars = null;
					System.out.println(e);
				}
				onGetTypeListListener.OnGetTypeList(typePars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetTypeListListener.OnGetTypeList(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getApplyList(final OnGetApplyListListener onGetApplyListListener){
		send(HttpMethod.GET, HttpURL.RECRUIT_INFOR_23, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<ApplyPar> applyPars = null;
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
								java.lang.reflect.Type type = new TypeToken<List<ApplyPar>>() {}.getType();
								applyPars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}
					}
				} catch (Exception e) {
					applyPars = null;
					System.out.println(e);
				}
				onGetApplyListListener.OnGetApplyList(applyPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetApplyListListener.OnGetApplyList(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void getMajorList(String id, final OnGetMajorListListener onGetMajorListListener) {
		String url = HttpURL.APPLY_COURSE+id;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				List<MajorPar> majorPars = null;
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
								java.lang.reflect.Type type = new TypeToken<List<MajorPar>>() {}.getType();
								majorPars = new Gson().fromJson(jsonArray.toString(), type);
							}
						}
					}
				} catch (Exception e) {
					majorPars = null;
					System.out.println(e);
				}
				onGetMajorListListener.OnGetMajorList(majorPars);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onGetMajorListListener.OnGetMajorList(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	public void postForm1(RequestParams params, final OnPostForm1Listener onPostForm1Listener){
		send(HttpMethod.POST, HttpURL.APPLY_FORM_1, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.INSERT_SUCCESS)) {
							onPostForm1Listener.OnPostForm1(true);
						}
					}
				} catch (Exception e) {
					onPostForm1Listener.OnPostForm1(false);
				}
//				System.out.println(responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onPostForm1Listener.OnPostForm1(false);
			}
		});
	}
	
	public void postForm2(RequestParams params, final OnPostForm2Listener onPostForm2Listener){
		send(HttpMethod.POST, HttpURL.APPLY_FORM_2, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.INSERT_SUCCESS)) {
							onPostForm2Listener.OnPostForm2(true);
						}
					}
				} catch (Exception e) {
					onPostForm2Listener.OnPostForm2(false);
				}
//				System.out.println(responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onPostForm2Listener.OnPostForm2(false);
			}
		});
	}
	
	public void resultCheck(String name, String phone, final OnResultCheckListener onResultCheckListener) {
		String url = HttpURL.RECRUIT_RESULT_CHECK+name+HttpURL.TELEPHONE_PARAM+phone;
		send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				AdmissionResultPar resultPar = null;
				try {
					JsonParser parser = new JsonParser();
					JsonObject jsonObject = parser.parse(responseInfo.result).getAsJsonObject();

					JsonElement recodeEle = jsonObject.get("recode");
					if (recodeEle.isJsonPrimitive()) {
						String recode = recodeEle.getAsString();
						if (recode.equalsIgnoreCase(HttpRecode.SEARCH_SUCCESS)) {
							JsonElement dataEle = jsonObject.get("data");
							if (dataEle.isJsonObject()) {
								resultPar = new Gson().fromJson(dataEle, AdmissionResultPar.class);
							}
						}
					}
				} catch (Exception e) {
					resultPar = null;
					System.out.println(e);
				}		
				onResultCheckListener.onResultCheck(resultPar);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				onResultCheckListener.onResultCheck(null);
				Toast.makeText(context, "数据请求失败", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
}
