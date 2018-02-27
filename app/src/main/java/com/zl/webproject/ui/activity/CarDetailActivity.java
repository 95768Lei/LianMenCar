package com.zl.webproject.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarInfoEntity;
import com.zl.webproject.model.CarResourceEntity;
import com.zl.webproject.ui.dialog.ImagePreviewDialog;
import com.zl.webproject.ui.fragment.HomeFragment;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.view.MyListView;

import org.json.JSONObject;

import java.io.Serializable;
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
    @BindView(R.id.carDetail_iv_listView)
    MyListView carDetailIvListView;

    private List<String> dList = new ArrayList<>();
    private List<CarResourceEntity> ivList = new ArrayList<>();
    private List<CarResourceEntity> bList = new ArrayList<>();
    private UniversalAdapter<String> dAdapter;
    private UniversalAdapter<CarResourceEntity> ivAdapter;
    private CarInfoEntity carInfoEntity;
    private ImagePreviewDialog previewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
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
    }

    private void initData() {
        carInfoEntity = (CarInfoEntity) getIntent().getSerializableExtra("data");
        bList.addAll(carInfoEntity.getResources());
        homeBanner.notifyDataSetChanged();
        /** 修改了布局显示 */
//        ivList.addAll(carInfoEntity.getResources());
//        ivAdapter.notifyDataSetChanged();
        dList.add("品牌型号：" + carInfoEntity.getCarBrandName());
        dList.add("车辆类型：" + carInfoEntity.getLv().getDictName());
        dList.add("变速器：" + carInfoEntity.getGearbox().getDictName());
        dList.add("上牌日期：" + carInfoEntity.getCarBrandName());
        dList.add("行驶里程：" + carInfoEntity.getCarMileage() + "万公里");
        dList.add("所在地区：" + carInfoEntity.getCarAreaCitysEntity().getCityName());
        dList.add("销售价格：" + carInfoEntity.getCarPrice() + "万元");
        dList.add("订车定金：" + carInfoEntity.getCarDeposit());
        dList.add("车辆排量：" + carInfoEntity.getCarDisplacement());
        dList.add("燃油类型：" + carInfoEntity.getFuel().getDictName());

        dAdapter.notifyDataSetChanged();
    }

    private void initView() {
        dAdapter = new UniversalAdapter<String>(mActivity, dList, R.layout.car_data_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {
                holder.setText(R.id.car_item_key, s);
            }
        };
        ivAdapter = new UniversalAdapter<CarResourceEntity>(mActivity, ivList, R.layout.car_iv_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarResourceEntity s) {
                holder.setImageUrl(mActivity, R.id.car_item_image, s.getResUrl());
            }
        };

        carDetailDataListView.setAdapter(dAdapter);
        carDetailIvListView.setAdapter(ivAdapter);

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

        previewDialog = new ImagePreviewDialog(mActivity);
    }

    @OnClick({R.id.iv_title_back, R.id.into_car_hang, R.id.into_share_car, R.id.info_shou_cang_car, R.id.info_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.iv_title_back:
                finish();
                break;
            //进入车行
            case R.id.into_car_hang:
                break;
            //分享车辆
            case R.id.into_share_car:
                break;
            //收藏车辆
            case R.id.info_shou_cang_car:
                break;
            //打电话
            case R.id.info_call:
                break;
        }
    }

    public class LocalImageHolderView implements Holder<CarResourceEntity> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, CarResourceEntity data) {
            ImageLoader.loadImageUrl(context, data.getResUrl(), imageView);
        }
    }
}
