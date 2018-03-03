package com.zl.webproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.LoginBean;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SmsUtils;
import com.zl.webproject.utils.SpUtlis;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.sms.listener.SmscheckListener;
import okhttp3.Request;

public class BindPhoneActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.et_input_phone)
    EditText etInputPhone;
    @BindView(R.id.et_input_code)
    EditText etInputCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.et_input_new_password)
    EditText etInputNewPassword;
    @BindView(R.id.tv_commit_update)
    TextView tvCommitUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleName.setText("绑定手机号");
    }

    @OnClick({R.id.iv_title_back, R.id.tv_send_code, R.id.tv_commit_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_send_code:
                sendCode();
                break;
            case R.id.tv_commit_update:
                commit();
                break;
        }
    }

    private void commit() {
        final String phone = etInputPhone.getText().toString();
        String code = etInputCode.getText().toString();
        final String password = etInputNewPassword.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }

        if (!RegexUtils.isMobileExact(phone)) {
            showToast("手机号格式不对");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            showToast("验证码不能为空");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }

        if (!SpUtlis.getLoginData(mActivity).getPassword().equals(password)) {
            showToast("密码不正确");
            return;
        }

        SmsUtils.checkSmsCode(mActivity, phone, code, new SmscheckListener() {
            @Override
            public void checkCodeSuccess(String s) {
                Map<String, String> params = new HashMap<>();
                params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
                params.put("phone", phone);
                params.put("pass", password);

                HttpUtils.getInstance().Post(mActivity, params, API.saveBindingPhone, new HttpUtils.OnOkHttpCallback() {
                    @Override
                    public void onSuccess(String body) {
                        showToast("手机号绑定成功");
                        LoginBean loginData = SpUtlis.getLoginData(mActivity);
                        loginData.setPhone(phone);
                        SpUtlis.setLoginData(mActivity, loginData);
                        LoginActivity.loginActivity.finish();
                        finish();
                    }

                    @Override
                    public void onError(Request error, Exception e) {
                        Log.e("body", "");
                    }
                });
            }

            @Override
            public void checkCodeFail(int i, String s) {

            }
        });
    }

    private void sendCode() {
        String phone = etInputPhone.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }

        if (!RegexUtils.isMobileExact(phone)) {
            showToast("手机号格式不对");
            return;
        }

        SmsUtils.sendCode(mActivity, phone, tvSendCode);
    }
}
