package com.zl.webproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.google.gson.Gson;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CarDealerEntity;
import com.zl.webproject.model.CarDealerResourceEntity;
import com.zl.webproject.model.CarResourceEntity;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.ui.dialog.ImagePreviewDialog;
import com.zl.webproject.ui.fragment.ImageFragment;
import com.zl.webproject.ui.fragment.UpdateImageFragment;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.FragmentHelper;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ImageFactory;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.SpUtlis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.foamtrace.photopicker.PhotoPickerActivity.EXTRA_RESULT;

/**
 * Created by Administrator on 2018/3/7.
 */

public class EditMotorsActivity extends BaseActivity {
    public static final int REQUEST_CAMERA_CODE = 12;
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
    @BindView(R.id.iv_car_hang_icon)
    ImageView ivCarHangIcon;
    @BindView(R.id.motors_car_rl)
    RelativeLayout motorsCarRl;
    private AddressDialog addressDialog;
    private CityBean mCityBean;
    private UpdateImageFragment updateImageFragment = new UpdateImageFragment();
    private FragmentHelper helper;
    private String carHangIconPath;
    private List<String> imagePaths = new ArrayList<>();
    private List<String> deleteList = new ArrayList<>();
    private CarDealerEntity carDealerEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_motors);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        carDealerEntity = (CarDealerEntity) getIntent().getSerializableExtra("data");
        updateUi();
