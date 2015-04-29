package com.holyn.selectlocalpiclib;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocalPicturePreviewFragment extends Fragment {
	public static final String EXTRA_CUR_POSITION = "extra_cur_position";// 预览图片开销的位置
	public static final String EXTRA_PREVIEW_IMAGEVOS = "extra_preview_imagevos";// 所有待预览的图片的list

	private View rootView;

	private int curPosition = 0;
	private List<LocalImageVo> localImageVos = new ArrayList<LocalImageVo>();
	private int imageSize = 0;

	private ViewPager viewPager;
	private PicturePagerAdapter pagerAdapter;

	/* 1.图片的切换事件 start.. */
	public OnPictureChangeListener onPictureChangeListener;

	public interface OnPictureChangeListener {
		public void OnPictureChange(LocalImageVo localImageVo);
	}

	public void setOnPictureChangeListener(OnPictureChangeListener onPictureChangeListener) {
		this.onPictureChangeListener = onPictureChangeListener;
	}

	/* 1.图片的切换事件 end.. */

	public static LocalPicturePreviewFragment newInstance() {
		return new LocalPicturePreviewFragment();
	}

	public LocalPicturePreviewFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = (View) inflater.inflate(R.layout.fragment_local_picture_preview, container, false);

		initData();
		initView();

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		((SelectLocalPicActivity) getActivity()).setTitle((curPosition + 1) + "/" + imageSize);
		((SelectLocalPicActivity) getActivity()).picturePreviewFragmentSetting();
		((SelectLocalPicActivity) getActivity()).setSelectState(localImageVos.get(curPosition));
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// 回退到LocalPictureGridFragment，还原设置
		((SelectLocalPicActivity) getActivity()).setTitle("图片选择");
		((SelectLocalPicActivity) getActivity()).pictureGridFragmentSetting();
	}

	private void initData() {
		curPosition = getArguments().getInt(EXTRA_CUR_POSITION);
		localImageVos.clear();
		List<LocalImageVo> newLocalImageVos = getArguments().getParcelableArrayList(EXTRA_PREVIEW_IMAGEVOS);
		localImageVos.addAll(newLocalImageVos);// 必须使用addAll方式

		imageSize = localImageVos.size();
	}

	private void initView() {
		viewPager = (ViewPager) rootView.findViewById(R.id.vp_loacl_picture_preview);

		// 注意：这里不能使用getChildFragmentManager()，否则会报getFragment
		// nullpointerexception
		pagerAdapter = new PicturePagerAdapter(getActivity().getSupportFragmentManager(), localImageVos);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(curPosition);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		// pagerAdapter.notifyDataSetChanged();
	}

	/*
	 * 监听ViewPager的页面切换事件
	 */
	private class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);
			curPosition = position;
			((SelectLocalPicActivity) getActivity()).setTitle((curPosition + 1) + "/" + imageSize);
			onPictureChangeListener.OnPictureChange(localImageVos.get(position));
		}
	}

	private class PicturePagerAdapter extends FragmentStatePagerAdapter {

		// private String albumType;
		// private AlbumVo albumVo;
		private List<LocalImageVo> imageVos;

		public PicturePagerAdapter(FragmentManager fm, List<LocalImageVo> imageVos) {
			super(fm);
			this.imageVos = imageVos;
		}

		@Override
		public int getCount() {
			return imageVos.size();
		}

		@Override
		public Fragment getItem(int position) {

			LocalPicturePreviewItemFragment itemFragment = LocalPicturePreviewItemFragment.newInstance();
			Bundle args = new Bundle();

			args.putString(LocalPicturePreviewItemFragment.EXTRA_PICTURE_PATH, imageVos.get(position).getPath());
			itemFragment.setArguments(args);

			return itemFragment;
		}

	}

}
