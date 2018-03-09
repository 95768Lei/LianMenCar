package com.zl.webproject.ui.activity;

import android.os.Bundle;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.ui.fragment.RealPersonFragment;
import com.zl.webproject.ui.fragment.UpdatePhoneFragment;

public class RealPersonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_person);
        RealPersonFragment realPersonFragment = new RealPersonFragment();
        openFragmentNoTask(realPersonFragment, R.id.update_phone_fl);
    }
}
