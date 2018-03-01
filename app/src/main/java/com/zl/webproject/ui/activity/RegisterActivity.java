package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.utils.SmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 * @date 18/2/28
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.et_input_phone)
    EditText etInputPhone;
    @BindView(R.id.et_input_code)
    EditText etInputCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.et_input_password)
    EditText etInputPassword;
    @BindView(R.id.tv_to_login)
    TextView tvToLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleName.setText("新用户注册");
    }

    @OnClick({R.id.iv_title_back, R.id.tv_send_code, R.id.tv_to_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_send_code:
                sendCode();
                break;
            case R.id.tv_to_login:
                finish();
                break;
            case R.id.tv_register:
                register();
                break;
        }
    }

    private void register() {
        String phone = etInputPhone.getText().toString().trim();
        String code = etInputCode.getText().toString().trim();
        String password = etInputPassword.toString().trim();

        if (!TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }

        if (!RegexUtils.isMobileExact(phone)) {
            showToast("手机号格式不对");
            return;
        }

        if (!TextUtils.isEmpty(code)) {
            showToast("验证码不能为空");
            return;
        }

        if (!TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }

        if (!SmsUtils.checkSmsCode(mActivity, phone, code)) {
            return;
        }
    }

    private void sendCode() {
        String phone = etInputPhone.getText().toString();

        if (!TextUtils.isEmpty(phone)) {
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
