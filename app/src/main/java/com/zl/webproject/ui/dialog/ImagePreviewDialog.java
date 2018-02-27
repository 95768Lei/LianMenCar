package com.zl.webproject.ui.dialog;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseDialog;
import com.zl.webproject.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27.
 */

public class ImagePreviewDialog extends BaseDialog {

    private TextView tvIndex;
    private ViewPager viewPager;
    private List<String> mList = new ArrayList<>();
    private ImageAdapter adapter = new ImageAdapter();

    public ImagePreviewDialog(Activity mActivity) {
        super(mActivity);
        initView();
        initListener();
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndex.setText((position + 1) + "/" + mList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.image_preview_layout, null);
        view.findViewById(R.id.iv_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });
        tvIndex = view.findViewById(R.id.home_tv_index);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        initPopupWindow(view);
    }

    private class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.car_iv_item, null);
            ImageView image = view.findViewById(R.id.car_item_image);
            ImageLoader.loadImageUrl(mActivity, mList.get(position), image);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public void setData(List<String> mList) {
        if (this.mList.size() > 0) {
            return;
        }

        this.mList.addAll(mList);

        tvIndex.setText("1/" + this.mList.size());
        adapter.notifyDataSetChanged();
    }
}
