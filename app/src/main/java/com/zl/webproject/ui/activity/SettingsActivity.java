package com.zl.webproject.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foamtrace.photopicker.PhotoPickerActivity;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.ui.dialog.PhotoDialog;
import com.zl.webproject.ui.fragment.RealPersonFragment;
import com.zl.webproject.ui.fragment.UpdateNickNameFragment;
import com.zl.webproject.ui.fragment.UpdatePasswordFragment;
import com.zl.webproject.ui.fragment.UpdatePhoneFragment;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.PhotoBitmapUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.zl.webproject.ui.dialog.PhotoDialog.PICK_FROM_CAMERA;
import static com.zl.webproject.ui.dialog.PhotoDialog.REQUEST_CAMERA_CODE;

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
    private PhotoDialog photoDialog;
    private String userIconPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initListener() {
        photoDialog.setOnChoosePhotoListener(new PhotoDialog.OnChoosePhotoListener() {
            @Override
            public void openAlbum() {
                photoDialog.openSingleAlbum();
            }

            @Override
            public void openCamera() {
                photoDialog.openCamera();
            }
        });
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

        photoDialog = new PhotoDialog(mActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (realPersonFragment != null) {
            realPersonFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_FROM_CAMERA:
                    String imagePath = photoDialog.getImagePath();
                    //修改小米手机拍照后图片旋转的问题
                    userIconPath = PhotoBitmapUtils.amendRotatePhoto(imagePath, mActivity);
                    break;
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    userIconPath = pathList.get(0);
                    break;
            }

            ImageLoader.loadImageFile(mActivity,userIconPath,civUserIcon);
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
                openPhoto();
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

    private void openPhoto() {
        //动态申请相机权限
        if (!isPermission(Manifest.permission.CAMERA)) {
            applyPermission(Permission.CAMERA, new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    openPhoto();
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    showToast("您拒绝了相机权限，无法进行拍照");
                    finish();
                }
            });
            return;
        }
        //动态申请文件读写权限
        if (!isPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            applyPermission(Permission.STORAGE, new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    openPhoto();
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    showToast("您拒绝了文件储存权限，无法进行拍照");
                    finish();
                }
            });
            return;
        }

        photoDialog.showDialog(civUserIcon);
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
