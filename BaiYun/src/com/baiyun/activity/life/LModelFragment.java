package com.baiyun.activity.life;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.activity.home.JobActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.SchoolLifeHttpUtils;
import com.baiyun.vo.parcelable.LifeAssociationPar;
import com.baiyun.vo.parcelable.Vo2Par;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LModelFragment extends BaseFragment{
	private SchoolLifeHttpUtils httpUtils;
	
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView gridView;
	private List<Vo2Par> models = new ArrayList<Vo2Par>();
	private MyGridAdapter gridAdapter;
	
	private int page = 1;
	
	public static LModelFragment newInstance() {
		return new LModelFragment();
	}

	public LModelFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new SchoolLifeHttpUtils(getActivity());
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.pull_to_refresh_gridview;
	}

	@Override
	public void createMyView(View rootView) {
		// TODO Auto-generated method stub
		initView(rootView);
		
		((LModelActivity)getActivity()).setLoadingBarVisible();
		getNetData(page);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((LModelActivity)getActivity()).setTopBarTitle("榜样白云");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((LModelActivity)getActivity()).setLoadingBarGone();
	}
	
	private void initView(View rootView){
		mPullRefreshGridView = (PullToRefreshGridView) rootView.findViewById(R.id.reflesh_gridview);
		mPullRefreshGridView.setMode(Mode.PULL_FROM_END);
		
		gridView = mPullRefreshGridView.getRefreshableView();
		gridAdapter = new MyGridAdapter();
		gridView.setAdapter(gridAdapter);
		
		mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {

			@Override
			public void onRefresh(PullToRefreshBase<GridView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				mPullRefreshGridView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
				page++;
				getNetData(page);
			}
			
		});
		
		//监听griview的点击事件
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("====> position = "+position);
				Vo2Par model = models.get(position);
				((LModelActivity)getActivity()).showWebViewFragment2(model.getContentUrl(), "个人简介");
			}
			
		});
	}
	
	private void getNetData(final int page){
		httpUtils.getModel(page, new SchoolLifeHttpUtils.onGetModelListener() {
			
			@Override
			public void onGetModel(List<Vo2Par> vo2Pars) {
				if (page == 1) {
					if (getActivity() != null) {
						((LModelActivity)getActivity()).setLoadingBarGone();
					}
				}
				if (vo2Pars != null) {
					models.addAll(vo2Pars);
					gridAdapter.notifyDataSetChanged();
					gridView.smoothScrollToPosition(models.size());
				}
				mPullRefreshGridView.onRefreshComplete();
			}
		});
	}
	
	private class MyGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return models.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return models.get(position);
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.grid_item_model, null);
				holder.ivHeader = (ImageView)convertView.findViewById(R.id.iv_header);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tvContent = (TextView)convertView.findViewById(R.id.tv_content);
				holder.ivLineRight = (ImageView)convertView.findViewById(R.id.iv_line_right);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			if ((position+1)%3 == 0) {
				holder.ivLineRight.setVisibility(View.GONE);
			}else {
				holder.ivLineRight.setVisibility(View.VISIBLE);
			}
			
			Vo2Par model = models.get(position);
			if (model.getPicUrl() != null && !(model.getPicUrl().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST+model.getPicUrl().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivHeader);
			}
			holder.tvTitle.setText(model.getTitle());
			holder.tvContent.setText(model.getBrief());
			
			return convertView;
		}
		
		public final class ViewHolder{
			ImageView ivHeader;
			TextView tvTitle;
			TextView tvContent;
			ImageView ivLineRight;
		}
		
	}

}
