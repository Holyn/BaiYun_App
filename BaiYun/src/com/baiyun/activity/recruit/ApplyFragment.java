package com.baiyun.activity.recruit;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.activity.main.MainActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.RecruitHttpUtils;
import com.baiyun.vo.parcelable.ApplyPar;

public class ApplyFragment extends BaseFragment{
	private RecruitHttpUtils httpUtils;
	
	private ListView listView;
	private List<ApplyPar> applyPars = new ArrayList<ApplyPar>();
	private ListAdapter adapter;
	
	public static ApplyFragment newInstance() {
		return new ApplyFragment();
	}
	
	public ApplyFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_apply;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new RecruitHttpUtils(getActivity());
		
		listView = (ListView)rootView.findViewById(R.id.listview);
		adapter = new ListAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String type = applyPars.get(position).getType();
				Intent intent = new Intent(getActivity(), ApplyFormActivity.class);
				if (type.equalsIgnoreCase("131")) {//导入报名表1接口：导入（年制高技+成人大专招生、四年制技师+成人本科招生、五年制高技+成人大专招生三年制中级技工招生）报名表至后台 
					intent.putExtra(ApplyFormActivity.FORM_TYPE_INT, ApplyFormActivity.FORM_1); 
				}else if (type.equalsIgnoreCase("132")) {//导入报名表2接口：导入职业技能培训招生报名表至后台
					intent.putExtra(ApplyFormActivity.FORM_TYPE_INT, ApplyFormActivity.FORM_2); 
				}
				intent.putExtra(ApplyFormActivity.VALUE_APPLYPAR, applyPars.get(position)); 
				getActivity().startActivity(intent);
			}
			
		});
		
		getData();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((MainActivity) getActivity()).setLoadingBarGone();
	}

	private void getData() {
		((MainActivity) getActivity()).setLoadingBarVisible();

		httpUtils.getApplyList(new RecruitHttpUtils.OnGetApplyListListener() {
			
			@Override
			public void OnGetApplyList(List<ApplyPar> applyPars) {
				if (getActivity() != null) {
					((MainActivity) getActivity()).setLoadingBarGone();
				}
				if (applyPars != null) {
					ApplyFragment.this.applyPars = applyPars;
					adapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	private class ListAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return applyPars.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return applyPars.get(position);
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_apply, parent, false);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ApplyPar applyPar = applyPars.get(position);
			holder.tvTitle.setText(applyPar.getName());

			return convertView;
		}

		public final class ViewHolder {
			public TextView tvTitle;
		}

	}
}
