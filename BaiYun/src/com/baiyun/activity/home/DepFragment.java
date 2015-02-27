package com.baiyun.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeDepHttpUtils;
import com.baiyun.vo.parcelable.HomeDepPar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DepFragment extends BaseFragment{
	private HomeDepHttpUtils httpUtils;
	
	private GridView gridView;
	private List<HomeDepPar> depPars = new ArrayList<HomeDepPar>();
	private MyGridAdapter gridAdapter;
	
	public static DepFragment newInstance() {
		return new DepFragment();
	}
	
	public DepFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_dep;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new HomeDepHttpUtils(getActivity());
		
		gridView = (GridView)rootView.findViewById(R.id.gv_dep);
		gridAdapter = new MyGridAdapter();
		gridView.setAdapter(gridAdapter);
		
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HomeDepPar depPar = depPars.get(position);
				((DepartmentActivity)getActivity()).showWebViewFragment2(depPar.getContentUrl(), depPar.getThreeMenuName());
			}
			
		});
		
		getData();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((DepartmentActivity)getActivity()).setTopBarTitle("系部设置");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((DepartmentActivity)getActivity()).setLoadingBarGone();
	}
	
	private void getData(){
		((DepartmentActivity)getActivity()).setLoadingBarVisible();
		httpUtils.getList(new HomeDepHttpUtils.OnGetListListener() {
			
			@Override
			public void OnGetList(List<HomeDepPar> depPars) {
				if (getActivity() != null) {
					((DepartmentActivity)getActivity()).setLoadingBarGone();
				}
				if (depPars != null) {
					DepFragment.this.depPars = depPars;
					gridAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	private class MyGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return depPars.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return depPars.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.grid_item_dep, null);
				holder.ivIcon = (ImageView)convertView.findViewById(R.id.iv_icon);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			HomeDepPar depPar = depPars.get(position);
			if (depPar.getImg() != null && !(depPar.getImg().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST+depPar.getImg().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivIcon);
			}
			holder.tvTitle.setText(depPar.getThreeMenuName());
			
			return convertView;
		}
		
		public final class ViewHolder{
			ImageView ivIcon;
			TextView tvTitle;
		}
		
	}
	
}
