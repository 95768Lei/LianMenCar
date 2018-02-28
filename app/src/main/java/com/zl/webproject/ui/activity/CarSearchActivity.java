package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.view.WrapLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * @author zhanglei
 * @date 18/2/28
 */
public class CarSearchActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.wrap_hot_car)
    WrapLayout wrapHotCar;
    @BindView(R.id.wrap_hot)
    WrapLayout wrapHot;
    private String[] myData1 = {"奥迪", "宝马", "特斯拉", "奔驰", "大众", "雪佛兰", "东风日产"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search);
        ButterKnife.bind(this);
        wrapHot.setData(myData1, this, 12, 10, 6, 10, 6, 6, 6, 6, 6);
        wrapHotCar.setData(myData1, this, 12, 10, 6, 10, 6, 6, 6, 6, 6);
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        HttpUtils.getInstance().Post(mActivity, params, API.getHot, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body);
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });
        HttpUtils.getInstance().Post(mActivity, params, API.getHotKey, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body);
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_search:
                break;
        }
    }
}
