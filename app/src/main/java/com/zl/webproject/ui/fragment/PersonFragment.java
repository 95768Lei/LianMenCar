package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.model.PersonGridBean;
import com.zl.webproject.ui.activity.CarCollectActivity;
import com.zl.webproject.ui.activity.CarHangCollectActivity;
import com.zl.webproject.ui.activity.CarShareListActivity;
import com.zl.webproject.ui.activity.FeedBackActivity;
import com.zl.webproject.ui.activity.LoginActivity;
import com.zl.webproject.ui.activity.MyCarActivity;
import com.zl.webproject.ui.activity.MyMotorsActivity;
import com.zl.webproject.ui.activity.RealPersonActivity;
import com.zl.webproject.ui.activity.SendCarActivity;
import com.zl.webproject.ui.activity.SettingsActivity;
import com.zl.webproject.ui.activity.UpdatePasswordActivity;
import com.zl.webproject.ui.activity.WebActivity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.utils.SystemUtils;
import com.zl.webproject.view.ImageTagView;
import com.zl.webproject.view.MyGridView;
import com.zl.webproject.view.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

/**
 * @author zhanglei
 * @date 18/2/22
 */
public class PersonFragment extends BaseFragment {


    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.iv_person_icon)
    CircleImageView ivPersonIcon;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.person_listView)
    MyListView personListView;
    Unbinder unbinder;
    @BindView(R.id.tagView_person)
    ImageTagView tagViewPerson;
    private UniversalAdapter<PersonGridBean> mAdapter;
    private List<PersonGridBean> mList = new ArrayList<>();
    private String[] cars = {"发布车源", "我的车源", "赚取佣金", "车辆关注"};
    private String[] carHangs = {"车行关注", "我的车行", "中介认证",};
    private String[] womens = {"客服电话", "免责声明", "意见反馈", "关于我们"};
    private String[] centers = {"用户信息", "密码修改", "实名认证", "车行认证"};


    private Integer[] iconCars = {R.mipmap.fbcy, R.mipmap.wdcy, R.mipmap.zjcl, R.mipmap.wdsc};
    private Integer[] iconCarhangs = {R.mipmap.wdgz, R.mipmap.wdch, R.mipmap.zj};
    private Integer[] iconWoMens = {R.mipmap.kfdh, R.mipmap.mzsm, R.mipmap.yjfk, R.mipmap.gy};
    private Integer[] iconCenters = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};

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

        PersonGridBean centerBean = new PersonGridBean();
        centerBean.setTitle("个人中心");
        List<PersonGridBean.ImageList> centerList = new ArrayList<>();
        for (int i = 0; i < centers.length; i++) {
            PersonGridBean.ImageList bean = new PersonGridBean.ImageList();
            bean.setName(centers[i]);
            bean.setImagePath(iconCenters[i]);
            centerList.add(bean);
        }
        centerBean.setImageList(centerList);

        mList.add(carBean);
        mList.add(carHangBean);
        mList.add(carWoMenBean);
        mList.add(centerBean);

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
                                SystemUtils.toActivity(mActivity, new Intent(mActivity, SendCarActivity.class));
                                break;
                            case "我的车源":
                                SystemUtils.toActivity(mActivity, new Intent(mActivity, MyCarActivity.class));
                                break;
                            case "我的车行":
                                intoMyMotors();
                                break;
                            case "车行关注":
                                SystemUtils.toActivityForResult(mActivity, new Intent(mActivity, CarHangCollectActivity.class), 0);
                                break;
                            //车辆关注
                            case "车辆关注":
                                SystemUtils.toActivityForResult(mActivity, new Intent(mActivity, CarCollectActivity.class), 1);
                                break;
                            //赚取佣金
                            case "赚取佣金":
                                SystemUtils.toActivityForResult(mActivity, new Intent(mActivity, CarShareListActivity.class), 1);
                                break;
                            //中介认证
                            case "中介认证":
                                applyInfo();
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
                            //用户信息
                            case "用户信息":
                                SystemUtils.toActivity(mActivity, new Intent(mActivity, SettingsActivity.class));
                                break;
                            //密码修改
                            case "密码修改":
                                SystemUtils.toActivity(mActivity, new Intent(mActivity, UpdatePasswordActivity.class));
                                break;
                            //实名认证
                            case "实名认证":
                                SystemUtils.toActivity(mActivity, new Intent(mActivity, RealPersonActivity.class));
                                break;
                            //车行认证
                            case "车行认证":
                                SystemUtils.toActivity(mActivity, new Intent(mActivity, CarShareListActivity.class));
                                break;
                        }
                    }
                });
            }
        };
        personListView.setAdapter(mAdapter);

        updatePerson();
    }

    /**
     * 申请中介
     */
    private void applyInfo() {

        if (!SpUtlis.getLoginData(mActivity).isLogin()) {
            startActivity(new Intent(mActivity, LoginActivity.class));
            return;
        }

        CarUserEntity userData = SpUtlis.getUserData(mActivity);
        if (userData.getUserApply() == 1) {
            showToast("您已经进行了中介认证");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("uid", userData.getId() + "");
        HttpUtils.getInstance().Post(mActivity, params, API.applyInfo, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                tagViewPerson.addImage(R.mipmap.rzzj);
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

    private void intoMyMotors() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        HttpUtils.getInstance().Post(mActivity, params, API.getApproverInfo, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body);
                SystemUtils.toActivity(mActivity, new Intent(mActivity, MyMotorsActivity.class));
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePerson();
    }

    private void updatePerson() {
        boolean login = SpUtlis.getLoginData(mActivity).isLogin();
        if (login) {
            CarUserEntity userData = SpUtlis.getUserData(mActivity);
            tvPersonName.setText(userData.getUserNikeName());
            if (!TextUtils.isEmpty(userData.getUserImg())) {
                ImageLoader.loadImageUrl(mActivity, userData.getUserImg(), ivPersonIcon);
            }
            if (userData.getUserApply() == 1) {
                tagViewPerson.addImage(R.mipmap.rzzj);
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_setting, R.id.iv_person_icon, R.id.tv_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //进入设置
            case R.id.iv_setting:
                SystemUtils.toActivity(mActivity, new Intent(mActivity, SettingsActivity.class));
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

        if (SpUtlis.getLoginData(mActivity).isLogin()) {
            return;
        }

        Intent intent = new Intent(mActivity, LoginActivity.class);
        intent.putExtra(API.ISFINISH, false);
        startActivity(intent);
    }
}
