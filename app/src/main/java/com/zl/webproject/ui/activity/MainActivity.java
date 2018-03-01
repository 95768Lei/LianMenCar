package com.zl.webproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseActivity;
import com.zl.webproject.ui.fragment.CarFragment;
import com.zl.webproject.ui.fragment.CarHangFragment;
import com.zl.webproject.ui.fragment.HomeFragment;
import com.zl.webproject.ui.fragment.PersonFragment;
import com.zl.webproject.utils.BottomNavigationViewHelper;
import com.zl.webproject.utils.FragmentHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.main_nv)
    BottomNavigationView mainNv;
    private String imagePath;
    private HomeFragment homeFragment;
    private CarFragment carFragment;
    private CarHangFragment carHangFragment;
    private PersonFragment personFragment;
    private FragmentHelper helper;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainActivity = this;
        initView();
        initListener();
    }

    private void initListener() {
        mainNv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_home:
                        helper.showFragment(homeFragment);
                        break;
                    case R.id.main_car:
                        helper.showFragment(carFragment);
                        break;
                    case R.id.main_car_hang:
                        helper.showFragment(carHangFragment);
                        break;
                    case R.id.main_person:
                        helper.showFragment(personFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void initView() {
        BottomNavigationViewHelper.disableShiftMode(mainNv);
        homeFragment = HomeFragment.newInstance();
        carFragment = CarFragment.newInstance();
        carHangFragment = CarHangFragment.newInstance();
        personFragment = PersonFragment.newInstance();
        helper = FragmentHelper.builder(mActivity)
                .attach(R.id.main_rl)
                .addFragment(homeFragment)
                .addFragment(carFragment)
                .addFragment(carHangFragment)
                .addFragment(personFragment)
                .commit();
        helper.showFragment(homeFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mActivity.startActivity(intent);
        }
        return true;
    }
}
