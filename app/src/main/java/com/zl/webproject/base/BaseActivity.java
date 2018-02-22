package com.zl.webproject.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected AppCompatActivity mActivity;
    protected ProgressDialog baseDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        baseDialog = new ProgressDialog(mActivity);
        baseDialog.setMessage("加载中...");
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

}
