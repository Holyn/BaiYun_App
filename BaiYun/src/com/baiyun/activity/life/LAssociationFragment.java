package com.baiyun.activity.life;

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
import com.baiyun.httputils.SchoolLifeHttpUtils;
import com.baiyun.vo.parcelable.LifeAssociationPar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LAssociationFragment extends BaseFragment{
	private SchoolLifeHttpUtils httpUtils;
	
	private GridView gridView;
	private List<LifeAssociationPar> associationPars = new ArrayList<LifeAssociationPar>();
	private MyGridAdapter gridAdapter;
	
	public static LAssociationFragment newInstance() {
		return new LAssociationFragment();
	}
	
	public LAssociationFragment() {
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
		return R.layout.fragment_association;
	}

	@Override
	public void createMyView(View rootView) {
		// TODO Auto-generated method stub
		initView(rootView);
		getNetData();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((LAssociationActivity)getActivity()).setTopBarTitle("学生社团");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((LAssociationActivity)getActivity()).setLoadingBarGone();
	}
	
	private void initView(View rootView){
		gridView = (GridView)rootView.findViewById(R.id.gridview);
		gridAdapter = new MyGridAdapter();
		gridView.setAdapter(gridAdapter);
		
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				LifeAssociationPar associationPar = associationPars.get(position);
				((LAssociationActivity)getActivity()).showLAssociationDetailFragment(associationPar);
			}
			
		});
	}
	
	private void getNetData(){
		((LAssociationActivity)getActivity()).setLoadingBarVisible();
		httpUtils.getAssociation(new SchoolLifeHttpUtils.onGetAssociationListener() {
			
			@Override
			public void onGetAssociation(List<LifeAssociationPar> associationPars) {
				if (getActivity() != null) {
					((LAssociationActivity)getActivity()).setLoadingBarGone();
				}
				if (associationPars != null) {
					LAssociationFragment.this.associationPars = associationPars;
					gridAdapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	private class MyGridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return associationPars.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return associationPars.get(position);
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.grid_item_association, null);
				holder.ivIcon = (ImageView)convertView.findViewById(R.id.iv_icon);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				holder.ivLineRight = (ImageView)convertView.findViewById(R.id.iv_line_right);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			if ((position+1)%3 == 0) {
				holder.ivLineRight.setVisibility(View.INVISIBLE);
			}else {
				holder.ivLineRight.setVisibility(View.VISIBLE);
			}
			
			LifeAssociationPar associationPar = associationPars.get(position);
			if (associationPar.getImg() != null && !(associationPar.getImg().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST+associationPar.getImg().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivIcon);
			}
			holder.tvTitle.setText(associationPar.getName());
			
			return convertView;
		}
		
		public final class ViewHolder{
			ImageView ivIcon;
			TextView tvTitle;
			ImageView ivLineRight;
		}
		
	}

}
