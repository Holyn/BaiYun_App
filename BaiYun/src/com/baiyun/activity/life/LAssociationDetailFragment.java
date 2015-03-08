package com.baiyun.activity.life;

import com.baiyun.activity.R;
import com.baiyun.activity.home.TraGuideFragment;
import com.baiyun.activity.home.TraSearchFragment;
import com.baiyun.httputils.SchoolLifeHttpUtils;
import com.baiyun.vo.parcelable.LifeAssociationPar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class LAssociationDetailFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
	public static final String KEY_LIFE_ASSOCIATION_PAR = "key_life_association_par";
	
	private View rootView;
	
	private LifeAssociationPar associationPar;
	
	private FragmentManager fragmentManager;
	private Fragment curFragment;
	
	private int curPosition = -1;
	private LAssociationIntroduceFragment introduceFragment = null;
	private LAssociationNewsFragment newsFragment = null;

	private RadioGroup rgTab;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		associationPar = getArguments().getParcelable(KEY_LIFE_ASSOCIATION_PAR);
		
		fragmentManager = getChildFragmentManager();
		
		if (introduceFragment == null) {
			introduceFragment = LAssociationIntroduceFragment.newInstance();
		}
		if (!introduceFragment.isAdded()) {
			Bundle args = new Bundle();
			args.putString(LAssociationIntroduceFragment.KEY_URL, associationPar.getUrl());
			introduceFragment.setArguments(args);
			curFragment = introduceFragment;
			fragmentManager.beginTransaction().add(R.id.fl_child_container, curFragment).commit();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_association_detail, container, false);
		initView();
		return rootView;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((LAssociationActivity)getActivity()).setTopBarTitle(associationPar.getName());
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		switchFragment(0);
//		rgTab.check(R.id.rb_tab_1);
	}

	private void initView(){
		rgTab = (RadioGroup)rootView.findViewById(R.id.rg_tab);
		rgTab.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_tab_1:
			switchFragment(0);
			break;
		case R.id.rb_tab_2:
			switchFragment(1);
			break;
		default:
			break;
		}
	}
	
	public void switchFragment(int position) {
		Fragment nextFragment = null;
		if (position == 0) {
			if (introduceFragment == null) {
				introduceFragment = LAssociationIntroduceFragment.newInstance();
				Bundle args = new Bundle();
				args.putString(LAssociationIntroduceFragment.KEY_URL, associationPar.getUrl());
				introduceFragment.setArguments(args);
			}
			nextFragment = introduceFragment;
		}else if (position == 1) {
			if (newsFragment == null) {
				newsFragment = LAssociationNewsFragment.newInstance();
				Bundle args = new Bundle();
				args.putString(LAssociationNewsFragment.KEY_ID, associationPar.getId());
				newsFragment.setArguments(args);
			}
			nextFragment = newsFragment;
		}
		if (curPosition != position) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			if (!nextFragment.isAdded()) {    // 先判断是否被add过  
                transaction.hide(curFragment).add(R.id.fl_child_container, nextFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中  
            } else {  
                transaction.hide(curFragment).show(nextFragment).commit(); // 隐藏当前的fragment，显示下一个  
            } 
			curPosition = position;
			curFragment = nextFragment;
		}
		
	}
}
