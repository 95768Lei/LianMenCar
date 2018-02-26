package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 * @date 18/2/26
 * 我的车源
 */
public class MyCarActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.my_car_listView)
    ListView myCarListView;
    @BindView(R.id.my_car_trl)
    TwinklingRefreshLayout myCarTrl;

    private List<String> mList = new ArrayList<>();
    private UniversalAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 6; i++) {
            mList.add("");
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        tvTitleName.setText("我的车源");

        adapter = new UniversalAdapter<String>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {
                bindData(holder, position, s);
            }
        };
        myCarListView.setAdapter(adapter);
        initProgress(myCarTrl);
    }

    /**
     * 绑定数据  绑定事件
     *
     * @param holder
     * @param position
     * @param s
     */
    private void bindData(UniversalViewHolder holder, int position, String s) {
        holder.getView(R.id.linear_bottom).setVisibility(View.VISIBLE);
        holder.getView(R.id.linear_yu_ding).setOnClickListener(this);
        holder.getView(R.id.linear_yi_shou).setOnClickListener(this);
        holder.getView(R.id.linear_xia_jia).setOnClickListener(this);
        holder.getView(R.id.linear_fen_xiang).setOnClickListener(this);
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linear_yu_ding:
                break;
            case R.id.linear_yi_shou:
                break;
            case R.id.linear_xia_jia:
                break;
            case R.id.linear_fen_xiang:
                break;
        }
    }
}
