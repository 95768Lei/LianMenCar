package com.zl.webproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.utils.SpUtlis;


/**
 * @author zhanglei
 * @date 17/8/6
 * 启动页
 */
public class LauncherActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launcher);

        SpUtlis.setLocationData(mActivity, "", "全国城市", "全国城市");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PackageInfo pkg = null;
                try {
                    pkg = getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
                    int versionCode = SpUtlis.getVersionCode(LauncherActivity.this);
                    if (versionCode == -1) {
                        //储存当前版本号
                        SpUtlis.setVersionCode(LauncherActivity.this, pkg.versionCode);
                        //进入欢迎页
                        startActivity(new Intent(LauncherActivity.this, WelComeActivity.class));
                    } else {
                        if (versionCode != pkg.versionCode) {
                            //储存当前版本号
                            SpUtlis.setVersionCode(LauncherActivity.this, pkg.versionCode);
                            //进入欢迎页
                            startActivity(new Intent(LauncherActivity.this, WelComeActivity.class));
                        } else {
                            //进入首页
                            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                        }
                    }

                    finish();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }, 1500);
    }
}
