package com.zl.webproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.google.gson.Gson;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.model.CityBean;
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
import okhttp3.Request;

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
        final String phone = etInputPhone.getText().toString().trim();
        String code = etInputCode.getText().toString().trim();
        String password = etInputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }

        if (!RegexUtils.isMobileExact(phone)) {
            showToast("手机号格式不对");
            return;
        }

//        if (TextUtils.isEmpty(code)) {
//            showToast("验证码不能为空");
//            return;
//        }

        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }

//        if (!SmsUtils.checkSmsCode(mActivity, phone, code)) {
//            return;
//        }
        CityBean locationData = SpUtlis.getLocationData(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("pass", password);
        params.put("cityCode", locationData.getCityCode());
        params.put("location", locationData.getCityData());
        HttpUtils.getInstance().Post(mActivity, params, API.Register, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body.toString());
                showToast("注册成功");
                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
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
