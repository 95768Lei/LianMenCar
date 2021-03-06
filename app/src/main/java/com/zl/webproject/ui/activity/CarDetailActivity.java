package com.zl.webproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarCommentEntity;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.model.CarResourceEntity;
import com.zl.webproject.model.ShareBean;
import com.zl.webproject.ui.dialog.ImagePreviewDialog;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ShareUtils;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.utils.StringUtils;
import com.zl.webproject.utils.SystemUtils;
import com.zl.webproject.utils.TagUtils;
import com.zl.webproject.view.LocalImageHolderView;
import com.zl.webproject.view.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhanglei
 * @date 18/2/22
 */
public class CarDetailActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.into_car_hang)
    AutoLinearLayout intoCarHang;
    @BindView(R.id.into_share_car)
    AutoLinearLayout intoShareCar;
    @BindView(R.id.info_shou_cang_car)
    AutoLinearLayout infoShouCangCar;
    @BindView(R.id.info_call)
    AutoLinearLayout infoCall;
    @BindView(R.id.car_detail_bottom)
    AutoLinearLayout carDetailBottom;
    @BindView(R.id.car_detail_line)
    TextView carDetailLine;
    @BindView(R.id.home_banner)
    ConvenientBanner homeBanner;
    @BindView(R.id.car_detail_data_listView)
    MyListView carDetailDataListView;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.tv_car_tag)
    TextView tvCarTag;
    @BindView(R.id.tv_car_content)
    TextView tvCarContent;
    @BindView(R.id.tv_carCollectionCount)
    TextView tvCarCollectionCount;
    @BindView(R.id.iv_isCollectionCar)
    ImageView ivIsCollectionCar;
    @BindView(R.id.tv_car_tag1)
    TextView tvCarTag1;
    @BindView(R.id.linear_more_data)
    AutoLinearLayout linearMoreData;
    @BindView(R.id.discuss_listView)
    MyListView discussListView;
    @BindView(R.id.tv_info_more_discuss)
    TextView tvInfoMoreDiscuss;
    @BindView(R.id.linear_car_dis)
    AutoLinearLayout linearCarDis;
    @BindView(R.id.tv_to_discuss)
    TextView tvToDiscuss;
    @BindView(R.id.car_tab)
    TabLayout carTab;
    @BindView(R.id.car_detail_trl)
    TwinklingRefreshLayout carDetailTrl;

    private List<String> dList = new ArrayList<>();
    private List<CarCommentEntity> disList = new ArrayList<>();
    private List<CarResourceEntity> bList = new ArrayList<>();
    private UniversalAdapter<String> dAdapter;
    private UniversalAdapter<CarCommentEntity> disAdapter;
    private CarInfoEntity carInfoEntity;
    private ImagePreviewDialog previewDialog;
    private boolean isCollectionCar = false;
    private Integer carCollectionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mActivity).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取车辆评论
        getCarDisData();
    }

    private void initListener() {
        homeBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (carInfoEntity == null) {
                    return;
                }
                List<CarResourceEntity> resources = carInfoEntity.getResources();
                List<String> list = new ArrayList<String>();
                for (CarResourceEntity resource : resources) {
                    list.add(resource.getResUrl());
                }
                previewDialog.setData(list);
                previewDialog.showDialog(intoShareCar);
            }
        });

        carTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        linearCarDis.setVisibility(View.GONE);
                        linearMoreData.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        linearCarDis.setVisibility(View.VISIBLE);
                        linearMoreData.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initData() {
        carInfoEntity = (CarInfoEntity) getIntent().getSerializableExtra("data");
        bList.addAll(carInfoEntity.getResources());
        homeBanner.notifyDataSetChanged();
        /** 修改了布局显示 */
        dList.add("品牌型号：" + carInfoEntity.getCarBrandName());
        dList.add("车辆类型：" + carInfoEntity.getLv().getDictName());
        dList.add("变速器：" + carInfoEntity.getGearbox().getDictName());
        dList.add("上牌日期：" + carInfoEntity.getCarLicensingDateStr());
        dList.add("行驶里程：" + carInfoEntity.getCarMileage() + "万公里");
        dList.add("所在地区：" + carInfoEntity.getCarAreaCitysEntity().getCityName());
        dList.add("销售价格：" + carInfoEntity.getCarPrice() + "万元");
        dList.add("订车定金：" + carInfoEntity.getCarDeposit());
        dList.add("车辆排量：" + carInfoEntity.getCarDisplacement());
        dList.add("燃油类型：" + carInfoEntity.getFuel().getDictName());
        carCollectionCount = carInfoEntity.getCarCollectionCount();
        tvCarContent.setText("*" + carInfoEntity.getCarContext());
        tvCarCollectionCount.setText("热度  " + carCollectionCount + "");
        dAdapter.notifyDataSetChanged();
        TagUtils.setTag(mActivity, carInfoEntity.getCarLabel(), tvCarTag);
        Integer carLocking = carInfoEntity.getCarLocking();
        Integer carSeized = carInfoEntity.getCarSeized();
        Integer carPeccancy = carInfoEntity.getCarPeccancy();
        if (carLocking == 1) {
            tvCarTag1.setText("锁定");
            tvCarTag1.setVisibility(View.VISIBLE);
        } else {
            tvCarTag1.setVisibility(View.GONE);
        }
        if (carSeized == 1) {
            tvCarTag1.setText("查封");
            tvCarTag1.setVisibility(View.VISIBLE);
        } else {
            tvCarTag1.setVisibility(View.GONE);
        }
        if (carPeccancy == 1) {
            tvCarTag1.setText("违章");
            tvCarTag1.setVisibility(View.VISIBLE);
        } else {
            tvCarTag1.setVisibility(View.GONE);
        }

        //插入浏览记录
        Map<String, String> params = new HashMap<>();
        params.put("cid", carInfoEntity.getId() + "");
        params.put("isSee", "true");
        HttpUtils.getInstance().Post(mActivity, params, API.getCarInfoById, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body);
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });

        //是否关注该车辆
        isCollectionCar();

        carTab.addTab(carTab.newTab().setText("其他信息"));
        carTab.addTab(carTab.newTab().setText("车辆评论"));
    }

    /**
     * 获取车辆评论
     */
    private void getCarDisData() {
        if (carInfoEntity == null) return;
        Map<String, String> params = new HashMap<>();
        params.put("cid", carInfoEntity.getId() + "");
        params.put("page", "1");
        HttpUtils.getInstance().Post(mActivity, params, API.carInfoCommentShowList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    disList.clear();
                    JSONObject object = new JSONObject(body);
                    JSONArray array = new JSONArray(object.optString("items"));
                    for (int i = 0; i < (array.length() > 5 ? 5 : array.length()); i++) {
                        disList.add(new Gson().fromJson(array.optString(i), CarCommentEntity.class));
                    }
                    if (array.length() <= 0) {
                        tvToDiscuss.setVisibility(View.VISIBLE);
                    } else {
                        tvToDiscuss.setVisibility(View.GONE);
                    }
                    disAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

    private void isCollectionCar() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("cid", carInfoEntity.getId() + "");

        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.add(entry.getKey(), entry.getValue());
        }
        //创建请求体
        Request request = new Request.Builder().url(API.isCollectionCar).post(builder.build()).build();

        //加入任务调度
        new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String string = response.body().string();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            isCollectionCar = jsonObject.optBoolean("result");
                            if (isCollectionCar) {
                                ivIsCollectionCar.setImageResource(R.drawable.ic_favorite_pink);
                            } else {
                                ivIsCollectionCar.setImageResource(R.drawable.ic_favorite_border_black);
                            }
                        } catch (Exception e) {
                            showToast("发生了未知错误");
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        dAdapter = new UniversalAdapter<String>(mActivity, dList, R.layout.car_data_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {
                String[] split = s.split("：");
                holder.setText(R.id.car_item_key, split[0]);
                holder.setText(R.id.car_item_value, split[1]);
            }
        };
        disAdapter = new UniversalAdapter<CarCommentEntity>(mActivity, disList, R.layout.discuss_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarCommentEntity s) {
                holder.setText(R.id.tv_dis_data, s.getCommContext());
                holder.setText(R.id.tv_person_name, TextUtils.isEmpty(s.getCarUserEntity().getUserName()) ?
                        s.getCarUserEntity().getUserNikeName() : s.getCarUserEntity().getUserName());
                holder.setText(R.id.tv_discuss_date, StringUtils.dateYYYY_MM_DD_HH_mm_ss(s.getCommDate()));
                holder.setImageUrl(mActivity, R.id.iv_person_icon, s.getCarUserEntity().getUserImg());
            }
        };
        carDetailDataListView.setAdapter(dAdapter);
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        homeBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, bList);

        initBanner(homeBanner);

        tvTitleName.setText("车辆详情");
        discussListView.setAdapter(disAdapter);

        previewDialog = new ImagePreviewDialog(mActivity);
        carDetailTrl.setEnableLoadmore(false);
        carDetailTrl.setEnableRefresh(false);
    }

    @OnClick({R.id.iv_title_back, R.id.into_car_hang, R.id.into_share_car, R.id.info_shou_cang_car,
            R.id.info_call, R.id.tv_info_more_discuss, R.id.tv_to_discuss})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.iv_title_back:
                finish();
                break;
            //进入车行
            case R.id.into_car_hang:
                intoCarHang();
                break;
            //分享车辆
            case R.id.into_share_car:
                share();
                break;
            //收藏车辆
            case R.id.info_shou_cang_car:
                shouCang();
                break;
            //打电话
            case R.id.info_call:
                SystemUtils.call(mActivity, carInfoEntity.getUserPhone());
                break;
            case R.id.tv_info_more_discuss:
                intoDis();
                break;
            case R.id.tv_to_discuss:
                intoDis();
                break;
        }
    }

    /**
     * 进入评价页
     */
    private void intoDis() {
        if (carInfoEntity == null) return;
        Intent intent = new Intent(mActivity, CarDiscussActivity.class);
        intent.putExtra("cid", carInfoEntity.getId());
        startActivity(intent);
    }

    /**
     * 分享
     */
    private void share() {
        ShareBean shareBean = new ShareBean();
        shareBean.setShareTitle(carInfoEntity.getCarTitle());
        shareBean.setImgUrl(carInfoEntity.getCarImg());
        shareBean.setShareContent(carInfoEntity.getCarContext());
        shareBean.setUrl(API.toCarDetails + "?cid=" + carInfoEntity.getId());
        ShareUtils.share(mActivity, shareBean, new ShareUtils.OnShareListener() {
            @Override
            public void shareSuccess(SHARE_MEDIA share_media) {
                showToast("分享成功");
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
                params.put("cid", carInfoEntity.getId() + "");
                HttpUtils.getInstance().Post(mActivity, params, API.toForwardCarInfo, new HttpUtils.OnOkHttpCallback() {
                    @Override
                    public void onSuccess(String body) {

                    }

                    @Override
                    public void onError(Request error, Exception e) {

                    }
                });
            }

            @Override
            public void shareError(SHARE_MEDIA share_media, Throwable throwable) {

            }
        });
    }

    /**
     * 进入车行
     */
    private void intoCarHang() {
        Integer carDealerId = carInfoEntity.getCarUserEntity().getCarDealerId();
        if (carDealerId == -1) {
            showToast("该车为个人车源，无法进入车行");
            return;
        }
        Intent intent = new Intent(mActivity, CarHangDetailActivity.class);
        intent.putExtra("did", carDealerId);
        startActivity(intent);
    }

    /**
     * 收藏车辆(关注)
     */
    private void shouCang() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("cid", carInfoEntity.getId() + "");

        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.add(entry.getKey(), entry.getValue());
        }
        //创建请求体
        Request request = new Request.Builder().url(API.collectionCar).post(builder.build()).build();

        //加入任务调度
        new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String string = response.body().string();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            if (jsonObject.optString("msg").equals("已收藏")) {
                                carCollectionCount += 1;
                                tvCarCollectionCount.setText("热度  " + carCollectionCount + "");
                                isCollectionCar = true;
                            } else {
                                carCollectionCount -= 1;
                                tvCarCollectionCount.setText("热度  " + carCollectionCount + "");
                                isCollectionCar = false;
                            }
                            if (isCollectionCar) {
                                ivIsCollectionCar.setImageResource(R.drawable.ic_favorite_pink);
                            } else {
                                ivIsCollectionCar.setImageResource(R.drawable.ic_favorite_border_black);
                            }
                        } catch (Exception e) {
                            showToast("发生了未知错误");
                        }
                    }
                });
            }
        });
    }
}
