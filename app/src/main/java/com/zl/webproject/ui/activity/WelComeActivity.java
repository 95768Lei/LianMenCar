package com.zl.webproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zl.webproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglei
 * @date 17/8/6
 * 欢迎页
 */
public class WelComeActivity extends Activity {

    //    private final String[] images = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502119060715&di=94455a60b02c9cffb81d2b0fcf9689bd&imgtype=0&src=http%3A%2F%2Fimg.kutoo8.com%2Fupload%2Fthumb%2F134903%2Fe541c320571ab0e0555d943f86b5e882_228x342.jpg",
//            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502627032&di=f579a2611e8541e33c45492329d54e52&imgtype=jpg&er=1&src=http%3A%2F%2Fsjbz.fd.zol-img.com.cn%2Ft_s320x510c%2Fg5%2FM00%2F00%2F03%2FChMkJlfJVauIAHBHAA2il9dRBTwAAU95QAjzmgADaKv028.jpg",
//            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502032361046&di=09563adfe31d7c85c20451935a888e59&imgtype=0&src=http%3A%2F%2Fimg.woyaogexing.com%2F2015%2F04%2F13%2F7ff110715c8efe15%2521600x600.jpg"};
    private final int[] images = {R.mipmap.image1, R.mipmap.image2, R.mipmap.image3};
    private LinearLayout linear;
    private ViewPager viewPager;
    private List<Integer> mList = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private int oldIndex = 0;
    private Button btIntoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wel_come);
        linear = (LinearLayout) findViewById(R.id.wel_linear);
        viewPager = (ViewPager) findViewById(R.id.wel_viewPager);
        btIntoMain = (Button) findViewById(R.id.bt_into_main);

        for (int image : images) {
            mList.add(image);
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16, 16);
            params.setMargins(16, 0, 16, 0);
            textView.setBackground(getResources().getDrawable(R.drawable.text_bg_selector));
            textView.setLayoutParams(params);
            linear.addView(textView);
        }

        linear.getChildAt(oldIndex).setSelected(true);

        mAdapter = new MyPagerAdapter();

        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == (mList.size() - 1)) {
                    btIntoMain.setVisibility(View.VISIBLE);
                } else {
                    btIntoMain.setVisibility(View.GONE);
                }
                linear.getChildAt(oldIndex).setSelected(false);
                linear.getChildAt(i).setSelected(true);
                oldIndex = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        btIntoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelComeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(WelComeActivity.this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(mList.get(position));
//            Glide.with(WelComeActivity.this).load(mList.get(position)).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
