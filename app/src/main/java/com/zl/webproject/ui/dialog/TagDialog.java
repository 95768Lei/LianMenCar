package com.zl.webproject.ui.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglei
 * @date 18/2/23
 * 车行页检索用的弹窗
 */

public class TagDialog {

    private Activity mActivity;
    private GridView gridView;
    private List<String> mList = new ArrayList<>();
    private UniversalAdapter<String> mAdapter;
    private PopupWindow mPopupWindow;
    private int mPostion = -1;
    private View bgTv;
    private View view;

    public TagDialog(Activity mActivity, List<String> mList) {
        this.mActivity = mActivity;
        this.mList = mList;
    }

    public TagDialog(Activity mActivity) {
        this.mActivity = mActivity;
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mPostion == i) {
                    mPostion = -1;
                } else {
                    mPostion = i;
                }
                mAdapter.notifyDataSetChanged();
                if (onReturnDataListener != null) {
                    if (mPostion == -1) {
                        onReturnDataListener.returnData("");
                    } else {
                        onReturnDataListener.returnData(mList.get(mPostion));
                    }
                }
            }
        });

        bgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initData() {

    }

    private void initView() {
        view = LayoutInflater.from(mActivity).inflate(R.layout.tag_grid_layout, null);
        gridView = view.findViewById(R.id.tag_grid);
        bgTv = view.findViewById(R.id.bg_tv);
        mAdapter = new UniversalAdapter<String>(mActivity, mList, R.layout.tag_grid_item_layout) {
            @Override
            public void convert(UniversalViewHolder holder, int position, String s) {
                TextView item = holder.getView(R.id.tv_grid_item);
                item.setText(s);
                if (mPostion != -1) {
                    if (mPostion == position) {
                        item.setSelected(true);
                    } else {
                        item.setSelected(false);
                    }
                } else {
                    item.setSelected(false);
                }
            }
        };
        gridView.setAdapter(mAdapter);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setAnimationStyle(R.style.WindowScaleAnim);
    }

    public void setData(List<String> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();

    }

    public void showDialog(View view) {
        mPopupWindow.showAsDropDown(view);
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    private OnReturnDataListener onReturnDataListener;

    public void setOnReturnDataListener(OnReturnDataListener onReturnDataListener) {
        this.onReturnDataListener = onReturnDataListener;
    }

    public interface OnReturnDataListener {
        void returnData(String data);
    }

    public boolean isShow() {
        return mPopupWindow.isShowing();
    }

}
