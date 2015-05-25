package com.holyn.selectlocalpiclib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.holyn.selectlocalpiclib.util.LoclImageLoader;
import com.holyn.selectlocalpiclib.util.MediaScannerUtil;
import com.holyn.selectlocalpiclib.util.TakePhotoUtil;
import com.holyn.selectlocalpiclib.util.LoclImageLoader.Type;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LocalPictureGridFragment extends BaseFragment {
	// 请求照相机拍照获取图片
	public static final int REQUEST_CODE_TAKE_PHOTO = 1;
	
	private LoclImageLoader imageLoader2;

	private List<LocalImageVo> localImageVos;
	private List<LocalImageVo> selectImageVos;
	private int maxSelect = 1;// 可选择的图片的最大数,默认为1
	private boolean isShowCamera = true;// 是否显示拍照功能
	
	public String imgPath;// 拍照时图片存储的路径

	private LocalPictureGridAdapter adapter;
	private GridView gridView;
	
	private ProgressDialog scanPhotoDialog = null;//拍完照后，手机扫描存储图片的时候会消耗一定时间

	/* 1.图片的选择事件 start.. */
	public OnPictureSelectListener onPictureSelectListener;

	public interface OnPictureSelectListener {
		public void onPictureSelect(LocalImageVo localImageVo, boolean isAdd);
	}

	public void setOnPictureSelectListener(OnPictureSelectListener onPictureSelectListener) {
		this.onPictureSelectListener = onPictureSelectListener;
	}
	/* 1.图片的选择事件 end.. */
	
	public static LocalPictureGridFragment newInstance() {
		return new LocalPictureGridFragment();
	}

	public LocalPictureGridFragment() {
		localImageVos = new ArrayList<LocalImageVo>();
		selectImageVos = new ArrayList<LocalImageVo>();
		imageLoader2 = LoclImageLoader.getInstance(12, Type.LIFO);
	}

	@Override
	protected int getResourceLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_local_picture_grid;
	}

	@Override
	protected void init() {
		gridView = (GridView) rootLayout.findViewById(R.id.gv_picture);
		adapter = new LocalPictureGridAdapter();
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (isShowCamera && arg2 == 0) {
					goTakePhoto();//开始拍照
				}
			}
		});
	}

	public void setMaxSelect(int maxSelect) {
		this.maxSelect = maxSelect;
	}

	public void setIsShowCamera(boolean isShowCamera) {
		this.isShowCamera = isShowCamera;
	}
	
	/** 拍照 */
	private void goTakePhoto(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if(intent.resolveActivity(getActivity().getPackageManager()) != null){
			TakePhotoUtil takePhotoUtil = new TakePhotoUtil(getActivity());
			imgPath = takePhotoUtil.getImgPath();
			//把拍照指定存储的路径保存到application，有些手机在拍照回调该页面时，imgPath会null
//			application.setImgPath(imgPath);
			intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgPath)));
			/** ----------------- */
			startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
		}else {
			Toast.makeText(getActivity(), "无法调用系统相机", Toast.LENGTH_SHORT).show();
		}

	}

	public void notifyDataChange(List<LocalImageVo> localImageVos, List<LocalImageVo> selectImageVos) {
		// System.out.println("====> notifyDataChange---localImageVos = "+localImageVos.size());
		this.localImageVos.clear();
		this.localImageVos.addAll(localImageVos);

		this.selectImageVos.clear();
		this.selectImageVos.addAll(selectImageVos);

		adapter.notifyDataSetChanged();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_CODE_TAKE_PHOTO:
			switch (resultCode) {
			case Activity.RESULT_OK:
//				System.out.println("====> 拍照回调imgPath = "+imgPath);
				if (imgPath == null) {
					//这里的imgPath是全局变量，有些手机比如三星SCH-I699，
					//它在拍完照快速跳转回来的时候，imgPath是空的，故此处使用缓存application读取
//					imgPath = application.getImgPath();
					imgPath = new TakePhotoUtil(getActivity()).getImgPath();
				}
				// 启动MediaScanner，扫描图片并把图片路径保存到/data/data/com.android.providers.media/databases上的一个数据库
				showScanPhotoDialog();
				MediaScannerUtil scanUtil = new MediaScannerUtil(getActivity());
				scanUtil.scanFile(imgPath, "image/jpeg");
				scanUtil.setOnMediaScannerListener(new MediaScannerUtil.OnMediaScannnerListener() {
					public void onMediaScanner(Uri uri) {
//						System.out.println("====> photoUri = "+photoUri);
						// 根据指定的uri，查询图片在数据库中的信息
						LocalImageVo localImageVo = QueryLocalPictureUtil.queryLocalImageVo(LocalPictureGridFragment.this.getActivity(),uri);

						// 判断查询出来的图片路径是否为空，不为空则返回结果
						if (localImageVo.getPath() != null) {// 拍照成功
							List<LocalImageVo> localImageVos = new ArrayList<LocalImageVo>();
							localImageVos.add(localImageVo);
							
							((SelectLocalPicActivity)getActivity()).setSelectImageVosResult(localImageVos);//返回照片给caller，并关闭activity
						} else {
							Toast.makeText(getActivity(), "拍照失败", Toast.LENGTH_SHORT).show();
						}
						closeScanPhotoDialog();
					}
				});
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
	}



	private class LocalPictureGridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (isShowCamera) {
				return localImageVos.size() + 1;
			} else {
				return localImageVos.size();
			}
		}

		@Override
		public Object getItem(int position) {
			if (isShowCamera) {
				return localImageVos.get(position - 1);
			} else {
				return localImageVos.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_image_select, parent, false);
				holder.ivPicture = (ImageView) convertView.findViewById(R.id.iv_picture);
				holder.rlSurfaceColor = (RelativeLayout) convertView.findViewById(R.id.rl_surface_color);
				holder.btnSelect = (Button) convertView.findViewById(R.id.btn_picture_select);
				holder.flCamera = (FrameLayout) convertView.findViewById(R.id.fl_camera);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final LocalImageVo localImageVo;
			if (isShowCamera) {
				if (position == 0) {
					holder.flCamera.setVisibility(View.VISIBLE);
					holder.ivPicture.setVisibility(View.GONE);
					holder.rlSurfaceColor.setVisibility(View.GONE);
				}else {
					holder.flCamera.setVisibility(View.GONE);
					holder.ivPicture.setVisibility(View.VISIBLE);
					holder.rlSurfaceColor.setVisibility(View.VISIBLE);
					
					localImageVo = localImageVos.get(position - 1);
					initViewHolder(position - 1, holder, localImageVo);
				}
			} else {
				localImageVo = localImageVos.get(position);
				initViewHolder(position, holder, localImageVo);
			}
			return convertView;
		}

		/**
		 * 设置ViewHolder里面的值
		 */
		private void initViewHolder(final int position, final ViewHolder holder, final LocalImageVo localImageVo) {

			imageLoader2.loadImage(localImageVo.getPath(), holder.ivPicture);

			if (isSelect(localImageVo) != -1) {
				// System.out.println("====> 已经选择："+localImageVo.getId());
				holder.btnSelect.setBackgroundResource(R.drawable.ic_picture_selected);
				holder.rlSurfaceColor.setBackgroundColor(getActivity().getResources().getColor(R.color.black_trans_50));
			} else {
				// System.out.println("====> 未选择："+localImageVo.getId());
				holder.btnSelect.setBackgroundResource(R.drawable.ic_picture_unselected);
				holder.rlSurfaceColor.setBackgroundColor(getActivity().getResources().getColor(R.color.trans));
			}

			holder.btnSelect.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (isSelect(localImageVo) != -1) {
						selectImageVos.remove(isSelect(localImageVo));
						holder.btnSelect.setBackgroundResource(R.drawable.ic_picture_unselected);
						holder.rlSurfaceColor.setBackgroundColor(getActivity().getResources().getColor(R.color.trans));
						onPictureSelectListener.onPictureSelect(localImageVo, false);
					} else {
						if (selectImageVos.size() >= maxSelect) {
							Toast.makeText(getActivity(), "最多可选择" + maxSelect + "张图片", Toast.LENGTH_SHORT).show();
						} else {
							selectImageVos.add(localImageVo);
							holder.btnSelect.setBackgroundResource(R.drawable.ic_picture_selected);
							holder.rlSurfaceColor.setBackgroundColor(getActivity().getResources().getColor(R.color.black_trans_50));
							onPictureSelectListener.onPictureSelect(localImageVo, true);
						}
					}
				}
			});

			holder.rlSurfaceColor.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// 跳转到可左右滑动切换、放大缩小的Fragment
					((SelectLocalPicActivity) getActivity()).showLocalPicturePreviewFragment(position, localImageVos);
				}
			});
		}

		public final class ViewHolder {
			public ImageView ivPicture;
			public RelativeLayout rlSurfaceColor;
			public Button btnSelect;
			public FrameLayout flCamera;
		}
	}

	private int isSelect(LocalImageVo localImageVo) {
		int size = selectImageVos.size();
		for (int i = 0; i < size; i++) {
			int selectId = selectImageVos.get(i).getId();
			if (selectId == localImageVo.getId()) {
				return i;
			}
		}
		return -1;
	}
	
	private void showScanPhotoDialog(){
		if (scanPhotoDialog == null) {
			scanPhotoDialog = getProgressDialog("正在扫描照片....");
			scanPhotoDialog.show();
		}
	}
	
	private void closeScanPhotoDialog(){
		if (scanPhotoDialog != null) {
			scanPhotoDialog.dismiss();
			scanPhotoDialog = null;
		}
	}
	
	private ProgressDialog getProgressDialog(String message){
		ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage(message);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

}
