package com.baiyun.fragment.sliding;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.setting.SettingItemActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.SlipButton;

public class SettingFragment extends BaseFragment{
	private SlipButton sbChangePush;
	private TextView tvChangePwd;
	private TextView tvVersionUpdate;
	private TextView tvHelp;
	private TextView tvFeedback;
	private TextView tvAbout;
	
	public static SettingFragment newInstance() {
		return new SettingFragment();
	}
	
	public SettingFragment() {
		
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_setting;
	}

	@Override
	public void createMyView(View rootView) {
		initView(rootView);
		initListener();
	}
	
	private void initView(View rootView){
		sbChangePush = (SlipButton)rootView.findViewById(R.id.slip_utton);
		
		tvChangePwd = (TextView)rootView.findViewById(R.id.tv_change_pwd);
		tvVersionUpdate = (TextView)rootView.findViewById(R.id.tv_version_update);
		tvHelp = (TextView)rootView.findViewById(R.id.tv_help);
		tvFeedback = (TextView)rootView.findViewById(R.id.tv_feedback);
		tvAbout = (TextView)rootView.findViewById(R.id.tv_about);
	}
	
	private void initListener(){
		sbChangePush.SetOnChangedListener(new SlipButton.onChangeListener() {
			
			@Override
			public void OnChanged(boolean CheckState) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), String.valueOf(CheckState), Toast.LENGTH_SHORT).show();
			}
		});
		
		tvChangePwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.CHANGE_PWD);
				startActivity(intent);
			}
		});
		
		tvVersionUpdate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		tvHelp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.HELP);
				startActivity(intent);
			}
		});
		
		tvFeedback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.FEEDBACK);
				startActivity(intent);
			}
		});
		
		tvAbout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.ABOUT);
				startActivity(intent);
			}
		});
	}
	
}
