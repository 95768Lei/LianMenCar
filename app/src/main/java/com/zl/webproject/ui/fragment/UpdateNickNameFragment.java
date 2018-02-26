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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
        String nickName = etInputNickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)){
            showToast("昵称不能为空");
            return;
        }

    }
}
