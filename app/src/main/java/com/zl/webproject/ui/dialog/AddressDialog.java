package com.zl.webproject.ui.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseDialog;
import com.zl.webproject.view.SideBar;

/**
 * Created by Administrator on 2018/2/22.
 */

public class AddressDialog extends BaseDialog {

    private SideBar dialogSide;
    private TextView dialogText;
    private ListView dialogListView;

    public AddressDialog(Activity mActivity) {
        super(mActivity);
        initView();
        initListener();
    }

    private void initListener() {
        dialogSide.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {

            }
        });
    }

    private void initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.address_layout, null);
        dialogSide = view.findViewById(R.id.dialog_side);
        dialogText = view.findViewById(R.id.dialog_text);
        dialogListView = view.findViewById(R.id.dialog_listView);
        dialogSide.setTextView(dialogText);
        initPopupWindow(view);
    }

}
