package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarDealerEntity;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.BindDataUtils;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SpUtlis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * 搜索结果页
 */
public class CarSearchDataActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.car_search_listView)
    ListView carSearchListView;
    @BindView(R.id.car_search_trl)
    TwinklingRefreshLayout carSearchTrl;
    private String searchData;
    private int page = 1;
    private List<CarInfoEntity> mList = new ArrayList<>();
    private UniversalAdapter<CarInfoEntity> mAdapter;
    private View viewBottom;
    private TextView tvBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search_data);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        carSearchTrl.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getDataList();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataList();
                    }
                }, 800);

            }
        });
    }

    private void initData() {
        searchData = getIntent().getStringExtra("searchData");
        getDataList();
    }


    private void getDataList() {
        Map<String, String> params = new HashMap<>();
        if (TextUtils.isEmpty(searchData)) {
            showToast("搜索内容不能为空");
            return;
        }
        params.put("keywold", searchData);
        params.put("displacementStart", "");
        params.put("displacementEnd", "");
        params.put("ageStart", "");
        params.put("ageEnd", "");
        params.put("mileageStart", "");
        params.put("mileageEnd", "");
        params.put("priceStart", "");
        params.put("priceEnd", "");
        params.put("lv", "");
        params.put("fuel", "");
        params.put("label", "");
        params.put("gearbox", "");

        HttpUtils.getInstance().Post(mActivity, params, API.carInfoList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {

                    if (page == 1) {
                        carSearchTrl.finishRefreshing();
                        mList.clear();
                    } else {
                        carSearchTrl.finishLoadmore();
                    }

                    JSONObject object = new JSONObject(body);
                    JSONArray array = object.optJSONArray("items");

                    for (int i = 0; i < array.length(); i++) {
                        mList.add(new Gson().fromJson(array.optString(i), CarInfoEntity.class));
                    }
                    mAdapter.notifyDataSetChanged();
                    if (page == 1) {
                        carSearchListView.setSelection(0);
                    }

                    if (array.length() <= 0) {
                        if (carSearchListView.getFooterViewsCount() <= 0) {
                            tvBottom.setText("共" + mList.size() + "条车辆信息");
                            carSearchListView.addFooterView(viewBottom);
                        }
                    } else {
                        if (carSearchListView.getFooterViewsCount() > 0) {
                            carSearchListView.removeFooterView(viewBottom);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });
    }

    private void initView() {
        tvTitleName.setText("车辆搜索");
        mAdapter = new UniversalAdapter<CarInfoEntity>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarInfoEntity s) {
                BindDataUtils.bindCarData(mActivity, holder, s);
            }
        };
        carSearchListView.setAdapter(mAdapter);
        initProgress(carSearchTrl);
        viewBottom = LayoutInflater.from(mActivity).inflate(R.layout.tv_bottom_layout, null);
        tvBottom = viewBottom.findViewById(R.id.tv_bottom);
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }
}
