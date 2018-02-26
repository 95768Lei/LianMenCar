package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.ui.activity.CarCollectActivity;
import com.zl.webproject.ui.activity.CarHangCollectActivity;
import com.zl.webproject.ui.activity.FeedBackActivity;
import com.zl.webproject.ui.activity.MessageActivity;
import com.zl.webproject.ui.activity.MyCarActivity;
import com.zl.webproject.ui.activity.SendCarActivity;
import com.zl.webproject.ui.activity.SettingsActivity;
import com.zl.webproject.ui.activity.WebActivity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.SystemUtils;
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
    private String[] names = {"发布车源", "我的车源", "我的车行", "车行收藏", "车辆关注", "赚取佣金",
            "中介认证", "客服电话", "免责声明", "意见反馈", "关于我们"};
    private Integer[] icons = {R.mipmap.fbcy, R.mipmap.wdcy, R.mipmap.wdch, R.mipmap.wdgz, R.mipmap.wdsc,
            R.mipmap.zjcl, R.mipmap.zj, R.mipmap.kfdh, R.mipmap.mzsm, R.mipmap.yjfk, R.mipmap.gy};

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
        initListener();
        return view;
    }

    private void initListener() {
        personListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    //发布车源
                    case 0:
                        startActivity(new Intent(mActivity, SendCarActivity.class));
                        break;
                    //我的车源
                    case 1:
                        startActivity(new Intent(mActivity, MyCarActivity.class));
                        break;
                    //我的车行
                    case 2:
                        break;
                    //车行收藏
                    case 3:
                        startActivity(new Intent(mActivity, CarHangCollectActivity.class));
                        break;
                    //车辆关注
                    case 4:
                        startActivity(new Intent(mActivity, CarCollectActivity.class));
                        break;
                    //赚取佣金
                    case 5:
                        startActivity(new Intent(mActivity, MyCarActivity.class));
                        break;
                    //中介认证
                    case 6:
                        break;
                    //客服电话
                    case 7:
                        SystemUtils.call(mActivity, "15075993917");
                        break;
                    //免责声明
                    case 8:
                        Intent intent = new Intent(mActivity, WebActivity.class);
                        intent.putExtra("url", API.MFSM_URL);
                        startActivity(intent);
                        break;
                    //意见反馈
                    case 9:
                        startActivity(new Intent(mActivity, FeedBackActivity.class));
                        break;
                    //关于我们
                    case 10:
                        Intent intent1 = new Intent(mActivity, WebActivity.class);
                        intent1.putExtra("url", API.GYWM_URL);
                        startActivity(intent1);
                        break;
                }
            }
        });
    }

    private void initData() {
        for (int i = 0; i < names.length; i++) {
            mList.add(names[i]);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mAdapter = new UniversalAdapter<String>(mActivity, mList, R.layout.person_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {
                holder.setText(R.id.tv_list_item, s);
                holder.setImageResource(R.id.iv_list_item, icons[position]);
            }
        };
        personListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_setting, R.id.iv_message, R.id.tv_sign_out})
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
            //退出登录
            case R.id.tv_sign_out:
                mActivity.finish();
                break;
        }
    }
}
