package com.zl.webproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/1/22.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.computeVerticalScrollExtent() + this.computeVerticalScrollOffset() >= this.computeVerticalScrollRange()) {
            if (onListViewToBottomListener != null) {
                onListViewToBottomListener.toBottom();
            }
        }
    }

    private OnListViewToBottomListener onListViewToBottomListener;

    public void setOnListViewToBottomListener(OnListViewToBottomListener onListViewToBottomListener) {
        this.onListViewToBottomListener = onListViewToBottomListener;
    }

    public interface OnListViewToBottomListener {
        void toBottom();
    }
}
