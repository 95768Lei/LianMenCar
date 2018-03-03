package com.zl.webproject.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.zl.webproject.ui.activity.LoginActivity;

import java.util.List;

/**
 * Created by zhanglei on 2017\12\8 0008.
 * 系统功能工具类
 */

public class SystemUtils {

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    public static void call(final Context context, final String phone) {

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, "手机号为空，无法拨打电话", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isPermission(context, Manifest.permission.CALL_PHONE)) {
            //是否需要动态申请权限
            if (Build.VERSION.SDK_INT >= 23) {
                AndPermission.with(context)
                        .requestCode(1010)
                        .permission(Permission.PHONE)
                        .callback(new PermissionListener() {
                            @Override
                            public void onSucceed(int requestCode, @android.support.annotation.NonNull List<String> grantPermissions) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onFailed(int requestCode, @android.support.annotation.NonNull List<String> deniedPermissions) {
                                Toast.makeText(context, "您拒绝开启打电话权限，无法拨打电话", Toast.LENGTH_LONG).show();
                            }
                        }).start();
            } else {
                Toast.makeText(context, "应用没有授权打电话权限，无法拨打电话", Toast.LENGTH_LONG).show();
            }

            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 检查是否有该权限
     *
     * @param permission
     * @return
     */
    private static boolean isPermission(Context mActivity, String permission) {
        return (ContextCompat.checkSelfPermission(mActivity, permission)
                == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * 调用发短信界面
     *
     * @param context
     * @param phone
     */
    public static void sendSms(Context context, String phone) {
        Uri uri2 = Uri.parse("smsto:" + phone);
        Intent intentFinalMessage = new Intent(Intent.ACTION_VIEW, uri2);
//        intentFinalMessage.setType("vnd.android-dir/mms-sms");
        context.startActivity(intentFinalMessage);
    }

    public static void toActivity(Activity mActivity, Intent intent) {
        if (!SpUtlis.getLoginData(mActivity).isLogin()) {
            mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
            return;
        }

        mActivity.startActivity(intent);
    }

    public static void siganOut(Activity mActivity) {

    }

}
