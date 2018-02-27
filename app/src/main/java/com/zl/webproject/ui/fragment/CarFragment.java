package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.ui.activity.CarDetailActivity;
import com.zl.webproject.ui.activity.MessageActivity;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.ui.dialog.MoreTagDialog;
import com.zl.webproject.ui.dialog.TagDialog;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * @author zhanglei
 * @date 18/2/22
 */
public class CarFragment extends BaseFragment {


    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.car_listView)
    ListView carListView;
    @BindView(R.id.car_trl)
    TwinklingRefreshLayout carTrl;
    Unbinder unbinder;
    @BindView(R.id.fab_loop)
    FloatingActionButton fabLoop;
    @BindView(R.id.tv_qu_jian_money)
    TextView tvQuJianMoney;
    @BindView(R.id.tv_car_nian_xian)
    TextView tvCarNianXian;
    @BindView(R.id.car_run_range)
    TextView carRunRange;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.linear_gong_neng)
    AutoLinearLayout linearGongNeng;
    private UniversalAdapter<CarInfoEntity> mAdapter;
    private List<CarInfoEntity> mList = new ArrayList<>();
    private TagDialog tagDialog;
    private MoreTagDialog moreTagDialog;
    private TagDialog tagNianDialog;
    private TagDialog tagRunDialog;
    private Handler handler = new Handler();
    private int page = 1;
    private AddressDialog addressDialog;

    public CarFragment() {
        // Required empty public constructor
    }

    public static CarFragment newInstance() {

        Bundle args = new Bundle();

        CarFragment fragment = new CarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, CarDetailActivity.class);
                intent.putExtra("data", mList.get(i));
                startActivity(intent);
            }
        });

        addressDialog.setOnAddressDataListener(new AddressDialog.OnAddressDataListener() {
            @Override
            public void addressData(CityBean cityBean) {
                tvCity.setText(cityBean.getCityName());
                addressDialog.dismissDialog();
                getDataList();
            }
        });
        carTrl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getDataList();
            }
        });
    }

    private void initData() {
        tvCity.setText(SpUtlis.getLocationData(mActivity).getCityName());
        getDataList();
    }

    private void getDataList() {
        String cityCode = SpUtlis.getLocationData(mActivity).getCityCode();
        Map<String, String> params = new HashMap<>();
        params.put("cityCode", cityCode);
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
        params.put("page", page + "");

        HttpUtils.getInstance().POST(mActivity, new JSONObject(params).toString(), API.carInfoList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {

                    if (page == 1) {
                        carTrl.finishRefreshing();
                        stopLoop(fabLoop);
                        mList.clear();
                    } else {
                        carTrl.finishLoadmore();
                    }

                    JSONObject object = new JSONObject(body);
                    JSONArray array = object.optJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        mList.add(new Gson().fromJson(array.optString(i), CarInfoEntity.class));
                    }
                    mAdapter.notifyDataSetChanged();
                    if (page == 1) {
                        carListView.setSelection(0);
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
        carListView.setAdapter(mAdapter);
        initProgress(carTrl);

        //初始化弹窗
        tagDialog = new TagDialog(mActivity);
        tagNianDialog = new TagDialog(mActivity);
        tagRunDialog = new TagDialog(mActivity);
        moreTagDialog = new MoreTagDialog(mActivity);

        addressDialog = new AddressDialog(mActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_city, R.id.fab_loop, R.id.iv_message, R.id.linear_qu_jian_money, R.id.linear_car_nian_xian, R.id.linear_run_range, R.id.linear_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择城市
            case R.id.tv_city:
                addressDialog.showDialog(view);
                break;
            //进入消息中心
            case R.id.iv_message:
                startActivity(new Intent(mActivity, MessageActivity.class));
                break;
            //刷新数据
            case R.id.fab_loop:
                updateData();
                break;
            //价格区间
            case R.id.linear_qu_jian_money:
                showWindow(tagDialog);
                break;
            //车辆年限
            case R.id.linear_car_nian_xian:
                showWindow(tagNianDialog);
                break;
            //行驶里程
            case R.id.linear_run_range:
                showWindow(tagRunDialog);
                break;
            //更多检索
            case R.id.linear_more:
                showMoreWindow();
                break;
        }
    }

    private void updateData() {
        startLoop(fabLoop);
        page = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataList();
            }
        }, 400);
    }

    private void showMoreWindow() {

        if (moreTagDialog.isShow()) {
            moreTagDialog.dismiss();
            return;
        }

        tagDialog.dismiss();
        tagRunDialog.dismiss();
        tagNianDialog.dismiss();

        if (tagDialog.isShow() || tagRunDialog.isShow() || tagNianDialog.isShow()) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    moreTagDialog.showDialog(linearGongNeng);
                }
            }, 400);
        } else {
            moreTagDialog.showDialog(linearGongNeng);
        }

    }

    private void showWindow(final TagDialog dialog) {
        if (dialog.isShow()) {
            dialog.dismiss();
            return;
        }

        tagDialog.dismiss();
        tagRunDialog.dismiss();
        tagNianDialog.dismiss();

        if (tagDialog.isShow() || tagRunDialog.isShow() || tagNianDialog.isShow()) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.showDialog(linearGongNeng);
                }
            }, 400);
        } else {
            dialog.showDialog(linearGongNeng);
        }


    }
}
