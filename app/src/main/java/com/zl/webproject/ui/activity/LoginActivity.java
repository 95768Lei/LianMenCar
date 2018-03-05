package com.zl.webproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.model.LoginBean;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SpUtlis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    public static LoginActivity loginActivity;

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
        loginActivity = this;
    }

    private void initView() {
        umShareAPI = UMShareAPI.get(mActivity);
        tvTitleName.setText("用户登录");
        LoginBean loginData = SpUtlis.getLoginData(mActivity);
        if (!TextUtils.isEmpty(loginData.getPhone())) {
            etInputPhone.setText(loginData.getPhone());
            etInputPassword.setText(loginData.getPassword());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mActivity).onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 66:
                    etInputPhone.setText(data.getStringExtra("phone"));
                    etInputPassword.setText("");
                    break;
            }
        }
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
                umShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.QQ, authListener);
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
        Intent intent = new Intent(mActivity, RegisterActivity.class);
        startActivityForResult(intent, 66);
    }

    private void back() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void wxLogin(String openid) {
        String regId = SpUtlis.getRegId(mActivity);
        CityBean locationData = SpUtlis.getLocationData(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("unionType", "WECHAT");
        params.put("loginQq", "");
        params.put("loginWechat", openid);
        params.put("pustCode", regId);
        params.put("cityCode", locationData.getCityCode());
        params.put("location", locationData.getCityData());

        final FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.add(entry.getKey(), entry.getValue());
        }
        //创建请求体
        Request request = new Request.Builder().url(API.unionLogin).post(builder.build()).build();

        //加入任务调度
        pDialog.setTitleText("登录中...");
        pDialog.show();
        new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.hide();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int result = jsonObject.optInt("result");
                            String jsonData = jsonObject.optString("data");
                            switch (result) {
                                //登录成功但未绑定手机号
                                case 0:
                                    SpUtlis.setUserData(mActivity, new Gson().fromJson(jsonData, CarUserEntity.class));
                                    Intent intent = new Intent(mActivity, BindPhoneActivity.class);
                                    startActivity(intent);
                                    break;
                                //登录成功
                                case 1:
                                    SpUtlis.setUserData(mActivity, new Gson().fromJson(jsonData, CarUserEntity.class));
                                    LoginBean bean = new LoginBean();
                                    bean.setLogin(true);
                                    SpUtlis.setLoginData(mActivity, bean);
                                    finish();
                                    break;
                                //登录失败
                                default:
                                    showToast(jsonObject.optString("msg"));
                                    break;
                            }

                        } catch (Exception e) {
                        }

                    }
                }, 1000);
            }
        });
    }

    private void qqLogin(String openid) {
        String regId = SpUtlis.getRegId(mActivity);
        CityBean locationData = SpUtlis.getLocationData(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("unionType", "QQ");
        params.put("loginQq", openid);
        params.put("loginWechat", "");
        params.put("pustCode", regId);
        params.put("cityCode", locationData.getCityCode());
        params.put("location", locationData.getCityData());

        final FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.add(entry.getKey(), entry.getValue());
        }
        //创建请求体
        Request request = new Request.Builder().url(API.unionLogin).post(builder.build()).build();

        //加入任务调度
        pDialog.setTitleText("登录中...");
        pDialog.show();
        new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.hide();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int result = jsonObject.optInt("result");
                            String jsonData = jsonObject.optString("data");
                            switch (result) {
                                //登录成功但未绑定手机号
                                case 0:
                                    SpUtlis.setUserData(mActivity, new Gson().fromJson(jsonData, CarUserEntity.class));
                                    Intent intent = new Intent(mActivity, BindPhoneActivity.class);
                                    startActivity(intent);
                                    break;
                                //登录成功
                                case 1:
                                    SpUtlis.setUserData(mActivity, new Gson().fromJson(jsonData, CarUserEntity.class));
                                    LoginBean bean = new LoginBean();
                                    bean.setLogin(true);
                                    SpUtlis.setLoginData(mActivity, bean);
                                    finish();
                                    break;
                                //登录失败
                                default:
                                    showToast(jsonObject.optString("msg"));
                                    break;
                            }

                        } catch (Exception e) {
                            Log.e("body", "");
                        }

                    }
                }, 1000);
            }
        });
    }

    private void login() {
        final String phone = etInputPhone.getText().toString().trim();
        final String password = etInputPassword.getText().toString().trim();
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
        pDialog.setTitleText("登录中...");
        pDialog.show();
        HttpUtils.getInstance().Post(mActivity, params, API.login, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(final String body) {
                SpUtlis.setUserData(mActivity, new Gson().fromJson(body, CarUserEntity.class));
                LoginBean bean = new LoginBean();
                bean.setLogin(true);
                bean.setPhone(phone);
                bean.setPassword(password);
                SpUtlis.setLoginData(mActivity, bean);
                finish();
            }

            @Override
            public void onError(Request error, Exception e) {
                pDialog.hide();
            }
        });
    }
}
