package com.zl.webproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zl.webproject.model.CityBean;

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

    public static void setLocationData(Context context, String cityCode, String data, String city) {
        SharedPreferences sp = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("cityCode", cityCode);
        edit.putString("cityName", data);
        edit.putString("city", city);
        edit.commit();
    }

    public static CityBean getLocationData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        String cityCode = sp.getString("cityCode", "");
        String data = sp.getString("cityName", "全国城市");
        String city = sp.getString("city", "全国城市");
        CityBean cityBean = new CityBean();
        cityBean.setCityCode(cityCode);
        cityBean.setCityName(city);
        cityBean.setCityData(data);
        return cityBean;
    }

    public static void setCurrentLocationData(Context context, CityBean cityBean) {
        SharedPreferences sp = context.getSharedPreferences("CurrentLocation", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("cityCode", cityBean.getCityCode());
        edit.putString("data", cityBean.getCityName());
        edit.commit();
    }

    public static CityBean getCurrentLocationData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("CurrentLocation", Context.MODE_PRIVATE);
        String cityCode = sp.getString("cityCode", "");
        String data = sp.getString("data", "");
        CityBean cityBean = new CityBean();
        cityBean.setCityCode(cityCode);
        cityBean.setCityName(data);
        return cityBean;
    }

}
