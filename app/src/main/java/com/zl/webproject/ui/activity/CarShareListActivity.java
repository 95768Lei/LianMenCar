package com.zl.webproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.ui.fragment.CarForwardFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 * @date 18/2/28
 */
public class CarShareListActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    private CarForwardFragment carForwardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_share_list);
        ButterKnife.bind(this);
        tvTitleName.setText("赚取佣金");
        carForwardFragment = CarForwardFragment.newInstance();
        carForwardFragment.setOnToFinishListener(new CarForwardFragment.OnToFinishListener() {
            @Override
            public void onFinish() {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
        openFragmentNoTask(carForwardFragment, R.id.car_share_rl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        carForwardFragment.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }
}
