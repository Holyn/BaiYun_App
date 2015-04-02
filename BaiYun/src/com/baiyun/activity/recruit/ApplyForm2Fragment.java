package com.baiyun.activity.recruit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.StringPopupWindow;
import com.baiyun.httputils.RecruitHttpUtils;
import com.baiyun.httputils.RecruitHttpUtils.OnGetMajorListListener;
import com.baiyun.httputils.RecruitHttpUtils.OnPostForm1Listener;
import com.baiyun.httputils.RecruitHttpUtils.OnPostForm2Listener;
import com.baiyun.util.ui.DatePickerDialogUtil;
import com.baiyun.vo.parcelable.ApplyPar;
import com.baiyun.vo.parcelable.MajorPar;
import com.lidroid.xutils.http.RequestParams;

public class ApplyForm2Fragment  extends BaseFragment{
	private ApplyPar applyPar;
	private RecruitHttpUtils httpUtils;
	
	private TextView tvTitle;//年制招生标题
	private EditText etName;//姓名
	
	private LinearLayout llGender;
	private TextView tvGender;//性别
	private List<String> genderList;//性别列表（男、女）
	private StringPopupWindow genderPop;//性别选择器
	private String gender = "1";//性别 （1为男 0为女）
	
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
	
	private EditText etCertificate;//等级证书
	
	private TextView tvMajor;//报名层次级专业
	private List<MajorPar> majorPars;
	private List<String> majorList;
	private StringPopupWindow majorPop;
	private String baseCourseId;
	
	private EditText etAddress;//通讯地址
	private EditText etPostal;//邮政编码
	private EditText etPhone;//手机号码
	private EditText etMan;//联系人
	private EditText etMsg;//留言
	
	private Button btnSubmit;
	
	
	public static ApplyForm2Fragment newInstance() {
		return new ApplyForm2Fragment();
	}
	
	public ApplyForm2Fragment() {
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
		return R.layout.fragment_form_2;
	}

	@Override
	public void createMyView(View rootView) {
		initData();
		initView(rootView);
		initListener(rootView);
		submit();
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
		
		majorList = new ArrayList<String>();
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
		
		etCertificate = (EditText)rootView.findViewById(R.id.et_certificate);
		
		tvMajor = (TextView)rootView.findViewById(R.id.tv_major);
		
		etAddress = (EditText)rootView.findViewById(R.id.et_address);
		etPostal = (EditText)rootView.findViewById(R.id.et_postal);
		etPhone = (EditText)rootView.findViewById(R.id.et_phone);
		etMan = (EditText)rootView.findViewById(R.id.et_man);
		etMsg = (EditText)rootView.findViewById(R.id.et_msg);
		
		btnSubmit = (Button)rootView.findViewById(R.id.btn_submit);
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
				if (string.equalsIgnoreCase("男")) {
					gender = "1";
				}else if (string.equalsIgnoreCase("女")) {
					gender = "0";
				}
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
		
		tvMajor.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (majorPop != null && majorList.size() > 0) {
					majorPop.showFullScreen(rootView);
				}else {
					getMajorList();
				}
			}
		});
		
	}
	
	private void getMajorList(){
		showLoadingDialog();
		httpUtils.getMajorList(applyPar.getId(), new OnGetMajorListListener() {
			
			@Override
			public void OnGetMajorList(List<MajorPar> vos) {
				closeLoadingDialog();
				// TODO Auto-generated method stub
				if (vos != null) {
					ApplyForm2Fragment.this.majorPars = vos;
					Iterator<MajorPar> iterator = majorPars.iterator();
					while (iterator.hasNext()) {
						MajorPar majorPar = (MajorPar) iterator.next();
						majorList.add(majorPar.getName());
					}
					
					majorPop = new StringPopupWindow(getActivity(), majorList, "报名层次级专业");
					majorPop.setOnItemClickListener(new StringPopupWindow.OnItemClickListener() {
						
						@Override
						public void OnItemClick(String string, int position) {
							tvMajor.setText(string);
							baseCourseId = majorPars.get(position).getId();
						}
					});
					majorPop.showFullScreen(rootView);
					
				}
			}
		});
	}
	
	private void submit(){
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showLoadingDialog();
				
				String name = etName.getText().toString().trim();
				String birthday = tvBorn.getText().toString().trim();
				String culturalBackground = tvDegree.getText().toString().trim();
				String politicalBackground = tvPolitical.getText().toString().trim();
				String certification = etCertificate.getText().toString().trim();
				String address = etAddress.getText().toString().trim();
				String postcode = etPostal.getText().toString().trim();
				String phonecall = etPhone.getText().toString().trim();
				String linkman = etMan.getText().toString().trim();
				String note = etMsg.getText().toString().trim();
				
				final RequestParams params = new RequestParams();
				
				params.addBodyParameter("id", "32");
				params.addBodyParameter("name", name);
				params.addBodyParameter("sex", gender);
				params.addBodyParameter("birthday", birthday);
				params.addBodyParameter("culturalBackground", culturalBackground);
				params.addBodyParameter("politicalBackground", politicalBackground);
				params.addBodyParameter("baseCourseId", baseCourseId);
				params.addBodyParameter("certificationDateAndPlace", certification);
				params.addBodyParameter("address", address);
				params.addBodyParameter("postcode", postcode);
				params.addBodyParameter("telephone", phonecall);
				params.addBodyParameter("linkman", linkman);
				params.addBodyParameter("note", note);
				
				httpUtils.postForm2(params, new OnPostForm2Listener() {
					
					@Override
					public void OnPostForm2(boolean isSuccess) {
						if (isSuccess) {
							Toast.makeText(getActivity(), "报名成功", Toast.LENGTH_SHORT).show();
						}else {
							Toast.makeText(getActivity(), "报名失败", Toast.LENGTH_SHORT).show();
						}
						closeLoadingDialog();
					}
				});
			}
		});
	}
	
}
