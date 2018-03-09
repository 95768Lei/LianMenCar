package com.zl.webproject.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.zl.webproject.R;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/11/3.
 */

public class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected AppCompatActivity mActivity;
    protected ProgressDialog baseDialog;
    protected Handler handler = new Handler();
    protected SweetAlertDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        baseDialog = new ProgressDialog(mActivity);
        baseDialog.setMessage("加载中...");
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("加载中...");
        pDialog.setCancelable(true);
    }

    protected void showToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 找到控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getView(Integer viewId) {
        return (T) findViewById(viewId);
    }

    /**
     * 显示dialog 点击外部会消失
     *
     * @param Message
     */
    protected void showDialog(String Message) {
        baseDialog.setCanceledOnTouchOutside(true);
        baseDialog.setMessage(Message);
        baseDialog.show();
    }

    protected void initBanner(ConvenientBanner homeBanner) {
        homeBanner
                //设置指示器是否可见
                .setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(3000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_fiber_manual_record_black, R.drawable.ic_fiber_manual_record_white})
                //设置指示器的方向（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    /**
     * 显示dialog 点击外部不会消失
     *
     * @param Message
     */
    protected void showNotTouchDialog(String Message) {
        baseDialog.setCanceledOnTouchOutside(false);
        baseDialog.setMessage(Message);
        baseDialog.show();
    }

    protected void showDialog() {
        baseDialog.setCanceledOnTouchOutside(true);
        baseDialog.show();
    }

    protected void showNotTouchDialog() {
        baseDialog.setCanceledOnTouchOutside(false);
        baseDialog.show();
    }

    protected void hideDialog() {
        baseDialog.dismiss();
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 获取软键盘的显示状态
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // 隐藏软键盘
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 初始化刷新控件
     *
     * @param trl
     */
    protected void initProgress(TwinklingRefreshLayout trl) {
        ProgressLayout progressLayout = new ProgressLayout(mActivity);
        progressLayout.setColorSchemeResources(R.color.colorPrimary);
        trl.setHeaderView(progressLayout);
    }

    /**
     * 检查是否有该权限
     *
     * @param permission
     * @return
     */
    public boolean isPermission(String permission) {
        return (ContextCompat.checkSelfPermission(mActivity, permission)
                == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * 申请应用授权
     *
     * @param permissions 权限数组（同一组权限的）
     */
    protected void applyPermission(String[] permissions, final PermissionListener listener) {
        applyPermission(permissions, 0x100, listener);
    }

    /**
     * 申请应用授权
     *
     * @param permissions 权限数组（同一组权限的）
     */
    protected void applyPermission(String[] permissions, int code, final PermissionListener listener) {
        //是否需要动态申请权限
        if (Build.VERSION.SDK_INT >= 23) {
            AndPermission.with(this)
                    .requestCode(code)
                    .permission(permissions)
                    .callback(new PermissionListener() {
                        @Override
                        public void onSucceed(int requestCode, @android.support.annotation.NonNull List<String> grantPermissions) {
                            if (listener != null) {
                                listener.onSucceed(requestCode, grantPermissions);
                            }
                        }

                        @Override
                        public void onFailed(int requestCode, @android.support.annotation.NonNull List<String> deniedPermissions) {
                            if (listener != null) {
                                listener.onFailed(requestCode, deniedPermissions);
                            }
                        }
                    }).start();
        }
    }

    protected void openFragment(Fragment fragment, int layoutId) {
        getSupportFragmentManager().beginTransaction().addToBackStack("tag").add(layoutId, fragment).commit();
    }

    protected void openFragmentNoTask(Fragment fragment, int layoutId) {
        getSupportFragmentManager().beginTransaction().add(layoutId, fragment).commit();
    }

    /**
     * 打开相册的方法(单选)
     */
    public void openSingleAlbum(int requestCode) {

        //动态申请相机权限
        if (!isPermission(Manifest.permission.CAMERA)) {
            applyPermission(Permission.CAMERA, new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    openSingleAlbum(requestCode);
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
                    openSingleAlbum(requestCode);
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    showToast("您拒绝了文件储存权限，无法进行拍照");
                    finish();
                }
            });
            return;
        }

        PhotoPickerIntent intent = new PhotoPickerIntent(mActivity);
        intent.setSelectModel(SelectModel.SINGLE);
        intent.setShowCarema(false); // 是否显示拍照， 默认false
        mActivity.startActivityForResult(intent, requestCode);
    }

}
