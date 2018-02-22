package com.zl.webproject.utils;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by zhanglei on 2017\12\8 0008.
 */

public class ShareUtils {

    public static void share(final Activity context, String imageUrl, String webUrl, final OnShareListener onShareListener) {
        UMImage thumb = new UMImage(context, imageUrl);
        UMWeb web = new UMWeb(webUrl);
        web.setTitle("货车多应用分享");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("这里有一款好的软件分享给您，请点击查看");//描述
        new ShareAction(context)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        if (onShareListener != null) {
                            onShareListener.shareSuccess(share_media);
                        }
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        if (onShareListener != null) {
                            onShareListener.shareError(share_media, throwable);
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                })
                .open();
    }

    /**
     * 分享的回调监听
     */
    public interface OnShareListener {
        void shareSuccess(SHARE_MEDIA share_media);

        void shareError(SHARE_MEDIA share_media, Throwable throwable);
    }

}
