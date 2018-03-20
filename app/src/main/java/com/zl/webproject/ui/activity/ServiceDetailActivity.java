package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.view.MyListView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * @author zhanglei
 * @date 18/3/20
 */
public class ServiceDetailActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.service_banner)
    ConvenientBanner serviceBanner;
    @BindView(R.id.tv_serviceCollectionCount)
    TextView tvServiceCollectionCount;
    @BindView(R.id.iv_isFollowDetails)
    ImageView ivIsFollowDetails;
    @BindView(R.id.iv_service_hang_icon)
    ImageView ivServiceHangIcon;
    @BindView(R.id.tv_service_hang_name)
    TextView tvServiceHangName;
    @BindView(R.id.tv_service_data)
    TextView tvServiceData;
    @BindView(R.id.tv_service_hang_person)
    TextView tvServiceHangPerson;
    @BindView(R.id.tv_service_person_phone)
    TextView tvServicePersonPhone;
    @BindView(R.id.iv_call)
    ImageView ivCall;
    @BindView(R.id.service_tab)
    TabLayout serviceTab;
    @BindView(R.id.tv_service_content)
    TextView tvServiceContent;
    @BindView(R.id.service_linear)
    AutoLinearLayout serviceLinear;
    @BindView(R.id.tv_info_more_discuss)
    TextView tvInfoMoreDiscuss;
    @BindView(R.id.discuss_listView)
    MyListView discussListView;
    @BindView(R.id.tv_to_discuss)
    TextView tvToDiscuss;
    @BindView(R.id.linear_dis)
    AutoLinearLayout linearDis;
    @BindView(R.id.service_scroll_trl)
    TwinklingRefreshLayout serviceScrollTrl;
    private int sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        sid = getIntent().getIntExtra("sid", 0);
        getUiData();
    }

    /**
     * 获取界面数据
     */
    private void getUiData() {
        Map<String, String> params = new HashMap<>();
        params.put("sid", sid + "");
        HttpUtils.getInstance().Post(mActivity, params, API.getServerInfoById, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body);
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });
    }

    private void initView() {
        tvTitleName.setText("服务详情");
    }

    @OnClick({R.id.iv_title_back, R.id.iv_call, R.id.tv_info_more_discuss, R.id.tv_to_discuss})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.iv_call:

                break;
            case R.id.tv_info_more_discuss:

                break;
            case R.id.tv_to_discuss:

                break;
        }
    }
}
