package com.holyn.selectlocalpiclib;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author Holyn
 * @since 2015-3-12
 * 
 */

public class SelectLocalPicActivity extends FragmentActivity {
	public static final String EXTRA_MAX_SELECT = "extra_max_select";// 可选择的图片数量
	public static final String EXTRA_SELECT_IMAGEVOS = "extra_select_imagevos";// 已选择的图片列表

	public static final String EXTRA_IS_SHOW_CAMERA = "extra_is_show_camra";// 是否展示拍照

	private Intent preIntent;// 从其它Activity跳转过来的Intent
	private int maxSelect = 1;// //可选择的图片的最大数,默认为1
	private boolean isShowCamera = true;//默认显示拍照功能

	private FragmentManager fragmentManager;
	private LocalPictureGridFragment pictureGridFragment;

	private List<LocalAlbumVo> localAlbumVos = new ArrayList<LocalAlbumVo>();// 本地全部的相册分类
	private int curSelectAlbumPosition = 0;
	private AlbumListAdapter albumListAdapter;
	private ListView albumListView;
	private LocalAlbumListAnimatorUtil albumListAnimatorUtil;

	private List<LocalImageVo> selectImageVos = new ArrayList<LocalImageVo>();// 已经选中的图片列表

	private ImageButton ibBack;
	private TextView tvTitle;
	private Button btnFinish;
	private RelativeLayout rlAlbumName;
	private TextView tvAlbumName;
	private TextView tvPreView;
	private TextView tvSelectState;// 切换到预览图片的时候显示

	private Button btnTran;

	private LocalPicturePreviewFragment previewFragment = null;
	private LocalImageVo curPreviewImageVo = new LocalImageVo();// 当前预览的图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_local_pic);

		fragmentManager = getSupportFragmentManager();

