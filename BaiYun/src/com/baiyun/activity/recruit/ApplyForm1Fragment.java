package com.baiyun.activity.recruit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.RecruitHttpUtils;
import com.baiyun.vo.parcelable.ApplyPar;
import com.lidroid.xutils.http.RequestParams;

public class ApplyForm1Fragment extends BaseFragment{
	private ApplyPar applyPar;
	private RecruitHttpUtils httpUtils;
	
	private TextView tvTitle;//年制招生标题
	private EditText etName;//姓名
	
	private Button btnSubmit;
	
	private void initView(View rootView){
		etName = (EditText)rootView.findViewById(R.id.et_name);
	}
	
	public static ApplyForm1Fragment newInstance() {
		return new ApplyForm1Fragment();
	}
	
	public ApplyForm1Fragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		applyPar = getArguments().getParcelable(ApplyFormActivity.VALUE_APPLYPAR);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_form_1;
		
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new RecruitHttpUtils(getActivity());
		
		tvTitle = (TextView)rootView.findViewById(R.id.tv_title);
		tvTitle.setText(applyPar.getName());
		
		
		final RequestParams params = new RequestParams();
		
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
		params.addBodyParameter("birthday", "1988/08/08");


		btnSubmit = (Button)rootView.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("=== onClick。开始提交报名表 ....");
				httpUtils.postForm1(params);
			}
		});
	}

}
