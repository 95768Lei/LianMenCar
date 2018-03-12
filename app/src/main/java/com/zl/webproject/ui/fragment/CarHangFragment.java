package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarDealerEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.ui.activity.CarHangDetailActivity;
import com.zl.webproject.ui.activity.MessageActivity;
import com.zl.webproject.ui.dialog.AddressDialog;
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
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * @author zhanglei
 * @date 18/2/22
 */
public class CarHangFragment extends BaseFragment {


    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.car_hang_listView)
    ListView carHangListView;
    @BindView(R.id.car_hang_trl)
    TwinklingRefreshLayout carHangTrl;
    Unbinder unbinder;
    @BindView(R.id.et_search_data)
    EditText etSearchData;
    @BindView(R.id.fab_loop)
    FloatingActionButton fabLoop;
    @BindView(R.id.message_iv_tag)
    ImageView messageIvTag;
    private UniversalAdapter<CarDealerEntity> mAdapter;
    private List<CarDealerEntity> mList = new ArrayList<>();
    private int page = 1;
    private AddressDialog addressDialog;
    private View viewBottom;
    private TextView tvBottom;

    public CarHangFragment() {
        // Required empty public constructor
    }

    public static CarHangFragment newInstance() {

        Bundle args = new Bundle();

        CarHangFragment fragment = new CarHangFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_hang, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getNoReadMessage(messageIvTag);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            CityBean locationData = SpUtlis.getLocationData(mActivity);
            tvCity.setText(locationData.getCityName());
        }
    }

    private void initListener() {
        carHangListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, CarHangDetailActivity.class);
                intent.putExtra("did", mList.get(i).getId());
                startActivity(intent);
            }
        });
        addressDialog.setOnAddressDataListener(new AddressDialog.OnAddressDataListener() {
            @Override
            public void addressData(CityBean cityBean) {
                tvCity.setText(cityBean.getCityName());
                addressDialog.dismissDialog();
                page = 1;
                getListData();
            }
        });
        carHangTrl.setOnRefreshListener(new RefreshListenerAdapter() {
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
        CityBean locationData = SpUtlis.getLocationData(mActivity);
        final Map<String, String> params = new HashMap<>();
        params.put("keyWord", "");
        params.put("cityCode", locationData.getCityCode());
        params.put("page", page + "");

        HttpUtils.getInstance().Post(mActivity, params, API.getCarDealerList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    if (page == 1) {
                        mList.clear();
                        stopLoop(fabLoop);
                        carHangTrl.finishRefreshing();
                    } else {
                        carHangTrl.finishLoadmore();
                    }
                    JSONObject object = new JSONObject(body);
                    JSONArray array = object.optJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        CarDealerEntity carDealerEntity = new Gson().fromJson(array.optString(i), CarDealerEntity.class);
                        mList.add(carDealerEntity);
                    }
                    mAdapter.notifyDataSetChanged();

                    if (page == 1) {
                        carHangListView.setSelection(0);
                    }

                    if (array.length() <= 0) {
                        if (carHangListView.getFooterViewsCount() <= 0) {
                            tvBottom.setText("共" + mList.size() + "条车行信息");
                            carHangListView.addFooterView(viewBottom);
                        }
                    } else {
                        if (carHangListView.getFooterViewsCount() > 0) {
                            carHangListView.removeFooterView(viewBottom);
                        }
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
        mAdapter = new UniversalAdapter<CarDealerEntity>(mActivity, mList, R.layout.car_hang_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarDealerEntity s) {
                holder.setText(R.id.tv_car_hang_name, s.getDealerName());
                holder.setText(R.id.tv_car_data, s.getCity().getCityName());
                holder.setText(R.id.tv_car_hang_phone, "联系电话：" + s.getDealerPhone());
                holder.setText(R.id.tv_car_hang_data, "简述：" + s.getDealerContext());
                holder.setImageUrl(mActivity, R.id.iv_car_hang_icon, s.getDealerImg());
            }
        };
        carHangListView.setAdapter(mAdapter);
        initProgress(carHangTrl);

        addressDialog = new AddressDialog(mActivity);

        viewBottom = LayoutInflater.from(mActivity).inflate(R.layout.tv_bottom_layout, null);
        tvBottom = viewBottom.findViewById(R.id.tv_bottom);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_city, R.id.iv_message, R.id.fab_loop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_city:
                addressDialog.showDialog(view);
                break;
            case R.id.iv_message:
                startActivity(new Intent(mActivity, MessageActivity.class));
                break;
            case R.id.fab_loop:
                refreshData();
                break;
        }
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        page = 1;
        startLoop(fabLoop);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getListData();
            }
        }, 800);
    }
}
