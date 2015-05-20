package com.baiyun.fragment.sliding;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.SlideMenuHttpUtils;
import com.baiyun.sharepreferences.UserInfoSP;
import com.baiyun.vo.parcelable.UserInfoPar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class LoginFragment extends BaseFragment {
	private EditText etName, etPassword, etVeriCode;
	private ImageView ivName, ivPassword, ivVeriCode;
	private ImageButton ibVerCode;
	private Button btnSubmit;

	public static LoginFragment newInstance() {
		return new LoginFragment();
	}

	public LoginFragment() {

	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_login;
	}

	@Override
	public void createMyView(View rootView) {
		etName = (EditText) rootView.findViewById(R.id.et_name);
		ivName = (ImageView) rootView.findViewById(R.id.iv_name);
		etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					ivName.setImageResource(R.drawable.ic_login_pre);
				} else {
					ivName.setImageResource(R.drawable.ic_login_nor);
				}

			}
		});

		etPassword = (EditText) rootView.findViewById(R.id.et_password);
		ivPassword = (ImageView) rootView.findViewById(R.id.iv_password);
		etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					ivPassword.setImageResource(R.drawable.ic_password_pre);
				} else {
					ivPassword.setImageResource(R.drawable.ic_password_nor);
				}

			}
		});

		etVeriCode = (EditText) rootView.findViewById(R.id.et_veri_code);
		ivVeriCode = (ImageView) rootView.findViewById(R.id.iv_veri_code);
		etVeriCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					ivVeriCode.setImageResource(R.drawable.ic_veri_code_pre);
				} else {
					ivVeriCode.setImageResource(R.drawable.ic_veri_code_nor);
				}

			}
		});
		ibVerCode = (ImageButton) rootView.findViewById(R.id.ib_veri_code);
		displayImage(ibVerCode, HttpURL.S_GET_RANDOM_STRING_IMAGE);
		ibVerCode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				displayImage(ibVerCode, HttpURL.S_GET_RANDOM_STRING_IMAGE);
			}
		});

		btnSubmit = (Button) rootView.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submitLogin();
			}
		});
	}

	private void displayImage(final ImageButton imageButton, String uri) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.iv_photo_empty)
				.showImageForEmptyUri(R.drawable.iv_photo_empty)
				.showImageOnFail(R.drawable.iv_photo_empty)
				.cacheInMemory(false)
				.cacheOnDisk(false).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoader.getInstance().displayImage(uri, imageButton, defaultOptions);
		
	}
	
	private void submitLogin(){
		String userName = etName.getText().toString().trim();
		if (TextUtils.isEmpty(userName)) {
			Toast.makeText(getActivity(), "用户名为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String password = etPassword.getText().toString().trim();
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(getActivity(), "密码为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String randomString = etVeriCode.getText().toString().trim();
		if (TextUtils.isEmpty(randomString)) {
			Toast.makeText(getActivity(), "验证码为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		UserInfoSP userInfoSP = UserInfoSP.getSingleInstance(getActivity());
		String mobileUserId = userInfoSP.getMobileUserId();
		String mobileChannelId = userInfoSP.getMobileChannelId();
		
		new SlideMenuHttpUtils(getActivity()).postLogin(userName, password, randomString, 
				mobileUserId, mobileChannelId, new SlideMenuHttpUtils.OnPostLoginListener() {
					
					@Override
					public void onPostLogin(UserInfoPar userInfoPar) {
						// TODO Auto-generated method stub
						
					}
				});
		
	}
}
