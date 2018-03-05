package com.zl.webproject.utils;

import android.app.Activity;
import android.widget.TextView;

import com.zl.webproject.R;

/**
 * Created by Administrator on 2018/3/5.
 */

public class TagUtils {

    public static void setTag(Activity mActivity, int carLabel, TextView tag) {
        switch (carLabel) {
            //过户车
            case 31:
                tag.setBackground(mActivity.getResources().getDrawable(R.drawable.line_orange_round));
                tag.setText("过户车");
                tag.setTextColor(mActivity.getResources().getColor(R.color.orange));
                break;
            //新车/准新车
            case 32:
                tag.setBackground(mActivity.getResources().getDrawable(R.drawable.line_green_round));
                tag.setText("新车/准新车");
                tag.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                break;
            //下线车
            case 33:
                tag.setBackground(mActivity.getResources().getDrawable(R.drawable.line_blue_round));
                tag.setText("下线车");
                tag.setTextColor(mActivity.getResources().getColor(R.color.blue));
                break;
            //全款无绿本
            case 25:
                tag.setBackground(mActivity.getResources().getDrawable(R.drawable.line_red_round));
                tag.setText("全款无绿本");
                tag.setTextColor(mActivity.getResources().getColor(R.color.red));
                break;
            //全款有绿本
            case 26:
                tag.setBackground(mActivity.getResources().getDrawable(R.drawable.line_green_empty_round));
                tag.setText("全款有绿本");
                tag.setTextColor(mActivity.getResources().getColor(R.color.green_empty));
                break;
            //未上户
            case 27:
                tag.setBackground(mActivity.getResources().getDrawable(R.drawable.line_color1_round));
                tag.setText("未上户");
                tag.setTextColor(mActivity.getResources().getColor(R.color.color1));
                break;
            //抵押银行
            case 28:
                tag.setBackground(mActivity.getResources().getDrawable(R.drawable.line_color2_round));
                tag.setText("抵押银行");
                tag.setTextColor(mActivity.getResources().getColor(R.color.color2));
                break;
            //抵押金融
            case 29:
                tag.setBackground(mActivity.getResources().getDrawable(R.drawable.line_color3_round));
                tag.setText("抵押金融");
                tag.setTextColor(mActivity.getResources().getColor(R.color.color3));
                break;
            //抵押小贷
            case 30:
                tag.setBackground(mActivity.getResources().getDrawable(R.drawable.line_color4_round));
                tag.setText("抵押小贷");
                tag.setTextColor(mActivity.getResources().getColor(R.color.color4));
                break;

        }
    }

}
