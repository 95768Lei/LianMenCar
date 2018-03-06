package com.zl.webproject.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.zl.webproject.model.CarResourceEntity;
import com.zl.webproject.utils.ImageLoader;

/**
 * Created by Administrator on 2018\3\6 0006.
 */

public class LocalImageHolderView implements Holder<CarResourceEntity> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, CarResourceEntity data) {
        ImageLoader.loadImageUrl(context, data.getResUrl(), imageView);
    }
}
