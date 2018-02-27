package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 *         车行详情页
 */
public class CarHangDetailActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.car_hang_banner)
    ConvenientBanner carHangBanner;
    @BindView(R.id.iv_car_hang_icon)
    ImageView ivCarHangIcon;
    @BindView(R.id.tv_car_hang_name)
    TextView tvCarHangName;
    @BindView(R.id.tv_car_data)
    TextView tvCarData;
    @BindView(R.id.tv_car_hang_person_phone)
    TextView tvCarHangPersonPhone;
    @BindView(R.id.iv_call)
    ImageView ivCall;
    @BindView(R.id.tv_info_more_discuss)
    TextView tvInfoMoreDiscuss;
    @BindView(R.id.discuss_listView)
    MyListView discussListView;
    @BindView(R.id.tv_info_more_car)
    TextView tvInfoMoreCar;
    @BindView(R.id.car_hang_listView)
    MyListView carListView;

    private List<String> disList = new ArrayList<>();
    private List<String> carList = new ArrayList<>();
    private UniversalAdapter<String> disAdapter;
    private UniversalAdapter<String> carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hang_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            disList.add("");
            carList.add("");
        }

        disAdapter.notifyDataSetChanged();
        carAdapter.notifyDataSetChanged();
    }

    private void initView() {
        disAdapter = new UniversalAdapter<String>(mActivity, disList, R.layout.discuss_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };

        carAdapter = new UniversalAdapter<String>(mActivity, carList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };

        discussListView.setAdapter(disAdapter);
        carListView.setAdapter(carAdapter);

        tvTitleName.setText("车行详情");
    }

    @OnClick({R.id.iv_title_back, R.id.iv_title_share, R.id.iv_call, R.id.tv_info_more_discuss, R.id.tv_info_more_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                break;
            case R.id.iv_title_share:
                break;
            case R.id.iv_call:
                break;
            case R.id.tv_info_more_discuss:
                break;
            case R.id.tv_info_more_car:
                break;
        }
    }
}
