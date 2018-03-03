package com.zl.webproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.ui.fragment.ImageFragment;
import com.zl.webproject.utils.FragmentHelper;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 * @date 18/2/25
 */
public class SendCarActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.send_car_bottom)
    AutoLinearLayout sendCarBottom;
    @BindView(R.id.iv_clear_car_type)
    ImageView ivClearCarType;
    @BindView(R.id.et_car_type)
    EditText etCarType;
    @BindView(R.id.tv_car_type)
    TextView tvCarType;
    @BindView(R.id.tv_bian_su_fang_shi)
    TextView tvBianSuFangShi;
    @BindView(R.id.tv_choose_length)
    TextView tvChooseLength;
    @BindView(R.id.tv_choose_address)
    TextView tvChooseAddress;
    @BindView(R.id.tv_ran_you_type)
    TextView tvRanYouType;
    @BindView(R.id.iv_clear_li_cheng)
    ImageView ivClearLiCheng;
    @BindView(R.id.et_li_cheng)
    EditText etLiCheng;
    @BindView(R.id.iv_clear_car_money)
    ImageView ivClearCarMoney;
    @BindView(R.id.et_car_money)
    EditText etCarMoney;
    @BindView(R.id.iv_clear_car_ding_jin)
    ImageView ivClearCarDingJin;
    @BindView(R.id.et_car_ding_jin)
    EditText etCarDingJin;
    @BindView(R.id.iv_clear_car_yong_jin)
    ImageView ivClearCarYongJin;
    @BindView(R.id.et_car_yong_jin)
    EditText etCarYongJin;
    @BindView(R.id.iv_clear_car_pai_liang)
    ImageView ivClearCarPaiLiang;
    @BindView(R.id.et_car_pai_liang)
    EditText etCarPaiLiang;
    @BindView(R.id.send_car_grid)
    MyGridView sendCarGrid;
    @BindView(R.id.et_car_content)
    EditText etCarContent;
    @BindView(R.id.image_add)
    ImageView imageAdd;
    private AddressDialog addressDialog;
    private FragmentHelper helper;
    private ImageFragment imageFragment;
    private List<String> paths = new ArrayList<>();
    private String iconPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_car);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initListener() {
        imageFragment.setOnImageFragmentListener(new ImageFragment.OnImageFragmentListener() {
            @Override
            public void onHide() {
                helper.hideFragment(imageFragment);
            }

            @Override
            public void onHomeImage(String path) {
                iconPath = path;
                ImageLoader.loadImageFile(mActivity, path, imageAdd);
            }

            @Override
            public void onImageList(List<String> path) {
                paths = path;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFragment.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        addressDialog = new AddressDialog(mActivity);
        tvTitleName.setText("发布车源");
        imageFragment = new ImageFragment();
        helper = FragmentHelper.builder(mActivity).attach(R.id.send_car_rl).addFragment(imageFragment).commit();
    }

    @OnClick({R.id.iv_title_back, R.id.image_add, R.id.iv_clear_car_type, R.id.tv_bian_su_fang_shi, R.id.tv_choose_length,
            R.id.tv_choose_address, R.id.tv_ran_you_type, R.id.iv_clear_li_cheng, R.id.iv_clear_car_money,
            R.id.iv_clear_car_ding_jin, R.id.iv_clear_car_yong_jin, R.id.iv_clear_car_pai_liang, R.id.tv_send_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.iv_title_back:
                finish();
                break;
            //添加图片
            case R.id.image_add:
                addImage();
                break;
            //删除车辆型号
            case R.id.iv_clear_car_type:
                etCarType.setText("");
                break;
            //选择变速方式
            case R.id.tv_bian_su_fang_shi:

                break;
            //选择上牌日期
            case R.id.tv_choose_length:

                break;
            //选择所在地区
            case R.id.tv_choose_address:
                addressDialog.showDialog(view);
                break;
            //选择燃油类型
            case R.id.tv_ran_you_type:

                break;
            //删除行驶里程
            case R.id.iv_clear_li_cheng:
                etLiCheng.setText("");
                break;
            //删除销售价格
            case R.id.iv_clear_car_money:
                etCarMoney.setText("");
                break;
            //删除订车定金
            case R.id.iv_clear_car_ding_jin:
                etCarDingJin.setText("");
                break;
            //删除中介佣金
            case R.id.iv_clear_car_yong_jin:
                etCarYongJin.setText("");
                break;
            //删除车辆排量
            case R.id.iv_clear_car_pai_liang:
                etCarPaiLiang.setText("");
                break;
            //发布车辆
            case R.id.tv_send_car:
                sendCar();
                break;
        }
    }

    /**
     * 发布车辆
     */
    private void sendCar() {

    }

    private void addImage() {
        helper.showFragment(imageFragment);
    }
}
