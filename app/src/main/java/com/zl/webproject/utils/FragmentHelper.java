package com.zl.webproject.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglei
 * @date 17/06/21
 */

public class FragmentHelper {

    private AppCompatActivity mActivity;
    private Fragment hideFragment;
    private List<Fragment> fList;
    private int layoutId;

    private FragmentHelper(Builder builder) {
        mActivity = builder.mActivity;
        fList = builder.mList;
        hideFragment = fList.get(0);
        layoutId = builder.layoutId;
    }

    /**
     * 显示Fragment
     *
     * @param fragment
     */
    public void showFragment(Fragment fragment) {

        if (hideFragment != null) {
            //想要显示的Fragment 和需要隐藏的Fragment是同一个，则返回
            if (fragment == hideFragment) {
                return;
            }
            hideFragment(hideFragment);
        }

        //判断该Fragment是否已经添加到实务当中
        if (!mActivity.getSupportFragmentManager().getFragments().contains(fragment)) {
            mActivity.getSupportFragmentManager().beginTransaction().add(layoutId, fragment).show(fragment).commit();
        } else {
            mActivity.getSupportFragmentManager().beginTransaction().show(fragment).commit();
        }

        hideFragment = fragment;
    }

    /**
     * 隐藏Fragment
     *
     * @param fragment
     */
    public void hideFragment(Fragment fragment) {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.hide(fragment).commit();
    }

    public static IParentLayout builder(AppCompatActivity activity) {
        return new Builder(activity);
    }

    public interface IParentLayout {
        IFragmentInterface attach(int layoutId);
    }

    public interface IFragmentInterface {
        IFragmentInterface addFragment(Fragment fragment);

        IFragmentSetting beginSettings();

        FragmentHelper commit();
    }

    public interface IFragmentSetting {
        IFragmentInterface endSettings();
    }

    public static class Builder implements IParentLayout, IFragmentInterface, IFragmentSetting {

        private final FragmentTransaction transaction;
        private int layoutId;
        private AppCompatActivity mActivity;
        private List<Fragment> mList = new ArrayList<>();

        private Builder(AppCompatActivity context) {
            mActivity = context;
            transaction = mActivity.getSupportFragmentManager().beginTransaction();
        }

        @Override
        public IFragmentInterface attach(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        @Override
        public IFragmentInterface addFragment(Fragment fragment) {
            mList.add(fragment);
            return this;
        }

        @Override
        public IFragmentSetting beginSettings() {
            return this;
        }

        @Override
        public FragmentHelper commit() {
            transaction.add(layoutId, mList.get(0)).commit();
            return new FragmentHelper(this);
        }

        @Override
        public IFragmentInterface endSettings() {
            return this;
        }
    }

}
