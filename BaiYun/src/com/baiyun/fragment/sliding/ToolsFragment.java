package com.baiyun.fragment.sliding;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;

public class ToolsFragment extends BaseFragment{
	private RadioButton rb_1,rb_2,rb_3;
	private TextView tv_1,tv_2,tv_3;
	private TextView tv_item_title;
	
	private FragmentManager fragmentManager;
	private Fragment curFragment;
	
	private int curPosition = -1;
	
	private ToolsBusFragment busFragment = null;
	private ToolsMetroFragment metroFragment = null;
	private ToolsScanFragment scanFragment = null;
	
	public static ToolsFragment newInstance() {
		return new ToolsFragment();
	}
	
	public ToolsFragment() {
		
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_tools;
	}

	@Override
	public void createMyView(View rootView) {
		fragmentManager = getChildFragmentManager();
		
		initView(rootView);
		initRadioButtonListener();
		
		busFragment = ToolsBusFragment.newInstance();
		curFragment = busFragment;
		fragmentManager.beginTransaction().add(R.id.fl_container, curFragment).commit();
	}
	
	private void initView(View rootView){
		rb_1 = (RadioButton)rootView.findViewById(R.id.rb_bus);
		rb_2 = (RadioButton)rootView.findViewById(R.id.rb_metro);
		rb_3 = (RadioButton)rootView.findViewById(R.id.rb_scan);
		
		rb_1.setChecked(true);
		rb_2.setChecked(false);
		rb_3.setChecked(false);
		
		tv_1 = (TextView)rootView.findViewById(R.id.tv_bus);
		tv_2 = (TextView)rootView.findViewById(R.id.tv_metro);
		tv_3 = (TextView)rootView.findViewById(R.id.tv_scan);
		
		tv_item_title = (TextView)rootView.findViewById(R.id.tv_item_title);
	}
	
	private void initRadioButtonListener(){
		rb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rb_1.setChecked(true);
					rb_2.setChecked(false);
					rb_3.setChecked(false);
					
					tv_1.setTextColor(getActivity().getResources().getColor(R.color.actionbar_bg));
					tv_item_title.setText("班车表");
					
					switchFragment(0);
				}else {
					tv_1.setTextColor(getActivity().getResources().getColor(R.color.gray));
				}
				
			}
			
		});
		rb_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rb_1.setChecked(false);
					rb_2.setChecked(true);
					rb_3.setChecked(false);
					
					tv_2.setTextColor(getActivity().getResources().getColor(R.color.actionbar_bg));
					tv_item_title.setText("地铁线路图");
					
					switchFragment(1);
				}else {
					tv_2.setTextColor(getActivity().getResources().getColor(R.color.gray));
				}
			}
			
		});
		rb_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rb_1.setChecked(false);
					rb_2.setChecked(false);
					rb_3.setChecked(true);
					
					tv_3.setTextColor(getActivity().getResources().getColor(R.color.actionbar_bg));
					tv_item_title.setText("扫描找书");
					
					switchFragment(2);
				}else {
					tv_3.setTextColor(getActivity().getResources().getColor(R.color.gray));
				}
			}
			
		});
	}
	
	public void switchFragment(int position) {
		Fragment nextFragment = null;
		if (position == 0) {
			if (busFragment == null) {
				busFragment = ToolsBusFragment.newInstance();
			}
			nextFragment = busFragment;
		}else if (position == 1) {
			if (metroFragment == null) {
				metroFragment = ToolsMetroFragment.newInstance();
			}
			nextFragment = metroFragment;
		}else if (position == 2) {
			if (scanFragment == null) {
				scanFragment = ToolsScanFragment.newInstance();
			}
			nextFragment = scanFragment;
		}
		
		if (curPosition != position) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			if (!nextFragment.isAdded()) {    // 先判断是否被add过  
                transaction.hide(curFragment).add(R.id.fl_container, nextFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中  
            } else {  
                transaction.hide(curFragment).show(nextFragment).commit(); // 隐藏当前的fragment，显示下一个  
            } 
			curPosition = position;
			curFragment = nextFragment;
		}
	}
	
}
