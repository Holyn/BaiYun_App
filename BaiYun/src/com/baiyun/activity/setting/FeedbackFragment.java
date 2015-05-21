package com.baiyun.activity.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.SlideMenuHttpUtils;

public class FeedbackFragment extends BaseFragment{
	private EditText etContent,etEmail;
	private Button btnSubmit;
	
	public static FeedbackFragment newInstance() {
		return new FeedbackFragment();
	}
	
	public FeedbackFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_feedback;
	}

	@Override
	public void createMyView(View rootView) {
		// TODO Auto-generated method stub
		initView(rootView);
	}
	
	private void initView(View rootView){
		etContent = (EditText)rootView.findViewById(R.id.et_content);
		etEmail = (EditText)rootView.findViewById(R.id.et_email);
		
		btnSubmit = (Button)rootView.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submitAdvice();
			}
		});
		
	}
	
	private void submitAdvice(){
		String content = etContent.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(getActivity(), "内容为空", Toast.LENGTH_SHORT).show();
			return;
		}
		String email = etEmail.getText().toString().trim();
		if (TextUtils.isEmpty(email)) {
			Toast.makeText(getActivity(), "邮件为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
//		new SlideMenuHttpUtils(context)
	}

}