		initData();
		initView();
		initListener();
		getLocalAlbumVos();
	}

	private void initData() {
		preIntent = getIntent();
		maxSelect = preIntent.getIntExtra(EXTRA_MAX_SELECT, 1);
		selectImageVos = preIntent.getParcelableArrayListExtra(EXTRA_SELECT_IMAGEVOS);
		isShowCamera = preIntent.getBooleanExtra(EXTRA_IS_SHOW_CAMERA, true);
	}

	private void initView() {
		pictureGridFragment = (LocalPictureGridFragment) fragmentManager.findFragmentById(R.id.frag_local_picture_grid);
		pictureGridFragment.setMaxSelect(maxSelect);
		pictureGridFragment.setIsShowCamera(isShowCamera);

		ibBack = (ImageButton) findViewById(R.id.ib_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);

		btnFinish = (Button) findViewById(R.id.btn_finish);
		btnFinish.setText("完成(" + selectImageVos.size() + "/" + maxSelect + ")");

		rlAlbumName = (RelativeLayout) findViewById(R.id.rl_album_name);
		tvAlbumName = (TextView) findViewById(R.id.tv_album_name);

		tvPreView = (TextView) findViewById(R.id.tv_preview);
		tvPreView.setText("预览(" + selectImageVos.size() + ")");

		tvSelectState = (TextView) findViewById(R.id.tv_select_state);

		albumListView = (ListView) findViewById(R.id.lv_album);
		albumListAdapter = new AlbumListAdapter();
		albumListView.setAdapter(albumListAdapter);
		albumListAnimatorUtil = new LocalAlbumListAnimatorUtil(this, albumListView);// 控制albumListView的弹出与关闭的动画

		btnTran = (Button) findViewById(R.id.btn_trans);
	}

	private void initListener() {
		ibBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		btnFinish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (albumListAnimatorUtil.isShow()) {
					hideAlbumList();
				}
				preIntent.putParcelableArrayListExtra(SelectLocalPicActivity.EXTRA_SELECT_IMAGEVOS,
						(ArrayList<? extends Parcelable>) selectImageVos);
				setResult(RESULT_OK, preIntent);
				SelectLocalPicActivity.this.finish();
			}
		});

		rlAlbumName.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (albumListAnimatorUtil.isShow()) {
					hideAlbumList();
				} else {
					showAlbumList();
				}
			}
		});

		tvPreView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (albumListAnimatorUtil.isShow()) {
					hideAlbumList();
				}
				if (selectImageVos.size() <= 0) {
					Toast.makeText(SelectLocalPicActivity.this, "请选择预览的图片", Toast.LENGTH_SHORT).show();
				} else {
					showLocalPicturePreviewFragment(0, selectImageVos);
				}
			}
		});

		btnTran.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (albumListAnimatorUtil.isShow()) {
					hideAlbumList();
				}
			}
		});

		albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				hideAlbumList();
				if (arg2 != curSelectAlbumPosition) {
					curSelectAlbumPosition = arg2;
					albumListAdapter.notifyDataSetChanged();
					pictureGridFragment.notifyDataChange(localAlbumVos.get(curSelectAlbumPosition).getLocalImageVos(), selectImageVos);
					tvAlbumName.setText(localAlbumVos.get(curSelectAlbumPosition).getName());
				}
			}

		});

		pictureGridFragment.setOnPictureSelectListener(new LocalPictureGridFragment.OnPictureSelectListener() {

			@Override
			public void onPictureSelect(LocalImageVo localImageVo, boolean isAdd) {
				int size = selectImageVos.size();
				if (isAdd) {
					selectImageVos.add(localImageVo);
				} else {
					selectImageVos.remove(isSelect(localImageVo));
				}
				size = selectImageVos.size();
				tvPreView.setText("预览(" + size + ")");
				btnFinish.setText("完成(" + size + "/" + maxSelect + ")");
			}
		});

	}
	
	/**
	 *  把return to its caller  
	 */
	public void setSelectImageVosResult(List<LocalImageVo> selectImageVos){
		preIntent.putParcelableArrayListExtra(SelectLocalPicActivity.EXTRA_SELECT_IMAGEVOS,
				(ArrayList<? extends Parcelable>) selectImageVos);
		setResult(RESULT_OK, preIntent);
		SelectLocalPicActivity.this.finish();
	}

	private void getLocalAlbumVos() {
		new QueryLocalPictureHandler(this) {

			@Override
			protected void finishGetLocalAlbumVos(List<LocalAlbumVo> vos) {
				// TODO Auto-generated method stub
				super.finishGetLocalAlbumVos(vos);
				if (vos != null) {
					localAlbumVos.addAll(vos);
					pictureGridFragment.notifyDataChange(localAlbumVos.get(0).getLocalImageVos(), selectImageVos);
					albumListAdapter.notifyDataSetChanged();
					tvAlbumName.setText(localAlbumVos.get(0).getName());
				}
			}

		}.excute();
	}

	/**
	 * 跳转预览图片，左右切换、放大缩小
	 */
	public void showLocalPicturePreviewFragment(int curPosition, List<LocalImageVo> localImageVos) {
		previewFragment = LocalPicturePreviewFragment.newInstance();
		Bundle args = new Bundle();
		args.putInt(LocalPicturePreviewFragment.EXTRA_CUR_POSITION, curPosition);
		args.putParcelableArrayList(LocalPicturePreviewFragment.EXTRA_PREVIEW_IMAGEVOS, (ArrayList<? extends Parcelable>) localImageVos);
		previewFragment.setArguments(args);

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fl_container, previewFragment);
		transaction.addToBackStack(null);
		transaction.commit();

		curPreviewImageVo = localImageVos.get(curPosition);// 首次打开LocalPicturePreviewFragment显示的imageVo

		// 配置LocalPicturePreviewFragment的布局
		initPreviewFragment();
	}

	private void initPreviewFragment() {
		previewFragment.setOnPictureChangeListener(new LocalPicturePreviewFragment.OnPictureChangeListener() {

			@Override
			public void OnPictureChange(LocalImageVo localImageVo) {
				curPreviewImageVo = localImageVo;
				setSelectState(localImageVo);
			}
		});

		tvSelectState.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSelect(curPreviewImageVo) >= 0) {
					selectImageVos.remove(isSelect(curPreviewImageVo));
					setSelectStateFalse();
				} else {
					if (selectImageVos.size() >= maxSelect) {
						Toast.makeText(SelectLocalPicActivity.this, "最多可选择" + maxSelect + "张图片", Toast.LENGTH_SHORT).show();
					} else {
						selectImageVos.add(curPreviewImageVo);
						setSelectStateTrue();
					}
				}
				// 刷新一下pictureGridFragment的页面
				pictureGridFragment.notifyDataChange(localAlbumVos.get(curSelectAlbumPosition).getLocalImageVos(), selectImageVos);
				int size = selectImageVos.size();
				tvPreView.setText("预览(" + size + ")");
				btnFinish.setText("完成(" + size + "/" + maxSelect + ")");
			}
		});
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	public void pictureGridFragmentSetting() {
		tvPreView.setVisibility(View.VISIBLE);
		rlAlbumName.setVisibility(View.VISIBLE);
		tvSelectState.setVisibility(View.GONE);
	}

	public void picturePreviewFragmentSetting() {
		tvPreView.setVisibility(View.GONE);
		rlAlbumName.setVisibility(View.GONE);
		tvSelectState.setVisibility(View.VISIBLE);
	}

	public void setSelectState(LocalImageVo localImageVo) {
		Drawable drawable = null;
		if (isSelect(localImageVo) >= 0) {
			drawable = getResources().getDrawable(R.drawable.ic_picture_selected_2);
		} else {
			drawable = getResources().getDrawable(R.drawable.ic_picture_unselected_2);
		}
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tvSelectState.setCompoundDrawables(drawable, null, null, null);
	}

	public void setSelectStateTrue() {
		Drawable drawable = getResources().getDrawable(R.drawable.ic_picture_selected_2);
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tvSelectState.setCompoundDrawables(drawable, null, null, null);
	}

	public void setSelectStateFalse() {
		Drawable drawable = getResources().getDrawable(R.drawable.ic_picture_unselected_2);
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		tvSelectState.setCompoundDrawables(drawable, null, null, null);
	}

	private void hideAlbumList() {
		albumListAnimatorUtil.hideAlbumList();
		btnTran.setVisibility(View.GONE);
	}

	private void showAlbumList() {
		albumListAnimatorUtil.showAlbumList();
		btnTran.setVisibility(View.VISIBLE);
	}

	@Override
	public void onBackPressed() {
		if (albumListAnimatorUtil.isShow()) {
			hideAlbumList();
		} else {
			super.onBackPressed();
		}
	}

	private int isSelect(LocalImageVo localImageVo) {
		// System.out.println("====> 验证的id = "+localImageVo.getId());
		int size = selectImageVos.size();
		for (int i = 0; i < size; i++) {
			int selectId = selectImageVos.get(i).getId();
			// System.out.println("====> 已选择的id = "+selectId);
			if (selectId == localImageVo.getId()) {
				return i;
			}
		}
		return -1;
	}

	private class AlbumListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return localAlbumVos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return localAlbumVos.get(position);
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
				convertView = LayoutInflater.from(SelectLocalPicActivity.this).inflate(R.layout.list_item_local_album, parent, false);
				holder.ivCover = (ImageView) convertView.findViewById(R.id.iv_album_cover);
				holder.tvName = (TextView) convertView.findViewById(R.id.tv_album_name);
				holder.tvSize = (TextView) convertView.findViewById(R.id.tv_album_size);
				holder.ivState = (ImageView) convertView.findViewById(R.id.iv_select_state);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			LocalAlbumVo albumVo = localAlbumVos.get(position);

			DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_picture_empty)
					.showImageForEmptyUri(R.drawable.ic_picture_empty).showImageOnFail(R.drawable.ic_picture_empty).cacheInMemory(false)
					.cacheOnDisk(false).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

			ImageLoader.getInstance().displayImage("file://" + albumVo.getCoverPath(), holder.ivCover, defaultOptions);

			holder.tvName.setText(albumVo.getName());
			holder.tvSize.setText(albumVo.getLocalImageVos().size() + "张");

			if (position == curSelectAlbumPosition) {
				holder.ivState.setVisibility(View.VISIBLE);
			} else {
				holder.ivState.setVisibility(View.GONE);
			}

			return convertView;
		}

		public final class ViewHolder {
			public ImageView ivCover;
			public TextView tvName;
			public TextView tvSize;
			public ImageView ivState;
		}
	}
}
