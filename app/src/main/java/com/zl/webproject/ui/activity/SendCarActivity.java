package com.zl.webproject.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.model.CarDictionaryEntity;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.ui.dialog.AddressDialog;
import com.zl.webproject.ui.dialog.ListDialog;
import com.zl.webproject.ui.fragment.ImageFragment;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.FragmentHelper;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ImageFactory;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.view.WrapLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @BindView(R.id.et_car_content)
    EditText etCarContent;
    @BindView(R.id.image_add)
    ImageView imageAdd;
    @BindView(R.id.send_car_wrap)
    WrapLayout sendCarWrap;
    @BindView(R.id.tv_send_car)
    TextView tvSendCar;
    @BindView(R.id.rb_ok)
    RadioButton rbOk;
    @BindView(R.id.rb_no)
    RadioButton rbNo;
    @BindView(R.id.rg_ok_no)
    RadioGroup rgOkNo;
    @BindView(R.id.send_car_rl)
    RelativeLayout sendCarRl;
    @BindView(R.id.send_car_wrap_no)
    WrapLayout sendCarWrapNo;
    @BindView(R.id.tv_tag_suo_ding)
    TextView tvTagSuoDing;
    @BindView(R.id.tv_tag_cha_feng)
    TextView tvTagChaFeng;
    @BindView(R.id.tv_tag_wei_zhang)
    TextView tvTagWeiZhang;
    private AddressDialog addressDialog;
    private FragmentHelper helper;
    private ImageFragment imageFragment;
    private List<String> paths = new ArrayList<>();
    private List<CarDictionaryEntity> carTypeList = new ArrayList<>();
    private List<CarDictionaryEntity> speedTypeList = new ArrayList<>();
    private List<CarDictionaryEntity> fuelTypeList = new ArrayList<>();
    //不可过户数据
    private List<CarDictionaryEntity> noTypeList = new ArrayList<>();
    //可过户数据
    private List<CarDictionaryEntity> okTypeList = new ArrayList<>();
    private String iconPath = "";
    private ListDialog carTypeDialog;
    private ListDialog speedTypeDialog;
    private ListDialog fuelTypeDialog;
    private TimePickerView pvTime;
    private String upDate;
    private int carType, speedType, fuelType = -1;
    private CityBean mCityBean;
    private CarDictionaryEntity carDictionaryEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_car);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        //获取选择 车辆类型、变速方式、燃油类型数据
        getTagData();
    }

    private void getTagData() {
        Map<String, String> params = new HashMap<>();
        params.put("data", "");
        HttpUtils.getInstance().Post(mActivity, params, API.getRetrieval, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    JSONArray array = new JSONArray(body);
                    //变速方式
                    JSONObject object = array.optJSONObject(0);
                    JSONArray speedType = object.optJSONArray("value");
                    List<String> speedList = new ArrayList<String>();
                    for (int i = 0; i < speedType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(speedType.opt(i).toString(), CarDictionaryEntity.class);
                        speedTypeList.add(entity);
                        speedList.add(entity.getDictName());
                    }
                    speedTypeDialog.setData(speedList);
                    //汽车类型
                    JSONObject object1 = array.optJSONObject(1);
                    JSONArray carType = object1.optJSONArray("value");
                    List<String> carList = new ArrayList<String>();
                    for (int i = 0; i < carType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(carType.opt(i).toString(), CarDictionaryEntity.class);
                        carTypeList.add(entity);
                        carList.add(entity.getDictName());
                    }
                    carTypeDialog.setData(carList);
                    //燃油类型
                    JSONObject object2 = array.optJSONObject(6);
                    JSONArray fuelType = object2.optJSONArray("value");
                    List<String> fuelList = new ArrayList<String>();
                    for (int i = 0; i < fuelType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(fuelType.opt(i).toString(), CarDictionaryEntity.class);
                        fuelTypeList.add(entity);
                        fuelList.add(entity.getDictName());
                    }
                    fuelTypeDialog.setData(fuelList);
                    //可不可过户数据
                    JSONObject object3 = array.optJSONObject(2);
                    JSONArray huType = object3.optJSONArray("value");
                    List<String> noList = new ArrayList<String>();
                    List<String> okList = new ArrayList<String>();
                    JSONObject okJson = huType.optJSONObject(0);
                    JSONObject noJson = huType.optJSONObject(1);
                    JSONArray okArray = okJson.optJSONArray("dictionaryList");
                    JSONArray noArray = noJson.optJSONArray("dictionaryList");
                    for (int i = 0; i < okArray.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(okArray.opt(i).toString(), CarDictionaryEntity.class);
                        okList.add(entity.getDictName());
                        okTypeList.add(entity);
                    }
                    for (int i = 0; i < noArray.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(noArray.opt(i).toString(), CarDictionaryEntity.class);
                        noList.add(entity.getDictName());
                        noTypeList.add(entity);
                    }
                    sendCarWrap.setData(okList, mActivity, 12, 10, 6, 10, 6, 6, 6, 6, 6);
                    sendCarWrapNo.setData(noList, mActivity, 12, 10, 6, 10, 6, 6, 6, 6, 6);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
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

        carTypeDialog.setOnSelectorDataListener(new ListDialog.OnSelectorDataListener() {
            @Override
            public void onSelector(String data, int position) {
                carType = position;
                tvCarType.setText(data);
            }
        });
        speedTypeDialog.setOnSelectorDataListener(new ListDialog.OnSelectorDataListener() {
            @Override
            public void onSelector(String data, int position) {
                speedType = position;
                tvBianSuFangShi.setText(data);
            }
        });
        fuelTypeDialog.setOnSelectorDataListener(new ListDialog.OnSelectorDataListener() {
            @Override
            public void onSelector(String data, int position) {
                fuelType = position;
                tvRanYouType.setText(data);
            }
        });
        addressDialog.setOnAddressDataListener(new AddressDialog.OnAddressDataListener() {
            @Override
            public void addressData(CityBean cityBean) {
                mCityBean = cityBean;
                tvChooseAddress.setText(cityBean.getCityName());
                addressDialog.dismissDialog();
            }
        });

        rgOkNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_ok:
                        sendCarWrap.setVisibility(View.VISIBLE);
                        sendCarWrapNo.setVisibility(View.GONE);
                        break;
                    case R.id.rb_no:
                        sendCarWrap.setVisibility(View.GONE);
                        sendCarWrapNo.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });
