package com.zl.webproject.ui.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.utils.API;
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
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * @author zhanglei
 * @date 18/2/28
 * 车辆列表
 */
public class CarListFragment extends BaseFragment {


    @BindView(R.id.car_share_listView)
    ListView carShareListView;
    Unbinder unbinder;
    @BindView(R.id.car_share_trl)
    TwinklingRefreshLayout carShareTrl;
    private UniversalAdapter<CarInfoEntity> mAdapter;
    private List<CarInfoEntity> mList = new ArrayList<>();
    private int page = 1;
    private String did;

    public CarListFragment() {
        // Required empty public constructor
    }

    /**
     * @param
     * @return
     */
    public static CarListFragment newInstance(String did) {

        Bundle args = new Bundle();
        args.putString("did", did);
        CarListFragment fragment = new CarListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setClickable(true);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        carShareTrl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getListData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getListData();
            }
        });
    }

    private void initData() {
        did = getArguments().getString("did", "0");
        getListData();
    }

    private void getListData() {

        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("cityCode", "");
        params.put("did", did);
        HttpUtils.getInstance().Post(mActivity, params, API.getCarList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    if (page == 1) {
                        carShareTrl.finishRefreshing();
                        mList.clear();
                    } else {
                        carShareTrl.finishLoadmore();
                    }

                    JSONObject object = new JSONObject(body);
                    JSONArray array = object.optJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        mList.add(new Gson().fromJson(array.optString(i), CarInfoEntity.class));
                    }
                    mAdapter.notifyDataSetChanged();
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
        mAdapter = new UniversalAdapter<CarInfoEntity>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarInfoEntity s) {
                holder.setText(R.id.tv_car_name, s.getCarTitle());
                holder.setText(R.id.tv_car_money, s.getCarPrice() + "万");
                holder.setText(R.id.tv_car_city, s.getCarAreaCitysEntity().getCityName());
                holder.setText(R.id.tv_car_tag, s.getLabel().getDictName());
                holder.setText(R.id.tv_car_data, s.getCarLicensingDateStr() + "/" + s.getCarMileage() + "公里");
                holder.setImageUrl(mActivity, R.id.iv_car_icon, s.getCarImg());
            }
        };
        carShareListView.setAdapter(mAdapter);
        initProgress(carShareTrl);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
