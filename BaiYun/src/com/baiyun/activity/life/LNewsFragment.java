package com.baiyun.activity.life;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.baiyun.activity.R;
import com.baiyun.base.BaseAdAdapter;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeNewsHttpUtils;
import com.baiyun.httputils.SchoolLifeHttpUtils;
import com.baiyun.vo.parcelable.HomeNewsPar;
import com.baiyun.vo.parcelable.LNewsPar;
import com.baiyun.vo.parcelable.VoPicPar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LNewsFragment extends BaseFragment{
	private SchoolLifeHttpUtils httpUtils;
	private String newsId;
	
	/* headerView 开始 */
	private View headerView = null;
	private ViewPager viewPager = null;
	private BaseAdAdapter pagerAdapter;
	private List<VoPicPar> voPicPars = new ArrayList<VoPicPar>();
	private ArrayList<View> views = new ArrayList<View>();
	private List<ImageView> imageViews = new ArrayList<ImageView>(4);//广告图片显示组件
	private TextView tvTitle;//广告标题
	private RadioButton rb_0, rb_1, rb_2, rb_3;

	private ScheduledExecutorService scheduledExecutorService;// 控制viewpager自动滑动
	private int currentItem = 0; // 当前图片的索引号
	/* headerView 结束 */
	
	private PullToRefreshListView refreshListView;
	private ListView listView;
	private ListViewAdapter adapter;
	private List<LNewsPar> newsList = new ArrayList<LNewsPar>();
	
	private int page = 1;
	
	public static LNewsFragment newInstance() {
		return new LNewsFragment();
	}

	public LNewsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.pull_to_refresh_listview;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		page = 1;
		httpUtils = new SchoolLifeHttpUtils(getActivity());
		newsId = getArguments().getString(LNewsActivity.NEWS_ID_VALUE);
	}

	@Override
	public void createMyView(View rootView) {
		initHeaderView();
		initListView(rootView);
		
		((LNewsActivity)getActivity()).setLoadingBarVisible();
		getNewsAd();
		getNewsList(page);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (newsId.equalsIgnoreCase(LNewsActivity.NEWS_ID_24)) {
			((LNewsActivity)getActivity()).setTopBarTitle("学工动态");
		}else if (newsId.equalsIgnoreCase(LNewsActivity.NEWS_ID_26)) {
			((LNewsActivity)getActivity()).setTopBarTitle("体育艺术");
		}else if (newsId.equalsIgnoreCase(LNewsActivity.NEWS_ID_27)) {
			((LNewsActivity)getActivity()).setTopBarTitle("社团嘉年华");
		}
		
		// 启动循环线程
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3, TimeUnit.SECONDS);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((LNewsActivity)getActivity()).setLoadingBarGone();
		
		scheduledExecutorService.shutdown();// 停止循环线程
	}
	
	private void initHeaderView() {
		headerView = (View) LayoutInflater.from(getActivity()).inflate(R.layout.header_view_life_news, null);
		viewPager = (ViewPager) headerView.findViewById(R.id.vp_ad);
		tvTitle = (TextView)headerView.findViewById(R.id.tv_title);

		for (int i = 0; i < 4; i++) {
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.imageview_ad, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.iv_ad);
			imageViews.add(imageView);
			views.add(view);
		}

		pagerAdapter = new BaseAdAdapter(views);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		rb_0 = (RadioButton) headerView.findViewById(R.id.rb_ad_0);
		rb_1 = (RadioButton) headerView.findViewById(R.id.rb_ad_1);
		rb_2 = (RadioButton) headerView.findViewById(R.id.rb_ad_2);
		rb_3 = (RadioButton) headerView.findViewById(R.id.rb_ad_3);

	}
	
	private void initListView(View rootView) {
		refreshListView = (PullToRefreshListView)rootView.findViewById(R.id.refresh_listview);
		refreshListView.setMode(Mode.PULL_FROM_END);
		
		listView = refreshListView.getRefreshableView();
		adapter = new ListViewAdapter(getActivity(), newsList);
		listView.addHeaderView(headerView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new NewsListOnItemClickListener());
		refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				System.out.println("====> onRefresh....");
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
				page++;
				getNewsList(page);
			}
		});
	}
	
	//顶部图片
	private void getNewsAd(){
		httpUtils.getNewsAd(newsId, new SchoolLifeHttpUtils.onGetNewsAdListener() {
			
			@Override
			public void onGetNewsAd(List<VoPicPar> picPars) {
				if (picPars != null) {//广告图片
					voPicPars = picPars;
					
					//设置广告标题
					if (currentItem < voPicPars.size()) {
						String title = voPicPars.get(currentItem).getName().toString();
						if (!TextUtils.isEmpty(title)) {
							tvTitle.setText(title);
						}
					}else {
						tvTitle.setText("");
					}
					
					//设置显示广告图片
					int size = picPars.size();
					int forCount = 0;
					if (size > 4) {
						forCount = 4;
					} else {
						forCount = size;
					}
					for (int i = 0; i < forCount; i++) {
						VoPicPar picPar = picPars.get(i);
						String urlLast = picPar.getUrl();
						if (urlLast != null && !(urlLast.equalsIgnoreCase(""))) {
							String url = HttpURL.HOST + urlLast.substring(1);
							ImageLoader.getInstance().displayImage(url, imageViews.get(i));
						}
					}
				}
			}
		});
	}
	
	//新闻列表
	private void getNewsList(final int page){
		System.out.println("====> getNewsList....");
		httpUtils.getNewsList(newsId, page, new SchoolLifeHttpUtils.onGetNewsListListener() {
			
			@Override
			public void onGetNewsList(List<LNewsPar> lNewsPars) {
				if (page == 1) {
					if (getActivity() != null) {
						((LNewsActivity)getActivity()).setLoadingBarGone();
					}
				}
				if (lNewsPars != null) {//新闻
					newsList.addAll(lNewsPars);
					adapter.notifyDataSetChanged();
				}
				refreshListView.onRefreshComplete();
			}
		});
	}
	
	/**
	 * 换行切换任务
	 */
	private class ScrollTask implements Runnable {
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % views.size();
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
						
						if (currentItem < voPicPars.size()) {
							String title = voPicPars.get(currentItem).getName().toString();
							if (!TextUtils.isEmpty(title)) {
								tvTitle.setText(title);
							}
						}else {
							tvTitle.setText("");
						}
					}
				});
			}
		}
	}

	/**
	 * 新闻list的点击事件
	 */
	private class NewsListOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (id == -1) {
				// 点击的是headerView或者footerView
				return;
			}
			int realPosition = (int) id;
			System.out.println("===> realPosition = "+realPosition);
			LNewsPar news = newsList.get(realPosition);
			((LNewsActivity)getActivity()).showWebViewFragment2(news.getContentUrl(), "新闻详情");
		}

	}
	
	private class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int arg0) {
			currentItem = arg0;
			switch (arg0) {
			case 0:
				rb_0.setChecked(true);
				rb_1.setChecked(false);
				rb_2.setChecked(false);
				rb_3.setChecked(false);
				break;
			case 1:
				rb_0.setChecked(false);
				rb_1.setChecked(true);
				rb_2.setChecked(false);
				rb_3.setChecked(false);
				break;
			case 2:
				rb_0.setChecked(false);
				rb_1.setChecked(false);
				rb_2.setChecked(true);
				rb_3.setChecked(false);
				break;
			case 3:
				rb_0.setChecked(false);
				rb_1.setChecked(false);
				rb_2.setChecked(false);
				rb_3.setChecked(true);
				break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

	}
	
	private class ListViewAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<LNewsPar> list;

		public ListViewAdapter(Context context, List<LNewsPar> list) {
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

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.list_item_life_news, parent, false);
				holder.ivHeader = (ImageView) convertView.findViewById(R.id.iv_header);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
				holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			LNewsPar news = list.get(position);
			if (news.getPicUrl() != null && !(news.getPicUrl().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST + news.getPicUrl().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivHeader);
			}
			holder.tvTitle.setText(news.getTitle());
			holder.tvContent.setText(news.getBrief());
			holder.tvTime.setText(news.getContentCreateTime());

			return convertView;
		}
		
		public final class ViewHolder {
			public ImageView ivHeader;
			public TextView tvTitle;
			public TextView tvContent;
			public TextView tvTime;
		}
		
	}

}
