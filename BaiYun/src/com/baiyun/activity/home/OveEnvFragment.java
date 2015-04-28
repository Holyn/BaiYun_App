package com.baiyun.activity.home;

import java.util.ArrayList;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.picturesviewer.PicturesViewPagerActivity2;
import com.baiyun.vo.parcelable.VoPicPar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OveEnvFragment extends BaseFragment{
	private VoHttpUtils httpUtils;
	
	private GridView gridView;
	private ArrayList<VoPicPar> picPars = new ArrayList<VoPicPar>();
	private MyGridAdapter gridAdapter;
	
	public static OveEnvFragment newInstance() {
		return new OveEnvFragment();
	}
	
	public OveEnvFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.gridview_comment;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new VoHttpUtils(getActivity());
		
		gridView = (GridView)rootView.findViewById(R.id.gv_dep);
		gridAdapter = new MyGridAdapter();
		gridView.setAdapter(gridAdapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), PicturesViewPagerActivity2.class);
				intent.putParcelableArrayListExtra(PicturesViewPagerActivity2.EXTRA_VO_PIC_PAR_LIST, picPars);
				intent.putExtra(PicturesViewPagerActivity2.EXTRA_CUR_POSITION, position);
				getActivity().startActivity(intent);
			}
		});
		
		getData();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((OverviewActivity)getActivity()).setTopBarTitle("学校环境");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((OverviewActivity)getActivity()).setLoadingBarGone();
	}

	private void getData(){
		((OverviewActivity)getActivity()).setLoadingBarVisible();
		httpUtils.getPicList(HttpURL.SCHOOL_ENVIRONMENT, new VoHttpUtils.OnGetPicListListener() {
			
			@Override
			public void onGetPicList(ArrayList<VoPicPar> picPars) {
				if (getActivity() != null) {
					((OverviewActivity)getActivity()).setLoadingBarGone();
				}
				if (picPars != null) {
					OveEnvFragment.this.picPars = picPars;
					gridAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	private class MyGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return picPars.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return picPars.get(position);
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.grid_item_env, null);
				holder.ivPic = (ImageView)convertView.findViewById(R.id.iv_pic);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			VoPicPar picPar = picPars.get(position);
			if (picPar.getUrl() != null && !(picPar.getUrl().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST+picPar.getUrl().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivPic);
			}
			holder.tvTitle.setText(picPar.getName());
			
			return convertView;
		}
		
		public final class ViewHolder{
			ImageView ivPic;
			TextView tvTitle;
		}
		
	}
}
