package com.baiyun.activity.schoolservice;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.CircleImageView;
import com.baiyun.custom.CommonAdapter;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.SchoolServiceHttpUtils;
import com.baiyun.picturesviewer.PicturesViewPagerActivity;
import com.baiyun.vo.parcelable.LostPar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SLostFoundFragment extends BaseFragment{
	private SchoolServiceHttpUtils httpUtils;
	
	private ListView listView;
	private MyListAdapter listAdapter;
	private List<LostPar> lostPars = new ArrayList<LostPar>();
	
	public static SLostFoundFragment newInstance() {
		return new SLostFoundFragment();
	}
	
	public SLostFoundFragment() {
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
		return R.layout.listview_common;
	}

	@Override
	public void createMyView(View rootView) {
		// TODO Auto-generated method stub
		initView(rootView);
		getNetData();
	}
	
	private void initView(View rootView){
		listView = (ListView) rootView.findViewById(R.id.listview);
		listAdapter = new MyListAdapter(getActivity(), lostPars);
		listView.setAdapter(listAdapter);
	}
	
	private void getNetData(){
		httpUtils.getLostPar(null, new SchoolServiceHttpUtils.OnGetLostParListener() {
			
			@Override
			public void onGetLostPar(List<LostPar> pars) {
				// TODO Auto-generated method stub
				if (pars != null) {
					lostPars.addAll(pars);
					listAdapter.notifyDataSetChanged();
				}
			}
		});
	}
	

	
	private class MyListAdapter extends CommonAdapter<LostPar>{
		
		public MyListAdapter(Context context, List<LostPar> mDatas) {
			this(context, mDatas, R.layout.list_item_lost_found);
		}

		public MyListAdapter(Context context, List<LostPar> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(com.baiyun.custom.ViewHolder helper, LostPar item) {
			CircleImageView cvHeader = helper.getView(R.id.civ_header);
			String headerUrlLast = item.getUserImg();
			if (headerUrlLast != null) {
				ImageLoader.getInstance().displayImage(HttpURL.HOST+headerUrlLast.substring(1), cvHeader);
			}
			helper.setText(R.id.tv_name, item.getUserAccount());
			helper.setText(R.id.tv_time, item.getContentCreateTime());
			helper.setText(R.id.tv_comment, "评论数:"+item.getComment_count());
			helper.setText(R.id.tv_content, item.getBrief());
			
			GridView gridView = helper.getView(R.id.gv_picture);
			final ArrayList<String> imagePathList = getImagePathList(item);
			MyGridAdapter gridAdapter = new MyGridAdapter(getActivity(), imagePathList);
			gridView.setAdapter(gridAdapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(getActivity(), PicturesViewPagerActivity.class);
					intent.putStringArrayListExtra(PicturesViewPagerActivity.EXTRA_IMAGE_LIST, imagePathList);
					intent.putExtra(PicturesViewPagerActivity.EXTRA_CUR_POSITION, position);
					getActivity().startActivity(intent);
				}
			});
		}
		
	}
	
	private class MyGridAdapter extends CommonAdapter<String>{
		
		public MyGridAdapter(Context context, List<String> mDatas) {
			this(context, mDatas, R.layout.grid_item_imageview);
		}

		public MyGridAdapter(Context context, List<String> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(com.baiyun.custom.ViewHolder helper, String item) {
			helper.setImageByUrl(R.id.iv_picture, item);
		}
		
	}
	
	private ArrayList<String> getImagePathList(LostPar lostPar){
		ArrayList<String> pathList = new ArrayList<String>();
		String picUrl = lostPar.getPicUrl();
		if (!TextUtils.isEmpty(picUrl)) {
			String[] pathArray = picUrl.split(",");
			for (int i = 0; i < pathArray.length; i++) {
				pathList.add(HttpURL.HOST+pathArray[i].substring(1));
			}
		}
		return pathList;
	}
}
