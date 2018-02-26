package com.zl.webproject.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.ui.activity.CameraActivity;
import com.zl.webproject.utils.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
        view.setClickable(true);
        return view;
    }

    private void initView() {
        tvTitleName.setText("实名认证");
        //时间选择器
        //选中事件回调
        pvTime = new TimePickerView.Builder(mActivity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String date1 = new SimpleDateFormat("yyyyMMdd").format(date);
                String date2 = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                tvPersonDate.setText(date2);
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
                getFragmentManager().popBackStack();
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
        String realName = etInputRealName.getText().toString().trim();
        String idNumber = etInputIdNumber.getText().toString().trim();
    }
}
