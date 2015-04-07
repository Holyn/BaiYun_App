package com.baiyun.activity.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.schoolservice.SLostFoundActivity;
import com.baiyun.activity.schoolservice.SNoticeActivity;
import com.baiyun.activity.schoolservice.SPhoneActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.Vo1Par;

public class SchoolServiceFragment extends BaseFragment {
	private Button btnNotice;// 通知公告
	private Button btnSchedule;// 课表查询
	private Button btnScore;// 成绩查询
	private Button btnOA;// 办公OA
	private Button btnLostFound;// 失物招领
	private Button btnLostPublish;// 发布失物
	private Button btnTeach;// 教学任务
	private Button btnExam;// 考试安排
	private Button btnUtilities;// 水电查询
	private Button btnRepairs;// 故障报修
	private Button btnPhone;// 办公电话
	private Button btnLibrary;// 图书馆

	private VoHttpUtils voHttpUtils;
	private String goRepairsUrl = null;// 故障报修的跳转链接

	public static SchoolServiceFragment newInstance() {
		return new SchoolServiceFragment();
	}

	public SchoolServiceFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		voHttpUtils = new VoHttpUtils(getActivity());
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_school_service;
	}

	@Override
	public void createMyView(View rootView) {
		initView(rootView);
		getNetData();
	}
	
	private void initView(View rootView){
		// 1.通知公告
		btnNotice = (Button) rootView.findViewById(R.id.btn_notice);
		btnNotice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SNoticeActivity.class);
				getActivity().startActivity(intent);
			}
		});
		// 2.课表查询
		btnSchedule = (Button) rootView.findViewById(R.id.btn_schedule);
		btnSchedule.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		// 3.成绩查询
		btnScore = (Button) rootView.findViewById(R.id.btn_score);
		btnScore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// 4.办公OA
		btnOA = (Button) rootView.findViewById(R.id.btn_oa);
		btnOA.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// 5.失物招领
		btnLostFound = (Button) rootView.findViewById(R.id.btn_lost_found);
		btnLostFound.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SLostFoundActivity.class);
				getActivity().startActivity(intent);
			}
		});

		// 6.发布失物
		btnLostPublish = (Button) rootView.findViewById(R.id.btn_lost_publish);
		btnLostPublish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// 7.教学任务
		btnTeach = (Button) rootView.findViewById(R.id.btn_teach);
		btnTeach.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// 8.考试安排
		btnExam = (Button) rootView.findViewById(R.id.btn_exam);
		btnExam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// 9.水电查询
		btnUtilities = (Button) rootView.findViewById(R.id.btn_utilities);
		btnUtilities.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		// 10.故障报修
		btnRepairs = (Button) rootView.findViewById(R.id.btn_repairs);
		btnRepairs.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (goRepairsUrl != null) {
					if (!(goRepairsUrl.contains("http://"))) {
						goRepairsUrl = "http://" + goRepairsUrl;
					}
					new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("跳转到：" + goRepairsUrl)
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if (URLUtil.isNetworkUrl(goRepairsUrl)) {
										Uri uri = Uri.parse(goRepairsUrl);
										Intent intent = new Intent(Intent.ACTION_VIEW, uri);
										getActivity().startActivity(intent);
									} else {
										Toast.makeText(getActivity(), "网站链接不正确\n" + goRepairsUrl, Toast.LENGTH_SHORT).show();
									}
								}
							}).setNegativeButton("取消", null).create().show();
				}
			}
		});

		// 11.办公电话
		btnPhone = (Button) rootView.findViewById(R.id.btn_phone);
		btnPhone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SPhoneActivity.class);
				getActivity().startActivity(intent);
			}
		});

		// 12.图书馆
		btnLibrary = (Button) rootView.findViewById(R.id.btn_library);
		btnLibrary.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

	private void getNetData() {
		voHttpUtils.getVo1(HttpURL.S_REPAIRS, new VoHttpUtils.OnGetVo1Listener() {

			@Override
			public void onGetVo1(Vo1Par vo1Par) {
				// TODO Auto-generated method stub
				if (vo1Par != null) {
					goRepairsUrl = vo1Par.getUrl();
				}
			}
		});
	}

}
