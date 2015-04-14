package com.baiyun.activity.main;

import com.baiyun.activity.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SlideMenuFragment extends Fragment{
	public static final int MENU_LOGIN = 1;//用户登陆
	public static final int MENU_TOOLS = 2;//实用工具
	public static final int MENU_SETTING = 3;//设置
	public static final int MENU_HELP = 4;//使用帮助
	public static final int MENU_ABOUT = 5;//关于我们
	public static final int MENU_EXIT = 6;//退出
	private View rootView;
	
	public OnSlideMenuFragmentEventListener onSlideMenuFragmentEventListener;
	public void setOnSlideMenuFragmentEventListener(
			OnSlideMenuFragmentEventListener onSlideMenuFragmentEventListener) {
		this.onSlideMenuFragmentEventListener = onSlideMenuFragmentEventListener;
	}
	public interface OnSlideMenuFragmentEventListener{
		public void onSlideMenuFragmentEvent(int menuType);
	};
	
	public static SlideMenuFragment newInstance() {
		return new SlideMenuFragment();
	}
	
	public SlideMenuFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_slide_menu, container, false);
		
		LinearLayout llInfo = (LinearLayout)rootView.findViewById(R.id.ll_info);
		llInfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		RelativeLayout rlLogin = (RelativeLayout)rootView.findViewById(R.id.rl_login);
		rlLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_LOGIN);
			}
		});
		
		RelativeLayout rlTools = (RelativeLayout)rootView.findViewById(R.id.rl_tools);
		rlTools.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_TOOLS);
			}
		});
		
		RelativeLayout rlSetting = (RelativeLayout)rootView.findViewById(R.id.rl_setting);
		rlSetting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_SETTING);
			}
		});
		
		RelativeLayout rlHelp = (RelativeLayout)rootView.findViewById(R.id.rl_help);
		rlHelp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_HELP);
			}
		});
		
		RelativeLayout rlAbout = (RelativeLayout)rootView.findViewById(R.id.rl_about);
		rlAbout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_ABOUT);
			}
		});
		
		RelativeLayout rlExit = (RelativeLayout)rootView.findViewById(R.id.rl_exit);
		rlExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSlideMenuFragmentEventListener.onSlideMenuFragmentEvent(MENU_EXIT);
			}
		});
		
		
		return rootView;
	}
	
	
}
