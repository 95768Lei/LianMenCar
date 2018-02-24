package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.ui.activity.CarHangDetailActivity;
import com.zl.webproject.ui.activity.MessageActivity;

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
public class CarHangFragment extends BaseFragment {


    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.car_hang_listView)
    ListView carHangListView;
    @BindView(R.id.car_hang_trl)
    TwinklingRefreshLayout carHangTrl;
    Unbinder unbinder;
    private UniversalAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();

    public CarHangFragment() {
        // Required empty public constructor
    }

    public static CarHangFragment newInstance() {

        Bundle args = new Bundle();

        CarHangFragment fragment = new CarHangFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_hang, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        carHangListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(mActivity, CarHangDetailActivity.class));
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mList.add("0");
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mAdapter = new UniversalAdapter<String>(mActivity, mList, R.layout.car_hang_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };
        carHangListView.setAdapter(mAdapter);
        initProgress(carHangTrl);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_city, R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_city:
                break;
            case R.id.iv_message:
                startActivity(new Intent(mActivity, MessageActivity.class));
                break;
        }
    }
}
