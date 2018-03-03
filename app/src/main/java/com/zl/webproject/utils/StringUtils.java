package com.zl.webproject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/24.
 */

public class StringUtils {

    /**
     * 将身份证号码加密显示
     *
     * @param idCardNumber
     * @return
     */
    public static String handleIdCardNumber(String idCardNumber) {
        String head = idCardNumber.substring(0, 4);
        String footer = idCardNumber.substring(14, 18);
        return head + "**********" + footer;
    }

    /**
     * 通过身份证计算出性别
     *
     * @param idCardNumber
     * @return
     */
    public static String countSex(String idCardNumber) {
        String number = idCardNumber.substring(16, 17);
        int i = Integer.parseInt(number);
        if (i % 2 == 0) {
            return "女";
        } else {
            return "男";
        }
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String dateYYYY_MM_DD(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String dateYYYY_MM_DD_HH_mm_ss(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}
