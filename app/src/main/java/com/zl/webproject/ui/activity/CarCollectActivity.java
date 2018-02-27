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
 * 车辆收藏
 */
public class CarCollectActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.car_collect_listView)
    ListView carCollectListView;
    @BindView(R.id.car_collect_trl)
    TwinklingRefreshLayout carCollectTrl;

    private List<String> mList = new ArrayList<>();
    private UniversalAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_collect);
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
        tvTitleName.setText("车辆关注");

        adapter = new UniversalAdapter<String>(mActivity, mList, R.layout.home_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {
                bindData(holder, position, s);
            }
        };
        carCollectListView.setAdapter(adapter);
        initProgress(carCollectTrl);
    }

    /**
     * 绑定数据  绑定事件
     *
     * @param holder
     * @param position
     * @param s
     */
    private void bindData(UniversalViewHolder holder, int position, String s) {
        holder.getView(R.id.linear_bottom_two).setVisibility(View.VISIBLE);
        holder.getView(R.id.linear_share).setOnClickListener(this);
        holder.getView(R.id.linear_delete).setOnClickListener(this);
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //分享
            case R.id.linear_share:
                break;
            //删除
            case R.id.linear_delete:
                break;
        }
    }

}
