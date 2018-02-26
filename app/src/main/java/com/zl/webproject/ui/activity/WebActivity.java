package com.zl.webproject.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zl.webproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends Activity {

    @BindView(R.id.main_web)
    WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        url = getIntent().getStringExtra("url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        //设置在本应用内打开网页
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.addJavascriptInterface(new JavaInterface(), "android");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    private class JavaInterface {
        /**
         * 吊起分享功能
         */
        @JavascriptInterface
        public void finish() {
            WebActivity.this.finish();
        }
    }
}
