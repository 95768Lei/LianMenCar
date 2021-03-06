package com.zl.webproject.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
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
import butterknife.Unbinder;
import cn.jpush.sms.listener.SmscheckListener;
import okhttp3.Request;

/**
 * @author zhanglei
 *         修改密码
 */
public class UpdatePasswordFragment extends BaseFragment {


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
    @BindView(R.id.et_input_old_password)
    EditText etInputOldPassword;
    @BindView(R.id.et_input_new_password)
    EditText etInputNewPassword;
    @BindView(R.id.et_input_again_password)
    EditText etInputAgainPassword;
    @BindView(R.id.tv_commit_update)
    TextView tvCommitUpdate;
    Unbinder unbinder;

    public UpdatePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setClickable(true);
        initView();
        return view;
    }

    private void initView() {
        tvTitleName.setText("修改密码");

        LoginBean loginData = SpUtlis.getLoginData(mActivity);
        if (!TextUtils.isEmpty(loginData.getPhone())) {
            etInputPhone.setText(loginData.getPhone());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_title_back, R.id.tv_send_code, R.id.tv_commit_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                mActivity.finish();
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
        String phone = etInputPhone.getText().toString();
        String code = etInputCode.getText().toString();
        final String oldPassword = etInputOldPassword.getText().toString();
        final String newPassword = etInputNewPassword.getText().toString();
        String againPassword = etInputAgainPassword.getText().toString();

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

        if (TextUtils.isEmpty(oldPassword)) {
            showToast("旧密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            showToast("新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(againPassword)) {
            showToast("确认密码不能为空");
            return;
        }

        //验证
        SmsUtils.checkSmsCode(mActivity, phone, code, new SmscheckListener() {
            @Override
            public void checkCodeSuccess(String s) {
                Map<String, String> params = new HashMap<>();
                params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
                params.put("oldPass", oldPassword);
                params.put("newPass", newPassword);
                HttpUtils.getInstance().Post(mActivity, params, API.editPass, new HttpUtils.OnOkHttpCallback() {
                    @Override
                    public void onSuccess(String body) {
                        showToast("密码修改成功");
//                        getFragmentManager().popBackStack();
                        mActivity.finish();
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
