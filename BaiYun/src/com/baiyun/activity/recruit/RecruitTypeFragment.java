package com.baiyun.activity.recruit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.activity.main.MainActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.ListViewForScrollView;
import com.baiyun.httputils.RecruitHttpUtils;
import com.baiyun.vo.parcelable.RecruitTypePar;

public class RecruitTypeFragment extends BaseFragment{
	private RecruitHttpUtils httpUtils;
	private List<RecruitTypePar> recruitTypePars;
	
	private List<RecruitTypePar> list_1 = new ArrayList<RecruitTypePar>();//menuSubId=16，高中生(职中、中专、中技)报读专区
	private List<RecruitTypePar> list_2 = new ArrayList<RecruitTypePar>();//menuSubId=17，初中生报读专区
	private List<RecruitTypePar> list_3 = new ArrayList<RecruitTypePar>();//menuSubId=18，职能培训报读专区
	
	private ScrollView scrollView;
	private ListViewForScrollView lvsv_1;
	private LvsvAdapter adapter_1;
	
	private ListViewForScrollView lvsv_2;
	private LvsvAdapter adapter_2;
	
	private ListViewForScrollView lvsv_3;
	private LvsvAdapter adapter_3;
	
	public static RecruitTypeFragment newInstance() {
		return new RecruitTypeFragment();
	}
	
	public RecruitTypeFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new RecruitHttpUtils(getActivity());
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_recruit_type;
	}

	@Override
	public void createMyView(View rootView) {
		scrollView = (ScrollView)rootView.findViewById(R.id.scrollview);
		scrollView.smoothScrollTo(0, 0);
		
		lvsv_1 = (ListViewForScrollView)rootView.findViewById(R.id.lvsv_1);
		adapter_1 = new LvsvAdapter(getActivity(),list_1);
		lvsv_1.setAdapter(adapter_1);
		
		lvsv_2 = (ListViewForScrollView)rootView.findViewById(R.id.lvsv_2);
		adapter_2 = new LvsvAdapter(getActivity(),list_2);
		lvsv_2.setAdapter(adapter_2);
		
		lvsv_3 = (ListViewForScrollView)rootView.findViewById(R.id.lvsv_3);
		adapter_3 = new LvsvAdapter(getActivity(),list_3);
		lvsv_3.setAdapter(adapter_3);
		
		getData();
	}
	
	private void getData() {
		((MainActivity) getActivity()).setLoadingBarVisible();
		httpUtils.getTypeList(new RecruitHttpUtils.OnGetTypeListListener() {
			
			@Override
			public void OnGetTypeList(List<RecruitTypePar> typePars) {
				if (getActivity() != null) {
					((MainActivity) getActivity()).setLoadingBarGone();
				}
				if (typePars != null) {
					recruitTypePars = typePars;
					System.out.println("====> typePars.size() = "+typePars.size());
//					separateList(typePars);
//					
//					adapter_1.notifyDataSetChanged();
				}
			}
		});
	}
	
	// 分解网络请求回来的List<RecruitTypePar> recruitTypePars
	private void separateList(List<RecruitTypePar> recruitTypePars){
		Iterator<RecruitTypePar> iterator = recruitTypePars.iterator();
		while (iterator.hasNext()) {
			RecruitTypePar recruitTypePar = (RecruitTypePar) iterator.next();
			if (recruitTypePar.getMenuSubId().equalsIgnoreCase("16")) {
				list_1.add(recruitTypePar);
			}else if (recruitTypePar.getMenuSubId().equalsIgnoreCase("17")) {
				list_2.add(recruitTypePar);
			}else if (recruitTypePar.getMenuSubId().equalsIgnoreCase("18")) {
				list_3.add(recruitTypePar);
			}
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((MainActivity) getActivity()).setLoadingBarGone();
	}
	
	private class LvsvAdapter extends BaseAdapter{
		
		private LayoutInflater inflater;
		private List<RecruitTypePar> list;

		public LvsvAdapter(Context context,List<RecruitTypePar> list) {
			inflater = LayoutInflater.from(context);
			this.list = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
//			return list.size()/2;
			return 2;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.list_item_recruit_type, parent, false);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				holder.btnPlan = (Button)convertView.findViewById(R.id.btn_plan);
				holder.btnIntroduce = (Button)convertView.findViewById(R.id.btn_intro);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.btnPlan.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("====> holder.btnPlan.setOnClick。。。。");
				}
			});
			
			return convertView;
		}
		
		public final class ViewHolder {
			public TextView tvTitle;
			public Button btnPlan;
			public Button btnIntroduce;
		}
		
	}
}
