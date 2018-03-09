package com.zl.webproject.ui.activity;

import android.os.Bundle;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.ui.fragment.UpdatePasswordFragment;

public class UpdatePasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        UpdatePasswordFragment updatePasswordFragment = new UpdatePasswordFragment();
        openFragmentNoTask(updatePasswordFragment,R.id.update_password_fl);
    }
}
