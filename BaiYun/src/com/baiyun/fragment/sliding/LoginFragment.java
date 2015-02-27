package com.baiyun.fragment.sliding;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;

public class LoginFragment extends BaseFragment{
	private EditText etName,etPassword,etVeriCode;
	private ImageView ivName,ivPassword,ivVeriCode;
	private Button btnSubmit;
	
	public static LoginFragment newInstance() {
		return new LoginFragment();
	}
	
	public LoginFragment() {
		
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_login;
	}

	@Override
	public void createMyView(View rootView) {
		etName = (EditText)rootView.findViewById(R.id.et_name);
		ivName = (ImageView)rootView.findViewById(R.id.iv_name);
		etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					ivName.setImageResource(R.drawable.ic_login_pre);
				}else {
					ivName.setImageResource(R.drawable.ic_login_nor);
				}
				
			}
		});
		
		etPassword = (EditText)rootView.findViewById(R.id.et_password);
		ivPassword = (ImageView)rootView.findViewById(R.id.iv_password);
		etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					ivPassword.setImageResource(R.drawable.ic_password_pre);
				}else {
					ivPassword.setImageResource(R.drawable.ic_password_nor);
				}
				
			}
		});
		
		etVeriCode = (EditText)rootView.findViewById(R.id.et_veri_code);
		ivVeriCode = (ImageView)rootView.findViewById(R.id.iv_veri_code);
		etVeriCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					ivVeriCode.setImageResource(R.drawable.ic_veri_code_pre);
				}else {
					ivVeriCode.setImageResource(R.drawable.ic_veri_code_nor);
				}
				
			}
		});
		
		btnSubmit = (Button)rootView.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
}
