package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.ui.activity.CarDetailActivity;
import com.zl.webproject.ui.activity.MessageActivity;
import com.zl.webproject.ui.dialog.MoreTagDialog;
import com.zl.webproject.ui.dialog.TagDialog;

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
    private UniversalAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();
    private TagDialog tagDialog;
    private MoreTagDialog moreTagDialog;
    private TagDialog tagNianDialog;
    private TagDialog tagRunDialog;
    private Handler handler = new Handler();

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
                startActivity(new Intent(mActivity, CarDetailActivity.class));
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mList.add("");
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mAdapter = new UniversalAdapter<String>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };
        carListView.setAdapter(mAdapter);
        initProgress(carTrl);

        //初始化弹窗
        tagDialog = new TagDialog(mActivity);
        tagNianDialog = new TagDialog(mActivity);
        tagRunDialog = new TagDialog(mActivity);
        moreTagDialog = new MoreTagDialog(mActivity);
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

                break;
            //进入消息中心
            case R.id.iv_message:
                startActivity(new Intent(mActivity, MessageActivity.class));
                break;
            //刷新数据
            case R.id.fab_loop:

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
