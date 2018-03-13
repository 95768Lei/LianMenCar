package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CarCommentEntity;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * @author zhanglei
 * @date 18/2/28
 */
public class DiscussActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.et_discuss)
    EditText etDiscuss;
    @BindView(R.id.discuss_listView)
    ListView discussListView;
    @BindView(R.id.discuss_trl)
    TwinklingRefreshLayout discussTrl;
    private List<CarCommentEntity> disList = new ArrayList<>();
    private UniversalAdapter<CarCommentEntity> disAdapter;
    private int page = 1;
    private int did;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        discussTrl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getListData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getListData();
                    }
                }, 800);
            }
        });
    }

    private void initData() {
        did = getIntent().getIntExtra("did", 0);
        getListData();
    }

    //获取评论数据
    private void getListData() {
        Map<String, String> params = new HashMap<>();
        params.put("did", did + "");
        params.put("page", page + "");
        HttpUtils.getInstance().Post(mActivity, params, API.carDealerCommentShowList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    if (page == 1) {
                        disList.clear();
                        discussTrl.finishRefreshing();
                    } else {
                        discussTrl.finishLoadmore();
                    }
                    JSONObject object = new JSONObject(body);
                    JSONArray array = new JSONArray(object.optString("items"));
                    for (int i = 0; i < array.length(); i++) {
                        disList.add(new Gson().fromJson(array.optString(i), CarCommentEntity.class));
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

    private void initView() {
        tvTitleName.setText("车行评论");
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
        discussListView.setAdapter(disAdapter);
        initProgress(discussTrl);
    }

    @OnClick({R.id.iv_title_back, R.id.tv_send_discuss})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_send_discuss:
                sendDiscuss();
                break;
        }
    }

    private void sendDiscuss() {

        if(!SpUtlis.getLoginData(mActivity).isLogin()){
            showToast("您还未登录，无法进行评价");
        }

        final String discuss = etDiscuss.getText().toString().trim();
        if (TextUtils.isEmpty(discuss)) {
            showToast("内容不能为空");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("did", did + "");
        params.put("commContext", discuss);

        HttpUtils.getInstance().Post(mActivity, params, API.saveCarDealerComment, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                showToast("评论发布成功");
                CarCommentEntity entity = new CarCommentEntity();
                CarUserEntity userData = SpUtlis.getUserData(mActivity);
                userData.setUserName(userData.getUserNikeName());
                entity.setCommContext(discuss);
                entity.setCommDate(new Date());
                entity.setCarUserEntity(userData);
                disList.add(0, entity);
                disAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }
}
