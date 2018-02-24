package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.ui.activity.MessageActivity;
import com.zl.webproject.ui.activity.SettingsActivity;
import com.zl.webproject.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author zhanglei
 * @date 18/2/22
 */
public class PersonFragment extends BaseFragment {


    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.iv_person_icon)
    CircleImageView ivPersonIcon;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.person_listView)
    MyListView personListView;
    Unbinder unbinder;
    private UniversalAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();

    public PersonFragment() {
        // Required empty public constructor
    }

    public static PersonFragment newInstance() {

        Bundle args = new Bundle();

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        for (int i = 0; i < 7; i++) {
            mList.add("");
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mAdapter = new UniversalAdapter<String>(mActivity, mList, R.layout.person_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {

            }
        };
        personListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_setting, R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //进入设置
            case R.id.iv_setting:
                startActivity(new Intent(mActivity, SettingsActivity.class));
                break;
            //进入消息中心
            case R.id.iv_message:
                startActivity(new Intent(mActivity, MessageActivity.class));
                break;
        }
    }
}
