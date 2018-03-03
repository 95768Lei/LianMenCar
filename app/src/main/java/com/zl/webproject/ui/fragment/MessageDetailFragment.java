package com.zl.webproject.ui.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.model.CarMessageEntity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * @author zhanglei
 *         消息详情
 */
public class MessageDetailFragment extends BaseFragment {


    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    Unbinder unbinder;
    @BindView(R.id.tv_message_data)
    TextView tvMessageData;

    public MessageDetailFragment() {
        // Required empty public constructor
    }

    public static MessageDetailFragment newInstance(String mid) {

        Bundle args = new Bundle();
        args.putString("mid", mid);
        MessageDetailFragment fragment = new MessageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setClickable(true);
        initData();
        return view;
    }

    private void initData() {
        String mid = getArguments().getString("mid");
        Map<String, String> params = new HashMap<>();
        params.put("mid", mid);
        HttpUtils.getInstance().Post(mActivity, params, API.getMessageInfoById, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                CarMessageEntity carMessageEntity = new Gson().fromJson(body, CarMessageEntity.class);
                tvTitleName.setText(carMessageEntity.getMessTitle());
                tvMessageData.setText(carMessageEntity.getMessContext());
                Log.e("body", body);
            }

            @Override
            public void onError(Request error, Exception e) {
                Log.e("body", "");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        getFragmentManager().popBackStack();
    }
}
