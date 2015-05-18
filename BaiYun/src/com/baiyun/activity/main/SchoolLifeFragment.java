package com.baiyun.activity.main;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.baiyun.activity.R;
import com.baiyun.activity.life.LAssociationActivity;
import com.baiyun.activity.life.LGuideActivity;
import com.baiyun.activity.life.LModelActivity;
import com.baiyun.activity.life.LNewsActivity;
import com.baiyun.base.BaseFragment;

public class SchoolLifeFragment extends BaseFragment{
	private Button btnNews;//学工动态
	private Button btnAssociation;//学生社团
	private Button btnArt;//体育艺术
	private Button btnScience;//科技创新（社团嘉年华）
	private Button btnModel;//榜样风云
	private Button btnGuide;//服务指南
	
	
	public static SchoolLifeFragment newInstance() {
		return new SchoolLifeFragment();
	}

	public SchoolLifeFragment() {
		
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_school_life;
	}

	@Override
	public void createMyView(View rootView) {
		btnNews = (Button)rootView.findViewById(R.id.btn_news);
		btnNews.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LNewsActivity.class);
				intent.putExtra(LNewsActivity.NEWS_ID_VALUE, LNewsActivity.NEWS_ID_24);
				getActivity().startActivity(intent);
			}
		});
		
		btnAssociation = (Button)rootView.findViewById(R.id.btn_association);
		btnAssociation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LAssociationActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		btnArt = (Button)rootView.findViewById(R.id.btn_art);
		btnArt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LNewsActivity.class);
				intent.putExtra(LNewsActivity.NEWS_ID_VALUE, LNewsActivity.NEWS_ID_26);
				getActivity().startActivity(intent);
			}
		});
		
		btnScience = (Button)rootView.findViewById(R.id.btn_science);
		btnScience.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LNewsActivity.class);
				intent.putExtra(LNewsActivity.NEWS_ID_VALUE, LNewsActivity.NEWS_ID_27);
				getActivity().startActivity(intent);
			}
		});
		
		btnModel = (Button)rootView.findViewById(R.id.btn_model);
		btnModel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LModelActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		btnGuide = (Button)rootView.findViewById(R.id.btn_guide);
		btnGuide.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LGuideActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}
	
	
}
