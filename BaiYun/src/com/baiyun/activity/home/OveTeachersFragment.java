package com.baiyun.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.Vo1Par;

public class OveTeachersFragment extends BaseFragment implements OnClickListener{
	private VoHttpUtils httpUtils;
	private LinearLayout llSummary;
	private TextView tvSummary;
	private Vo1Par vo1Par = null;
	
	private GridView gridView;
	private List<Vo1Par> teacherList = new ArrayList<Vo1Par>();
	private MyGridAdapter gridAdapter;
	
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
		
		initView(rootView);
		
		getSum();
		getList();
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
		
		gridView = (GridView)rootView.findViewById(R.id.gv_teachers);
		gridAdapter = new MyGridAdapter();
		gridView.setAdapter(gridAdapter);
		
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				((OverviewActivity)getActivity()).showWebViewFragment2(teacherList.get(position).getUrl(), teacherList.get(position).getTitle());
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
				if (getActivity() != null) {
					((OverviewActivity)getActivity()).setLoadingBarGone();
				}
				if (vo1Par != null) {
					OveTeachersFragment.this.vo1Par = vo1Par;
					tvSummary.setText(vo1Par.getBrief());
					llSummary.setOnClickListener(OveTeachersFragment.this);
				}
			}
		});
	}

	private void getList(){
		httpUtils.getVo1List(HttpURL.SCHOOL_TEACHERS_LIST, new VoHttpUtils.OnGetVo1ListListener() {
			
			@Override
			public void onGetVo1List(List<Vo1Par> vo1Pars) {
				if (vo1Pars != null) {
					teacherList = vo1Pars;
					gridAdapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	private class MyGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return teacherList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return teacherList.get(position);
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.grid_item_teacher, null);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			Vo1Par teacher = teacherList.get(position);
			holder.tvTitle.setText(teacher.getTitle());
			
			return convertView;
		}
		
		public final class ViewHolder{
			TextView tvTitle;
		}
		
	}
}
