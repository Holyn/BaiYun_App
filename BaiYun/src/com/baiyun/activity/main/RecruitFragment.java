package com.baiyun.activity.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.activity.recruit.ApplyFragment;
import com.baiyun.activity.recruit.RSearchActivity;
import com.baiyun.activity.recruit.RecruitTypeFragment;
import com.baiyun.activity.recruit.EnterFragment;
import com.baiyun.activity.recruit.TuitionFragment;
import com.baiyun.base.BaseFragment;
import com.baiyun.kefu.KeFuManager;

public class RecruitFragment extends BaseFragment{
	private RadioButton rb_1,rb_2,rb_3,rb_4;
	private TextView tv_1,tv_2,tv_3,tv_4;
	
	private Button btnConsult;
	private TextView tv_item_title;
	private Button btnSearch;
	
	private FragmentManager fragmentManager;
	private Fragment curFragment;
	
	private int curPosition = -1;
	private TuitionFragment tuitionFragment = null;
	private EnterFragment enterFragment = null;
	private RecruitTypeFragment consultFragment = null;
	private ApplyFragment applyFragment = null;
	
	public static RecruitFragment newInstance() {
		return new RecruitFragment();
	}

	public RecruitFragment() {
		
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_recruit;
	}

	@Override
	public void createMyView(View rootView) {
		fragmentManager = getChildFragmentManager();
		initView(rootView);
		
		consultFragment = RecruitTypeFragment.newInstance();
		curFragment = consultFragment;
		fragmentManager.beginTransaction().add(R.id.fl_container, curFragment).commit();
	}
	
	private void initView(View rootView){
		rb_1 = (RadioButton)rootView.findViewById(R.id.rb_tuition);
		rb_2 = (RadioButton)rootView.findViewById(R.id.rb_enter);
		rb_3 = (RadioButton)rootView.findViewById(R.id.rb_consult);
		rb_4 = (RadioButton)rootView.findViewById(R.id.rb_apply);
		
		rb_1.setChecked(false);
		rb_2.setChecked(false);
		rb_3.setChecked(true);
		rb_4.setChecked(false);
		
		tv_1 = (TextView)rootView.findViewById(R.id.tv_tuition);
		tv_2 = (TextView)rootView.findViewById(R.id.tv_enter);
		tv_3 = (TextView)rootView.findViewById(R.id.tv_consult);
		tv_4 = (TextView)rootView.findViewById(R.id.tv_apply);
		
		tv_item_title = (TextView)rootView.findViewById(R.id.tv_item_title);
		btnConsult = (Button)rootView.findViewById(R.id.btn_consult);
		btnConsult.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new KeFuManager(getActivity()).startChat();
			}
		});
		
		btnSearch = (Button)rootView.findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), RSearchActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		initRadioButtonListener();
	}
	
	private void initRadioButtonListener(){
		rb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rb_1.setChecked(true);
					rb_2.setChecked(false);
					rb_3.setChecked(false);
					rb_4.setChecked(false);
					
					tv_1.setTextColor(getActivity().getResources().getColor(R.color.actionbar_bg));
					tv_item_title.setText("减免学费申请指南");
					
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
					rb_4.setChecked(false);
					
					tv_2.setTextColor(getActivity().getResources().getColor(R.color.actionbar_bg));
					tv_item_title.setText("新生入学须知");
					
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
					rb_4.setChecked(false);
					
					tv_3.setTextColor(getActivity().getResources().getColor(R.color.actionbar_bg));
					tv_item_title.setText("招生层次");
					
					switchFragment(2);
				}else {
					tv_3.setTextColor(getActivity().getResources().getColor(R.color.gray));
				}
			}
			
		});
		rb_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					rb_1.setChecked(false);
					rb_2.setChecked(false);
					rb_3.setChecked(false);
					rb_4.setChecked(true);
					
					tv_4.setTextColor(getActivity().getResources().getColor(R.color.actionbar_bg));
					tv_item_title.setText("网上报名");
					
					switchFragment(3);
				}else {
					tv_4.setTextColor(getActivity().getResources().getColor(R.color.gray));
				}
			}
			
		});
	}
	
	public void switchFragment(int position) {
		Fragment nextFragment = null;
		if (position == 0) {
			if (tuitionFragment == null) {
				tuitionFragment = TuitionFragment.newInstance();
			}
			nextFragment = tuitionFragment;
		}else if (position == 1) {
			if (enterFragment == null) {
				enterFragment = EnterFragment.newInstance();
			}
			nextFragment = enterFragment;
		}else if (position == 2) {
			if (consultFragment == null) {
				consultFragment = RecruitTypeFragment.newInstance();
			}
			nextFragment = consultFragment;
		}else if (position == 3) {
			if (applyFragment == null) {
				applyFragment = ApplyFragment.newInstance();
			}
			nextFragment = applyFragment;
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
