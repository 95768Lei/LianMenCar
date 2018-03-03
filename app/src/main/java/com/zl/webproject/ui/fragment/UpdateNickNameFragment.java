package com.zl.webproject.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.model.CarUserEntity;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.OnReturnStringListener;
import com.zl.webproject.utils.SpUtlis;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * @author zhanglei
 *         修改昵称
 */
public class UpdateNickNameFragment extends BaseFragment {


    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.et_input_nickname)
    EditText etInputNickname;
    @BindView(R.id.tv_update_nickname)
    TextView tvUpdateNickname;
    Unbinder unbinder;

    private OnReturnStringListener onReturnStringListener;

    public UpdateNickNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_nick_name, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setClickable(true);
        initView();
        return view;
    }

    private void initView() {
        tvTitleName.setText("修改昵称");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_title_back, R.id.tv_update_nickname})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_update_nickname:
                updateNickName();
                break;
        }
    }

    private void updateNickName() {
        final String nickName = etInputNickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            showToast("昵称不能为空");
            return;
        }
        final CarUserEntity userData = SpUtlis.getUserData(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("nikeName", nickName);
        params.put("uid", userData.getId() + "");
        HttpUtils.getInstance().Post(mActivity, params, API.editNikeName, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                getFragmentManager().popBackStack();
                showToast("昵称修改成功");
                userData.setUserNikeName(nickName);
                SpUtlis.setUserData(mActivity, userData);
                if (onReturnStringListener != null) {
                    onReturnStringListener.onReturnString(nickName);
                }
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });

    }

    public void setOnReturnStringListener(OnReturnStringListener onReturnStringListener) {
        this.onReturnStringListener = onReturnStringListener;
    }
}
