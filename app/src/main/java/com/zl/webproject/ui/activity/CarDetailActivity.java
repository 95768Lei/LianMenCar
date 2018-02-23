package com.zl.webproject.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.ui.fragment.HomeFragment;
import com.zl.webproject.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 * @date 18/2/22
 */
public class CarDetailActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.into_car_hang)
    AutoLinearLayout intoCarHang;
    @BindView(R.id.into_share_car)
    AutoLinearLayout intoShareCar;
    @BindView(R.id.info_shou_cang_car)
    AutoLinearLayout infoShouCangCar;
    @BindView(R.id.info_call)
    AutoLinearLayout infoCall;
    @BindView(R.id.car_detail_bottom)
    AutoLinearLayout carDetailBottom;
    @BindView(R.id.car_detail_line)
    TextView carDetailLine;
    @BindView(R.id.home_banner)
    ConvenientBanner homeBanner;
    @BindView(R.id.car_detail_data_listView)
    MyListView carDetailDataListView;
    @BindView(R.id.carDetail_iv_listView)
    MyListView carDetailIvListView;

    private List<String> dList = new ArrayList<>();
    private List<String> ivList = new ArrayList<>();
    private List<Integer> bList = new ArrayList<>();
    private UniversalAdapter<String> dAdapter;
    private UniversalAdapter<String> ivAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            dList.add("");
            ivList.add("");
        }
        dAdapter.notifyDataSetChanged();
        ivAdapter.notifyDataSetChanged();
        bList.add(R.mipmap.icon1);
        bList.add(R.mipmap.icon1);
        bList.add(R.mipmap.icon1);
        bList.add(R.mipmap.icon1);
        homeBanner.notifyDataSetChanged();
    }

    private void initView() {
        dAdapter = new UniversalAdapter<String>(mActivity, dList, R.layout.car_data_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };
        ivAdapter = new UniversalAdapter<String>(mActivity, ivList, R.layout.car_iv_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };

        carDetailDataListView.setAdapter(dAdapter);
        carDetailIvListView.setAdapter(ivAdapter);

        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        homeBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, bList);

        initBanner(homeBanner);
    }

    @OnClick({R.id.iv_title_back, R.id.into_car_hang, R.id.into_share_car, R.id.info_shou_cang_car, R.id.info_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.iv_title_back:
                finish();
                break;
            //进入车行
            case R.id.into_car_hang:
                break;
            //分享车辆
            case R.id.into_share_car:
                break;
            //收藏车辆
            case R.id.info_shou_cang_car:
                break;
            //打电话
            case R.id.info_call:
                break;
        }
    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }
}
