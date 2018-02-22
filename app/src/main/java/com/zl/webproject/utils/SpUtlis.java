package com.zl.webproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhanglei on 2017/8/6.
 */

public class SpUtlis {
    public static void setVersionCode(Context context, int versionCode) {
        SharedPreferences sp = context.getSharedPreferences("version", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("versionCode", versionCode);
        edit.commit();
    }

    public static int getVersionCode(Context context) {
        SharedPreferences sp = context.getSharedPreferences("version", Context.MODE_PRIVATE);
        return sp.getInt("versionCode", -1);
    }

    public static void setRegId(Context context, String regId) {
        SharedPreferences sp = context.getSharedPreferences("regId", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("regId", regId);
        edit.commit();
    }

    public static String getRegId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("regId", Context.MODE_PRIVATE);
        return sp.getString("regId", "");
    }

    public static void setLocationData(Context context, String cityCode, String data) {
        SharedPreferences sp = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("cityCode", cityCode);
        edit.putString("data", data);
        edit.commit();
    }

}
