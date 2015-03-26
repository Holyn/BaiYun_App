package com.baiyun.activity.recruit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.StringPopupWindow;
import com.baiyun.httputils.RecruitHttpUtils;
import com.baiyun.util.ui.DatePickerDialogUtil;
import com.baiyun.vo.parcelable.ApplyPar;
import com.lidroid.xutils.http.RequestParams;

public class ApplyForm1Fragment extends BaseFragment{
	private ApplyPar applyPar;
	private RecruitHttpUtils httpUtils;
	
	private TextView tvTitle;//年制招生标题
	private EditText etName;//姓名
	
	private LinearLayout llGender;
	private TextView tvGender;//性别
	private List<String> genderList;//性别列表（男、女）
	private StringPopupWindow genderPop;//性别选择器
	
	private LinearLayout llDegree;
	private TextView tvDegree;//文化程度
	private List<String> degreeList;
	private StringPopupWindow degreePop;
	
	private LinearLayout llPolitical;
	private TextView tvPolitical;//政治面貌
	private List<String> politicalList;
	private StringPopupWindow politicalPop;
	
	private LinearLayout llBorn;
	private TextView tvBorn;//出生年月
	private DatePickerDialogUtil datePickerDialogUtil;
	
	private Button btnSubmit;
	
	public static ApplyForm1Fragment newInstance() {
		return new ApplyForm1Fragment();
	}
	
	public ApplyForm1Fragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		applyPar = getArguments().getParcelable(ApplyFormActivity.VALUE_APPLYPAR);
		httpUtils = new RecruitHttpUtils(getActivity());
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_form_1;
		
	}

	@Override
	public void createMyView(View rootView) {
		initData();
		initView(rootView);
		initListener(rootView);
	}
	
	private void initData(){
		genderList = new ArrayList<String>();
		genderList.add("男");
		genderList.add("女");
		genderPop = new StringPopupWindow(getActivity(), genderList, "性别");
		
		degreeList = new ArrayList<String>();
		degreeList.add("本科");
		degreeList.add("大专");
		degreeList.add("高中");
		degreeList.add("初中");
		degreeList.add("小学");
		degreePop = new StringPopupWindow(getActivity(), degreeList, "文化程度");
		
		politicalList = new ArrayList<String>();
		politicalList.add("党员");
		politicalList.add("团员");
		politicalList.add("群众");
		politicalPop = new StringPopupWindow(getActivity(), politicalList, "政治面貌");
		
	}
	
	private void initView(View rootView){
		tvTitle = (TextView)rootView.findViewById(R.id.tv_title);
		tvTitle.setText(applyPar.getName());
		
		etName = (EditText)rootView.findViewById(R.id.et_name);
		
		llGender = (LinearLayout)rootView.findViewById(R.id.ll_gender);
		tvGender = (TextView)rootView.findViewById(R.id.tv_gender);
		
		llDegree = (LinearLayout)rootView.findViewById(R.id.ll_degree);
		tvDegree = (TextView)rootView.findViewById(R.id.tv_degree);
		
		llPolitical = (LinearLayout)rootView.findViewById(R.id.ll_political);
		tvPolitical = (TextView)rootView.findViewById(R.id.tv_political);
		
		llBorn = (LinearLayout)rootView.findViewById(R.id.ll_born);
		tvBorn = (TextView)rootView.findViewById(R.id.tv_born);
		datePickerDialogUtil = new DatePickerDialogUtil(getActivity(), tvBorn);
	}
	
	private void initListener(final View rootView){
		llGender.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				genderPop.showFullScreen(rootView);
			}
		});
		genderPop.setOnItemClickListener(new StringPopupWindow.OnItemClickListener() {
			
			@Override
			public void OnItemClick(String string, int position) {
				// TODO Auto-generated method stub
				tvGender.setText(string);
			}
		});
		llDegree.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				degreePop.showFullScreen(rootView);
			}
		});
		degreePop.setOnItemClickListener(new StringPopupWindow.OnItemClickListener() {
			
			@Override
			public void OnItemClick(String string, int position) {
				// TODO Auto-generated method stub
				tvDegree.setText(string);
			}
		});
		llPolitical.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				politicalPop.showFullScreen(rootView);
			}
		});
		politicalPop.setOnItemClickListener(new StringPopupWindow.OnItemClickListener() {
			
			@Override
			public void OnItemClick(String string, int position) {
				// TODO Auto-generated method stub
				tvPolitical.setText(string);
			}
		});
		llBorn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datePickerDialogUtil.show();
			}
		});
	}
	
	private void submit(){
		final RequestParams params = new RequestParams();
		
		params.addBodyParameter("id", "32");
		params.addBodyParameter("name", "陈浩林");
		params.addBodyParameter("address", "佛山石湾");
		params.addBodyParameter("gender", "1");
		params.addBodyParameter("culturalBackground", "本科");
		params.addBodyParameter("politicalBackground", "党员");
		params.addBodyParameter("iDNumber", "888888888888888");
		params.addBodyParameter("graduateBackground", "毕业");
		params.addBodyParameter("graduateSchool", "佛大");
		params.addBodyParameter("baseCourseId", "10");
		params.addBodyParameter("residence", "广东肇庆");
		params.addBodyParameter("specialStudent", "0");
		params.addBodyParameter("graduateScore", "100");
		params.addBodyParameter("postcode", "528000");
		params.addBodyParameter("phonecall", "111111111");
		params.addBodyParameter("telephone", "10086");
		params.addBodyParameter("linkman", "陈浩林");
		params.addBodyParameter("note", "留言");
		params.addBodyParameter("birthday", "1988/08/08");


		btnSubmit = (Button)rootView.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("=== onClick。开始提交报名表 ....");
				httpUtils.postForm1(params);
			}
		});
	}

}
