package com.zl.webproject.utils;

import android.os.Environment;

/**
 * Created by Administrator on 2017/8/25.
 */

public class API {

    //图片存储父路径
    public static final String image_file_path = Environment.getExternalStorageDirectory().getPath() + "/LianMenImage/";
    //裁剪后得到的身份证号码条图片
    public static final String image_path_id_number = image_file_path + "/name.JPG";

    public static final String TESSBASE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/tesseract/";

    //免费声明
    public static final String MFSM_URL = "http://app.tzlm.cc/view/disclaimer.jsp";
    //关于我们
    public static final String GYWM_URL = "http://app.tzlm.cc/app/main/toAboutUs.do";

}
