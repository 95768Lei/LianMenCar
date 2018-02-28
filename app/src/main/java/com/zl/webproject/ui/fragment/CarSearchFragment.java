package com.zl.webproject.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;

/**
 * @author zhanglei
 * @date 18/2/28
 */
public class CarSearchFragment extends BaseFragment {


    public CarSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_search, container, false);
    }

}
