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
import com.google.gson.Gson;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CarDealerEntity;
import com.zl.webproject.model.CarDealerResourceEntity;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.model.ShareBean;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.ui.dialog.GongNengDialog;
import com.zl.webproject.ui.dialog.ImagePreviewDialog;
import com.zl.webproject.ui.fragment.ImageFragment;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.FragmentHelper;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ImageFactory;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.ShareUtils;
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
 * @author zhanglei
 * @date 18/2/28
 */
public class MyMotorsActivity extends BaseActivity {

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
    private ImageFragment imageFragment;
    private FragmentHelper helper;
    private String carHangIconPath;
    private List<String> imagePaths;
    private CarDealerEntity carDealerEntity;
    private ImagePreviewDialog previewDialog;
    private GongNengDialog gongNengDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_motors);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        tvTitleRight.setText("操作车行");

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
        gongNengDialog.setOnGongNengListener(new GongNengDialog.OnGongNengListener() {
            @Override
            public void share() {
                shareCarDealer();
            }

            @Override
            public void edit() {
                editCarDealer();
            }

            @Override
            public void refresh() {
                showToast("刷新成功");
                initData();
            }
        });
    }

    /**
     * 编辑车行
     */
    private void editCarDealer() {
        Intent intent = new Intent(mActivity, EditMotorsActivity.class);
        intent.putExtra("data", carDealerEntity);
        startActivity(intent);
    }

    /**
     * 分享车行
     */
    private void shareCarDealer() {
        if (carDealerEntity == null) return;
        ShareBean shareBean = new ShareBean();
        shareBean.setShareTitle(carDealerEntity.getDealerName());
        shareBean.setImgUrl(carDealerEntity.getDealerImg());
        shareBean.setShareContent(carDealerEntity.getDealerContext());
        shareBean.setUrl(API.toCarShop + "?did=" + carDealerEntity.getId());
        ShareUtils.share(mActivity, shareBean, new ShareUtils.OnShareListener() {
            @Override
            public void shareSuccess(SHARE_MEDIA share_media) {
                showToast("分享成功");
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
                params.put("did", carDealerEntity.getId() + "");
                HttpUtils.getInstance().Post(mActivity, params, API.toForwardCarDealer, new HttpUtils.OnOkHttpCallback() {
                    @Override
                    public void onSuccess(String body) {

                    }

                    @Override
                    public void onError(Request error, Exception e) {

                    }
                });
            }

            @Override
            public void shareError(SHARE_MEDIA share_media, Throwable throwable) {

            }
        });
    }

    private void initView() {
        addressDialog = new AddressDialog(mActivity);
        tvTitleName.setText("我的车行");
        imageFragment = ImageFragment.newInstance(0);
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText("保存");
        helper = FragmentHelper.builder(mActivity).attach(R.id.motors_car_rl).addFragment(imageFragment).commit();

        previewDialog = new ImagePreviewDialog(mActivity);
        tvTitleRight.setText("提交保存");

        gongNengDialog = new GongNengDialog(mActivity);
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
                if (carDealerEntity != null) {
                    gongNengDialog.showDialog(view);
                } else {
                    commit();
                }
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

        if (carDealerEntity != null) {
            List<CarDealerResourceEntity> resources = carDealerEntity.getResources();
            List<String> list = new ArrayList<String>();
            for (CarDealerResourceEntity resource : resources) {
                list.add(resource.getResDealerUrl());
            }
            previewDialog.setData(list);
            previewDialog.showDialog(etMotorsName);
            return;
        }

        helper.showFragment(imageFragment);
    }

    private void commit() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String motorsName = etMotorsName.getText().toString().trim();
        String address = etCarAddress.getText().toString().trim();
        String content = etCarContent.getText().toString().trim();

        if (imagePaths == null || imagePaths.size() <= 0) {
            showToast("车行轮播图不能为空");
            return;
        }

        if (TextUtils.isEmpty(carHangIconPath)) {
            showToast("车行头像不能为空");
            return;
        }

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
        params.put("did", "");
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
                                        showToast("成功发布车行");
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
