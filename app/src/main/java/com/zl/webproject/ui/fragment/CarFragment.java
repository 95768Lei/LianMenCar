package com.zl.webproject.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarDictionaryEntity;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.ui.activity.CarDetailActivity;
import com.zl.webproject.ui.activity.CarSearchActivity;
import com.zl.webproject.ui.activity.MessageActivity;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.ui.dialog.MoreTagDialog;
import com.zl.webproject.ui.dialog.TagDialog;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.BindDataUtils;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.OnDismissListener;
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
    @BindView(R.id.message_iv_tag)
    ImageView messageIvTag;
    @BindView(R.id.et_search_data)
    TextView etSearchData;
    @BindView(R.id.linear_qu_jian_money)
    AutoLinearLayout linearQuJianMoney;
    @BindView(R.id.linear_car_nian_xian)
    AutoLinearLayout linearCarNianXian;
    @BindView(R.id.linear_run_range)
    AutoLinearLayout linearRunRange;
    @BindView(R.id.linear_more)
    AutoLinearLayout linearMore;
    @BindView(R.id.tv_money_pai_xu)
    TextView tvMoneyPaiXu;
    @BindView(R.id.linear_money_pai_xu)
    AutoLinearLayout linearMoneyPaiXu;
    @BindView(R.id.tv_car_year_pai_xu)
    TextView tvCarYearPaiXu;
    @BindView(R.id.linear_car_year_pai_xu)
    AutoLinearLayout linearCarYearPaiXu;
    @BindView(R.id.car_run_range_pai_xu)
    TextView carRunRangePaiXu;
    @BindView(R.id.linear_run_range_pai_xu)
    AutoLinearLayout linearRunRangePaiXu;
    @BindView(R.id.tv_more_pai_xu)
    TextView tvMorePaiXu;
    @BindView(R.id.linear_more_pai_xu)
    AutoLinearLayout linearMorePaiXu;
    @BindView(R.id.linear_gong_neng2)
    AutoLinearLayout linearGongNeng2;
    private UniversalAdapter<CarInfoEntity> mAdapter;
    private List<CarInfoEntity> mList = new ArrayList<>();
    private TagDialog tagDialog;
    private MoreTagDialog moreTagDialog;
    private TagDialog tagAgeDialog;
    private TagDialog tagRunDialog;
    private Handler handler = new Handler();
    private int page = 1;
    private AddressDialog addressDialog;
    private List<CarDictionaryEntity> carMoneyList = new ArrayList<>();
    private List<CarDictionaryEntity> carYearList = new ArrayList<>();
    private List<CarDictionaryEntity> carRunList = new ArrayList<>();
    private Map<String, String> params = new HashMap<>();
    private View viewBottom;
    private TextView tvBottom;
    private String[] items = {"全部车源", "个人车源", "车行车源"};
    private AlertDialog alertDialog;

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

    @Override
    public void onResume() {
        super.onResume();
        getNoReadMessage(messageIvTag);
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
                page = 1;
                getDataList();
            }
        });
        carTrl.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        getDataList();
                    }
                }, 600);

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
        tagDialog.setOnReturnDataListener(new TagDialog.OnReturnDataListener() {
            @Override
            public void returnData(String data) {
                String priceStart = "";
                String priceEnd = "";
                if (!TextUtils.isEmpty(data)) {
                    if (data.contains("-")) {
                        String[] split = data.split(" - ");
                        priceStart = split[0].replace("万元", "");
                        priceEnd = split[1].replace("万元", "");
                    } else if (data.contains("以下")) {
                        priceStart = "0";
                        priceEnd = data.replace("万元以下", "");
                    } else {
                        priceStart = data.replace("万元以上", "");
                        priceEnd = String.valueOf(Integer.MAX_VALUE);
                    }
                }
                params.put("priceStart", priceStart);
                params.put("priceEnd", priceEnd);
                tvQuJianMoney.setText(data);
                getDataList();
            }
        });
        tagAgeDialog.setOnReturnDataListener(new TagDialog.OnReturnDataListener() {
            @Override
            public void returnData(String data) {
                String ageStart = "";
                String ageEnd = "";
                if (!TextUtils.isEmpty(data)) {
                    if (data.contains("-")) {
                        String[] split = data.split(" - ");
                        ageStart = split[0].replace("年", "");
                        ageEnd = split[1].replace("年", "");
                    } else if (data.contains("以下")) {
                        ageStart = "0";
                        ageEnd = data.replace("年以下", "");
                    } else {
                        ageStart = data.replace("年以上", "");
                        ageEnd = String.valueOf(Integer.MAX_VALUE);
                    }
                }
                params.put("ageStart", ageStart);
                params.put("ageEnd", ageEnd);
                tvCarNianXian.setText(data);
                getDataList();
            }
        });
        tagRunDialog.setOnReturnDataListener(new TagDialog.OnReturnDataListener() {
            @Override
            public void returnData(String data) {
                String mileageStart = "";
                String mileageEnd = "";
                if (!TextUtils.isEmpty(data)) {
                    if (data.contains("-")) {
                        String[] split = data.split(" - ");
                        mileageStart = split[0].replace("公里", "");
                        mileageEnd = split[1].replace("公里", "");
                    } else if (data.contains("以下")) {
                        mileageStart = "0";
                        mileageEnd = data.replace("公里以下", "");
                    } else {
                        mileageStart = data.replace("公里以上", "");
                        mileageEnd = String.valueOf(Integer.MAX_VALUE);
                    }
                }
                params.put("mileageStart", mileageStart);
                params.put("mileageEnd", mileageEnd);
                carRunRange.setText(data);
                getDataList();
            }
        });

        moreTagDialog.setOnReturnMapDataListener(new MoreTagDialog.OnReturnMapDataListener() {
            @Override
            public void returnData(Map<String, String> data) {
                Log.e("data", data.toString());
                params.putAll(data);
                getDataList();
            }
        });

        tagDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                tvQuJianMoney.setSelected(false);
            }
        });
        tagAgeDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                tvCarNianXian.setSelected(false);
            }
        });
        tagRunDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                carRunRange.setSelected(false);
            }
        });
        moreTagDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMore.setSelected(false);
            }
        });
    }

    private void initData() {
        tvCity.setText(SpUtlis.getLocationData(mActivity).getCityName());
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
        carTrl.startRefresh();
        getTagList();
    }

    private void getTagList() {
        Map<String, String> params = new HashMap<>();
        params.put("data", "");
        HttpUtils.getInstance().Post(mActivity, params, API.getRetrieval, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    JSONArray array = new JSONArray(body);
                    //价格区间
                    JSONObject object = array.optJSONObject(12);
                    JSONArray moneyType = object.optJSONArray("value");
                    List<String> moneyList = new ArrayList<String>();
                    for (int i = 0; i < moneyType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(moneyType.opt(i).toString(), CarDictionaryEntity.class);
                        carMoneyList.add(entity);
                        if (i == 0) {
                            moneyList.add(entity.getDictName() + "万元以下");
                        } else if (i == (moneyType.length() - 1)) {
                            moneyList.add(entity.getDictName() + "万元以上");
                        } else if (entity.getDictName().contains("-")) {
                            String[] split = entity.getDictName().split("-");
                            moneyList.add(split[0] + "万元 - " + split[1] + "万元");
                        } else {
                            moneyList.add(entity.getDictName());
                        }
                    }
                    tagDialog.setData(moneyList);

                    //行驶里程
                    JSONObject object2 = array.optJSONObject(11);
                    JSONArray runType = object2.optJSONArray("value");
                    List<String> runList = new ArrayList<String>();
                    for (int i = 0; i < runType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(runType.opt(i).toString(), CarDictionaryEntity.class);
                        carRunList.add(entity);
                        if (i == 0) {
                            runList.add(entity.getDictName() + "公里以下");
                        } else if (i == (runType.length() - 1)) {
                            runList.add(entity.getDictName() + "公里以上");
                        } else if (entity.getDictName().contains("-")) {
                            String[] split = entity.getDictName().split("-");
                            runList.add(split[0] + "公里 - " + split[1] + "公里");
                        } else {
                            runList.add(entity.getDictName());
                        }
                    }
                    tagRunDialog.setData(runList);

                    //车辆年限
                    JSONObject object1 = array.optJSONObject(10);
                    JSONArray ageType = object1.optJSONArray("value");
                    List<String> ageList = new ArrayList<String>();
                    for (int i = 0; i < ageType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(ageType.opt(i).toString(), CarDictionaryEntity.class);
                        carYearList.add(entity);
                        if (i == 0) {
                            ageList.add(entity.getDictName() + "年以下");
                        } else if (i == (ageType.length() - 1)) {
                            ageList.add(entity.getDictName() + "年以上");
                        } else if (entity.getDictName().contains("-")) {
                            String[] split = entity.getDictName().split("-");
                            ageList.add(split[0] + "年 - " + split[1] + "年");
                        } else {
                            ageList.add(entity.getDictName());
                        }
                    }
                    tagAgeDialog.setData(ageList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

    private void getDataList() {
        String cityCode = SpUtlis.getLocationData(mActivity).getCityCode();
        params.put("cityCode", cityCode);
        params.put("page", page + "");

        HttpUtils.getInstance().Post(mActivity, params, API.carInfoList, new HttpUtils.OnOkHttpCallback() {
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

                    if (array.length() <= 0) {
                        if (carListView.getFooterViewsCount() <= 0) {
                            tvBottom.setText("共" + mList.size() + "条车辆信息");
                            carListView.addFooterView(viewBottom);
                        }
                    } else {
                        if (carListView.getFooterViewsCount() > 0) {
                            carListView.removeFooterView(viewBottom);
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
        mAdapter = new UniversalAdapter<CarInfoEntity>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarInfoEntity s) {
                BindDataUtils.bindCarData(mActivity, holder, s);
            }
        };
        carListView.setAdapter(mAdapter);
        initProgress(carTrl);

        //初始化弹窗
        tagDialog = new TagDialog(mActivity);
        tagAgeDialog = new TagDialog(mActivity);
        tagRunDialog = new TagDialog(mActivity);
        moreTagDialog = new MoreTagDialog(mActivity);

        addressDialog = new AddressDialog(mActivity);

        viewBottom = LayoutInflater.from(mActivity).inflate(R.layout.tv_bottom_layout, null);
        tvBottom = viewBottom.findViewById(R.id.tv_bottom);

        alertDialog = new AlertDialog.Builder(mActivity).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        tvMorePaiXu.setText("全部车源");
                        tvMorePaiXu.setSelected(false);
                        break;
                    case 1:
                        tvMorePaiXu.setText("个人车源");
                        tvMorePaiXu.setSelected(true);
                        break;
                    case 2:
                        tvMorePaiXu.setText("车行车源");
                        tvMorePaiXu.setSelected(true);
                        break;
                }
                alertDialog.dismiss();
            }
        }).create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_city, R.id.fab_loop, R.id.iv_message, R.id.linear_qu_jian_money, R.id.linear_car_nian_xian,
            R.id.linear_run_range, R.id.et_search_data, R.id.linear_more, R.id.linear_money_pai_xu,
            R.id.linear_car_year_pai_xu, R.id.linear_run_range_pai_xu, R.id.linear_more_pai_xu})
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
                tvQuJianMoney.setSelected(true);
                showWindow(tagDialog);
                break;
            //车辆年限
            case R.id.linear_car_nian_xian:
                tvCarNianXian.setSelected(true);
                showWindow(tagAgeDialog);
                break;
            //行驶里程
            case R.id.linear_run_range:
                carRunRange.setSelected(true);
                showWindow(tagRunDialog);
                break;
            //更多检索
            case R.id.linear_more:
                tvMore.setSelected(true);
                showMoreWindow();
                break;
            //进入车辆搜索
            case R.id.et_search_data:
                startActivity(new Intent(mActivity, CarSearchActivity.class));
                break;
            case R.id.linear_money_pai_xu:
                tvMoneyPaiXu.setSelected(!tvMoneyPaiXu.isSelected());
                if (tvMoneyPaiXu.isSelected()) {
                    tvMoneyPaiXu.setText("价格最低");
                } else {
                    tvMoneyPaiXu.setText("价格最高");
                }
                break;
            case R.id.linear_car_year_pai_xu:
                tvCarYearPaiXu.setSelected(!tvCarYearPaiXu.isSelected());
                if (tvCarYearPaiXu.isSelected()) {
                    tvCarYearPaiXu.setText("年限最低");
                } else {
                    tvCarYearPaiXu.setText("年限最高");
                }
                break;
            case R.id.linear_run_range_pai_xu:
                carRunRangePaiXu.setSelected(!carRunRangePaiXu.isSelected());
                if (carRunRangePaiXu.isSelected()) {
                    carRunRangePaiXu.setText("里程最高");
                } else {
                    carRunRangePaiXu.setText("里程最低");
                }
                break;
            case R.id.linear_more_pai_xu:
                alertDialog.show();
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

        if (tagDialog.isShow() || tagRunDialog.isShow() || tagAgeDialog.isShow()) {
            tagDialog.dismiss();
            tagRunDialog.dismiss();
            tagAgeDialog.dismiss();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    moreTagDialog.showDialog(linearGongNeng);
                }
            }, 300);
        } else {
            moreTagDialog.showDialog(linearGongNeng);
        }


    }

    private void showWindow(final TagDialog dialog) {
        if (dialog.isShow()) {
            dialog.dismiss();
            return;
        }

        if (tagDialog.isShow() || tagRunDialog.isShow() || tagAgeDialog.isShow() || moreTagDialog.isShow()) {
            tagDialog.dismiss();
            tagRunDialog.dismiss();
            tagAgeDialog.dismiss();
            moreTagDialog.dismiss();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.showDialog(linearGongNeng);
                }
            }, 300);
        } else {
            dialog.showDialog(linearGongNeng);
        }


    }
}
