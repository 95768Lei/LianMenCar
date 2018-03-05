package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SpUtlis;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;


/**
 * @author zhanglei
 * @date 18/2/26
 */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.tv_commit_feedback)
    TextView tvCommitFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        tvTitleName.setText("意见反馈");
    }

    @OnClick({R.id.iv_title_back, R.id.tv_commit_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_commit_feedback:
                commit();
                break;
        }
    }

    private void commit() {
        String data = etFeedback.getText().toString().trim();
        if (TextUtils.isEmpty(data)) {
            showToast("内容不能为空");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("feedbackContext", data);
        HttpUtils.getInstance().Post(mActivity, params, API.saveFeedback, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                showToast("反馈成功");
                finish();
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });

    }
}
