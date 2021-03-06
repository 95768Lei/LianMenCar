package com.zl.webproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foamtrace.photopicker.ImageCaptureManager;
import com.google.gson.Gson;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CarDealerEntity;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.SpUtlis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.foamtrace.photopicker.PhotoPickerActivity.EXTRA_RESULT;

public class RealCarDealerActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.iv_person_front)
    ImageView ivPersonFront;
    @BindView(R.id.iv_person_back)
    ImageView ivPersonBack;
    @BindView(R.id.iv_license)
    ImageView ivLicense;
    @BindView(R.id.tv_commit_real)
    TextView tvCommitReal;
    private String frontPath, backPath, licensePath;
    private CarDealerEntity carDealerEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_car_dealer);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("did", SpUtlis.getUserData(mActivity).getCarDealerId() + "");
        params.put("isSee", true + "");
        HttpUtils.getInstance().Post(mActivity, params, API.getCarDealerById, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                carDealerEntity = new Gson().fromJson(body, CarDealerEntity.class);
                updateUi();
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

    /**
     * 更新UI
     */
    private void updateUi() {
        if (TextUtils.isEmpty(carDealerEntity.getDealerBusinessLicense())) {
            showToast("您还未进行车行认证");
            return;
        }

        ImageLoader.loadImageUrl(mActivity, carDealerEntity.getDealerBusinessLicense(), ivLicense);
        ImageLoader.loadImageUrl(mActivity, carDealerEntity.getDealerLegalIdP(), ivPersonFront);
        ImageLoader.loadImageUrl(mActivity, carDealerEntity.getDealerLegalIdN(), ivPersonBack);
        ivLicense.setEnabled(false);
        ivPersonFront.setEnabled(false);
        ivPersonBack.setEnabled(false);
        tvCommitReal.setEnabled(false);
    }

    private void initView() {
        tvTitleName.setText("认证车行");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10:
                    frontPath = data.getStringExtra("path");
                    ImageLoader.loadImageFile(mActivity, frontPath, ivPersonFront);
                    break;
                case 11:
                    backPath = data.getStringExtra("path");
                    ImageLoader.loadImageFile(mActivity, backPath, ivPersonBack);
                    break;
                case 12:
                    ArrayList<String> list = data.getStringArrayListExtra(EXTRA_RESULT);
                    licensePath = list.get(0);
                    ImageLoader.loadImageFile(mActivity, licensePath, ivLicense);
                    break;
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    ArrayList<String> list1 = data.getStringArrayListExtra(EXTRA_RESULT);
                    licensePath = list1.get(0);
                    ImageLoader.loadImageFile(mActivity, licensePath, ivLicense);
                    break;
            }
        }
    }

    @OnClick({R.id.iv_title_back, R.id.iv_person_front, R.id.iv_person_back, R.id.tv_commit_real, R.id.iv_license})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_commit_real:
                commit();
                break;
            case R.id.iv_person_front:
                Intent intent = new Intent(mActivity, CameraActivity.class);
                intent.putExtra("type", 0);
                startActivityForResult(intent, 10);
                break;
            case R.id.iv_person_back:
                Intent intent1 = new Intent(mActivity, CameraActivity.class);
                intent1.putExtra("type", 1);
                startActivityForResult(intent1, 11);
                break;
            case R.id.iv_license:
                openSingleAlbum(12);
                break;
        }
    }

    private void commit() {

        if (TextUtils.isEmpty(frontPath) || TextUtils.isEmpty(backPath)) {
            showToast("请上传身份证正反面");
            return;
        }

        if (TextUtils.isEmpty(licensePath)) {
            showToast("请上传营业执照");
            return;
        }

        CarUserEntity userData = SpUtlis.getUserData(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("uid", userData.getId() + "");
        params.put("did", userData.getCarDealerId() + "");
        List<String> list = new ArrayList<>();
        list.add(frontPath);
        list.add(backPath);
        list.add(licensePath);
        pDialog.setTitleText("认证中...");
        pDialog.show();
        HttpUtils.getInstance().upLoadFile(mActivity, params, API.replenishCarDealerInfo, list, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                pDialog.hide();
                Log.e("body", body);
            }

            @Override
            public void onError(Request error, Exception e) {
                pDialog.hide();
               showToast(e.getMessage());
            }
        });
    }


}