//        Map<String, String> params = new HashMap<>();
//        params.put("did", SpUtlis.getUserData(mActivity).getCarDealerId() + "");
//        params.put("isSee", true + "");
//        HttpUtils.getInstance().Post(mActivity, params, API.getCarDealerById, new HttpUtils.OnOkHttpCallback() {
//            @Override
//            public void onSuccess(String body) {
//                carDealerEntity = new Gson().fromJson(body, CarDealerEntity.class);
//                updateUi();
//            }
//
//            @Override
//            public void onError(Request error, Exception e) {
//
//            }
//        });
    }

    /**
     * 更新UI
     */
    private void updateUi() {
        if (carDealerEntity == null) {
            return;
        }

        ImageLoader.loadImageUrl(mActivity, carDealerEntity.getResources().get(0).getResDealerUrl(), imageAdd);
        ImageLoader.loadImageUrl(mActivity, carDealerEntity.getDealerImg(), ivCarHangIcon);
        ivCarHangIcon.setEnabled(true);
        etMotorsName.setText(carDealerEntity.getDealerName());
        etCarAddress.setText(carDealerEntity.getDealerLocation());
        etName.setText(carDealerEntity.getDealerUser());
        etPhone.setText(carDealerEntity.getDealerPhone());
        tvChooseAddress.setText(carDealerEntity.getCity().getCityName());
        etCarContent.setText(carDealerEntity.getDealerContext());

        List<CarDealerResourceEntity> resources = carDealerEntity.getResources();
        ArrayList<String> images = new ArrayList<>();
        for (CarDealerResourceEntity resource : resources) {
            images.add(resource.getResDealerUrl());
        }
        updateImageFragment = UpdateImageFragment.newInstance(images);
        helper = FragmentHelper.builder(mActivity).attach(R.id.motors_car_rl).addFragment(updateImageFragment).commit();
        updateImageFragment.setOnImageFragmentListener(new UpdateImageFragment.OnImageFragmentListener() {
            @Override
            public void onHide() {
                helper.hideFragment(updateImageFragment);
            }

            @Override
            public void onImageList(List<String> newPath, List<String> deletePath) {
                imagePaths = newPath;
                deleteList = deletePath;
            }
        });

        mCityBean = new CityBean();
        mCityBean.setCityCode(carDealerEntity.getCity().getCityCode());
        mCityBean.setCityName(carDealerEntity.getCity().getCityName());
    }

    private void initListener() {
        addressDialog.setOnAddressDataListener(new AddressDialog.OnAddressDataListener() {
            @Override
            public void addressData(CityBean cityBean) {
                mCityBean = cityBean;
                tvChooseAddress.setText(cityBean.getCityName());
            }
        });
    }

    private void initView() {
        addressDialog = new AddressDialog(mActivity);
        tvTitleName.setText("我的车行");
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText("保存编辑");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateImageFragment.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> pathList = data.getStringArrayListExtra(EXTRA_RESULT);
                    carHangIconPath = pathList.get(0);
                    ImageLoader.loadImageFile(mActivity, carHangIconPath, ivCarHangIcon);
                    break;
            }
        }
    }

    @OnClick({R.id.iv_title_back, R.id.image_add, R.id.tv_title_right, R.id.iv_clear_motor_name, R.id.iv_clear_name,
            R.id.iv_clear_phone, R.id.tv_choose_address, R.id.iv_car_hang_icon, R.id.iv_clear_car_address})
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
            case R.id.iv_car_hang_icon:
                openSingleAlbum(REQUEST_CAMERA_CODE);
                break;
            case R.id.iv_clear_car_address:
                etCarAddress.setText("");
                break;
        }
    }

    private void addImage() {
        helper.showFragment(updateImageFragment);
    }

    private void commit() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String motorsName = etMotorsName.getText().toString().trim();
        String address = etCarAddress.getText().toString().trim();
        String content = etCarContent.getText().toString().trim();

        if (mCityBean == null) {
            showToast("车行所在区域不能为空");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            showToast("联系人姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(motorsName)) {
            showToast("车行名称不能为空");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            showToast("车行地址不能为空");
            return;
        }


        if (TextUtils.isEmpty(content)) {
            showToast("车行描述不能为空");
            return;
        }


        if (TextUtils.isEmpty(phone)) {
            showToast("联系方式不能为空");
            return;
        }

        if (!RegexUtils.isMobileExact(phone) && RegexUtils.isTel(phone)) {
            showToast("请输入正确联系方式");
            return;
        }

        final Map<String, String> params = new HashMap<>();
        CarUserEntity userData = SpUtlis.getUserData(mActivity);
        params.put("uid", userData.getId() + "");
        params.put("userPhone", userData.getUserPhone() + "");
        params.put("did", userData.getCarDealerId() + "");
        params.put("dealerName", motorsName);
        params.put("userName", name);
        params.put("phone", phone);
        params.put("detail", content);
        params.put("location", address);
        params.put("cityCode", mCityBean.getCityCode());

        new Thread() {
            @Override
            public void run() {
                //添加请求参数
                //找到要删除的图片
                StringBuilder sBuilder = new StringBuilder();
                if (!TextUtils.isEmpty(carHangIconPath)) {
                    sBuilder.append(carDealerEntity.getDealerImg())
                            .append(",");
                }
                if (deleteList != null && deleteList.size() >= 0) {
                    for (String s : deleteList) {
                        sBuilder.append(s)
                                .append(",");
                    }
                }
                String str = sBuilder.toString();
                if (TextUtils.isEmpty(str)) {
                    params.put("delResources", "");
                } else {
                    String delResources = str.substring(0, (str.length() - 1));
                    params.put("delResources", delResources);
                }
                MultipartBody.Builder builder = new MultipartBody.Builder();
                Set<Map.Entry<String, String>> entries = params.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
                //轮播图
                for (String s : imagePaths) {
                    byte[] getimage = ImageFactory.getimage(s);
                    final RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), getimage);
                    String fileName = s.substring(s.lastIndexOf("/"));
                    builder.addFormDataPart("file", fileName, body);
                }
                //列表展示图
                if (!TextUtils.isEmpty(carHangIconPath)) {
                    byte[] getimage = ImageFactory.getimage(carHangIconPath);
                    final RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), getimage);
                    String fileName = carHangIconPath.substring(carHangIconPath.lastIndexOf("/"));
                    builder.addFormDataPart("fileImg", fileName, body);
                }

                //创建请求体
                Request request = new Request.Builder().url(API.saveOrUpdateCarDealer).post(builder.build()).build();

                //加入任务调度
                new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pDialog.hide();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String string = response.body().string();
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    pDialog.hide();
                                    JSONObject jsonObject = new JSONObject(string);
                                    boolean result = jsonObject.optBoolean("result");
                                    if (result) {
                                        showToast("成功修改车行");
                                        finish();
                                    } else {
                                        showToast(jsonObject.optString("msg"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    showToast("发生了未知的错误");
                                }
                            }
                        });
                    }
                });
            }
        }.start();
    }
}
