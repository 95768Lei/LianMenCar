package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.RecyclerAdapter;
import com.zl.webproject.base.ViewHolder;
import com.zl.webproject.model.CarServerEntity;
import com.zl.webproject.ui.activity.SendServiceActivity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.view.MRecyclerView;

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
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * @author zhanglei
 */
public class ServiceListFragment extends BaseFragment {


    @BindView(R.id.service_list_mrv)
    MRecyclerView serviceListMrv;
    @BindView(R.id.fab_loop)
    FloatingActionButton fabLoop;
    @BindView(R.id.service_list_srl)
    SwipeRefreshLayout serviceListSrl;
    Unbinder unbinder;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.null_arl)
    AutoRelativeLayout nullArl;
    private List<CarServerEntity> mList = new ArrayList<>();
    private RecyclerAdapter<CarServerEntity> mAdapter;
    private int page = 1;
    private int type;

    public ServiceListFragment() {
        // Required empty public constructor
    }

    public static ServiceListFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        ServiceListFragment fragment = new ServiceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initData() {
        type = getArguments().getInt("type", -1);
        getListData();
    }

    private void getListData() {
        if (type == -1) return;

        Map<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("page", page + "");
        HttpUtils.getInstance().Post(mActivity, params, API.getServerList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body);
                try {
                    stopLoop(fabLoop);
                    if (page == 1) {
                        mList.clear();
                        serviceListSrl.setRefreshing(false);
                    }
                    JSONObject object = new JSONObject(body);
                    JSONArray array = object.optJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        CarServerEntity carDealerEntity = new Gson().fromJson(array.optString(i), CarServerEntity.class);
                        mList.add(carDealerEntity);
                    }
                    mAdapter.notifyDataSetChanged();

                    if (page == 1) {
                        serviceListMrv.scrollToPosition(0);
                    }

                    if (mList.size() <= 0) {
                        nullArl.setVisibility(View.VISIBLE);
                    } else {
                        nullArl.setVisibility(View.GONE);
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
        mAdapter = new RecyclerAdapter<CarServerEntity>(mActivity, mList, R.layout.car_hang_list_item) {
            @Override
            protected void convert(ViewHolder holder, CarServerEntity s, int position) {
                holder.setText(R.id.tv_car_hang_name, s.getSerTitle());
                holder.setText(R.id.tv_car_data, s.getCity().getCityName());
                holder.setText(R.id.tv_car_hang_phone, "联系电话：" + s.getCarUser().getUserPhone());
                holder.setText(R.id.tv_car_hang_data, "简述：" + s.getSerContext());
                holder.setImageUrl(mActivity, R.id.iv_car_hang_icon, s.getSerImg());
//                holder.setText(R.id.carForWard, s.getDealerForWard() + "");
//                holder.setText(R.id.tv_carBrowse, s.getDealerBrowse() + "");
            }
        };
        serviceListSrl.setColorSchemeResources(R.color.colorPrimary);
        serviceListMrv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        serviceListMrv.setAdapter(mAdapter);
        tvAction.setText("去发布");
    }

    private void initListener() {
        serviceListSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getListData();
            }
        });
        serviceListMrv.setOnBottomListener(new MRecyclerView.OnBottomListener() {
            @Override
            public void onBottom() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_action)
    public void onViewClicked() {
        startActivity(new Intent(mActivity, SendServiceActivity.class));
    }
}
