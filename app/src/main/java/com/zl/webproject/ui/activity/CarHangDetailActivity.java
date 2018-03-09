package com.zl.webproject.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarCommentEntity;
import com.zl.webproject.model.CarDealerEntity;
import com.zl.webproject.model.CarDealerResourceEntity;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.model.ShareBean;
import com.zl.webproject.ui.dialog.ImagePreviewDialog;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.BindDataUtils;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.ShareUtils;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.utils.StringUtils;
import com.zl.webproject.utils.SystemUtils;
import com.zl.webproject.view.MyListView;

import org.json.JSONArray;
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
 *         车行详情页
 */
public class CarHangDetailActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.car_hang_banner)
    ConvenientBanner carHangBanner;
    @BindView(R.id.iv_car_hang_icon)
    ImageView ivCarHangIcon;
    @BindView(R.id.tv_car_hang_name)
    TextView tvCarHangName;
    @BindView(R.id.tv_car_data)
    TextView tvCarData;
    @BindView(R.id.tv_car_hang_person_phone)
    TextView tvCarHangPersonPhone;
    @BindView(R.id.iv_call)
    ImageView ivCall;
    @BindView(R.id.tv_info_more_discuss)
    TextView tvInfoMoreDiscuss;
    @BindView(R.id.discuss_listView)
    MyListView discussListView;
    @BindView(R.id.tv_info_more_car)
    TextView tvInfoMoreCar;
    @BindView(R.id.car_hang_listView)
    MyListView carListView;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.tv_to_discuss)
    TextView tvToDiscuss;
    @BindView(R.id.tv_no_car)
    TextView tvNoCar;
    @BindView(R.id.tv_car_hang_person)
    TextView tvCarHangPerson;
    @BindView(R.id.tv_carCollectionCount)
    TextView tvCarCollectionCount;
    @BindView(R.id.iv_isFollowDetails)
    ImageView ivIsFollowDetails;

    private List<CarCommentEntity> disList = new ArrayList<>();
    private List<CarInfoEntity> carList = new ArrayList<>();
    private List<CarDealerResourceEntity> bList = new ArrayList<>();
    private UniversalAdapter<CarCommentEntity> disAdapter;
    private UniversalAdapter<CarInfoEntity> carAdapter;
    private CarDealerEntity carDealerEntity;
    private int did;
    private ImagePreviewDialog previewDialog;
    private boolean isFollowDetails;
    private Integer followCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hang_detail);
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

    private void initListener() {
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, CarDetailActivity.class);
                intent.putExtra("data", carList.get(i));
                startActivity(intent);
            }
        });

        carHangBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (carDealerEntity == null) {
                    return;
                }
                List<CarDealerResourceEntity> resources = carDealerEntity.getResources();
                List<String> list = new ArrayList<String>();
                for (CarDealerResourceEntity resource : resources) {
                    list.add(resource.getResDealerUrl());
                }
                previewDialog.setData(list);
                previewDialog.showDialog(carHangBanner);
            }
        });
    }

    private void initData() {
        did = getIntent().getIntExtra("did", 0);
        getData();
        disAdapter.notifyDataSetChanged();
        carAdapter.notifyDataSetChanged();
    }

    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("did", did + "");
        params.put("isSee", true + "");
        HttpUtils.getInstance().Post(mActivity, params, API.getCarDealerById, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                carDealerEntity = new Gson().fromJson(body, CarDealerEntity.class);
                updateUi();
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

    private void updateUi() {
        if (carDealerEntity == null) {
            return;
        }

        followCount = carDealerEntity.getFollowCount();
        tvCarCollectionCount.setText("热度  " + followCount + "");
        //轮播图
        bList.clear();
        List<CarDealerResourceEntity> resources = carDealerEntity.getResources();
        bList.addAll(resources);
        carHangBanner.notifyDataSetChanged();

        ImageLoader.loadImageUrl(mActivity, carDealerEntity.getDealerImg(), ivCarHangIcon);
        tvCarHangName.setText(carDealerEntity.getDealerName());
        tvCarHangPersonPhone.setText("联系电话：" + carDealerEntity.getDealerPhone());
        tvCarHangPerson.setText("联系人：");
        tvCarData.setText("地址：" + carDealerEntity.getCity().getCityName());

        //评论数据
        disList.clear();
        if (carDealerEntity.getComment().size() > 0) {
            tvToDiscuss.setVisibility(View.GONE);
            disList.addAll(carDealerEntity.getComment());
            disAdapter.notifyDataSetChanged();
        } else {
            tvToDiscuss.setVisibility(View.VISIBLE);
        }

        //车源数据
        Map<String, String> params = new HashMap<>();
        params.put("did", did + "");
        params.put("cityCode", "");
        params.put("page", "1");

        HttpUtils.getInstance().Post(mActivity, params, API.getCarList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    carList.clear();
                    JSONObject object = new JSONObject(body);
                    JSONArray array = object.optJSONArray("items");
                    if (array.length() <= 0) {
                        tvNoCar.setVisibility(View.VISIBLE);
                    } else {
                        tvNoCar.setVisibility(View.GONE);
                    }
                    for (int i = 0; i < (array.length() >= 5 ? 5 : array.length()); i++) {
                        carList.add(new Gson().fromJson(array.optString(i), CarInfoEntity.class));
                    }
                    carAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });

        //是否关注了该车行
        isFollowDetails();

    }

    private void initView() {
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

        carAdapter = new UniversalAdapter<CarInfoEntity>(mActivity, carList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarInfoEntity s) {
                BindDataUtils.bindCarData(mActivity, holder, s);
            }
        };

        discussListView.setAdapter(disAdapter);
        carListView.setAdapter(carAdapter);

        tvTitleName.setText("车行详情");
        ivTitleShare.setVisibility(View.VISIBLE);

        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        carHangBanner.setPages(
                new CBViewHolderCreator<ImageHolderView>() {
                    @Override
                    public ImageHolderView createHolder() {
                        return new ImageHolderView();
                    }
                }, bList);

        initBanner(carHangBanner);

        previewDialog = new ImagePreviewDialog(mActivity);
    }

    public class ImageHolderView implements Holder<CarDealerResourceEntity> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, CarDealerResourceEntity data) {
            ImageLoader.loadImageUrl(context, data.getResDealerUrl(), imageView);
        }
    }

    @OnClick({R.id.iv_title_back, R.id.iv_title_share, R.id.iv_call, R.id.tv_to_discuss, R.id.tv_info_more_discuss, R.id.iv_isFollowDetails, R.id.tv_info_more_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.iv_title_share:
                share();
                break;
            case R.id.iv_call:
                SystemUtils.call(mActivity, carDealerEntity.getDealerPhone());
                break;
            case R.id.tv_to_discuss:
                toDiscuss();
                break;
            case R.id.tv_info_more_discuss:
                toDiscuss();
                break;
            case R.id.tv_info_more_car:
                toMoreCar();
                break;
            case R.id.iv_isFollowDetails:
                shouCang();
                break;
        }
    }

    /**
     * 收藏车辆(关注)
     */
    private void shouCang() {
        if (carDealerEntity == null) return;
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("did", carDealerEntity.getId() + "");

        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.add(entry.getKey(), entry.getValue());
        }
        //创建请求体
        Request request = new Request.Builder().url(API.followDetails).post(builder.build()).build();

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
                            if (jsonObject.optString("msg").equals("已关注")) {
                                followCount += 1;
                                tvCarCollectionCount.setText("热度  " + followCount + "");
                                isFollowDetails = true;
                            } else {
                                followCount -= 1;
                                tvCarCollectionCount.setText("热度  " + followCount + "");
                                isFollowDetails = false;
                            }
                            if (isFollowDetails) {
                                ivIsFollowDetails.setImageResource(R.drawable.ic_favorite_white);
                            } else {
                                ivIsFollowDetails.setImageResource(R.drawable.ic_favorite_border_white);
                            }
                        } catch (Exception e) {
                            showToast("发生了未知错误");
                        }
                    }
                });
            }
        });
    }

    private void isFollowDetails() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("did", carDealerEntity.getId() + "");

        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.add(entry.getKey(), entry.getValue());
        }
        //创建请求体
        Request request = new Request.Builder().url(API.isFollowDetails).post(builder.build()).build();

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
                            isFollowDetails = jsonObject.optBoolean("result");
                            if (isFollowDetails) {
                                ivIsFollowDetails.setImageResource(R.drawable.ic_favorite_white);
                            } else {
                                ivIsFollowDetails.setImageResource(R.drawable.ic_favorite_border_white);
                            }
                        } catch (Exception e) {
                            showToast("发生了未知错误");
                        }
                    }
                });
            }
        });
    }

    @OnClick(R.id.iv_isFollowDetails)
    public void onViewClicked() {
    }

    private void share() {
        ShareBean shareBean = new ShareBean();
        shareBean.setShareTitle(carDealerEntity.getDealerName());
        shareBean.setImgUrl(carDealerEntity.getDealerImg());
        shareBean.setShareContent(carDealerEntity.getDealerContext());
        shareBean.setUrl(API.toCarShop + "?did=" + carDealerEntity.getId());
        ShareUtils.share(mActivity, shareBean, new ShareUtils.OnShareListener() {
            @Override
            public void shareSuccess(SHARE_MEDIA share_media) {
                showToast("分享成功");
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
                params.put("did", carDealerEntity.getId() + "");
                HttpUtils.getInstance().Post(mActivity, params, API.toForwardCarDealer, new HttpUtils.OnOkHttpCallback() {
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

    private void toMoreCar() {
        Intent intent = new Intent(mActivity, MotorsCarListActivity.class);
        intent.putExtra("did", carDealerEntity.getId());
        startActivity(intent);
    }

    private void toDiscuss() {
        Intent intent = new Intent(mActivity, DiscussActivity.class);
        intent.putExtra("did", carDealerEntity.getId());
        startActivity(intent);
    }
}
