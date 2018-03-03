package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.TagEntity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.view.WrapLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.et_search)
    EditText etSearch;
    private String[] myData1 = {"奥迪", "宝马", "特斯拉", "奔驰", "大众", "雪佛兰", "东风日产"};
    private List<String> hotCarList;
    private List<String> hotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initListener() {
        wrapHotCar.setMarkClickListener(new WrapLayout.MarkClickListener() {
            @Override
            public void clickMark(int position) {
                String s = hotCarList.get(position);
                etSearch.setText(s);
            }
        });
        wrapHot.setMarkClickListener(new WrapLayout.MarkClickListener() {
            @Override
            public void clickMark(int position) {
                String s = hotCarList.get(position);
                etSearch.setText(s);
            }
        });
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("data", "");
        HttpUtils.getInstance().Post(mActivity, params, API.getHot, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    JSONArray array = new JSONArray(body);
                    hotCarList = new ArrayList<String>();
                    for (int i = 0; i < array.length(); i++) {
                        hotCarList.add(new Gson().fromJson(array.optString(i), TagEntity.class).getDictName());
                    }
                    wrapHotCar.setData(hotCarList, mActivity, 12, 10, 6, 10, 6, 6, 6, 6, 6);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });
        HttpUtils.getInstance().Post(mActivity, params, API.getHotKey, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    JSONArray array = new JSONArray(body);
                    hotList = new ArrayList<String>();
                    for (int i = 0; i < array.length(); i++) {
                        hotList.add(new Gson().fromJson(array.optString(i), TagEntity.class).getDictName());
                    }
                    wrapHot.setData(hotList, mActivity, 12, 10, 6, 10, 6, 6, 6, 6, 6);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                finish();
                break;
            case R.id.tv_search:
                search();
                break;
        }
    }

    private void search() {
        String searchData = etSearch.getText().toString().trim();

    }
}
