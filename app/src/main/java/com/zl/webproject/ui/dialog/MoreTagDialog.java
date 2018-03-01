package com.zl.webproject.ui.dialog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zl.webproject.R;
import com.zl.webproject.base.RecyclerAdapter;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/23.
 */

public class MoreTagDialog {
    private Activity mActivity;
    private ListView listView;
    private List<String> mList = new ArrayList<>();
    private UniversalAdapter<String> mAdapter;
    private PopupWindow mPopupWindow;
    private SparseArray<Integer> sparseArray = new SparseArray<>();
    private View bgTv;

    public MoreTagDialog(Activity mActivity) {
        this.mActivity = mActivity;
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.notifyDataSetChanged();
                if (onReturnDataListener != null) {
                    onReturnDataListener.returnData(mList.get(i));
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
        for (int i = 0; i < 5; i++) {
            mList.add("");
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.listview_layout, null);
        listView = view.findViewById(R.id.list_view);
        bgTv = view.findViewById(R.id.bg_tv);
        mAdapter = new UniversalAdapter<String>(mActivity, mList, R.layout.more_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, final int position, String s) {
                RecyclerView itemRv = holder.getView(R.id.list_item_rv);
//                itemRv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
                itemRv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    list.add("");
                }
                final RecyclerAdapter<String> adapter = new RecyclerAdapter<String>(mActivity, list, R.layout.more_tv_item) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int p) {
                        View view = holder.getView(R.id.tv_more_item);
                        view.setSelected(false);
                        try {
                            Integer integer = sparseArray.get(position);
                            if (integer == p) {
                                view.setSelected(true);
                            }
                        } catch (Exception e) {

                        }
                    }
                };
                itemRv.setAdapter(adapter);
                adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        try {
                            Integer integer = sparseArray.get(position);
                            if (integer == i) {
                                sparseArray.remove(position);
                            } else {
                                sparseArray.put(position, i);
                            }
                        } catch (Exception e) {
                            sparseArray.put(position, i);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        listView.setAdapter(mAdapter);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setAnimationStyle(R.style.WindowScaleAnim);

    }

    public void showDialog(View view) {
        mPopupWindow.showAsDropDown(view);
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public boolean isShow() {
        return mPopupWindow.isShowing();
    }

    private TagDialog.OnReturnDataListener onReturnDataListener;

    public void setOnReturnDataListener(TagDialog.OnReturnDataListener onReturnDataListener) {
        this.onReturnDataListener = onReturnDataListener;
    }

    public interface OnReturnDataListener {
        void returnData(String data);
    }
}
