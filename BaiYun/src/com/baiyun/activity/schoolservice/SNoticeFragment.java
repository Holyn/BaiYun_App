package com.baiyun.activity.schoolservice;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.SchoolServiceHttpUtils;
import com.baiyun.vo.parcelable.Vo1Par;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class SNoticeFragment extends BaseFragment{
	private SchoolServiceHttpUtils httpUtils;
	
	private PullToRefreshListView pullToRefreshListView;
	
	private List<Vo1Par> notices = new ArrayList<Vo1Par>();
	private ListAdapter listAdapter;
	private int page = 1;
	
	public static SNoticeFragment newInstance() {
		return new SNoticeFragment();
	}
	
	public SNoticeFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new SchoolServiceHttpUtils(getActivity());
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.pull_to_refresh_listview;
	}

	@Override
	public void createMyView(View rootView) {
		initView(rootView);
		
		((SNoticeActivity)getActivity()).setLoadingBarVisible();
		getNetData(page);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((SNoticeActivity)getActivity()).setTopBarTitle("通知公告");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((SNoticeActivity)getActivity()).setLoadingBarGone();
	}
	
	private void initView(View rootView){
		pullToRefreshListView = (PullToRefreshListView)rootView.findViewById(R.id.refresh_listview);
		pullToRefreshListView.setMode(Mode.PULL_FROM_END);
		
		listAdapter = new ListAdapter(getActivity(), notices);
		pullToRefreshListView.getRefreshableView().setAdapter(listAdapter);
		
		pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
				page++;
				getNetData(page);
			}
		});
		
		pullToRefreshListView.getRefreshableView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    if(id == -1) {
			        // 点击的是headerView或者footerView
			        return;
			    }
			    int realPosition=(int)id;
			    Vo1Par notice = notices.get(realPosition);
				((SNoticeActivity)getActivity()).showWebViewFragment2(notice.getUrl(), "公告详情");
			}
			
		});
	}
	
	private void getNetData(final int page){
		httpUtils.getNotice(page, new SchoolServiceHttpUtils.OnGetNoticeListener() {
			
			@Override
			public void onGetNotice(List<Vo1Par> vo1Pars) {
				if (page == 1) {
					if (getActivity() != null) {
						((SNoticeActivity)getActivity()).setLoadingBarGone();
					}
					if (vo1Pars != null) {
						notices.addAll(vo1Pars);
						listAdapter.notifyDataSetChanged();
					}
					pullToRefreshListView.onRefreshComplete();
				}
			}
		});
	}

	private class ListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<Vo1Par> list;

		public ListAdapter(Context context, List<Vo1Par> list) {
			inflater = LayoutInflater.from(context);
			this.list = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
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
				convertView = inflater.inflate(R.layout.list_item_service_notice, null);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			Vo1Par notice = list.get(position);
			holder.tvTitle.setText(notice.getTitle());;
			
			return convertView;
		}
		
		public final class ViewHolder {
			public TextView tvTitle;
		}
		
	}
	
}
