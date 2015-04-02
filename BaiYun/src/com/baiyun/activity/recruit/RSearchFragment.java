package com.baiyun.activity.recruit;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.RecruitHttpUtils;
import com.baiyun.vo.parcelable.AdmissionResultPar;

public class RSearchFragment extends BaseFragment{
	private RecruitHttpUtils httpUtils;
	
	private EditText etName,etPhone;
	private Button btnSearch;
	private TextView tvResult;
	
	public static RSearchFragment newInstance() {
		return new RSearchFragment();
	}
	
	public RSearchFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_r_search;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new RecruitHttpUtils(getActivity());
		
		initView(rootView);
		
	}
	
	private void initView(View rootView){
		etName = (EditText)rootView.findViewById(R.id.et_name);
		etPhone = (EditText)rootView.findViewById(R.id.et_phone);
		tvResult = (TextView)rootView.findViewById(R.id.tv_result);
		
		btnSearch = (Button)rootView.findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = etName.getText().toString().trim();
				if (TextUtils.isEmpty(name)) {
					Toast.makeText(getActivity(), "名字不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				String phone = etPhone.getText().toString().trim();
				if (TextUtils.isEmpty(phone)) {
					Toast.makeText(getActivity(), "电话不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				
				showLoadingDialog();
				httpUtils.resultCheck(name, phone, new RecruitHttpUtils.OnResultCheckListener() {
					
					@Override
					public void onResultCheck(AdmissionResultPar resultPar) {
						// TODO Auto-generated method stub
						closeLoadingDialog();
						if (resultPar != null) {
							tvResult.setText(resultPar.getResult());
						}
					}
				});
			}
		});
	
	}

}
