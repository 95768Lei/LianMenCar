package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 * @date 18/2/28
 */
public class DiscussActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.et_discuss)
    EditText etDiscuss;
    @BindView(R.id.discuss_listView)
    ListView discussListView;
    @BindView(R.id.discuss_trl)
    TwinklingRefreshLayout discussTrl;
    private List<String> disList = new ArrayList<>();
    private UniversalAdapter<String> disAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        discussTrl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);

            }
        });
    }

    private void initData() {
        getListData();
    }

    //获取评论数据
    private void getListData() {

    }

    private void initView() {
        tvTitleName.setText("车行评论");
        disAdapter = new UniversalAdapter<String>(mActivity, disList, R.layout.discuss_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };
        discussListView.setAdapter(disAdapter);
        initProgress(discussTrl);
    }

    @OnClick({R.id.iv_title_back, R.id.tv_send_discuss})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_send_discuss:
                sendDiscuss();
                break;
        }
    }

    private void sendDiscuss() {
        String discuss = etDiscuss.getText().toString().trim();
        if (TextUtils.isEmpty(discuss)) {
            showToast("内容不能为空");
            return;
        }

    }
}
