package com.baiyun.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeHttpUtils;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.OveDepPar;
import com.baiyun.vo.parcelable.OveDepTeacherPar;
import com.baiyun.vo.parcelable.Vo1Par;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OveTeachersFragment extends BaseFragment implements OnClickListener{
	private VoHttpUtils httpUtils;
	private HomeHttpUtils depParHttpUtils;
	
	private LinearLayout llSummary;
	private TextView tvSummary;
	private Vo1Par vo1Par = null;
	
	private PullToRefreshListView pullToRefreshListView;
	private List<OveDepPar> depPars = new ArrayList<OveDepPar>();
	private MyListAdapter listAdapter;
	
	private int page = 1;
	
	public static OveTeachersFragment newInstance() {
		return new OveTeachersFragment();
	}
	
	public OveTeachersFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_ove_teachers;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new VoHttpUtils(getActivity());
		depParHttpUtils = new HomeHttpUtils(getActivity());
		
		initView(rootView);
		
		getSum();
		getDepParList(page);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((OverviewActivity)getActivity()).setTopBarTitle("师资队伍");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((OverviewActivity)getActivity()).setLoadingBarGone();
	}

	private void initView(View rootView){
		llSummary = (LinearLayout)rootView.findViewById(R.id.ll_summary);
		tvSummary = (TextView)rootView.findViewById(R.id.tv_summary);
		
		pullToRefreshListView= (PullToRefreshListView)rootView.findViewById(R.id.reflesh_listview);
		pullToRefreshListView.setMode(Mode.PULL_FROM_END);
		
		listAdapter = new MyListAdapter();
		pullToRefreshListView.getRefreshableView().setAdapter(listAdapter);
		
		pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
				page++;
				getDepParList(page);
			}
			
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_summary:
			((OverviewActivity)getActivity()).showWebViewFragment2(vo1Par.getUrl(), "师资队伍");
			break;

		default:
			break;
		}
		
	}

	private void getSum(){
		((OverviewActivity)getActivity()).setLoadingBarVisible();
		httpUtils.getVo1(HttpURL.SCHOOL_TEACHERS_SUM, new VoHttpUtils.OnGetVo1Listener() {
			
			@Override
			public void onGetVo1(Vo1Par vo1Par) {
				if (vo1Par != null) {
					OveTeachersFragment.this.vo1Par = vo1Par;
					tvSummary.setText(vo1Par.getBrief());
					llSummary.setOnClickListener(OveTeachersFragment.this);
				}
			}
		});
	}
	
	private void getDepParList(final int page){
		depParHttpUtils.getOveDepPars(page, new HomeHttpUtils.OnGetOveDepParsListener() {
			
			@Override
			public void onGetOveDepPars(List<OveDepPar> oveDepPars) {
				if (page == 1) {
					if (getActivity() != null) {
						((OverviewActivity)getActivity()).setLoadingBarGone();
					}
				}
				if (oveDepPars != null) {
					depPars.addAll(oveDepPars);
					listAdapter.notifyDataSetChanged();
				}
				pullToRefreshListView.onRefreshComplete();
				
			}
		});
	}
	
	private class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return depPars.size();
		}

		@Override
		public Object getItem(int position) {
			return depPars.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_ove_dep, null);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tvMore = (TextView)convertView.findViewById(R.id.tv_more);
				holder.gridView = (GridView)convertView.findViewById(R.id.gv_picture);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			final OveDepPar depPar = depPars.get(position);
			holder.tvTitle.setText(depPar.getName()+":");
			holder.tvMore.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((OverviewActivity)getActivity()).showOveTeachersGridFragment(depPar.getId());
				}
			});
			
			final List<OveDepTeacherPar> teacherPars = depPar.getgAppContentPicViewList();
			
			MyGridAdapter gridAdapter = new MyGridAdapter(getActivity(), teacherPars);
			holder.gridView.setAdapter(gridAdapter);
			holder.gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					OveDepTeacherPar teacherPar = teacherPars.get(position);
					((OverviewActivity)getActivity()).showOveTeachersDetailFragment(teacherPar);
				}
				
			});
			
			return convertView;
		}
		
		public final class ViewHolder{
			TextView tvTitle;
			TextView tvMore;
			GridView gridView;
		}
		
	}
	
	private class MyGridAdapter extends BaseAdapter{
		private Context context;
		private List<OveDepTeacherPar> teacherPars;
		
		public MyGridAdapter(Context context, List<OveDepTeacherPar> teacherPars) {
			this.context = context;
			this.teacherPars = teacherPars;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return teacherPars.size();
		}

		@Override
		public Object getItem(int position) {
			return teacherPars.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_ove_dep_teacher, null);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_name);
				holder.ivPicture = (ImageView)convertView.findViewById(R.id.iv_picture);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			OveDepTeacherPar teacherPar = teacherPars.get(position);
			holder.tvTitle.setText(teacherPar.getTitle());
			String urlLast = teacherPar.getPicUrl();
			if (!TextUtils.isEmpty(urlLast)) {
				String picUrl = HttpURL.HOST+urlLast.substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivPicture);
			}
			
			return convertView;
		}
		
		public final class ViewHolder{
			TextView tvTitle;
			ImageView ivPicture;
		}
		
	}
}
