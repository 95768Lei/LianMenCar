package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarMessageEntity;
import com.zl.webproject.ui.fragment.MessageDetailFragment;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * @date 18/2/24
 * 消息中心
 */
public class MessageActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.message_listView)
    ListView messageListView;
    @BindView(R.id.message_trl)
    TwinklingRefreshLayout messageTrl;
    @BindView(R.id.message_rl)
    RelativeLayout messageRl;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;

    private List<CarMessageEntity> mList = new ArrayList<>();
    private UniversalAdapter<CarMessageEntity> adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mList.get(i).setMessUnread(0);
                adapter.notifyDataSetChanged();
                getSupportFragmentManager().beginTransaction().addToBackStack("message").replace(R.id.message_rl,
                        MessageDetailFragment.newInstance(mList.get(i).getId() + "")).commit();
            }
        });

        messageTrl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getDataList();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataList();
                    }
                }, 800);
            }
        });
    }

    private void initData() {
        getDataList();
    }

    private void getDataList() {
        final Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("page", page + "");

        HttpUtils.getInstance().Post(mActivity, params, API.getMessageList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {

                    if (page == 1) {
                        mList.clear();
                        messageTrl.finishRefreshing();
                    } else {
                        messageTrl.finishLoadmore();
                    }

                    JSONObject object = new JSONObject(body);
                    JSONArray array = object.optJSONArray("items");
                    if (array.length() <= 0) {
                        showToast("没有更多数据了");
                        return;
                    }
                    for (int i = 0; i < array.length(); i++) {
                        mList.add(new Gson().fromJson(array.optString(i), CarMessageEntity.class));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("body", body);
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });
    }

    private void initView() {
        tvTitleName.setText("消息中心");
        tvTitleRight.setText("全部已读");
        tvTitleRight.setVisibility(View.VISIBLE);
        adapter = new UniversalAdapter<CarMessageEntity>(mActivity, mList, R.layout.message_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, CarMessageEntity s) {
                holder.setText(R.id.message_tv_title, s.getMessTitle());
                holder.setText(R.id.message_tv_date, StringUtils.dateYYYY_MM_DD_HH_mm_ss(s.getMessDate()));
                holder.setText(R.id.message_data, s.getMessContext());
                View view = holder.getView(R.id.message_iv_tag);
                Integer messUnread = s.getMessUnread();
                Integer messType = s.getMessType();
                if (messUnread == 0) {
                    //已读
                    view.setVisibility(View.GONE);
                } else {
                    //未读
                    view.setVisibility(View.VISIBLE);
                }

                //根据消息类型显示不同的图标
                switch (messType) {

                }
            }
        };
        messageListView.setAdapter(adapter);
        initProgress(messageTrl);
    }

    @OnClick({R.id.iv_title_back, R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_title_right:
                allRead();
                break;
        }
    }

    //全部已读
    private void allRead() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        HttpUtils.getInstance().Post(mActivity, params, API.readAllMessage, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                showToast("已全部标记为已读");
                for (CarMessageEntity carMessageEntity : mList) {
                    carMessageEntity.setMessUnread(0);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }
}
