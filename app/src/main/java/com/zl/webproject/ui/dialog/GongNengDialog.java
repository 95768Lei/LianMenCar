package com.zl.webproject.ui.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.zl.webproject.R;
import com.zl.webproject.base.BaseDialog;

/**
 * Created by Administrator on 2018/3/12.
 */

public class GongNengDialog extends BaseDialog implements View.OnClickListener {

    public GongNengDialog(Activity mActivity) {
        super(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.gong_neng_layout, null);
        view.findViewById(R.id.linear_share).setOnClickListener(this);
        view.findViewById(R.id.linear_edit).setOnClickListener(this);
        view.findViewById(R.id.linear_refresh).setOnClickListener(this);
        initPopupWindow(view);
    }

    @Override
    public void onClick(View view) {
        dismissDialog();
        switch (view.getId()) {
            case R.id.linear_share:
                if (onGongNengListener != null) {
                    onGongNengListener.share();
                }
                break;
            case R.id.linear_edit:
                if (onGongNengListener != null) {
                    onGongNengListener.edit();
                }
                break;
            case R.id.linear_refresh:
                if (onGongNengListener != null) {
                    onGongNengListener.refresh();
                }
                break;
        }
    }

    private OnGongNengListener onGongNengListener;

    public void setOnGongNengListener(OnGongNengListener onGongNengListener) {
        this.onGongNengListener = onGongNengListener;
    }

    public interface OnGongNengListener {
        void share();

        void edit();

        void refresh();
    }

}
