package com.zl.webproject;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.sms.SMSSDK;

/**
 * Created by Administrator on 2017\12\8 0008.
 */

public class APP extends Application {

    {
        //正式的
        PlatformConfig.setWeixin("wx632fe3bffde07bb8", "a9bbdfd2087dabf7e0ab83b88b7de7b4");
        //张磊测试
//        PlatformConfig.setWeixin("wx4c2275d459244353", "e0f0a91eaf88ccb6fb2d1f551ab3e2f7");
        PlatformConfig.setQQZone("1106281667", "hm7mqiwacyT1Ywzb");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        //初始化JPush
        JPushInterface.init(this);
        //初始化JPush SMSSDK
        SMSSDK.getInstance().initSdk(this);
        //设置JPush前后两次获取验证码的时间间隔
        SMSSDK.getInstance().setIntervalTime(60000);
        //初始化友盟分享
        UMShareAPI.get(this);

    }
}
