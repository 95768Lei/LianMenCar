package com.zl.webproject.ui.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.ui.activity.CarDetailActivity;
import com.zl.webproject.ui.activity.CarSearchActivity;
import com.zl.webproject.ui.activity.MessageActivity;
import com.zl.webproject.ui.activity.SendCarActivity;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.LocationUtils;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.view.MyGridView;
import com.zl.webproject.view.MyListView;

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
public class HomeFragment extends BaseFragment {


    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.home_banner)
    ConvenientBanner homeBanner;
    @BindView(R.id.home_grid)
    MyGridView homeGrid;
    @BindView(R.id.home_listView)
    MyListView homeListView;
    @BindView(R.id.home_trl)
    TwinklingRefreshLayout homeTrl;
    Unbinder unbinder;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.fab_loop)
    FloatingActionButton fabLoop;
    @BindView(R.id.home_scroll)
    ScrollView homeScroll;

    private UniversalAdapter<String> gAdapter;
    private UniversalAdapter<CarInfoEntity> mAdapter;
    private List<CarInfoEntity> mList = new ArrayList<>();
    private List<String> gList = new ArrayList<>();
    private List<Integer> ivList = new ArrayList<>();
    private String[] names = {"发布车源", "查找车源", "车行收藏", "赚取佣金", "便捷查询", "违章处理", "维修服务", "保险服务", "年审服务", "联盟论坛"};
    private Integer[] icons = {R.mipmap.fabu, R.mipmap.cheyuan, R.mipmap.shoucang, R.mipmap.zhongjie,
            R.mipmap.chaxun, R.mipmap.weizhang, R.mipmap.weixiu, R.mipmap.baoxian, R.mipmap.nianshen, R.mipmap.luntan};
    private AddressDialog addressDialog;
    private LocationUtils locationUtils;
    private int page = 1;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        homeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(mActivity, CarDetailActivity.class));
            }
        });

        locationUtils.setOnLocationListener(new LocationUtils.OnLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                final String data = location.getProvince() + location.getCity() + location.getStreet();
                final String cityCode = location.getCityCode();
                tvCity.setText(location.getCity());
                SpUtlis.setLocationData(mActivity, cityCode, data, location.getCity());
                SpUtlis.setCuLocationData(mActivity, cityCode, data, location.getCity());
                getListData();
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {
                showToast("定位失败");
            }
        });

        homeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    //发布车源
                    case 0:
                        startActivity(new Intent(mActivity, SendCarActivity.class));
                        break;
                    //查找车源
                    case 1:

                        break;
                    //车行收藏
                    case 2:
                        break;
                    //赚取佣金
                    case 3:
                        break;
                    //便捷查询
                    case 4:
                        break;
                    //违章处理
                    case 5:
                        break;
                    //维修服务
                    case 6:
                        break;
                    //保险服务
                    case 7:
                        break;
                    //年审服务
                    case 8:
                        break;
                    //联盟论坛
                    case 9:
                        break;
                }
            }
        });

        addressDialog.setOnAddressDataListener(new AddressDialog.OnAddressDataListener() {
            @Override
            public void addressData(CityBean cityBean) {
                tvCity.setText(cityBean.getCityName());
                addressDialog.dismissDialog();
                getListData();
            }
        });

        homeTrl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getListData();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < names.length; i++) {
            gList.add(names[i]);
        }
        gAdapter.notifyDataSetChanged();

        ivList.add(R.mipmap.guanggao1);
        ivList.add(R.mipmap.guanggao2);
        ivList.add(R.mipmap.guanggao3);
        ivList.add(R.mipmap.guanggao4);

        homeBanner.notifyDataSetChanged();
        CityBean locationData = SpUtlis.getLocationData(mActivity);
        tvCity.setText(locationData.getCityName());
        //获取最新发布的车辆
        getListData();
    }

    private void getListData() {
        String cityCode = SpUtlis.getLocationData(mActivity).getCityCode();
        Map<String, String> params = new HashMap<>();
        params.put("did", "");
        params.put("cityCode", cityCode);
        params.put("page", page + "");

        HttpUtils.getInstance().Post(mActivity, params, API.getCarList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {

                    if (page == 1) {
                        homeTrl.finishRefreshing();
                        stopLoop(fabLoop);
                        mList.clear();
                    } else {
                        homeTrl.finishLoadmore();
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
                    mAdapter.notifyDataSetChanged();
                    if (page == 1) {
                        homeScroll.fullScroll(ScrollView.FOCUS_UP);
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
        gAdapter = new UniversalAdapter<String>(mActivity, gList, R.layout.home_grid_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {
                holder.setText(R.id.tv_name, s);
                holder.setImageResource(R.id.image_icon, icons[position]);
            }
        };
        mAdapter = new UniversalAdapter<CarInfoEntity>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarInfoEntity s) {
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
            }
        };
        homeTrl.setEnableRefresh(false);
        homeGrid.setAdapter(gAdapter);
        homeListView.setAdapter(mAdapter);

        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        homeBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, ivList);

        initBanner(homeBanner);

        addressDialog = new AddressDialog(mActivity);

        locationUtils = LocationUtils.getInstance(mActivity);
        if (!isPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            applyPermission(Permission.LOCATION, new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    locationUtils.startLocation();
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    showToast("您拒绝了定位，无法正常使用部分功能");
                }
            });
        } else {
            locationUtils.startLocation();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_city, R.id.iv_message, R.id.et_search_data, R.id.fab_loop})
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
            //进入消息中心
            case R.id.et_search_data:
                startActivity(new Intent(mActivity, CarSearchActivity.class));
                break;
            case R.id.fab_loop:
                updateData();
                break;
        }
    }

    /**
     * 刷新数据
     */
    private void updateData() {
        startLoop(fabLoop);
        page = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getListData();
            }
        }, 400);
    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }
}
