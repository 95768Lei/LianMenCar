package com.zl.webproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.umeng.socialize.UMShareAPI;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.ui.dialog.ListDialog;
import com.zl.webproject.ui.fragment.ImageFragment;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.FragmentHelper;
import com.zl.webproject.utils.ImageFactory;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.SpUtlis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class EditServiceActivity extends BaseActivity {

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
    @BindView(R.id.et_motors_name)
    EditText etMotorsName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_choose_address)
    TextView tvChooseAddress;
    @BindView(R.id.et_car_address)
    EditText etCarAddress;
    @BindView(R.id.et_car_content)
    EditText etCarContent;
    @BindView(R.id.iv_car_hang_icon)
    ImageView ivCarHangIcon;
    @BindView(R.id.iv_clear_motor_name)
    ImageView ivClearMotorName;
    @BindView(R.id.iv_clear_name)
    ImageView ivClearName;
    @BindView(R.id.iv_clear_phone)
    ImageView ivClearPhone;
    @BindView(R.id.tv_choose_service_type)
    TextView tvChooseServiceType;
    @BindView(R.id.iv_clear_car_address)
    ImageView ivClearCarAddress;
    @BindView(R.id.motors_car_rl)
    RelativeLayout motorsCarRl;
    private AddressDialog addressDialog;
    private CityBean mCityBean;
    private ImageFragment imageFragment;
    private FragmentHelper helper;
    private String carHangIconPath;
    private List<String> imagePaths;
    private ListDialog typeDialog;
    private List<String> services = Arrays.asList("违章处理", "维修服务", "保险服务", "年审服务");
    private int typePositon = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_service);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        typeDialog.setData(services);
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
                ImageLoader.loadImageFile(mActivity, path, imageAdd);
            }

            @Override
            public void onImageList(List<String> path) {
                imagePaths = path;
            }
        });
        typeDialog.setOnSelectorDataListener(new ListDialog.OnSelectorDataListener() {
            @Override
            public void onSelector(String data, int position) {
                tvChooseServiceType.setText(data);
                typePositon = position;
            }
        });
    }

    private void initView() {
        addressDialog = new AddressDialog(mActivity);
        tvTitleName.setText("发布服务");
        imageFragment = ImageFragment.newInstance(0);
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText("发布");
        helper = FragmentHelper.builder(mActivity).attach(R.id.motors_car_rl).addFragment(imageFragment).commit();

        typeDialog = new ListDialog(mActivity, "服务类型");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mActivity).onActivityResult(requestCode, resultCode, data);
        imageFragment.onActivityResult(requestCode, resultCode, data);
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
            R.id.iv_clear_phone, R.id.tv_choose_address, R.id.iv_car_hang_icon, R.id.tv_choose_service_type, R.id.iv_clear_car_address})
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
            case R.id.tv_choose_service_type:
                typeDialog.showDialog(view);
                break;
        }
    }

    private void addImage() {
        helper.showFragment(imageFragment);
    }

    private void commit() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String motorsName = etMotorsName.getText().toString().trim();
        String address = etCarAddress.getText().toString().trim();
        String content = etCarContent.getText().toString().trim();
        String serviceType = tvChooseServiceType.getText().toString().trim();

        if (imagePaths == null || imagePaths.size() <= 0) {
            showToast("服务轮播图不能为空");
            return;
        }

        if (TextUtils.isEmpty(carHangIconPath)) {
            showToast("服务头像不能为空");
            return;
        }

        if (mCityBean == null) {
            showToast("服务所在区域不能为空");
            return;
        }

        if (TextUtils.isEmpty(serviceType)) {
            showToast("服务类型未被获取，请退出后重试");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            showToast("联系人姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(motorsName)) {
            showToast("服务名称不能为空");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            showToast("服务地址不能为空");
            return;
        }

        if (TextUtils.isEmpty(content)) {
            showToast("服务描述不能为空");
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
        params.put("serUserId", userData.getId() + "");
        params.put("userPhone", userData.getUserPhone() + "");
        params.put("serHotTop", "0");
        switch (serviceType) {
            case "违章处理":
                params.put("serType", "1");
                break;
            case "维修服务":
                params.put("serType", "2");
                break;
            case "保险服务":
                params.put("serType", "3");
                break;
            case "年审服务":
                params.put("serType", "4");
                break;

        }
        params.put("serTitle", motorsName);
        params.put("userName", name);
        params.put("serContext", content);
        params.put("serAddress", address);
        params.put("serCityId", mCityBean.getCityCode());

        pDialog.setTitleText("发布中...");
        pDialog.show();
        new Thread() {
            @Override
            public void run() {
                //添加请求参数
                params.put("delResources", "");
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
                byte[] getimage = ImageFactory.getimage(carHangIconPath);
                final RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), getimage);
                String fileName = carHangIconPath.substring(carHangIconPath.lastIndexOf("/"));
                builder.addFormDataPart("fileImg", fileName, body);

                //创建请求体
                Request request = new Request.Builder().url(API.saveOrUpdateServerInfo).post(builder.build()).build();

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
                                        showToast("成功发布服务");
                                        //储存车行id
                                        JSONObject data = jsonObject.optJSONObject("data");
                                        int id = data.optInt("id");
                                        CarUserEntity userData = SpUtlis.getUserData(mActivity);
                                        userData.setCarDealerId(id);
                                        SpUtlis.setUserData(mActivity, userData);
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
