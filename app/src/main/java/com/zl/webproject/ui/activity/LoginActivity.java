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
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SpUtlis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * @author zhanglei
 * @date 18/2/27
 * 登录
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.et_input_phone)
    EditText etInputPhone;
    @BindView(R.id.et_input_password)
    EditText etInputPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.iv_login_qq)
    ImageView ivLoginQq;
    @BindView(R.id.iv_login_wx)
    ImageView ivLoginWx;
    @BindView(R.id.linear_other_login)
    AutoLinearLayout linearOtherLogin;
    private UMShareAPI umShareAPI;

    private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String openid = data.get("openid");
            if (platform == SHARE_MEDIA.QQ) {
                qqLogin(openid);
            } else if (platform == SHARE_MEDIA.WEIXIN) {
                wxLogin(openid);
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showToast("失败：" + t.getMessage());
//            showToast("登录失败");
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToast("用户取消");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        umShareAPI = UMShareAPI.get(mActivity);
        tvTitleName.setText("用户登录");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mActivity).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.iv_title_back, R.id.tv_title_right, R.id.tv_login, R.id.iv_login_qq, R.id.tv_forget_password, R.id.iv_login_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                back();
                break;
            case R.id.tv_title_right:
                toRegister();
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_forget_password:
                toForgetPassword();
                break;
            case R.id.iv_login_qq:
                umShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.iv_login_wx:
                umShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    private void toForgetPassword() {
        startActivity(new Intent(mActivity, ForgetPasswordActivity.class));
    }

    private void toRegister() {
        startActivity(new Intent(mActivity, RegisterActivity.class));
    }

    private void back() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void wxLogin(String openid) {

    }

    private void qqLogin(String openid) {

    }

    private void login() {
        String phone = etInputPhone.getText().toString().trim();
        String password = etInputPassword.getText().toString().trim();
        String regId = SpUtlis.getRegId(mActivity);
        CityBean locationData = SpUtlis.getLocationData(mActivity);

        if (TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }

        if (!RegexUtils.isMobileExact(phone)) {
            showToast("手机号格式不对");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("pass", password);
        params.put("pustCode", regId);
        params.put("cityCode", locationData.getCityCode());
        params.put("location", locationData.getCityData());
        HttpUtils.getInstance().Post(mActivity,params, API.login, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body.toString());
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });
    }
}
