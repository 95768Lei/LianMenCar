package com.zl.webproject.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanglei on 2017/4/14.
 * 图片加载类
 */

public class ImageLoader {

    public static void loadImageUrl(Activity activity, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) return;

//        Glide.with(activity)
//                .load(API.BASEURL + url)
//                .crossFade()
//                .into(imageView);

        Glide.with(activity)
                .load("http://app.tzlm.cc/" + url)
                .crossFade()
                .into(imageView);
    }

    public static void loadImageUrl(Fragment activity, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) return;
//        Glide.with(activity)
//                .load(API.BASEURL + url)
//                .crossFade()
//                .into(imageView);
        Glide.with(activity)
                .load("http://app.tzlm.cc/" + url)
                .crossFade()
                .into(imageView);
    }

    public static void loadImageUrl(Context activity, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) return;
//        Glide.with(activity)
//                .load(API.BASEURL + url)
//                .crossFade()
//                .into(imageView);
        Glide.with(activity)
                .load("http://app.tzlm.cc/" + url)
                .crossFade()
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param imagePath
     * @param image
     */
    public static void loadImageFile(Activity activity, final String imagePath, final ImageView image) {
        //本地文件
        File file = new File(imagePath);
        Glide.with(activity).load(file).into(image);
//        Observable.just(imagePath)
//                .map(new Function<String, Bitmap>() {
//                    @Override
//                    public Bitmap apply(@NonNull String s) throws Exception {
//                        return ImageFactory.getSimpeImage(imagePath);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Bitmap>() {
//                    @Override
//                    public void accept(@NonNull Bitmap bitmap) throws Exception {
//                        image.setImageBitmap(bitmap);
//                    }
//                });
    }

}
