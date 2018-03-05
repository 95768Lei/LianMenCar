package com.zl.webproject.ui.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseDialog;
import com.zl.webproject.view.MyWheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglei on 2018/2/25.
 */

public class ListDialog extends BaseDialog {

    private TextView title;
    private TextView tvOk;
    private MyWheelView wheelView;
    private List<String> mList = new ArrayList<>();

    private String titleName;

    public ListDialog(Activity mActivity, String titleName) {
        super(mActivity);
        this.titleName = titleName;
        initView();
        initListener();
    }

    private void initListener() {
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = wheelView.getSelected();
                String selectedText = wheelView.getSelectedText();
                if (onSelectorDataListener != null) {
                    onSelectorDataListener.onSelector(selectedText, selected);
                }
                dismissDialog();
            }
        });
    }

    private void initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_layout, null);
        title = view.findViewById(R.id.choose_list_title);
        tvOk = view.findViewById(R.id.choose_list_ok);
        wheelView = view.findViewById(R.id.wheelView);

        if (!TextUtils.isEmpty(titleName)) {
            title.setText(titleName);
        }

        initPopupWindow(view);
    }

    public void setData(List<String> mList) {
        this.mList.clear();
        this.mList.addAll(mList);

        wheelView.refreshData(this.mList);
    }

    private OnSelectorDataListener onSelectorDataListener;

    public void setOnSelectorDataListener(OnSelectorDataListener onSelectorDataListener) {
        this.onSelectorDataListener = onSelectorDataListener;
    }

    public interface OnSelectorDataListener {
        void onSelector(String data, int position);
    }

}
