package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.RegexUtils;
import com.google.gson.Gson;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.model.CarIntermediaryEntity;
import com.zl.webproject.ui.activity.CameraActivity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.ImageLoader;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * @author zhanglei
 *         实名认证
 */
public class RealPersonFragment extends BaseFragment {


    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.et_input_realName)
    EditText etInputRealName;
    @BindView(R.id.et_input_idNumber)
    EditText etInputIdNumber;
    @BindView(R.id.tv_person_date)
    TextView tvPersonDate;
    @BindView(R.id.iv_person_front)
    ImageView ivPersonFront;
    @BindView(R.id.iv_person_back)
    ImageView ivPersonBack;
    @BindView(R.id.tv_commit_update)
    TextView tvCommitUpdate;
    Unbinder unbinder;
    private String frontPath;
    private String backPath;
    private TimePickerView pvTime;
    private String birth;
    private CarIntermediaryEntity entity;

    public RealPersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_real_person, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        view.setClickable(true);
        return view;
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        HttpUtils.getInstance().Post(mActivity, params, API.getApproverInfo, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                Log.e("body", body);
                updateUi(body);
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

    /**
     * 更新Ui
     *
     * @param body
     */
    private void updateUi(String body) {
        tvCommitUpdate.setVisibility(View.GONE);
        entity = new Gson().fromJson(body, CarIntermediaryEntity.class);
        etInputIdNumber.setText(entity.getInserNo());
        etInputRealName.setText(entity.getInserName());
        tvPersonDate.setText(entity.getInserDate());
        etInputIdNumber.setEnabled(false);
        etInputRealName.setEnabled(false);
        tvPersonDate.setEnabled(false);
        ImageLoader.loadImageUrl(mActivity, entity.getInserPositiveImg(), ivPersonFront);
        ImageLoader.loadImageUrl(mActivity, entity.getInserOppositeImg(), ivPersonBack);
    }

    private void initView() {
        tvTitleName.setText("实名认证");
        //时间选择器
        //选中事件回调
        pvTime = new TimePickerView.Builder(mActivity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                birth = new SimpleDateFormat("yyyy-MM-dd").format(date);
                tvPersonDate.setText(birth);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
        pvTime.setDate(Calendar.getInstance());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mActivity.RESULT_OK) {
            switch (requestCode) {
                case 10:
                    frontPath = data.getStringExtra("path");
                    ImageLoader.loadImageFile(mActivity, frontPath, ivPersonFront);
                    break;
                case 11:
                    backPath = data.getStringExtra("path");
                    ImageLoader.loadImageFile(mActivity, backPath, ivPersonBack);
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_title_back, R.id.tv_person_date, R.id.iv_person_front, R.id.iv_person_back, R.id.tv_commit_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                mActivity.finish();
                break;
            case R.id.tv_person_date:
                pvTime.show();
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
            case R.id.tv_commit_update:
                commit();
                break;
        }
    }

    private void commit() {
        final String realName = etInputRealName.getText().toString().trim();
        final String idNumber = etInputIdNumber.getText().toString().trim();

        if (TextUtils.isEmpty(realName)) {
            showToast("真实姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(idNumber)) {
            showToast("身份证号不能为空");
            return;
        }
        if (!RegexUtils.isIDCard18(idNumber)) {
            showToast("请输入正确身份证号");
            return;
        }
        if (TextUtils.isEmpty(birth)) {
            showToast("出生日期不能为空");
            return;
        }

        if (TextUtils.isEmpty(frontPath) || TextUtils.isEmpty(backPath)) {
            showToast("请上传身份证正反面");
            return;
        }

        final List<String> mList = new ArrayList<>();
        mList.add(frontPath);
        mList.add(backPath);

        final Map<String, String> params = new HashMap<>();
        params.put("uid", SpUtlis.getUserData(mActivity).getId() + "");
        params.put("userName", realName);
        params.put("userDate", birth);
        params.put("userNo", idNumber);
        pDialog.setTitleText("实名认证中...");
        pDialog.show();
        HttpUtils.getInstance().upLoadFile(mActivity, params, API.approverInfo, mList, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                pDialog.hide();
                showToast("实名认证成功");
//                getFragmentManager().popBackStack();
                mActivity.finish();
            }

            @Override
            public void onError(Request error, Exception e) {
                pDialog.hide();
                showToast("实名认证失败，请尝试重新提交");
            }
        });
    }
}
