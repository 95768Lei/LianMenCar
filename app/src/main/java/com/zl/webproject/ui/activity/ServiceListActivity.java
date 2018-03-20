package com.zl.webproject.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.ui.fragment.ServiceListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhanglei
 * @date 18/3/20
 */
public class ServiceListActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    private ServiceListFragment serviceListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        ButterKnife.bind(this);
        int type = getIntent().getIntExtra("type", -1);
        serviceListFragment = ServiceListFragment.newInstance(type);
        openFragmentNoTask(serviceListFragment, R.id.service_list_rl);
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                search();
                break;
        }
    }

    private void search() {
        String searchData = etSearch.getText().toString().trim();
    }
}
