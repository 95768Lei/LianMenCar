package com.zl.webproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.model.LoginBean;

import java.util.HashMap;
import java.util.Map;

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

    //储存选择的城市信息
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

    //储存当前定位的城市信息
    public static void setCuLocationData(Context context, String cityCode, String data, String city) {
        SharedPreferences sp = context.getSharedPreferences("cuLocation", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("cityCode", cityCode);
        edit.putString("cityName", data);
        edit.putString("city", city);
        edit.commit();
    }

    public static CityBean getCuLocationData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("cuLocation", Context.MODE_PRIVATE);
        String cityCode = sp.getString("cityCode", "");
        String data = sp.getString("cityName", "全国城市");
        String city = sp.getString("city", "全国城市");
        CityBean cityBean = new CityBean();
        cityBean.setCityCode(cityCode);
        cityBean.setCityName(city);
        cityBean.setCityData(data);
        return cityBean;
    }

    public static void setUserData(Context context, CarUserEntity userEntity) {
        SharedPreferences sp = context.getSharedPreferences("userEntity", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userImg", userEntity.getUserImg());
        edit.putInt("userId", userEntity.getId());
        edit.putString("userPustCode", userEntity.getUserPustCode());
        edit.putInt("userApply", userEntity.getUserApply());
        edit.putString("userNikeName", userEntity.getUserNikeName());
        edit.putInt("carDealerId", userEntity.getCarDealerId());
        edit.putString("userPhone", userEntity.getUserPhone());
        edit.commit();
    }

    public static CarUserEntity getUserData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userEntity", Context.MODE_PRIVATE);
        CarUserEntity entity = new CarUserEntity();
        entity.setId(sp.getInt("userId",0));
        entity.setUserImg(sp.getString("userImg", ""));
        entity.setUserApply(sp.getInt("userApply", 0));
        entity.setUserPustCode(sp.getString("userPustCode", ""));
        entity.setUserNikeName(sp.getString("userNikeName", ""));
        entity.setCarDealerId(sp.getInt("carDealerId",-1));
        entity.setUserPhone(sp.getString("userPhone",""));
        return entity;
    }

    public static void setLoginData(Context context, LoginBean bean) {
        SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("phone", bean.getPhone());
        edit.putString("password", bean.getPassword());
        edit.putBoolean("isLogin", bean.isLogin());
        edit.commit();
    }

    public static LoginBean getLoginData(Context context) {
        LoginBean bean = new LoginBean();
        SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        bean.setPhone(sp.getString("phone", ""));
        bean.setPassword(sp.getString("password", ""));
        bean.setLogin(sp.getBoolean("isLogin", false));
        return bean;
    }

}
