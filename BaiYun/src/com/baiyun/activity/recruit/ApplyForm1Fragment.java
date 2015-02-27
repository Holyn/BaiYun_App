package com.baiyun.activity.recruit;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.RecruitHttpUtils;
import com.lidroid.xutils.http.RequestParams;

public class ApplyForm1Fragment extends BaseFragment{
	private RecruitHttpUtils httpUtils;
	private Button btnSubmit;
	
	public static ApplyForm1Fragment newInstance() {
		return new ApplyForm1Fragment();
	}
	
	public ApplyForm1Fragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_form_1;
		
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new RecruitHttpUtils(getActivity());
		
		final TextView tvTest = (TextView)rootView.findViewById(R.id.tv_test);
		
		
		final RequestParams params = new RequestParams();
		
//		params.addQueryStringParameter("id", "32");
//		params.addQueryStringParameter("name", "陈浩林");
//		params.addQueryStringParameter("address", "佛山石湾");
//		params.addQueryStringParameter("gender", "男");
//		params.addQueryStringParameter("culturalBackground", "本科");
//		params.addQueryStringParameter("politicalBackground", "党员");
//		params.addQueryStringParameter("iDNumber", "888888888888888");
//		params.addQueryStringParameter("graduateBackground", "毕业");
//		params.addQueryStringParameter("graduateSchool", "佛大");
//		params.addQueryStringParameter("baseCourseId", "10");
//		params.addQueryStringParameter("residence", "广东肇庆");
//		params.addQueryStringParameter("specialStudent", "否");
//		params.addQueryStringParameter("graduateScore", "100");
//		params.addQueryStringParameter("postcode", "528000");
//		params.addQueryStringParameter("phonecall", "111111111");
//		params.addQueryStringParameter("telephone", "10086");
//		params.addQueryStringParameter("linkman", "陈浩林");
//		params.addQueryStringParameter("note", "留言");
//		params.addQueryStringParameter("birthday", "88年8月8日");
		
		
		
		params.addBodyParameter("id", "32");
		params.addBodyParameter("name", "陈浩林");
		params.addBodyParameter("address", "佛山石湾");
		params.addBodyParameter("gender", "1");
		params.addBodyParameter("culturalBackground", "本科");
		params.addBodyParameter("politicalBackground", "党员");
		params.addBodyParameter("iDNumber", "888888888888888");
		params.addBodyParameter("graduateBackground", "毕业");
		params.addBodyParameter("graduateSchool", "佛大");
		params.addBodyParameter("baseCourseId", "10");
		params.addBodyParameter("residence", "广东肇庆");
		params.addBodyParameter("specialStudent", "0");
		params.addBodyParameter("graduateScore", "100");
		params.addBodyParameter("postcode", "528000");
		params.addBodyParameter("phonecall", "111111111");
		params.addBodyParameter("telephone", "10086");
		params.addBodyParameter("linkman", "陈浩林");
		params.addBodyParameter("note", "留言");
		params.addBodyParameter("birthday", "88年8月8日");


		btnSubmit = (Button)rootView.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("=== 开始提交报名表 ....");
				httpUtils.postForm1(params, tvTest);
			}
		});
	}

}
