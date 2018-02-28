package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.ui.fragment.ImageFragment;
import com.zl.webproject.utils.FragmentHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 * @date 18/2/28
 */
public class MyMotorsActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.image_add)
    ImageView imageAdd;
    @BindView(R.id.iv_clear_motor_name)
    ImageView ivClearMotorName;
    @BindView(R.id.et_motors_name)
    EditText etMotorsName;
    @BindView(R.id.iv_clear_name)
    ImageView ivClearName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_clear_phone)
    ImageView ivClearPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_choose_address)
    TextView tvChooseAddress;
    @BindView(R.id.iv_clear_car_address)
    ImageView ivClearCarAddress;
    @BindView(R.id.et_car_address)
    EditText etCarAddress;
    @BindView(R.id.et_car_content)
    EditText etCarContent;
    private AddressDialog addressDialog;
    private CityBean mCityBean;
    private ImageFragment imageFragment;
    private FragmentHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_motors);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initListener() {
        addressDialog.setOnAddressDataListener(new AddressDialog.OnAddressDataListener() {
            @Override
            public void addressData(CityBean cityBean) {
                mCityBean = cityBean;
                tvChooseAddress.setText(cityBean.getCityName());
            }
        });
        imageFragment.setOnImageFragmentListener(new ImageFragment.OnImageFragmentListener() {
            @Override
            public void onHide() {
                helper.hideFragment(imageFragment);
            }

            @Override
            public void onHomeImage(String path) {

            }

            @Override
            public void onImageList(List<String> path) {

            }
        });
    }

    private void initView() {
        addressDialog = new AddressDialog(mActivity);
        tvTitleName.setText("我的车行");
        imageFragment = new ImageFragment();
        helper = FragmentHelper.builder(mActivity).attach(R.id.motors_car_rl).addFragment(imageFragment).commit();
    }

    @OnClick({R.id.iv_title_back, R.id.image_add, R.id.tv_title_right, R.id.iv_clear_motor_name, R.id.iv_clear_name,
            R.id.iv_clear_phone, R.id.tv_choose_address, R.id.iv_clear_car_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.image_add:
                addImage();
                break;
            case R.id.tv_title_right:
                commit();
                break;
            case R.id.iv_clear_motor_name:
                etMotorsName.setText("");
                break;
            case R.id.iv_clear_name:
                etName.setText("");
                break;
            case R.id.iv_clear_phone:
                etPhone.setText("");
                break;
            case R.id.tv_choose_address:
                addressDialog.showDialog(view);
                break;
            case R.id.iv_clear_car_address:
                etCarAddress.setText("");
                break;
        }
    }

    private void addImage() {
        helper.showFragment(imageFragment);
    }

    private void commit() {

    }
}
