package com.zl.webproject.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.ui.activity.CarDetailActivity;
import com.zl.webproject.ui.activity.MessageActivity;
import com.zl.webproject.ui.activity.SendCarActivity;
import com.zl.webproject.ui.activity.SettingsActivity;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.utils.LocationUtils;
import com.zl.webproject.view.MyGridView;
import com.zl.webproject.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    private UniversalAdapter<String> gAdapter;
    private UniversalAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();
    private List<String> gList = new ArrayList<>();
    private List<Integer> ivList = new ArrayList<>();
    private String[] names = {"发布车源", "查找车源", "车行收藏", "赚取佣金", "便捷查询", "违章处理", "维修服务", "保险服务", "年审服务", "联盟论坛"};
    private Integer[] icons = {R.mipmap.fabu, R.mipmap.cheyuan, R.mipmap.shoucang, R.mipmap.zhongjie,
            R.mipmap.chaxun, R.mipmap.weizhang, R.mipmap.weixiu,R.mipmap.baoxian, R.mipmap.nianshen, R.mipmap.luntan};
    private AddressDialog addressDialog;
    private LocationUtils locationUtils;

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
                Log.e("BDLocation", "");
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {
                Log.e("BDLocation", "");
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
    }

    private void initData() {
        for (int i = 0; i < names.length; i++) {
            gList.add(names[i]);
            mList.add(names[i]);
        }
        gAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();

        ivList.add(R.mipmap.guanggao1);
        ivList.add(R.mipmap.guanggao2);
        ivList.add(R.mipmap.guanggao3);
        ivList.add(R.mipmap.guanggao4);

        homeBanner.notifyDataSetChanged();
    }

    private void initView() {
        gAdapter = new UniversalAdapter<String>(mActivity, gList, R.layout.home_grid_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {
                holder.setText(R.id.tv_name, s);
                holder.setImageResource(R.id.image_icon,icons[position]);
            }
        };
        mAdapter = new UniversalAdapter<String>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

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
        locationUtils.startLocation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_city, R.id.iv_message, R.id.fab_loop})
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
