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
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarDealerEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SpUtlis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import retrofit2.http.HTTP;

/**
 * @author zhanglei
 * @date 18/2/26
 * 车行收藏
 */
public class CarHangCollectActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.car_hang_collect_listView)
    ListView carHangCollectListView;
    @BindView(R.id.car_hang_collect_trl)
    TwinklingRefreshLayout carHangCollectTrl;
    private List<CarDealerEntity> mList = new ArrayList<>();
    private UniversalAdapter<CarDealerEntity> adapter;
    private int page = 1;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hang_collect);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        carHangCollectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, CarHangDetailActivity.class);
                intent.putExtra("did", mList.get(i).getId());
                startActivity(intent);
            }
        });
        carHangCollectTrl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getListData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getListData();
                    }
                }, 800);
            }
        });
    }


    private void initData() {
        getListData();
    }

    private void getListData() {
        final Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("page", page + "");

        HttpUtils.getInstance().Post(mActivity, params, API.getFollowList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    if (page == 1) {
                        mList.clear();
                        carHangCollectTrl.finishRefreshing();
                    } else {
                        carHangCollectTrl.finishLoadmore();
                    }
                    JSONObject object = new JSONObject(body);
                    JSONArray array = object.optJSONArray("items");
                    if (array.length() <= 0) {
                        showToast("没有更多了");
                        return;
                    }
                    for (int i = 0; i < array.length(); i++) {
                        CarDealerEntity carDealerEntity = new Gson().fromJson(array.optString(i), CarDealerEntity.class);
                        mList.add(carDealerEntity);
                    }
                    adapter.notifyDataSetChanged();

                    if (page == 1) {
                        carHangCollectListView.setSelection(0);
                    }

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

    private void initView() {
        tvTitleName.setText("车行收藏");

        adapter = new UniversalAdapter<CarDealerEntity>(mActivity, mList, R.layout.car_hang_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarDealerEntity s) {
                bindData(holder, position, s);
            }
        };
        carHangCollectListView.setAdapter(adapter);
        initProgress(carHangCollectTrl);
    }

    /**
     * 绑定数据  绑定事件
     *
     * @param holder
     * @param position
     * @param s
     */
    private void bindData(UniversalViewHolder holder, int position, CarDealerEntity s) {
        mPosition = position;
        holder.getView(R.id.linear_bottom_two).setVisibility(View.VISIBLE);
        holder.getView(R.id.linear_share).setOnClickListener(this);
        holder.getView(R.id.linear_delete).setOnClickListener(this);
        holder.setText(R.id.tv_car_hang_name, s.getDealerName());
        holder.setText(R.id.tv_car_data, s.getCity().getCityName());
        holder.setText(R.id.tv_car_hang_phone, "联系电话：" + s.getDealerPhone());
        holder.setText(R.id.tv_car_hang_data, "简述：" + s.getDealerContext());
        holder.setImageUrl(mActivity, R.id.iv_car_hang_icon, s.getDealerImg());
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
     * 删除收藏
     */
    private void delete() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("did", mList.get(mPosition).getId() + "");
        HttpUtils.getInstance().Post(mActivity, params, API.followDetails, new HttpUtils.OnOkHttpCallback() {
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
     * 分享车行
     */
    private void share() {

    }
}
