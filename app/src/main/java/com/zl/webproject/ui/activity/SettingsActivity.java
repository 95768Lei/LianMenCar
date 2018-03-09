package com.zl.webproject.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foamtrace.photopicker.PhotoPickerActivity;
import com.google.gson.Gson;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.model.LoginBean;
import com.zl.webproject.ui.dialog.PhotoDialog;
import com.zl.webproject.ui.fragment.RealPersonFragment;
import com.zl.webproject.ui.fragment.UpdateNickNameFragment;
import com.zl.webproject.ui.fragment.UpdatePasswordFragment;
import com.zl.webproject.ui.fragment.UpdatePhoneFragment;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.OnReturnStringListener;
import com.zl.webproject.utils.PhotoBitmapUtils;
import com.zl.webproject.utils.SpUtlis;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

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
    private UpdateNickNameFragment updateNickNameFragment;
    private UpdatePhoneFragment updatePhoneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        HttpUtils.getInstance().Post(mActivity, params, API.getUserInfo, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                CarUserEntity entity = new Gson().fromJson(body, CarUserEntity.class);
                updateUi(entity);
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });

        HttpUtils.getInstance().Post(mActivity, params, API.getApproverInfo, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body);
                tvUserReal.setText("您已实名认证");
            }

            @Override
            public void onError(Request error, Exception e) {
                if (e.getMessage().equals("您还未完善个人信息")) {
                    tvUserReal.setText("您还未实名认证");
                }
            }
        });

    }

    /**
     * 获得到了数据，更新Ui
     *
     * @param entity
     */
    private void updateUi(CarUserEntity entity) {
        ImageLoader.loadImageUrl(mActivity, entity.getUserImg(), civUserIcon);
        tvNickName.setText(entity.getUserNikeName());
        tvUserPhone.setText(entity.getUserPhone());
        Integer userSex = entity.getUserSex();
        if (userSex == 1) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }
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
                        String sex = sexs[i];
                        tvSex.setText(sex);
                        sexDialog.dismiss();
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
                        if (sex.equals("男")) {
                            params.put("sex", "1");
                        } else {
                            params.put("sex", "2");
                        }

                        HttpUtils.getInstance().Post(mActivity, params, API.editUserSex, new HttpUtils.OnOkHttpCallback() {
                            @Override
                            public void onSuccess(String body) {

                            }

                            @Override
                            public void onError(Request error, Exception e) {

                            }
                        });

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
                    upLoadImg();
                    break;
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    userIconPath = pathList.get(0);
                    upLoadImg();
                    break;
            }

        }
    }

    /**
     * 上传图片
     */
    private void upLoadImg() {
        List<String> list = new ArrayList<>();
        list.add(userIconPath);
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        HttpUtils.getInstance().upLoadFile(mActivity, params, API.editUserImg, list, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    CarUserEntity entity = new Gson().fromJson(body, CarUserEntity.class);
                    ImageLoader.loadImageFile(mActivity, userIconPath, civUserIcon);
                    showToast("修改成功");
                    CarUserEntity userData = SpUtlis.getUserData(mActivity);
                    userData.setUserImg(entity.getUserImg());
                    SpUtlis.setUserData(mActivity, userData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("body", body);
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });
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
                signOut();
                break;
        }
    }

    private void signOut() {
        LoginBean loginData = SpUtlis.getLoginData(mActivity);
        loginData.setLogin(false);
        SpUtlis.setLoginData(mActivity, loginData);
        CarUserEntity userData = SpUtlis.getUserData(mActivity);
        userData.setUserNikeName("");
        userData.setUserImg("");
        SpUtlis.setUserData(mActivity, userData);
        startActivity(new Intent(mActivity, LoginActivity.class));
        finish();
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
        if (!isPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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
        updatePhoneFragment = new UpdatePhoneFragment();
        updatePhoneFragment.setOnReturnStringListener(new OnReturnStringListener() {
            @Override
            public void onReturnString(String data) {
                tvUserPhone.setText(data);
            }
        });
        getSupportFragmentManager().beginTransaction().addToBackStack("nickName").replace(R.id.setting_rl, updatePhoneFragment).commit();
    }

    private void infoUpdateNickName() {
        updateNickNameFragment = new UpdateNickNameFragment();
        updateNickNameFragment.setOnReturnStringListener(new OnReturnStringListener() {
            @Override
            public void onReturnString(String data) {
                tvNickName.setText(data);
            }
        });
        getSupportFragmentManager().beginTransaction().addToBackStack("nickName").replace(R.id.setting_rl, updateNickNameFragment).commit();
    }
}
