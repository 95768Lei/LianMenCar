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
    public static final String ISFINISH = "isFinish";
    //免费声明
    public static final String MFSM_URL = "http://app.tzlm.cc/view/disclaimer.jsp";
    //关于我们
    public static final String GYWM_URL = "http://app.tzlm.cc/app/main/toAboutUs.do";

//    public static final String BASEURL = "http://app.tzlm.cc";
//    public static final String BASEURL = "http://60.6.202.157:8889/CARS";
    public static final String BASEURL = "http://172.16.18.20:8888/CARS";

    //用户登录
    public static final String login = BASEURL + "/app/user/login.do";
    //用户注册检索手机是否被注册
    public static final String checkPhone = BASEURL + "/app/user/checkPhone.do";
    //用户注册
    public static final String Register = BASEURL + "/app/user/register.do";
    //用户密码修改
    public static final String editPass = BASEURL + "/app/user/editPass.do";
    //登录状态下，获得用户信息
    public static final String getUserInfo = BASEURL + "/app/user/getUserInfo.do";
    //获得个人完善信息
    public static final String getApproverInfo = BASEURL + "/app/user/getApproverInfo.do";
    //保存或修改信息个人完善
    public static final String approverInfo = BASEURL + "/app/user/approverInfo.do";
    //用户头像变更
    public static final String editUserImg = BASEURL + "/app/user/editUserImg.do";
    //用户昵称修修改
    public static final String editNikeName = BASEURL + "/app/user/editNikeName.do";
    //用户性别修改
    public static final String editUserSex = BASEURL + "/app/user/editUserSex.do";
    //手机号变更
    public static final String editUserPhone = BASEURL + "/app/user/editUserPhone.do";
    //申请中介
    public static final String applyInfo = BASEURL + "/app/user/applyInfo.do";
    //忘记密码
    public static final String forgotPass = BASEURL + "/app/user/forgotPass.do";
    //联合登陆校验(微信、QQ)
    public static final String unionLogin = BASEURL + "/app/user/unionLogin.do";
    //绑定手机号
    public static final String saveBindingPhone = BASEURL + "/app/user/saveBindingPhone.do";
    //获取城市列表
    public static final String getCity = BASEURL + "/app/main/getCity.do";
    //热门车辆
    public static final String getHot = BASEURL + "/app/main/getHot.do";
    //热门检索
    public static final String getHotKey = BASEURL + "/app/main/getHotKey.do";
    //车源车辆检索数据
    public static final String getRetrieval = BASEURL + "/app/main/getRetrieval.do";
    //登陆用户收到的信息列表
    public static final String getMessageList = BASEURL + "/app/message/getMessageList.do";
    //未读信息数量
    public static final String unReadMessageList = BASEURL + "/app/message/unReadMessageList.do";
    //信息详情
    public static final String getMessageInfoById = BASEURL + "/app/message/getMessageInfoById.do";
    //全部标记已读
    public static final String readAllMessage = BASEURL + "/app/message/readAllMessage.do";
    //查看所有车行，根据认证状态与积分情况进行倒序
    public static final String getCarDealerList = BASEURL + "/app/dealer/getCarDealerList.do";
    //查看车行信息，包括车行评论信息
    public static final String getCarDealerById = BASEURL + "/app/dealer/getCarDealerById.do";
    //查看我车行
    public static final String toMyCarDealer = BASEURL + "/app/dealer/toMyCarDealer.do";
    //刷新车行信息,变更刷新时间
    public static final String resCarDealer = BASEURL + "/app/dealer/resCarDealer.do";
    //添加/修改车行信息
    public static final String saveOrUpdateCarDealer = BASEURL + "/app/dealer/saveOrUpdateCarDealer.do";
    //完善车行信息、营业执照、正反照片
    public static final String replenishCarDealerInfo = BASEURL + "/app/dealer/replenishCarDealerInfo.do";
    //会员车行
    public static final String carDealerVip = BASEURL + "/app/dealer/carDealerVip.do";
    //特约车行申请
    public static final String contributingCarDealer = BASEURL + "/app/dealer/contributingCarDealer.do";
    //我的车行关注列表
    public static final String getFollowList = BASEURL + "/app/follow/getFollowList.do";
    //关注/取消关注车行信息
    public static final String followDetails = BASEURL + "/app/follow/followDetails.do";
    //是否关注车行信息
    public static final String isFollowDetails = BASEURL + "/app/follow/isFollowDetails.do";
    //我的转发列表
    public static final String getForwardList = BASEURL + "/app/forward/getForwardList.do";
    //转发车辆信息
    public static final String forwardCar = BASEURL + "/app/forward/forwardCar.do";
    //微信转发后的车行信息
    public static final String toCarShop = BASEURL + "/app/forward/toCarShop.do";
    //微信转发后的车辆信息
    public static final String toCarDetails = BASEURL + "/app/forward/toCarDetails.do";
    //微信转发后的车辆信息更换手机号
    public static final String forwardCarInfo = BASEURL + "/app/forward/forwardCarInfo.do";
    //保存临时图片
    public static final String saveTempImg = BASEURL + "/app/file/saveTempImg.do";
    //删除临时图片
    public static final String delTempImg = BASEURL + "/app/file/delTempImg.do";
    //查看车行或首页下车辆信息
    public static final String getCarList = BASEURL + "/app/car/getCarList.do";
    //查看所有车辆，查询(分页)
    public static final String carInfoList = BASEURL + "/app/car/carInfoList.do";
    //查看车辆详情
    public static final String getCarInfo = BASEURL + "/app/car/getCarInfo.do";
    //查看我发布的车辆
    public static final String carInfoListToMy = BASEURL + "/app/car/carInfoListToMy.do";
    //添加/修改车辆信息
    public static final String saveOrUpdateCarInfo = BASEURL + "/app/car/saveOrUpdateCarInfo.do";
    //车辆信息变更：下架/已售/锁定
    public static final String editCarStateInfo = BASEURL + "/app/car/editCarStateInfo.do";
    //刷新车辆信息
    public static final String refreshCarInfo = BASEURL + "/app/car/refreshCarInfo.do";
    //反馈意见
    public static final String saveFeedback = BASEURL + "/app/feedback/saveFeedback.do";

}
