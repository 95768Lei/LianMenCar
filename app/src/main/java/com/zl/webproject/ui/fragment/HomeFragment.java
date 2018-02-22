package com.zl.webproject.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
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
    ListView homeListView;
    @BindView(R.id.home_trl)
    TwinklingRefreshLayout homeTrl;
    Unbinder unbinder;

    private UniversalAdapter<String> gAdapter;
    private UniversalAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();
    private List<String> gList = new ArrayList<>();

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
        return view;
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            gList.add("");
            mList.add("");
        }
        gAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        gAdapter = new UniversalAdapter<String>(mActivity, gList, R.layout.home_grid_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };
        mAdapter = new UniversalAdapter<String>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };
        homeGrid.setAdapter(gAdapter);
        homeListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_city, R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择城市
            case R.id.tv_city:
                break;
            //进入消息中心
            case R.id.iv_message:
                break;
        }
    }
}
