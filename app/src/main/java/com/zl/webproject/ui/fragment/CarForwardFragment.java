package com.zl.webproject.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarForwardEntity;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.ui.activity.CarDetailActivity;
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
 * Created by Administrator on 2018/3/2.
 */

/**
 * @author zhanglei
 * @date 18/2/28
 * 车辆列表
 */
public class CarForwardFragment extends BaseFragment {


    @BindView(R.id.car_share_listView)
    ListView carShareListView;
    Unbinder unbinder;
    @BindView(R.id.car_share_trl)
    TwinklingRefreshLayout carShareTrl;
    private UniversalAdapter<CarForwardEntity> mAdapter;
    private List<CarForwardEntity> mList = new ArrayList<>();
    private int page = 1;
    private String url = API.carInfoList;

    public CarForwardFragment() {
        // Required empty public constructor
    }

    /**
     * @return
     */
    public static CarForwardFragment newInstance() {

        Bundle args = new Bundle();

        CarForwardFragment fragment = new CarForwardFragment();
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
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        getListData();
                    }
                }, 600);
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

        carShareListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, CarDetailActivity.class);
                intent.putExtra("data", mList.get(i).getCarInfoEntity());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        carShareTrl.startRefresh();
    }

    private void getListData() {

        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");

        HttpUtils.getInstance().Post(mActivity, params, API.getForwardList, new HttpUtils.OnOkHttpCallback() {
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
                        mList.add(new Gson().fromJson(array.optString(i), CarForwardEntity.class));
                    }
                    mAdapter.notifyDataSetChanged();
                    if (page == 1) {
                        carShareListView.setSelection(0);
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
        mAdapter = new UniversalAdapter<CarForwardEntity>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarForwardEntity entity) {
                CarInfoEntity s = entity.getCarInfoEntity();
                Integer carSource = s.getCarSource();
                ImageView ivCarTag = holder.getView(R.id.iv_car_tag);
                if (carSource == 0) {
                    ivCarTag.setImageResource(R.mipmap.geren);
                } else {
                    ivCarTag.setImageResource(R.mipmap.hang);
                }
                holder.setText(R.id.tv_car_name, s.getCarTitle());
                holder.setText(R.id.tv_car_money, s.getCarPrice() + "万");
                holder.setText(R.id.tv_car_city, s.getCarAreaCitysEntity().getCityName());
                holder.setText(R.id.tv_car_tag, s.getLabel().getDictName());
                holder.setText(R.id.tv_car_data, s.getCarLicensingDateStr() + "/" + s.getCarMileage() + "公里");
                holder.setImageUrl(mActivity, R.id.iv_car_icon, s.getCarImg());
                holder.setText(R.id.carForWard, s.getCarForWard() + "");
                holder.setText(R.id.tv_carBrowse, s.getCarBrowse() + "");
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
