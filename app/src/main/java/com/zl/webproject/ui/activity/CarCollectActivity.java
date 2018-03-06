package com.zl.webproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.BindDataUtils;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.utils.TagUtils;

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
 * @author zhanglei
 * @date 18/2/26
 * 车辆收藏
 */
public class CarCollectActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.car_collect_listView)
    ListView carCollectListView;
    @BindView(R.id.car_collect_trl)
    TwinklingRefreshLayout carCollectTrl;
    private int page = 1;
    private List<CarInfoEntity> mList = new ArrayList<>();
    private UniversalAdapter<CarInfoEntity> adapter;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_collect);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        carCollectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, CarDetailActivity.class);
                intent.putExtra("data", mList.get(i));
                startActivity(intent);
            }
        });
    }

    private void initData() {
        getDataList();
    }

    private void getDataList() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("page", page + "");

        HttpUtils.getInstance().Post(mActivity, params, API.getCollectionList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {

                    if (page == 1) {
                        carCollectTrl.finishRefreshing();
                        mList.clear();
                    } else {
                        carCollectTrl.finishLoadmore();
                    }

                    JSONObject object = new JSONObject(body);
                    JSONArray array = object.optJSONArray("items");
                    if (array.length() <= 0) {
                        showToast("没有更多了");
                        return;
                    }
                    for (int i = 0; i < array.length(); i++) {
                        mList.add(new Gson().fromJson(array.optString(i), CarInfoEntity.class));
                    }
                    adapter.notifyDataSetChanged();

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
        tvTitleName.setText("车辆关注");

        adapter = new UniversalAdapter<CarInfoEntity>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarInfoEntity s) {
                bindData(holder, position, s);
            }
        };
        carCollectListView.setAdapter(adapter);
        initProgress(carCollectTrl);
    }

    /**
     * 绑定数据  绑定事件
     *
     * @param holder
     * @param position
     * @param s
     */
    private void bindData(UniversalViewHolder holder, int position, CarInfoEntity s) {
        mPosition = position;
        holder.getView(R.id.linear_bottom_two).setVisibility(View.VISIBLE);
        holder.getView(R.id.linear_share).setOnClickListener(this);
        holder.getView(R.id.linear_delete).setOnClickListener(this);
        BindDataUtils.bindCarData(mActivity, holder, s);
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //分享
            case R.id.linear_share:
                share();
                break;
            //删除
            case R.id.linear_delete:
                delete();
                break;
        }
    }

    /**
     * 删除关注车辆
     */
    private void delete() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("cid", mList.get(mPosition).getId() + "");
        HttpUtils.getInstance().Post(mActivity, params, API.collectionCar, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                showToast("成功取消收藏");
                mList.remove(mPosition);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

    /**
     * 分享车辆
     */
    private void share() {

    }

}