//        sendCarWrap.setMarkClickListener(new WrapLayout.MarkClickListener() {
//            @Override
//            public void clickMark(int position) {
//                carDictionaryEntity = okTypeList.get(position);
//            }
//        });
//        sendCarWrapNo.setMarkClickListener(new WrapLayout.MarkClickListener() {
//            @Override
//            public void clickMark(int position) {
//                carDictionaryEntity = noTypeList.get(position);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (imageFragment.isHidden()) {
                new AlertDialog.Builder(mActivity).setMessage("退出后信息将无法保存，是否退出").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            } else {
                helper.hideFragment(imageFragment);
            }
        }
        return true;
    }

    private void initView() {
        addressDialog = new AddressDialog(mActivity);
        tvTitleName.setText("发布车源");
        imageFragment = ImageFragment.newInstance(1);
        helper = FragmentHelper.builder(mActivity).attach(R.id.send_car_rl).addFragment(imageFragment).commit();

        carTypeDialog = new ListDialog(mActivity, "车辆类型");
        speedTypeDialog = new ListDialog(mActivity, "变速方式");
        fuelTypeDialog = new ListDialog(mActivity, "燃油类型");

        //选中事件回调
        pvTime = new TimePickerView.Builder(mActivity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                upDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                tvChooseLength.setText(upDate);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
        pvTime.setDate(Calendar.getInstance());

        rgOkNo.check(R.id.rb_ok);
    }

    @OnClick({R.id.iv_title_back, R.id.image_add, R.id.tv_car_type, R.id.iv_clear_car_type, R.id.tv_bian_su_fang_shi,
            R.id.tv_choose_length, R.id.tv_choose_address, R.id.tv_ran_you_type, R.id.iv_clear_li_cheng, R.id.iv_clear_car_money,
            R.id.iv_clear_car_ding_jin, R.id.iv_clear_car_yong_jin, R.id.iv_clear_car_pai_liang, R.id.tv_send_car, R.id.tv_tag_suo_ding,
            R.id.tv_tag_cha_feng, R.id.tv_tag_wei_zhang})
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
            //车辆类型
            case R.id.tv_car_type:
                carTypeDialog.showDialog(view);
                break;
            //删除车辆型号
            case R.id.iv_clear_car_type:
                etCarType.setText("");
                break;
            //选择变速方式
            case R.id.tv_bian_su_fang_shi:
                speedTypeDialog.showDialog(view);
                break;
            //选择上牌日期
            case R.id.tv_choose_length:
                pvTime.show();
                break;
            //选择所在地区
            case R.id.tv_choose_address:
                addressDialog.showDialog(view);
                break;
            //选择燃油类型
            case R.id.tv_ran_you_type:
                fuelTypeDialog.showDialog(view);
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
            //锁定
            case R.id.tv_tag_suo_ding:
                if (tvTagSuoDing.isSelected()) {
                    tvTagSuoDing.setSelected(false);
                } else {
                    tvTagSuoDing.setSelected(true);
                }
                tvTagChaFeng.setSelected(false);
                tvTagWeiZhang.setSelected(false);
                break;
            //查封
            case R.id.tv_tag_cha_feng:
                if (tvTagChaFeng.isSelected()) {
                    tvTagChaFeng.setSelected(false);
                } else {
                    tvTagChaFeng.setSelected(true);
                }
                tvTagSuoDing.setSelected(false);
                tvTagWeiZhang.setSelected(false);
                break;
            //违章
            case R.id.tv_tag_wei_zhang:
                if (tvTagWeiZhang.isSelected()) {
                    tvTagWeiZhang.setSelected(false);
                } else {
                    tvTagWeiZhang.setSelected(true);
                }
                tvTagSuoDing.setSelected(false);
                tvTagChaFeng.setSelected(false);
                break;
        }
    }

    /**
     * 发布车辆
     */
    private void sendCar() {
        String paiLiang = etCarPaiLiang.getText().toString().trim();
        String carType = etCarType.getText().toString().trim();
        String carMoney = etCarMoney.getText().toString().trim();
        String carDingJin = etCarDingJin.getText().toString().trim();
        String carYongJin = etCarYongJin.getText().toString().trim();
        String carContent = etCarContent.getText().toString().trim();
        String carLiCheng = etLiCheng.getText().toString().trim();

        if (TextUtils.isEmpty(carType)) {
            showToast("车辆型号不能为空");
            return;
        }
        if (this.carType == -1) {
            showToast("请选择车辆类型");
            return;
        }
        if (speedType == -1) {
            showToast("请选择变速方式");
            return;
        }
        if (TextUtils.isEmpty(upDate)) {
            showToast("请选择上牌日期");
            return;
        }
        if (mCityBean == null) {
            showToast("请选择城市");
            return;
        }
        if (fuelType == -1) {
            showToast("请选择燃油类型");
            return;
        }
        if (TextUtils.isEmpty(carLiCheng)) {
            showToast("车辆里程不能为空");
            return;
        }

        if (TextUtils.isEmpty(carMoney)) {
            showToast("车辆价格不能为空");
            return;
        }
        if (TextUtils.isEmpty(carDingJin)) {
            showToast("车辆定金不能为空");
            return;
        }
        if (TextUtils.isEmpty(carYongJin)) {
            showToast("车辆佣金不能为空");
            return;
        }
        if (TextUtils.isEmpty(paiLiang)) {
            showToast("排量不能为空");
            return;
        }

        if (TextUtils.isEmpty(carContent)) {
            showToast("车辆描述不能为空");
            return;
        }
        if (TextUtils.isEmpty(iconPath)) {
            showToast("车辆首图不能为空");
            return;
        }
        if (paths.size() <= 0) {
            showToast("车辆轮播图不能为空");
            return;
        }

        CarUserEntity userData = SpUtlis.getUserData(mActivity);
        final Map<String, String> params = new HashMap<>();
        params.put("uid", userData.getId() + "");
        params.put("userPhone", userData.getUserPhone());
        params.put("did", userData.getCarDealerId() == -1 ? "" : userData.getCarDealerId() + "");
        params.put("carBrandName", carType);
        params.put("carLv", carTypeList.get(this.carType).getId() + "");
        params.put("carGearboxId", speedTypeList.get(speedType).getId() + "");
        params.put("carLicensingDate", upDate);
        params.put("carMileage", carLiCheng);
        params.put("carDisplacement", paiLiang);
        params.put("carFuel", fuelTypeList.get(fuelType).getId() + "");
        params.put("carLocation", mCityBean.getCityName());
        params.put("carLocationCitys", mCityBean.getCityCode());
        params.put("carPrice", carMoney);
        params.put("carDeposit", carDingJin);
        params.put("carCommission", carYongJin);
        params.put("carContext", carContent);
        int id = rgOkNo.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rb_ok:
                params.put("carLabelType", "128");
                if (sendCarWrap.getPosition() == -1) {
                    showToast("请选择车辆状态");
                    return;
                }
                params.put("carLabel", okTypeList.get(sendCarWrap.getPosition()).getId() + "");
                break;
            case R.id.rb_no:
                params.put("carLabelType", "129");
                if (sendCarWrapNo.getPosition() == -1) {
                    showToast("请选择车辆状态");
                    return;
                }
                params.put("carLabel", noTypeList.get(sendCarWrapNo.getPosition()).getId() + "");
                break;

        }
        if (tvTagSuoDing.isSelected()) {
            params.put("carLocking", "1");
        } else {
            params.put("carLocking", "0");
        }
        if (tvTagChaFeng.isSelected()) {
            params.put("carSeized", "1");
        } else {
            params.put("carSeized", "0");
        }
        if (tvTagWeiZhang.isSelected()) {
            params.put("carPeccancy", "1");
        } else {
            params.put("carPeccancy", "0");
        }

        params.put("delResources", "");

        new Thread() {
            @Override
            public void run() {
                //添加请求参数
                MultipartBody.Builder builder = new MultipartBody.Builder();
                Set<Map.Entry<String, String>> entries = params.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
                //轮播图
                for (String s : paths) {
                    byte[] getimage = ImageFactory.getimage(s);
                    final RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), getimage);
                    String fileName = s.substring(s.lastIndexOf("/"));
                    builder.addFormDataPart("file", fileName, body);
                }
                //列表展示图
                byte[] getimage = ImageFactory.getimage(iconPath);
                final RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), getimage);
                String fileName = iconPath.substring(iconPath.lastIndexOf("/"));
                builder.addFormDataPart("fileImg", fileName, body);

                //创建请求体
                Request request = new Request.Builder().url(API.saveTempImg).post(builder.build()).build();

                //加入任务调度
                new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String string = response.body().string();
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(string);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                boolean result = jsonObject.optBoolean("result");
                                String jsonData = jsonObject.optString("data");
                                if (result) {

                                } else {

                                }
                            }
                        });
                    }
                });
            }
        }.start();

    }

    private void addImage() {
        helper.showFragment(imageFragment);
    }
}
