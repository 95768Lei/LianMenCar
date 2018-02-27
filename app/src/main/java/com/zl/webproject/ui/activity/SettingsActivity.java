package com.zl.webproject.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.ui.fragment.RealPersonFragment;
import com.zl.webproject.ui.fragment.UpdateNickNameFragment;
import com.zl.webproject.ui.fragment.UpdatePasswordFragment;
import com.zl.webproject.ui.fragment.UpdatePhoneFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author zhanglei
 * @date 18/2/24
 * 用户设置界面
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.civ_user_icon)
    CircleImageView civUserIcon;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.rl_sex)
    AutoRelativeLayout rlSex;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_user_real)
    TextView tvUserReal;
    @BindView(R.id.tv_update_password)
    TextView tvUpdatePassword;
    @BindView(R.id.tv_sign_out)
    TextView tvSignOut;
    private AlertDialog sexDialog;
    String[] sexs = {"男", "女"};
    private RealPersonFragment realPersonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleName.setText("修改资料");

        sexDialog = new AlertDialog.Builder(mActivity).setTitle("选择性别")
                .setSingleChoiceItems(sexs, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvSex.setText(sexs[i]);
                        sexDialog.dismiss();
                    }
                }).create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (realPersonFragment != null) {
            realPersonFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick({R.id.iv_title_back, R.id.civ_user_icon, R.id.rl_sex, R.id.tv_nick_name, R.id.tv_user_phone,
            R.id.tv_user_real, R.id.tv_update_password, R.id.tv_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.iv_title_back:
                finish();
                break;
            //修改头像
            case R.id.civ_user_icon:
                break;
            //修改性别
            case R.id.rl_sex:
                sexDialog.show();
                break;
            //修改昵称
            case R.id.tv_nick_name:
                infoUpdateNickName();
                break;
            //换绑手机号
            case R.id.tv_user_phone:
                intoUpdatePhone();
                break;
            //实名认证
            case R.id.tv_user_real:
                intoRealPerson();
                break;
            //修改密码
            case R.id.tv_update_password:
                intoUpdatePassword();
                break;
            //退出登录
            case R.id.tv_sign_out:
                finish();
                break;
        }
    }

    private void intoRealPerson() {
        realPersonFragment = new RealPersonFragment();
        getSupportFragmentManager().beginTransaction().addToBackStack("nickName").replace(R.id.setting_rl, realPersonFragment).commit();
    }

    private void intoUpdatePassword() {
        getSupportFragmentManager().beginTransaction().addToBackStack("nickName").replace(R.id.setting_rl, new UpdatePasswordFragment()).commit();
    }

    private void intoUpdatePhone() {
        getSupportFragmentManager().beginTransaction().addToBackStack("nickName").replace(R.id.setting_rl, new UpdatePhoneFragment()).commit();
    }

    private void infoUpdateNickName() {
        getSupportFragmentManager().beginTransaction().addToBackStack("nickName").replace(R.id.setting_rl, new UpdateNickNameFragment()).commit();
    }
}
