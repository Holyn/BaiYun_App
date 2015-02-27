package com.baiyun.activity.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.home.DepartmentActivity;
import com.baiyun.activity.home.JobActivity;
import com.baiyun.activity.home.NewsActivity;
import com.baiyun.activity.home.OverviewActivity;
import com.baiyun.activity.home.TrafficActivity;
import com.baiyun.activity.home.VideoActivity;
import com.baiyun.activity.webview.WebViewActiviry;
import com.baiyun.base.BaseFragment;
import com.baiyun.base.BaseAdAdapter;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeHttpUtils;
import com.baiyun.vo.parcelable.HomeAdPar;
import com.baiyun.vo.parcelable.HomeNewsPar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeFragment extends BaseFragment {
	private HomeHttpUtils httpUtils;

	/* headerView 开始 */
	private View headerView = null;
	private ViewPager viewPager = null;
	private BaseAdAdapter pagerAdapter;
	private ArrayList<View> views = new ArrayList<View>();
	private List<ImageView> imageViews = new ArrayList<ImageView>(4);
	private RadioButton rb_0, rb_1, rb_2, rb_3;

	private ScheduledExecutorService scheduledExecutorService;// 控制viewpager自动滑动
	private int currentItem = 0; // 当前图片的索引号

	/* headerView 结束 */

	private ListView lvNews;
	private HomeNewsAdapter lvAdapter;
	private List<HomeNewsPar> homeNewsPars = new ArrayList<HomeNewsPar>();

	private String onlineUrl = null;// 白云在线的跳转链接

	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	public HomeFragment() {

	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_home;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new HomeHttpUtils(getActivity());
	}

	@Override
	public void createMyView(View rootView) {
		initHeaderView();
		initListView(rootView);
		getData();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 启动循环线程
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3, TimeUnit.SECONDS);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		scheduledExecutorService.shutdown();// 停止循环线程
	}

	private void initHeaderView() {
		headerView = (View) LayoutInflater.from(getActivity()).inflate(R.layout.header_view_home, null);
		viewPager = (ViewPager) headerView.findViewById(R.id.vp_ad);

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

		initHeaderViewBtnListener();
	}

	private void initHeaderViewBtnListener() {
		Button btnOverview = (Button) headerView.findViewById(R.id.btn_overview);// 1学校概况
		btnOverview.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), OverviewActivity.class));

			}
		});
		Button btnNews = (Button) headerView.findViewById(R.id.btn_reports);// 2技师微报(新闻)
		btnNews.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), NewsActivity.class));

			}
		});
		Button btnVideo = (Button) headerView.findViewById(R.id.btn_video);// 3视频白云
		btnVideo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), VideoActivity.class));
			}
		});
		Button btnSetting = (Button) headerView.findViewById(R.id.btn_sys_setting);// 4系部设置
		btnSetting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), DepartmentActivity.class));
			}
		});

		Button btnConsult = (Button) headerView.findViewById(R.id.btn_consult);// 5招生咨询
		btnConsult.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		Button btnOnline = (Button) headerView.findViewById(R.id.btn_online);// 6白云在线
		btnOnline.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (onlineUrl != null) {
					if (!(onlineUrl.contains("http://"))) {
						onlineUrl = "http://" + onlineUrl;
					}
					new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("跳转到：" + onlineUrl)
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if (URLUtil.isNetworkUrl(onlineUrl)) {
										Uri uri = Uri.parse(onlineUrl);
										Intent intent = new Intent(Intent.ACTION_VIEW, uri);
										getActivity().startActivity(intent);
									} else {
										Toast.makeText(getActivity(), "网站链接不正确\n" + onlineUrl, Toast.LENGTH_SHORT).show();
									}
								}
							}).setNegativeButton("取消", null).create().show();
				} else {
					Toast.makeText(getActivity(), "链接为空" + onlineUrl, Toast.LENGTH_SHORT).show();
				}
			}
		});

		Button btnJob = (Button) headerView.findViewById(R.id.btn_job);// 7就业服务
		btnJob.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), JobActivity.class));
			}
		});

		Button btnTraffic = (Button) headerView.findViewById(R.id.btn_traffic);// 8交通查询
		btnTraffic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), TrafficActivity.class));
			}
		});

		RelativeLayout rlMoreNews = (RelativeLayout) headerView.findViewById(R.id.rl_more_news);
		rlMoreNews.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), NewsActivity.class));
			}
		});
	}

	private void initListView(View rootView) {
		lvNews = (ListView) rootView.findViewById(R.id.lv);
		lvAdapter = new HomeNewsAdapter(getActivity(), homeNewsPars);
		lvNews.addHeaderView(headerView);
		lvNews.setAdapter(lvAdapter);
		lvNews.setOnItemClickListener(new NewsListOnItemClickListener());
	}

	private void getData() {

		httpUtils.getHomeAdPar(new HomeHttpUtils.OnGetHomeAdParsListener() {

			@Override
			public void onGetHomeAdPars(List<HomeAdPar> homeAdPars) {
				if (homeAdPars != null) {
					int size = homeAdPars.size();
					int forCount = 0;
					if (size > 4) {
						forCount = 4;
					} else {
						forCount = size;
					}
					for (int i = 0; i < forCount; i++) {
						String urlLast = homeAdPars.get(i).getUrl();
						if (urlLast != null && !(urlLast.equalsIgnoreCase(""))) {
							String url = HttpURL.HOST + urlLast.substring(1);
							ImageLoader.getInstance().displayImage(url, imageViews.get(i));
						}
					}
				}
				pagerAdapter.notifyDataSetChanged();
			}
		});

		httpUtils.getHomeNewsPar(new HomeHttpUtils.OnGetHomeNewsListener() {

			@Override
			public void onGerHomeNews(List<HomeNewsPar> newsList) {
				if (newsList != null) {
					homeNewsPars.addAll(newsList);
					lvAdapter.notifyDataSetChanged();
				}
			}
		});

		httpUtils.getOnlineUrl(new HomeHttpUtils.OnGetOnlineUrlListener() {

			@Override
			public void onGetOnlineUrl(String url) {
				if (url != null) {
					onlineUrl = url;
				}

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
			HomeNewsPar news = homeNewsPars.get(realPosition);

			Intent intent = new Intent(getActivity(), WebViewActiviry.class);
			intent.putExtra(WebViewActiviry.KEY_WEB_VIEW_TYPE, WebViewActiviry.NEWS_DETAIL);
			intent.putExtra(WebViewActiviry.KEY_CONTENT_URL, news.getContentUrl());
			getActivity().startActivity(intent);
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

	private class HomeNewsAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<HomeNewsPar> newsList;

		public HomeNewsAdapter(Context context, List<HomeNewsPar> newsList) {
			inflater = LayoutInflater.from(context);
			this.newsList = newsList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return newsList.get(position);
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
				convertView = inflater.inflate(R.layout.list_item_home, parent, false);
				holder.ivHeader = (ImageView) convertView.findViewById(R.id.iv_header);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			HomeNewsPar news = newsList.get(position);
			if (news.getPicUrl() != null && !(news.getPicUrl().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST + news.getPicUrl().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivHeader);
			}
			holder.tvTitle.setText(news.getTitle());
			holder.tvContent.setText(news.getBrief());

			return convertView;
		}

		public final class ViewHolder {
			public ImageView ivHeader;
			public TextView tvTitle;
			public TextView tvContent;
		}

	}
}
