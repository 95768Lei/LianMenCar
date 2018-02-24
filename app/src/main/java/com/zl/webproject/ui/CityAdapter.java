package com.zl.webproject.ui;

import android.content.Context;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.model.CityBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public class CityAdapter extends UniversalAdapter<CityBean> implements SectionIndexer {

    public CityAdapter(Context context, List<CityBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(UniversalViewHolder holder, int position, CityBean cityBean) {
        TextView tvLetter = holder.getView(R.id.catalog);

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText(cityBean.getLetters());
        } else {
            tvLetter.setVisibility(View.GONE);
        }
        holder.setText(R.id.city_name, cityBean.getName());
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int i) {
        return list.get(i).getLetters().charAt(0);
    }
}
