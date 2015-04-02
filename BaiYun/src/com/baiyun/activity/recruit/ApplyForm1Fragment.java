package com.baiyun.activity.recruit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.StringPopupWindow;
import com.baiyun.httputils.RecruitHttpUtils;
import com.baiyun.httputils.RecruitHttpUtils.OnGetMajorListListener;
import com.baiyun.httputils.RecruitHttpUtils.OnPostForm1Listener;
import com.baiyun.util.ui.DatePickerDialogUtil;
import com.baiyun.vo.parcelable.ApplyPar;
import com.baiyun.vo.parcelable.MajorPar;
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
	
	private EditText etIdCard;//身份证
	private EditText etSchool;//毕业学校
	
	private RadioButton rbGb_0, rbGb_1, rbGb_2, rbGb_3;//graduateBackground（农村应届，城镇应届，农村往届，城镇往届）
	private String graduateBackground = "农村应届";
	
	private TextView tvMajor;//报名层次级专业
	private List<MajorPar> majorPars;
	private List<String> majorList;
	private StringPopupWindow majorPop;
	private String baseCourseId;
	
	private RadioGroup rgResidence;//户口所在地
	private String residenceType;//户籍类型 （1为农业户，0为居民户）
	private EditText etCity,etDistrict,etTown;
	
	private RadioGroup rgSpecial;//是否“粤北、东西两翼”考生
	private String specialStudent = "1";//是否“粤北、东西两翼”考生(1为是，0为否)
	
	private EditText etScore;//毕业成绩
	private EditText etAddress;//通讯地址
	private EditText etPostal;//邮政编码
	private EditText etTel;//固定电话
	private EditText etPhone;//手机号码
	private EditText etMan;//联系人
	private EditText etMsg;//留言
	
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
		
		etIdCard = (EditText)rootView.findViewById(R.id.et_id_card);
		etSchool = (EditText)rootView.findViewById(R.id.et_school);
		
		rbGb_0 = (RadioButton)rootView.findViewById(R.id.rb_gb_0);
		rbGb_1 = (RadioButton)rootView.findViewById(R.id.rb_gb_1);
		rbGb_2 = (RadioButton)rootView.findViewById(R.id.rb_gb_2);
		rbGb_3 = (RadioButton)rootView.findViewById(R.id.rb_gb_3);
		
		tvMajor = (TextView)rootView.findViewById(R.id.tv_major);
		
		rgResidence = (RadioGroup)rootView.findViewById(R.id.rg_residence);
		etCity = (EditText)rootView.findViewById(R.id.et_city);
		etDistrict = (EditText)rootView.findViewById(R.id.et_district);
		etTown = (EditText)rootView.findViewById(R.id.et_town);
		
		rgSpecial = (RadioGroup)rootView.findViewById(R.id.rg_special);
		
		etScore = (EditText)rootView.findViewById(R.id.et_score);
		etAddress = (EditText)rootView.findViewById(R.id.et_address);
		etPostal = (EditText)rootView.findViewById(R.id.et_postal);
		etTel = (EditText)rootView.findViewById(R.id.et_tel);
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
		rbGb_0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					graduateBackground = "农村应届";
//					rbGb_0.setChecked(true);
					rbGb_1.setChecked(false);
					rbGb_2.setChecked(false);
					rbGb_3.setChecked(false);
				}
			}
		});
		rbGb_1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					graduateBackground = "城镇应届";
					rbGb_0.setChecked(false);
//					rbGb_1.setChecked(true);
					rbGb_2.setChecked(false);
					rbGb_3.setChecked(false);
				}
			}
		});
		rbGb_2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					graduateBackground = "农村往届";
					rbGb_0.setChecked(false);
					rbGb_1.setChecked(false);
//					rbGb_2.setChecked(true);
					rbGb_3.setChecked(false);
				}
			}
		});
		rbGb_3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					graduateBackground = "城镇往届";
					rbGb_0.setChecked(false);
					rbGb_1.setChecked(false);
					rbGb_2.setChecked(false);
//					rbGb_3.setChecked(true);
				}
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

		rgResidence.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.rb_residence_0) {//居民户
					residenceType = "0";
				}else if (checkedId == R.id.rb_residence_1) {//农业户
					residenceType = "1";
				}
			}
		});
		rgSpecial.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.rb_special_yes) {//是
					specialStudent = "1";
				}else if (checkedId == R.id.rb_special_no) {//否
					specialStudent = "0";
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
					ApplyForm1Fragment.this.majorPars = vos;
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
				String address = etAddress.getText().toString().trim();
				String culturalBackground = tvDegree.getText().toString().trim();
				String politicalBackground = tvPolitical.getText().toString().trim();
				String iDNumber = etIdCard.getText().toString().trim();
				String graduateSchool = etSchool.getText().toString().trim();
				String residence = etCity.getText().toString().trim() + etDistrict.getText().toString().trim() + etTown.getText().toString().trim();
				String graduateScore = etScore.getText().toString().trim();
				String postcode = etPostal.getText().toString().trim();
				String phonecall = etPhone.getText().toString().trim();
				String telephone = etTel.getText().toString().trim();
				String linkman = etMan.getText().toString().trim();
				String note = etMsg.getText().toString().trim();
				String birthday = tvBorn.getText().toString().trim();
				
				final RequestParams params = new RequestParams();
				
				params.addBodyParameter("id", "32");
				params.addBodyParameter("name", name);
				params.addBodyParameter("address", address);
				params.addBodyParameter("gender", gender);
				params.addBodyParameter("culturalBackground", culturalBackground);
				params.addBodyParameter("politicalBackground", politicalBackground);
				params.addBodyParameter("iDNumber", iDNumber);
				params.addBodyParameter("graduateBackground", graduateBackground);
				params.addBodyParameter("graduateSchool", graduateSchool);
				params.addBodyParameter("baseCourseId", baseCourseId);
				params.addBodyParameter("residenceType", residenceType);
				params.addBodyParameter("residence", residence);
				params.addBodyParameter("specialStudent", specialStudent);
				params.addBodyParameter("graduateScore", graduateScore);
				params.addBodyParameter("postcode", postcode);
				params.addBodyParameter("phonecall", phonecall);
				params.addBodyParameter("telephone", telephone);
				params.addBodyParameter("linkman", linkman);
				params.addBodyParameter("note", note);
				params.addBodyParameter("birthday", birthday);
				
				httpUtils.postForm1(params, new OnPostForm1Listener() {
					
					@Override
					public void OnPostForm1(boolean isSuccess) {
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
