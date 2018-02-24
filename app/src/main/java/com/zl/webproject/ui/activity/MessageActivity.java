package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.ui.fragment.MessageDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 * @date 18/2/24
 * 消息中心
 */
public class MessageActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.message_listView)
    ListView messageListView;
    @BindView(R.id.message_trl)
    TwinklingRefreshLayout messageTrl;
    @BindView(R.id.message_rl)
    RelativeLayout messageRl;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;

    private List<String> mList = new ArrayList<>();
    private UniversalAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getSupportFragmentManager().beginTransaction().addToBackStack("message").replace(R.id.message_rl, new MessageDetailFragment()).commit();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mList.add("");
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        tvTitleName.setText("消息中心");
        tvTitleRight.setText("全部已读");
        tvTitleRight.setVisibility(View.VISIBLE);
        adapter = new UniversalAdapter<String>(mActivity, mList, R.layout.message_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };
        messageListView.setAdapter(adapter);
        initProgress(messageTrl);
    }

    @OnClick({R.id.iv_title_back, R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_title_right:
                showToast("标记成功");
                break;
        }
    }
}
