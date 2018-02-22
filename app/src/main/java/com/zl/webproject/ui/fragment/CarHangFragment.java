package com.zl.webproject.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zl.webproject.R;

/**
 * @author zhanglei
 * @date 18/2/22
 */
public class CarHangFragment extends Fragment {


    public CarHangFragment() {
        // Required empty public constructor
    }

    public static CarHangFragment newInstance() {

        Bundle args = new Bundle();

        CarHangFragment fragment = new CarHangFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_hang, container, false);
    }

}
