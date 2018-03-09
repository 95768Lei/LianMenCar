package com.zl.webproject.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zl.webproject.R;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.base.ViewHolder;
import com.zl.webproject.model.CarInfoEntity;

/**
 * Created by Administrator on 2018\3\6 0006.
 */

public class BindDataUtils {

    public static void bindCarData(Activity mActivity, UniversalViewHolder holder, CarInfoEntity s) {
        bindCarData(mActivity, holder, s, true);
    }

    public static void bindCarData(Activity mActivity, UniversalViewHolder holder, CarInfoEntity s, boolean isShowTag) {
        Integer carSource = s.getCarSource();
        TextView carTag = holder.getView(R.id.tv_car_tag);
        TextView carTag1 = holder.getView(R.id.tv_car_tag1);
        ImageView ivCarTag = holder.getView(R.id.iv_car_tag);
        ImageView ivTag = holder.getView(R.id.iv_tag);
        Integer carLocking = s.getCarLocking();
        Integer carSeized = s.getCarSeized();
        Integer carPeccancy = s.getCarPeccancy();
        Integer carState = s.getCarState();
        if (isShowTag) {
            if (carSource == 0) {
                ivCarTag.setImageResource(R.mipmap.geren);
            } else {
                ivCarTag.setImageResource(R.mipmap.hang);
            }
        } else {
            ivCarTag.setVisibility(View.GONE);
        }
        switch (carState) {
            //锁定
            case 1:
                ivTag.setVisibility(View.VISIBLE);
                ivTag.setImageResource(R.mipmap.ic_yu_ding);
                break;
            //出售
            case 2:
                ivTag.setVisibility(View.VISIBLE);
                ivTag.setImageResource(R.mipmap.ic_sell_ok);
                break;
            //下架
            case 3:
                ivTag.setVisibility(View.VISIBLE);
                ivTag.setImageResource(R.mipmap.ic_sold_out);
                break;
            default:
                ivTag.setVisibility(View.GONE);
                break;
        }
        holder.setText(R.id.tv_car_name, s.getCarTitle());
        holder.setText(R.id.tv_car_money, s.getCarPrice() + "万");
        holder.setText(R.id.tv_car_city, s.getCarAreaCitysEntity().getCityName());
        TagUtils.setTag(mActivity, s.getLabel().getId(), carTag);
        holder.setText(R.id.tv_car_data, s.getCarLicensingDateStr() + "/" + s.getCarMileage() + "公里");
        holder.setImageUrl(mActivity, R.id.iv_car_icon, s.getCarImg());
        holder.setText(R.id.carForWard, s.getCarForWard() + "");
        holder.setText(R.id.tv_carBrowse, s.getCarBrowse() + "");

        if (carLocking == 1) {
            carTag1.setText("锁定");
            carTag1.setVisibility(View.VISIBLE);
            return;
        } else {
            carTag1.setVisibility(View.GONE);
        }
        if (carSeized == 1) {
            carTag1.setText("查封");
            carTag1.setVisibility(View.VISIBLE);
            return;
        } else {
            carTag1.setVisibility(View.GONE);
        }
        if (carPeccancy == 1) {
            carTag1.setText("违章");
            carTag1.setVisibility(View.VISIBLE);
            return;
        } else {
            carTag1.setVisibility(View.GONE);
        }
    }

    public static void bindCarData(Activity mActivity, ViewHolder holder, CarInfoEntity s) {
        Integer carSource = s.getCarSource();
        TextView carTag = holder.getView(R.id.tv_car_tag);
        TextView carTag1 = holder.getView(R.id.tv_car_tag1);
        ImageView ivTag = holder.getView(R.id.iv_tag);
        ImageView ivCarTag = holder.getView(R.id.iv_car_tag);
        Integer carLocking = s.getCarLocking();
        Integer carSeized = s.getCarSeized();
        Integer carPeccancy = s.getCarPeccancy();
        Integer carState = s.getCarState();
        if (carSource == 0) {
            ivCarTag.setImageResource(R.mipmap.geren);
        } else {
            ivCarTag.setImageResource(R.mipmap.hang);
        }
        switch (carState) {
            //锁定
            case 1:
                ivTag.setVisibility(View.VISIBLE);
                ivTag.setImageResource(R.mipmap.ic_yu_ding);
                break;
            //出售
            case 2:
                ivTag.setVisibility(View.VISIBLE);
                ivTag.setImageResource(R.mipmap.ic_sell_ok);
                break;
            //下架
            case 3:
                ivTag.setVisibility(View.VISIBLE);
                ivTag.setImageResource(R.mipmap.ic_sold_out);
                break;
            default:
                ivTag.setVisibility(View.GONE);
                break;
        }
        holder.setText(R.id.tv_car_name, s.getCarTitle());
        holder.setText(R.id.tv_car_money, s.getCarPrice() + "万");
        holder.setText(R.id.tv_car_city, s.getCarAreaCitysEntity().getCityName());
        TagUtils.setTag(mActivity, s.getLabel().getId(), carTag);
        holder.setText(R.id.tv_car_data, s.getCarLicensingDateStr() + "/" + s.getCarMileage() + "公里");
        holder.setImageUrl(mActivity, R.id.iv_car_icon, s.getCarImg());
        holder.setText(R.id.carForWard, s.getCarForWard() + "");
        holder.setText(R.id.tv_carBrowse, s.getCarBrowse() + "");

        if (carLocking == 1) {
            carTag1.setText("锁定");
            carTag1.setVisibility(View.VISIBLE);
            return;
        } else {
            carTag1.setVisibility(View.GONE);
        }
        if (carSeized == 1) {
            carTag1.setText("查封");
            carTag1.setVisibility(View.VISIBLE);
            return;
        } else {
            carTag1.setVisibility(View.GONE);
        }
        if (carPeccancy == 1) {
            carTag1.setText("违章");
            carTag1.setVisibility(View.VISIBLE);
            return;
        } else {
            carTag1.setVisibility(View.GONE);
        }
    }

}
