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
import com.zl.webproject.model.PersonGridBean;
import com.zl.webproject.ui.activity.CarCollectActivity;
import com.zl.webproject.ui.activity.CarHangCollectActivity;
import com.zl.webproject.ui.activity.FeedBackActivity;
import com.zl.webproject.ui.activity.LoginActivity;
import com.zl.webproject.ui.activity.MessageActivity;
import com.zl.webproject.ui.activity.MyCarActivity;
import com.zl.webproject.ui.activity.SendCarActivity;
import com.zl.webproject.ui.activity.SettingsActivity;
import com.zl.webproject.ui.activity.WebActivity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.SystemUtils;
import com.zl.webproject.view.MyGridView;
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
    private UniversalAdapter<PersonGridBean> mAdapter;
    private List<PersonGridBean> mList = new ArrayList<>();
    private String[] cars = {"发布车源", "我的车源", "赚取佣金", "车辆关注"};
    private String[] carHangs = {"车行收藏", "我的车行", "中介认证",};
    private String[] womens = {"客服电话", "免责声明", "意见反馈", "关于我们"};


    private Integer[] iconCars = {R.mipmap.fbcy, R.mipmap.wdcy, R.mipmap.zjcl, R.mipmap.wdsc};
    private Integer[] iconCarhangs = {R.mipmap.wdgz, R.mipmap.wdch, R.mipmap.zj};
    private Integer[] iconWoMens = {R.mipmap.kfdh, R.mipmap.mzsm, R.mipmap.yjfk, R.mipmap.gy};

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

            }
        });
    }

    private void initData() {

        PersonGridBean carBean = new PersonGridBean();
        carBean.setTitle("关于车辆");
        List<PersonGridBean.ImageList> carList = new ArrayList<>();
        for (int i = 0; i < cars.length; i++) {
            PersonGridBean.ImageList bean = new PersonGridBean.ImageList();
            bean.setName(cars[i]);
            bean.setImagePath(iconCars[i]);
            carList.add(bean);
        }
        carBean.setImageList(carList);

        PersonGridBean carHangBean = new PersonGridBean();
        carHangBean.setTitle("关于车行");
        List<PersonGridBean.ImageList> carHangList = new ArrayList<>();
        for (int i = 0; i < carHangs.length; i++) {
            PersonGridBean.ImageList bean = new PersonGridBean.ImageList();
            bean.setName(carHangs[i]);
            bean.setImagePath(iconCarhangs[i]);
            carHangList.add(bean);
        }
        carHangBean.setImageList(carHangList);

        PersonGridBean carWoMenBean = new PersonGridBean();
        carWoMenBean.setTitle("关于我们");
        List<PersonGridBean.ImageList> carWoMenList = new ArrayList<>();
        for (int i = 0; i < womens.length; i++) {
            PersonGridBean.ImageList bean = new PersonGridBean.ImageList();
            bean.setName(womens[i]);
            bean.setImagePath(iconWoMens[i]);
            carWoMenList.add(bean);
        }
        carWoMenBean.setImageList(carWoMenList);

        mList.add(carBean);
        mList.add(carHangBean);
        mList.add(carWoMenBean);

        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mAdapter = new UniversalAdapter<PersonGridBean>(mActivity, mList, R.layout.person_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, int position, PersonGridBean s) {
                holder.setText(R.id.tv_grid_title, s.getTitle());
                MyGridView grid = holder.getView(R.id.person_item_grid);
                final List<PersonGridBean.ImageList> imageList = s.getImageList();
                UniversalAdapter<PersonGridBean.ImageList> adapter = new UniversalAdapter<PersonGridBean.ImageList>(mActivity,
                        imageList, R.layout.person_grid_item) {
                    @Override
                    public void convert(UniversalViewHolder holder, int position, PersonGridBean.ImageList imageBean) {
                        holder.setText(R.id.tv_name, imageBean.getName());
                        holder.setImageResource(R.id.image_icon, imageBean.getImagePath());
                    }
                };
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (imageList.get(i).getName()) {
                            case "发布车源":
                                startActivity(new Intent(mActivity, SendCarActivity.class));
                                break;
                            case "我的车源":
                                startActivity(new Intent(mActivity, MyCarActivity.class));
                                break;
                            case "我的车行":

                                break;
                            case "车行收藏":
                                startActivity(new Intent(mActivity, CarHangCollectActivity.class));
                                break;
                            //车辆关注
                            case "车辆关注":
                                startActivity(new Intent(mActivity, CarCollectActivity.class));
                                break;
                            //赚取佣金
                            case "赚取佣金":
                                startActivity(new Intent(mActivity, MyCarActivity.class));
                                break;
                            //中介认证
                            case "中介认证":

                                break;
                            //客服电话
                            case "客服电话":
                                SystemUtils.call(mActivity, "15075993917");
                                break;
                            //免责声明
                            case "免责声明":
                                Intent intent = new Intent(mActivity, WebActivity.class);
                                intent.putExtra("url", API.MFSM_URL);
                                startActivity(intent);
                                break;
                            //意见反馈
                            case "意见反馈":
                                startActivity(new Intent(mActivity, FeedBackActivity.class));
                                break;
                            //关于我们
                            case "关于我们":
                                Intent intent1 = new Intent(mActivity, WebActivity.class);
                                intent1.putExtra("url", API.GYWM_URL);
                                startActivity(intent1);
                                break;
                        }
                    }
                });
            }
        };
        personListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_setting, R.id.iv_message, R.id.iv_person_icon, R.id.tv_sign_out})
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
            //点击头像（若是未登录状态则进入登录页）
            case R.id.iv_person_icon:
                login();
                break;
            //退出登录
            case R.id.tv_sign_out:
                startActivity(new Intent(mActivity, LoginActivity.class));
                break;
        }
    }

    private void login() {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        intent.putExtra(API.ISFINISH, false);
        startActivity(intent);
    }
}
