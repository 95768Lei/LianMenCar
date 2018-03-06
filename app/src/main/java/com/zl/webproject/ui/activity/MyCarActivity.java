package com.zl.webproject.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
 * 我的车源
 */
public class MyCarActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.my_car_listView)
    ListView myCarListView;
    @BindView(R.id.my_car_trl)
    TwinklingRefreshLayout myCarTrl;
    private int page = 1;
    private List<CarInfoEntity> mList = new ArrayList<>();
    private UniversalAdapter<CarInfoEntity> adapter;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        myCarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, EditCarActivity.class);
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

        HttpUtils.getInstance().Post(mActivity, params, API.carInfoListToMy, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    if (page == 1) {
                        myCarTrl.finishRefreshing();
                        mList.clear();
                    } else {
                        myCarTrl.finishLoadmore();
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
        tvTitleName.setText("我的车源");

        adapter = new UniversalAdapter<CarInfoEntity>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarInfoEntity s) {
                bindData(holder, position, s);
            }
        };
        myCarListView.setAdapter(adapter);
        initProgress(myCarTrl);
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
        holder.getView(R.id.linear_bottom).setVisibility(View.VISIBLE);
        holder.getView(R.id.linear_yu_ding).setOnClickListener(this);
        holder.getView(R.id.linear_yi_shou).setOnClickListener(this);
        holder.getView(R.id.linear_xia_jia).setOnClickListener(this);
        holder.getView(R.id.linear_fen_xiang).setOnClickListener(this);
        BindDataUtils.bindCarData(mActivity, holder, s);
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_yu_ding:
                yuDing();
                break;
            case R.id.linear_yi_shou:
                yiShou();
                break;
            case R.id.linear_xia_jia:
                xiaJia();
                break;
            case R.id.linear_fen_xiang:
                share();
                break;
        }
    }

    //分享
    private void share() {

    }

    //下架
    private void xiaJia() {
        new AlertDialog.Builder(mActivity).setMessage("是否将车辆下架").setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateState(3);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

    }

    //已售
    private void yiShou() {
        new AlertDialog.Builder(mActivity).setMessage("是否将车辆标记为已售").setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateState(2);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    //预定
    private void yuDing() {
        new AlertDialog.Builder(mActivity).setMessage("是否将车辆标记为预定").setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateState(1);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    private void updateState(final int type) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("cid", mList.get(mPosition).getId() + "");
        params.put("state", type + "");
        HttpUtils.getInstance().Post(mActivity, params, API.editCarStateInfo, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                switch (type) {
                    case 1:
                        showToast("车辆成功标记为预定");
                        break;
                    case 2:
                        showToast("车辆成功标记为已售");
                        break;
                    case 3:
                        showToast("车辆下架成功");
                        break;
                }
                mList.get(mPosition).setCarState(type);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

}
