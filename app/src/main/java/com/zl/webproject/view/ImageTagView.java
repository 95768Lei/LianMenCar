package com.zl.webproject.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zl.webproject.R;
import com.zl.webproject.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ImageTagView extends LinearLayout {

    private List<Integer> images = new ArrayList<>();

    public ImageTagView(Context context) {
        super(context);
    }

    public ImageTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addImage(int drawableId) {
        for (int i = 0; i < this.getChildCount(); i++) {
            removeView(this.getChildAt(i));
        }
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(drawableId);
        LayoutParams params = new LayoutParams(60, 60);
        params.setMargins(8, 0, 0, 0);
        addView(imageView, params);
    }

    public void addImage(List<Integer> images) {
        for (int i = 0; i < this.getChildCount(); i++) {
            removeView(this.getChildAt(i));
        }
        for (Integer image : images) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(image);
            LayoutParams params = new LayoutParams(60, 60);
            params.setMargins(8, 0, 0, 0);
            addView(imageView, params);
        }
    }

    public void addUrl(String imageUrl) {
        ImageView imageView = new ImageView(getContext());
        ImageLoader.loadImageUrl(getContext(), imageUrl, imageView);
        LayoutParams params = new LayoutParams(30, 30);
        params.setMargins(8, 0, 0, 0);
        addView(imageView, params);
    }
}
