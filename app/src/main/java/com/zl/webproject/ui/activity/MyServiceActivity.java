package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.ui.fragment.ServiceListFragment;
import com.zl.webproject.utils.FragmentHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyServiceActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.my_service_tab)
    TabLayout myServiceTab;
    @BindView(R.id.my_service_rl)
    RelativeLayout myServiceRl;
    private ServiceListFragment fragment0;
    private ServiceListFragment fragment1;
    private ServiceListFragment fragment2;
    private ServiceListFragment fragment3;
    private FragmentHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initListener() {
        myServiceTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        helper.showFragment(fragment0);
                        break;
                    case 1:
                        helper.showFragment(fragment1);
                        break;
                    case 2:
                        helper.showFragment(fragment2);
                        break;
                    case 3:
                        helper.showFragment(fragment3);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        myServiceTab.addTab(myServiceTab.newTab().setText("处理违章"));
        myServiceTab.addTab(myServiceTab.newTab().setText("维修服务"));
        myServiceTab.addTab(myServiceTab.newTab().setText("保险服务"));
        myServiceTab.addTab(myServiceTab.newTab().setText("年审服务"));

        fragment0 = ServiceListFragment.newInstance(1);
        fragment1 = ServiceListFragment.newInstance(2);
        fragment2 = ServiceListFragment.newInstance(3);
        fragment3 = ServiceListFragment.newInstance(4);
        helper = FragmentHelper.builder(mActivity).attach(R.id.my_service_rl)
                .addFragment(fragment0)
                .addFragment(fragment1)
                .addFragment(fragment2)
                .addFragment(fragment3)
                .commit();
        tvTitleName.setText("我的服务");
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }
}
